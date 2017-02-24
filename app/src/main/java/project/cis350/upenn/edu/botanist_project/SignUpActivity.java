package project.cis350.upenn.edu.botanist_project;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * Created by lynx313 on 2/15/17.
 */
/*TODO: Ensure that the passwirds are equal*/
public class SignUpActivity extends AppCompatActivity {
    EditText editUserName,editPassword,editConfirmPassword;
    Button createAccount;
    ActionBar actionBar;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    //LoginDataBase loginDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loginDataBase=new LoginDataBase(this);
        //loginDataBase =loginDataBase.open();
        openHelper = new DataBaseHelper(this);
        db = openHelper.getWritableDatabase();
        setContentView(R.layout.sign_up);
        editUserName=(EditText)findViewById(R.id.editUserName);
        editPassword=(EditText)findViewById(R.id.editPassword);
        editConfirmPassword=(EditText)findViewById(R.id.editConfirmPassword);
        createAccount = (Button) findViewById(R.id.createAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertUserData(editUserName.getText().toString(), editPassword.getText().toString());
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
    //Inserting Data into database
    public void insertUserData(String username, String password ) {

        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COLUMN_USERNAME,username);
        values.put(DataBaseHelper.COLUMN_PASSWORD,password);
        long id = db.insert(DataBaseHelper.TABLE_NAME,null,values);

    }
}
