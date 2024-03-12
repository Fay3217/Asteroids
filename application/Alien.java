package application;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.Random;

public class Alien extends Character {
    private Point2D velocity;
    private final int width;
    private final int height;

    public Alien(int width, int height) {
        super(Alien.createAlien(), new Random().nextInt(width), new Random().nextInt(height));
        this.width = width;
        this.height = height;
        this.getCharacter().setFill(Color.GREENYELLOW);

        // initiate the velocity (in a random way)
        this.velocity = new Point2D(new Random().nextInt(5) - 2, new Random().nextInt(5) - 2);
    }

    //create alien polygon
    public static Polygon createAlien() {
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(10.0, 0.0,
                15.0, 0.0,
                17.0, -15.0,
                19.0, 0.0,
                31.0, 0.0,
                33.0, -15.0,
                35.0, 0.0,
                40.0, 0.0,
                50.0, 30.0,
                45.0, 30.0,
                45.0, 35.0,
                40.0, 35.0,
                40.0, 30.0,
                10.0, 30.0,
                10.0, 35.0,
                5.0, 35.0,
                5.0, 30.0,
                0.0, 30.0);
        return polygon;
    }

    // movement of the alien (in a random way, different from asteroids)
    @Override
    public void move() {
        // calculate the next position of the alien
        double nextX = super.character.getTranslateX() + velocity.getX();
        double nextY = super.character.getTranslateY() + velocity.getY();

        // make sure the alien move in the windows
        if (nextX <= 0 || nextX >= width) {
            velocity = new Point2D(-velocity.getX(), new Random().nextInt(3) - 2);
        }

        if (nextY <= 0 || nextY >= height) {
            velocity = new Point2D(new Random().nextInt(3) - 2, -velocity.getY());
        }

        // the movement
        super.character.setTranslateX(nextX);
        super.character.setTranslateY(nextY);
    }

    public boolean collide(Character ship) {
        Shape collisionArea = Shape.intersect(this.character, ship.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    // shoot at the character
    public AlienBullet shoot(Character ship) {
        Point2D target = new Point2D(ship.getCharacter().getTranslateX(), ship.getCharacter().getTranslateY());
        Point2D position = new Point2D(this.character.getTranslateX(), this.character.getTranslateY());
        // the velocity of the bullet
        Point2D bulletVelocity;
        bulletVelocity = target.subtract(position).normalize().multiply(1.5);
        AlienBullet bullet = new AlienBullet((int) this.character.getTranslateX(), (int) this.character.getTranslateY());
        bullet.setMovement(bulletVelocity);
        return bullet;
    }




}