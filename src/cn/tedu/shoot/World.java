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
/** ������Ϸ���� */
/**
 * @author Administrator
 *
 */
public class World extends JPanel {
	public static final int WIDTH = 400;//���ڵĿ�
	public static final int HEIGHT = 700;//���ڵĸ�
	public static final int START=0;//����
	public static final int RUNNING=1;//����
	public static final int PAUSE=2;//��ͣ
	public static final int GAME_OVER=3;//��Ϸ����
	private int state=START;//��ǰ״̬
	
	private static BufferedImage start;
	private static BufferedImage pause;
	private static BufferedImage gameover;
	static {
		start=FlyingObject.loadImage("start.png");
		pause=FlyingObject.loadImage("pause.png");
		gameover=FlyingObject.loadImage("gameover.png");
	}
	private Sky sky = new Sky();//���
	private Hero hero = new Hero();// Ӣ�ۻ�
	private FlyingObject[] enemies = {};//����(С�л�����л���С�۷�)����	
	private Bullet[] bullets = {};//�ӵ�����	

	/** ���ɵ��˶��� */
	public FlyingObject nextOne() {
		Random rand = new Random();//���������
		int type = rand.nextInt(20);//0��19֮��
		if (type < 5) {//0��4������С�۷�
			return new Bee();
		} else if (type < 12) {//5��11������С�л�
			return new Airplane();
		} else {//12��19�����ɴ�л�
			return new BigAirplane();
		}
	}

	int enterIndex = 0;// �����볡����
	/** ����(С�л�����л���С�۷�)�볡 */
	public void enterAction() {//ÿ10������һ��
		enterIndex++;//ÿ10������1
		if (enterIndex % 40 == 0) {//ÿ400(40*10)������һ��
			FlyingObject obj = nextOne();//��ȡ���˶���
			enemies = Arrays.copyOf(enemies, enemies.length + 1);//����
			enemies[enemies.length - 1] = obj;//��������ӵ�enemies���һ��Ԫ����
		}
	}

	int shootIndex = 0;//�ӵ��볡����
	/** �ӵ��볡 */
	public void shootAction() {//ÿ10������һ��
		shootIndex++;// ÿ10������1
		if (shootIndex % 30 == 0) {//ÿ300(30*10)������һ��
			Bullet[] bs = hero.shoot();//��ȡ�ӵ�����
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);//����(bs�м���Ԫ�ؾ����󼸸�����)
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);//�����׷��
		}
	}

	/** �������ƶ� */
	public void stepAction() {//ÿ10������һ��
		sky.step();// ��ն�
		for (int i = 0; i < enemies.length; i++) {//�������е���
			enemies[i].step();// ���˶�
		}
		for (int i = 0; i < bullets.length; i++) {//���������ӵ�
			bullets[i].step();// �ӵ���
		}
	}

	/** ɾ��Խ��ĵ��˺��ӵ� */
	public void outOfBoundsAction() { //ÿ10������һ��
		int index = 0;// 1)��Խ�����������±� 2)��Խ����˵ĸ���
		FlyingObject[] enemyLives = new FlyingObject[enemies.length];//��Խ���������
		for (int i = 0; i < enemies.length; i++) {// �������е���
			FlyingObject f = enemies[i];// ��ȡÿһ������
			if (!f.outOfBounds() && !f.isRemove()) {// ��Խ��
				enemyLives[index] = f;// ����Խ����˶�����Ӳ�Խ�����������
				index++;// 1)��Խ����������±���һ 2)��Խ����˸�����һ
			}
		}
		enemies = Arrays.copyOf(enemyLives, index);// ����Խ��������鸳ֵ��enemies�У�indexΪ����enemiesΪ��
		index = 0;// 1)�±���� 2)��������
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
	
	/**�ӵ��������ײ*/
	int score=0;
	public void BulletBangAction(){
		for(int i=0;i<bullets.length;i++){
			Bullet b=bullets[i];
			for(int j=0;j<enemies.length;j++){
				FlyingObject f=enemies[j];
				if(b.isLife() && f.isLife() && f.hit(b)){
					b.goDead();//�ӵ�ȥ��
					f.goDead();//����ȥ��
					if(f instanceof Enemy){//����ײ�����ܵ÷�
						Enemy e=(Enemy)f;//��ǿתΪ�÷�
						score +=e.getScore();//��ҵ÷�
						
					}
					if(f instanceof Award){//����ײ����Ϊ����
						Award a=(Award)f;
						int type=a.getAwardType();
						switch (type) {//���ݽ���������Ӣ�ۻ��÷�
						case Award.DOUBLE_FIRE:
							hero.addDoubleFire();//Ӣ�ۻ���˫������ֵ
							break;

						case Award.LIFE:
							hero.addLife();//Ӣ�ۻ�����
							break;
						}
					}
				}
				System.out.println(score);
			}
		}
	}
	
	/**Ӣ�ۻ�����˵���ײ*/
	public void heroBangAction(){//ÿ10������һ��
		for(int i=0;i<enemies.length;i++){
			FlyingObject f=enemies[i];//��ȡÿһ������
			if(hero.isLife() && f.isLife() && f.hit( hero)){
				f.goDead();
				hero.subtractLife();
				hero.clearDoubleFire();
			}
		}
	}
	
	/**�����Ϸ����*/
	public void checkGameOverAction(){
		if(hero.getLife()<=0){//��Ϸ������
		state=GAME_OVER;
		}
	}

	/** ���������ִ�� */
	public void action() {
		//�������������
		MouseAdapter l = new MouseAdapter() {
			/** ��дmouseMoved()����ƶ� */
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING){
					int x = e.getX();// �������x����
					int y = e.getY();// �������y����
					hero.moveTo(x, y);//Ӣ�ۻ���������ƶ�
				}				
			}
			/**��дmouseClicked()�����*/
			public void mouseClicked(MouseEvent e) {
				switch(state){//���ݵ�ǰ״̬����ͬ����	
				case START:
					state=RUNNING;
					break;
				case GAME_OVER:
					score=0;
					sky=new Sky();
					hero=new Hero();
					enemies=new FlyingObject[0];
					bullets=new Bullet[0];
					state=START;//�޸�Ϊ����״̬
					break;
				}
			}
			/**��дmouseExited()����Ƴ�*/
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){
					state=PAUSE;
				}
			}
			/**��дmouseEntered()�������*/
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){
					state=RUNNING;
				}
			}
		};
		this.addMouseListener(l); //�����������¼�
		this.addMouseMotionListener(l);// ������껬���¼�

		Timer timer = new Timer();// ��ʱ������
		int intervel = 10;// �Ժ���Ϊ��λ
		timer.schedule(new TimerTask() {		
			public void run() {//��ʱ�ɵ���--ÿ10������һ��
				if(state==RUNNING){
					enterAction();//����(С�л�����л���С�۷�)�볡
					shootAction();//�ӵ��볡(Ӣ�ۻ������ӵ�����)
					stepAction();// �������ƶ�
					outOfBoundsAction();//ɾ��Խ��ĵ��˺��ӵ�
					//System.out.println(enemies.length + "," + bullets.length);
					BulletBangAction();
					heroBangAction();
					checkGameOverAction();//�����Ϸ����
				}
				
				repaint();// �ػ�(����paint())
			}
		}, intervel, intervel);// ��ʱ�ƻ�
	}

	/** ��дpaint()�� */
	public void paint(Graphics g) {
		sky.paintObject(g);//�����
		hero.paintObject(g);//��Ӣ�ۻ�
		for (int i = 0; i < enemies.length; i++) {//�������е���
			enemies[i].paintObject(g); //������
		}
		for (int i = 0; i < bullets.length; i++) {//���������ӵ�
			bullets[i].paintObject(g);//���ӵ�
		}
		g.drawString("SCORE:"+score,10,20);//����
		g.drawString("LIFE:"+hero.getLife(),80,20);//����
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
		frame.add(world);// �������ӵ�JF
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Ĭ�Ϲرղ���
		frame.setSize(WIDTH, HEIGHT);// ��С
		frame.setLocationRelativeTo(null);// ���ô��ڳ�ʼλ��
		frame.setVisible(true); // (1)���ô��ڿɼ�(2)�������paint
		world.action();// ����ִ��
	}
}
