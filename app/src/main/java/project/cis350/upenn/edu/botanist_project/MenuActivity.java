package project.cis350.upenn.edu.botanist_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MenuActivity extends AppCompatActivity {
    public static String owner;

    public LoginDataBase loginAdapter;
    public FetchPlantData plantAdapter;

    public static final int PlantButtonClickActivity_ID = 1;
    public static final int AboutButtonClickActivity_ID = 2;
    public static final int LogoutButtonClickActivity_ID = 3;
    public static final int InfoButtonClickActivity_ID = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        loginAdapter = new LoginDataBase(this);
        loginAdapter = loginAdapter.open();
        plantAdapter = new FetchPlantData(getApplicationContext());
        plantAdapter = plantAdapter.open();

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
        StringBuilder sb = new StringBuilder();
        for (Plant p : getUserPlants()) {
            if (p.needsWatering()) {
                sb.append(p.getName());
                sb.append("needs to be watered! ");
                long ago = TimeUnit.DAYS.convert(
                        System.currentTimeMillis() - p.getLastWatered().getTime(),
                        TimeUnit.MILLISECONDS);
                sb.append("Last watered ");
                sb.append(ago);
                sb.append(" days ago.\n");
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

    //needs to be an ArrayList to be explicitly serializable
    ArrayList<Plant> getUserPlants() {
        Toast.makeText(this, "user is " + MenuActivity.owner, Toast.LENGTH_LONG).show();
        return new ArrayList<>(plantAdapter.getPlants(MenuActivity.owner));
    }

    // creates a plant activity
    public void onPlantButtonClick(View v) {
        Intent i = new Intent(this, PlantActivity.class);
        i.putExtra("plant_list", getUserPlants());
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
