package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.common.picture;

public class PictureConverterThread extends Thread
{
	public  PictureConverterThread(PictureConverterMgr converterMgr, String srcPath, String dstPath, String type )
    {
        this.converterMgr = converterMgr;
        this.type = type;
        
        this.sourPath = srcPath;
        this.destPath = dstPath;
    }
    
    public void run() 
    {
    	converterMgr.convertPNG(sourPath, destPath, type,  false);
    }   
    
    
    private PictureConverterMgr converterMgr;
    private String type;
    private String sourPath;
    private String destPath;
}
