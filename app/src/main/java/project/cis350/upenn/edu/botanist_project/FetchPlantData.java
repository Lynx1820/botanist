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
 * <p>
 * Created by Keren, updated by Max
 */
public class FetchPlantData {
    public SQLiteDatabase db;
    private final Context context;
    private UserPlantsDBHelper userPlants;

    public FetchPlantData(Context logInContext) {
        context = logInContext;
        userPlants = new UserPlantsDBHelper(context);
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

        byte[] serialPlant = Plant.serializePlant(p);

        // serialization may fail, in which case we do nothing
        if (serialPlant == null) {
            Toast.makeText(context, "Tried to insert plant but serialization failed", Toast.LENGTH_LONG).show();
            return false;
        } else {
            ContentValues newValues = new ContentValues();
            newValues.put(UserPlantsDBHelper.COL_USERNAME, username);
            newValues.put(UserPlantsDBHelper.COL_PLANT_NAME, p.getName());
            newValues.put(UserPlantsDBHelper.COL_PLANT, serialPlant);
            db.insert(UserPlantsDBHelper.TABLE_NAME, null, newValues);
            return true;
        }
    }

    /**
     * This method is called after the plant is watered. We remove the old plant data and replace
     * it with the updated, watered version.
     *
     * @param username username of the app user
     * @param p        Plant object that has just been watered
     */
    public void updateEntry(String username, Plant p) {

        byte[] serialPlant = Plant.serializePlant(p);

        // serialization may fail, in which case we do nothing
        if (serialPlant == null) {
            Toast.makeText(context, "Tried to apply update to plant but serialization failed.",
                    Toast.LENGTH_LONG).show();
        } else {
            ContentValues newValues = new ContentValues();
            newValues.put(UserPlantsDBHelper.COL_USERNAME, username);
            newValues.put(UserPlantsDBHelper.COL_PLANT_NAME, p.getName());
            newValues.put(UserPlantsDBHelper.COL_PLANT, serialPlant);
            db.update(
                    UserPlantsDBHelper.TABLE_NAME,
                    newValues,
                    UserPlantsDBHelper.COL_USERNAME + "= ? AND " +
                            UserPlantsDBHelper.COL_PLANT_NAME + "= ?",
                    new String[]{username, p.getName()});
        }
    }

    public void deleteEntry(String username, Plant p) {
        db.delete(
                UserPlantsDBHelper.TABLE_NAME,
                UserPlantsDBHelper.COL_USERNAME + "= ? AND " +
                        UserPlantsDBHelper.COL_PLANT_NAME + "= ?",
                new String[]{username, p.getName()});
    }

    public int deleteUser(String username) {
        // String where=
        return 1;
    }

    private List<Plant> getPlantsInternal(String username) {
        List<Plant> plants = new ArrayList<>();
        Cursor cursor = db.query(
                UserPlantsDBHelper.TABLE_NAME,
                new String[]{UserPlantsDBHelper.COL_PLANT},
                " username=?",
                new String[]{username},
                null, null, null);
        if (cursor.getCount() < 1) {
            return plants;
        }
        try {
            while (cursor.moveToNext()) {
                byte[] serialPlant = cursor.getBlob(cursor.getColumnIndex(UserPlantsDBHelper.COL_PLANT));
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

    public static List<Plant> getPlants(Context c, String username) {
        FetchPlantData fpd = new FetchPlantData(c).open();
        List<Plant> ret = fpd.getPlantsInternal(username);
        fpd.close();
        return ret;
    }

}
