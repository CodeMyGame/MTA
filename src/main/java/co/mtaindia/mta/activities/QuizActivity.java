package co.mtaindia.mta.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.adapters.QuizAdapter;
import co.mtaindia.mta.beans.TabCourseBean;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
        final String name = getIntent().getStringExtra("name");
        TextView textView = (TextView) findViewById(R.id.logo);
        textView.setText("MTA-INDIA - " + name);
        RecyclerView recyclerView;
        final List<TabCourseBean> gallaryList = new ArrayList<>();
        final QuizAdapter adapter = new QuizAdapter(gallaryList, this);
        for (int i = 0; i < 20; i++) {
            gallaryList.add(new TabCourseBean("Set : " + i));
        }
        adapter.notifyDataSetChanged();
        adapter.setClickListener(new QuizAdapter.ClickListener() {
            @Override
            public void itemClicked(View v, int position) {
                Intent intent = new Intent(QuizActivity.this, QuizNextActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("position", position);
                startActivity(intent);
                overridePendingTransition(R.transition.slide_enter, R.transition.slide_exit);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
    }

    public void return2Home(View view) {
        HomeActivity.vibe.vibrate(15);
        onBackPressed();
    }
}
