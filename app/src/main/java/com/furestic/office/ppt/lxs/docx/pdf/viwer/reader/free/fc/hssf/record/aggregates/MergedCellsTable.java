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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.aggregates;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.model.RecordStream;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.MergeCellsRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ss.util.CellRangeAddressList;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ss.util.HSSFCellRangeAddress;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Josh Micich
 */
public final class MergedCellsTable extends RecordAggregate {
	private static int MAX_MERGED_REGIONS = 1027; // enforced by the 8224 byte limit

	private final List _mergedRegions;

	/**
	 * Creates an empty aggregate
	 */
	public MergedCellsTable() {
		_mergedRegions = new ArrayList();
	}

	/**
	 * reads zero or more consecutive {@link MergeCellsRecord}s
	 * @param rs
	 */
	public void read(RecordStream rs) {
		List temp = _mergedRegions;
		while (rs.peekNextClass() == MergeCellsRecord.class) {
			MergeCellsRecord mcr = (MergeCellsRecord) rs.getNext();
			int nRegions = mcr.getNumAreas();
			for (int i = 0; i < nRegions; i++) {
				HSSFCellRangeAddress cra = mcr.getAreaAt(i);
				temp.add(cra);
			}
		}
	}

	public int getRecordSize() {
		// a bit cheaper than the default impl
		int nRegions = _mergedRegions.size();
		if (nRegions < 1) {
			// no need to write a single empty MergeCellsRecord
			return 0;
		}
		int nMergedCellsRecords = nRegions / MAX_MERGED_REGIONS;
		int nLeftoverMergedRegions = nRegions % MAX_MERGED_REGIONS;

		int result = nMergedCellsRecords
				* (4 + CellRangeAddressList.getEncodedSize(MAX_MERGED_REGIONS)) + 4
				+ CellRangeAddressList.getEncodedSize(nLeftoverMergedRegions);
		return result;
	}

	public void visitContainedRecords(RecordVisitor rv) {
		int nRegions = _mergedRegions.size();
		if (nRegions < 1) {
			// no need to write a single empty MergeCellsRecord
			return;
		}

		int nFullMergedCellsRecords = nRegions / MAX_MERGED_REGIONS;
		int nLeftoverMergedRegions = nRegions % MAX_MERGED_REGIONS;
		HSSFCellRangeAddress[] cras = new HSSFCellRangeAddress[nRegions];
		_mergedRegions.toArray(cras);

		for (int i = 0; i < nFullMergedCellsRecords; i++) {
			int startIx = i * MAX_MERGED_REGIONS;
			rv.visitRecord(new MergeCellsRecord(cras, startIx, MAX_MERGED_REGIONS));
		}
		if (nLeftoverMergedRegions > 0) {
			int startIx = nFullMergedCellsRecords * MAX_MERGED_REGIONS;
			rv.visitRecord(new MergeCellsRecord(cras, startIx, nLeftoverMergedRegions));
		}
	}
	public void addRecords(MergeCellsRecord[] mcrs) {
		for (int i = 0; i < mcrs.length; i++) {
			addMergeCellsRecord(mcrs[i]);
		}
	}

	private void addMergeCellsRecord(MergeCellsRecord mcr) {
		int nRegions = mcr.getNumAreas();
		for (int i = 0; i < nRegions; i++) {
			HSSFCellRangeAddress cra = mcr.getAreaAt(i);
			_mergedRegions.add(cra);
		}
	}

	public HSSFCellRangeAddress get(int index) {
		checkIndex(index);
		return (HSSFCellRangeAddress) _mergedRegions.get(index);
	}

	public void remove(int index) {
		checkIndex(index);
		_mergedRegions.remove(index);
	}

	private void checkIndex(int index) {
		if (index < 0 || index >= _mergedRegions.size()) {
			throw new IllegalArgumentException("Specified CF index " + index
					+ " is outside the allowable range (0.." + (_mergedRegions.size() - 1) + ")");
		}
	}

	public void addArea(int rowFrom, int colFrom, int rowTo, int colTo) {
		_mergedRegions.add(new HSSFCellRangeAddress(rowFrom, rowTo, colFrom, colTo));
	}

	public int getNumberOfMergedRegions() {
		return _mergedRegions.size();
	}
}
