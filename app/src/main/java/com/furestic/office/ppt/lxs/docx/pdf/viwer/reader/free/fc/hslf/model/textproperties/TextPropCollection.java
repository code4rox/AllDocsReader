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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hslf.model.textproperties;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hslf.record.StyleTextPropAtom;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.LittleEndian;

import java.util.LinkedList;


/**
 * For a given run of characters, holds the properties (which could
 *  be paragraph properties or character properties).
 * Used to hold the number of characters affected, the list of active
 *  properties, and the random reserved field if required.
 */
public class TextPropCollection
{
    public int getSpecialMask()
    {
        return maskSpecial;
    }

    /** Fetch the number of characters this styling applies to */
    public int getCharactersCovered()
    {
        return charactersCovered;
    }

    /** Fetch the TextProps that define this styling */
    public LinkedList<TextProp> getTextPropList()
    {
        return textPropList;
    }

    /** Fetch the TextProp with this name, or null if it isn't present */
    public TextProp findByName(String textPropName)
    {
        for (int i = 0; i < textPropList.size(); i++)
        {
            TextProp prop = textPropList.get(i);
            if (prop.getName().equals(textPropName))
            {
                return prop;
            }
        }
        return null;
    }

    /** Add the TextProp with this name to the list */
    public TextProp addWithName(String name)
    {
        // Find the base TextProp to base on
        TextProp base = null;
        for (int i = 0; i < StyleTextPropAtom.characterTextPropTypes.length; i++)
        {
            if (StyleTextPropAtom.characterTextPropTypes[i].getName().equals(name))
            {
                base = StyleTextPropAtom.characterTextPropTypes[i];
            }
        }
        for (int i = 0; i < StyleTextPropAtom.paragraphTextPropTypes.length; i++)
        {
            if (StyleTextPropAtom.paragraphTextPropTypes[i].getName().equals(name))
            {
                base = StyleTextPropAtom.paragraphTextPropTypes[i];
            }
        }
        if (base == null)
        {
            throw new IllegalArgumentException("No TextProp with name " + name
                + " is defined to add from");
        }

        // Add a copy of this property, in the right place to the list
        TextProp textProp = (TextProp)base.clone();
        int pos = 0;
        for (int i = 0; i < textPropList.size(); i++)
        {
            TextProp curProp = textPropList.get(i);
            if (textProp.getMask() > curProp.getMask())
            {
                pos++;
            }
        }
        textPropList.add(pos, textProp);
        return textProp;
    }

    /**
     * For an existing set of text properties, build the list of 
     *  properties coded for in a given run of properties.
     * @return the number of bytes that were used encoding the properties list
     */
    public int buildTextPropList(int containsField, TextProp[] potentialProperties, byte[] data,
        int dataOffset)
    {
        int bytesPassed = 0;

        // For each possible entry, see if we match the mask
        // If we do, decode that, save it, and shuffle on
        for (int i = 0; i < potentialProperties.length; i++)
        {
            // Check there's still data left to read

            // Check if this property is found in the mask
            if ((containsField & potentialProperties[i].getMask()) != 0)
            {
                if (dataOffset + bytesPassed >= data.length)
                {
                    // Out of data, can't be any more properties to go
                    // remember the mask and return
                    maskSpecial |= potentialProperties[i].getMask();
                    return bytesPassed;
                }

                // Bingo, data contains this property
                TextProp prop = (TextProp)potentialProperties[i].clone();
                int val = 0;
                if (prop.getSize() == 2)
                {
                    val = LittleEndian.getShort(data, dataOffset + bytesPassed);
                }
                else if (prop.getSize() == 4)
                {
                    val = LittleEndian.getInt(data, dataOffset + bytesPassed);
                }
                else if (prop.getSize() == 0)
                {
                    //remember "special" bits.
                    maskSpecial |= potentialProperties[i].getMask();
                    continue;
                }
                if (CharFlagsTextProp.NAME.equals(prop.getName()) && val < 0)
                {
                    val = 0;
                }
                prop.setValue(val);
                bytesPassed += prop.getSize();
                if ("tabStops".equals(prop.getName()))
                {
                    bytesPassed += val * 4;
                }
                textPropList.add(prop);
            }
        }

        // Return how many bytes were used
        return bytesPassed;
    }

    /**
     * Create a new collection of text properties (be they paragraph
     *  or character) which will be groked via a subsequent call to
     *  buildTextPropList().
     */
    public TextPropCollection(int charactersCovered, short reservedField)
    {
        this.charactersCovered = charactersCovered;
        this.reservedField = reservedField;
        textPropList = new LinkedList<TextProp>();
    }

    /**
     * Create a new collection of text properties (be they paragraph
     *  or character) for a run of text without any
     */
    public TextPropCollection(int textSize)
    {
        charactersCovered = textSize;
        reservedField = -1;
        textPropList = new LinkedList<TextProp>();
    }

    /**
     * Update the size of the text that this set of properties
     *  applies to 
     */
    public void updateTextSize(int textSize)
    {
        charactersCovered = textSize;
    }

    public short getReservedField()
    {
        return reservedField;
    }

    public void setReservedField(short val)
    {
        reservedField = val;
    }
    
    /**
     * 
     */
    public void dispose()
    {
        if (textPropList != null)
        {   
            for (int i = 0; i < textPropList.size(); i++)
            {
                textPropList.get(i).dispose();
            }
            textPropList.clear();
            textPropList = null;
        }
    }
    
    private int charactersCovered;
    private short reservedField;
    private LinkedList<TextProp> textPropList;
    private int maskSpecial = 0;
}
