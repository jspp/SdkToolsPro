package com.jf.game.subPackage;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * sun
 * 2018/8/13
 * 13:44
 */
public class StandaloneWriter extends XMLWriter {

    protected void writeDeclaration() throws IOException {
        OutputFormat format = getOutputFormat();

        String encoding = format.getEncoding();

        if (!format.isSuppressDeclaration()) {
            if (encoding.equals("UTF8")) {
                writer.write("<?xml version=\"1.0\"");
                if (!format.isOmitEncoding()) {
                    writer.write(" encoding=\"UTF-8\"");
                }
                writer.write(" standalone=\"no\"");
                writer.write("?>");
            } else {
                writer.write("<?xml version=\"1.0\"");
                if (!format.isOmitEncoding()) {
                    writer.write(" encoding=\"" + encoding + "\"");
                }
                writer.write(" standalone=\"no\"");
                writer.write("?>");
            }

            if (format.isNewLineAfterDeclaration()) {
                println();
            }
        }
    }

    public StandaloneWriter(Writer writer, OutputFormat format) {
        super(writer, format);
    }

    public StandaloneWriter(OutputStream out, OutputFormat format) throws UnsupportedEncodingException {
        super(out, format);
    }
}
