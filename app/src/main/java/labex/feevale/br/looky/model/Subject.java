package labex.feevale.br.looky.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import labex.feevale.br.looky.dao.model.DegreeModel;
import labex.feevale.br.looky.dao.model.SubjectModel;

/**
 * Created by 0126128 on 07/07/2015.
 */
public class Subject implements Serializable, Parcelable{

    private static final long serialVersionUID = 6497238944859626381L;

    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private Degree degree;


    public Subject() {
    }

    public Subject(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subject(Long id, String name, Degree degree) {
        this.id = id;
        this.name = name;
        this.degree = degree;
    }

    public Subject(Parcel parcel){
        this.id = parcel.readLong();
        this.name = parcel.readString();
        this.degree = (Degree) parcel.readSerializable();
    }

    public Subject(SubjectModel s) {
        this.id = s.getIdUser();
        this.name = s.getName();
        this.setDegree(s.getDegree());
    }

    private void setDegree(DegreeModel degree) {
        if(degree != null)
            this.degree = new Degree(degree.getIdDegree(), degree.getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeSerializable(degree);
    }

    public static final Parcelable.Creator<Subject> CREATOR
            = new Parcelable.Creator<Subject>() {
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };
}