package project.cis350.upenn.edu.botanist_project;

/**
 * Created by lynx313 on 2/14/17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
public class LoginDataBase {
    public  SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;
    static final String DATABASE_NAME = "logInDb";
    static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text); ";;
    public  LoginDataBase(Context logInContext)
    {
        context = logInContext;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME,null,1);
    }
    public void insertUser(String username,String password){
        ContentValues newVal = new ContentValues();
        newVal.put("USERNAME",username);
        newVal.put("PASSWORD",password);
        db.insert("LOGIN", null, newVal);
    }
    public int deleteUser(String username){
       // String where=
        return 1;
    }
    public LoginDataBase open(){
        db = dbHelper.getWritableDatabase();
        return this;
    }

}
