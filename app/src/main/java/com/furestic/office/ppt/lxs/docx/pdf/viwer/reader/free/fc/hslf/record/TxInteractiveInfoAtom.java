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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hslf.record;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.LittleEndian;


/**
 * Tne atom that holds starting and ending character positions of a hyperlink
 *
 * @author Yegor Kozlov
 */
public final class TxInteractiveInfoAtom extends RecordAtom {
    /**
     * Record header.
     */
    private byte[] _header;

    /**
     * Record data.
     */
    private byte[] _data;

    /**
     * Constructs a brand new link related atom record.
     */
    public TxInteractiveInfoAtom() {
        _header = new byte[8];
        _data = new byte[8];

        LittleEndian.putShort(_header, 2, (short)getRecordType());
        LittleEndian.putInt(_header, 4, _data.length);
    }

    /**
     * Constructs the link related atom record from its
     *  source data.
     *
     * @param source the source data as a byte array.
     * @param start the start offset into the byte array.
     * @param len the length of the slice in the byte array.
     */
    protected TxInteractiveInfoAtom(byte[] source, int start, int len) {
        // Get the header.
        _header = new byte[8];
        System.arraycopy(source,start,_header,0,8);

        // Get the record data.
        _data = new byte[len-8];
        System.arraycopy(source,start+8,_data,0,len-8);

    }

    /**
     * Gets the beginning character position
     *
     * @return the beginning character position
     */
    public int getStartIndex() {
        return LittleEndian.getInt(_data, 0);
    }

    /**
     * Sets the beginning character position
     * @param idx the beginning character position
     */
    public void setStartIndex(int idx) {
        LittleEndian.putInt(_data, 0, idx);
    }

    /**
     * Gets the ending character position
     *
     * @return the ending character position
     */
    public int getEndIndex() {
        return LittleEndian.getInt(_data, 4);
    }

    /**
     * Sets the ending character position
     *
     * @param idx the ending character position
     */
    public void setEndIndex(int idx) {
        LittleEndian.putInt(_data, 4, idx);
    }

    /**
     * Gets the record type.
     * @return the record type.
     */
    public long getRecordType() { return RecordTypes.TxInteractiveInfoAtom.typeID; }
    
    /**
     * 
     */
    public void dispose()
    {
        _header = null;
        _data = null;
    }
}
