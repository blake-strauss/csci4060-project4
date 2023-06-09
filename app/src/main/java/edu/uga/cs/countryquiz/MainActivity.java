package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Button button; //start a new quiz button
    private Button button2; //previous quizzes button
    CountriesDBHelper controller;
    public static final ArrayList<String> quizQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.quizButton);
        button2 = findViewById(R.id.previousButton);

        //reads csv file and puts it into countries table
        ContentValues values = new ContentValues();
        controller = CountriesDBHelper.getInstance(getApplicationContext());
        SQLiteDatabase db = controller.getWritableDatabase();
        String count = "SELECT count(*) FROM Countries";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        //inserts data into table only once
        if (icount==0) {
            try {
                db.beginTransaction();
                // Open the CSV data file in the assets folder
                InputStream in_s = getAssets().open("country_continent.csv");

                // read the CSV data
                CSVReader reader = new CSVReader(new InputStreamReader(in_s));
                String[] nextRow;
                while ((nextRow = reader.readNext()) != null) {
                    for (int i = 0; i < nextRow.length; i++) {
                        String country = nextRow[0];
                        String continent = nextRow[1];
                        values.put(CountriesDBHelper.COUNTRIES_COLUMN_COUNTRY, country);
                        values.put(CountriesDBHelper.COUNTRIES_COLUMN_CONTINENT, continent);
                    }
                    db.insert(CountriesDBHelper.TABLE_COUNTRIES, null, values);
                }
                db.setTransactionSuccessful();
                db.endTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //puts data of CSV file into ArrayList
        try {
            InputStream in_s = getAssets().open("country_continent.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(in_s));
            String[] nextRow;
            while ((nextRow = reader.readNext()) != null) {
                for (int i = 0; i < nextRow.length; i++) {
                    quizQuestions.add(nextRow[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //takes user to the quiz from the splash screen
        button.setOnClickListener((view) -> {
            Intent intent = new Intent(view.getContext(), QuizActivity.class);
            startActivity(intent);
        });
        //takes user to be able to see their previous quizzes
        button2.setOnClickListener((view) -> {
            Intent intent = new Intent(view.getContext(), PreviousActivity.class);
            startActivity(intent);
        });
    }
}