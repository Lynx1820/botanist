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
    private String type;
    private ArrayList<Bitmap> photos;
    private boolean hasPicture;
    private int flowerID; //temporary var... eventually should be name
    public Plant(Date created, String name, String type) {
        this.created = created;
        this.name = name;
        this.type = type;
        photos = new ArrayList<>();
        hasPicture = false;
    }
    //just temporary...we can talk about what a plant object needs in the future.
    // Still need to integrate the plant database someother time...
    public Plant(int flowerID){
        this.flowerID = flowerID;
    }
    public void addPhoto(Bitmap photo) {
        photos.add(photo);
        if (!hasPicture) {
            hasPicture = true;
        }
    }

    public String getType() {return type;}

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
