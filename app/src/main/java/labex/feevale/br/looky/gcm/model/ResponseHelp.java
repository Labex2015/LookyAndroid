package labex.feevale.br.looky.gcm.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import labex.feevale.br.looky.model.Interaction;
import labex.feevale.br.looky.model.User;

public class ResponseHelp implements Serializable{

    @Expose
    public String message;
    @Expose
    public Boolean status;
    @Expose
    public User user;
    @Expose
    public Interaction interaction;

    public ResponseHelp() {
    }

    public ResponseHelp(String message, Boolean status, User user) {
        this.message = message;
        this.status = status;
        this.user = user;
    }

    public ResponseHelp(String message, Boolean status, User user, Interaction interaction) {
        this.message = message;
        this.status = status;
        this.user = user;
        this.interaction = interaction;
    }
}