package project.cis350.upenn.edu.botanist_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
        GridLayout plant_grid = (GridLayout) findViewById(R.id.plant_grid);
        plant_grid.removeAllViews(); // clear previous plants in case a new one has been created

        // Dimensions of grid
        int n = currentPlants.size();
        int cols = 2;
        int rows = (n + 1) / cols;
        plant_grid.setColumnCount(cols);
        plant_grid.setRowCount(rows);

        for (int i = 0; i < n; i++) {
            final Plant p = currentPlants.get(i);

            // ImageView for the plant picture
            ImageView image = new ImageView(this);
            image.setId(i + 1);  // ids must be > 0, so we add 1
            image.setImageResource(R.drawable.pun_pending);

            // TextView for the plant name
            TextView name = new TextView(this);
            name.setText(p.getName());
            name.setGravity(Gravity.CENTER);

            // Put ImageView on top of TextView
            RelativeLayout stacked = new RelativeLayout(this);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params2.addRule(RelativeLayout.BELOW, image.getId());
            stacked.addView(image);
            stacked.addView(name, params2);
            stacked.setGravity(Gravity.CENTER);  // TODO: figure out how to center text. this isn't working

            // Each box in the grid is clickable to open up the plant details
            stacked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPlantDetails(p);
                }
            });

            plant_grid.addView(stacked);
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

                if (p != null) {
                    currentPlants.add(p); // add newly created plant to the grid
                }

                if (result) {
                    Toast.makeText(this, "Added plant successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        displayCurrentPlants();
    }
}
