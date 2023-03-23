package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button2);

        //takes user to the quiz from the splash screen
        button.setOnClickListener((view) -> {
            Intent intent = new Intent(view.getContext(), QuizActivity.class);
            startActivity(intent);
        });
    }
}