package project.cis350.upenn.edu.botanist_project;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InfoActivity extends AppCompatActivity {
    List<PlantInfo> info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        try {
            info = PlantInfo.readInfoFromJson(getAssets().open("plant_info.json"));
        } catch(IOException e) {
            e.printStackTrace();
            finish();
        }
        //ArrayAdapter<PlantInfo> adapter = new ArrayAdapter<PlantInfo>(this, android.R.layout.simple_list_item_1, info);
        PlantInfoAdapter adapter = new PlantInfoAdapter(this, info);

        ListView listView = (ListView) findViewById(R.id.infoList);
        listView.setAdapter(adapter);
    }
}
