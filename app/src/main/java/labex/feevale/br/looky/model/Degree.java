package labex.feevale.br.looky.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by grimmjowjack on 8/21/15.
 */
public class Degree implements Serializable{

    private static final long serialVersionUID = -4766000440237750487L;
    @Expose
    private Long id;
    @Expose
    private String name;

    public Degree() {
    }

    public Degree(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
