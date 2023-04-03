package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class PreviousActivity extends AppCompatActivity {
    private QuizzesData quizzesData = null;
    private List<Quizzes> quizzesList;
    private QuizRecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous);
        quizzesData = new QuizzesData( getApplicationContext() );
        quizzesList = new ArrayList<Quizzes>();
        recyclerView = findViewById(R.id.recyclerView);
        // use a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( layoutManager );
        // Open that database for reading of the full list of quizzes
        quizzesData.open();
        new QuizDBReader().execute();
    }
    // This is an AsyncTask class (it extends AsyncTask) to perform DB reading of quizzes, asynchronously.
    private class QuizDBReader extends AsyncTask<Void, List<Quizzes>> {
        // This method will run as a background process to read from db.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback
        @Override
        protected List<Quizzes> doInBackground( Void... params ) {
            List<Quizzes> quizList = quizzesData.retrieveAllQuizzes();

            return quizList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        // onPostExecute is like the notify method in an asynchronous method call.
        @Override
        protected void onPostExecute( List<Quizzes> quizList ) {
            quizzesList.addAll( quizList );

            // create the RecyclerAdapter and set it for the RecyclerView
            recyclerAdapter = new QuizRecyclerAdapter( getApplicationContext(), quizzesList );
            recyclerView.setAdapter( recyclerAdapter );
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        // Open the database
        if( quizzesData != null && !quizzesData.isDBOpen() ) {
            quizzesData.open();
        }
    }
    @Override
    public void onPause() {
        super.onPause();

        // close the database in onPause
        if( quizzesData != null ) {
            quizzesData.close();
        }
    }
}