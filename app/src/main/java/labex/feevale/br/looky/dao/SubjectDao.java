package labex.feevale.br.looky.dao;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import labex.feevale.br.looky.dao.model.DegreeModel;
import labex.feevale.br.looky.dao.model.SubjectModel;
import labex.feevale.br.looky.model.Degree;
import labex.feevale.br.looky.model.Subject;

/**
 * Created by grimmjowjack on 8/28/15.
 */
public class SubjectDao {

    private Realm realm;

    public SubjectDao() {

    }

    public Boolean ïsEmpty(){
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<SubjectModel> results = realm.where(SubjectModel.class)
                    .findAll();
            return (results == null || results.isEmpty());
        }finally {
            if(realm != null)
                realm.close();
        }
    }
    public void saveSubjects(Subject... subjects){
        try {
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            for (Subject s : subjects) {
                SubjectModel sm = realm.createObject(SubjectModel.class);
                sm.setName(s.getName());
                sm.setIdUser(s.getId());

                if (s.getDegree() != null) {
                    DegreeModel resultado = realm.where(DegreeModel.class).equalTo("name", s.getDegree().getName()).findFirst();
                    if (resultado != null) {
                        sm.setDegree(resultado);
                    } else {
                        resultado = realm.createObject(DegreeModel.class);
                        resultado.setIdDegree(s.getDegree().getId());
                        resultado.setName(s.getDegree().getName());
                    }
                    sm.setDegree(resultado);
                }
                realm.commitTransaction();
            }
        }finally {
            if(realm != null)
                realm.close();
        }
    }

    public List<Subject> listAll() {
        try {
            List<Subject> subjects = new ArrayList<>();
            realm = Realm.getDefaultInstance();
            RealmResults<SubjectModel> results = realm.where(SubjectModel.class)
                    .findAll();
            for (SubjectModel s : results) {
                subjects.add(new Subject(s));
            }
            return subjects;
        }finally {
            if(realm != null)
                realm.close();
        }
    }

    public List<Degree> listAllDegrees(){
        realm = Realm.getDefaultInstance();
        List<DegreeModel> list = realm.where(DegreeModel.class).findAll();
        List<Degree> degrees = new ArrayList<>();
        for(DegreeModel m : list)
            degrees.add(new Degree(m));

        return degrees;
    }
}
