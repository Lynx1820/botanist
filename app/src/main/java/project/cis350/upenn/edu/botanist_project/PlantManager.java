package project.cis350.upenn.edu.botanist_project;

import java.util.ArrayList;

/**
 * Created by John on 2/16/17.
 * This is the view that comes up when you click the Plants button from Main Menu.
 * It shows the grid of plant objects the user has.
 *
 * It connects to the database that stores plant info for the current user and displays the plants
 *
 */

class PlantManager {
    ArrayList<Plant> plants;

    /**
     * Constructor. This could potentially take in a User object
     * as a key to access the correct data.
     */
    public PlantManager() {
        // TODO: use current User to access list of plants
        plants = new ArrayList<>();

        displayPlants();
    }

    private void sortByRecent() {
        // sorts plants by date created

    }

    private void displayPlants() {

    }
}
