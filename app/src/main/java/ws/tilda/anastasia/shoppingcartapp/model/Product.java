package ws.tilda.anastasia.shoppingcartapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int mCode;
    private String mName;
    private double mPrice;

    public Product(int code, String name, double price) {
        mCode = code;
        mName = name;
        mPrice = price;
    }

    public Product() {

    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mCode);
        dest.writeString(this.mName);
        dest.writeDouble(this.mPrice);
    }

    protected Product(Parcel in) {
        this.mCode = in.readInt();
        this.mName = in.readString();
        this.mPrice = in.readDouble();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
