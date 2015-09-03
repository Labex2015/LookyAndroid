package labex.feevale.br.looky.utils;

import java.io.Serializable;

public class MessageResponse implements Serializable{

    static final long serialVersionUID =-326280055423255368L;

    protected String message;
    protected Boolean status;

    public MessageResponse(String msg, Boolean status) {
        this.message = msg;
        this.status = status;
    }

    public MessageResponse() {
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Message: "+getMsg()+", Status: "+String.valueOf(getStatus());
    }
}
