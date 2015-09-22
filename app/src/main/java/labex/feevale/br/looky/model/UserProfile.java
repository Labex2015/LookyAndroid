package labex.feevale.br.looky.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by grimmjowjack on 8/13/15.
 */
public class UserProfile extends User implements Serializable, Parcelable{

    private static final long serialVersionUID = -3980100745205975917L;

    public int requester;
    public int helper;
    public int answerPoints;
    public int helpPoints;

    public float rating;

    public List<Evaluation> evaluations;
    public List<Knowledge> knowledges;

    public UserProfile(){}

    public UserProfile(Parcel parcel){
        setValues(parcel);
    }

    public UserProfile(Long id, String username, String name, Float latitude, Float longitude,
                       String description, String degree, Integer semester, String picturePath,
                       byte[] picture, int requester, int helper, int answerPoints, int helpPoints, float rating) {
        super(id, username, name, latitude, longitude, description, degree, semester, picturePath, picture);
        this.requester = requester;
        this.helper = helper;
        this.answerPoints = answerPoints;
        this.helpPoints = helpPoints;
        this.rating = rating;
    }

    public UserProfile(Long id, String username, String name, Float latitude, Float longitude,
                       String description, String degree, Integer semester, String picturePath,
                       byte[] picture, int requester, int helper, int answerPoints, int helpPoints,
                       float rating, List<Evaluation> evaluations, List<Knowledge> knowledges) {
        super(id, username, name, latitude, longitude, description, degree, semester, picturePath, picture);
        this.requester = requester;
        this.helper = helper;
        this.answerPoints = answerPoints;
        this.helpPoints = helpPoints;
        this.rating = rating;
        this.evaluations = evaluations;
        this.knowledges = knowledges;
    }

    public int getRequester() {
        return requester;
    }

    public void setRequester(int requester) {
        this.requester = requester;
    }

    public int getHelper() {
        return helper;
    }

    public void setHelper(int helper) {
        this.helper = helper;
    }

    public int getAnswerPoints() {
        return answerPoints;
    }

    public void setAnswerPoints(int answerPoints) {
        this.answerPoints = answerPoints;
    }

    public int getHelpPoints() {
        return helpPoints;
    }

    public void setHelpPoints(int helpPoints) {
        this.helpPoints = helpPoints;
    }

    public float getRating() {
        if(this.helper > 0 && (this.answerPoints + helpPoints) > 0)
            this.rating = Math.round(((this.answerPoints + helpPoints) / this.helper)/2);
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public List<Knowledge> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(List<Knowledge> knowledges) {
        this.knowledges = knowledges;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(id);
        parcel.writeString(username);
        parcel.writeString(name);
        parcel.writeFloat(latitude);
        parcel.writeFloat(longitude);
        parcel.writeString(description);
        parcel.writeString(degree);
        parcel.writeInt(semester);
        parcel.writeString(picturePath);
        parcel.writeByteArray(picture);
        parcel.writeInt(requester);
        parcel.writeInt(helper);
        parcel.writeInt(answerPoints);
        parcel.writeInt(helpPoints);
        parcel.writeFloat(rating);
        parcel.writeList(evaluations);
        parcel.writeList(knowledges);
    }

    public void setValues(Parcel parcel) {
        id = parcel.readLong();
        username = parcel.readString();
        name = parcel.readString();
        latitude = parcel.readFloat();
        longitude = parcel.readFloat();
        description = parcel.readString();
        degree = parcel.readString();
        semester = parcel.readInt();
        picturePath = parcel.readString();
        //picture = parcel.readByteArray();
        requester = parcel.readInt();
        helper = parcel.readInt();
        answerPoints = parcel.readInt();
        helpPoints = parcel.readInt();
        rating = parcel.readFloat();
        evaluations = parcel.readArrayList(Evaluation.class.getClassLoader());
        knowledges = parcel.readArrayList(Knowledge.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserProfile> CREATOR
            = new Parcelable.Creator<UserProfile>() {
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };
}
