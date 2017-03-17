package project.cis350.upenn.edu.botanist_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static project.cis350.upenn.edu.botanist_project.DataBaseHelper.COLUMN_USERNAME;

/**
 * Created by lynx313 on 3/17/17.
 */
/*This method will be used to lookup what plants a user has created.
Table should look something | User Id | Flower ID | Other Info? |*/
public class UserFlowersDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userFlowerLookUp.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "userFlowers";
    public static final String COLUMN_ID =  "userid";
    public static final String COLUMN_FLOWERID ="flowerIDs";
    public static final String COLUMN_VAR =  "daysWatered";
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INT" +
                    COLUMN_FLOWERID + " INT, " +
                    COLUMN_VAR + " INT" +
                    ")";
    public UserFlowersDatabaseHelper(Context context)
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
