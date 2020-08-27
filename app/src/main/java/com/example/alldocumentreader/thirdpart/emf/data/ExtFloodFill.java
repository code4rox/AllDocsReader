// Copyright 2002, FreeHEP.

package com.example.alldocumentreader.thirdpart.emf.data;

import android.graphics.Point;

import com.example.alldocumentreader.java.awt.Color;
import com.example.alldocumentreader.thirdpart.emf.EMFConstants;
import com.example.alldocumentreader.thirdpart.emf.EMFInputStream;
import com.example.alldocumentreader.thirdpart.emf.EMFTag;

import java.io.IOException;

/**
 * ExtFloodFill TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: ExtFloodFill.java 10367 2007-01-22 19:26:48Z duns $
 */
public class ExtFloodFill extends EMFTag implements EMFConstants
{

    private Point start;

    private Color color;

    private int mode;

    public ExtFloodFill()
    {
        super(53, 1);
    }

    public ExtFloodFill(Point start, Color color, int mode)
    {
        this();
        this.start = start;
        this.color = color;
        this.mode = mode;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new ExtFloodFill(emf.readPOINTL(), emf.readCOLORREF(), emf.readDWORD());
    }

    public String toString()
    {
        return super.toString() + "\n  start: " + start + "\n  color: " + color + "\n  mode: "
            + mode;
    }
}
