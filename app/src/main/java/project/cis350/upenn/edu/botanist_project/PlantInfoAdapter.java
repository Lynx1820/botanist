package project.cis350.upenn.edu.botanist_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

public class PlantInfoAdapter extends ArrayAdapter<PlantInfo> {
    public PlantInfoAdapter(Context context, List<PlantInfo> info) {
        super(context, 0, info);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlantInfo p = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.plantinfo_item, parent, false);
        }
        Button pButton = (Button) convertView.findViewById(R.id.plantinfobutton);
        pButton.setText(p.name);
        pButton.setTag(position);
        pButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                PlantInfo p = getItem(position);
                Intent i = new Intent(view.getContext(), PlantInfoActivity.class);
                i.putExtra("PLANT", p);
                view.getContext().startActivity(i);
            }
        });
        return convertView;
    }
}
