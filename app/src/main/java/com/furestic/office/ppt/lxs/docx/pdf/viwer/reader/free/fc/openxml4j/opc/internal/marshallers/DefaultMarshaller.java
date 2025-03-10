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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.openxml4j.opc.internal.marshallers;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.openxml4j.exceptions.OpenXML4JException;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.openxml4j.opc.PackagePart;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.openxml4j.opc.internal.PartMarshaller;

import java.io.OutputStream;


/**
 * Default marshaller that specified that the part is responsible to marshall its content.
 *
 * @author Julien Chable
 * @version 1.0
 * @see PartMarshaller
 */
public final class DefaultMarshaller implements PartMarshaller
{

    /**
     * Save part in the output stream by using the save() method of the part.
     *
     * @throws OpenXML4JException
     *             If any error occur.
     */
    public boolean marshall(PackagePart part, OutputStream out) throws OpenXML4JException
    {
        return part.save(out);
    }
}
