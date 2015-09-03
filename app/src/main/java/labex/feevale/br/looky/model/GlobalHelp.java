package labex.feevale.br.looky.model;

/**
 * Created by 0126128 on 23/06/2015.
 */
public class GlobalHelp {

    private Long idRequestHelp;
    private String title;
    private String description;


    public GlobalHelp() { }

    public GlobalHelp(Long idRequestHelp, String title, String description) {
        this.idRequestHelp = idRequestHelp;
        this.title = title;
        this.description = description;
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
}
