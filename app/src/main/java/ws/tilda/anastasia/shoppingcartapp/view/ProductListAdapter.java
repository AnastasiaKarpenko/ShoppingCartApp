package ws.tilda.anastasia.shoppingcartapp.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ws.tilda.anastasia.shoppingcartapp.R;
import ws.tilda.anastasia.shoppingcartapp.model.Product;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private List<Product> mProducts;

    public ProductListAdapter(List<Product> products) {
        mProducts = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product,
                parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = mProducts.get(position);

        holder.mProductName.setText(product.getName());
        holder.mProductCode.setText(Integer.toString(product.getCode()));
        holder.mProductPrice.setText(Double.toString(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        if (mProducts != null) {
            return mProducts.size();
        } else {
            return 0;
        }
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView mProductName;
        ImageView mProductCodeIcon;
        TextView mProductCode;
        ImageView mProductPriceIcon;
        TextView mProductPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);

            mProductName = itemView.findViewById(R.id.new_product_name);
            mProductCodeIcon = itemView.findViewById(R.id.code_icon);
            mProductCode = itemView.findViewById(R.id.product_code);
            mProductPriceIcon = itemView.findViewById(R.id.price_icon);
            mProductPrice = itemView.findViewById(R.id.product_price);
        }
    }
}
