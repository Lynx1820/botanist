package project.cis350.upenn.edu.botanist_project;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by John on 2/16/17.
 * The general plant object. I might make this an abstract class with different types of plants
 * as subclasses
 */

public class Plant {
    private Date created;
    private String name;
    private ArrayList<Bitmap> photos;
    private boolean hasPicture;

    public Plant(Date created, String name) {
        this.created = created;
        this.name = name;
        photos = new ArrayList<>();
        hasPicture = false;
    }

    public void addPhoto(Bitmap photo) {
        photos.add(photo);
        if (!hasPicture) {
            hasPicture = true;
        }
    }

    public Date getCreated() {
        return created;
    }

    public String getName() {
        return name;
    }

    public boolean hasPicture() {
        return hasPicture;
    }

}
