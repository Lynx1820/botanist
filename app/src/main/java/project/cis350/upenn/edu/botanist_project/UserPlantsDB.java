package project.cis350.upenn.edu.botanist_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Max on 4/5/17.
 */
public class UserPlantsDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userPlants.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "userPlants";
    public static final String COL_USERNAME =  "username";
    public static final String COL_PLANT = "plantSerialized";
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_USERNAME + " TEXT, " +
                    COL_PLANT + " BLOB" +
                    ")";

    public UserPlantsDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
