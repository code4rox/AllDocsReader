/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.eventusermodel;


import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.eventusermodel.dummyrecord.MissingRowDummyRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.BOFRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.CellValueRecordInterface;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.MulBlankRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.MulRKRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.NoteRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.Record;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.RecordFactory;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.RowRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.SharedFormulaRecord;


/**
 * <p>A HSSFListener which tracks rows and columns, and will
 *  trigger your HSSFListener for all rows and cells,
 *  even the ones that aren't actually stored in the file.</p>
 * <p>This allows your code to have a more "Excel" like
 *  view of the data in the file, and not have to worry
 *  (as much) about if a particular row/cell is in the
 *  file, or was skipped from being written as it was
 *  blank.
 */
public final class MissingRecordAwareHSSFListener implements HSSFListener {
	private HSSFListener childListener;

	// Need to have different counters for cell rows and
	//  row rows, as you sometimes get a RowRecord in the
	//  middle of some cells, and that'd break everything
	private int lastRowRow;

	private int lastCellRow;
	private int lastCellColumn;

	/**
	 * Constructs a new MissingRecordAwareHSSFListener, which
	 *  will fire processRecord on the supplied child
	 *  HSSFListener for all Records, and missing records.
	 * @param listener The HSSFListener to pass records on to
	 */
	public MissingRecordAwareHSSFListener(HSSFListener listener) {
		resetCounts();
		childListener = listener;
	}

	public void processRecord(Record record) {
		int thisRow;
		int thisColumn;
		CellValueRecordInterface[] expandedRecords = null;

		if (record instanceof CellValueRecordInterface) {
			CellValueRecordInterface valueRec = (CellValueRecordInterface) record;
			thisRow = valueRec.getRow();
			thisColumn = valueRec.getColumn();
		} else {
			thisRow = -1;
			thisColumn = -1;

			switch (record.getSid()) {
				// the BOFRecord can represent either the beginning of a sheet or
				// the workbook
				case BOFRecord.sid:
					BOFRecord bof = (BOFRecord) record;
					if (bof.getType() == bof.TYPE_WORKBOOK || bof.getType() == bof.TYPE_WORKSHEET) {
						// Reset the row and column counts - new workbook / worksheet
						resetCounts();
					}
					break;
				case RowRecord.sid:
					RowRecord rowrec = (RowRecord) record;
					//System.out.println("Row " + rowrec.getRowNumber() + " found, first column at "
					//        + rowrec.getFirstCol() + " last column at " + rowrec.getLastCol());

					// If there's a jump in rows, fire off missing row records
					if (lastRowRow + 1 < rowrec.getRowNumber()) {
						for (int i = (lastRowRow + 1); i < rowrec.getRowNumber(); i++) {
							MissingRowDummyRecord dr = new MissingRowDummyRecord(i);
							childListener.processRecord(dr);
						}
					}

					// Record this as the last row we saw
					lastRowRow = rowrec.getRowNumber();
					break;

				case SharedFormulaRecord.sid:
					// SharedFormulaRecord occurs after the first FormulaRecord of the cell range.
					// There are probably (but not always) more cell records after this
					// - so don't fire off the LastCellOfRowDummyRecord yet
					childListener.processRecord(record);
					return;
				case MulBlankRecord.sid:
					// These appear in the middle of the cell records, to
					//  specify that the next bunch are empty but styled
					// Expand this out into multiple blank cells
					MulBlankRecord mbr = (MulBlankRecord)record;
					expandedRecords = RecordFactory.convertBlankRecords(mbr);
					break;
				case MulRKRecord.sid:
					// This is multiple consecutive number cells in one record
					// Exand this out into multiple regular number cells
					MulRKRecord mrk = (MulRKRecord)record;
					expandedRecords = RecordFactory.convertRKRecords(mrk);
					break;
				case NoteRecord.sid:
					NoteRecord nrec = (NoteRecord) record;
					thisRow = nrec.getRow();
					thisColumn = nrec.getColumn();
					break;
			}
		}

		// First part of expanded record handling
		if(expandedRecords != null && expandedRecords.length > 0) {
			thisRow = expandedRecords[0].getRow();
			thisColumn = expandedRecords[0].getColumn();
		}

		// If we're on cells, and this cell isn't in the same
		//  row as the last one, then fire the
		//  dummy end-of-row records
		if(thisRow != lastCellRow && lastCellRow > -1) {
			for(int i=lastCellRow; i<thisRow; i++) {
				int cols = -1;
				if(i == lastCellRow) {
					cols = lastCellColumn;
				}
				childListener.processRecord(new LastCellOfRowDummyRecord(i, cols));
			}
		}

		// If we've just finished with the cells, then fire the
		// final dummy end-of-row record
		if(lastCellRow != -1 && lastCellColumn != -1 && thisRow == -1) {
			childListener.processRecord(new LastCellOfRowDummyRecord(lastCellRow, lastCellColumn));

			lastCellRow = -1;
			lastCellColumn = -1;
		}

		// If we've moved onto a new row, the ensure we re-set
		//  the column counter
		if(thisRow != lastCellRow) {
			lastCellColumn = -1;
		}

		// If there's a gap in the cells, then fire
		//  the dummy cell records
		if(lastCellColumn != thisColumn-1) {
			for(int i=lastCellColumn+1; i<thisColumn; i++) {
				childListener.processRecord(new MissingCellDummyRecord(thisRow, i));
			}
		}

		// Next part of expanded record handling
		if(expandedRecords != null && expandedRecords.length > 0) {
			thisColumn = expandedRecords[expandedRecords.length-1].getColumn();
		}


		// Update cell and row counts as needed
		if(thisColumn != -1) {
			lastCellColumn = thisColumn;
			lastCellRow = thisRow;
		}

		// Pass along the record(s)
		if(expandedRecords != null && expandedRecords.length > 0) {
			for(CellValueRecordInterface r : expandedRecords) {
				childListener.processRecord((Record)r);
			}
		} else {
			childListener.processRecord(record);
		}
	}

	private void resetCounts() {
		lastRowRow = -1;
		lastCellRow = -1;
		lastCellColumn = -1;
	}
}
