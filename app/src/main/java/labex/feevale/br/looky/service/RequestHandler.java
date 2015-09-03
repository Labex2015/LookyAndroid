package labex.feevale.br.looky.service;

import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import labex.feevale.br.looky.model.ServiceError;
import labex.feevale.br.looky.service.utils.HttpDeleteWithEntity;
import labex.feevale.br.looky.service.utils.HttpGetWithEntity;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.AppVariables;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by 0126128 on 11/05/2015.
 */
public abstract class RequestHandler<Entity> {

    public final static int GET = 1;
    public final static int POST = 2;
    public final static int PUT = 3;
    public final static int DELETE = 4;

    public static final int SERVICE = 100;
    public static final int TASK = 200;

    public static final String TYPE_JSON = "application/json; charset=UTF-8";
    public static final String ENCODING_UTF = "UTF-8";

    protected MessageResponse messageResponse = new MessageResponse();
    private String response;
    protected Entity entity;
    private JsonUtils<Entity> jsonUtils;

    private Boolean status = false;

    private Context context;
    private String URL;
    private int methodConnection;
    private String params;

    protected RequestHandler(Entity entity, Context context, String URL, int methodConnection, String params) {
        this.entity = entity;
        this.context = context;
        this.URL = URL;
        this.methodConnection = methodConnection;
        this.params = params;

        jsonUtils = new JsonUtils<Entity>(entity);
        messageResponse = new MessageResponse("", false);
    }

    protected RequestHandler(Entity entity, Context context, String URL, int methodConnection) {
        this(entity, context, URL, methodConnection, null);
    }

    public void makeRequest() {
        try {
            if (validateConnection()) {
                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, AppVariables.HTTP_TIME_OUT);
                HttpConnectionParams.setSoTimeout(httpParams, AppVariables.SOCKET_TIME_OUT);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
                HttpResponse httpResponse = null;
                switch (methodConnection) {
                    case POST:
                        httpResponse = httpClient.execute(executePOST());
                        break;
                    case GET:
                        if (params != null) {
                            httpResponse = httpClient.execute(executeGetWithEntity());
                        } else {
                            HttpGet httpGet = new HttpGet(URL);
                            httpResponse = httpClient.execute(httpGet);
                        }
                        break;
                    case PUT:
                        httpResponse = httpClient.execute(executePut());
                        break;
                    case DELETE:
                        httpResponse = httpClient.execute(executeDelete());
                        break;
                }
                processReturn(httpResponse);
            }
            postExecute(response);

        } catch (Exception e) {
            ServiceError serviceError;
            if (!(e instanceof ConnectTimeoutException)) {
                try {
                    serviceError = new JsonUtils().JsonToError((response == null ? "" : response));
                    messageResponse.setMsg("Status: " + serviceError.getStatus() + ", Message: " + serviceError.getMessage());
                } catch (Exception ex) {
                    messageResponse.setMsg("Problemas ao tentar conectar com o servidor.");
                }

            }else if (e instanceof ConnectionPoolTimeoutException)
                messageResponse.setMsg("Problemas ao tentar conectar com o servidor.");
            else
                messageResponse.setMsg("Verifique sua conexão com a internet.");

            error(messageResponse);
        }
    }


    private void processReturn(HttpResponse httpResponse) throws IOException {
        HttpEntity httpEntity = null;
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if(statusCode >= 200 && statusCode <= 202) {
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
            messageResponse.setStatus(true);
            status = true;
        }else if(statusCode == HttpStatus.SC_NO_CONTENT)
            messageResponse.setMsg("Nenhum conteúdo encontrado.");
        else if(statusCode == HttpStatus.SC_CONFLICT)
            messageResponse.setMsg("Possível duplicidade.");
        else if(statusCode == HttpStatus.SC_GATEWAY_TIMEOUT ||
                statusCode == HttpStatus.SC_REQUEST_TIMEOUT)
            messageResponse.setMsg("Tentativas de consulta expiraram.");
        else if(statusCode == HttpStatus.SC_BAD_REQUEST)
            messageResponse.setMsg("Url inválida: "+URL);
        else if(statusCode == HttpStatus.SC_NOT_FOUND)
            messageResponse.setMsg("Problemas ao localizar serviço.");
        else if(statusCode == HttpStatus.SC_UNAUTHORIZED ||
                statusCode == HttpStatus.SC_FORBIDDEN)
            messageResponse.setMsg("Autenticação necessária.");
        else if(statusCode == HttpStatus.SC_NOT_ACCEPTABLE)
            messageResponse.setMsg("Parâmetros inválidos.");
        else if(statusCode == HttpStatus.SC_SERVICE_UNAVAILABLE)
            messageResponse.setMsg("Problemas ao efetuar solicitação para o servidor.");
        else if(statusCode == HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE)
            messageResponse.setMsg("Parâmetros de requisição inválido.");
    }

    private HttpDeleteWithEntity executeDelete() throws UnsupportedEncodingException {
        HttpDeleteWithEntity httpDelete = new HttpDeleteWithEntity(URL);
        if (params != null) {
            StringEntity se = new StringEntity(params, HTTP.UTF_8);
            se.setContentEncoding(ENCODING_UTF);
            se.setContentType(TYPE_JSON);
            httpDelete.setEntity(se);
        }
        return httpDelete;
    }

    private HttpPut executePut() throws UnsupportedEncodingException {
        HttpPut httpPut = new HttpPut(URL);
        if (params != null) {
            StringEntity se = new StringEntity(params, HTTP.UTF_8);
            se.setContentEncoding(ENCODING_UTF);
            se.setContentType(TYPE_JSON);
            httpPut.setEntity(se);
        }
        return httpPut;
    }

    private void postExecute(String response) {
        try {
            if(status) {
                this.entity = postExecuteCore(response);
                messageResponse.setStatus(true);
                close(this.entity);
            }else
                error(messageResponse);
        } catch (Exception e) {
            e.printStackTrace();
            messageResponse.setMsg("Problemas ao processar retorno.");
            messageResponse.setStatus(false);
            error(messageResponse);
        }
    }

    protected Entity postExecuteCore(String response){
        return (Entity) jsonUtils.process(response);
    }


    private HttpPost executePOST() throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(URL);
        if (params != null) {
            StringEntity se = new StringEntity(params, HTTP.UTF_8);
            /*se.setContentEncoding(ENCODING_UTF);*/
            se.setContentType(TYPE_JSON);
            httpPost.setEntity(se);
        }
        return httpPost;
    }

    private HttpGetWithEntity executeGetWithEntity() throws UnsupportedEncodingException {
        HttpGetWithEntity httpGet = new HttpGetWithEntity(URL);
        StringEntity se = new StringEntity(params.toString());
        se.setContentEncoding(ENCODING_UTF);
        se.setContentType(TYPE_JSON);
        httpGet.setEntity(se);
        return httpGet;
    }


    private Boolean validateConnection(){
        this.messageResponse.setMsg("Sem conexão com a internet.");
        this.messageResponse.setStatus(false);
        return new AppHelp(context).validateConnection();
    }

    protected abstract void error(MessageResponse messageResponse);

    protected abstract void close(Entity entity);

}
