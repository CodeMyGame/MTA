package co.mtaindia.mta.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import co.mtaindia.mta.R;
import co.mtaindia.mta.fragments.tab1;
import co.mtaindia.mta.fragments.tab2;
import co.mtaindia.mta.fragments.tab3;
import co.mtaindia.mta.fragments.tab4;

public class MainActivity extends FragmentActivity {
    public static Vibrator vibe;
    Handler mHandler = new Handler();
    boolean isRunning = true;
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(co.mtaindia.mta.R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1
            );
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (isRunning) {
                    try {
                        Thread.sleep(10000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!isNetworkAvailable()) {
                                    Toast.makeText(MainActivity.this, "Check your network connection!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).start();
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment fragment ;
                if (tabId == R.id.tab_favorites) {
                   fragment = new tab1();
                    vibe.vibrate(12);
                }
                else if(tabId==R.id.tab_nearby){
                    fragment = new tab2();
                    vibe.vibrate(12);
                }
                else if(tabId==R.id.tab_about) {
                    fragment = new tab4();
                    vibe.vibrate(12);
                }
                else{
                    fragment = new tab3();
                    vibe.vibrate(12);
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.scrollingContent,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void gallaryActivity(View view) {
        vibe.vibrate(12);
        startActivity(new Intent(this,GallaryActivity.class));
    }

    public void registerActivity(View view) {
        vibe.vibrate(12);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.loginsignup, null);
        Button signup = (Button)v.findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(15);
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url","http://mtaindia.co/Registration.aspx");
                startActivity(intent);
            }
        });
        Button login = (Button)v.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(15);
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url","http://mtaindia.co/Registration.aspx");
                startActivity(intent);
            }
        });
        Button grp = (Button)v.findViewById(R.id.grp);
        grp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(15);
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url","http://mtaindia.co/Group-Discount.aspx");
                startActivity(intent);
            }
        });
        Button con = (Button)v.findViewById(R.id.contact);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(15);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View v = inflater.inflate(R.layout.contact, null);
                builder.setView(v);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
