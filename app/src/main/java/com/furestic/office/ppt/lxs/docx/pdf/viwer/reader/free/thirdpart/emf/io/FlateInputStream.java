// Copyright 2001-2003, FreeHEP.
package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.emf.io;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

/**
 * The FlateInputStream uses the Deflate mechanism to compress data. The exact
 * definition of Deflate encoding can be found in the PostScript Language
 * Reference (3rd ed.) chapter 3.13.3.
 * 
 * @author Mark Donszelmann
 */
public class FlateInputStream extends InflaterInputStream {

	/**
	 * Create a (De)Flate input stream.
	 * 
	 * @param in
	 *            stream to read from
	 */
	public FlateInputStream(InputStream in) {
		super(in);
	}

	/**
	 * Reads an image FIXME NOT IMPLEMENTED
	 * 
	 * @return null
	 * @throws IOException
	 */
	public Bitmap readImage() throws IOException {
		return null;
	}
}
