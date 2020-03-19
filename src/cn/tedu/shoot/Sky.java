package cn.tedu.shoot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/** ���: �Ƿ����� */
public class Sky extends FlyingObject {
	private static BufferedImage image;
	static {
		image = loadImage("background.png");
	}
	private int speed;//�ƶ��ٶ�
	private int y1; //��2��ͼƬ��y����

	/** ���췽�� */
	public Sky() {
		super(World.WIDTH, World.HEIGHT, 0, 0);
		speed = 1;
		y1 = -this.height;
	}

	/** ��дstep()�ƶ� */
	public void step() {
		y += speed;//y+(����)
		y1 += speed;//y1+(����)
		if (y >= World.HEIGHT) {//��y>=���ڵĸߣ���ʾ�Ѿ��Ƴ�ȥ��
			y = -World.HEIGHT;//��y�޸�Ϊ���Ĵ��ڵĸ�(Ų��������ȥ)
		}
		if (y1 >= World.HEIGHT) {//��y1>=���ڵĸߣ���ʾ�Ѿ��Ƴ�ȥ��
			y1 = -World.HEIGHT;//��y1�޸�Ϊ���Ĵ��ڵĸ�(Ų��������ȥ)
		}
	}

	/** ��дgetImage() */
	public BufferedImage getImage() {
		return image;//ֱ�ӷ���image����
	}

	/** ��дpaintObject()������ */
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y, null);// ��һ��ͼƬ
		g.drawImage(getImage(), x, y1, null);// �ڶ���ͼƬ
	}

}
