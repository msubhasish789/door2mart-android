package knowledge.hood.door2mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import knowledge.hood.door2mart.Adapters.ProductAdapter;
import knowledge.hood.door2mart.Models.ProductResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_list extends AppCompatActivity {
    RecyclerView pro_list_recview;
    TextView cat_txtv;

    ShimmerFrameLayout catList_shimmer_view_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        cat_txtv= findViewById(R.id.cat_txtv);

        catList_shimmer_view_container = findViewById(R.id.catList_shimmer_view_container);

        pro_list_recview=findViewById(R.id.pro_list_recview);
        pro_list_recview.setLayoutManager(new GridLayoutManager(this,3));

        String cat_id = getIntent().getStringExtra("cat_id");
        cat_txtv.setText(getIntent().getStringExtra("cat_name"));;


        Call<List<ProductResponseModel>> call= apicontroler.getInstance().getapi().cat_pro_fetch(cat_id);

        call.enqueue(new Callback<List<ProductResponseModel>>() {
            @Override
            public void onResponse(Call<List<ProductResponseModel>> call, Response<List<ProductResponseModel>> response) {
                List<ProductResponseModel> data= response.body();
                if(data!=null){
                ProductAdapter productAdapter= new ProductAdapter(data);
                pro_list_recview.setAdapter(productAdapter);

                    catList_shimmer_view_container.stopShimmerAnimation();
                    catList_shimmer_view_container.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<ProductResponseModel>> call, Throwable t) {
                Toast.makeText(Product_list.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        catList_shimmer_view_container.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        catList_shimmer_view_container.stopShimmerAnimation();
        super.onPause();
    }
}