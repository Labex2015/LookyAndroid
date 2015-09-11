package labex.feevale.br.looky.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by 0126128 on 08/06/2015.
 */
public class User implements Serializable, Parcelable{

    private static final long serialVersionUID = 6844993266929805511L;

    public Long id;
    public String username;
    public String name;
    public Float latitude;
    public Float longitude;
    public String description;
    public String degree;
    public Integer semester;
    public String picturePath;
    public byte[] picture;
    private String accountID;

    public User() {}

    public User(Long id, String username, String name, Float latitude, Float longitude,
                String description, String degree, Integer semester, String picturePath,
                byte[] picture) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.degree = degree;
        this.semester = semester;
        this.picturePath = picturePath;
        this.picture = picture;
    }

    public User(Parcel parcel){
        this.id = parcel.readLong();
        this.username = parcel.readString();
        this.name = parcel.readString();
        this.latitude = parcel.readFloat();
        this.longitude = parcel.readFloat();
        this.description = parcel.readString();
        this.degree = parcel.readString();
        this.semester = parcel.readInt();
        this.picturePath = parcel.readString();
        //this.picture = parcel.readByteArray();TODO:Arrumar picture
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(username != null ? username:"");
        parcel.writeString(name != null ? name:"");
        parcel.writeFloat(latitude != null ? latitude:0);
        parcel.writeFloat(longitude != null ? longitude:0);
        parcel.writeString(description != null ? description:"");
        parcel.writeString(degree != null ? degree:null);
        parcel.writeInt(semester != null ? semester:1);
        parcel.writeString(picturePath != null ? picturePath:"");
        parcel.writeByteArray(picture);
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
}
