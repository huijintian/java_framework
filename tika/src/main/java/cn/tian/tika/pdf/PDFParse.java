package cn.tian.tika.pdf;

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.*;

/**
 * Created by mengtian on 2018/3/14
 */
public class PDFParse {
    public static void main(String[] args) throws IOException, TikaException, SAXException {
        String filename = "/Users/mengtian/Documents/BOOK/AI论文/D14-1181-cnn文本分类.pdf";

        String text = parsing(filename);
        System.out.println(text);
    }

    public static String parsing(String filename) throws IOException, TikaException, SAXException {
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();

        metadata.set("resourceName", filename);
        InputStream is = TikaInputStream.get(new File(filename));

        parser.parse(is, handler, metadata, new ParseContext());
        return handler.toString();
    }

}
