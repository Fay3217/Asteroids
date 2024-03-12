package application;


import BasicGUI.GameLogin;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

public abstract class Character {

    //Polygon object represents the shape of the character
    protected Polygon character;

    //Point2D object represents the current movement of the character on the x and y axes.
    private Point2D movement;

    //create alive variable
    private boolean alive;

    // creates a new Character object with the specified polygon shape, position on the x-axis and y-axis.
    // The movement is set to (0, 0) by default and the alive variable is set to true.
    public Character(Polygon polygon, int x, int y) {
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

        this.movement = new Point2D(0, 0);

        //character objects start off as being alive
        this.alive = true;

        this.character.setFill(Color.WHITE);

    }


    public Polygon getCharacter() {
        return character;
    }

    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 3.5);
    }

    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 3.5);
    }


    public void move() {
        // Update the position of the character by adding its current position
        // and the movement vector
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());

        // Check if the character has gone off the left edge of the screen
        if (this.character.getTranslateX() < 0) {
            // If it has, wrap it around to the right edge of the screen
            this.character.setTranslateX(this.character.getTranslateX() + GameLogin.WIDTH);
        }

        // Check if the character has gone off the right edge of the screen
        if (this.character.getTranslateX() > GameLogin.WIDTH) {
            // If it has, wrap it around to the left edge of the screen
            this.character.setTranslateX(this.character.getTranslateX() % GameLogin.WIDTH);
        }

        // Check if the character has gone off the top edge of the screen
        if (this.character.getTranslateY() < 0) {
            // If it has, wrap it around to the bottom edge of the screen
            this.character.setTranslateY(this.character.getTranslateY() + GameLogin.HEIGHT);
        }

        // Check if the character has gone off the bottom edge of the screen
        if (this.character.getTranslateY() > GameLogin.HEIGHT) {
            // If it has, wrap it around to the top edge of the screen
            this.character.setTranslateY(this.character.getTranslateY() % GameLogin.HEIGHT);
        }
    }


    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

        changeX *= 0.02;
        changeY *= 0.02;

        this.movement = this.movement.add(changeX, changeY);
    }

    public boolean collide(Character other){
        Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    //Add getters and setter methods to Character

    public Point2D getMovement() {
        return this.movement;
    }

    public void setMovement(Point2D x) {
        this.movement = x;
    }


    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean x) {
        this.alive = x;
    }


}