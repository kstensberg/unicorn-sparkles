package stensberg.kevin.unicornsparkles.particle;

import android.graphics.Bitmap;

import java.util.Random;

// Originally found at https://github.com/manoj-chauhan/Sparkles/
// Written by Manoj Chauhan <manojchauhan100@gmail.com>
// Modified by Kevin Stensberg <kstensberg@gmail.com>
public class Particle {
    public int distFromOrigin = 0;
    private double direction;
    private double directionCosine;
    private double directionSine;
    public int x;
    public int y;
    public Bitmap bitmap;
    private int initX;
    private int initY;

    public Particle(int x, int y, Bitmap bitmap) {
        init(x, y, bitmap);
        this.direction = 2 * Math.PI * new Random().nextInt(NO_OF_DIRECTION) / NO_OF_DIRECTION;
        this.directionCosine = Math.cos(direction);
        this.directionSine = Math.sin(direction);
    }

    public void init(int x, int y, Bitmap bitmap) {
        distFromOrigin = 0;

        this.initX = x;
        this.x = x;

        this.initY = y;
        this.y = y;

        this.bitmap = bitmap;
    }

    public synchronized void move(){
        distFromOrigin += 2;
        x = (int) (initX + distFromOrigin * directionCosine);
        y = (int) (initY + distFromOrigin * directionSine);
    }
    private final static int NO_OF_DIRECTION = 400;

}