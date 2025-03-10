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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.usermodel;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.poifs.filesystem.DirectoryEntry;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.poifs.filesystem.Entry;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.Internal;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.POIUtils;

import java.io.FileNotFoundException;
import java.io.IOException;


@ Internal
public class ObjectPoolImpl implements ObjectsPool
{
    private DirectoryEntry _objectPool;

    public ObjectPoolImpl(DirectoryEntry _objectPool)
    {
        super();
        this._objectPool = _objectPool;
    }

    public Entry getObjectById(String objId)
    {
        if (_objectPool == null)
            return null;

        try
        {
            return _objectPool.getEntry(objId);
        }
        catch(FileNotFoundException exc)
        {
            return null;
        }
    }

    @ Internal
    public void writeTo(DirectoryEntry directoryEntry) throws IOException
    {
        if (_objectPool != null)
            POIUtils.copyNodeRecursively(_objectPool, directoryEntry);
    }
}
