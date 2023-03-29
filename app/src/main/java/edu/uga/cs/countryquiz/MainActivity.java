package edu.uga.cs.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    private Button button;
    CountriesDBHelper controller;
    private static final String DB_PATH = "/data/data/edu.uga.cs.countryquiz/databases/countries.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button2);

        //reads csv file and puts it into countries table
        ContentValues values = new ContentValues();
        controller = CountriesDBHelper.getInstance(getApplicationContext());
        SQLiteDatabase db = controller.getWritableDatabase();
        //inserts data into table only once
        if (!databaseExist()) {
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
        //takes user to the quiz from the splash screen
        button.setOnClickListener((view) -> {
            Intent intent = new Intent(view.getContext(), QuizActivity.class);
            startActivity(intent);
        });
    }
    //method to check if database already exists
    public boolean databaseExist() {
        File dbFile = new File(DB_PATH + "countries.db");
        return dbFile.exists();
    }
}