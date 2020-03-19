package cn.tedu.shoot;

import java.awt.image.BufferedImage;
/** 英雄机: 是飞行物 */
public class Hero extends FlyingObject {
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[2];
		images[0] = loadImage("hero0.png");
		images[1] = loadImage("hero1.png");
	}
	private int life; // 命
	private int doubleFire; // 火力值

	/** 构造方法 */
	public Hero() {
		super(97, 124, 140, 400);
		life = 3;
		doubleFire = 0;
	}

	/** 重写step()移动 */
	public void step() {	
	}

	int index = 0;// 下标
	/** 重写getImage()获取图片 */
	public BufferedImage getImage() {//每10毫秒走一次
		if (isLife()) {//若活着的
			return images[index++ % images.length];
		} else if (isDead()) {// 死了的
			state = REMOVE;
		}
		return null;
	}

	/** 英雄机发射子弹(生成子弹对象) */
	public Bullet[] shoot() {
		int xStep=this.width/4;//1/4英雄机的宽
		int yStep=10;//固定的值
		if (doubleFire > 0) {//双
			Bullet[] bs = new Bullet[4];//2发子弹
			bs[0]=new Bullet(this.x+1*xStep,this.y-yStep);//x:英雄机的x+1/4英雄机的宽 y:英雄机的y-固定的20
			bs[1]=new Bullet(this.x+2*xStep,this.y-yStep);
			bs[2]=new Bullet(this.x+3*xStep,this.y-yStep);//x:英雄机的x+3/4英雄机的宽 y:英雄机的y-固定的20
			bs[3]=new Bullet(this.x+4*xStep,this.y-yStep);
			
			doubleFire-=2;//发射一次双倍火力，则火力值减2
			return bs;
		} else {//单
			Bullet[] bs = new Bullet[1];//1发子弹
			bs[0]=new Bullet(this.x+2*xStep,this.y-yStep);//x:英雄机的x+2/4英雄机的宽 y:英雄机的y-固定的20
			return bs;
		}
	}
	/*
	 * index=0 10M 返回image[0] index=1 20M 返回image[1] index=2 30M 返回image[0]
	 * image=3 40M 返回image[1] index=4 50M 返回image[0] index=5 60M 返回image[1]
	 * index=6
	 * 
	 */

	/** 英雄机随着鼠标动 x:鼠标的x坐标 y:鼠标的y坐标 */
	public void moveTo(int x, int y) {
		this.x=x-this.width /2;//英雄机的x=鼠标x减去英雄机的宽
		this.y=y-this.height/2;//英雄机的y=鼠标y减去英雄机的高		
	}
	
	/**英雄机增命*/
	public void addLife(){
		life++;
	}
	
	/**获取英雄机的命*/
	public int getLife(){
		return life;
	}
	
	/**英雄机减命*/
	public void subtractLife(){
		life--;//命数减1
	}
	
	/**英雄机增火力*/
	public void addDoubleFire(){
		doubleFire+=40;
	}
	
	/**清空英雄机火力值*/
	public void clearDoubleFire(){
		doubleFire=0;//火力清零
	}
}
