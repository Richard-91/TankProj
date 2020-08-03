package tank;



public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();

        int initialTank = Integer.parseInt(PropertyMgr.get("initTankCount"));

        //初始化敌方坦克
        for(int i=0; i<initialTank; i++){
            tf.tanks.add(new Tank(50 + i * 80, 200, Dir.DOWN, Group.BAD, tf));
        }

        while (true){
            Thread.sleep(50);
            tf.repaint();
        }
    }
}
