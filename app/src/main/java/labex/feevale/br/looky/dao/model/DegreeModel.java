package labex.feevale.br.looky.dao.model;

import com.orm.SugarRecord;

import java.io.Serializable;

import labex.feevale.br.looky.model.Degree;

/**
 * Created by grimmjowjack on 8/28/15.
 */
public class DegreeModel extends SugarRecord<DegreeModel> implements Serializable{

    private static final long serialVersionUID = 6465806781769429123L;

    private Long _id;
    private String name;

    public DegreeModel() {}

    public DegreeModel(Degree degree) {
        this.name = degree.getName();
        this._id = degree.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
