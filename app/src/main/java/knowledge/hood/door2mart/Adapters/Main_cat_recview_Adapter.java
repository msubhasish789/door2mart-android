package knowledge.hood.door2mart.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import knowledge.hood.door2mart.Models.CategoryResponseModel;
import knowledge.hood.door2mart.Models.ProductResponseModel;
import knowledge.hood.door2mart.Product_list;
import knowledge.hood.door2mart.R;
import knowledge.hood.door2mart.apicontroler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_cat_recview_Adapter extends RecyclerView.Adapter<Main_cat_recview_Adapter.ViewHolder> {
    List<CategoryResponseModel> data;


    public Main_cat_recview_Adapter(List<CategoryResponseModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_cat_rec_sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sample_cat_name.setText(data.get(position).getCategory_name());
        String cat_id= data.get(position).getCategory_id();
        holder.see_all_cat_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(holder.sample_cat_name.getContext(), Product_list.class);
                intent.putExtra("cat_id", cat_id);
                intent.putExtra("cat_name", holder.sample_cat_name.getText());
                holder.sample_cat_name.getContext().startActivity(intent);
            }
        });

        Call<List<ProductResponseModel>> call= apicontroler
                .getInstance()
                .getapi()
                .cat_pro_fetch(cat_id);

        call.enqueue(new Callback<List<ProductResponseModel>>() {
            @Override
            public void onResponse(Call<List<ProductResponseModel>> call, Response<List<ProductResponseModel>> response) {
                List<ProductResponseModel> data= response.body();
                if(data!=null){
                ProductAdapter productAdapter= new ProductAdapter(data);
                holder.sample_product_recview.setLayoutManager(new LinearLayoutManager(holder.sample_product_recview.getContext(),LinearLayoutManager.HORIZONTAL,false));
                holder.sample_product_recview.setAdapter(productAdapter); }
            }

            @Override
            public void onFailure(Call<List<ProductResponseModel>> call, Throwable t) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sample_cat_name,see_all_cat_pro;
        RecyclerView sample_product_recview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sample_cat_name= itemView.findViewById(R.id.sample_cat_name);
            sample_product_recview= itemView.findViewById(R.id.sample_product_recview);
            see_all_cat_pro =itemView.findViewById(R.id.see_all_cat_pro);
        }
    }
}
