package project.cis350.upenn.edu.botanist_project;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

public class PlantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button addPlant = (Button) findViewById(R.id.add_plant);
        // set action for when add_plant button is pressed
        addPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: display menu for adding new plant
                // this is a temporary message
                Snackbar.make(view, "Adding new plant!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                GridLayout plant_grid = (GridLayout) findViewById(R.id.plant_grid);
                ImageView image = new ImageView(getApplicationContext());
                image.setImageResource(R.drawable.plant);
                plant_grid.addView(image);
            }
        });
    }
}
