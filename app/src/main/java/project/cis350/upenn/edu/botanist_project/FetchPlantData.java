package project.cis350.upenn.edu.botanist_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lynx313 on 3/24/17.
 */

public class FetchPlantData {
    public SQLiteDatabase db;
    public SQLiteDatabase fl;
    private final Context context;
    SQLiteOpenHelper openHelper;
    private UserFlowersDatabaseHelper dbHelper;
    private FlowerDatabaseHelper fHelper;
    public  FetchPlantData(Context logInContext) {
        context = logInContext;
        fHelper = new FlowerDatabaseHelper(context);
        dbHelper = new UserFlowersDatabaseHelper(context);
    }
    public FetchPlantData open() {
        db = dbHelper.getWritableDatabase();
        fl = fHelper.getWritableDatabase(); //need writeable
        return this;
    }
    public void close() {
        db.close();
    }
    public  SQLiteDatabase getDatabaseInstance() {
        return db;
    }
    public void insertEntry(String username,int plantId, int daysWat) {
        // Create a new map of values, where column names are the keys

        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("username", username);
        newValues.put("flowerID", plantId);
        newValues.put("daysWatered", daysWat);
        // Insert the row into your table
        db.insert("userFlowers", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteUser(String username) {
        // String where=
        return 1;
    }
    public List<Plant> getPlants(String username){
        List<Plant> plants = new ArrayList<Plant>();
        Cursor cursor=db.query("userFlowers", null, " username=?", new String[]{username}, null, null, null);
        if(cursor.getCount()<1){
            return plants;
        }
        try {
            while (cursor.moveToNext()) {
                int flowerID =  cursor.getInt(cursor.getColumnIndex("flowerID"));
                //add info??
                Plant curr = new Plant(flowerID);
                plants.add(curr);
            }
        } finally {
            cursor.close();
            return plants;
        }
    }
}
