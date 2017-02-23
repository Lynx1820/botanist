package project.cis350.upenn.edu.botanist_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
    public static final int PlantButtonClickActivity_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    // creates a plant activity
    public void onPlantButtonClick(View v) {
        Intent i = new Intent(this, PlantActivity.class);
        startActivityForResult(i, PlantButtonClickActivity_ID);
    }

    public void onAboutButtonClick(View v) {
        // whatever is about activity
    }

    public void onLogoutButtonClick(View v) {
        // logout functionality
    }
}
