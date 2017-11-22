package ws.tilda.anastasia.shoppingcartapp.view;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import ws.tilda.anastasia.shoppingcartapp.R;
import ws.tilda.anastasia.shoppingcartapp.model.Product;

public class CreateProductActivity extends AppCompatActivity {
    public static final String PRODUCTS = "products";
    public static final String CURRENT_PRODUCT_CODE = "current_product_code";
    public static final String NEW_PRODUCT_NAME = "new_product_name";
    public static final String NEW_PRODUCT_PRICE = "new_product_price";
    public static final int RESULT_CODE = 1;

    private TextView mCodeFieldTextView;
    private TextView mNewProductCodeTextView;
    private EditText mNewProductNameEditText;
    private EditText mNewProductPriceEditText;

    private List<Product> mProducts;
    private int mProductCode;
    private String mNewProductName;
    private double mNewProductPrice;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        adjustToSoftKeybord();

        ActionBar mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setTitle("New Product Details");
        mActionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null && savedInstanceState.containsKey(PRODUCTS)
                && savedInstanceState.containsKey(CURRENT_PRODUCT_CODE)
                && savedInstanceState.containsKey(NEW_PRODUCT_NAME)
                && savedInstanceState.containsKey(NEW_PRODUCT_PRICE)) {

            mProducts = savedInstanceState.getParcelableArrayList(PRODUCTS);
            mProductCode = savedInstanceState.getInt(CURRENT_PRODUCT_CODE);
            mNewProductName = savedInstanceState.getString(NEW_PRODUCT_NAME);
            mNewProductPrice = savedInstanceState.getDouble(NEW_PRODUCT_PRICE);

        } else {
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mProducts = getIntent().getParcelableArrayListExtra(PRODUCTS);
                mProductCode = getIntent().getIntExtra(CURRENT_PRODUCT_CODE, 0);
            }

        }

        mCodeFieldTextView = findViewById(R.id.new_product_code_field);
        mNewProductCodeTextView = findViewById(R.id.new_product_code);
        mNewProductNameEditText = findViewById(R.id.new_product_name);
        mNewProductPriceEditText = findViewById(R.id.new_product_price);
        Button newProductSaveButton = findViewById(R.id.new_product_save_button);


        mNewProductNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (mNewProductNameEditText.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), R.string.warning_toast_if_input_isEmpty,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mNewProductPriceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (mNewProductPriceEditText.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), R.string.warning_toast_if_input_isEmpty,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mNewProductCodeTextView.setText(Integer.toString(mProductCode));

        newProductSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!mNewProductNameEditText.getText().toString().equals("")) && (!mNewProductPriceEditText.getText().toString().equals(""))) {

                    mNewProductName = mNewProductNameEditText.getText().toString();
                    mNewProductPrice = parseDoubleOnCheckedString(mNewProductPriceEditText.getText().toString());

                    Product newProduct = new Product(mProductCode, mNewProductName, mNewProductPrice);

                    Intent intent = new Intent(getBaseContext(), ShoppingCartActivity.class);
                    intent.putExtra(ShoppingCartActivity.NEW_PRODUCT, newProduct);

                    setResult(RESULT_CODE, intent);

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.some_empty_fields_warning_toast,
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(PRODUCTS, (ArrayList<? extends Parcelable>) mProducts);
        outState.putInt(CURRENT_PRODUCT_CODE, mProductCode);
        outState.putString(NEW_PRODUCT_NAME, mNewProductName);
        outState.putDouble(NEW_PRODUCT_PRICE, mNewProductPrice);

        super.onSaveInstanceState(outState);
    }

    private double parseDoubleOnCheckedString(String string) {
        if (string != null && string.length() > 0) {
            try {
                return Double.parseDouble(string);
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        } else return 0;
    }


    private void adjustToSoftKeybord() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

}

