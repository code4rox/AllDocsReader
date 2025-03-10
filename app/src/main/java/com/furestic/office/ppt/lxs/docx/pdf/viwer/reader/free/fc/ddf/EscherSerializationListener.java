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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf;

/**
 * Interface for listening to escher serialization events.
 *
 * @author Glen Stampoultzis (glens at apache.org)
 */
public interface EscherSerializationListener
{
    /**
     * Fired before a given escher record is serialized.
     *
     * @param offset    The position in the data array at which the record will be serialized.
     * @param recordId  The id of the record about to be serialized.
     */
    void beforeRecordSerialize(int offset, short recordId, EscherRecord record);

    /**
     * Fired after a record has been serialized.
     *
     * @param offset    The position of the end of the serialized record + 1
     * @param recordId  The id of the record about to be serialized
     * @param size      The number of bytes written for this record.  If it is a container
     *                  record then this will include the size of any included records.
     */
    void afterRecordSerialize(int offset, short recordId, int size, EscherRecord record);
}
