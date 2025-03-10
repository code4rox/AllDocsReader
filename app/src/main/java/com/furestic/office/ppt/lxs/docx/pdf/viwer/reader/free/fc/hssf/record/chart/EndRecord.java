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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.chart;


import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.RecordInputStream;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.StandardRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.LittleEndianOutput;


/**
 * The end record defines the end of a block of records for a (Graphing)
 * data object. This record is matched with a corresponding BeginRecord.
 *
 * @see BeginRecord
 *
 * @author Glen Stampoultzis (glens at apache.org)
 */

public final class EndRecord extends StandardRecord {
    public static final short sid = 0x1034;

    public EndRecord()
    {
    }

    /**
     * @param in unused (since this record has no data)
     */
    public EndRecord(RecordInputStream in)
    {
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[END]\n");
        buffer.append("[/END]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
    }

    protected int getDataSize() {
        return 0;
    }

    public short getSid()
    {
        return sid;
    }
    
    public Object clone() {
       EndRecord er = new EndRecord();
       // No data so nothing to copy
       return er;
    }
}
