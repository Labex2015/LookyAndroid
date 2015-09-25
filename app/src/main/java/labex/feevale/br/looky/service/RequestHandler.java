package labex.feevale.br.looky.service;

import android.content.Context;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.L;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by 0126128 on 11/05/2015.
 */
public abstract class RequestHandler<Entity> {

    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";

    public static final int TIMEOUT = 5000;

    public static final String TYPE_JSON = "application/json; charset=UTF-8";
    public static final String ENCODING_UTF = "UTF-8";

    public static final String USER_PARAM = "user";
    public static final String TOKEN_PARAM = "token";
    public static final String ITEM_PARAM = "item";


    protected MessageResponse messageResponse = new MessageResponse();
    protected Entity entity;
    private JsonUtils<Entity> jsonUtils;

    private Boolean status = false;

    private HttpURLConnection connection;
    private DataOutputStream wr = null;

    private Context context;
    private String URL;
    private String methodConnection;
    private String params;
    private StringBuffer response;
    private HashMap<String, String> headerParams;

    private int statusResponse;

    protected RequestHandler(Entity entity, Context context, String URL, String methodConnection, String params) {
        this.entity = entity;
        this.context = context;
        this.URL = URL;
        this.methodConnection = methodConnection;
        this.params = params;

        jsonUtils = new JsonUtils<Entity>(entity);
        messageResponse = new MessageResponse("", false);
    }

    protected RequestHandler(Entity entity, Context context, String URL, String methodConnection) {
        this(entity, context, URL, methodConnection, null);
    }

    protected RequestHandler(Entity entity, Context context, String URL, String methodConnection,
                             String params, HashMap headerParams) {
        this(entity, context, URL, methodConnection, params);
        this.headerParams = headerParams;
    }

    public void makeRequest() {
        java.net.URL url;
        try {
            if (validateConnection()) {
                url = new URL(URL);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod(methodConnection);
                connection.setRequestProperty("Content-Type", TYPE_JSON);
                connection.setConnectTimeout(TIMEOUT);
                if((methodConnection.equals(POST)|| methodConnection.equals(PUT))&& params != null)
                    configBody(params);

                if(headerParams != null)
                    processHeader();

                processReturn();
                postExecute(response != null ? response.toString() : null);
            }else{
                error(new MessageResponse("Sem conexão com a internet", false));
            }
        } catch (Exception e) {
            if (e instanceof ConnectTimeoutException || e instanceof ConnectException)
                messageResponse.setMsg("Problemas ao tentar conectar com o servidor.");
            else if (e instanceof NullPointerException)
                messageResponse.setMsg("Humm, algo de errado deve ter acontecido com o servidor.");
            else
                messageResponse.setMsg(e.getMessage());
            error(messageResponse);
        }finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    private void processHeader() {
        Iterator it = headerParams.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            connection.setRequestProperty(pair.getKey().toString(), pair.getValue().toString());
        }
    }

    private void processReturn() throws IOException {
        InputStream is = null;
        BufferedReader rd = null;
        try {
            if(processReturn(connection.getResponseCode())) {
                is = connection.getInputStream();
                rd = new BufferedReader(new InputStreamReader(is));
                String line;
                response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
            }
        } catch (IOException e) {
            if(e instanceof ConnectException)
                throw new IOException("Não foi possível conectar com o servidor.");
            throw new IOException("Erro ao efetuar leitura dos dados retornados");
        }finally {
            try {
                if(rd != null)
                    rd.close();
                if(is != null)
                    is.close();
            } catch (IOException e) {L.output(e.getMessage());}
        }
    }

    private void configBody(String params) throws IOException {
        try{
            byte[] postData       = params.getBytes(ENCODING_UTF);
            int    postDataLength = postData.length;
            connection.setDoOutput(true);
            connection.setRequestProperty("charset", ENCODING_UTF);
            connection.setRequestProperty("Content-Length", Integer.toString( postDataLength ));
            connection.setUseCaches(false);
            wr = new DataOutputStream(connection.getOutputStream());
            wr.write(postData);
        } catch (IOException e) {
            if(e instanceof ConnectException)
                throw new IOException("Problemas ao tentar conectar com o servidor.");

            throw new IOException("Problemas ao processar dados para envio.");
        }finally {
            if(wr != null){
                try {
                    wr.flush();
                    wr.close();
                } catch (IOException e) {L.output(e.getMessage());}
            }
        }
    }


    private Boolean processReturn(int statusCode) throws IOException {
        L.output("STATUS CODE: "+statusCode);
        switch (statusCode) {
            case 200:;
            case 201:;
            case 202:
                messageResponse.setStatus(true);
                return true;
            case HttpURLConnection.HTTP_NO_CONTENT:
                messageResponse.setStatus(true);
                messageResponse.setMsg("Nenhum conteúdo encontrado.");
                statusResponse = statusCode;
                break;
            case HttpURLConnection.HTTP_CONFLICT:
                messageResponse.setMsg("Possível duplicidade.");
                break;
            case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:;
            case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
                messageResponse.setMsg("Tentativas de consulta expiraram.");
                break;
            case HttpURLConnection.HTTP_BAD_REQUEST:
                messageResponse.setMsg("Url inválida: " + URL);
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                messageResponse.setMsg("Problemas ao localizar serviço.");
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:;
            case HttpURLConnection.HTTP_FORBIDDEN:
                messageResponse.setMsg("Autenticação necessária.");
                break;
            case HttpURLConnection.HTTP_NOT_ACCEPTABLE:
                messageResponse.setMsg("Parâmetros inválidos.");
                break;
            case HttpURLConnection.HTTP_UNAVAILABLE:
                messageResponse.setMsg("Problemas ao efetuar solicitação para o servidor.");
                break;
            case 422:
                messageResponse.setMsg(connection.getResponseMessage());
                break;
            case HttpURLConnection.HTTP_UNSUPPORTED_TYPE:
                messageResponse.setMsg("Parâmetros de requisição inválido.");
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                messageResponse.setMsg("Humm, parece que estamos com problema nos nossos servidores.");
                break;
            default: messageResponse.setMsg("Problemas ao efetuar comunicação com o servidor.");
        }
        return false;
    }

    private void postExecute(String response) {
        if(response != null && statusResponse != 204) {
            this.entity = postExecuteCore(response);
            messageResponse.setStatus(true);
            close(this.entity);
        }else
            if(messageResponse.getStatus())
                close(null);
            else
                error(messageResponse);
    }

    protected Entity postExecuteCore(String response){
        return (Entity) jsonUtils.process(response);
    }

    private boolean validateConnection(){
        return new AppHelp(context).validateConnection();
    }

    protected abstract void error(MessageResponse messageResponse);

    protected abstract void close(Entity entity);
}
