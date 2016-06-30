package com.yuchengtech.mrtn.utils.xml;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxXmlParser implements xmlParser {

    @Override
    public XmlForm parse(InputStream is) throws Exception {
        // TODO Auto-generated method stub
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XmlHandler handler = new XmlHandler();
        parser.parse(is, handler);
        return handler.getForm();
    }

    @Override
    public String serialize(XmlForm form) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
