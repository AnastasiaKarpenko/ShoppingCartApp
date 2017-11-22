package ws.tilda.anastasia.shoppingcartapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Parcelable {
    private List<Product> mProductList;

    public ShoppingCart() {
        mProductList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public void addFirst(Product product) {
        mProductList.add(0, product);
    }

    public double getSumPrice() {
        double sumPrice = 0.0;
        for (Product product : mProductList) {
            sumPrice += product.getPrice();
        }
        return new BigDecimal(sumPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mProductList);
    }

    protected ShoppingCart(Parcel in) {
        this.mProductList = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Parcelable.Creator<ShoppingCart> CREATOR = new Parcelable.Creator<ShoppingCart>() {
        @Override
        public ShoppingCart createFromParcel(Parcel source) {
            return new ShoppingCart(source);
        }

        @Override
        public ShoppingCart[] newArray(int size) {
            return new ShoppingCart[size];
        }
    };
}
