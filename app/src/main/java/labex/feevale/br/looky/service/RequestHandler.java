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

import labex.feevale.br.looky.model.ServiceError;
import labex.feevale.br.looky.utils.AppHelp;
import labex.feevale.br.looky.utils.JsonUtils;
import labex.feevale.br.looky.utils.MessageResponse;

/**
 * Created by 0126128 on 11/05/2015.
 */
public abstract class RequestHandler<Entity> {

    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";

    public static final int SERVICE = 100;
    public static final int TASK = 200;

    public static final int TIMEOUT = 5000;

    public static final String TYPE_JSON = "application/json; charset=UTF-8";
    public static final String ENCODING_UTF = "UTF-8";

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

    public void makeRequest() {
        java.net.URL url;
        try {
            if (validateConnection()) {
                url = new URL(URL);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod(methodConnection);
                connection.setRequestProperty("Content-Type", TYPE_JSON);
                connection.setConnectTimeout(TIMEOUT);
                if(params != null)
                    configBody(params);
                processReturn();
            }
            postExecute(String.valueOf(response));
        } catch (Exception e) {
            ServiceError serviceError;
            if (!(e instanceof ConnectTimeoutException)) {
                try {
                    serviceError = new JsonUtils().JsonToError((response == null ? "" : response.toString()));
                    messageResponse.setMsg("Status: " + serviceError.getStatus() + ", Message: " + serviceError.getMessage());
                } catch (Exception ex) {
                    messageResponse.setMsg("Problemas ao tentar conectar com o servidor.");
                }

            }else if (e instanceof ConnectException)
                messageResponse.setMsg("Problemas ao tentar conectar com o servidor.");
            else
                messageResponse.setMsg("Verifique sua conexão com a internet.");

            error(messageResponse);
        }finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    private void processReturn(){
        InputStream is = null;
        BufferedReader rd = null;
        try {
            is = connection.getInputStream();
            rd = new BufferedReader(new InputStreamReader(is));
            String line;
            response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            processReturn(connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
            error(new MessageResponse("Erro ao efetuar leitura dos dados retornados", false));
        }finally {
            try {
                if(rd != null)
                    rd.close();
                if(is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void configBody(String params){
        try{
            byte[] postData       = params.getBytes(ENCODING_UTF);
            int    postDataLength = postData.length;
            connection.setDoOutput(true);
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString( postDataLength ));
            connection.setUseCaches(false);
            wr = new DataOutputStream(connection.getOutputStream());
            wr.write(postData);
        } catch (IOException e) {
            e.printStackTrace();
            error(new MessageResponse("Problemas ao processar dados para envio.", false));
        }finally {
            if(wr != null){
                try {
                    wr.flush();
                    wr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void processReturn(int statusCode) throws IOException {
        if(statusCode >= 200 && statusCode <= 202) {
            messageResponse.setStatus(true);
            status = true;
        }else if(statusCode == HttpURLConnection.HTTP_NO_CONTENT)
            messageResponse.setMsg("Nenhum conteúdo encontrado.");
        else if(statusCode == HttpURLConnection.HTTP_CONFLICT)
            messageResponse.setMsg("Possível duplicidade.");
        else if(statusCode == HttpURLConnection.HTTP_GATEWAY_TIMEOUT ||
                statusCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT)
            messageResponse.setMsg("Tentativas de consulta expiraram.");
        else if(statusCode == HttpURLConnection.HTTP_BAD_REQUEST)
            messageResponse.setMsg("Url inválida: "+URL);
        else if(statusCode == HttpURLConnection.HTTP_NOT_FOUND)
            messageResponse.setMsg("Problemas ao localizar serviço.");
        else if(statusCode == HttpURLConnection.HTTP_UNAUTHORIZED ||
                statusCode == HttpURLConnection.HTTP_FORBIDDEN)
            messageResponse.setMsg("Autenticação necessária.");
        else if(statusCode == HttpURLConnection.HTTP_NOT_ACCEPTABLE)
            messageResponse.setMsg("Parâmetros inválidos.");
        else if(statusCode == HttpURLConnection.HTTP_UNAVAILABLE)
            messageResponse.setMsg("Problemas ao efetuar solicitação para o servidor.");
        else if(statusCode == HttpURLConnection.HTTP_UNSUPPORTED_TYPE)
            messageResponse.setMsg("Parâmetros de requisição inválido.");
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

    private Boolean validateConnection(){
        this.messageResponse.setMsg("Sem conexão com a internet.");
        this.messageResponse.setStatus(false);
        return new AppHelp(context).validateConnection();
    }

    protected abstract void error(MessageResponse messageResponse);

    protected abstract void close(Entity entity);

}
