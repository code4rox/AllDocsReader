/*
 * Copyright 2001-2005 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * This software is open source.
 * See the bottom of this file for the licence.
 */

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.dom4j.tree;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.dom4j.Element;

/**
 * <p>
 * <code>DefaultEntity</code> is the default Entity implementation. It is a
 * doubly linked node which supports the parent relationship and can be modified
 * in place.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.11 $
 */
public class DefaultEntity extends FlyweightEntity
{
    /** The parent of this node */
    private Element parent;

    /**
     * Creates the <code>Entity</code> with the specified name
     * 
     * @param name
     *            is the name of the entity
     */
    public DefaultEntity(String name)
    {
        super(name);
    }

    /**
     * Creates the <code>Entity</code> with the specified name and text.
     * 
     * @param name
     *            is the name of the entity
     * @param text
     *            is the text of the entity
     */
    public DefaultEntity(String name, String text)
    {
        super(name, text);
    }

    /**
     * Creates the <code>Entity</code> with the specified name and text.
     * 
     * @param parent
     *            is the parent element
     * @param name
     *            is the name of the entity
     * @param text
     *            is the text of the entity
     */
    public DefaultEntity(Element parent, String name, String text)
    {
        super(name, text);
        this.parent = parent;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Element getParent()
    {
        return parent;
    }

    public void setParent(Element parent)
    {
        this.parent = parent;
    }

    public boolean supportsParent()
    {
        return true;
    }

    public boolean isReadOnly()
    {
        return false;
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
