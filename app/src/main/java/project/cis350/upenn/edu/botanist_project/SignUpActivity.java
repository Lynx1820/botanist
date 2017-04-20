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
import android.widget.Toast;

/**
 * Created by lynx313 on 2/15/17.
 */

public class SignUpActivity extends AppCompatActivity {
    EditText editUserName,editPassword,editConfirmPassword;
    Button createAccount;
    ActionBar actionBar;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openHelper = new DataBaseHelper(this);
        db = openHelper.getWritableDatabase();
        setContentView(R.layout.sign_up);
        editUserName=(EditText)findViewById(R.id.editUserName);
        editPassword=(EditText)findViewById(R.id.editPassword);
        editConfirmPassword=(EditText)findViewById(R.id.editConfirmPassword);
        createAccount = (Button) findViewById(R.id.createAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertUserData(editUserName.getText().toString(), editPassword.getText().toString(),
                        editConfirmPassword.getText().toString());
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

    // Given the entered fields, see if we can create a new user
    public void insertUserData(String username, String password, String confirmPassword) {

        Cursor cursor = db.query(DataBaseHelper.TABLE_NAME, null,
                DataBaseHelper.COLUMN_USERNAME + " =?", new String[]{username}, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.close();
            Toast.makeText(this, "Username already taken.", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            cursor.close();
            Toast.makeText(this, "Password fields do not match.", Toast.LENGTH_SHORT).show();
        } else {
            cursor.close();
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.COLUMN_USERNAME, username);
            values.put(DataBaseHelper.COLUMN_PASSWORD, password);
            long id = db.insert(DataBaseHelper.TABLE_NAME, null, values);
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
        }

    }
}
