package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** С�л�: �Ƿ����Ҳ�ǵ����ܵ÷� */
public class Airplane extends FlyingObject implements Enemy {
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[4];
		for (int i = 0; i < images.length; i++) {
			images[i] = loadImage("airplane" + i + ".png");
		}
	}
	private int speed;//�ƶ��ٶ�

	/** ���췽�� */
	public Airplane() {
		super(49, 36);
		speed = 2;
	}

	/** ��дstep()�ƶ� */
	public void step() {
		y += speed;//y+(����)
	}

	int index = 1;
	/** ��дgetImage()ͼƬ */
	public BufferedImage getImage() {//ÿ10������һ��
		if (isLife()) {//�����ŵ�
			return images[0];//ֱ�ӷ���images[0]
		} else if (isDead()) {// ���˵�
			BufferedImage img = images[index++];//��ȡ��2�ŵ���5��ͼ
			if (index == images.length) {//�����һ��ͼ��
				state = REMOVE;//�򽫵�ǰ�޸��޸�Ϊɾ����״̬
			}
			return img;
		}
		return null;//ɾ��״̬ʱ������null
	}
	/*
	 * index=1 10M img=image[1] index=2 ����image[1] 20M img=image[2] index=3
	 * ����image[2] 30M img=image[3] index=4 ����image[3] 40M img=image[4] index=5
	 * ����image[4] 50M ����null
	 */
	
	/** ��дgetScore()�÷� */	
public int getScore(){
	return 1;//���С�л�����ҵ�1��
}
}
