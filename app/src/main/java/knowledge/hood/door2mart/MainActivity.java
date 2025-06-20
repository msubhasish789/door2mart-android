package knowledge.hood.door2mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import knowledge.hood.door2mart.Adapters.BannerAdapter;
import knowledge.hood.door2mart.Adapters.Main_cat_recview_Adapter;
import knowledge.hood.door2mart.Models.BannerModel;
import knowledge.hood.door2mart.Models.CategoryResponseModel;
import knowledge.hood.door2mart.Models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton category_ftb;
    RecyclerView main_cat_recview, banner_rec_view;
    ImageView srch_btn, account;

    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addUser();

        main_cat_recview = findViewById(R.id.main_cat_recview);
        srch_btn = findViewById(R.id.srch_btn);
        account = findViewById(R.id.account);
        category_ftb = findViewById(R.id.category_ftb);
        banner_rec_view = findViewById(R.id.banner_rec_view);

        ArrayList<BannerModel> listbanner = new ArrayList<BannerModel>();
        listbanner.add(new BannerModel(R.drawable.banner1));
        listbanner.add(new BannerModel(R.drawable.banner2));

        BannerAdapter bannerAdapter = new BannerAdapter(listbanner, this);
        banner_rec_view.setAdapter(bannerAdapter);

        LinearLayoutManager bannerlayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        banner_rec_view.setLayoutManager(bannerlayout);

        findViewById(R.id.cart_ftb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Cart_pg.class);
                MainActivity.this.startActivity(intent);
            }
        });

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);


        category_ftb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Category.class));
            }
        });

        srch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Search.class));
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Account.class));
            }
        });

        findViewById(R.id.order_ftb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewOrder.class));
            }
        });

        main_cat_recview.setLayoutManager(new LinearLayoutManager(this));

        main_cat_recview_process();
    }

    public void main_cat_recview_process() {
        Call<List<CategoryResponseModel>> call = apicontroler
                .getInstance()
                .getapi()
                .getdata();

        call.enqueue(new Callback<List<CategoryResponseModel>>() {
            @Override
            public void onResponse(Call<List<CategoryResponseModel>> call, Response<List<CategoryResponseModel>> response) {
                List<CategoryResponseModel> data = response.body();
                if(data != null ) {
                    Main_cat_recview_Adapter main_cat_recview_adapter = new Main_cat_recview_Adapter(data);
                    main_cat_recview.setAdapter(main_cat_recview_adapter);
                    // Stopping Shimmer Effect's animation after data is loaded to ListView
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    banner_rec_view.setVisibility(View.VISIBLE);
                }
                else
                    Toast.makeText(MainActivity.this, "Sorry, Something went Wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<CategoryResponseModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    private void addUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("credential", MODE_PRIVATE);
        if (sharedPreferences.contains("user_mobile")) {
            String mobile = sharedPreferences.getString("user_mobile","000000000");
            if (!sharedPreferences.contains("user_id")) {
                Call<List<UserModel>> call_user = apicontroler
                        .getInstance()
                        .getapi()
                        .addUser(String.valueOf(mobile));
            //            .addUser(null, mobile, null, null, null, null);

                call_user.enqueue(new Callback<List<UserModel>>() {
                    @Override
                    public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                        List<UserModel> data = response.body();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_id", data.get(0).getUser_id());
                        editor.apply();
                    }

                    @Override
                    public void onFailure(Call<List<UserModel>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Can not Connect to the Server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }

}
