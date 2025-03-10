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

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.model.types.DOPAbstractType;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.Internal;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.LittleEndian;


/**
 * Comment me
 * 
 * @author Ryan Ackley
 */
@Internal
public final class DocumentProperties extends DOPAbstractType
{

    private byte[] _preserved;

    /**
     * @deprecated Use {@link #DocumentProperties(byte[],int,int)} instead
     */
    public DocumentProperties( byte[] tableStream, int offset )
    {
        this( tableStream, offset, DOPAbstractType.getSize() );
    }

    public DocumentProperties( byte[] tableStream, int offset, int length )
    {
        super.fillFields( tableStream, offset );

        final int supportedSize = DOPAbstractType.getSize();
        if ( length != supportedSize )
        {
            this._preserved = LittleEndian.getByteArray( tableStream, offset
                    + supportedSize, length - supportedSize );
        }
        else
        {
            _preserved = new byte[0];
        }
    }

    @Override
    public void serialize( byte[] data, int offset )
    {
        super.serialize( data, offset );
    }
}
