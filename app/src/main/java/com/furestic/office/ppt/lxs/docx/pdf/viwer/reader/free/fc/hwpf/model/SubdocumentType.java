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

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.Internal;

/**
 * Document text parts that can have text pieces (CPs)
 * 
 * @author Sergey Vladimirov (vlsergey {at} gmail {dot} com)
 */
@Internal
public enum SubdocumentType {
    MAIN( FIBLongHandler.CCPTEXT ),

    FOOTNOTE( FIBLongHandler.CCPFTN ),

    HEADER( FIBLongHandler.CCPHDD ),

    MACRO( FIBLongHandler.CCPMCR ),

    ANNOTATION( FIBLongHandler.CCPATN ),

    ENDNOTE( FIBLongHandler.CCPEDN ),

    TEXTBOX( FIBLongHandler.CCPTXBX ),

    HEADER_TEXTBOX( FIBLongHandler.CCPHDRTXBX );

    /**
     * Array of {@link SubdocumentType}s ordered by document position and FIB
     * field order
     */
    public static final SubdocumentType[] ORDERED = new SubdocumentType[] {
            MAIN, FOOTNOTE, HEADER, MACRO, ANNOTATION, ENDNOTE, TEXTBOX,
            HEADER_TEXTBOX };

    private final int fibLongFieldIndex;

    private SubdocumentType( int fibLongFieldIndex )
    {
        this.fibLongFieldIndex = fibLongFieldIndex;
    }

    public int getFibLongFieldIndex()
    {
        return fibLongFieldIndex;
    }

}
