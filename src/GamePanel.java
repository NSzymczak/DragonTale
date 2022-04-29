
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH=320;
    public static final int HEIGHT = 240;
    public static final int SCALE=2;

    private Thread thread;
    private boolean running;
    private int FPS;
    private long targetTime =1000/FPS;

    private BufferedImage image;
    private Graphics2D g;

    public GamePanel(){
        super();
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify(){
        super.addNotify();
        if(thread==null){
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }
    public void init(){
        image = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g=(Graphics2D) g;
        running =true;
    }

    @Override
    public void run() {
        init();

        long start, elapsed,wait;
        while(running){

            start=System.nanoTime();

            update();
            draw();
            drawToScreen();
        }
    }

    private void update(){

    }
    private void draw(){

    }
    private void drawToScreen(){
        Graphics g2=getGraphics();
        g2.drawImage(image,0,0,null);
        g2.dispose();
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
