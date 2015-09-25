package labex.feevale.br.looky.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by 0126128 on 08/06/2015.
 */
public class User implements Serializable, Parcelable{

    private static final long serialVersionUID = 6844993266929805511L;
    public static final String GOOGLE = "GOOGLE", FACEBOOK = "FACEBOOK";
    public static final char COMPLETE = 'C', PENDING = 'P';

    public Long id;
    @Expose
    public String username;
    @Expose
    public String name;
    @Expose
    public Float latitude;
    @Expose
    public Float longitude;
    @Expose
    public String description;
    @Expose
    public String degree;
    @Expose
    public Long degreeID;
    @Expose
    public Integer semester;
    @Expose
    public String picturePath;
    public byte[] picture;
    @Expose
    private String accountID;
    @Expose
    private String token;
    @Expose
    private String deviceKey;


    public String accountType;
    private char profileStatus;

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
        this.token = parcel.readString();
        this.degreeID = parcel.readLong();
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
        parcel.writeString(name != null ? name : "");
        parcel.writeFloat(latitude != null ? latitude:0);
        parcel.writeFloat(longitude != null ? longitude : 0);
        parcel.writeString(description != null ? description:"");
        parcel.writeString(degree != null ? degree : null);
        parcel.writeInt(semester != null ? semester : 1);
        parcel.writeString(picturePath != null ? picturePath : "");
        parcel.writeString(token != null ? token:"");
        parcel.writeLong(degreeID);
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getDegreeID() {
        if(this.getDegreeID() == null)
            this.degreeID = 0L;
        return degreeID;
    }

    public void setDegreeID(Long degreeID) {
        this.degreeID = degreeID;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public char getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(char profileStatus) {
        this.profileStatus = profileStatus;
    }
}
