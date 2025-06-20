package knowledge.hood.door2mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import knowledge.hood.door2mart.Adapters.OrderViewAdapter;
import knowledge.hood.door2mart.Models.OrderModel;

import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrder extends AppCompatActivity {
    String user_id;
    RecyclerView order_RecView;
    TextView order_txtv;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        findViewById(R.id.blank_order).setVisibility(View.VISIBLE);

        order_RecView = findViewById(R.id.order_RecView);
        order_txtv = findViewById(R.id.order_txtv);

        swipeRefreshLayout = findViewById(R.id.reload);

        get_oreder_details();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_oreder_details();
            }
        });



        findViewById(R.id.home_ftb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
         findViewById(R.id.cart_ftb).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getApplicationContext(),Cart_pg.class));
             }
         });
         findViewById(R.id.category_ftb).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getApplicationContext(),Category.class));
             }
         });


    }

    private void get_oreder_details() {

        order_txtv.setText("View Order");
        SharedPreferences sharedPreferences = getSharedPreferences("credential", MODE_PRIVATE);
        if (sharedPreferences.contains("user_id")) {
            user_id = sharedPreferences.getString("user_id", "0");



            retrofit2.Call<List<OrderModel>> order_view_call= apicontroler
                    .getInstance()
                    .getapi()
                    .getOrder(user_id.toString());

            order_view_call.enqueue(new Callback<List<OrderModel>>() {
                @Override
                public void onResponse(retrofit2.Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                    List<OrderModel> data = response.body();
                    if(data!=null)
                    {
                        findViewById(R.id.blank_order).setVisibility(View.GONE);
                        order_RecView.setAdapter(new OrderViewAdapter(data,order_RecView,order_txtv));
                        order_RecView.setLayoutManager(new LinearLayoutManager(ViewOrder.this));
                    }
                    else
                    {
                        order_RecView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<List<OrderModel>> call, Throwable t) {
                    Toast.makeText(ViewOrder.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });

        }
        swipeRefreshLayout.setRefreshing(false);
    }
}