package edu.uga.cs.countryquiz;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FragmentActivity extends Fragment {

    //placeholder before connecting to database
    private static final String[] questions = {"1","2","3","4","5","6","7"};

    /* integer used to show the correct content
    based on what page the user is on */
    private int num=0;
    ArrayList<String> choice = MainActivity.quizQuestions; //ArrayList that contains data from CSV file
    Random rand = new Random();
    int var = rand.nextInt(195/2)*2; //variable to get random countries
    int var2 = rand.nextInt(195/2)*2+1; //variable to get the wrong random continents
    int var3 = rand.nextInt(195/2)*2+1; //variable to get the wrong random continents


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
        //executes only if answer choices are unique
        if (isUnique()) {
            //randomizes the text of the radio buttons
            String one = choice.get(var + 1); //variable that contains the answer
            String two = choice.get(var2);
            String three = choice.get(var3);
            List<String> choices = Arrays.asList(one, two, three);
            Collections.shuffle(choices);

            if (!(questions[num].equals("7"))) {
                TextView textView = view.findViewById(R.id.question);
                RadioButton rb = view.findViewById(R.id.radioButton);
                RadioButton rb2 = view.findViewById(R.id.radioButton2);
                RadioButton rb3 = view.findViewById(R.id.radioButton3);

                textView.setText("Name the continent where " + choice.get(var) + " is located");
                rb.setText(choices.get(0));
                rb2.setText(choices.get(1));
                rb3.setText(choices.get(2));
            } else {
                TextView tv = view.findViewById(R.id.score);
                //will change once we make it dynamic
                tv.setText("100%");
            }
        }
    }

    public static int getLength() { return questions.length; }

    //method to make sure the answer choices are unique
    public boolean isUnique() {
        Set<String> unique = new HashSet<>();
        unique.add(choice.get(var+1));
        unique.add(choice.get(var2));
        unique.add(choice.get(var3));
        if(unique.size()!=3) {
            var2 = rand.nextInt(195/2)*2+1;
            var3 = rand.nextInt(195/2)*2+1;
            isUnique();
        }
        return true;
    }
}