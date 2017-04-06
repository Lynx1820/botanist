package project.cis350.upenn.edu.botanist_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InteractiveUserPlantActivity extends AppCompatActivity {

    private Plant plant;
    private TextView plantDesc; // name and species
    private TextView plantReminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_user_plant);

        plant = (Plant) getIntent().getSerializableExtra("chosenPlant");

        plantDesc = (TextView) findViewById(R.id.plantDescr);
        plantReminders = (TextView) findViewById(R.id.plantReminders);

        plantDesc.setText("Name: " + plant.getName() + "\nType: " + plant.getType());
        plantReminders.setText(plant.lastWateredText());
    }

    public void onWaterPlantButtonClick(View v) {
        plant.water();
        // FIXME
        new FetchPlantData(getApplicationContext()).open().updateEntry(MenuActivity.owner, plant);
        Toast.makeText(this, "Plant has been watered!", Toast.LENGTH_SHORT).show();
        plantReminders.setText(plant.lastWateredText()); // refresh this view
    }

    public void onPlantDeleteClick(View v) {
        new FetchPlantData(getApplicationContext()).open().deleteEntry(MenuActivity.owner, plant);
        Toast.makeText(this, "Plant has been deleted :(", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, PlantActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }


}
