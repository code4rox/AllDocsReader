/*
 * 文件名称:          ArbitraryPolygonShape.java
 *  
 * 编译器:            android2.2
 * 时间:              下午3:46:04
 */
package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.common.shape;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.common.autoshape.ExtendPath;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2012-10-9
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:           
 * <p>
 * <p>
 */
public class ArbitraryPolygonShape extends LineShape
{   
    public ArbitraryPolygonShape()
    {
        paths = new ArrayList<ExtendPath>();
    }
    
    public void appendPath(ExtendPath path)
    {
        this.paths.add(path);
    }
    
    public List<ExtendPath> getPaths()
    {
        return paths;
    }
    
    //
    private List<ExtendPath> paths;
}
