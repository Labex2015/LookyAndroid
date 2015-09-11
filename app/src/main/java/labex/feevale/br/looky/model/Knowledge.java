package labex.feevale.br.looky.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by 0126128 on 08/07/2015.
 */
public class Knowledge implements Serializable, Parcelable {

    static final long serialVersionUID =-8005542365454255368L;

    @Expose
    private String name;
    @Expose
    private Long area;
    @Expose
    private Long subject;
    @Expose
    private Long idUser;
    private String subjectName;

    public Knowledge() {
    }

    public Knowledge(Area area, Subject subject, Long idUser) {
        this.area = area.getId();
        this.subject = subject.getId();
        this.idUser = idUser;
        this.name = area.getName();
    }

    public Knowledge(String name, Long area, Long subject, Long idUser) {
        this.name = name;
        this.area = area;
        this.subject = subject;
        this.idUser = idUser;
    }

    public Knowledge(Parcel parcel) {
        this.name = parcel.readString();
        this.area = parcel.readLong();
        this.subject = parcel.readLong();
        this.idUser = parcel.readLong();
        this.subjectName = parcel.readString();
    }

    public Knowledge(Area selectedArea, Subject selectedSubject) {
        if(selectedArea.getId() != null && selectedArea.getId() > 0)
            this.area = selectedArea.getId();
        this.name = selectedArea.getName();
        if(selectedSubject != null) {
            this.subject = selectedSubject.getId();
        }
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.area);
        parcel.writeLong(this.subject != null ? this.subject : 0);
        parcel.writeLong(this.idUser);
        parcel.writeString(this.name);
    }

    public static final Parcelable.Creator<Knowledge> CREATOR
            = new Parcelable.Creator<Knowledge>() {
        public Knowledge createFromParcel(Parcel in) {
            return new Knowledge(in);
        }

        public Knowledge[] newArray(int size) {
            return new Knowledge[size];
        }
    };
}
