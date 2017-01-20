package co.mtaindia.mta.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.mtaindia.mta.Picasso.AnimationUtils;
import co.mtaindia.mta.Picasso.PicasoClient;
import co.mtaindia.mta.R;
import co.mtaindia.mta.beans.BatchBean;

/**
 * Created by Kapil Gehlot on 1/19/2017.
 */

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.MyViewHolder> {
    private List<BatchBean> gallaryList;
    int previousPosition = 0;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHeading;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.title_city);
            imageView = (ImageView) itemView.findViewById(R.id.head_img);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    public BatchAdapter(List<BatchBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    @Override
    public BatchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.batch_row, parent, false);

        return new BatchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BatchAdapter.MyViewHolder holder, int position) {
        BatchBean gallary = gallaryList.get(position);
        holder.tvHeading.setText(gallary.getHeading());
        PicasoClient.downLoadImg(context, gallaryList.get(position).url, holder.imageView);
        if (position > previousPosition) {
            AnimationUtils.animate(holder, true);
        } else {
            AnimationUtils.animate(holder, false);
        }
        previousPosition = position;
        if (position > previousPosition) {
            AnimationUtils.animate(holder, true);
        } else {
            AnimationUtils.animate(holder, false);
        }
        previousPosition = position;

    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }
}
