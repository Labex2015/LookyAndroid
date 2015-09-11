package labex.feevale.br.looky.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by grimmjowjack on 8/25/15.
 */
public class SearchItem implements Serializable, Parcelable{

    private static final long serialVersionUID = -1250594727873157302L;

    @Expose
    public int max;
    @Expose
    public int position;
    @Expose
    public String param;
    @Expose
    public Long subject;

    public SearchItem() {
    }

    public SearchItem(Parcel parcel) {
        this.max = parcel.readInt();
        this.position = parcel.readInt();
        this.param = parcel.readString();
        this.subject = parcel.readLong();
    }

    public SearchItem(int max, int position, String param, Long subject) {
        this.max = max;
        this.position = position;
        this.param = param;
        this.subject = subject;
    }

    public SearchItem resetPositions(String param, Long subject){
        resetPositions(param);
        this.subject = subject;
        return this;
    }

    public SearchItem resetPositions(String param){
        this.max = 10;
        this.position = 0;
        this.param = param;
        return this;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(max);
        parcel.writeInt(position);
        parcel.writeString(param);
        parcel.writeLong(subject != null ? subject : 0L);

    }

    public static final Parcelable.Creator<SearchItem> CREATOR
            = new Parcelable.Creator<SearchItem>() {
        public SearchItem createFromParcel(Parcel in) {
            return new SearchItem(in);
        }

        public SearchItem[] newArray(int size) {
            return new SearchItem[size];
        }
    };
}
