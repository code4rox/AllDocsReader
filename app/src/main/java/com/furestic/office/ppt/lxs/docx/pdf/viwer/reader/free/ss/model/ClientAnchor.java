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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.constant.MainConstant;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.baseModel.Row;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.baseModel.Sheet;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.interfacePart.IClientAnchor;

/**
 * A client anchor is attached to an excel worksheet.  It anchors against a
 * top-left and buttom-right cell.
 *
 * @author Glen Stampoultzis (glens at apache.org)
 */
public final class ClientAnchor extends Anchor implements IClientAnchor
{
    /**
     * Creates a new client anchor and defaults all the anchor positions to 0.
     */
    public ClientAnchor()
    {
    }

    /**
     * Creates a new client anchor and sets the top-left and bottom-right
     * coordinates of the anchor.
     *
     * @param dx1   the x coordinate within the first cell.
     * @param dy1   the y coordinate within the first cell.
     * @param dx2   the x coordinate within the second cell.
     * @param dy2   the y coordinate within the second cell.
     * @param col1  the column (0 based) of the first cell.
     * @param row1  the row (0 based) of the first cell.
     * @param col2  the column (0 based) of the second cell.
     * @param row2  the row (0 based) of the second cell.
     */
    public ClientAnchor(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2,
        int row2)
    {
        super(dx1, dy1, dx2, dy2);

        checkRange(dx1, 0, 1023, "dx1");
        checkRange(dx2, 0, 1023, "dx2");
        checkRange(dy1, 0, 255, "dy1");
        checkRange(dy2, 0, 255, "dy2");
        checkRange(col1, 0, 255, "col1");
        checkRange(col2, 0, 255, "col2");
        checkRange(row1, 0, 255 * 256, "row1");
        checkRange(row2, 0, 255 * 256, "row2");

        this.col1 = col1;
        this.row1 = row1;
        this.col2 = col2;
        this.row2 = row2;
    }

    /**
     * Calculates the height of a client anchor in points.
     *
     * @param sheet     the sheet the anchor will be attached to
     * @return          the shape height.
     */
    public float getAnchorHeightInPoints(Sheet sheet)
    {
        int y1 = getDy1();
        int y2 = getDy2();
        int row1 = Math.min(getRow1(), getRow2());
        int row2 = Math.max(getRow1(), getRow2());

        float points = 0;
        if (row1 == row2)
        {
            points = ((y2 - y1) / 256.0f) * getRowHeightInPoints(sheet, row2);
        }
        else
        {
            points += ((256.0f - y1) / 256.0f) * getRowHeightInPoints(sheet, row1);
            for (int i = row1 + 1; i < row2; i++)
            {
                points += getRowHeightInPoints(sheet, i);
            }
            points += (y2 / 256.0f) * getRowHeightInPoints(sheet, row2);
        }
        return points;
    }

    /**
     * 
     * @param sheet
     * @param rowNum
     * @return
     */
    private float getRowHeightInPoints(Sheet sheet, int rowNum)
    {
        Row row = sheet.getRow(rowNum);
        if (row == null)
        {
            return 18 * MainConstant.PIXEL_TO_POINT;
        }
        return row.getRowPixelHeight() * MainConstant.PIXEL_TO_POINT;
    }

    /**
     * 
     *(non-Javadoc)
     * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.interfacePart.IClientAnchor#getCol1()
     *
     */
    public short getCol1()
    {
        return col1;
    }

    /**
     * 
     * @param col1
     */
    public void setCol1(short col1)
    {
        checkRange(col1, 0, 255, "col1");
        this.col1 = col1;
    }

    /**
     * 
     *(non-Javadoc)
     * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.interfacePart.IClientAnchor#setCol1(int)
     *
     */
    public void setCol1(int col1)
    {
        setCol1((short)col1);
    }

    /**
     * 
     *(non-Javadoc)
     * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.interfacePart.IClientAnchor#getCol2()
     *
     */
    public short getCol2()
    {
        return col2;
    }

    /**
     * 
     * @param col2
     */
    public void setCol2(short col2)
    {
        checkRange(col2, 0, 255, "col2");
        this.col2 = col2;
    }

    /**
     * 
     *(non-Javadoc)
     * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.interfacePart.IClientAnchor#setCol2(int)
     *
     */
    public void setCol2(int col2)
    {
        setCol2((short)col2);
    }

    /**
     * 
     *(non-Javadoc)
     * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.interfacePart.IClientAnchor#getRow1()
     *
     */
    public int getRow1()
    {
        return row1;
    }

    /**
     * 
     *(non-Javadoc)
     * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.interfacePart.IClientAnchor#setRow1(int)
     *
     */
    public void setRow1(int row1)
    {
        checkRange(row1, 0, 256 * 256, "row1");
        this.row1 = row1;
    }

    /**
     * 
     *(non-Javadoc)
     * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.interfacePart.IClientAnchor#getRow2()
     *
     */
    public int getRow2()
    {
        return row2;
    }

    /**
     * 
     *(non-Javadoc)
     * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.interfacePart.IClientAnchor#setRow2(int)
     *
     */
    public void setRow2(int row2)
    {
        checkRange(row2, 0, 256 * 256, "row2");
        this.row2 = row2;
    }

    /**
     * Dets the top-left and bottom-right
     * coordinates of the anchor.
     *
     * @param x1   the x coordinate within the first cell.
     * @param y1   the y coordinate within the first cell.
     * @param x2   the x coordinate within the second cell.
     * @param y2   the y coordinate within the second cell.
     * @param col1  the column (0 based) of the first cell.
     * @param row1  the row (0 based) of the first cell.
     * @param col2  the column (0 based) of the second cell.
     * @param row2  the row (0 based) of the second cell.
     */
    public void setAnchor(short col1, int row1, int x1, int y1, short col2, int row2, int x2, int y2)
    {
        checkRange(dx1, 0, 1023, "dx1");
        checkRange(dx2, 0, 1023, "dx2");
        checkRange(dy1, 0, 255, "dy1");
        checkRange(dy2, 0, 255, "dy2");
        checkRange(col1, 0, 255, "col1");
        checkRange(col2, 0, 255, "col2");
        checkRange(row1, 0, 255 * 256, "row1");
        checkRange(row2, 0, 255 * 256, "row2");

        this.col1 = col1;
        this.row1 = row1;
        this.dx1 = x1;
        this.dy1 = y1;
        this.col2 = col2;
        this.row2 = row2;
        this.dx2 = x2;
        this.dy2 = y2;
    }

    /**
     * @return  true if the anchor goes from right to left.
     */
    public boolean isHorizontallyFlipped()
    {
        if (col1 == col2)
        {
            return dx1 > dx2;
        }
        return col1 > col2;
    }

    /**
     * @return  true if the anchor goes from bottom to top.
     */
    public boolean isVerticallyFlipped()
    {
        if (row1 == row2)
        {
            return dy1 > dy2;
        }
        return row1 > row2;
    }

    /**
     * Gets the anchor type
     * <p>
     * 0 = Move and size with Cells, 2 = Move but don't size with cells, 3 = Don't move or size with cells.
     */
    public int getAnchorType()
    {
        return anchorType;
    }

    /**
     * Sets the anchor type
     * <p>
     * 0 = Move and size with Cells, 2 = Move but don't size with cells, 3 = Don't move or size with cells.
     */
    public void setAnchorType(int anchorType)
    {
        this.anchorType = anchorType;
    }

    private void checkRange(int value, int minRange, int maxRange, String varName)
    {
        if (value < minRange || value > maxRange)
            throw new IllegalArgumentException(varName + " must be between " + minRange + " and "
                + maxRange);
    }
    
    //
    private short col1;
    //
    private int row1;
    //
    private short col2;
    //
    private int row2;
    //
    private int anchorType;

}
