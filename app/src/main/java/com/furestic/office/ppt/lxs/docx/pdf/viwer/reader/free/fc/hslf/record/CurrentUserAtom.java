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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hslf.record;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.fs.filesystem.CFBFileSystem;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hslf.exceptions.CorruptPowerPointFileException;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hslf.exceptions.EncryptedPowerPointFileException;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hslf.exceptions.OldPowerPointFormatException;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.OldWordFileFormatException;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.poifs.filesystem.*;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.LittleEndian;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.StringUtil;

import java.io.IOException;


/**
 * This is a special kind of Atom, becauase it doesn't live inside the
 *  PowerPoint document. Instead, it lives in a seperate stream in the
 *  document. As such, it has to be treaded specially
 *
 * @author Nick Burch
 */

public class CurrentUserAtom
{
    /** Standard Atom header */
    public static final byte[] atomHeader = new byte[]{0, 0, -10, 15};
    /** The PowerPoint magic number for a non-encrypted file */
    public static final byte[] headerToken = new byte[]{95, -64, -111, -29};
    /** The PowerPoint magic number for an encrytpted file */
    public static final byte[] encHeaderToken = new byte[]{-33, -60, -47, -13};
    /** The Powerpoint 97 version, major and minor numbers */
    public static final byte[] ppt97FileVer = new byte[]{8, 00, -13, 03, 03, 00};

    /** The version, major and minor numbers */
    private int docFinalVersion;
    private byte docMajorNo;
    private byte docMinorNo;

    /** The Offset into the file for the current edit */
    private long currentEditOffset;
    /** The Username of the last person to edit the file */
    private String lastEditUser;
    /** The document release version. Almost always 8 */
    private long releaseVersion;

    /** Only correct after reading in or writing out */
    private byte[] _contents;

    /* ********************* getter/setter follows *********************** */

    public int getDocFinalVersion()
    {
        return docFinalVersion;
    }

    public byte getDocMajorNo()
    {
        return docMajorNo;
    }

    public byte getDocMinorNo()
    {
        return docMinorNo;
    }

    public long getReleaseVersion()
    {
        return releaseVersion;
    }

    public void setReleaseVersion(long rv)
    {
        releaseVersion = rv;
    }

    /** Points to the UserEditAtom */
    public long getCurrentEditOffset()
    {
        return currentEditOffset;
    }

    public void setCurrentEditOffset(long id)
    {
        currentEditOffset = id;
    }

    public String getLastEditUsername()
    {
        return lastEditUser;
    }

    public void setLastEditUsername(String u)
    {
        lastEditUser = u;
    }

    /* ********************* real code follows *************************** */

    /**
     * Create a new Current User Atom
     */
    public CurrentUserAtom()
    {
        _contents = new byte[0];

        // Initialise to empty
        docFinalVersion = 0x03f4;
        docMajorNo = 3;
        docMinorNo = 0;
        releaseVersion = 8;
        currentEditOffset = 0;
        lastEditUser = "Apache POI";
    }

    /** 
     * Find the Current User in the filesystem, and create from that
     */
    public CurrentUserAtom(POIFSFileSystem fs) throws IOException
    {
        //this(fs.getRoot());
    }

    /** 
     * Find the Current User in the filesystem, and create from that
     */
    public CurrentUserAtom(CFBFileSystem cfbFS) throws IOException
    {
        // Decide how big it is
        //DocumentEntry docProps = (DocumentEntry)dir.getEntry("Current User");
        _contents = cfbFS.getPropertyRawData("Current User"); 
        // If it's clearly junk, bail out
        if (_contents == null || _contents.length > 131072)
        {
            throw new CorruptPowerPointFileException(
                "The Current User stream is implausably long. It's normally 28-200 bytes long, but was "
                    + _contents.length + " bytes");
        }

        // Grab the contents
        /*_contents = new byte[docProps.getSize()];
        InputStream in = dir.createDocumentInputStream("Current User");
        in.read(_contents);*/

        // See how long it is. If it's under 28 bytes long, we can't
        //  read it
        if (_contents.length < 28)
        {
            if (_contents.length >= 4)
            {
                // PPT95 has 4 byte size, then data
                int size = LittleEndian.getInt(_contents);
                System.err.println(size);
                if (size + 4 == _contents.length)
                {
                    throw new OldPowerPointFormatException(
                        "Based on the Current User stream, you seem to have supplied a PowerPoint95 file, which isn't supported");
                }
            }
            throw new CorruptPowerPointFileException(
                "The Current User stream must be at least 28 bytes long, but was only "
                    + _contents.length);
        }

        // Set everything up
        init();
    }

    /** 
     * Create things from the bytes
     */
    public CurrentUserAtom(byte[] b)
    {
        _contents = b;
        init();
    }

    /**
     * Actually do the creation from a block of bytes
     */
    private void init()
    {
        // First up is the size, in 4 bytes, which is fixed
        // Then is the header - check for encrypted
        if (_contents[12] == encHeaderToken[0] && _contents[13] == encHeaderToken[1]
            && _contents[14] == encHeaderToken[2] && _contents[15] == encHeaderToken[3])
        {
            throw new EncryptedPowerPointFileException(
                "Cannot process encrypted office files!");
        }

        // Grab the edit offset
        currentEditOffset = LittleEndian.getUInt(_contents, 16);

        // Grab the versions
        docFinalVersion = LittleEndian.getUShort(_contents, 22);
        docMajorNo = _contents[24];
        docMinorNo = _contents[25];

        // Get the username length
        long usernameLen = LittleEndian.getUShort(_contents, 20);
        if (usernameLen > 512)
        {
            // Handle the case of it being garbage
            System.err.println("Warning - invalid username length " + usernameLen
                + " found, treating as if there was no username set");
            usernameLen = 0;
        }

        // Now we know the length of the username, 
        //  use this to grab the revision
        if (_contents.length >= 28 + (int)usernameLen + 4)
        {
            releaseVersion = LittleEndian.getUInt(_contents, 28 + (int)usernameLen);
            if (releaseVersion == 0)
            {
                throw new OldWordFileFormatException(
                    "The document is too old - Word 95 or older. Try HWPFOldDocument instead?");
            }
        }
        else
        {
            // No revision given, as not enough data. Odd
            releaseVersion = 0;
        }

        // Grab the unicode username, if stored
        int start = 28 + (int)usernameLen + 4;
        int len = 2 * (int)usernameLen;

        if (_contents.length >= start + len)
        {
            byte[] textBytes = new byte[len];
            System.arraycopy(_contents, start, textBytes, 0, len);
            lastEditUser = StringUtil.getFromUnicodeLE(textBytes);
        }
        else
        {
            // Fake from the 8 bit version
            byte[] textBytes = new byte[(int)usernameLen];
            System.arraycopy(_contents, 28, textBytes, 0, (int)usernameLen);
            lastEditUser = StringUtil.getFromCompressedUnicode(textBytes, 0, (int)usernameLen);
        }
    }

    /**
     * 
     */
    public void dispose()
    {
        _contents = null;
        lastEditUser = null;
    }
    
    /**
     * Writes ourselves back out
     */
    /*public void writeOut(OutputStream out) throws IOException
    {
        // Decide on the size
        //  8 = atom header
        //  20 = up to name
        //  4 = revision
        //  3 * len = ascii + unicode
        int size = 8 + 20 + 4 + (3 * lastEditUser.length());
        _contents = new byte[size];

        // First we have a 8 byte atom header
        System.arraycopy(atomHeader, 0, _contents, 0, 4);
        // Size is 20+user len + revision len(4)
        int atomSize = 20 + 4 + lastEditUser.length();
        LittleEndian.putInt(_contents, 4, atomSize);

        // Now we have the size of the details, which is 20
        LittleEndian.putInt(_contents, 8, 20);

        // Now the ppt un-encrypted header token (4 bytes)
        System.arraycopy(headerToken, 0, _contents, 12, 4);

        // Now the current edit offset
        LittleEndian.putInt(_contents, 16, (int)currentEditOffset);

        // The username gets stored twice, once as US 
        //  ascii, and again as unicode laster on
        byte[] asciiUN = new byte[lastEditUser.length()];
        StringUtil.putCompressedUnicode(lastEditUser, asciiUN, 0);

        // Now we're able to do the length of the last edited user
        LittleEndian.putShort(_contents, 20, (short)asciiUN.length);

        // Now the file versions, 2+1+1
        LittleEndian.putShort(_contents, 22, (short)docFinalVersion);
        _contents[24] = docMajorNo;
        _contents[25] = docMinorNo;

        // 2 bytes blank
        _contents[26] = 0;
        _contents[27] = 0;

        // At this point we have the username as us ascii
        System.arraycopy(asciiUN, 0, _contents, 28, asciiUN.length);

        // 4 byte release version
        LittleEndian.putInt(_contents, 28 + asciiUN.length, (int)releaseVersion);

        // username in unicode
        byte[] ucUN = new byte[lastEditUser.length() * 2];
        StringUtil.putUnicodeLE(lastEditUser, ucUN, 0);
        System.arraycopy(ucUN, 0, _contents, 28 + asciiUN.length + 4, ucUN.length);

        // Write out
        out.write(_contents);
    }*/

    /**
     * Writes ourselves back out to a filesystem
     */
    /*public void writeToFS(POIFSFileSystem fs) throws IOException
    {
        // Grab contents
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeOut(baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        // Write out
        fs.createDocument(bais, "Current User");
    }*/
}
