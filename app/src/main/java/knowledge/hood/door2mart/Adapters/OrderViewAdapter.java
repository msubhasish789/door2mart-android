package knowledge.hood.door2mart.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import knowledge.hood.door2mart.Models.OrderModel;
import knowledge.hood.door2mart.R;
import knowledge.hood.door2mart.ViewOrder;
import knowledge.hood.door2mart.apicontroler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewAdapter.ViewOrder> {
    List<OrderModel> data;
    RecyclerView order_RecView;
    TextView order_txtv;
    Context context;

    public OrderViewAdapter(List<OrderModel> data, RecyclerView order_RecView, TextView order_txtv) {
        this.data = data;
        this.order_RecView = order_RecView;
        this.order_txtv = order_txtv;
    }

    public OrderViewAdapter() {
    }

    @NonNull
    @Override
    public ViewOrder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_layout,parent,false);
        return new ViewOrder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewOrder holder, int position) {
            holder.order_bill_id.setText("order id "+data.get(position).getOrder_id());
            holder.payment_mode.setText(data.get(position).getPayment_status().toString());
            holder.Order_bill_total.setText("\u20b9"+data.get(position).getBill_total());
            holder.order_dateTime.setText(data.get(position).getOrder_time());
            if(data.get(position).getOrder_status().equals("0"))
            {
                holder.order_status.setTextColor(Color.RED);
                holder.order_status.setText("Canceled");
            }
            else if(data.get(position).getOrder_status().equals("1")){
                holder.order_status.setTextColor(Color.parseColor("#FF9800"));
                holder.order_status.setText("Pending");
            }
            else if(data.get(position).getOrder_status().equals("2")){
                holder.order_status.setTextColor(Color.GRAY);
                holder.order_status.setText("Out of Delivery");
            }
            else {
                holder.order_status.setTextColor(Color.parseColor("#FF237101"));
                holder.order_status.setText("Delivered");
            }

            String order_id = data.get(position).getOrder_id().toString();

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Call<List<OrderModel>> orderdetails_call = apicontroler
                            .getInstance()
                            .getapi()
                            .getOrder_details(order_id.toString());

                    orderdetails_call.enqueue(new Callback<List<OrderModel>>() {
                        @Override
                        public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                            List<OrderModel> details_data = response.body();

                            if(details_data!=null){
                                order_RecView.setAdapter(new OrderDetailsViewAdapter(details_data,order_txtv));
                                order_RecView.setLayoutManager(new LinearLayoutManager(context));
                            }
                        }

                        @Override
                        public void onFailure(Call<List<OrderModel>> call, Throwable t) {

                        }
                    });
                }
            });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewOrder extends RecyclerView.ViewHolder {
        TextView order_bill_id,payment_mode, Order_bill_total, order_dateTime, order_status;
        public ViewOrder(@NonNull View itemView) {
            super(itemView);
            order_bill_id = itemView.findViewById(R.id.order_bill_id);
            payment_mode = itemView.findViewById(R.id.payment_mode);
            Order_bill_total = itemView.findViewById(R.id.Order_bill_total);
            order_dateTime =  itemView.findViewById(R.id.order_dateTime);
            order_status = itemView.findViewById(R.id.order_status);
        }
    }
}
