package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** С�۷�: �Ƿ����Ҳ�ǽ��� */
public class Bee extends FlyingObject implements Award{
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for (int i = 0; i < images.length; i++) {
			images[i] = loadImage("bee" + i + ".png");
		}
	}
	private int xSpeed; // x�����ƶ��ٶ�
	private int ySpeed; // y�����ƶ��ٶ�
	private int awardType; //��������(0��1)

	/** ���췽�� */
	public Bee() {
		super(60, 50);
		xSpeed = 1;
		ySpeed = 2;
		awardType = (int) (Math.random()*2);
	}

	/** ��дstep()�ƶ� */
	public void step() {
		x += xSpeed;
		y += ySpeed;
		if (x <= 0 || x >= World.WIDTH - this.width) {// ��С�۷䵽������
			xSpeed *= -1;// �����为��������(��������)
		}
	}

	int index = 1;
	/** ��дgetImage()��ȡͼƬ */
	public BufferedImage getImage() {//ÿ10������һ��
		if (isLife()) {//�����ŵ�
			return images[0];//ֱ�ӷ���images[0]
		} else if (isDead()) {//�����˵�
			BufferedImage img = images[index++];//��ȡ��2�ŵ���5��ͼ
			if (index == images.length) {//�����һ��ͼ��
				state = REMOVE;//�򽫵�ǰ�޸��޸�Ϊɾ����״̬
			}
			return img;
		}
		return null;//ɾ��״̬ʱ������null
	}
	
	/** ��дgetAwardType()��ȡ���� */
	public int getAwardType(){
		return awardType;//���ؽ�������
	}
}
