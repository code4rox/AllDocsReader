/*
 * 文件名称:          XLSReader2.java
 *  
 * 编译器:            android2.2
 * 时间:              上午11:12:41
 */
package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.xls;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.formula.eval.ErrorEval;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.model.InternalSheet;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.model.InternalWorkbook;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.model.RecordStream;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.BoolErrRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.CellValueRecordInterface;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.NameRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.NumberRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.Record;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.RecordFactory;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.poifs.filesystem.DirectoryNode;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.poifs.filesystem.POIFSFileSystem;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.XLSModel.ACell;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.XLSModel.AWorkbook;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.baseModel.Cell;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.ss.model.baseModel.Workbook;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.system.AbortReaderError;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.system.IControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2012-7-19
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:           
 * <p>
 * <p>
 */
public class XLSReader extends SSReader
{    
    /**
     * 
     * @param filePath
     */
    public XLSReader(IControl control, String filePath)
    {
        this.control = control;
        this.filePath = filePath;
    }
    
    /**
     * 
     */
    public Object getModel() throws Exception
    {
        FileInputStream is = new FileInputStream(filePath);
        Workbook book = new AWorkbook(is, this);
        
        return book;
    }
    
    /**
     * 
     * @param file
     * @param key
     * @return
     */
    public boolean searchContent(File file, String key) throws Exception
    {        
        try
        {
            key = key.toLowerCase();
            
            FileInputStream is = new FileInputStream(file.getAbsolutePath());
            DirectoryNode directory = new POIFSFileSystem(is).getRoot();
            
            String workbookName = AWorkbook.getWorkbookDirEntryName(directory);

            // Grab the data from the workbook stream, however
            //  it happens to be spelled.
            InputStream stream = directory.createDocumentInputStream(workbookName);

            List<Record> records = RecordFactory.createRecords(stream, this);

            InternalWorkbook workbook = InternalWorkbook.createWorkbook(records, this);
            
            //sheet name
            int numSheets = workbook.getNumSheets();
            int sheetIndex = 0;
            while(sheetIndex < numSheets)
            {
                if(workbook.getSheetName(sheetIndex++).toLowerCase().contains(key))
                {
                    return true;
                }
            }
            
            // shared string
            int size = workbook.getSSTUniqueStringSize();
            for(int i = 0; i < size; i++)
            {
                checkAbortReader();                
               if(workbook.getSSTString(i).getString().toLowerCase().contains(key))
               {
                   return true;
               }
            }            
            
            //cell of every sheet
            int recOffset = workbook.getNumRecords();
            RecordStream rs = new RecordStream(records, recOffset);
            sheetIndex = 0;
            while (rs.hasNext())
            {
                InternalSheet internalSheet = InternalSheet.createSheet(rs, this);
                if(search_Sheet(internalSheet, key))
                {
                    return true;
                }           
            }
            
            //name
            for (int i = 0; i < workbook.getNumNames(); ++i)
            {               
                NameRecord nameRecord = workbook.getNameRecord(i);
                if(nameRecord.getNameText().toLowerCase().contains(key))
                {
                   return true;
                }
            }
            
            return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        
    }
    

    private  boolean search_Sheet(InternalSheet sheet, String key)
    { 
        Iterator<CellValueRecordInterface> iter = sheet.getCellValueIterator();
        
        // Add every cell to its row
        while (iter.hasNext())
        {
            CellValueRecordInterface cval = iter.next();
            checkAbortReader();
            
            if(search_Cell(cval, key))
            {
                return true;
            }
        }
        
        return false;
    }  
    
    
    private boolean search_Cell(CellValueRecordInterface cval, String key)
    {
        short cellType    = (short)ACell.determineType(cval);              
        
        switch (cellType)
        {
            case Cell.CELL_TYPE_NUMERIC:
                return String.valueOf(((NumberRecord)cval).getValue()).contains(key);
            case Cell.CELL_TYPE_STRING:
            case Cell.CELL_TYPE_BLANK:
            case Cell.CELL_TYPE_FORMULA: 
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf((( BoolErrRecord )cval).getBooleanValue()).toLowerCase().contains(key);
                
            case Cell.CELL_TYPE_ERROR:
                return ErrorEval.getText(((BoolErrRecord)cval).getErrorValue()).toLowerCase().contains(key);
                
        }
        
        return false;
    }
    
    private void checkAbortReader()
    {
        if (abortReader)
        {
            throw new AbortReaderError("abort Reader");
        }
    }
    /**
     * 
     */
    public void dispose()
    {
        super.dispose();
        filePath = null;
    }
    
    // 文件路径
    private String filePath;
}
