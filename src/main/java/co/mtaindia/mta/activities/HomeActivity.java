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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.homefragments.tab1;
import co.mtaindia.mta.homefragments.tab2;
import co.mtaindia.mta.homefragments.tab3;

/**
 * Created by Kapil Gehlot on 2/25/2017.
 */

public class HomeActivity extends AppCompatActivity {
    public static Vibrator vibe;
    Handler mHandler = new Handler();
    boolean isRunning = true;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1
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
                                    Toast.makeText(HomeActivity.this, "Check your network connection!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).start();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public void gallaryActivity(View view) {
        vibe.vibrate(12);
        startActivity(new Intent(this, GallaryActivity.class));
        overridePendingTransition(R.transition.slideup, R.transition.slidedown);
    }

    public void registerActivity(View view) {
        vibe.vibrate(12);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.loginsignup, null);
        Button signup = (Button) v.findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(15);
                Intent intent = new Intent(HomeActivity.this, WebViewActivity.class);
                intent.putExtra("url", "http://mtaindia.co/Registration.aspx");
                startActivity(intent);
                overridePendingTransition(R.transition.slideup, R.transition.slidedown);
            }
        });
        Button login = (Button) v.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(15);
                Intent intent = new Intent(HomeActivity.this, WebViewActivity.class);
                intent.putExtra("url", "http://mtaindia.co/Registration.aspx");
                startActivity(intent);
                overridePendingTransition(R.transition.slideup, R.transition.slidedown);
            }
        });
        Button grp = (Button) v.findViewById(R.id.grp);
        grp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(15);
                Intent intent = new Intent(HomeActivity.this, WebViewActivity.class);
                intent.putExtra("url", "http://mtaindia.co/Group-Discount.aspx");
                startActivity(intent);
                overridePendingTransition(R.transition.slideup, R.transition.slidedown);
            }
        });
        Button con = (Button) v.findViewById(R.id.contact);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibe.vibrate(15);
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                LayoutInflater inflater = HomeActivity.this.getLayoutInflater();
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

    private void setupViewPager(ViewPager viewPager) {
        HomeActivity.ViewPagerAdapter adapter = new HomeActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new tab1(), "Summer training");
        adapter.addFragment(new tab2(), "Winter training");
        adapter.addFragment(new tab3(), "Polytechnic training");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        this.viewPager = viewPager;

    }

    public void return2tab2(View view) {
        vibe.vibrate(15);
        viewPager.setCurrentItem(1);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
