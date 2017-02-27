package co.mtaindia.mta.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import co.mtaindia.mta.R;

public class StudyNextActivity extends AppCompatActivity {

    public String name;
    public int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_next);
        TextView textView = (TextView) findViewById(R.id.logo);
        position = getIntent().getIntExtra("position", 0);
        textView.setText("MTA-INDIA - chapter #" + position);
        name = getIntent().getStringExtra("name");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.slide_enter, R.transition.slide_exit);
    }

    public void return2Study(View view) {
        HomeActivity.vibe.vibrate(15);
        onBackPressed();
    }
}
