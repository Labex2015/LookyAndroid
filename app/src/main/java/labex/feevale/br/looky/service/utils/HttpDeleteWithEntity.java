package labex.feevale.br.looky.service.utils;

import org.apache.http.client.methods.HttpPost;

import java.net.URI;

/**
 * Created by 0126128 on 11/05/2015.
 */
public class HttpDeleteWithEntity extends HttpPost {
    public final static String METHOD_NAME = "DELETE";

    public HttpDeleteWithEntity(URI url) {
        super(url);
    }

    public HttpDeleteWithEntity(String url) {
        super(url);
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}
