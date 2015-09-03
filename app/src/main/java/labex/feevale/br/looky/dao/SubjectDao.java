package labex.feevale.br.looky.dao;

import java.util.ArrayList;
import java.util.List;

import labex.feevale.br.looky.dao.model.DegreeModel;
import labex.feevale.br.looky.dao.model.SubjectModel;
import labex.feevale.br.looky.model.Subject;
import labex.feevale.br.looky.utils.L;

/**
 * Created by grimmjowjack on 8/28/15.
 */
public class SubjectDao {

    public Boolean ïsEmpty(){
        List result = SubjectModel.listAll(SubjectModel.class);
        return (result == null || result.isEmpty());
    }
    public void saveSubjects(Subject... subjects){
        SubjectModel sm = null;
        for(Subject s : subjects){
            sm = new SubjectModel(s);
            if(s.getDegree() != null) {
                List<DegreeModel> resultado = DegreeModel.find(DegreeModel.class, "name = ? ", s.getName());
                DegreeModel dm = null;
                if(resultado != null && !resultado.isEmpty()) {
                    dm = resultado.get(0);
                }else {
                    dm = new DegreeModel(s.getDegree());
                    dm.save();
                }
                sm.setDegree(dm);
            }
            sm.save();
        }
    }

    public List<Subject> listAll() {
        List<Subject> subjects = new ArrayList<>();
        for(SubjectModel s : SubjectModel.listAll(SubjectModel.class)) {
            subjects.add(new Subject(s));
        }
        return subjects;
    }
}
