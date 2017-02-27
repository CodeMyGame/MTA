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
import co.mtaindia.mta.beans.GallaryBean;

/**
 * Created by Kapil Gehlot on 1/14/2017.
 */

public class GallaryAdapter extends RecyclerView.Adapter<GallaryAdapter.MyViewHolder> {
    private List<GallaryBean> gallaryList;
    private Context context;

    public GallaryAdapter(List<GallaryBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.galary_single_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }
}
