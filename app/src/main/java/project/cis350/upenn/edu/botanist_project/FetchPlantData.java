package project.cis350.upenn.edu.botanist_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lynx313 on 3/24/17.
 * TODO: Change date created
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
        ContentValues flValues = new ContentValues();
        // Assign values for each row
        newValues.put("username", username);
        newValues.put("flowerID", plantId);
        newValues.put("daysWatered", daysWat);
        flValues.put("flowerID",plantId);
        //right now, I have the names this should be parsed or something
        flValues.put("flowerName","my flower");
        flValues.put("flowerType","rose");
        // Insert the row into your table
        db.insert("userFlowers", null, newValues);
        fl.insert("flowers", null, flValues);
    }
    public int deletePlant(String username) {
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
                String flowerName;
                String flowerType;
                int flowerID =  cursor.getInt(cursor.getColumnIndex("flowerID"));
                //gets the name of the plant
                Cursor flowerCursor = fl.rawQuery("SELECT flowerID, flowerName FROM flowers " +
                        "WHERE flowerID = ?", new String[] {Integer.toString(flowerID)});
                flowerName = flowerCursor.getString(flowerCursor.getColumnIndex("flowerName"));
                flowerCursor = fl.rawQuery("SELECT flowerID, flowerType FROM flowers " +
                        "WHERE flowerID = ?", new String[] {Integer.toString(flowerID)});
                flowerType = flowerCursor.getString(flowerCursor.getColumnIndex("flowerType"));
                //The following gets the name of the plant
                Plant curr = new Plant(new Date(),flowerName, flowerType);
                plants.add(curr);
            }
        } finally {
            cursor.close();
            return plants;
        }
    }
}
