package application;

import BasicGUI.FontSize;
import BasicGUI.GameLogin;
import BasicGUI.GameOver;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.io.IOException;


public class AsteroidsApplication extends Application {

    //add a level counter and initialise it at 0
    public int level = 1;

    public Stage stage1;

    private long lastFiredTime = 0;

    //Adding  in alien
    private boolean alienExist = false;


    @Override
    public void start(Stage stage) throws Exception {

        stage1 = stage;
        SoundEffect.playBgm("/bgm.mp3");

        //instantiate pane and set its size
        //Add points counter
        Pane pane = new Pane();

        pane.setPrefSize(GameLogin.WIDTH, GameLogin.HEIGHT);
        pane.setStyle("-fx-background-color: black;");

        Font font = Font.font("Impact", FontSize.SMALL.getFontSize());
        Text text1 = new Text(10, 64, "Level: 1");
        text1.setFont(font);
        text1.setFill(Color.WHITE);
        Text text = new Text(10, 42, "Points: 0");
        text.setFont(font);
        text.setFill(Color.WHITE);
        //create ship object, place it in the middle of the pane
        Ship ship = new Ship(GameLogin.WIDTH / 2, GameLogin.HEIGHT / 2);
        Text text3 = new Text(10, 86, "Lives: " + ship.numLives());
        text3.setFont(font);
        text3.setFill(Color.WHITE);
        Text text4 = new Text(10, 20, "Invincibility: Offline");
        text4.setFont(font);
        text4.setFill(Color.WHITE);


        AtomicInteger points = new AtomicInteger(); //create points counter, starts as null


        //create a new scene in the pane
        Scene scene = new Scene(pane);
        stage.setTitle("Asteroids!");
        stage.setScene(scene);
        stage.show();

        //scene.setFill(Color.BLACK);

        //empty projectiles list
        List<Projectile> projectiles = new ArrayList<>();

        //empty alien list
        List<Alien> aliens = new ArrayList<>();

        //empty alien projectile list
        List<AlienBullet> alienBullets = new ArrayList<>();



        //create list of asteroids & spawn 1 initially
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            //vary the size and shape of asteroid somewhat
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(GameLogin.WIDTH / 3), rnd.nextInt(GameLogin.HEIGHT), 1, 3);
            asteroids.add(asteroid);
        }

        //add ship and asteroids to the pane object
        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));


        //add alien
        aliens.forEach(alien -> pane.getChildren().add(alien.getCharacter()));

        //add text, ship and asteroids list as children of pane object
        pane.getChildren().add(text);
        pane.getChildren().add(text1);

        pane.getChildren().add(text3);
        pane.getChildren().add(text4);


        //record pressed keys
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });
        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });


        //Timer
        new AnimationTimer() {

            int livesMultiplier = 0;

            @Override
            public void handle(long now) {

                if (points.get()> (25000+ livesMultiplier*25000)){
                    livesMultiplier +=1;
                    ship.increaseLives();
                    text3.setText("Lives: " + ship.numLives());
                }

                if (ship.invincibleTimer() == 0){
                    text4.setText("Invincibility: Offline");
                }


                // iterate over all the projectiles and check if they have expired
                for (Iterator<Projectile> iterator = projectiles.iterator(); iterator.hasNext(); ) {
                    Projectile projectile = iterator.next();
                    if (projectile.hasExpired(now)) {
                        projectile.setAlive(false);
                    }
                }

                //ship turns left when left arrow pressed
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                //ship turns right when right arrow pressed
                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }

                //ship goes up when up arrow pressed
                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }

                //
                // results in issues
                //gameover when E key pressed
                if (pressedKeys.getOrDefault(KeyCode.E, false)) {
                    try {
                        int currentPoints = points.get();
                        if(currentPoints>GameLogin.getInstance().player.getScore()){
                            GameLogin.getInstance().player.setScore(currentPoints);
                        }
                        GameLogin.getInstance().wirtetxt(GameLogin.getInstance().playerlist);
                        SoundEffect.stopBgm();
                        GameOver.start(stage,currentPoints);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                if(pressedKeys.getOrDefault(KeyCode.H, false)) {
                    ship.hyperspace(asteroids,aliens);
                    ship.setInvincible(100);
                }

                //update the position of the ship based on the current movement vector
                ship.move();

                asteroids.forEach(asteroid -> {
                            asteroid.move(); //change position according to momentum
                            if (ship.collide(asteroid) && ship.invincibleTimer() == 0) {
                                ship.reduceLives();
                                text3.setText("Lives: " + ship.numLives());
                                if (ship.numLives() == 0) {
                                    level = 1;
                                    stop(); //if ship touches asteroid game ends
                                    try {
                                        int currentPoints = points.get();
                                        if(currentPoints>GameLogin.getInstance().player.getScore()){
                                            GameLogin.getInstance().player.setScore(currentPoints);
                                        }
                                        GameLogin.getInstance().wirtetxt(GameLogin.getInstance().playerlist);
                                        SoundEffect.stopBgm();
                                        GameOver.start(stage,currentPoints);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    ship.setInvincible(100);
                                    text4.setText("Invincibility: " + ship.invincibleTimer());
                                    ship.getCharacter().setTranslateX(GameLogin.WIDTH / 2);
                                    ship.getCharacter().setTranslateY(GameLogin.HEIGHT / 2);
                                }

                            }
                        }
                );

                //same implementation for alien
                aliens.forEach(alien -> {
                    alien.move();
                    if(ship.collide(alien) && ship.invincibleTimer() == 0) {
                        ship.reduceLives();
                        text3.setText("Lives: " + ship.numLives());
                        if (ship.numLives() == 0) {
                            level = 1;
                            stop(); //if ship touches asteroid game ends
                            try {
                                int currentPoints = points.get();
                                if(currentPoints>GameLogin.getInstance().player.getScore()){
                                    GameLogin.getInstance().player.setScore(currentPoints);
                                }
                                GameLogin.getInstance().wirtetxt(GameLogin.getInstance().playerlist);
                                SoundEffect.stopBgm();
                                GameOver.start(stage,currentPoints);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            ship.setInvincible(100);
                            text4.setText("Invincibility: " + ship.invincibleTimer());
                            ship.getCharacter().setTranslateX(GameLogin.WIDTH / 2);
                            ship.getCharacter().setTranslateY(GameLogin.HEIGHT / 2);
                        }
                    }

                    //shoot randomly
                    int i = new Random().nextInt(300);
                    if (i == 30) {//Create new bullet
                        AlienBullet bullet = alien.shoot(ship);
                        //add current bullet to list
                        alienBullets.add(bullet);
                        //increase the bullet velocities
                        bullet.accelerate();
                        //normalize keeps vector angle but sets unit to 1, then multiplied by 3
                        //to change bullet speed alter the multiply() value
                        bullet.setMovement(bullet.getMovement().normalize().multiply(3));
                        //add bullet to list of children of pane parent
                        pane.getChildren().add(bullet.getCharacter());
                    }

                });

                alienBullets.forEach(bullet -> {
                    bullet.move();

                    //set bullet to dead if it expires
                    if (bullet.hasExpired(System.nanoTime())) {
                        bullet.setAlive(false);
                    }

                    //if bullet hit the player's ship
                    if (bullet.collide(ship) && ship.invincibleTimer() == 0) {
                        //set bullet to dead
                        bullet.setAlive(false);
//                        pane.getChildren().remove(bullet.getCharacter());
//                        projectiles.remove(bullet);
                        //player's ship lives get changed
                        ship.reduceLives();
                        text3.setText("Lives: " + ship.numLives());
                        if (ship.numLives() == 0) {
                            level = 1;
                            stop(); //game ends
                            try {
                                int currentPoints = points.get();
                                GameLogin.getInstance().player.setScore(currentPoints);
                                GameLogin.getInstance().wirtetxt(GameLogin.getInstance().playerlist);
                                SoundEffect.stopBgm();
                                GameOver.start(stage,currentPoints);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ship.setInvincible(100);
                            text4.setText("Invincibility: " + ship.invincibleTimer());
                            ship.getCharacter().setTranslateX(GameLogin.WIDTH / 2);
                            ship.getCharacter().setTranslateY(GameLogin.HEIGHT / 2);
                        }
                    }
                });

                if (ship.invincibleTimer() > 0) {
                    //reduce invincible timer
                    ship.reduceInvincible();
                    text4.setText("Invincibility: " + ship.invincibleTimer());
                    ship.getCharacter().setFill(Color.YELLOWGREEN);
                } else {
                    ship.getCharacter().setFill(Color.WHITE);
                }


                //hit space bar to shoot
                if (pressedKeys.getOrDefault(KeyCode.SPACE, false)) {
                    long currentTime = System.nanoTime();

                    if (currentTime - lastFiredTime >= 300_000_000L) { // 0.15 seconds in nanoseconds

                        //ADD IN MOMENTUM CONTINGENT ON SHIP SPEED

                        // Create a new projectile object at the position of the ship and set its rotation to match the ship's rotation
                        Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                        projectile.getCharacter().setRotate(ship.getCharacter().getRotate());

                        // Add the projectile to the list of active projectiles
                        projectiles.add(projectile);

                        // Movement dependent on ships momentum and direction
                        Point2D shipMomentum = ship.getMovement();
                        double shipDirection = Math.toRadians(ship.getCharacter().getRotate());
                        Point2D projectileVel = shipMomentum.add(new Point2D(Math.cos(shipDirection), Math.sin(shipDirection)).multiply(5));
                        projectile.setMovement(projectileVel);

                        // Add the projectile to the game pane
                        pane.getChildren().add(projectile.getCharacter());

                        // Update the last fired time
                        lastFiredTime = currentTime;
                    }
                }

                List<Asteroid> destroyedAsteroids = new ArrayList<>();
                List<Asteroid> spawnedAsteroids = new ArrayList<>();
                List<Projectile> collidedProjectiles = new ArrayList<>();

                projectiles.forEach(projectile -> {
                    projectile.move();
                    // Detect collision between projectiles and asteroids
                    asteroids.forEach(asteroid -> {
                        if (projectile.collide(asteroid)) {

                            asteroid.setAlive(false);
                            projectile.setAlive(false);
                            destroyedAsteroids.add(asteroid);
                            collidedProjectiles.add(projectile);
                            pane.getChildren().removeAll(asteroid.getCharacter());

                            if (asteroid.getSize() == 3) {
//                                projectile.setAlive(false);
                                // Create two new medium asteroids at the position of the large asteroid
                                Asteroid mediumAsteroid1 = new Asteroid((int) asteroid.getCharacter().getTranslateX(), (int) asteroid.getCharacter().getTranslateY(), level, 2);
                                Asteroid mediumAsteroid2 = new Asteroid((int) asteroid.getCharacter().getTranslateX(), (int) asteroid.getCharacter().getTranslateY(), level, 2);

                                spawnedAsteroids.addAll(Arrays.asList(mediumAsteroid1, mediumAsteroid2));
//                                System.out.println("Added medium asteroids to list: " + spawnedAsteroids.size());
//                                System.out.println("Double checking size of medium asteroid #1: " + mediumAsteroid1.getSize());
//                                System.out.println("Double checking size of medium asteroid #2: " + mediumAsteroid2.getSize());

                                text.setText("Points: " + points.addAndGet(1000));

                            } else if (asteroid.getSize() == 2) {
                                // Create two new small asteroids at the position of the medium asteroid
                                Asteroid smallAsteroid1 = new Asteroid((int) asteroid.getCharacter().getTranslateX(), (int) asteroid.getCharacter().getTranslateY(), level, 1);
                                Asteroid smallAsteroid2 = new Asteroid((int) asteroid.getCharacter().getTranslateX(), (int) asteroid.getCharacter().getTranslateY(), level, 1);

//                                System.out.println("Added small asteroids to list: " + spawnedAsteroids.size());
//                                System.out.println("Double checking size of small asteroid #1: " + smallAsteroid1.getSize());
//                                System.out.println("Double checking size of small asteroid #2: " + smallAsteroid2.getSize());

                                spawnedAsteroids.addAll(Arrays.asList(smallAsteroid1,smallAsteroid2));

                                text.setText("Points: " + points.addAndGet(500));
//                                projectile.setAlive(false);

                            } else if (asteroid.getSize() == 1) {
                                text.setText("Points: " + points.addAndGet(250));
                            }


                        }
                    });


                    aliens.forEach(alien -> {
                        if (projectile.collide(alien)) {
                            alien.setAlive(false);
                            text.setText("Points: " + points.addAndGet(1500));

                            // Set the projectile to dead
                            projectile.setAlive(false);
                        }
                    });
                });


                //Remove from pane
                projectiles.stream()
                        // Filter out projectiles that are still alive
                        .filter(projectile -> !projectile.isAlive())
                        // Remove dead projectiles from the pane
                        .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));

                //Remove from list
                projectiles.removeAll(projectiles.stream()
                        // Filter out projectiles that are still alive
                        .filter(projectile -> !projectile.isAlive())
                        // Remove dead projectiles from the list of projectiles
                        .collect(Collectors.toList()));

                alienBullets.stream()
                        // Filter out projectiles that are still alive
                        .filter(alienBullet -> !alienBullet.isAlive())
                        // Remove dead projectiles from the pane
                        .forEach(alienBullet -> pane.getChildren().remove(alienBullet.getCharacter()));

                //Remove from list
                alienBullets.removeAll(alienBullets.stream()
                        // Filter out bullets that are still alive
                        .filter(alienBullet -> !alienBullet.isAlive())
                        // Remove dead bullets from the list of projectiles
                        .collect(Collectors.toList()));

                asteroids.stream()
                        //Filter out asteroids that are still alive
                        .filter(asteroid -> !asteroid.isAlive())
                        // Remove dead asteroids from the pane
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));

                asteroids.removeAll(asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        // Remove dead asteroids from the list of asteroids
                        .collect(Collectors.toList()));

                //remove dead aliens
                aliens.stream()
                        .filter((alien -> !alien.isAlive()))
                        .forEach(alien -> pane.getChildren().remove(alien.getCharacter()));

                aliens.removeAll(aliens.stream()
                        .filter(alien -> !alien.isAlive())
                        .collect(Collectors.toList()));

                //asteroids.addAll(spawnedAsteroids);
                projectiles.removeAll(collidedProjectiles);
                asteroids.removeAll(destroyedAsteroids);

                spawnedAsteroids.forEach(spawnAsteroid -> {
                            pane.getChildren().add(spawnAsteroid.getCharacter());
                            asteroids.add(spawnAsteroid);
                        });


                if (asteroids.isEmpty() && aliens.isEmpty()) {
                    stop();
                    level++; // increase level counter
                    System.out.println("Congratulations! You have completed level " + (level - 1) + ". Starting level " + level + "...");
                    text1.setText("Level: " + level);


                    // spawn additional asteroids for each level, excluding the first level which already has one big asteroid
                    for (int i = 1; i < level; i++) {
                        Random rnd = new Random();
                        Asteroid asteroid = new Asteroid(rnd.nextInt(GameLogin.WIDTH / 3), rnd.nextInt(GameLogin.HEIGHT / 3), level, 3);
                         //ADD IN CHECK TO ENSURE THEY DON'T COLLIDE
                        if (!asteroid.collide(ship)) {
                            asteroids.add(asteroid);
                            pane.getChildren().add(asteroid.getCharacter());
                        }
                    }

                    //spawn aliens every two levels
                    if (level % 2 == 0) {
                        //spawn 1 additional alien per 2 levels
                        for (int i = 0; i < level / 2; i++) {
                            Alien alien = new Alien(GameLogin.WIDTH, GameLogin.HEIGHT);
                            aliens.add(alien);
                            pane.getChildren().add(alien.getCharacter());
                        }

                    }

                    // Print out size of asteroids list to ensure that it is increasing for each level
                    System.out.println("Size of asteroids list: " + asteroids.size());
                    start(); // restart game for new level
                }

            }

        }.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
