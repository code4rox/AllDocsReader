// Copyright 2002, FreeHEP.

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.data;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFInputStream;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFRenderer;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFTag;

import java.io.IOException;

/**
 * SelectObject TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SelectObject.java 10515 2007-02-06 18:42:34Z duns $
 */
public class SelectObject extends EMFTag
{

    private int index;

    public SelectObject()
    {
        super(37, 1);
    }

    public SelectObject(int index)
    {
        this();
        this.index = index;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new SelectObject(emf.readDWORD());
    }

    public String toString()
    {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(index);
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {
        GDIObject gdiObject;

        if (index < 0)
        {
            gdiObject = StockObjects.getStockObject(index);
        }
        else
        {
            gdiObject = renderer.getGDIObject(index);
        }

        if (gdiObject != null)
        {
            // render that object
            gdiObject.render(renderer);
        }
        else
        {
        }
    }
}
