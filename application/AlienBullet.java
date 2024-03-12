package application;

import javafx.scene.paint.Color;

public class AlienBullet extends Projectile{
    public AlienBullet(int x, int y) {
        super(x, y);
        this.getCharacter().setFill(Color.GREENYELLOW);
    }
}
