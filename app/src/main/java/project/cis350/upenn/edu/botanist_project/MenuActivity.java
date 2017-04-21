package project.cis350.upenn.edu.botanist_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MenuActivity extends AppCompatActivity {
    public static String owner;

    public static final int PlantButtonClickActivity_ID = 1;
    public static final int AboutButtonClickActivity_ID = 2;
    public static final int LogoutButtonClickActivity_ID = 3;
    public static final int InfoButtonClickActivity_ID = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        writeReminders();
    }

    @Override
    public void onResume() {
        super.onResume();
        writeReminders();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        writeReminders();
    }

    void writeReminders() {
        String myText = "Hello " + owner + "!";
        TextView myTextView= (TextView) findViewById(R.id.myTextView);
        myTextView.setText(myText);
        myTextView = (TextView) findViewById(R.id.info_text);
        myTextView.setText(getReminders());
    }

    String getReminders() {
        List<Plant> userPlants = getUserPlants();

        if (userPlants.isEmpty()) {
            return "Add a plant to keep track of it!";
        }

        StringBuilder sb = new StringBuilder();
        for (Plant p : userPlants) {
            if (p.needsWatering()) {
                sb.append(p.lastWateredText());
                sb.append("\n\n");
            }
        }
        String res = sb.toString();
        if (res.equals("")) {
            return "No reminders. Good job taking care of your plants!";
        }
        else {
            return res;
        }
    }

    List<Plant> getUserPlants() {
        return FetchPlantData.getPlants(getApplicationContext(), MenuActivity.owner);
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
        // deletes all previous activities so user can't just press back to re-login
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(i, LogoutButtonClickActivity_ID);
        finish();
    }
}
