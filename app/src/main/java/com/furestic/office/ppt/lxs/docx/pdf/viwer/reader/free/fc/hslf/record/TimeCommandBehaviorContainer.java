/*
 * 文件名称:          TimeCommandBehaviorContainer.java
 *  
 * 编译器:            android2.2
 * 时间:              下午7:36:06
 */
package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hslf.record;

/**
 * TODO: a command-animation behavior that performs a command as an animation
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2013-1-7
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:           
 * <p>
 * <p>
 */
public class TimeCommandBehaviorContainer extends PositionDependentRecordContainer
{
    private byte[] _header;
    public static long RECORD_ID = 0xF132;
    
    /**
     * We are of type 0xF144
     */
    public long getRecordType()
    {
        return RECORD_ID;
    }
    
    /**
     * Set things up, and find our more interesting children
     */
    protected TimeCommandBehaviorContainer(byte[] source, int start, int len)
    {
        // Grab the header
        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        // Find our children
        _children = Record.findChildRecords(source, start + 8, len - 8);
    }

}
