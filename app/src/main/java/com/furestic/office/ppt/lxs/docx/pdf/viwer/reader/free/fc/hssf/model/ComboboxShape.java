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


package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.model;

import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.common.shape.ShapeTypes;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.EscherBoolProperty;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.EscherClientDataRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.EscherContainerRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.EscherOptRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.EscherProperties;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.EscherRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.EscherSimpleProperty;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.ddf.EscherSpRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.CommonObjectDataSubRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.EndSubRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.FtCblsSubRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.LbsDataSubRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.record.ObjRecord;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.usermodel.HSSFClientAnchor;
import com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hssf.usermodel.HSSFSimpleShape;


/**
 * Represents a combobox shape.
 * 
 * @author Yegor Kozlov
 */
public class ComboboxShape
        extends AbstractShape {
    private EscherContainerRecord spContainer;
    private ObjRecord objRecord;

    /**
     * Creates the low evel records for a combobox.
     *
     * @param hssfShape The highlevel shape.
     * @param shapeId   The shape id to use for this shape.
     */
    ComboboxShape(HSSFSimpleShape hssfShape, int shapeId) {
        spContainer = createSpContainer(hssfShape, shapeId);
        objRecord = createObjRecord(hssfShape, shapeId);
    }

    /**
     * Creates the low level OBJ record for this shape.
     */
    private ObjRecord createObjRecord(HSSFSimpleShape shape, int shapeId) {
        ObjRecord obj = new ObjRecord();
        CommonObjectDataSubRecord c = new CommonObjectDataSubRecord();
        c.setObjectType(HSSFSimpleShape.OBJECT_TYPE_COMBO_BOX);
        c.setObjectId(  getCmoObjectId(shapeId) );
        c.setLocked(true);
        c.setPrintable(false);
        c.setAutofill(true);
        c.setAutoline(false);

        FtCblsSubRecord f = new FtCblsSubRecord();

        LbsDataSubRecord l = LbsDataSubRecord.newAutoFilterInstance();

        EndSubRecord e = new EndSubRecord();

        obj.addSubRecord(c);
        obj.addSubRecord(f);
        obj.addSubRecord(l);
        obj.addSubRecord(e);

        return obj;
    }

    /**
     * Generates the escher shape records for this shape.
     */
    private EscherContainerRecord createSpContainer(HSSFSimpleShape shape, int shapeId) {
        EscherContainerRecord spContainer = new EscherContainerRecord();
        EscherSpRecord sp = new EscherSpRecord();
        EscherOptRecord opt = new EscherOptRecord();
        EscherClientDataRecord clientData = new EscherClientDataRecord();

        spContainer.setRecordId(EscherContainerRecord.SP_CONTAINER);
        spContainer.setOptions((short) 0x000F);
        sp.setRecordId(EscherSpRecord.RECORD_ID);
        sp.setOptions((short) ((ShapeTypes.HostControl << 4) | 0x2));

        sp.setShapeId(shapeId);
        sp.setFlags(EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_HASSHAPETYPE);
        opt.setRecordId(EscherOptRecord.RECORD_ID);
        opt.addEscherProperty(new EscherBoolProperty(EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, 17039620));
        opt.addEscherProperty(new EscherBoolProperty(EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 0x00080008));
        opt.addEscherProperty(new EscherBoolProperty(EscherProperties.LINESTYLE__NOLINEDRAWDASH, 0x00080000));
        opt.addEscherProperty(new EscherSimpleProperty(EscherProperties.GROUPSHAPE__PRINT, 0x00020000));

        HSSFClientAnchor userAnchor = (HSSFClientAnchor) shape.getAnchor();
        userAnchor.setAnchorType(1);
        EscherRecord anchor = createAnchor(userAnchor);
        clientData.setRecordId(EscherClientDataRecord.RECORD_ID);
        clientData.setOptions((short) 0x0000);

        spContainer.addChildRecord(sp);
        spContainer.addChildRecord(opt);
        spContainer.addChildRecord(anchor);
        spContainer.addChildRecord(clientData);

        return spContainer;
    }

    public EscherContainerRecord getSpContainer() {
        return spContainer;
    }

    public ObjRecord getObjRecord() {
        return objRecord;
    }

}
