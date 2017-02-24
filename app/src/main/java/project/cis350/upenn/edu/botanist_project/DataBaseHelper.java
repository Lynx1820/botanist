package project.cis350.upenn.edu.botanist_project;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by lynx313 on 2/24/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "Botanist.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "profile";
    public static final String COLUMN_ID =  "userid";
    public static final String COLUMN_USERNAME =  "username";
    public static final String COLUMN_PASSWORD =  "password";
  //  public static final String COLUMN_MOBILE =  "mobile";
    private static final String CREATE_TABLE_QUERY =
          "CREATE TABLE " + TABLE_NAME + " (" +
                  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                  COLUMN_USERNAME + " TEXT, " +
                  COLUMN_PASSWORD + " TEXT " +
                  ")";
    public DataBaseHelper(Context context)
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
