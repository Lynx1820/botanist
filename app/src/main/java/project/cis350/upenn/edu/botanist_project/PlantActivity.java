package project.cis350.upenn.edu.botanist_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static project.cis350.upenn.edu.botanist_project.R.id.center;
import static project.cis350.upenn.edu.botanist_project.R.id.center_horizontal;
import static project.cis350.upenn.edu.botanist_project.R.id.left_column;
import static project.cis350.upenn.edu.botanist_project.R.id.right_column;

/**
 * The activity that allows a user to view their plants. Also connects to creation of new plants,
 * AddPlantActivity, and more detailed information of *user* plants (not to be confused with
 * PlantInfo, which is general).
 */
public class PlantActivity extends AppCompatActivity {
    public static final int ADDPLANT_ID = 1;
    private static List<Plant> currentPlants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateUsersPlants();
        displayCurrentPlants();

        Button addPlant = (Button) findViewById(R.id.add_plant);
        // set action for when add_plant button is pressed
        addPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddView();
            }
        });
    }

    protected void populateUsersPlants() {
        //noinspection unchecked
        currentPlants = (ArrayList<Plant>) getIntent().getSerializableExtra("plant_list");
    }

    protected void displayCurrentPlants() {
        LinearLayout left_col = (LinearLayout) findViewById(left_column);
        LinearLayout right_col = (LinearLayout) findViewById(right_column);
        left_col.removeAllViews();
        right_col.removeAllViews();
        for (int i = 0; i < currentPlants.size(); i++) {
            Plant p = currentPlants.get(i);
            ImageView image = new ImageView(getApplicationContext());
            image.setImageResource(R.drawable.pun_pending);

            TextView nameDisplay = new TextView(getApplicationContext());
            nameDisplay.setText(p.getName());
            nameDisplay.setGravity(center_horizontal);
            nameDisplay.setTextColor(Color.BLACK);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(0, 0, 0, 30);

            LinearLayout plantLayout = new LinearLayout(getApplicationContext());
            plantLayout.setOrientation(LinearLayout.VERTICAL);
            plantLayout.setGravity(center);
            plantLayout.addView(image);
            plantLayout.addView(nameDisplay, layoutParams);


            if (i % 2 == 0) {
                left_col.addView(plantLayout, 0);
            }
            else {
                right_col.addView(plantLayout, 0);
            }
        }
    }

    protected void openAddView() {
        Intent i = new Intent(this, AddPlantActivity.class);
        startActivityForResult(i, ADDPLANT_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (data != null) {
                boolean result = data.getBooleanExtra("result", false);
                Plant p = (Plant) data.getSerializableExtra("createdPlant");

                if (p != null) {
                    currentPlants.add(p); // add newly created plant to the grid
                    Toast.makeText(this, "# of plants is" + currentPlants.size(), Toast.LENGTH_SHORT).show();
                }

                if (result) {
                    // this is a temporary message
                    View v = findViewById(android.R.id.content).getRootView();
                    Snackbar.make(v, "Added plant successfully!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        }
        displayCurrentPlants();
    }
}
