package application;

import javafx.scene.shape.Polygon;

public class Projectile extends Character {

    private long creationTime;

    //constructor of the projectile class. Creates a new instance of projectile with the given coordinates
    public Projectile(int x, int y) {

        //Calls constructor character class with three arguments
        //new polygon shape that defines the projectile shape
        //x and y coordinates of the initial position of the projectile
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
        this.creationTime = System.nanoTime();


    }

    public boolean hasExpired(long now) {
        long elapsedTime = now - creationTime;
        return elapsedTime > 2_300_000_000L; // 3 seconds in nanoseconds
    }


}