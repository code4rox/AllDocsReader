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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.model;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.ArrayRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.BOFRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.BlankRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.BoolErrRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.CalcCountRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.CalcModeRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.DVALRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.DateWindow1904Record;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.DefaultColWidthRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.DefaultRowHeightRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.DeltaRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.DimensionsRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.DrawingRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.DrawingSelectionRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.EOFRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.FeatRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.FormulaRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.GridsetRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.GutsRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.HyperlinkRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.IndexRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.IterationRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.LabelRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.LabelSSTRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.NumberRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.ObjRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.PaneRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.PrecisionRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.PrintGridlinesRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.PrintHeadersRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.RKRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.Record;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.RecordBase;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.RefModeRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.RowRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.SCLRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.SaveRecalcRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.SelectionRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.SharedFormulaRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.TableRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.TextObjectRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.UncalcedRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.UnknownRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.WindowOneRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.WindowTwoRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.aggregates.ColumnInfoRecordsAggregate;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.aggregates.ConditionalFormattingTable;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.aggregates.DataValidityTable;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.aggregates.MergedCellsTable;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.aggregates.PageSettingsBlock;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.aggregates.WorksheetProtectionBlock;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.pivottable.ViewDefinitionRecord;

import java.util.List;


/**
 * Finds correct insert positions for records in workbook streams<p/>
 *
 * See OOO excelfileformat.pdf sec. 4.2.5 'Record Order in a BIFF8 Workbook Stream'
 *
 * @author Josh Micich
 */
final class RecordOrderer {

	// TODO - simplify logic using a generalised record ordering

	private RecordOrderer() {
		// no instances of this class
	}
	/**
	 * Adds the specified new record in the correct place in sheet records list
	 */
	public static void addNewSheetRecord(List<RecordBase> sheetRecords, RecordBase newRecord) {
		int index = findSheetInsertPos(sheetRecords, newRecord.getClass());
		sheetRecords.add(index, newRecord);
	}

	private static int findSheetInsertPos(List<RecordBase> records, Class<? extends RecordBase> recClass) {
		if (recClass == DataValidityTable.class) {
			return findDataValidationTableInsertPos(records);
		}
		if (recClass == MergedCellsTable.class) {
			return findInsertPosForNewMergedRecordTable(records);
		}
		if (recClass == ConditionalFormattingTable.class) {
			return findInsertPosForNewCondFormatTable(records);
		}
		if (recClass == GutsRecord.class) {
			return getGutsRecordInsertPos(records);
		}
		if (recClass == PageSettingsBlock.class) {
			return getPageBreakRecordInsertPos(records);
		}
		if (recClass == WorksheetProtectionBlock.class) {
			return getWorksheetProtectionBlockInsertPos(records);
		}
		throw new RuntimeException("Unexpected record class (" + recClass.getName() + ")");
	}

	/**
	 * Finds the index where the protection block should be inserted
	 * @param records the records for this sheet
	 * <pre>
	 * + BOF
	 * o INDEX
	 * o Calculation Settings Block
	 * o PRINTHEADERS
	 * o PRINTGRIDLINES
	 * o GRIDSET
	 * o GUTS
	 * o DEFAULTROWHEIGHT
	 * o SHEETPR
	 * o Page Settings Block
	 * o Worksheet Protection Block
	 * o DEFCOLWIDTH
	 * oo COLINFO
	 * o SORT
	 * + DIMENSION
	 * </pre>
	 */
	private static int getWorksheetProtectionBlockInsertPos(List<RecordBase> records) {
		int i = getDimensionsIndex(records);
		while (i > 0) {
			i--;
			Object rb = records.get(i);
			if (!isProtectionSubsequentRecord(rb)) {
				return i+1;
			}
		}
		throw new IllegalStateException("did not find insert pos for protection block");
	}


	/**
	 * These records may occur between the 'Worksheet Protection Block' and DIMENSION:
	 * <pre>
	 * o DEFCOLWIDTH
	 * oo COLINFO
	 * o SORT
	 * </pre>
	 */
	private static boolean isProtectionSubsequentRecord(Object rb) {
		if (rb instanceof ColumnInfoRecordsAggregate) {
			return true; // oo COLINFO
		}
		if (rb instanceof Record) {
			Record record = (Record) rb;
			switch (record.getSid()) {
				case DefaultColWidthRecord.sid:
				case UnknownRecord.SORT_0090:
					return true;
			}
		}
		return false;
	}

	private static int getPageBreakRecordInsertPos(List<RecordBase> records) {
		int dimensionsIndex = getDimensionsIndex(records);
		int i = dimensionsIndex-1;
		while (i > 0) {
			i--;
			Object rb = records.get(i);
			if (isPageBreakPriorRecord(rb)) {
				return i+1;
			}
		}
		throw new RuntimeException("Did not find insert point for GUTS");
	}
	private static boolean isPageBreakPriorRecord(Object rb) {
		if (rb instanceof Record) {
			Record record = (Record) rb;
			switch (record.getSid()) {
				case BOFRecord.sid:
				case IndexRecord.sid:
				// calc settings block
					case UncalcedRecord.sid:
					case CalcCountRecord.sid:
					case CalcModeRecord.sid:
					case PrecisionRecord.sid:
					case RefModeRecord.sid:
					case DeltaRecord.sid:
					case IterationRecord.sid:
					case DateWindow1904Record.sid:
					case SaveRecalcRecord.sid:
				// end calc settings
				case PrintHeadersRecord.sid:
				case PrintGridlinesRecord.sid:
				case GridsetRecord.sid:
				case DefaultRowHeightRecord.sid:
				case UnknownRecord.SHEETPR_0081:
					return true;
				// next is the 'Worksheet Protection Block'
			}
		}
		return false;
	}
	/**
	 * Find correct position to add new CFHeader record
	 */
	private static int findInsertPosForNewCondFormatTable(List<RecordBase> records) {

		for (int i = records.size() - 2; i >= 0; i--) { // -2 to skip EOF record
			Object rb = records.get(i);
			if (rb instanceof MergedCellsTable) {
				return i + 1;
			}
			if (rb instanceof DataValidityTable) {
				continue;
			}

			Record rec = (Record) rb;
			switch (rec.getSid()) {
				case WindowTwoRecord.sid:
				case SCLRecord.sid:
				case PaneRecord.sid:
				case SelectionRecord.sid:
				case UnknownRecord.STANDARDWIDTH_0099:
				// MergedCellsTable usually here
				case UnknownRecord.LABELRANGES_015F:
				case UnknownRecord.PHONETICPR_00EF:
					// ConditionalFormattingTable goes here
					return i + 1;
				// HyperlinkTable (not aggregated by POI yet)
				// DataValidityTable
			}
		}
		throw new RuntimeException("Did not find Window2 record");
	}

	private static int findInsertPosForNewMergedRecordTable(List<RecordBase> records) {
		for (int i = records.size() - 2; i >= 0; i--) { // -2 to skip EOF record
			Object rb = records.get(i);
			if (!(rb instanceof Record)) {
				// DataValidityTable, ConditionalFormattingTable,
				// even PageSettingsBlock (which doesn't normally appear after 'View Settings')
				continue;
			}
			Record rec = (Record) rb;
			switch (rec.getSid()) {
				// 'View Settings' (4 records)
				case WindowTwoRecord.sid:
				case SCLRecord.sid:
				case PaneRecord.sid:
				case SelectionRecord.sid:

				case UnknownRecord.STANDARDWIDTH_0099:
					return i + 1;
			}
		}
		throw new RuntimeException("Did not find Window2 record");
	}


	/**
	 * Finds the index where the sheet validations header record should be inserted
	 * @param records the records for this sheet
	 *
	 * + WINDOW2
	 * o SCL
	 * o PANE
	 * oo SELECTION
	 * o STANDARDWIDTH
	 * oo MERGEDCELLS
	 * o LABELRANGES
	 * o PHONETICPR
	 * o Conditional Formatting Table
	 * o Hyperlink Table
	 * o Data Validity Table
	 * o SHEETLAYOUT
	 * o SHEETPROTECTION
	 * o RANGEPROTECTION
	 * + EOF
	 */
	private static int findDataValidationTableInsertPos(List<RecordBase> records) {
		int i = records.size() - 1;
		if (!(records.get(i) instanceof EOFRecord)) {
			throw new IllegalStateException("Last sheet record should be EOFRecord");
		}
		while (i > 0) {
			i--;
			RecordBase rb = records.get(i);
			if (isDVTPriorRecord(rb)) {
				Record nextRec = (Record) records.get(i + 1);
				if (!isDVTSubsequentRecord(nextRec.getSid())) {
					throw new IllegalStateException("Unexpected (" + nextRec.getClass().getName()
							+ ") found after (" + rb.getClass().getName() + ")");
				}
				return i+1;
			}
			Record rec = (Record) rb;
			if (!isDVTSubsequentRecord(rec.getSid())) {
				throw new IllegalStateException("Unexpected (" + rec.getClass().getName()
						+ ") while looking for DV Table insert pos");
			}
		}
		return 0;
	}


	private static boolean isDVTPriorRecord(RecordBase rb) {
		if (rb instanceof MergedCellsTable || rb instanceof ConditionalFormattingTable) {
			return true;
		}
		short sid = ((Record)rb).getSid();
		switch(sid) {
			case WindowTwoRecord.sid:
			case UnknownRecord.SCL_00A0:
			case PaneRecord.sid:
			case SelectionRecord.sid:
			case UnknownRecord.STANDARDWIDTH_0099:
			// MergedCellsTable
			case UnknownRecord.LABELRANGES_015F:
			case UnknownRecord.PHONETICPR_00EF:
			// ConditionalFormattingTable
			case HyperlinkRecord.sid:
			case UnknownRecord.QUICKTIP_0800:
            // name of a VBA module    
            case UnknownRecord.CODENAME_1BA:
				return true;
		}
		return false;
	}

	private static boolean isDVTSubsequentRecord(short sid) {
		switch(sid) {
			case UnknownRecord.SHEETEXT_0862:
			case UnknownRecord.SHEETPROTECTION_0867:
			case FeatRecord.sid:
			case EOFRecord.sid:
				return true;
		}
		return false;
	}
	/**
	 * DIMENSIONS record is always present
	 */
	private static int getDimensionsIndex(List<RecordBase> records) {
		int nRecs = records.size();
		for(int i=0; i<nRecs; i++) {
			if(records.get(i) instanceof DimensionsRecord) {
				return i;
			}
		}
		// worksheet stream is seriously broken
		throw new RuntimeException("DimensionsRecord not found");
	}

	private static int getGutsRecordInsertPos(List<RecordBase> records) {
		int dimensionsIndex = getDimensionsIndex(records);
		int i = dimensionsIndex-1;
		while (i > 0) {
			i--;
			RecordBase rb = records.get(i);
			if (isGutsPriorRecord(rb)) {
				return i+1;
			}
		}
		throw new RuntimeException("Did not find insert point for GUTS");
	}

	private static boolean isGutsPriorRecord(RecordBase rb) {
		if (rb instanceof Record) {
			Record record = (Record) rb;
			switch (record.getSid()) {
				case BOFRecord.sid:
				case IndexRecord.sid:
				// calc settings block
					case UncalcedRecord.sid:
					case CalcCountRecord.sid:
					case CalcModeRecord.sid:
					case PrecisionRecord.sid:
					case RefModeRecord.sid:
					case DeltaRecord.sid:
					case IterationRecord.sid:
					case DateWindow1904Record.sid:
					case SaveRecalcRecord.sid:
				// end calc settings
				case PrintHeadersRecord.sid:
				case PrintGridlinesRecord.sid:
				case GridsetRecord.sid:
					return true;
				// DefaultRowHeightRecord.sid is next
			}
		}
		return false;
	}
	/**
	 * @return <code>true</code> if the specified record ID terminates a sequence of Row block records
	 * It is assumed that at least one row or cell value record has been found prior to the current
	 * record
	 */
	public static boolean isEndOfRowBlock(int sid) {
		switch(sid) {
			case ViewDefinitionRecord.sid:
				// should have been prefixed with DrawingRecord (0x00EC), but bug 46280 seems to allow this
			case DrawingRecord.sid:
			case DrawingSelectionRecord.sid:
			case ObjRecord.sid:
			case TextObjectRecord.sid:

            case GutsRecord.sid:   // see Bugzilla 50426
			case WindowOneRecord.sid:
				// should really be part of workbook stream, but some apps seem to put this before WINDOW2
			case WindowTwoRecord.sid:
				return true;

			case DVALRecord.sid:
				return true;
			case EOFRecord.sid:
				// WINDOW2 should always be present, so shouldn't have got this far
				throw new RuntimeException("Found EOFRecord before WindowTwoRecord was encountered");
		}
		return PageSettingsBlock.isComponentRecord(sid);
	}

	/**
	 * @return <code>true</code> if the specified record id normally appears in the row blocks section
	 * of the sheet records
	 */
	public static boolean isRowBlockRecord(int sid) {
		switch (sid) {
			case RowRecord.sid:

			case BlankRecord.sid:
			case BoolErrRecord.sid:
			case FormulaRecord.sid:
			case LabelRecord.sid:
			case LabelSSTRecord.sid:
			case NumberRecord.sid:
			case RKRecord.sid:

			case ArrayRecord.sid:
			case SharedFormulaRecord.sid:
			case TableRecord.sid:
				return true;
		}
		return false;
	}
}
