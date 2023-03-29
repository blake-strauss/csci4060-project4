package edu.uga.cs.countryquiz;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

public class FragmentActivity extends Fragment {

    //placeholder before connecting to database
    private static final String[] questions = {"1","2","3","4","5","6","7"};

    /* integer used to show the correct content
    based on what page the user is on */
    private int num=0;

    public FragmentActivity() {
        // Required empty public constructor
    }

    public static FragmentActivity newInstance( int questionNum ) {
        FragmentActivity fragment = new FragmentActivity();
        Bundle args = new Bundle();
        args.putInt( "question", questionNum );
        fragment.setArguments( args );
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( getArguments() != null ) {
            num = getArguments().getInt( "question" );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflates the last page for the fragment
        if (questions[num].equals("7")) {
            return inflater.inflate(R.layout.quiz_results, container, false);
        }
        // Inflates the layout of the first 6 pages of the fragment
        else {
            return inflater.inflate(R.layout.fragment_activity, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        if (!(questions[num].equals("7"))) {
            RadioButton rb = view.findViewById(R.id.radioButton);
            RadioButton rb2 = view.findViewById(R.id.radioButton2);
            RadioButton rb3 = view.findViewById(R.id.radioButton3);

            //will change when connected to database
            rb.setText(questions[num]);
            rb2.setText(questions[num]);
            rb3.setText(questions[num]);
        }
        else {
            TextView tv = view.findViewById(R.id.score);
            //will change once we make it dynamic
            tv.setText("100%");
        }
    }

    public static int getLength() {
        return questions.length;
    }
}