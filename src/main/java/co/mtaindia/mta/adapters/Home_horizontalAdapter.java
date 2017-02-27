package co.mtaindia.mta.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.beans.Home_horizontalBean;

/**
 * Created by Kapil Gehlot on 1/18/2017.
 */

public class Home_horizontalAdapter extends RecyclerView.Adapter<Home_horizontalAdapter.MyViewHolder> {
    private List<Home_horizontalBean> gallaryList;
    private Context context;
    private Home_horizontalAdapter.ClickListener clickListener;
    public Home_horizontalAdapter(List<Home_horizontalBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    public void setClickListener(Home_horizontalAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public Home_horizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_horizontal, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Home_horizontalAdapter.MyViewHolder holder, int position) {
        Glide
                .with(context)
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

        MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.horizontal_img);
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
