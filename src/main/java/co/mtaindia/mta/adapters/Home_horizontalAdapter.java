package co.mtaindia.mta.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import co.mtaindia.mta.Picasso.PicasoClient;
import co.mtaindia.mta.R;
import co.mtaindia.mta.beans.Home_horizontalBean;

/**
 * Created by Kapil Gehlot on 1/18/2017.
 */

public class Home_horizontalAdapter extends RecyclerView.Adapter<Home_horizontalAdapter.MyViewHolder> {
    private List<Home_horizontalBean> gallaryList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.horizontal_img);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }


    public Home_horizontalAdapter(List<Home_horizontalBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    @Override
    public Home_horizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_horizontal, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Home_horizontalAdapter.MyViewHolder holder, int position) {
        PicasoClient.downLoadImg(context, gallaryList.get(position).url, holder.imageView);
    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }
}
