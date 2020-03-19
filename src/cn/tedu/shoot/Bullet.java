package cn.tedu.shoot;
import java.awt.image.BufferedImage;
public class Bullet extends FlyingObject {
	private static BufferedImage image;
	static {
		image = loadImage("bullet.png");
	}
	private int speed;//移动速度

	/** 构造方法 */
	public Bullet(int x, int y) {
		super(8, 14, x, y);
		speed = 3;
	}

	/** 重写step()移动 */
	public void step() {
		y -= speed;
	}

	/** 重写getImage()获取图片 */
	public BufferedImage getImage() {
		if (isLife()) {// 活着的
			return image;
		} else if (isDead()) {// 死了的
			state = REMOVE;//将当前状态修改删除状态
		}
		return null;//死了的和删除的，则返回null
	}
	
	/** 重写outOfBounds()检测越界 */
	public boolean outOfBounds() {
		return this.y<=-this.height; //子弹的y<=负的子弹的高，即为越界了
	}
}
