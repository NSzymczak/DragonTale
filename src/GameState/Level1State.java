package GameState;

import Entity.Enemies.Slugger;
import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Main.GamePanel;
import TileMap.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level1State extends GameState{

    private TileMap tileMap;
    private Background bg;

    private Player player;

    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;
    private HUD hud;

    public Level1State(GameStateManager gsm){
        this.gsm=gsm;
        init();
    }

    @Override
    public void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Resources/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Resources/Maps/level1-1.map");
        tileMap.setPosition(0,0);
        tileMap.setTween(1);

        bg=new Background("/Resources/Backgrounds/grassbg1.gif",0.1);

        player = new Player(tileMap);
        player.setPosition(100,100);

        populateEnemies();

        explosions = new ArrayList<Explosion>();

        hud = new HUD(player);
    }

    private void populateEnemies(){
        enemies = new ArrayList<Enemy>();
        Slugger s;
        Point[] points = new Point[]{
          new Point(200,100),
          new Point(860,200),
          new Point(1525,200),
          new Point(1680,200),
          new Point(1800,200)
        };
        for(int i = 0; i < points.length; i++){
            s = new Slugger(tileMap);
            s.setPosition(points[i].x,points[i].y);
            enemies.add(s);
        }
    }

    @Override
    public void update() {
        //update
        player.update();
        tileMap.setPosition(GamePanel.WIDTH/2-player.getx(),GamePanel.HEIGHT/2-player.gety());
        //moving background
        //bg.setPosition(tileMap.getx(), tileMap.gety());

        //attack enemies
        player.checkAttack(enemies);

        //update all enemies
        for(int i = 0; i<enemies.size();i++){
            Enemy e = enemies.get(i);
            enemies.get(i).update();
            if(enemies.get(i).isDead()){
                enemies.remove(i);
                i--;
                explosions.add(new Explosion(e.getx(), e.gety()));
            }
        }

        for(int i = 0; i <explosions.size();i++){
            explosions.get(i).update();
            if(explosions.get(i).shouldRemove()){
                explosions.remove(i);
                i--;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
        tileMap.draw(g);
        player.draw(g);
        for(int i =0;i<enemies.size();i++){
            enemies.get(i).draw(g);
        }
        for(int i = 0; i < explosions.size();i++){
            explosions.get(i).setMapPosition((int)tileMap.getx(),(int)tileMap.gety())  ;
            explosions.get(i).draw(g);
        }
        hud.draw(g);
    }

    @Override
    public void keyPressed(int k) {
        if(k== KeyEvent.VK_A) player.setLeft(true);
        if(k==KeyEvent.VK_D) player.setRight(true);
        if(k==KeyEvent.VK_W) player.setUp(true);
        if(k==KeyEvent.VK_S) player.setDown(true);
        if(k==KeyEvent.VK_SPACE) player.setJumping(true);
        if(k==KeyEvent.VK_SHIFT) player.setGliding(true);
        if(k==KeyEvent.VK_CONTROL) player.setScratching();
        if(k==KeyEvent.VK_F) player.setFiring();

    }

    @Override
    public void keyReleased(int k) {
        if(k== KeyEvent.VK_A) player.setLeft(false);
        if(k==KeyEvent.VK_D) player.setRight(false);
        if(k==KeyEvent.VK_W) player.setUp(false);
        if(k==KeyEvent.VK_S) player.setDown(false);
        if(k==KeyEvent.VK_SPACE) player.setJumping(false);
        if(k==KeyEvent.VK_SHIFT) player.setGliding(false);
    }
}
