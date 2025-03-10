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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.model;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.sprm.SprmBuffer;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.Internal;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.LittleEndian;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


@ Internal
public final class ComplexFileTable
{

    private static final byte GRPPRL_TYPE = 1;
    private static final byte TEXT_PIECE_TABLE_TYPE = 2;

    protected TextPieceTable _tpt;

    private SprmBuffer[] _grpprls;

    public ComplexFileTable()
    {
        _tpt = new TextPieceTable();
    }

    public ComplexFileTable(byte[] documentStream, byte[] tableStream, int offset, int fcMin)
        throws IOException
    {
        //skips through the prms before we reach the piece table. These contain data
        //for actual fast saved files
        List<SprmBuffer> sprmBuffers = new LinkedList<SprmBuffer>();
        while (tableStream[offset] == GRPPRL_TYPE)
        {
            offset++;
            int size = LittleEndian.getShort(tableStream, offset);
            offset += LittleEndian.SHORT_SIZE;
            byte[] bs = LittleEndian.getByteArray(tableStream, offset, size);
            offset += size;

            SprmBuffer sprmBuffer = new SprmBuffer(bs, false, 0);
            sprmBuffers.add(sprmBuffer);
        }
        this._grpprls = sprmBuffers.toArray(new SprmBuffer[sprmBuffers.size()]);

        if (tableStream[offset] != TEXT_PIECE_TABLE_TYPE)
        {
            throw new IOException("The text piece table is corrupted");
        }
        int pieceTableSize = LittleEndian.getInt(tableStream, ++offset);
        offset += LittleEndian.INT_SIZE;
        _tpt = new TextPieceTable(documentStream, tableStream, offset, pieceTableSize, fcMin);
    }

    public TextPieceTable getTextPieceTable()
    {
        return _tpt;
    }

    public SprmBuffer[] getGrpprls()
    {
        return _grpprls;
    }

}
