package knowledge.hood.door2mart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;

import java.util.List;

import knowledge.hood.door2mart.Models.CartResponseModel;
import knowledge.hood.door2mart.Models.ProductResponseModel;
import knowledge.hood.door2mart.Product_pg;
import knowledge.hood.door2mart.R;
import knowledge.hood.door2mart.RoomDataBAse.AppDatabase;
import knowledge.hood.door2mart.RoomDataBAse.Cartdb;
import knowledge.hood.door2mart.RoomDataBAse.CartdbDao;
import knowledge.hood.door2mart.apicontroler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<Cartdb> data;
    TextView mrp_total;
    TextView bill_total;
    TextView deliv_total;
    ConstraintLayout empty_cart_layout, cart_layoutconst;
    TextView total;

    String price;

    public CartAdapter(List<Cartdb> data, TextView mrp_total, TextView bill_total, TextView deliv_total, ConstraintLayout empty_cart_layout, ConstraintLayout cart_layoutconst, TextView total) {
        this.data = data;
        this.mrp_total = mrp_total;
        this.bill_total = bill_total;
        this.deliv_total = deliv_total;
        this.empty_cart_layout = empty_cart_layout;
        this.cart_layoutconst = cart_layoutconst;
        this.total = total;
    }

    public CartAdapter() {
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_rec_sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.cQuantity.setText(data.get(position).item_qn);
        Call<List<ProductResponseModel>> call= apicontroler
                .getInstance()
                .getapi()
                .product_fetch(String.valueOf(data.get(position).id));
        call.enqueue(new Callback<List<ProductResponseModel>>() {
            @Override
            public void onResponse(Call<List<ProductResponseModel>> call, Response<List<ProductResponseModel>> response) {
                List<ProductResponseModel> cart_data = response.body();
                holder.cart_pname.setText(cart_data.get(0).getProduct_name());
                holder.cart_pquantity.setText(cart_data.get(0).getProduct_quantity());
                holder.cart_pprice.setText(cart_data.get(0).getProduct_price());
                Glide.with(holder.itemView.getContext()).load("https://emanational-barrel.000webhostapp.com/admin/image/"+cart_data.get(0).getProduct_image()).into(holder.cart_pimage);

                holder.cart_pimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent  intent=  new Intent(holder.itemView.getContext(), Product_pg.class);
                        intent.putExtra("product_id", cart_data.get(0).getProduct_id());
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
               holder.cpos.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       if ((Integer.parseInt(holder.cQuantity.getText() + ""))<(Integer.parseInt(cart_data.get(0).getProduct_stock()+"")))
                       {
                           price= cart_data.get(0).getProduct_price();
                           holder.cQuantity.setText(String.valueOf(Integer.parseInt(holder.cQuantity.getText() + "") + 1));
                           AppDatabase db = Room.databaseBuilder(holder.itemView.getContext(),
                                   AppDatabase.class, "cart-db").allowMainThreadQueries().build();
                           CartdbDao cartdbDao = db.cartdbDao();
                           cartdbDao.update(new Cartdb(Integer.parseInt(cart_data.get(0).getProduct_id()), holder.cQuantity.getText().toString()));
                           int total= Integer.parseInt(mrp_total.getText().toString())+ Integer.parseInt(price);
                           mrp_total.setText(String.valueOf(total));
                           totalPriceUpdate(holder.itemView.getContext());
                       }
                       else
                           Toast.makeText(holder.itemView.getContext(), "Maximum Quantity Reached", Toast.LENGTH_SHORT).show();
                   }
               });

               holder.cnag.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       price= cart_data.get(0).getProduct_price();
                       AppDatabase db = Room.databaseBuilder(holder.itemView.getContext(),
                               AppDatabase.class, "cart-db").allowMainThreadQueries().build();
                       CartdbDao cartdbDao = db.cartdbDao();
                       int total= Integer.parseInt(mrp_total.getText().toString())- Integer.parseInt(price);
                       mrp_total.setText(String.valueOf(total));

                       if (Integer.parseInt(holder.cQuantity.getText() + "") > 1) {
                           holder.cQuantity.setText(String.valueOf(Integer.parseInt(holder.cQuantity.getText() + "") - 1));
                           cartdbDao.update(new Cartdb(Integer.parseInt(cart_data.get(0).getProduct_id()), holder.cQuantity.getText().toString()));
                           totalPriceUpdate(holder.itemView.getContext());
                       }
                       else if (Integer.parseInt(holder.cQuantity.getText() + "") <= 1) {
                           cartdbDao.delete(new Cartdb(Integer.parseInt(cart_data.get(0).getProduct_id()), holder.cQuantity.getText().toString()));
                           totalPriceUpdate(holder.itemView.getContext());
                           holder.itemView.setVisibility(View.GONE);
                       }
                   }
               });

               holder.drop.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       price= cart_data.get(0).getProduct_price();
                       int qn= Integer.parseInt(holder.cQuantity.getText().toString());
                       int total= Integer.parseInt(mrp_total.getText().toString())- (Integer.parseInt(price)*qn);
                       mrp_total.setText(String.valueOf(total));
                       AppDatabase db = Room.databaseBuilder(holder.itemView.getContext(),
                               AppDatabase.class, "cart-db").allowMainThreadQueries().build();
                       CartdbDao cartdbDao = db.cartdbDao();
                       cartdbDao.delete(new Cartdb(Integer.parseInt(cart_data.get(0).getProduct_id()), holder.cQuantity.getText().toString()));
                       totalPriceUpdate(holder.itemView.getContext());
                       holder.itemView.setVisibility(View.GONE);
                   }
               });
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
        ImageView cart_pimage;
        TextView cart_pname, cart_pquantity, cart_pprice, cQuantity;
        Button cnag, cpos, drop;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_pimage = itemView.findViewById(R.id.cart_pimage);
            cart_pname=itemView.findViewById(R.id.cart_pname);
            cart_pprice=itemView.findViewById(R.id.cart_pprice);
            cart_pquantity= itemView.findViewById(R.id.cart_pquantity);
            cQuantity= itemView.findViewById(R.id.cQuantity);
            cnag= itemView.findViewById(R.id.cnag);
            cpos = itemView.findViewById(R.id.cpos);
            drop= itemView.findViewById(R.id.drop);
        }
    }

    private void totalPriceUpdate(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "cart-db").allowMainThreadQueries().build();

        CartdbDao cartdbDao = db.cartdbDao();
        List<Cartdb> cartdbs = cartdbDao.getAll();
        bill_total.setText((Integer.parseInt(mrp_total.getText() + "") + Integer.parseInt(deliv_total.getText() + "")) + "");
        total.setText(bill_total.getText() + "");

//        int i;
//        if (cartdbs.size() != 0) {
//            int total= Integer.parseInt(mrp_total.getText().toString())+ Integer.parseInt(price);
//            mrp_total.setText(String.valueOf(total));
//            for (i = 0; cartdbs.size() > i; i++) {
//                Call<List<ProductResponseModel>> call = apicontroler
//                        .getInstance()
//                        .getapi()
//                        .product_fetch(String.valueOf(cartdbs.get(i).id));
//                int finalI = i;
//                call.enqueue(new Callback<List<ProductResponseModel>>() {
//                    @Override
//                    public void onResponse(Call<List<ProductResponseModel>> call, Response<List<ProductResponseModel>> response) {
//                        List<ProductResponseModel> data = response.body();
//                        mrp_total.setText(Integer.parseInt(mrp_total.getText() + "") + Integer.parseInt(cartdbs.get(finalI).item_qn) * Integer.parseInt(data.get(0).getProduct_price()) + "");
//                        bill_total.setText((Integer.parseInt(mrp_total.getText() + "") + Integer.parseInt(deliv_total.getText() + "")) + "");
//                        total.setText(bill_total.getText() + "");
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<ProductResponseModel>> call, Throwable t) {
//
//                    }
//                });
//            }
 //       }
        if(cartdbs.size() == 0)//else
        {
            bill_total.setText((Integer.parseInt(mrp_total.getText() + "") + Integer.parseInt(deliv_total.getText() + "")) + "");
            total.setText(bill_total.getText() + "");
            if (mrp_total.getText().equals("0")) {
                empty_cart_layout.setVisibility(View.VISIBLE);
                cart_layoutconst.setVisibility(View.GONE);
            }
        }
    }
}
