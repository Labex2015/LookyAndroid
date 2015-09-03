package labex.feevale.br.looky.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 0126128 on 10/06/2015.
 */
public class RequestHelp implements Serializable{

    private Long id;
    private User requester;
    private User helper;
    private String text;
    private String params;
    private Date date;
    private char status;
    private char type;

    public RequestHelp() {}

    public RequestHelp(Long id, User requester, User helper, String text, String params,
                       Date date, char status, char type) {
        this.id = id;
        this.requester = requester;
        this.helper = helper;
        this.text = text;
        this.params = params;
        this.date = date;
        this.status = status;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getHelper() {
        return helper;
    }

    public void setHelper(User helper) {
        this.helper = helper;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
}
