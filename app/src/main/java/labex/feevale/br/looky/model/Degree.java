package labex.feevale.br.looky.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by grimmjowjack on 8/21/15.
 */
public class Degree implements Serializable, Parcelable{

    private static final long serialVersionUID = -4766000440237750487L;
    @Expose
    private Long id;
    @Expose
    private String name;

    public Degree() {
    }

    public Degree(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Degree(Parcel parcel) {
        this.id = parcel.readLong();
        this.name = parcel.readString();
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

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<Degree> CREATOR
            = new Parcelable.Creator<Degree>() {
        public Degree createFromParcel(Parcel in) {
            return new Degree(in);
        }

        public Degree[] newArray(int size) {
            return new Degree[size];
        }
    };
}
