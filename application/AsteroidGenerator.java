package application;

import java.util.Random;
import javafx.scene.shape.Polygon;

public class AsteroidGenerator {
    public static Polygon createPolygon(int size) {
        Random rnd = new Random();

//        double size = 10;
        switch (size) {
            case 1:
                size = 20 + rnd.nextInt(10);
                break;
            case 2:
                size = 35 + rnd.nextInt(10);
                break;
            case 3:
                size = 55 + rnd.nextInt(10);
                break;
        }

        double indentFactor = 0.20 * size;

        Polygon polygon = new Polygon();
        double angle = 0;
        double increment = Math.PI / 15;


        while (angle < 2 * Math.PI) {
            double r = size + rnd.nextDouble() * indentFactor * 2 - indentFactor;
            double x = r * Math.cos(angle);
            double y = r * Math.sin(angle);
            polygon.getPoints().addAll(x, y);
            angle += increment;
        }

        return polygon;
    }

}



