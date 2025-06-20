package knowledge.hood.door2mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import knowledge.hood.door2mart.Models.ProductResponseModel;
import knowledge.hood.door2mart.RoomDataBAse.AppDatabase;
import knowledge.hood.door2mart.RoomDataBAse.Cartdb;
import knowledge.hood.door2mart.RoomDataBAse.CartdbDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_pg extends AppCompatActivity {

    TextView product_name, prod_qnty, prod_price, prod_desc;
    ImageView product_image;
    String product_stock, product_id;
    int id;
    Button addcart, cnag, cpos;
    TextView cQuantity;

    TextView total_price, total_item;

    ShimmerFrameLayout shimm_prod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_pg);

        product_name = findViewById(R.id.product_name);
        prod_qnty = findViewById(R.id.prod_qnty);
        prod_price = findViewById(R.id.prod_price);
        prod_desc = findViewById(R.id.prod_desc);
        product_image = findViewById(R.id.product_image);

        addcart = findViewById(R.id.addcart);
        cnag = findViewById(R.id.cnag);
        cpos = findViewById(R.id.cpos);

        total_item = findViewById(R.id.total_item);
        total_price = findViewById(R.id.total_price);

        cQuantity = findViewById(R.id.cQuantity);

        shimm_prod = findViewById(R.id.shimm_prod);

        product_id = getIntent().getStringExtra("product_id");
        id = Integer.parseInt(getIntent().getStringExtra("product_id"));

        productSelected();

        Call<List<ProductResponseModel>> call = apicontroler
                .getInstance()
                .getapi()
                .product_fetch(product_id);

        call.enqueue(new Callback<List<ProductResponseModel>>() {
            @Override
            public void onResponse(Call<List<ProductResponseModel>> call, Response<List<ProductResponseModel>> response) {
                List<ProductResponseModel> data = response.body();
                Glide.with(Product_pg.this).load("https://emanational-barrel.000webhostapp.com/admin/image/" + data.get(0).getProduct_image()).into(product_image);
                product_name.setText(data.get(0).getProduct_name());
                prod_qnty.setText(data.get(0).getProduct_quantity());
                prod_price.setText(data.get(0).getProduct_price());
                prod_desc.setText(data.get(0).getProduct_description());
                product_stock = data.get(0).getProduct_stock();

                findViewById(R.id.constraintLayout12).setVisibility(View.VISIBLE);
                findViewById(R.id.st_shim).setVisibility(View.GONE);

                shimm_prod.stopShimmerAnimation();
                shimm_prod.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<ProductResponseModel>> call, Throwable t) {

            }
        });

        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Product_pg.this, "Added to cart", Toast.LENGTH_SHORT).show();
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "cart-db").allowMainThreadQueries().build();

                CartdbDao cartdbDao = db.cartdbDao();
                cartdbDao.insertcart(new Cartdb(id, cQuantity.getText().toString()));
                addcart.setVisibility(View.GONE);
                pqvisibility();
                productSelected();
            }
        });

        findViewById(R.id.prod_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Product_pg.this, Cart_pg.class);
                Product_pg.this.startActivity(intent);
            }
        });
    }

//    class bgthread extends Thread {
//        @Override
//        public void run() {
//            super.run();
//
//        }
//    }

    private void cartBarVisibile() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cart-db").allowMainThreadQueries().build();
        CartdbDao cartdbDao = db.cartdbDao();
        if(cartdbDao.getAll().size()>0) {
            findViewById(R.id.cartbar).setVisibility(View.VISIBLE);
            findViewById(R.id.cart_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Product_pg.this, Cart_pg.class);
                    Product_pg.this.startActivity(intent);
                }
            });
        }
        else if (cartdbDao.getAll().size()<=0)
        {
            findViewById(R.id.cartbar).setVisibility(View.GONE);
        }
      //  totalPriceUpdate();
    }

    private void productSelected() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cart-db").allowMainThreadQueries().build();

        CartdbDao cartdbDao = db.cartdbDao();
        List<Cartdb> cartdbs = cartdbDao.getSelected(id);
        totalPriceUpdate();
        if (cartdbs.size() > 0) {
            pqvisibility();
            addcart.setVisibility(View.GONE);
            cartBarVisibile();
            cQuantity.setText(cartdbs.get(0).item_qn);
        } else {
            List<Cartdb> cartdbs1 = cartdbDao.getAll();
            if (cartdbs1.size() > 0) {
                cartBarVisibile();
            }

        }

    }

    private void pqvisibility() {
        findViewById(R.id.pq).setVisibility(View.VISIBLE);

        cpos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cQuantity.setText(String.valueOf(Integer.parseInt(cQuantity.getText() + "") + 1));
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "cart-db").allowMainThreadQueries().build();
                CartdbDao cartdbDao = db.cartdbDao();
                cartdbDao.update(new Cartdb(id, cQuantity.getText().toString()));
                total_price.setText((Integer.parseInt(total_price.getText().toString())+Integer.parseInt(prod_price.getText().toString()))+"");
           //     totalPriceUpdate();
            }
        });
        cnag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "cart-db").allowMainThreadQueries().build();
                CartdbDao cartdbDao = db.cartdbDao();

                if (Integer.parseInt(cQuantity.getText() + "") > 1) {
                    cQuantity.setText(String.valueOf(Integer.parseInt(cQuantity.getText() + "") - 1));
                    cartdbDao.update(new Cartdb(id, cQuantity.getText().toString()));
                    total_price.setText((Integer.parseInt(total_price.getText().toString())-Integer.parseInt(prod_price.getText().toString()))+"");
                  //  totalPriceUpdate();
                }
                else if (Integer.parseInt(cQuantity.getText() + "") <= 1) {
                    cartdbDao.delete(new Cartdb(id, cQuantity.getText().toString()));
                    total_price.setText((Integer.parseInt(total_price.getText().toString())-Integer.parseInt(prod_price.getText().toString()))+"");
                    addcart.setVisibility(View.VISIBLE);
                    findViewById(R.id.pq).setVisibility(View.GONE);
                    cartBarVisibile();
                }

            }
        });
    }

    private void totalPriceUpdate() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cart-db").allowMainThreadQueries().build();

        CartdbDao cartdbDao = db.cartdbDao();
        List<Cartdb> cartdbs = cartdbDao.getAll();

        int i;
        total_item.setText(cartdbs.size() + "");
        total_price.setText("0");
        if (cartdbs.size() != 0)
            for (i = 0; cartdbs.size() > i; i++) {
                Call<List<ProductResponseModel>> call = apicontroler
                        .getInstance()
                        .getapi()
                        .product_fetch(String.valueOf(cartdbs.get(i).id));
                int finalI = i;
                call.enqueue(new Callback<List<ProductResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductResponseModel>> call, Response<List<ProductResponseModel>> response) {
                        List<ProductResponseModel> data = response.body();
                        total_price.setText(Integer.parseInt(total_price.getText() + "") + Integer.parseInt(cartdbs.get(finalI).item_qn) * Integer.parseInt(data.get(0).getProduct_price()) + "");
                    }

                    @Override
                    public void onFailure(Call<List<ProductResponseModel>> call, Throwable t) {
                        Toast.makeText(Product_pg.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }
    }

    @Override
    public void onResume() {
        super.onResume();
        shimm_prod.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        shimm_prod.stopShimmerAnimation();
        super.onPause();
    }
}