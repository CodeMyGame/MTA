package co.mtaindia.mta.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import co.mtaindia.mta.R;

public class QuizNextActivity extends AppCompatActivity {

    public String name;
    public int position;
    RadioGroup radioGroup;
    RadioButton r1, r2, r3, r4;
    int question = 0;
    ArrayList arrayList;
    TextView ques;
    String ans;
    Button chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_next);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("mta");
        ques = (TextView) findViewById(R.id.question);
        final TextSwitcher textSwitcher = new TextSwitcher(this);

// specify the in/out animations you wish to use
        textSwitcher.setInAnimation(this, R.anim.slide_up);
        textSwitcher.setOutAnimation(this, R.anim.slide_down);
        textSwitcher.addView(ques);
        radioGroup = (RadioGroup) findViewById(R.id.radiogrp);
        r1 = (RadioButton) findViewById(R.id.r1);
        r2 = (RadioButton) findViewById(R.id.r2);
        r3 = (RadioButton) findViewById(R.id.r3);
        r4 = (RadioButton) findViewById(R.id.r4);
        chk = (Button) findViewById(R.id.check);
        TextView textView = (TextView) findViewById(R.id.logo);
        position = getIntent().getIntExtra("position", 0);
        textView.setText("MTA-INDIA - set #" + position);
        name = getIntent().getStringExtra("name");
        myRef.child("quiz").child(name.toLowerCase()).child("" + position).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    arrayList = (ArrayList) dataSnapshot.getValue();
                    HashMap hashMap = (HashMap) arrayList.get(question);
                    ArrayList options = (ArrayList) hashMap.get("options");
                    r1.setText(options.get(0).toString());
                    r2.setText(options.get(1).toString());
                    r3.setText(options.get(2).toString());
                    r4.setText(options.get(3).toString());
                    textSwitcher.setText(hashMap.get("question").toString());
                    ans = hashMap.get("answer").toString();
                } else {
                    Toast.makeText(QuizNextActivity.this, "empty!!!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chk.getText().toString().toLowerCase().trim().equals("check")) {
                    RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton != null) {
                        if (radioButton.getText().toString().toLowerCase().trim().equals(ans)) {
                            chk.setText("next");
                            Snackbar snackbar = Snackbar.make(view, "Correct answer!!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            question++;
                            snackbar.getView().setBackgroundColor(Color.GREEN);
                            snackbar.show();

                        } else {
                            chk.setText("check");
                            HomeActivity.vibe.vibrate(30);
                            Snackbar snackbar = Snackbar.make(view, "wrong answer!!!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            snackbar.getView().setBackgroundColor(Color.RED);
                            snackbar.show();
                        }
                    } else {
                        Snackbar snackbar = Snackbar.make(view, "Select answer!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        snackbar.getView().setBackgroundColor(Color.BLACK);
                        snackbar.show();
                    }
                } else {
                    Toast.makeText(QuizNextActivity.this, "next question...", Toast.LENGTH_SHORT).show();
                    if (arrayList.size() != question) {
                        HashMap hashMap = (HashMap) arrayList.get(question);
                        ArrayList options = (ArrayList) hashMap.get("options");
                        r1.setText(options.get(0).toString());
                        r2.setText(options.get(1).toString());
                        r3.setText(options.get(2).toString());
                        r4.setText(options.get(3).toString());
                        textSwitcher.setText(hashMap.get("question").toString());
                        ans = hashMap.get("answer").toString();
                        chk.setText("check");
                    } else {
                        Toast.makeText(QuizNextActivity.this, "questions over!!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
    }

    public void return2Quiz(View view) {
        HomeActivity.vibe.vibrate(15);
        onBackPressed();
    }
}
