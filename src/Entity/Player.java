package Entity;

import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends MapObject {
    // player stuff
    private int health;
    private int maxHealth;
    private int fire;
    private int maxFire;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;

    // fireball
    private boolean firing;
    private int fireCost;
    private int fireBallDamage;

    // scratch
    private boolean scratching;
    private int scratchDamage;
    private int scratchRange;

    // gliding
    private boolean gliding;

    // animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {2, 8, 1, 2, 4, 2, 5};

    // animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int FIREBALL = 5;
    private static final int SCRATCHING = 6;


    public Player(TileMap tm) {
        super(tm);
        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;
        health = maxHealth = 5;
        fire = maxFire = 2500;

        fireCost = 200;
        fireBallDamage = 5;

        scratchDamage = 8;
        scratchRange = 40;

        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Resources/Sprites/Player/playersprites.gif"));
            sprites = new ArrayList<BufferedImage[]>();
            for (int i = 0; i < 7; i++) {
                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                for (int j = 0; j < numFrames[i]; j++) {
                    if(i!=6)
                    bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
                    else{
                    bi[j] = spritesheet.getSubimage(j*width*2,i*height,width,height);
                    }
                }
                sprites.add(bi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        animation=new Animation();
        currentAction=IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);
    }
    public int getHealth(){return health;}
    public int getMaxHealth(){return maxHealth;}
    public int getFire() {return fire;}
    public int getMaxFire(){return maxFire;}

    public void setFiring(){firing=true;}
    public void setScratching(){scratching=true;}
    public void setGliding(boolean b){gliding=b;}

    private void getNextPosition(){
        //movement
        if(left){
            dx-=moveSpeed;
            if(dx<-maxSpeed){
                dx=-maxSpeed;
            }
        }else if(right){
            dx+=moveSpeed;
            if(dx>maxSpeed){
                dx=maxSpeed;
            }
        }
        else{
            if(dx>0){
                dx-=stopSpeed;
                if(dx<0){
                    dx=0;
                }
            }else if(dx<0){
                dx+=stopSpeed;
                if(dx>0){
                    dx=0;
                }
            }
        }
        // cannot move while attacking, except in air
        if((currentAction==SCRATCHING || currentAction==FIREBALL )&& !(jumping && falling)){
            dx=0;
        }
        //jumping
        if(jumping && !falling){
            dy=jumpStart;
            falling=true;
        }
        //falling
        if(falling){
            if(dy>0 && gliding) dy+=fallSpeed*0.1;
            else dy+=fallSpeed;
            if(dy>0) jumping=false;
            if(dy<0 && !jumping) dy+=stopJumpSpeed;
            if(dy>maxFallSpeed) dy=maxFallSpeed;
        }

    }

    public void action(int kindOfAction, int delay, int thiswidth){
        if(currentAction != kindOfAction) {
            currentAction = kindOfAction;
            animation.setFrames(sprites.get(kindOfAction));
            animation.setDelay(delay);
            width = thiswidth;
        }
    }

    public void update(){
        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        // set animation
        if(scratching) { //czy drapie
            action(SCRATCHING,50,60);
        }
        else if(firing) { //czy atakuje
            action(FIREBALL,100,30);
        }
        else if(dy > 0) { // czy jest w górze
            if(gliding) //szybuje
            {
                action(GLIDING,100,30);
            }
            else{ //spada
                action(FALLING,100,30);
            }
        }
        else if(dy < 0) { //postać skacze
            action(JUMPING,-1,30);
        }
        else if(left || right) { //postać porusza się lewo/ prawo
            action(WALKING,40,30);
        }
        else { //postać stoi
            action(IDLE,400,30);
        }

        animation.update();

        // set direction
        if(currentAction != SCRATCHING && currentAction != FIREBALL) {
            if(right) facingRight = true;
            if(left) facingRight = false;
        }

    }
    public void draw (Graphics2D g){
        setMapPosition();
        //draw player
        if(flinching){
            long elapsed=(System.nanoTime()-flinchTimer)/1000000;
            if(elapsed/100%2==0){
                return;
            }
        }if(facingRight){
            g.drawImage(animation.getImage(),(int)(x+xmap-width/2),(int)(y+ymap-height/2),null);
        }else{
            g.drawImage(animation.getImage(),(int)(x+xmap-width/2+width),(int)(y+ymap-height/2),-width,height,null);
        }
    }

}

