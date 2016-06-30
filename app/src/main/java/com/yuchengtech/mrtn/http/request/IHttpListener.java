package com.yuchengtech.mrtn.http.request;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author L1
 */
public interface IHttpListener {

    void handleError(String err) throws IOException;

    void decode(String in) throws IOException;

    void decode(InputStream in) throws IOException;

}
