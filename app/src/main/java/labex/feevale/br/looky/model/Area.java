package labex.feevale.br.looky.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by 0126128 on 07/07/2015.
 */
public class Area implements Parcelable, Serializable{

    @Expose
    private Long id;
    @Expose
    private String name;

    private Subject subject;

    public Area() { }

    public Area(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Area(Long id, String name, Subject subject) {
        this.id = id;
        this.name = name;
        this.subject = subject;
    }

    public Area(Parcel parcel){
        this.id = parcel.readLong();
        this.name = parcel.readString();
        this.subject = parcel.readParcelable(Subject.class.getClassLoader());
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeParcelable(subject, i);
    }
}
