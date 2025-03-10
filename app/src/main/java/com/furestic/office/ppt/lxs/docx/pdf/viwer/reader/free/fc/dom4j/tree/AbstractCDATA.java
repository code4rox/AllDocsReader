/*
 * Copyright 2001-2005 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * This software is open source.
 * See the bottom of this file for the licence.
 */

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.dom4j.tree;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.dom4j.CDATA;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.dom4j.Visitor;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;


/**
 * <p>
 * <code>AbstractCDATA</code> is an abstract base class for tree implementors
 * to use for implementation inheritence.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.12 $
 */
public abstract class AbstractCDATA extends AbstractCharacterData implements CDATA
{
    public AbstractCDATA()
    {
    }

    public short getNodeType()
    {
        return CDATA_SECTION_NODE;
    }

    public String toString()
    {
        return super.toString() + " [CDATA: \"" + getText() + "\"]";
    }

    public String asXML()
    {
        StringWriter writer = new StringWriter();

        try
        {
            write(writer);
        }
        catch(IOException e)
        {
            // will not happen since we are using a StringWriter!
        }

        return writer.toString();
    }

    public void write(Writer writer) throws IOException
    {
        writer.write("<![CDATA[");

        if (getText() != null)
        {
            writer.write(getText());
        }

        writer.write("]]>");
    }

    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}

/*
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided that the
 * following conditions are met:
 * 
 * 1. Redistributions of source code must retain copyright statements and
 * notices. Redistributions must also contain a copy of this document.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The name "DOM4J" must not be used to endorse or promote products derived
 * from this Software without prior written permission of MetaStuff, Ltd. For
 * written permission, please contact dom4j-info@metastuff.com.
 * 
 * 4. Products derived from this Software may not be called "DOM4J" nor may
 * "DOM4J" appear in their names without prior written permission of MetaStuff,
 * Ltd. DOM4J is a registered trademark of MetaStuff, Ltd.
 * 
 * 5. Due credit should be given to the DOM4J Project - http://www.dom4j.org
 * 
 * THIS SOFTWARE IS PROVIDED BY METASTUFF, LTD. AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL METASTUFF, LTD. OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * Copyright 2001-2005 (C) MetaStuff, Ltd. All Rights Reserved.
 */
