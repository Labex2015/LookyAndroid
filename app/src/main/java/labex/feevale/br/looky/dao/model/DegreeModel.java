package labex.feevale.br.looky.dao.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import labex.feevale.br.looky.model.Degree;

/**
 * Created by grimmjowjack on 8/28/15.
 */
@RealmClass
public class DegreeModel extends RealmObject {

    @PrimaryKey
    private long idDegree;
    private String name;

    public DegreeModel() {}

    public DegreeModel(Degree degree) {
        this.name = degree.getName();
        this.idDegree = degree.getId();
    }

    public long getIdDegree() {
        return idDegree;
    }

    public void setIdDegree(long idDegree) {
        this.idDegree = idDegree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
