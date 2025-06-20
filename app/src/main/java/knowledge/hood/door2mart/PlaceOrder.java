package knowledge.hood.door2mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import knowledge.hood.door2mart.Models.OrderModel;
import knowledge.hood.door2mart.Models.UserModel;
import knowledge.hood.door2mart.RoomDataBAse.AppDatabase;
import knowledge.hood.door2mart.RoomDataBAse.Cartdb;
import knowledge.hood.door2mart.RoomDataBAse.CartdbDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrder extends AppCompatActivity {
    String bill_total, user_id, delivary_address;
    int item_count;

    TextView order_name, order_address, order_pin, mobile;
    String product_ids;
    String product_qnt;

    TextView bill_amount;

    TextView order_id_disp;

    EditText input_name, input_house, input_road, input_city, input_pincode;

    Button confirm_btn, cont_change, adrs_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        order_name=  findViewById(R.id.name);
        order_address = findViewById(R.id.order_address);
        order_pin = findViewById(R.id.order_pin);

        input_name=findViewById(R.id.input_name);
        input_house= findViewById(R.id.input_house);
        input_road=findViewById(R.id.input_road);
        input_city=findViewById(R.id.input_city);
        input_pincode=findViewById(R.id.input_pincode);

        order_id_disp = findViewById(R.id.order_id_disp);

        mobile= findViewById(R.id.mobile);

        adrs_change= findViewById(R.id.adrs_change);
        cont_change= findViewById(R.id.cont_change);
        confirm_btn = findViewById(R.id.confirm_btn);

        bill_amount = findViewById(R.id.bill_amount);

        SharedPreferences sharedPreferences = getSharedPreferences("credential", MODE_PRIVATE);

        if (sharedPreferences.contains("user_id")) {
            user_id = sharedPreferences.getString("user_id", "0");
            mobile.setText(sharedPreferences.getString("user_mobile","000000000").toString());
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginUser.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getApplicationContext().startActivity(intent);
        }

        getUserDetails(user_id);

        bill_total = getIntent().getStringExtra("total");

        bill_amount.setText("\u20b9"+bill_total);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cart-db").allowMainThreadQueries().build();

        CartdbDao cartdbDao = db.cartdbDao();
        List<Cartdb> cartdbs = cartdbDao.getAll();

        product_ids ="";
        product_qnt = "";

        item_count = cartdbs.size();
        for (int i = 0; i < item_count; i++) {
            product_ids=product_ids+(String.valueOf(cartdbs.get(i).id)+",");
            product_qnt=product_qnt+(String.valueOf(cartdbs.get(i).item_qn)+",");
        }
        product_ids=product_ids+"/";
        product_qnt= product_qnt+"/";

        findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!order_name.getText().toString().isEmpty() && !order_address.getText().toString().isEmpty() && !order_pin.getText().toString().isEmpty()){
                   if(order_pin.getText().toString().equals("700061") || order_pin.getText().toString().equals("700062")) {
                       Call<List<OrderModel>> order_call = apicontroler
                               .getInstance()
                               .getapi()
                               .place_order(user_id, order_address.getText().toString() + order_pin.getText().toString(), bill_total, product_ids, product_qnt);

                       order_call.enqueue(new Callback<List<OrderModel>>() {
                           @Override
                           public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                               List<OrderModel> data = response.body();
                               if (data != null) {
                                   findViewById(R.id.done_layout).setVisibility(View.VISIBLE);
                                   confirm_btn.setEnabled(false);
                                   cont_change.setEnabled(false);
                                   adrs_change.setEnabled(false);
                                   order_id_disp.setText("Order ID " + data.get(0).getOrder_id());
                                   AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                           AppDatabase.class, "cart-db").allowMainThreadQueries().build();

                                   CartdbDao cartdbDao = db.cartdbDao();
                                   List<Cartdb> cartdbs = cartdbDao.getAll();
                                   for (int i = 0; i < cartdbs.size(); i++) {
                                       cartdbDao.delete(new Cartdb(cartdbs.get(i).id, cartdbs.get(i).item_qn));
                                   }
                               }
                               //  res_order.setText(String.valueOf(data.get(0).getOrder_id()));
                               else {
                                   Toast.makeText(PlaceOrder.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                               }
                               //   res_order.setText("Order not processed");
                           }

                           @Override
                           public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                               //   res_order.setText(t.toString());
                           }
                       });

                   }
                   else
                       Toast.makeText(PlaceOrder.this, "Sorry !!Address out of Serivice area", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(PlaceOrder.this, "Sorry !! Fill Details first", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.card_order).setVisibility(View.GONE);
                confirm_btn.setEnabled(true);
                cont_change.setEnabled(true);
                adrs_change.setEnabled(true);
                order_name.setText(input_name.getText().toString());
                order_address.setText(input_house.getText()+", "+input_road.getText()+", "+input_city.getText());
                order_pin.setText(input_pincode.getText().toString());
                input_name.setText("");
                input_house.setText("");
                input_road.setText("");
                input_city.setText("");
                input_pincode.setText("");
                Toast.makeText(PlaceOrder.this, "Details Changed Succesfully", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.card_order).setVisibility(View.GONE);
                confirm_btn.setEnabled(true);
                cont_change.setEnabled(true);
                adrs_change.setEnabled(true);
                input_name.setText("");
                input_house.setText("");
                input_road.setText("");
                input_city.setText("");
                input_pincode.setText("");
            }
        });

        findViewById(R.id.button_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.done_layout).setVisibility(View.GONE);
                confirm_btn.setEnabled(true);
                cont_change.setEnabled(true);
                adrs_change.setEnabled(true);

                startActivity(new Intent(PlaceOrder.this,ViewOrder.class));
            }
        });

        findViewById(R.id.cont_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlaceOrder.this, "Soryy !! You can not Change Contact Number", Toast.LENGTH_SHORT).show();
            }
        });
//
//        Call<List<PlaceOrderModel>>  call_p_order = apicontroler
//                .getInstance()
//                .getapi()
//                .place_order(user_id,delivary_address,bill_total,product_ids,product_qnt);
//
//        call_p_order.enqueue(new Callback<List<PlaceOrderModel>>() {
//            @Override
//            public void onResponse(Call<List<PlaceOrderModel>> call, Response<List<PlaceOrderModel>> response) {
//                List<PlaceOrderModel> data = response.body();
//                res_order.setText(data.get(0).getPlaceOrderResponse().toString());
//            }
//
//            @Override
//            public void onFailure(Call<List<PlaceOrderModel>> call, Throwable t) {
//                res_order.setText(t.toString());
//            }
//        });
//        Call<PlaceOrderModel> place_order = apicontroler
//                                            .getInstance()
//                                            .getapi()
//                                            .place_order(user_id.toString(),"12/23 Sp ROAD",mrp_total.toString(),product_ids,product_qnt);
//        place_order.enqueue(new Callback<PlaceOrderModel>() {
//            @Override
//            public void onResponse(Call<PlaceOrderModel> call, Response<PlaceOrderModel> response) {
//                PlaceOrderModel data = response.body();
//                res_order.setText(data.getMsg());
//            }
//
//            @Override
//            public void onFailure(Call<PlaceOrderModel> call, Throwable t) {
//                res_order.setText(t.toString());
//            }
//        });

    }

    private void getUserDetails(String user_id) {

        Call<List<UserModel>> getUserdetails_call= apicontroler
                .getInstance()
                .getapi()
                .getUser_details(user_id.toString());

        getUserdetails_call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                List<UserModel> data =  response.body();
                if(data!=null)
                {
                    if(data.get(0).getUser_name()!=null && data.get(0).getUser_address()!=null && data.get(0).getUser_pincode()!=null)
                    {
                        order_name.setText(data.get(0).getUser_name().toString());
                        order_address.setText(data.get(0).getUser_address().toString());
                        order_pin.setText(data.get(0).getUser_pincode().toString());
                    }
                    else {
                        findViewById(R.id.card_order).setVisibility(View.VISIBLE);
                        confirm_btn.setEnabled(false);
                        cont_change.setEnabled(false);
                        adrs_change.setEnabled(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(PlaceOrder.this, "Failed to Connect with Server", Toast.LENGTH_SHORT).show();
            }
        });

        adrs_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.card_order).setVisibility(View.VISIBLE);
                confirm_btn.setEnabled(false);
                cont_change.setEnabled(false);
                adrs_change.setEnabled(false);
            }
        });

    }

}