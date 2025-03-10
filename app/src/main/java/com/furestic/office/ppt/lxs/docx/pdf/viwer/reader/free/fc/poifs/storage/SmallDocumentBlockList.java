
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
        

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.poifs.storage;

import java.util.List;

/**
 * A list of SmallDocumentBlocks instances, and methods to manage the list
 *
 * @author Marc Johnson (mjohnson at apache dot org)
 */

public class SmallDocumentBlockList
    extends BlockListImpl
{

    /**
     * Constructor SmallDocumentBlockList
     *
     * @param blocks a list of SmallDocumentBlock instances
     */

    public SmallDocumentBlockList(final List blocks)
    {
        setBlocks((com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.poifs.storage.SmallDocumentBlock [] ) blocks
            .toArray(new SmallDocumentBlock[ blocks.size() ]));
    }
}   // end public class SmallDocumentBlockList

