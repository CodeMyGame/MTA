package co.mtaindia.mta.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.beans.HomeBean;

/**
 * Created by Kapil Gehlot on 1/16/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<HomeBean> gallaryList;
    private Context context;
    private HomeAdapter.ClickListener clickListener;
    public HomeAdapter(List<HomeBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    public void setClickListener(HomeAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_row, parent, false);
        return new HomeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.MyViewHolder holder, final int position) {
        HomeBean gallary = gallaryList.get(position);
        holder.tvHeading.setText(gallary.getHeading());
        holder.tvDescription.setText(gallary.getDescription());
        Glide.with(context)
                .load(gallaryList.get(position).url)
                .placeholder(R.drawable.image_no)
                .crossFade()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }

    public interface ClickListener {
        void itemClicked(View v, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        TextView tvHeading;
        TextView tvDescription;

        MyViewHolder(View itemView) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.heading_summer);
            tvDescription = (TextView) itemView.findViewById(R.id.descrp_summer);
            imageView = (ImageView) itemView.findViewById(R.id.head_img);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }

    }
}
