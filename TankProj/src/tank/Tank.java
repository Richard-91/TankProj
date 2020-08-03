package tank;

import java.awt.*;
import java.util.Random;

public class Tank {
    public static int WIDTH = ResourceMgr.goodTankD.getWidth();
    public static int HEIGHT = ResourceMgr.goodTankD.getHeight();

    private int x = 200, y = 200;
    private Dir dir = Dir.DOWN;
    private static final int SPEED = 5;
    private boolean moving = true;
    private TankFrame tf = null;
    private boolean live = true;
    private Group group = Group.BAD;
    private Random random = new Random();

    Rectangle rect = null;

    public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
        rect = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g){
        if(!live) tf.tanks.remove(this);
        switch(dir){
            case LEFT:
                g.drawImage(this.group == Group.BAD ? ResourceMgr.badTankL : ResourceMgr.goodTankL, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.BAD ? ResourceMgr.badTankU : ResourceMgr.goodTankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.BAD ? ResourceMgr.badTankR : ResourceMgr.goodTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.BAD ? ResourceMgr.badTankD : ResourceMgr.goodTankD, x, y, null);
                break;
        }


        move();
    }

    private void move() {
        if(!moving) return;
        switch (dir){
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }

        //敌方坦克随机发射子弹
        if(this.group == Group.BAD && (random.nextInt(100) > 90))
            this.fire();

        //敌方坦克随机改变方向
        if(this.group == Group.BAD && random.nextInt(100) > 95)
            randomDir();

        //边缘检测
        boundsCheck();

        //更新rect
        rect.x = this.x;
        rect.y = this.y;
    }

    private void boundsCheck() {
        if(x < 0) x = 0;
        if(y < 30) y = 30;//上面菜单条占据一些像素
        if(x > TankFrame.GAME_WIDTH - Tank.WIDTH) x = TankFrame.GAME_WIDTH - Tank.WIDTH;
        if(y > TankFrame.GAME_HEIGHT - Tank.HEIGHT) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT;
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        int bX = this.x + this.WIDTH/2 - Bullet.WIDTH/2;
        int bY = this.y + this.HEIGHT/2 - Bullet.HEIGHT/2;
        tf.bullets.add(new Bullet(bX, bY, this.dir, this.group, this.tf));
    }

    public void die() {
        this.live = false;
    }
}
