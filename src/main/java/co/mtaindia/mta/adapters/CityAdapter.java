package co.mtaindia.mta.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.beans.CityBean;

/**
 * Created by Kapil Gehlot on 1/29/2017.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {
    private List<CityBean> cityList;
    private Context context;

    private CityAdapter.ClickListener clickListener;

    public CityAdapter(List<CityBean> cityList, Context c) {
        this.cityList = cityList;
        this.context = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CityBean city = cityList.get(position);
        holder.coursrname.setText(city.getCoursename());
        holder.fee.setText("Fee : " + city.getFee());
        holder.duration.setText("Duration : " + city.getDuration());
        holder.registered.setText("Registered : " + city.getRegistered());
        holder.batches.setText("Batches : " + city.getBatches());
        Glide.with(context).load(cityList.get(position).getDpurl()).asBitmap()
                .placeholder(R.drawable.image_no).into(new BitmapImageViewTarget(holder.imageViewdp) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.imageViewdp.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    interface ClickListener {
        void itemClicked(View v, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView coursrname, duration, registered, fee, batches;
        ImageView imageViewdp;

        MyViewHolder(View view) {
            super(view);
            coursrname = (TextView) view.findViewById(R.id.coursename);
            duration = (TextView) view.findViewById(R.id.duration);
            fee = (TextView) view.findViewById(R.id.totalfee);
            batches = (TextView) view.findViewById(R.id.totalbatches);
            registered = (TextView) view.findViewById(R.id.registered);
            imageViewdp = (ImageView) view.findViewById(R.id.imageView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }
}
