package labex.feevale.br.looky.dao.model;

import com.orm.SugarRecord;

import java.io.Serializable;

import labex.feevale.br.looky.model.Degree;
import labex.feevale.br.looky.model.Subject;

/**
 * Created by grimmjowjack on 8/28/15.
 */
public class SubjectModel extends SugarRecord<SubjectModel> implements Serializable{

    private static final long serialVersionUID = -7488144636704756313L;

    private Long _id;
    private String name;
    private DegreeModel degree;

    public SubjectModel() {}

    public SubjectModel(Subject subject) {
        this.name = subject.getName();
        this._id = subject.getId();
        this.setDegree(subject.getDegree());
    }

    private void setDegree(Degree param) {
        this.degree = new DegreeModel(param);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DegreeModel getDegree() {
        return degree;
    }

    public void setDegree(DegreeModel degree) {
        this.degree = degree;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
