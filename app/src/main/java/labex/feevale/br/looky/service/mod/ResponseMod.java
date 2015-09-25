package labex.feevale.br.looky.service.mod;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by grimmjowjack on 9/23/15.
 */
public class ResponseMod implements Serializable{

    private static final long serialVersionUID = 9158520327481707404L;

    @Expose
    public Long id;
    @Expose
    public String text;
    @Expose
    public Boolean accepted;

    public ResponseMod() {
    }

    public ResponseMod(Long id, String text, Boolean accepted) {
        this.id = id;
        this.text = text;
        this.accepted = accepted;
    }
}
