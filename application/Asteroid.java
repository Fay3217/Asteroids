package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import java.util.Random;


public class Asteroid extends Character {

    // Field for storing the amount of rotational movement of the asteroid
    private double rotationalMovement;

    private int size;

    private double accelerationAmount;


    // Constructor for creating a new instance of Asteroid object
    public Asteroid(int x, int y, int level, int size) {

        // Calling the constructor of the parent class and passing in the polygon, x and y coordinates
        super(AsteroidGenerator.createPolygon(size), x, y);

        // Creating a new instance of Random class for generating random values
        Random rnd = new Random();

        // Setting the initial rotation of the asteroid to a random value
        super.getCharacter().setRotate(rnd.nextInt(360));

        this.accelerationAmount = getAccelerationAmount(size, level);

        // Accelerating the asteroid by the determined amount
        for (int i = 0; i < this.accelerationAmount; i++) {
            accelerate();
        }

        this.character.setFill(getAsteroidColor(size));

        this.rotationalMovement = getRotationalMovement(size, rnd);

        //setting the size category for this instance of asteroid
        this.size = size;

    }

    // Overrides the move method in the parent class
    @Override
    public void move() {

        // Calls the move method of the parent class to update the position of the asteroid
        super.move();

        // Changes the rotation of the asteroid based on the rotational movement field
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMovement);
    }

    // Getter method for the size category of the asteroid
    public int getSize() {
        return this.size;
    }



    private double getRotationalMovement(int size, Random rnd) {
        switch (size) {
            case 1:
                return 0.8 - rnd.nextDouble() * 0.2;
            case 2:
                return 0.7 - rnd.nextDouble() * 0.2;
            case 3:
                return 0.6 - rnd.nextDouble() * 0.2;
            default:
                return 0.5 - rnd.nextDouble();
        }
    }

    private Color getAsteroidColor(int size) {
        switch (size) {
            case 1:
            case 3:
                return Color.DIMGRAY;
            case 2:
                return Color.GRAY;
//            case 3:
//                return Color.DARKGRAY;
            default:
                return Color.SLATEGRAY;
        }
    }


    private double getAccelerationAmount(int size, int level) {
        switch (size) {
            case 1:
                return 55 + (level * 3);
            case 2:
                return 50 + (level * 3);
            case 3:
                return 20 + (level * 3);
            default:
                return 10 + (level * 3);
        }
    }




}
