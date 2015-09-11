package labex.feevale.br.looky.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 0126128 on 11/06/2015.
 */
public class Evaluation implements Serializable, Parcelable{

    private Long id;
    private Integer helpPoints;
    private Integer answerPoints;
    private String comment;
    private Date evaluated;
    private User userEvaluator;

    public Evaluation() {
    }

    public Evaluation(Long id, Integer helpPoints, Integer answerPoints, String comment,
                      Date evaluated, User userEvaluator) {
        this.id = id;
        this.helpPoints = helpPoints;
        this.answerPoints = answerPoints;
        this.comment = comment;
        this.evaluated = evaluated;
        this.userEvaluator = userEvaluator;
    }

    public Evaluation(Parcel parcel){
        this.id = parcel.readLong();
        this.helpPoints = parcel.readInt();
        this.answerPoints = parcel.readInt();
        this.comment = parcel.readString();
        this.evaluated = (Date) parcel.readValue(Date.class.getClassLoader());
        this.userEvaluator = parcel.readParcelable(User.class.getClassLoader());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(Date evaluated) {
        this.evaluated = evaluated;
    }

    public User getUserEvaluator() {
        return userEvaluator;
    }

    public void setUserEvaluator(User userEvaluator) {
        this.userEvaluator = userEvaluator;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeInt(helpPoints);
        parcel.writeInt(answerPoints);
        parcel.writeString(comment);
        parcel.writeValue(evaluated);
        parcel.writeParcelable(userEvaluator, i);
    }

    public static final Parcelable.Creator<Evaluation> CREATOR
            = new Parcelable.Creator<Evaluation>() {
        public Evaluation createFromParcel(Parcel in) {
            return new Evaluation(in);
        }

        public Evaluation[] newArray(int size) {
            return new Evaluation[size];
        }
    };
}
