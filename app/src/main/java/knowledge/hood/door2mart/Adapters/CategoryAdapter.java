package knowledge.hood.door2mart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import knowledge.hood.door2mart.Models.CategoryResponseModel;
import knowledge.hood.door2mart.Product_list;
import knowledge.hood.door2mart.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<CategoryResponseModel> data;


    public CategoryAdapter(List<CategoryResponseModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load("https://emanational-barrel.000webhostapp.com/admin/image/"+data.get(position).getCategory_image()).into(holder.cat_image);
        holder.cat_name.setText(data.get(position).getCategory_name());
        String cat_id= data.get(position).getCategory_id();
        String cat_name = data.get(position).getCategory_name();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(holder.cat_image.getContext(), Product_list.class);
                intent.putExtra("cat_id", cat_id);
                intent.putExtra("cat_name", cat_name);
                holder.cat_image.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cat_image;
        TextView cat_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_image= itemView.findViewById(R.id.cat_image);
            cat_name=itemView.findViewById(R.id.cat_name);
        }
    }
}
