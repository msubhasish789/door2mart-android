package knowledge.hood.door2mart.Adapters;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import knowledge.hood.door2mart.Models.OrderModel;
import knowledge.hood.door2mart.R;

public class OrderDetailsViewAdapter extends RecyclerView.Adapter<OrderDetailsViewAdapter.ViewHolder> {
    List<OrderModel> details_data;
    TextView order_txtv;

    public OrderDetailsViewAdapter(List<OrderModel> details_data, TextView order_txtv) {
        this.details_data = details_data;
        this.order_txtv = order_txtv;
    }

    @NonNull
    @Override
    public OrderDetailsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oder_details_rec_sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsViewAdapter.ViewHolder holder, int position) {
        order_txtv.setText("id "+details_data.get(position).getOrder_id());
        holder.order_details_p_name.setText(details_data.get(position).getProduct_name());
        holder.order_details_p_qnty.setText("Qnty. "+details_data.get(position).getOrder_qnty());
        holder.order_details_p_mas.setText(details_data.get(position).getProduct_quantity());
        holder.order_details_p_price.setText(details_data.get(position).getProduct_price());
        Glide.with(holder.itemView.getContext()).load("https://emanational-barrel.000webhostapp.com/admin/image/"+details_data.get(position).getProduct_image()).into(holder.order_details_image);

    }

    @Override
    public int getItemCount() {
        return details_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView order_details_image;
        TextView order_details_p_name, order_details_p_mas,order_details_p_price, order_details_p_qnty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_details_image = itemView.findViewById(R.id.order_details_image);
            order_details_p_name =itemView.findViewById(R.id.order_details_p_name);
            order_details_p_mas = itemView.findViewById(R.id.order_details_p_mas);
            order_details_p_price = itemView.findViewById(R.id.order_details_p_price);
            order_details_p_qnty= itemView.findViewById(R.id.order_details_p_qnty);
        }
    }
}
