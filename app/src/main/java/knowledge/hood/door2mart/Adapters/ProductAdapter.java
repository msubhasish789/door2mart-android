package knowledge.hood.door2mart.Adapters;


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

import knowledge.hood.door2mart.Models.ProductResponseModel;
import knowledge.hood.door2mart.Product_pg;
import knowledge.hood.door2mart.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<ProductResponseModel> data;

    public ProductAdapter(List<ProductResponseModel> data) {
        this.data = data;
    }

    public ProductAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_rec_sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load("https://emanational-barrel.000webhostapp.com/admin/image/"+data.get(position).getProduct_image()).into(holder.cat_pro_image);
        holder.cat_pro_name.setText(data.get(position).getProduct_name());
        holder.cat_pro_qnty.setText(data.get(position).getProduct_quantity());
        holder.cat_pro_price.setText(data.get(position).getProduct_price());

        String product_id = data.get(position).getProduct_id();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(holder.cat_pro_name.getContext(), Product_pg.class);
                intent.putExtra("product_id", product_id);
                holder.cat_pro_name.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cat_pro_name, cat_pro_qnty, cat_pro_price;
        ImageView cat_pro_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cat_pro_name=itemView.findViewById(R.id.cat_pro_name);
            cat_pro_qnty=itemView.findViewById(R.id.cat_pro_qnty);
            cat_pro_price= itemView.findViewById(R.id.cat_pro_price);
            cat_pro_image=itemView.findViewById(R.id.cat_pro_image);
        }
    }
}
