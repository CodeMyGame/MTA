package co.mtaindia.mta.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import co.mtaindia.mta.R;
import co.mtaindia.mta.webviewpack.ViewClient;


public class RegisterActivity extends Activity {

    WebView wB;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        wB = (WebView)findViewById(R.id.webView);
        pb = (ProgressBar)findViewById(R.id.progressBar3);
        wB.setWebViewClient(new ViewClient());  //to open new link in same web application
        wB.getSettings().setJavaScriptEnabled(true);  //some sites uses java_scripts like youtube.com
        wB.getSettings().setUseWideViewPort(true);
        wB.getSettings().setLoadWithOverviewMode(true);
        wB.requestFocus();
        registerForContextMenu(wB);
        wB.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        wB.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wB.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wB.requestFocusFromTouch();
        wB.getSettings().setLoadsImagesAutomatically(true);
        wB.loadUrl("http://mtaindia.co/Registration.aspx");
        wB.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress < 90 && pb.getVisibility() == (ProgressBar.GONE)) {
                    pb.setVisibility(ProgressBar.VISIBLE);
                    pb.setProgress(newProgress);
                }
                if (newProgress == 90 && pb.getVisibility() == (ProgressBar.VISIBLE)) {
                    pb.setVisibility(ProgressBar.GONE);
                }

            }


        });

    }
}
