package project.cis350.upenn.edu.botanist_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button logInButton, signUpButtton;
    SQLiteDatabase db;
    SQLiteOpenHelper dbHelper;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a instance of SQLite Database
        dbHelper = new DataBaseHelper(this);
        db = dbHelper.getReadableDatabase();
        final  EditText editTextUserName=(EditText)findViewById(R.id.logInUsername);
        final  EditText editTextPassword=(EditText)findViewById(R.id.logInPassword);
        logInButton=(Button)findViewById(R.id.buttonLogIn);
        signUpButtton=(Button)findViewById(R.id.buttonSignUp);

        signUpButtton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent singUpIntent =new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(singUpIntent);
            }
        });
        //login
        logInButton.setOnClickListener(new View.OnClickListener() {
            Cursor cursor;
            public void onClick(View v) {
                String username=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();
                cursor = db.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_NAME +
                        " WHERE " + DataBaseHelper.COLUMN_USERNAME + "=? AND " +
                        DataBaseHelper.COLUMN_PASSWORD + "=?", new String[] {username,password});
              //  System.out.println("dsa!!!" + cursor);
                if (cursor != null && cursor.getCount()>0) {
                    MenuActivity.owner = username;
                    Intent menuIntent =new Intent(getApplicationContext(),MenuActivity.class);
                    startActivity(menuIntent);
                }
                else{
                    Toast.makeText(MainActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    }
