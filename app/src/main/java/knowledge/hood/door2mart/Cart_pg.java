package knowledge.hood.door2mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import knowledge.hood.door2mart.Adapters.CartAdapter;
import knowledge.hood.door2mart.Models.ProductResponseModel;
import knowledge.hood.door2mart.RoomDataBAse.AppDatabase;
import knowledge.hood.door2mart.RoomDataBAse.Cartdb;
import knowledge.hood.door2mart.RoomDataBAse.CartdbDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart_pg extends AppCompatActivity {
    RecyclerView cart_recview;
    TextView mrp_total, deliv_total,bill_total, total;

    ConstraintLayout empty_cart_layout, cart_layoutconst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_pg);

        mrp_total= findViewById(R.id.mrp_total);
        deliv_total= findViewById(R.id.deliv_total);
        bill_total = findViewById(R.id.bill_total);
        total= findViewById(R.id.total);

        empty_cart_layout= findViewById(R.id.empty_cart_layout);
        cart_layoutconst = findViewById(R.id.cart_layoutconst);

        cart_recview= findViewById(R.id.cart_recview);

        cart_recview.setLayoutManager(new LinearLayoutManager(this));



        findViewById(R.id.shopNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cart_pg.this,MainActivity.class));
            }
        });
        findViewById(R.id.homeBtnCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cart_pg.this,MainActivity.class));
            }
        });

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cart-db").allowMainThreadQueries().build();

        CartdbDao cartdbDao = db.cartdbDao();
        List<Cartdb> cartdbs = cartdbDao.getAll();

        if (cartdbs.size()!=0)
        {
            CartAdapter cartAdapter = new CartAdapter(cartdbs,mrp_total,bill_total,deliv_total,empty_cart_layout, cart_layoutconst, total);
            cart_recview.setAdapter(cartAdapter);

        }
        else if(cartdbs.size()==0)
        {
            empty_cart_layout.setVisibility(View.VISIBLE);
            cart_layoutconst.setVisibility(View.GONE);
        }


        totalPriceUpdate();
        bill_total.setText((Integer.parseInt(mrp_total.getText()+"")+Integer.parseInt(deliv_total.getText()+""))+"");

        findViewById(R.id.order_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart_pg.this,PlaceOrder.class);
                intent.putExtra("total", total.getText().toString());
                Cart_pg.this.startActivity(intent);
            }
        });
    }
    private void totalPriceUpdate() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cart-db").allowMainThreadQueries().build();

        CartdbDao cartdbDao = db.cartdbDao();
        List<Cartdb> cartdbs = cartdbDao.getAll();

        int i;
        mrp_total.setText("0");
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
                        mrp_total.setText(Integer.parseInt(mrp_total.getText() + "") + Integer.parseInt(cartdbs.get(finalI).item_qn) * Integer.parseInt(data.get(0).getProduct_price()) + "");
                        bill_total.setText((Integer.parseInt(mrp_total.getText()+"")+Integer.parseInt(deliv_total.getText()+""))+"");
                        total.setText(bill_total.getText()+"");
                        empty_cart_layout.setVisibility(View.GONE);
                        cart_layoutconst.setVisibility(View.VISIBLE);

                    }
                    @Override
                    public void onFailure(Call<List<ProductResponseModel>> call, Throwable t) {

                    }
                });
            }
    }
}