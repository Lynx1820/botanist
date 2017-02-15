package project.cis350.upenn.edu.botanist_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.app.Dialog;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {
    Button signInButton, signUpButtton;
    LoginDataBase loginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBase(this);
        signInButton=(Button)findViewById(R.id.signIn);
        signUpButtton=(Button)findViewById(R.id.signUp);
        signUpButtton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //switch to signup pahe
            }
        });
    }

    public void signIn(View V)
    {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.login);
        dialog.setTitle("Login");

        final  EditText editTextUserName=(EditText)dialog.findViewById(R.id.logInUsername);
        final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.logInPassword);

        Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);

        // Set On ClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });

        dialog.show();
    }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
//}