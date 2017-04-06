package project.cis350.upenn.edu.botanist_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Object representing communication with the backend User-Plant database. Contains
 * methods allowing adding, updating, and removing plants. Given a username, we can
 * also find all plants belonging to that user.
 *
 * Created by Keren, updated by Max
 */
public class FetchPlantData {
    public SQLiteDatabase db;
    private final Context context;
    private UserPlantsDB userPlants;

    public FetchPlantData(Context logInContext) {
        context = logInContext;
        userPlants = new UserPlantsDB(context);
    }

    public FetchPlantData open() {
        db = userPlants.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public boolean insertEntry(String username, Plant p) {
        ContentValues newValues = new ContentValues();

        newValues.put(UserPlantsDB.COL_USERNAME, username);
        byte[] serialPlant = Plant.serializePlant(p);

        // serialization may fail, in which case we do nothing
        if (serialPlant == null) {
            Toast.makeText(context, "Tried to insert plant but serial was null", Toast.LENGTH_LONG).show();
            return false;
        } else {
            newValues.put(UserPlantsDB.COL_PLANT, Plant.serializePlant(p));
            db.insert(UserPlantsDB.TABLE_NAME, null, newValues);
            return true;
        }
    }

    public int deleteUser(String username) {
        // String where=
        return 1;
    }

    public List<Plant> getPlants(String username) {
        List<Plant> plants = new ArrayList<>();
        Cursor cursor = db.query(
                UserPlantsDB.TABLE_NAME,
                new String[]{UserPlantsDB.COL_PLANT},
                " username=?",
                new String[]{username},
                null, null, null);
        if (cursor.getCount() < 1) {
            return plants;
        }
        try {
            while (cursor.moveToNext()) {
                byte[] serialPlant = cursor.getBlob(cursor.getColumnIndex(UserPlantsDB.COL_PLANT));
                Plant curr = Plant.deserializePlant(serialPlant);
                if (curr != null) {
                    plants.add(curr);
                }
            }
            return plants;
        } finally {
            try {
                cursor.close();
            } catch (Exception e) { /* do nothing */ }
        }
    }
}
