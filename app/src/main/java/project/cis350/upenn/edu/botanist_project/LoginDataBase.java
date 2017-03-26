package project.cis350.upenn.edu.botanist_project;

/**
 * Created by lynx313 on 2/14/17.
 */
/*I might implement this later for better modularity*/
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDataBase {
    public  SQLiteDatabase db;
    private final Context context;
    SQLiteOpenHelper openHelper;
    private DataBaseHelper dbHelper;

    public  LoginDataBase(Context logInContext)
    {
        context = logInContext;
        dbHelper = new DataBaseHelper(context);
    }
    public void insertUser(String username,String password){

    }
    public int deleteUser(String username) {
        // String where=
        return 1;
    }
    public LoginDataBase open(){
        // Gets the data repository in write mode
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public int getUserID(String username){
        Cursor cursor = db.query("profile", null, " username=?", new String[]{username}, null, null, null);
        cursor.moveToFirst();
        int userID= cursor.getInt(cursor.getColumnIndex("userID"));
        cursor.close();
        return userID;
    }
}
