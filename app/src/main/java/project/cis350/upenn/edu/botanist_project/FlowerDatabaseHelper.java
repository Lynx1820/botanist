package project.cis350.upenn.edu.botanist_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lynx313 on 3/17/17.
 */
/*This database stores all the information about plant.*/
public class FlowerDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flower.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "flowers";
    public static final String COLUMN_FLOWERID =  "flowerID";
    public static final String COLUMN_NAME =  "flowerName";
    public static final String COLUMN_TEMPERATURE=  "flowerName";
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_FLOWERID + " INT" +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_TEMPERATURE + "INT" +
                    ")";
    public FlowerDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_QUERY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
