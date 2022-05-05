package GameState;

import Entity.Player;
import Main.GamePanel;
import TileMap.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Level1State extends GameState{

    private TileMap tileMap;
    private Background bg;

    private Player player;

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
    }

    @Override
    public void update() {
        //update
        player.update();
        tileMap.setPosition(GamePanel.WIDTH/2-player.getx(),GamePanel.HEIGHT/2-player.gety());
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
        tileMap.draw(g);
        player.draw(g);
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
