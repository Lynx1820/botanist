package project.cis350.upenn.edu.botanist_project;

import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by John on 2/16/17.
 * The general plant object. I might make this an abstract class with different types of plants
 * as subclasses
 */

public class Plant implements Serializable {
    private String name;
    private String species;
    private int waterPeriodDays;
    private Date created;
    private Date lastWatered;

    public Plant(String name, String species) {
        this.name = name;
        this.species = species;
        this.waterPeriodDays = 7;
        this.created = new Date();
        this.lastWatered = new Date();
    }

    public Plant(String name, String species, int waterPeriodDays) {
        this.name = name;
        this.species = species;
        this.waterPeriodDays = waterPeriodDays;
        this.created = new Date();
        this.lastWatered = new Date();
    }

    public Plant(String name, String species, int waterPeriodDays, Date created, Date lastWatered) {
        this.name = name;
        this.species = species;
        this.waterPeriodDays = waterPeriodDays;
        this.created = created;
        this.lastWatered = lastWatered;
    }

    public boolean needsWatering() {
        Date waterBefore = new Date(lastWatered.getTime() + waterPeriodDays * 24 * 60 * 60 * 1000L);
        Date now = new Date();
        return waterBefore.compareTo(now) < 0;
    }

    public void water() {
        lastWatered = new Date();
    }

    public String getName() {
        return name;
    }
    public String getType() {
        return species;
    }
    public Date getCreated() {
        return created;
    }
    public Date getLastWatered() {
        return lastWatered;
    }
    public int getWaterPeriodDays() {
        return waterPeriodDays;
    }

    public static byte[] serializePlant(Plant p) {
        ByteArrayOutputStream b = null;
        ObjectOutputStream o = null;
        try {
            b = new ByteArrayOutputStream();
            o = new ObjectOutputStream(b);
            o.writeObject(p);
            return b.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (o != null) {
                    o.close();
                }
                if (b != null) {
                    b.close();
                }
            } catch (Exception e) { /* do nothing */ }
        }
    }

    public static Plant deserializePlant(byte[] pInfo) {
        ByteArrayInputStream b = null;
        ObjectInputStream o = null;
        try {
            b = new ByteArrayInputStream(pInfo);
            o = new ObjectInputStream(b);
            return (Plant) o.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (o != null) {
                    o.close();
                }
                if (b != null) {
                    b.close();
                }
            } catch (Exception e) { /* do nothing*/ }
        }
    }

}
