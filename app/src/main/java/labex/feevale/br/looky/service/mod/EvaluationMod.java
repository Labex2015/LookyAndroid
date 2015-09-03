package labex.feevale.br.looky.service.mod;

import java.util.Date;

/**
 * Created by 0126128 on 08/06/2015.
 */
public class EvaluationMod {

    public String avaliator;
    public Integer rating;
    public Integer helpPoints = 0;
    public Integer answerPoints = 0;
    public Date avaliated;
    public String comment;

    public EvaluationMod() {
    }


    public EvaluationMod(String avaliator, Integer helpPoints, Integer answerPoints, Date avaliated, String comment) {
        this.avaliator = avaliator;
        this.helpPoints = helpPoints;
        this.answerPoints = answerPoints;
        this.avaliated = avaliated;
        this.comment = comment;
    }

    public String getAvaliator() {
        return avaliator;
    }

    public void setAvaliator(String avaliator) {
        this.avaliator = avaliator;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRating() {
        if(this.helpPoints != null && this.helpPoints > 0
                || this.answerPoints != null && this.answerPoints > 0){
            this.rating = (this.helpPoints + this.answerPoints)/2;
        }else
            this.rating = 0;

        return rating;
    }

    public Date getAvaliated() {
        return avaliated;
    }

    public void setAvaliated(Date avaliated) {
        this.avaliated = avaliated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getHelpPoints() {
        return helpPoints;
    }

    public void setHelpPoints(Integer helpPoints) {
        this.helpPoints = helpPoints;
    }

    public Integer getAnswerPoints() {
        return answerPoints;
    }

    public void setAnswerPoints(Integer answerPoints) {
        this.answerPoints = answerPoints;
    }
}
