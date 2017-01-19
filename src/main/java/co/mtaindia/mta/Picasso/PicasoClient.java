package co.mtaindia.mta.Picasso;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import co.mtaindia.mta.R;

/**
 * Created by Kapil Malviya on 7/24/2016.
 */
public class PicasoClient {
    public static void downLoadImg(Context c, String url, ImageView imageView){
        if(url!=null && url.length()>0){
            Picasso.with(c).load(url).placeholder(R.drawable.image_no).into(imageView);
        }else{
            Picasso.with(c).load(R.drawable.image_no).into(imageView);
        }
    }
}
