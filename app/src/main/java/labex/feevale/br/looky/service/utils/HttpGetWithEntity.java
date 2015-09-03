package labex.feevale.br.looky.service.utils;

import org.apache.http.client.methods.HttpPost;

import java.net.URI;

/**
 * Created by 0126128 on 15/12/2014.
 */
public class HttpGetWithEntity extends HttpPost {
    public final static String METHOD_NAME = "GET";

    public HttpGetWithEntity(URI url) {
        super(url);
    }

    public HttpGetWithEntity(String url) {
        super(url);
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}
