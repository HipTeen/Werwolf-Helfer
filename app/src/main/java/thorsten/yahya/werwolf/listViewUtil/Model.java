package thorsten.yahya.werwolf.listViewUtil;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HipTeen on 29.07.2015.
 */
public class Model implements Parcelable {

    private String title;
    private int count;

    public Model(Parcel parcel) {
        this.title = parcel.readString();
        this.count = parcel.readInt();
    }

    public Model(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(count);
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<Model> CREATOR = new Parcelable.Creator<Model>() {
        public Model createFromParcel(Parcel pc) {
            return new Model(pc);
        }
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}
