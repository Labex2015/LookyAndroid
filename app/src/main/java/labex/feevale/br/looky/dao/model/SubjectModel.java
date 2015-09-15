package labex.feevale.br.looky.dao.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import labex.feevale.br.looky.model.Subject;

/**
 * Created by grimmjowjack on 8/28/15.
 */
@RealmClass
public class SubjectModel extends RealmObject {

    @PrimaryKey
    private long idUser;
    private String name;
    private DegreeModel degree;

    public SubjectModel() {}

    public SubjectModel(Subject subject) {
        this.name = subject.getName();
        this.idUser = subject.getId();
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
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
}
