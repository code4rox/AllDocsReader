// Copyright 2001, FreeHEP.

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.data;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFInputStream;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFRenderer;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFTag;

import java.io.IOException;

/**
 * CreateBrushIndirect TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: CreateBrushIndirect.java 10367 2007-01-22 19:26:48Z duns $
 */
public class CreateBrushIndirect extends EMFTag
{

    private int index;

    private LogBrush32 brush;

    public CreateBrushIndirect()
    {
        super(39, 1);
    }

    public CreateBrushIndirect(int index, LogBrush32 brush)
    {
        this();
        this.index = index;
        this.brush = brush;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new CreateBrushIndirect(emf.readDWORD(), new LogBrush32(emf));
    }
    
    public String toString()
    {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(index) + "\n"
            + brush.toString();
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {
        // CreateBrushIndirect
        //
        // The CreateBrushIndirect function creates a logical brush that has the
        // specified style, color, and pattern.
        //
        // HBRUSH CreateBrushIndirect(
        //   CONST LOGBRUSH *lplb   // brush information
        // );
        renderer.storeGDIObject(index, brush);
    }
}
