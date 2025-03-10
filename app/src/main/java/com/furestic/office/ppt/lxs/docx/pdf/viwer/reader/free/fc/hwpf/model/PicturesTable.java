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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.model;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.DefaultEscherRecordFactory;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.EscherBSERecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.EscherBlipRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.EscherRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.EscherRecordFactory;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.HWPFDocument;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.usermodel.CharacterRun;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.usermodel.InlineWordArt;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.usermodel.Picture;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.usermodel.Range;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.Internal;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.util.LittleEndian;

import java.util.ArrayList;
import java.util.List;


/**
 * Holds information about all pictures embedded in Word Document either via "Insert -> Picture -> From File" or via
 * clipboard. Responsible for images extraction and determining whether some document's piece contains embedded image.
 * Analyzes raw data bytestream 'Data' (where Word stores all embedded objects) provided by HWPFDocument.
 *
 * Word stores images as is within so called "Data stream" - the stream within a Word docfile containing various data
 * that hang off of characters in the main stream. For example, binary data describing in-line pictures and/or
 * formfields an also embedded objects-native data. Word picture structures are concatenated one after the other in
 * the data stream if the document contains pictures.
 * Data stream is easily reachable via HWPFDocument._dataStream property.
 * A picture is represented in the document text stream as a special character, an Unicode \u0001 whose
 * CharacterRun.isSpecial() returns true. The file location of the picture in the Word binary file is accessed
 * via CharacterRun.getPicOffset(). The CharacterRun.getPicOffset() is a byte offset into the data stream.
 * Beginning at the position recorded in picOffset, a header data structure, will be stored.
 *
 * @author Dmitry Romanov
 */
@Internal
public final class PicturesTable
{
  static final int TYPE_IMAGE = 0x08;
  static final int TYPE_IMAGE_WORD2000 = 0x00;
  static final int TYPE_IMAGE_PASTED_FROM_CLIPBOARD = 0xA;
  static final int TYPE_IMAGE_PASTED_FROM_CLIPBOARD_WORD2000 = 0x2;
  static final int TYPE_HORIZONTAL_LINE = 0xE;
  static final int BLOCK_TYPE_OFFSET = 0xE;
  static final int MM_MODE_TYPE_OFFSET = 0x6;

  private HWPFDocument _document;
  private byte[] _dataStream;
  private byte[] _mainStream;
  @Deprecated
  private FSPATable _fspa;
  @Deprecated
  private EscherRecordHolder _dgg;

  /** @link dependency
   * @stereotype instantiate*/
  /*# Picture lnkPicture; */

  /**
   *
   * @param _document
   * @param _dataStream
   */
  @Deprecated
  public PicturesTable(HWPFDocument _document, byte[] _dataStream, byte[] _mainStream, FSPATable fspa, EscherRecordHolder dgg)
  {
    this._document = _document;
    this._dataStream = _dataStream;
    this._mainStream = _mainStream;
    this._fspa = fspa;
    this._dgg = dgg;
  }

    public PicturesTable( HWPFDocument _document, byte[] _dataStream,
            byte[] _mainStream )
    {
        this._document = _document;
        this._dataStream = _dataStream;
        this._mainStream = _mainStream;
    }

    /**
     * determines whether specified CharacterRun contains reference to a picture
     * @param run
     */
    public boolean hasInlineWordArt(CharacterRun run) {
      if (run==null) {
          return false;
      }

      if (run.isSpecialCharacter() && !run.isObj() && !run.isOle2() && !run.isData()) {
         // Image should be in it's own run, or in a run with the end-of-special marker
         if("\u0001".equals(run.text()) || "\u0001\u0015".equals(run.text())) {
            return isBlockContainsInlineWordArt(run.getPicOffset());
         }
      }
      return false;
    }
    
  /**
   * determines whether specified CharacterRun contains reference to a picture
   * @param run
   */
  public boolean hasPicture(CharacterRun run) {
    if (run==null) {
        return false;
    }

    if (run.isSpecialCharacter() && !run.isObj() && !run.isOle2() && !run.isData()) {
       // Image should be in it's own run, or in a run with the end-of-special marker
       if("\u0001".equals(run.text()) || "\u0001\u0015".equals(run.text())) {
          return isBlockContainsImage(run.getPicOffset());
       }
    }
    return false;
  }

  public boolean hasEscherPicture(CharacterRun run) {
    if (run.isSpecialCharacter() && !run.isObj() && !run.isOle2() && !run.isData() && run.text().startsWith("\u0008")) {
      return true;
    }
    return false;
  }

  /**
   * determines whether specified CharacterRun contains reference to a picture
   * @param run
  */
  public boolean hasHorizontalLine(CharacterRun run) {
    if (run.isSpecialCharacter() && "\u0001".equals(run.text())) {
      return isBlockContainsHorizontalLine(run.getPicOffset());
    }
    return false;
  }

  private boolean isPictureRecognized(short blockType, short mappingModeOfMETAFILEPICT)
  {
    return (blockType == TYPE_IMAGE || blockType == TYPE_IMAGE_PASTED_FROM_CLIPBOARD || (blockType==TYPE_IMAGE_WORD2000 && mappingModeOfMETAFILEPICT==0x64) || (blockType==TYPE_IMAGE_PASTED_FROM_CLIPBOARD_WORD2000 && mappingModeOfMETAFILEPICT==0x64));
  }

  private boolean isInlineWordArtRecognized(short blockType, short mappingModeOfMETAFILEPICT) 
  {
	 return (blockType==6 && mappingModeOfMETAFILEPICT==0x64);
  }
  
  private static short getBlockType(byte[] dataStream, int pictOffset) {
    return LittleEndian.getShort(dataStream, pictOffset + BLOCK_TYPE_OFFSET);
  }

  private static short getMmMode(byte[] dataStream, int pictOffset) {
    return LittleEndian.getShort(dataStream, pictOffset + MM_MODE_TYPE_OFFSET);
  }

  /**
   * Returns picture object tied to specified CharacterRun
   * @param run
   * @param fillBytes if true, Picture will be returned with filled byte array that represent picture's contents. If you don't want
   * to have that byte array in memory but only write picture's contents to stream, pass false and then use Picture.writeImageContent
   * @see Picture#writeImageContent(java.io.OutputStream)
   * @return a Picture object if picture exists for specified CharacterRun, null otherwise. PicturesTable.hasPicture is used to determine this.
   * @see #hasPicture(arc.fc.hwpf.usermodel.poi.hwpf.usermodel.CharacterRun)
   */
  public Picture extractPicture(String tempPath, CharacterRun run, boolean fillBytes) {
    if (hasPicture(run)) 
    {
    	try
    	{
    		return new Picture(tempPath, run.getPicOffset(), _dataStream, fillBytes);
    	}
    	catch(Exception e)
    	{
    		return null;
    	}
    }
    return null;
  }

  /**
   * inline wordart
   * @param run
   * @return
   */
  public InlineWordArt extracInlineWordArt(CharacterRun run)
  {
	  if(hasInlineWordArt(run))
	  {
		  return new InlineWordArt(_dataStream, run.getPicOffset());
	  }
	  return null;
  }
  /**
     * Performs a recursive search for pictures in the given list of escher records.
     *
     * @param escherRecords the escher records.
     * @param pictures the list to populate with the pictures.
     */
    private void searchForPictures(List<EscherRecord> escherRecords, List<Picture> pictures)
    {
       for(EscherRecord escherRecord : escherRecords) {
          if (escherRecord instanceof EscherBSERecord) {
              EscherBSERecord bse = (EscherBSERecord) escherRecord;
              EscherBlipRecord blip = bse.getBlipRecord();
              if (blip != null)
              {
                  pictures.add(new Picture(blip.getPicturedata()));
              }
              else if (bse.getOffset() > 0)
              {
                  // Blip stored in delay stream, which in a word doc, is the main stream
                  EscherRecordFactory recordFactory = new DefaultEscherRecordFactory();
                  EscherRecord record = recordFactory.createRecord(_mainStream, bse.getOffset());

                  if (record instanceof EscherBlipRecord) {
                      record.fillFields(_mainStream, bse.getOffset(), recordFactory);
                      blip = (EscherBlipRecord) record;
                      pictures.add(new Picture(blip.getPicturedata()));
                  }
              }
          }

          // Recursive call.
          searchForPictures(escherRecord.getChildRecords(), pictures);
       }
    }

  /**
   * Not all documents have all the images concatenated in the data stream
   * although MS claims so. The best approach is to scan all character runs.
   *
   * @return a list of Picture objects found in current document
   */
  public List<Picture> getAllPictures(String tempPath) {
    ArrayList<Picture> pictures = new ArrayList<Picture>();

    Range range = _document.getOverallRange();
    for (int i = 0; i < range.numCharacterRuns(); i++) {
    	CharacterRun run = range.getCharacterRun(i);

        if (run==null) {
            continue;
        }

    	Picture picture = extractPicture(tempPath, run, false);
    	if (picture != null) {
    		pictures.add(picture);
    	}
	}

    searchForPictures(_dgg.getEscherRecords(), pictures);

    return pictures;
  }

  private boolean isBlockContainsImage(int i)
  {
    return isPictureRecognized(getBlockType(_dataStream, i), getMmMode(_dataStream, i));
  }

  private boolean isBlockContainsHorizontalLine(int i)
  {
    return getBlockType(_dataStream, i)==TYPE_HORIZONTAL_LINE && getMmMode(_dataStream, i)==0x64;
  }

  private boolean isBlockContainsInlineWordArt(int i)
  {
    return isInlineWordArtRecognized(getBlockType(_dataStream, i), getMmMode(_dataStream, i));
  }
}
