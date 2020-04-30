package com.jf.game.subPackage;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * sun
 * 2018/8/4
 * 17:58
 */
public class XmlUtils {

    /**
     * 获取Root元素
     *
     * @param filePath
     * @return
     */
    public static Element getRootElement(String filePath) {
        //创建Document对象，读取已存在的Xml文件person.xml
        Element rootElement = null;
        try {
            Document doc = new SAXReader().read(new File(filePath));
            rootElement = doc.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return rootElement;

    }

    /**
     * 写XML
     *
     * @param document
     * @throws IOException
     */
    public static void wirte(Document document,String targetFilePath) throws IOException {
        try {
            // 创建文件输出的时候，自动缩进的格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            format.setIndentSize(2);//缩进两个单位
            format.setIndent(true);
            XMLWriter writer = new XMLWriter(new FileOutputStream(targetFilePath), format);
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写XML
     *
     * @param document
     * @throws IOException
     */
    public static void wirteStandLong(Document document,String targetFilePath) throws IOException {
        try {
            // 创建文件输出的时候，自动缩进的格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding( document.getXMLEncoding());
            format.setIndentSize(2);//缩进两个单位
            format.setIndent(true);
            XMLWriter writer = new StandaloneWriter(new FileOutputStream(targetFilePath), format);
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
