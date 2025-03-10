/*
 * 文件名称:          SimpleArrow.java
 *  
 * 编译器:            android2.2
 * 时间:              下午3:07:55
 */
package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.common.autoshape.pathbuilder.arrow;

import android.graphics.Rect;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.common.shape.AutoShape;

/**
 * TODO: LeftArrow, RightArrow, UpArrow, DownArrow, LeftRightArrow, UpDownArrow
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2012-9-17
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:           
 * <p>
 * <p>
 */
public class ArrowPathBuilder
{    
    /**
     * get autoshape path
     * @param shape
     * @param rect
     * @return
     */
    public static Object getArrowPath(AutoShape shape, Rect rect)
    {
        if(shape.isAutoShape07())
        {
            return LaterArrowPathBuilder.getArrowPath(shape, rect);
        }
        else
        {
            return EarlyArrowPathBuilder.getArrowPath(shape, rect);
        }
    }
}
