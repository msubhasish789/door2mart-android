package knowledge.hood.door2mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import knowledge.hood.door2mart.Adapters.CategoryAdapter;
import knowledge.hood.door2mart.Models.CategoryResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category extends AppCompatActivity {
    RecyclerView Cat_RecView;
    FloatingActionButton home_ftb;

    ShimmerFrameLayout cat_shimmer_view_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        home_ftb=findViewById(R.id.cat_home_ftb);

        cat_shimmer_view_container = findViewById(R.id.cat_shimmer_view_container);

        Cat_RecView = findViewById(R.id.Cat_RecView);
        Cat_RecView.setLayoutManager(new GridLayoutManager(this,3));


        processdata();

        findViewById(R.id.cat_cart_ftb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Cart_pg.class));
            }
        });

        findViewById(R.id.cat_order_ftb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewOrder.class));
            }
        });

        home_ftb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

    public void processdata()
    {
        Call<List<CategoryResponseModel>> call= apicontroler
                .getInstance()
                .getapi()
                .getdata();

        call.enqueue(new Callback<List<CategoryResponseModel>>() {
            @Override
            public void onResponse(Call<List<CategoryResponseModel>> call, Response<List<CategoryResponseModel>> response) {
                List<CategoryResponseModel> data= response.body();
                if(data != null) {
                    CategoryAdapter categoryAdapter = new CategoryAdapter(data);
                    Cat_RecView.setAdapter(categoryAdapter);

                    cat_shimmer_view_container.stopShimmerAnimation();
                    cat_shimmer_view_container.setVisibility(View.GONE);
                }
                else
                    Toast.makeText(Category.this, "Sorry, Something went Wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<CategoryResponseModel>> call, Throwable t) {
                Toast.makeText(Category.this, "Failed to Connect With Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        cat_shimmer_view_container.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        cat_shimmer_view_container.stopShimmerAnimation();
        super.onPause();
    }
}