package ws.tilda.anastasia.shoppingcartapp.view;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ws.tilda.anastasia.shoppingcartapp.R;
import ws.tilda.anastasia.shoppingcartapp.model.Product;
import ws.tilda.anastasia.shoppingcartapp.model.ShoppingCart;

public class ShoppingCartActivity extends AppCompatActivity {
    public static final String PRODUCTS = "products";
    public static final String CURRENT_PRODUCT_CODE = "current_product_code";
    public static final String NEW_PRODUCT = "new product";
    public static final int REQUEST_CODE = 1;


    private ShoppingCart mShoppingCart;
    private List<Product> mProducts;
    private int mCurrentProductCode;
    private Product mNewProduct;

    private EditText mProductCodeInput;
    private RecyclerView mProductRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        adjustToSoftKeybord();

        mShoppingCart = new ShoppingCart();

        mProducts = mShoppingCart.getProductList();

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle(R.string.actionbar_title);

        mProductCodeInput = findViewById(R.id.product_code_input);
        mProductRecyclerView = findViewById(R.id.product_list_recycler_view);

        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProductCodeInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try {
                        mCurrentProductCode = Integer.parseInt(mProductCodeInput.getText().toString());
                        reactOnCurrentProductCode(mCurrentProductCode);

                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        Toast.makeText(getApplicationContext(), R.string.warning_toast_if_input_isEmpty,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        if (savedInstanceState != null) {
            mProducts = savedInstanceState.getParcelableArrayList(PRODUCTS);
            mCurrentProductCode = savedInstanceState.getInt(CURRENT_PRODUCT_CODE);
            updateUI(mProducts);
        } else {
            updateUI(mProducts);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode) {
            mNewProduct = data.getParcelableExtra(NEW_PRODUCT);
            mShoppingCart.addFirst(mNewProduct);
            updateUI(mShoppingCart.getProductList());
            mProductCodeInput.setText("");
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mProducts = savedInstanceState.getParcelableArrayList(PRODUCTS);
            mCurrentProductCode = savedInstanceState.getInt(CURRENT_PRODUCT_CODE);
            updateUI(mProducts);
        }
    }

    private void reactOnCurrentProductCode(int code) {
        if (isCurrentProductCodeNew(code)) {
            startCreateProductActivity();
        } else {
            alertUserAboutError();
        }

    }

    private void adjustToSoftKeybord() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private boolean isCurrentProductCodeNew(int code) {
        for (Product product : mProducts) {
            if (code == product.getCode()) {
                return false;
            }
        }
        return true;
    }


    private void updateUI(List<Product> products) {
        if (products != null) {
            setupAdapter(products);
        } else {
            Toast.makeText(this, "There are no products yet", Toast.LENGTH_LONG).show();
        }
    }

    private void setupAdapter(List<Product> products) {
        mProductRecyclerView.setAdapter(new ProductListAdapter(products));
    }

    private void startCreateProductActivity() {
        Intent intent = new Intent(this, CreateProductActivity.class);
        intent.putExtra(CreateProductActivity.CURRENT_PRODUCT_CODE, mCurrentProductCode);
        intent.putParcelableArrayListExtra(CreateProductActivity.PRODUCTS, (ArrayList<? extends Parcelable>) mProducts);

        startActivityForResult(intent, REQUEST_CODE);
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_shopping_cart, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_shopping_cart:
//                Open sum - up - and - close - dialog
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(PRODUCTS, (ArrayList<? extends Parcelable>) mProducts);
        outState.putInt(CURRENT_PRODUCT_CODE, mCurrentProductCode);

        super.onSaveInstanceState(outState);
    }


}
