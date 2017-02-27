package co.mtaindia.mta.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.mtaindia.mta.R;
import co.mtaindia.mta.fragments.tab1;

public class HomeNextActivity extends AppCompatActivity {

    DatabaseReference myRef;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView textView;
    int position;
    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_next);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }

        position = getIntent().getIntExtra("position", 0);
        progressBar = (ProgressBar) findViewById(R.id.progressBarContent);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setBackground(tab1.drawable);
        textView = (TextView) findViewById(R.id.detail);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("mta");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("name"));
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.vibe.vibrate(15);
                Intent intent = new Intent(HomeNextActivity.this, WebViewActivity.class);
                intent.putExtra("url", "http://mtaindia.co/Registration.aspx");
                startActivity(intent);
            }
        });
        myRef.child("homeinfo").child("" + position).child("detail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    String det = dataSnapshot.getValue().toString();
                    textView.setText(det);
                } else {
                    textView.setText("Information available soon!!!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
