package tank;

import java.awt.*;
import java.util.Random;

public class Explode {
    private int x, y;
    private boolean live = true;
    TankFrame tf = null;
    private int step = 0;

    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();


    public Explode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
    }

    public void paint(Graphics g){
        g.drawImage(ResourceMgr.explodes[step++], x, y, null);
        if(step >= ResourceMgr.explodes.length) step = 0;
    }

}
