package labex.feevale.br.looky.service.mod;

import java.util.List;

import labex.feevale.br.looky.model.Knowledge;
import labex.feevale.br.looky.model.UserProfile;

/**
 * Created by 0126128 on 08/06/2015.
 */
public class ProfileMod {

    public UserProfile userProfile;
    public List<Knowledge> knowledgeList;
    public List<EvaluationMod> evaluationsList;

    public ProfileMod() {
    }

    public ProfileMod(UserProfile user, List<Knowledge> knowledgeList, List<EvaluationMod> evaluationsList){
        this.userProfile = user;
        this.knowledgeList = knowledgeList;
        this.evaluationsList = evaluationsList;
    }
}
