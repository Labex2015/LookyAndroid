package labex.feevale.br.looky.model;

import com.google.gson.annotations.Expose;

/**
 * Created by 0126128 on 23/06/2015.
 */
public class GlobalHelp {

    @Expose
    private Long idUser;
    @Expose
    private Long idRequestHelp;
    @Expose
    private String title;
    @Expose
    private String description;
    @Expose
    private String params;


    public GlobalHelp() { }

    public GlobalHelp(Long idUser, Long idRequestHelp, String title, String description, String params) {
        this.idUser = idUser;
        this.idRequestHelp = idRequestHelp;
        this.title = title;
        this.description = description;
        this.params = params;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdRequestHelp() {
        return idRequestHelp;
    }

    public void setIdRequestHelp(Long idRequestHelp) {
        this.idRequestHelp = idRequestHelp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
