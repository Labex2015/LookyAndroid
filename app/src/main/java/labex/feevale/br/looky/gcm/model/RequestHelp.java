package labex.feevale.br.looky.gcm.model;

import java.io.Serializable;

import labex.feevale.br.looky.model.User;

public class RequestHelp implements Serializable{

    public static final long serialVersionUID = 421L;

    private Long id;
    private String text;
    private User user;

    public RequestHelp(Long id, String text, User user) {
        this.id = id;
        this.text = text;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
