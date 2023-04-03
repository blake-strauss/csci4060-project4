package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FragmentActivity extends Fragment {
    private static final String[] questions = {"1","2","3","4","5","6","7"};

    /* integer used to show the correct content
    based on what page the user is on */
    private int num = 0;
    private static boolean flag = false; //variable to keep track of what radio button is clicked once user swipes left
    private static double counter = 0; //variable to keep track of the points scored in the quiz
    private static final DecimalFormat df = new DecimalFormat("0.00"); //rounds score to two decimal places
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    String date = sdf.format(new Date()); //gets the current date
    ArrayList<String> choice = MainActivity.quizQuestions; //ArrayList that contains data from CSV file
    Random rand = new Random();
    int var = rand.nextInt(195/2)*2; //variable to get random countries
    int var2 = rand.nextInt(195/2)*2+1; //variable to get the wrong random continents
    int var3 = rand.nextInt(195/2)*2+1; //variable to get the wrong random continents
    private QuizzesData quizzesData = null;


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
            View view = inflater.inflate(R.layout.fragment_activity, container, false);
            RadioGroup rg = view.findViewById(R.id.radioGroup);
            rg.setOnCheckedChangeListener(onCheckedChangeListener);
            return view;
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
                //gives point to user for selecting right answer
                if (flag) {
                    counter++;
                    flag=false;
                    Log.i("Debug", String.valueOf(counter));
                }
                // restores the quiz progress
                if( savedInstanceState != null ) {
                    counter = savedInstanceState.getInt("score", 0 );
                }
                //last page that shows the quiz result
            } else {
                quizzesData = new QuizzesData( getActivity() ); //gets the database
                TextView tv = view.findViewById(R.id.score);
                //gives point to user for selecting right answer
                if (flag) {
                    counter++;
                    Log.i("Debug", String.valueOf(counter));
                }
                // restores the quiz progress
                if( savedInstanceState != null ) {
                    counter = savedInstanceState.getInt("score", 0 );
                }
                double result = (counter/6)*100;
                tv.setText((df.format(result))+"%");
                //writes the date and result of the quiz in the database
                Quizzes quiz = new Quizzes(date,df.format(result)+"%");
                new QuizDBWriter().execute(quiz);
            }
        }
    }

    //listener for what radio button the user clicks
    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.radioButton:
                    RadioButton r = getView().findViewById(R.id.radioButton);
                    if (r.getText() == choice.get(var + 1)) {
                        flag=true;
                    }
                    else {
                        flag=false;
                    }
                    break;
                case R.id.radioButton2:
                    RadioButton r2 = getView().findViewById(R.id.radioButton2);
                    if (r2.getText() == choice.get(var + 1)) {
                        flag=true;
                    }
                    else {
                        flag=false;
                    }
                    break;
                case R.id.radioButton3:
                    RadioButton r3 = getView().findViewById(R.id.radioButton3);
                    if (r3.getText() == choice.get(var + 1)) {
                        flag=true;
                    }
                    else {
                        flag=false;
                    }
                    break;
            }
        }
    };
    @Override
    // saves the amount of points scored in the quiz
    public void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState(outState);
        outState.putInt("score", (int) counter);
    }
    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing of the quiz, asynchronously.
    public class QuizDBWriter extends AsyncTask<Quizzes, Quizzes> {

        // This method will run as a background process to write into db.
        @Override
        protected Quizzes doInBackground( Quizzes... quizzes ) {
            quizzesData.storeQuiz( quizzes[0] );
            return quizzes[0];
        }

        // This method will be automatically called by Android once the writing to the database
        // in a background process has finished. onPostExecute is like the notify method in an asynchronous
        // method call.
        @Override
        protected void onPostExecute( Quizzes quiz ) {
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