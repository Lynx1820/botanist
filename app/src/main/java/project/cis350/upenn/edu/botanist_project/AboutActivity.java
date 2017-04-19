package project.cis350.upenn.edu.botanist_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Most of the content in the About Page comes from the XML. This class is mostly wrapper.
 *
 */
public class AboutActivity extends AppCompatActivity {
    public static final int MenuActivity_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
