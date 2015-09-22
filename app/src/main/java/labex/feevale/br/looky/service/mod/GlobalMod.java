package labex.feevale.br.looky.service.mod;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.utils.UserMock;

/**
 * Created by grimmjowjack on 9/21/15.
 */
public class GlobalMod implements Parcelable{

    public Long idRequestHelp;
    public String title;
    public String description;
    public String params;
    public User user;

    public GlobalMod() {
    }

    public GlobalMod(Long idRequestHelp, String title, String description, String params, User user) {
        this.idRequestHelp = idRequestHelp;
        this.title = title;
        this.description = description;
        this.params = params;
        this.user = user;
    }

    public GlobalMod(Parcel parcel) {
        this.idRequestHelp = parcel.readLong();
        this.title = parcel.readString();
        this.description = parcel.readString();
        this.params = parcel.readString();
        this.user = parcel.readParcelable(User.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(idRequestHelp);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(params);
        parcel.writeParcelable(user, i);
    }
}
