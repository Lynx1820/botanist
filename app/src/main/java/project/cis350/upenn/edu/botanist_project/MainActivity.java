package project.cis350.upenn.edu.botanist_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {
    Button logInButton, signUpButtton;
    LoginDataBase loginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBase(this);
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
        logInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent menuIntent =new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(menuIntent);
            }
        });
    }

    }
