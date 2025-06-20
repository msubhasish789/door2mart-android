package knowledge.hood.door2mart.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import knowledge.hood.door2mart.Models.ProductResponseModel;
import knowledge.hood.door2mart.Product_pg;
import knowledge.hood.door2mart.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> //implements Filterable
{
    List<ProductResponseModel> data;
  //  ArrayList<ProductResponseModel> backup;

    public SearchAdapter(List<ProductResponseModel> data) {
        this.data = data;
    //    backup= new ArrayList<>(data);
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_rec_sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
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
//
//    @Override
//    public Filter getFilter() {
//        return filter;
//    }
//
//    Filter filter= new Filter() {                               // anonymous innerClass call
//        @Override
//        // background thread oporation
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            ArrayList<ProductResponseModel> filteredData= new ArrayList<>();
//            if(charSequence.toString().isEmpty())
//                filteredData.addAll(backup);
//            else{
//                for (ProductResponseModel obj : backup){
//                    if (obj.getProduct_name().toLowerCase().contains(charSequence.toString().toLowerCase()))
//                        filteredData.add(obj);
//                }
//            }
//            FilterResults filterResults = new FilterResults();
//            filterResults.values =filteredData;
//            return filterResults;
//        }
//
//        @Override
//        //main ui thread
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                data.clear();
//                data.addAll((ArrayList<ProductResponseModel>)filterResults.values);
//                notifyDataSetChanged();
//                backup.clear();
//        }
//    };

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
