package project.cis350.upenn.edu.botanist_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    public static String owner;
    public static final int PlantButtonClickActivity_ID = 1;
    public static final int AboutButtonClickActivity_ID = 2;
    public static final int LogoutButtonClickActivity_ID = 3;
    public static final int InfoButtonClickActivity_ID = 4;
    // TODO: Create the plant object from database info?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        String myText = "Hello " + owner + "!";
        TextView myTextView= (TextView) findViewById(R.id.myTextView);
        myTextView.setText(myText);
        myTextView= (TextView) findViewById(R.id.info_text);
        myTextView.setText("No remainders yet!");
    }

    // creates a plant activity
    public void onPlantButtonClick(View v) {
        Intent i = new Intent(this, PlantActivity.class);
        startActivityForResult(i, PlantButtonClickActivity_ID);
    }

    public void onInfoButtonClick(View v) {
        Intent i = new Intent(this, InfoActivity.class);
        startActivityForResult(i, InfoButtonClickActivity_ID);
    }

    public void onAboutButtonClick(View v) {
        Intent i = new Intent(this, AboutActivity.class);
        startActivityForResult(i, AboutButtonClickActivity_ID);
    }

    public void onLogoutButtonClick(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, LogoutButtonClickActivity_ID);
    }
}
