package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import TileMap.Background;
public class MenuState extends GameState {

    private Background bg;

    private int currrentChoice=0;
    private String[] option = {"Start","Help","Quit"};
    private Color titleColor;
    private Font titleFont;
    private Font font;

    public MenuState(GameStateManager gsm){
        this.gsm=gsm;
        try{
            bg= new Background("/Resources/Backgrounds/menubg.gif",1);
            bg.setVector(-0.1,0);

            titleColor= new Color(128,0,0);
            titleFont = new Font("Century Gothic",Font.PLAIN,28);

            font = new Font("Arial", Font.PLAIN,12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {}

    @Override
    public void update() {
        bg.update();
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);

        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("DragonTale",80,70);

        g.setFont(font);
        for (int i =0; i< option.length; i++){
           if(i==currrentChoice){
               g.setColor(Color.BLACK);
           }else{
               g.setColor(Color.RED);
           }
           g.drawString(option[i],145,140+i*15);
        }
    }

    private void select(){
        if(currrentChoice==0){
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
        if(currrentChoice==1) {
            //Help
        }
        if(currrentChoice==2) {
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int k) {
        if(k== KeyEvent.VK_ENTER){
           select();
        }
        if(k== KeyEvent.VK_UP){
            currrentChoice--;
            if(currrentChoice==-1){
                currrentChoice=option.length-1;
            }
        }
        if(k== KeyEvent.VK_DOWN){
            currrentChoice++;
            if(currrentChoice== option.length){
                currrentChoice=0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {}
}
