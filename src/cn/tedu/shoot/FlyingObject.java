package cn.tedu.shoot;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
/** ������ */
public abstract class FlyingObject {
	public static final int LIFE = 0;//���ŵ�
	public static final int DEAD = 1;//���˵�
	public static final int REMOVE = 2;//ɾ����	
	protected int state = LIFE;//��ǰ״̬(Ĭ��Ϊ���ŵ�)
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	/** ר�Ÿ�Ӣ�ۻ�����ա��ӵ��ṩ�� */
	public FlyingObject(int width, int height, int x, int y) {
		super();
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}

	/** ר�Ÿ�С�л��� ��л� ��С�۷��ṩ�� */
	public FlyingObject(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		x = (int) (Math.random() * (World.WIDTH - this.width));//x:0��(���ڿ�-С�л���)֮��������
		y = -this.height;//y:����С�л��ĸ�
	}

	/** ��ȡͼƬ */
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));//ͬ���ж��ļ�
			return img;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/** �������ƶ� */
	public abstract void step();

	/** ��ȡ�����ͼƬ */
	public abstract BufferedImage getImage();

	/** �ж��Ƿ���ŵ� */
	public boolean isLife() {
		return state == LIFE;//��ǰ״̬ΪLIFE�����ʾ���ŵ�
	}

	/** �ж��Ƿ����� */
	public boolean isDead() {
		return state == DEAD;//��ǰ״̬ΪDEAD�����ʾ���˵�
	}

	/** �ж��Ƿ�ɾ�� */
	public boolean isRemove() {
		return state == REMOVE;//��ǰ״̬ΪREMOVE�����ʾɾ����
	}

	/** ������  g:���� */
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y, null);
	}
	
	/**����Ƿ�Խ��*/
	public  boolean outOfBounds() {
		return this.y>=World.HEIGHT;//���˵�y>=���ڵĸߣ���ΪԽ����
	}
	
	/**�����ײthis:����  other���ӵ�*/
	public boolean hit(FlyingObject other){
		int  x1=this.x-other.width;
		int  x2=this.x+this.width;
		int  y1=this.y-other.height;
		int  y2=this.y+this.height;
		int x=other.x;//�ӵ���x����
		int y=other.y;//�ӵ���y����
		return x>=x1 && x<=x2
				&&
				y>=y1 && y<=y2;//x��x1��x2֮��
	}
	/**����������*/
	public void goDead(){
		state=DEAD;//����ǰ״̬�޸�ΪDEAD(����)
	}
	
}
