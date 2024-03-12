package application;
import BasicGUI.GameLogin;
import javafx.geometry.Point2D;
import java.util.List;
import java.util.Random;
import javafx.scene.shape.Polygon;
public class Ship extends Character {
    private Polygon character;
    private Point2D movement;

    private int curr_lives = 3;
    public int invincibleTime = 0;

    public Ship(int x, int y) {

        //Polygon object represents the ship
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
    }

    public void reduceLives() {
        this.curr_lives -= 1;
    }

    public int numLives() {
        return this.curr_lives;
    }

    public void increaseLives(){
        this.curr_lives += 1;
    }


    public void setInvincible(int val) {
        this.invincibleTime = val;

    }

    public int invincibleTimer() {
        return this.invincibleTime;
    }
    public void reduceInvincible() {
        this.invincibleTime -= 1;
    }

    //work in progress method
    public void hyperspace(List<Asteroid> list1, List<Alien> a1) {
        Random rnd = new Random();//random number generator
        int temp_x = rnd.nextInt(GameLogin.WIDTH);
        int temp_y = rnd.nextInt(GameLogin.HEIGHT);
//        this.setInvincible(500);
        boolean noCollide = true ;

        if(list1!=null){
            for (Asteroid current : list1) {
                //make invincible instead

                if (Math.abs(temp_x - current.character.getTranslateX()) < 50 &&
                        Math.abs(temp_y - current.character.getTranslateY()) < 50) {
                    noCollide = false;
                    break; // break the loop early if a collision is detected
                }
            }
        }

        if(a1!=null) {
            for (Alien current : a1) {
                //make invincible instead

                if (Math.abs(temp_x - current.character.getTranslateX()) < 50 &&
                        Math.abs(temp_y - current.character.getTranslateY()) < 50) {
                    noCollide = false;
                    break; // break the loop early if a collision is detected
                }
            }
        }

        if(noCollide) {
            this.getCharacter().setTranslateX(temp_x);
            this.getCharacter().setTranslateY(temp_y);
        }
        else {
//            hyperspace(list1,list2,list3,a1);
            hyperspace(list1,a1);
        }
    }
}



