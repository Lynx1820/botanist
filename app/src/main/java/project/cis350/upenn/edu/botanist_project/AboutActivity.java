package project.cis350.upenn.edu.botanist_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AboutActivity extends AppCompatActivity {
    public static final int MenuActivity_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onGoBackButtonClick(View v) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivityForResult(i, MenuActivity_ID);
    }

}
