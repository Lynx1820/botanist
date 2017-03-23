package project.cis350.upenn.edu.botanist_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PlantInfoActivity extends AppCompatActivity {
    PlantInfo p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
        p = (PlantInfo) getIntent().getParcelableExtra("PLANT");
        TextView text = (TextView) findViewById(R.id.plantText);
        text.setText(p.toString());
    }
}
