package project.cis350.upenn.edu.botanist_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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
        currentPlants = FetchPlantData.getPlants(getApplicationContext(), MenuActivity.owner);
    }

    protected void displayCurrentPlants() {
        LinearLayout left_col = (LinearLayout) findViewById(R.id.left_column);
        LinearLayout right_col = (LinearLayout) findViewById(R.id.right_column);
        // clear previous plants in case a new one has been created
        left_col.removeAllViews();
        right_col.removeAllViews();

        for (int i = 0; i < currentPlants.size(); i++) {
            final Plant p = currentPlants.get(i);

            // ImageView for the plant picture
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.pun_pending);
            // make image clickable for plant details
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPlantDetails(p);
                }
            });

            // TextView for the plant name
            TextView nameDisplay = new TextView(this);
            nameDisplay.setText(p.getName());
            nameDisplay.setGravity(R.id.center_horizontal);
            nameDisplay.setTextColor(Color.BLACK);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(0, 0, 0, 30);
            LinearLayout plantLayout = new LinearLayout(getApplicationContext());
            plantLayout.setOrientation(LinearLayout.VERTICAL);
            plantLayout.setGravity(R.id.center);
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

    void openPlantDetails(Plant p) {
        Intent i = new Intent(this, InteractiveUserPlantActivity.class);
        i.putExtra("chosenPlant", p);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (data != null) {
                boolean result = data.getBooleanExtra("result", false);
                Plant p = (Plant) data.getSerializableExtra("createdPlant");

                if (result && p != null) {
                    currentPlants.add(p); // add newly created plant to the grid
                    Toast.makeText(this, "Added plant successfully!", Toast.LENGTH_SHORT).show();
                    displayCurrentPlants();
                }
                else {
                    Toast.makeText(this, "Unable to add plant.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
