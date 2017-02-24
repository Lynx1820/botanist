package project.cis350.upenn.edu.botanist_project;

/**
 * Created by lynx313 on 2/14/17.
 */
import android.content.ContentValues;
import android.content.Context;
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
        ContentValues newVal = new ContentValues();
        db.insert("LOGIN", null, newVal);
    }
    public int deleteUser(String username){
       // String where=
        return 1;
    }
    public LoginDataBase open(){
        // Gets the data repository in write mode

        db = dbHelper.getWritableDatabase();
        return this;
    }
}
