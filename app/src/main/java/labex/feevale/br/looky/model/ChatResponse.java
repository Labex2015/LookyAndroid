package labex.feevale.br.looky.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by grimmjowjack on 9/24/15.
 */
public class ChatResponse implements Parcelable{


    private String userFrom;
    private Long userFromID;
    private String message;
    private Date date;

    public ChatResponse() {}

    public ChatResponse(String userFrom, Long userFromID, String message, Date date) {
        this.userFrom = userFrom;
        this.userFromID = userFromID;
        this.message = message;
        this.date = date;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(userFromID);
    }
}
