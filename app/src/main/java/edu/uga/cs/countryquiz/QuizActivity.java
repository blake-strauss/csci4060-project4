package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //makes the pages swipeable
        ViewPager2 pager = findViewById( R.id.viewPager );
        QuestionsPagerAdapter qpAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(), getLifecycle() );
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL );
        pager.setAdapter( qpAdapter );
    }
}