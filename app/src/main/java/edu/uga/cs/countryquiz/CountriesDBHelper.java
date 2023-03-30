package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//creates table for storing countries and their respective continents
// as well as their quizzes in the database
public class CountriesDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "countries.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_COUNTRIES = "Countries";
    public static final String COUNTRIES_COLUMN_ID = "id";
    public static final String COUNTRIES_COLUMN_COUNTRY = "country";
    public static final String COUNTRIES_COLUMN_CONTINENT = "continent";
    public static final String TABLE_Quiz = "Quizzes";
    public static final String COUNTRIES_Quiz_ID = "id";
    public static final String COUNTRIES_COLUMN_quizDate = "date";
    public static final String COUNTRIES_COLUMN_quizResult = "result";

    // This is a reference to the only instance for the helper.
    private static CountriesDBHelper helperInstance;

    //countries table
    private static final String CREATE_COUNTRIES =
            "create table if not exists " + TABLE_COUNTRIES + " ("
                    + COUNTRIES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COUNTRIES_COLUMN_COUNTRY + " TEXT, "
                    + COUNTRIES_COLUMN_CONTINENT + " TEXT "
                    + ")";
    //quizzes table
    private static final String CREATE_Quiz =
            "create table if not exists " + TABLE_Quiz + " ("
                    + COUNTRIES_Quiz_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COUNTRIES_COLUMN_quizDate + " TEXT, "
                    + COUNTRIES_COLUMN_quizResult + " TEXT "
                    + ")";

    // Note that the constructor is private!
    // So, it can be called only from
    // this class, in the getInstance method.
    private CountriesDBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    // Access method to the single instance of the class.
    // It is synchronized, so that only one thread can executes this method, at a time.
    public static synchronized CountriesDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new CountriesDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    // We must override onCreate method, which will be used to create the database if
    // it does not exist yet.
    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( CREATE_COUNTRIES );
        db.execSQL( CREATE_Quiz );
    }

    // We should override onUpgrade method, which will be used to upgrade the database if
    // its version (DB_VERSION) has changed.  This will be done automatically by Android
    // if the version will be bumped up, as we modify the database schema.
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_COUNTRIES );
        db.execSQL( "drop table if exists " + TABLE_Quiz );
        onCreate( db );
    }
}

