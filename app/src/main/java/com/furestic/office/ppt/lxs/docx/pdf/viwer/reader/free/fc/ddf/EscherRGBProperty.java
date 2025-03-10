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
 * A color property.
 *
 * @author Glen Stampoultzis (glens at apache.org)
 */
public class EscherRGBProperty
        extends EscherSimpleProperty
{

    public EscherRGBProperty( short propertyNumber, int rgbColor )
    {
        super( propertyNumber, rgbColor );
    }

    public int getRgbColor()
    {
        return propertyValue;
    }

    public byte getRed()
    {
        return (byte) ( propertyValue & 0xFF );
    }

    public byte getGreen()
    {
        return (byte) ( (propertyValue >> 8) & 0xFF );
    }

    public byte getBlue()
    {
        return (byte) ( (propertyValue >> 16) & 0xFF );
    }

}
