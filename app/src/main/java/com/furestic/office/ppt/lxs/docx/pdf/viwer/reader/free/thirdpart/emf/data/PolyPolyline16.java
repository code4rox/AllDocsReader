// Copyright 2002, FreeHEP.

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.data;

import android.graphics.Point;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.java.awt.Rectangle;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFInputStream;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFTag;

import java.io.IOException;

/**
 * PolyPolyline16 TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: PolyPolyline16.java 10510 2007-01-30 23:58:16Z duns $
 */
public class PolyPolyline16 extends AbstractPolyPolyline
{

    private int numberOfPolys;

    public PolyPolyline16()
    {
        super(90, 1, null, null, null);
    }

    public PolyPolyline16(Rectangle bounds, int numberOfPolys, int[] numberOfPoints,
        Point[][] points)
    {

        super(90, 1, bounds, numberOfPoints, points);
        this.numberOfPolys = numberOfPolys;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        Rectangle bounds = emf.readRECTL();
        int np = emf.readDWORD();
        /* int totalNumberOfPoints = */emf.readDWORD();
        int[] pc = new int[np];
        Point[][] points = new Point[np][];
        for (int i = 0; i < np; i++)
        {
            pc[i] = emf.readDWORD();
            points[i] = new Point[pc[i]];
        }
        for (int i = 0; i < np; i++)
        {
            points[i] = emf.readPOINTS(pc[i]);
        }
        return new PolyPolyline16(bounds, np, pc, points);
    }

}
