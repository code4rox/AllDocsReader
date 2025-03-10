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

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.usermodel;


import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.CFRuleRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.cf.PatternFormatting;


/**
 * High level representation for Conditional Formatting settings
 * 
 * @author Dmitriy Kumshayev
 *
 */
public class HSSFPatternFormatting implements com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ss.usermodel.PatternFormatting
{
	private final CFRuleRecord cfRuleRecord;
	private final PatternFormatting patternFormatting;
	
	protected HSSFPatternFormatting(CFRuleRecord cfRuleRecord)
	{
		this.cfRuleRecord = cfRuleRecord; 
		this.patternFormatting = cfRuleRecord.getPatternFormatting();
	}

	protected PatternFormatting getPatternFormattingBlock()
	{
		return patternFormatting;
	}

	/**
	 * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.cf.PatternFormatting#getFillBackgroundColor()
	 */
	public short getFillBackgroundColor()
	{
		return (short)patternFormatting.getFillBackgroundColor();
	}

	/**
	 * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.cf.PatternFormatting#getFillForegroundColor()
	 */
	public short getFillForegroundColor()
	{
		return (short)patternFormatting.getFillForegroundColor();
	}

	/**
	 * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.cf.PatternFormatting#getFillPattern()
	 */
	public short getFillPattern()
	{
		return (short)patternFormatting.getFillPattern();
	}

	/**
	 * @param bg
	 * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.cf.PatternFormatting#setFillBackgroundColor(int)
	 */
	public void setFillBackgroundColor(short bg)
	{
		patternFormatting.setFillBackgroundColor(bg);
		if( bg != 0)
		{
			cfRuleRecord.setPatternBackgroundColorModified(true);
		}
	}

	/**
	 * @param fg
	 * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.cf.PatternFormatting#setFillForegroundColor(int)
	 */
	public void setFillForegroundColor(short fg)
	{
		patternFormatting.setFillForegroundColor(fg);
		if( fg != 0)
		{
			cfRuleRecord.setPatternColorModified(true);
		}
	}

	/**
	 * @param fp
	 * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.cf.PatternFormatting#setFillPattern(int)
	 */
	public void setFillPattern(short fp)
	{
		patternFormatting.setFillPattern(fp);
		if( fp != 0)
		{
			cfRuleRecord.setPatternStyleModified(true);
		}
	}
}
