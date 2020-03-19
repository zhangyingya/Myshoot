package cn.tedu.shoot;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
/** 整个游戏世界 */
/**
 * @author Administrator
 *
 */
public class World extends JPanel {
	public static final int WIDTH = 400;//窗口的宽
	public static final int HEIGHT = 700;//窗口的高
	public static final int START=0;//启动
	public static final int RUNNING=1;//运行
	public static final int PAUSE=2;//暂停
	public static final int GAME_OVER=3;//游戏结束
	private int state=START;//当前状态
	
	private static BufferedImage start;
	private static BufferedImage pause;
	private static BufferedImage gameover;
	static {
		start=FlyingObject.loadImage("start.png");
		pause=FlyingObject.loadImage("pause.png");
		gameover=FlyingObject.loadImage("gameover.png");
	}
	private Sky sky = new Sky();//天空
	private Hero hero = new Hero();// 英雄机
	private FlyingObject[] enemies = {};//敌人(小敌机、大敌机、小蜜蜂)数组	
	private Bullet[] bullets = {};//子弹对象	

	/** 生成敌人对象 */
	public FlyingObject nextOne() {
		Random rand = new Random();//随机数对象
		int type = rand.nextInt(20);//0到19之间
		if (type < 5) {//0到4，生成小蜜蜂
			return new Bee();
		} else if (type < 12) {//5到11，生成小敌机
			return new Airplane();
		} else {//12到19，生成大敌机
			return new BigAirplane();
		}
	}

	int enterIndex = 0;// 敌人入场计数
	/** 敌人(小敌机、大敌机、小蜜蜂)入场 */
	public void enterAction() {//每10毫秒走一次
		enterIndex++;//每10毫秒增1
		if (enterIndex % 40 == 0) {//每400(40*10)毫秒走一次
			FlyingObject obj = nextOne();//获取敌人对象
			enemies = Arrays.copyOf(enemies, enemies.length + 1);//扩容
			enemies[enemies.length - 1] = obj;//将敌人添加到enemies最后一个元素上
		}
	}

	int shootIndex = 0;//子弹入场计数
	/** 子弹入场 */
	public void shootAction() {//每10毫秒走一次
		shootIndex++;// 每10毫秒增1
		if (shootIndex % 30 == 0) {//每300(30*10)毫秒走一次
			Bullet[] bs = hero.shoot();//获取子弹对象
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);//扩容(bs有几个元素就扩大几个容量)
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);//数组的追加
		}
	}

	/** 飞行物移动 */
	public void stepAction() {//每10毫秒走一次
		sky.step();// 天空动
		for (int i = 0; i < enemies.length; i++) {//遍历所有敌人
			enemies[i].step();// 敌人动
		}
		for (int i = 0; i < bullets.length; i++) {//遍历所有子弹
			bullets[i].step();// 子弹动
		}
	}

	/** 删除越界的敌人和子弹 */
	public void outOfBoundsAction() { //每10毫秒走一次
		int index = 0;// 1)不越界敌人数组的下标 2)不越界敌人的个数
		FlyingObject[] enemyLives = new FlyingObject[enemies.length];//不越界敌人数组
		for (int i = 0; i < enemies.length; i++) {// 遍历所有敌人
			FlyingObject f = enemies[i];// 获取每一个敌人
			if (!f.outOfBounds() && !f.isRemove()) {// 不越界
				enemyLives[index] = f;// 将不越界敌人对象添加不越界敌人数组中
				index++;// 1)不越界敌人数组下标增一 2)不越界敌人个数增一
			}
		}
		enemies = Arrays.copyOf(enemyLives, index);// 将不越界敌人数组赋值到enemies中，index为几即enemies为几
		index = 0;// 1)下标归零 2)个数归零
		Bullet[] bulletLives = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds() && !b.isRemove()) {
				bulletLives[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);		
	}
	
	/**子弹与敌人碰撞*/
	int score=0;
	public void BulletBangAction(){
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			for(int j=0;j<enemies.length;j++){
				FlyingObject f=enemies[j];
				if(b.isLife() && f.isLife() && f.hit(b)){
					b.goDead();//子弹去死
					f.goDead();//敌人去死
					if(f instanceof Enemy){//若被撞敌人能得分
						Enemy e=(Enemy)f;//则强转为得分
						score +=e.getScore();//玩家得分
						
					}
					if(f instanceof Award){//若被撞敌人为奖励
						Award a=(Award)f;
						int type=a.getAwardType();
						switch (type) {//根据奖励类型让英雄机得分
						case Award.DOUBLE_FIRE:
							hero.addDoubleFire();//英雄机得双倍火力值
							break;

						case Award.LIFE:
							hero.addLife();//英雄机增命
							break;
						}
					}
				}
				System.out.println(score);
			}
		}
	}
	
	/**英雄机与敌人的碰撞*/
	public void heroBangAction(){//每10毫秒走一次
		for(int i=0;i<enemies.length;i++){
			FlyingObject f=enemies[i];//获取每一个敌人
			if(hero.isLife() && f.isLife() && f.hit( hero)){
				f.goDead();
				hero.subtractLife();
				hero.clearDoubleFire();
			}
		}
	}
	
	/**检测游戏结束*/
	public void checkGameOverAction(){
		if(hero.getLife()<=0){//游戏结束了
		state=GAME_OVER;
		}
	}

	/** 启动程序的执行 */
	public void action() {
		//鼠标侦听器对象
		MouseAdapter l = new MouseAdapter() {
			/** 重写mouseMoved()鼠标移动 */
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING){
					int x = e.getX();// 获得鼠标的x坐标
					int y = e.getY();// 获得鼠标的y坐标
					hero.moveTo(x, y);//英雄机随着鼠标移动
				}				
			}
			/**重写mouseClicked()鼠标点击*/
			public void mouseClicked(MouseEvent e) {
				switch(state){//根据当前状态做不同处理	
				case START:
					state=RUNNING;
					break;
				case GAME_OVER:
					score=0;
					sky=new Sky();
					hero=new Hero();
					enemies=new FlyingObject[0];
					bullets=new Bullet[0];
					state=START;//修改为启动状态
					break;
				}
			}
			/**重写mouseExited()鼠标移出*/
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){
					state=PAUSE;
				}
			}
			/**重写mouseEntered()鼠标移入*/
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){
					state=RUNNING;
				}
			}
		};
		this.addMouseListener(l); //处理鼠标操作事件
		this.addMouseMotionListener(l);// 处理鼠标滑动事件

		Timer timer = new Timer();// 定时器对象
		int intervel = 10;// 以毫秒为单位
		timer.schedule(new TimerTask() {		
			public void run() {//定时干的事--每10毫秒走一次
				if(state==RUNNING){
					enterAction();//敌人(小敌机、大敌机、小蜜蜂)入场
					shootAction();//子弹入场(英雄机发射子弹对象)
					stepAction();// 飞行物移动
					outOfBoundsAction();//删除越界的敌人和子弹
					//System.out.println(enemies.length + "," + bullets.length);
					BulletBangAction();
					heroBangAction();
					checkGameOverAction();//检测游戏结束
				}
				
				repaint();// 重画(调用paint())
			}
		}, intervel, intervel);// 定时计划
	}

	/** 重写paint()画 */
	public void paint(Graphics g) {
		sky.paintObject(g);//画天空
		hero.paintObject(g);//画英雄机
		for (int i = 0; i < enemies.length; i++) {//遍历所有敌人
			enemies[i].paintObject(g); //画敌人
		}
		for (int i = 0; i < bullets.length; i++) {//遍历所有子弹
			bullets[i].paintObject(g);//画子弹
		}
		g.drawString("SCORE:"+score,10,20);//画分
		g.drawString("LIFE:"+hero.getLife(),80,20);//画命
		switch (state) {
		case START:
			g.drawImage(start, 0, 0, null);
			break;

		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
			
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);// 将面板添加到JF
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 默认关闭操作
		frame.setSize(WIDTH, HEIGHT);// 大小
		frame.setLocationRelativeTo(null);// 设置窗口初始位置
		frame.setVisible(true); // (1)设置窗口可见(2)尽快调用paint
		world.action();// 启动执行
	}
}
