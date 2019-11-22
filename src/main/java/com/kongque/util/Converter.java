package com.kongque.util;

import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

public class Converter {

    public static void main(String[] args) throws Exception {

    }


    public void generatePDF(String outputFileName,String html) throws Exception {
        StringReader stringReader=new StringReader(html);
    }
    // 手动构造HTML代码
    public void generatePDF_1(OutputStream fos, StringReader strReader) throws Exception {
        PD4ML pd4ml = new PD4ML();
        pd4ml.setPageInsets(new Insets(10, 10, 10, 0));
        pd4ml.setHtmlWidth(800);
//        pd4ml.setPageSize(new Dimension(1400,1415));
        pd4ml.setPageSize(PD4Constants.A4);
        pd4ml.useTTF("java:fonts", true);
//        pd4ml.setDefaultTTFs("KaiTi_GB2312", "KaiTi_GB2312", "KaiTi_GB2312");
        pd4ml.setDefaultTTFs("SimHei", "YouYuan", "SimSun");  //fonts.jar的配置文件中的值，用于中文乱码
        pd4ml.enableDebugInfo();
        pd4ml.render(strReader, fos);
    }

    // HTML代码来自于HTML文件
    public void generatePDF_2(File outputPDFFile, String inputHTMLFileName) throws Exception {
        FileOutputStream fos = new FileOutputStream(outputPDFFile);
        PD4ML pd4ml = new PD4ML();
        pd4ml.setPageInsets(new Insets(20, 10, 10, 10));
        pd4ml.setHtmlWidth(950);
        pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));
        pd4ml.useTTF("java:fonts", true);

//        pd4ml.setDefaultTTFs("KaiTi_GB2312", "KaiTi_GB2312", "KaiTi_GB2312");
        pd4ml.setDefaultTTFs("SimHei", "YouYuan", "SimSun");  //fonts.jar的配置文件中的值，用于中文乱码
        pd4ml.enableDebugInfo();
        pd4ml.render("file:" + inputHTMLFileName, fos);
    }

}
