// Copyright 2002, FreeHEP.

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.data;

import android.graphics.Point;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.java.awt.Rectangle;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFInputStream;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFTag;

import java.io.IOException;

/**
 * Polygon16 TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: Polygon16.java 10367 2007-01-22 19:26:48Z duns $
 */
public class Polygon16 extends EMFPolygon
{

    public Polygon16()
    {
        super(86, 1, null, 0, null);
    }

    public Polygon16(Rectangle bounds, int numberOfPoints, Point[] points)
    {
        super(86, 1, bounds, numberOfPoints, points);
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        Rectangle r = emf.readRECTL();
        int n = emf.readDWORD();
        return new Polygon16(r, n, emf.readPOINTS(n));
    }

}
