// Copyright 2001, FreeHEP.

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.data;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.java.awt.Rectangle;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFConstants;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFInputStream;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.EMFTag;

import java.io.IOException;

/**
 * GradientFill TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: GradientFill.java 10367 2007-01-22 19:26:48Z duns $
 */
public class GradientFill extends EMFTag implements EMFConstants
{

    private Rectangle bounds;

    private int mode;

    private TriVertex[] vertices;

    private Gradient[] gradients;

    public GradientFill()
    {
        super(118, 1);
    }

    public GradientFill(Rectangle bounds, int mode, TriVertex[] vertices, Gradient[] gradients)
    {
        this();
        this.bounds = bounds;
        this.mode = mode;
        this.vertices = vertices;
        this.gradients = gradients;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        Rectangle bounds = emf.readRECTL();
        TriVertex[] vertices = new TriVertex[emf.readDWORD()];
        Gradient[] gradients = new Gradient[emf.readDWORD()];
        int mode = emf.readULONG();

        for (int i = 0; i < vertices.length; i++)
        {
            vertices[i] = new TriVertex(emf);
        }
        for (int i = 0; i < gradients.length; i++)
        {
            if (mode == GRADIENT_FILL_TRIANGLE)
            {
                gradients[i] = new GradientTriangle(emf);
            }
            else
            {
                gradients[i] = new GradientRectangle(emf);
            }
        }
        return new GradientFill(bounds, mode, vertices, gradients);
    }

    public String toString()
    {
        StringBuffer s = new StringBuffer();
        s.append(super.toString());
        s.append("\n");
        s.append("  bounds: ");
        s.append(bounds);
        s.append("\n");
        s.append("  mode: ");
        s.append(mode);
        s.append("\n");
        for (int i = 0; i < vertices.length; i++)
        {
            s.append("  vertex[");
            s.append(i);
            s.append("]: ");
            s.append(vertices[i]);
            s.append("\n");
        }
        for (int i = 0; i < gradients.length; i++)
        {
            s.append("  gradient[");
            s.append(i);
            s.append("]: ");
            s.append(gradients[i]);
            s.append("\n");
        }
        return s.toString();
    }
}
