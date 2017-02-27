package co.mtaindia.mta.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.adapters.StudyAdapter;
import co.mtaindia.mta.beans.TabCourseBean;

public class StudyActivity extends AppCompatActivity {
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        name = getIntent().getStringExtra("name");
        TextView textView = (TextView) findViewById(R.id.logo);
        textView.setText("MTA-INDIA - " + name);
        RecyclerView recyclerView;
        final List<TabCourseBean> gallaryList = new ArrayList<>();
        final StudyAdapter adapter = new StudyAdapter(gallaryList, this);
        for (int i = 0; i < 20; i++) {
            gallaryList.add(new TabCourseBean("Chapter : " + i));
        }
        adapter.notifyDataSetChanged();
        adapter.setClickListener(new StudyAdapter.ClickListener() {
            @Override
            public void itemClicked(View v, int position) {
                Intent intent = new Intent(StudyActivity.this, StudyNextActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("name", name);
                startActivity(intent);
                overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.slide_enter, R.transition.slide_exit);
    }

    public void return2Home(View view) {
        HomeActivity.vibe.vibrate(15);
        onBackPressed();
    }


}
