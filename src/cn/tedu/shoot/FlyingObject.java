package cn.tedu.shoot;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
/** 飞行物 */
public abstract class FlyingObject {
	public static final int LIFE = 0;//活着的
	public static final int DEAD = 1;//死了的
	public static final int REMOVE = 2;//删除的	
	protected int state = LIFE;//当前状态(默认为活着的)
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	/** 专门给英雄机、天空、子弹提供的 */
	public FlyingObject(int width, int height, int x, int y) {
		super();
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}

	/** 专门给小敌机、 大敌机 、小蜜蜂提供的 */
	public FlyingObject(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		x = (int) (Math.random() * (World.WIDTH - this.width));//x:0到(窗口宽-小敌机宽)之间的随机数
		y = -this.height;//y:负的小敌机的高
	}

	/** 读取图片 */
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));//同包中读文件
			return img;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/** 飞行物移动 */
	public abstract void step();

	/** 获取对象的图片 */
	public abstract BufferedImage getImage();

	/** 判断是否活着的 */
	public boolean isLife() {
		return state == LIFE;//当前状态为LIFE，则表示活着的
	}

	/** 判断是否死了 */
	public boolean isDead() {
		return state == DEAD;//当前状态为DEAD，则表示死了的
	}

	/** 判断是否删除 */
	public boolean isRemove() {
		return state == REMOVE;//当前状态为REMOVE，则表示删除的
	}

	/** 画对象  g:画笔 */
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y, null);
	}
	
	/**检查是否越界*/
	public  boolean outOfBounds() {
		return this.y>=World.HEIGHT;//敌人的y>=窗口的高，即为越界了
	}
	
	/**检查碰撞this:敌人  other：子弹*/
	public boolean hit(FlyingObject other){
		int  x1=this.x-other.width;
		int  x2=this.x+this.width;
		int  y1=this.y-other.height;
		int  y2=this.y+this.height;
		int x=other.x;//子弹的x坐标
		int y=other.y;//子弹的y坐标
		return x>=x1 && x<=x2
				&&
				y>=y1 && y<=y2;//x在x1和x2之间
	}
	/**飞行物死亡*/
	public void goDead(){
		state=DEAD;//将当前状态修改为DEAD(死了)
	}
	
}
