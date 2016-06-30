package com.yuchengtech.mrtn.utils.xml;

import java.io.InputStream;


public interface xmlParser {
    public XmlForm parse(InputStream is) throws Exception;

    public String serialize(XmlForm form) throws Exception;

}
