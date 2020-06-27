package com.company;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class OutXsl {
    private String stuIf[][] = null;
    private String course = null;
    public OutXsl(String[][] stuIf, String course) {
        this.stuIf = stuIf;
        this.course = course;
    }
    public boolean output()
    {
        try {
            WritableWorkbook book = Workbook.createWorkbook(new File(course+".xls"));
            WritableSheet sheet = book.createSheet(course, 0);



            WritableFont font1 = new WritableFont(WritableFont.TIMES,20,WritableFont.BOLD);
            WritableFont font2 = new WritableFont(WritableFont.TIMES,11,WritableFont.BOLD);
            WritableFont font3 = new WritableFont(WritableFont.TIMES,13,WritableFont.BOLD);
            WritableCellFormat format1=new WritableCellFormat(font1);//format1：20号字体，加粗显示
            WritableCellFormat format2=new WritableCellFormat(font2);//format2：11号字体，加粗
            WritableCellFormat format3=new WritableCellFormat(font3);//format2：11号字体，加粗
            format1.setAlignment(jxl.format.Alignment.CENTRE);
            format2.setAlignment(jxl.format.Alignment.CENTRE);
            format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            format3.setAlignment(jxl.format.Alignment.CENTRE);
            format3.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            format2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

            sheet.mergeCells(0,0,8,1);
            sheet.setColumnView(3,12);
            sheet.setColumnView(1,9);
            sheet.setColumnView(4,11);
            sheet.setColumnView(5,15);
            sheet.setColumnView(6,15);
            sheet.setColumnView(7,23);
            sheet.setColumnView(8,15);

            Label[] labelt = new Label[9];
            labelt[0] = new Label(0,2,"序号",format3);
            labelt[1] = new Label(1,2,"姓名",format3);
            labelt[2] = new Label(2,2,"年龄",format3);
            labelt[3] = new Label(3,2,"性别",format3);
            labelt[4] = new Label(4,2,"专业",format3);
            labelt[5] = new Label(5,2,"班级",format3);
            labelt[6] = new Label(6,2,"学号",format3);
            labelt[7] = new Label(7,2,"邮箱",format3);
            labelt[8] = new Label(8,2,"手机号码",format3);
            for(int i=0;i<9;i++)
                sheet.addCell(labelt[i]);

            for(int i=0;i<stuIf.length;i++)
            {
                Label[] label = new Label[9];
                for(int j=0;j<9;j++)
                {
                    label[j] = new Label(j,3+i,stuIf[i][j],format2);
                    sheet.addCell(label[j]);
                }
            }

            Label labeltitle = new Label(0, 0, course+"的选课情况",format1);
            sheet.addCell(labeltitle);

            book.write();
            book.close();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return true;
    }

}