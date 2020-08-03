package tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TankFrame extends Frame {
    static final int GAME_WIDTH = 1300, GAME_HEIGHT = 800;
    Tank myTank = new Tank(200,400, Dir.DOWN, Group.GOOD, this);
    List<Bullet> bullets = new ArrayList<>();
    List<Tank> tanks = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();

    public TankFrame(){
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setResizable(false);
        this.setTitle("tank war");
        this.setVisible(true);

        this.addKeyListener(new MyKeyListener());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    //解决坦克闪烁问题，直接用就行，不必深究
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        //画出提示字符串
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量："+bullets.size(), 10,60);
        g.drawString("敌人的数量："+tanks.size(), 10,80);
        g.drawString("爆炸的数量："+explodes.size(), 10,100);
        g.setColor(c);


        //画出坦克
        myTank.paint(g);


        //画出子弹
        //用集合内部的迭代器iterator进行迭代，不能在迭代器外部进行删除，只能允许迭代器自身进行删除
        //如果用了下面迭代器的方法，就无法在其他地方删除子弹的对象
        //只能用普通的for循环方式画出子弹
//        for(Bullet b : bullets){
//            b.paint(g);
//        }
        for (int i=0; i<bullets.size(); i++){
            bullets.get(i).paint(g);
        }
//        for(Iterator<Bullet> it = bullets.iterator(); it.hasNext();){
//            Bullet b = it.next();
//            if(!b.live) it.remove();
//        }


        //画出敌人坦克
        for(int i=0; i<tanks.size(); i++){
            tanks.get(i).paint(g);
        }

        //画出爆炸
        for(int i=0; i<explodes.size(); i++){
            explodes.get(i).paint(g);
        }

        //子弹和坦克碰撞检测
        for(int i=0; i<bullets.size(); i++){
            for(int j=0; j<tanks.size(); j++){
                bullets.get(i).collideWith(tanks.get(j));
            }
        }

    }

    class MyKeyListener extends KeyAdapter{
        boolean bL = false;
        boolean bR = false;
        boolean bU = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
//            x += 20;
//            repaint();
            int keyCode = e.getKeyCode();
            switch (keyCode){
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }

            setMainTankDir();
        }



        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            if(!bL && !bU && !bR && !bD){
                myTank.setMoving(false);
            }else{
                myTank.setMoving(true);
                if(bL) myTank.setDir(Dir.LEFT);
                if(bU) myTank.setDir(Dir.UP);
                if(bR) myTank.setDir(Dir.RIGHT);
                if(bD) myTank.setDir(Dir.DOWN);
            }

        }

    }
}
