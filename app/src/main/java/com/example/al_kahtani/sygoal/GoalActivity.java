package com.example.al_kahtani.sygoal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class GoalActivity extends AppCompatActivity {
EditText new_goal;
    private RadioButton radioButton;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        new_goal = findViewById(R.id.goal);
        radioButton = findViewById(R.id.radio1);
    //    radioGroup = (RadioGroup) findViewById(R.id.radio);
        btnSave = (Button) findViewById(R.id.Button);

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
           //     int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
             //   radioButton =  findViewById(selectedId);

                Toast.makeText(GoalActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();

            }

        });

    }
}
