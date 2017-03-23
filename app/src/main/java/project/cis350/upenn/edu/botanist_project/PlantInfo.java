package project.cis350.upenn.edu.botanist_project;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PlantInfo implements Parcelable{
    public String name;
    public String fertilizer;
    public String humidity;
    public String light;
    public String potSize;
    public String soil;
    public String temperature;
    public String water;

    private PlantInfo() {
    }

    private PlantInfo(Parcel in) {
        name = in.readString();
        fertilizer = in.readString();
        humidity = in.readString();
        light = in.readString();
        potSize = in.readString();
        soil = in.readString();
        temperature = in.readString();
        water = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(fertilizer);
        out.writeString(humidity);
        out.writeString(light);
        out.writeString(potSize);
        out.writeString(soil);
        out.writeString(temperature);
        out.writeString(water);
    }

    public static final Parcelable.Creator<PlantInfo> CREATOR = new Parcelable.Creator<PlantInfo>() {
        public PlantInfo createFromParcel(Parcel in) {
            return new PlantInfo(in);
        }

        public PlantInfo[] newArray(int size) {
            return new PlantInfo[size];
        }
    };

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append(name + "\n \n");
        ans.append("Fertilizer: " + fertilizer + "\n \n");
        ans.append("Humidity: " + humidity + "\n \n");
        ans.append("Light: " + light + "\n \n");
        ans.append("Pot Size: " + potSize + "\n \n");
        ans.append("Soil: " + soil + "\n \n");
        ans.append("Temperature: " + temperature + "\n \n");
        ans.append("Water: " + water);
        return ans.toString();
    }

    public static List<PlantInfo> readInfoFromJson(InputStream input) throws IOException{
        JsonReader in = new JsonReader(new InputStreamReader(input));
        List<PlantInfo> list = new ArrayList<>();

        in.beginObject();
        while (in.hasNext()) {
            PlantInfo p = new PlantInfo();
            p.name = in.nextName();
            in.beginObject();
            while (in.hasNext()) {
                String field = in.nextName();
                if (field.equals("Fertilizer")) {
                    p.fertilizer = in.nextString();
                } else if (field.equals("Humidity")) {
                    p.humidity = in.nextString();
                } else if (field.equals("Light")) {
                    p.light = in.nextString();
                } else if (field.equals("Pot Size")) {
                    p.potSize = in.nextString();
                } else if (field.equals("Soil")) {
                    p.soil = in.nextString();
                } else if (field.equals("Temperature")) {
                    p.temperature = in.nextString();
                } else if (field.equals("Water")) {
                    p.water = in.nextString();
                } else {
                    in.skipValue();
                }
            }
            in.endObject();
            list.add(p);
        }
        in.endObject();

        in.close();
        return list;
    }
}
