package cn.tedu.shoot;

import java.awt.image.BufferedImage;
/** Ӣ�ۻ�: �Ƿ����� */
public class Hero extends FlyingObject {
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[2];
		images[0] = loadImage("hero0.png");
		images[1] = loadImage("hero1.png");
	}
	private int life; // ��
	private int doubleFire; // ����ֵ

	/** ���췽�� */
	public Hero() {
		super(97, 124, 140, 400);
		life = 3;
		doubleFire = 0;
	}

	/** ��дstep()�ƶ� */
	public void step() {	
	}

	int index = 0;// �±�
	/** ��дgetImage()��ȡͼƬ */
	public BufferedImage getImage() {//ÿ10������һ��
		if (isLife()) {//�����ŵ�
			return images[index++ % images.length];
		} else if (isDead()) {// ���˵�
			state = REMOVE;
		}
		return null;
	}

	/** Ӣ�ۻ������ӵ�(�����ӵ�����) */
	public Bullet[] shoot() {
		int xStep=this.width/4;//1/4Ӣ�ۻ��Ŀ�
		int yStep=10;//�̶���ֵ
		if (doubleFire > 0) {//˫
			Bullet[] bs = new Bullet[4];//2���ӵ�
			bs[0]=new Bullet(this.x+1*xStep,this.y-yStep);//x:Ӣ�ۻ���x+1/4Ӣ�ۻ��Ŀ� y:Ӣ�ۻ���y-�̶���20
			bs[1]=new Bullet(this.x+2*xStep,this.y-yStep);
			bs[2]=new Bullet(this.x+3*xStep,this.y-yStep);//x:Ӣ�ۻ���x+3/4Ӣ�ۻ��Ŀ� y:Ӣ�ۻ���y-�̶���20
			bs[3]=new Bullet(this.x+4*xStep,this.y-yStep);
			
			doubleFire-=2;//����һ��˫�������������ֵ��2
			return bs;
		} else {//��
			Bullet[] bs = new Bullet[1];//1���ӵ�
			bs[0]=new Bullet(this.x+2*xStep,this.y-yStep);//x:Ӣ�ۻ���x+2/4Ӣ�ۻ��Ŀ� y:Ӣ�ۻ���y-�̶���20
			return bs;
		}
	}
	/*
	 * index=0 10M ����image[0] index=1 20M ����image[1] index=2 30M ����image[0]
	 * image=3 40M ����image[1] index=4 50M ����image[0] index=5 60M ����image[1]
	 * index=6
	 * 
	 */

	/** Ӣ�ۻ�������궯 x:����x���� y:����y���� */
	public void moveTo(int x, int y) {
		this.x=x-this.width /2;//Ӣ�ۻ���x=���x��ȥӢ�ۻ��Ŀ�
		this.y=y-this.height/2;//Ӣ�ۻ���y=���y��ȥӢ�ۻ��ĸ�		
	}
	
	/**Ӣ�ۻ�����*/
	public void addLife(){
		life++;
	}
	
	/**��ȡӢ�ۻ�����*/
	public int getLife(){
		return life;
	}
	
	/**Ӣ�ۻ�����*/
	public void subtractLife(){
		life--;//������1
	}
	
	/**Ӣ�ۻ�������*/
	public void addDoubleFire(){
		doubleFire+=40;
	}
	
	/**���Ӣ�ۻ�����ֵ*/
	public void clearDoubleFire(){
		doubleFire=0;//��������
	}
}
