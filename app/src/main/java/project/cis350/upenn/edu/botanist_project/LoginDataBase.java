package project.cis350.upenn.edu.botanist_project;

/**
 * Created by lynx313 on 2/14/17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
public class LoginDataBase {

    private final Context context;
    public  LoginDataBase(Context _context)
    {
        context = _context;
       // dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
