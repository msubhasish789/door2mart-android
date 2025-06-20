package knowledge.hood.door2mart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import knowledge.hood.door2mart.Models.BannerModel;
import knowledge.hood.door2mart.R;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    ArrayList<BannerModel> list;
    Context context;

    public BannerAdapter(ArrayList<BannerModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BannerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.banner_rec_sample,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.ViewHolder holder, int position) {
            BannerModel model = list.get(position);
            holder.banimage.setImageResource(model.getBanimage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView banimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            banimage = itemView.findViewById(R.id.banimage);
        }
    }
}
