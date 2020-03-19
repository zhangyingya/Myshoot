package cn.tedu.shoot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/** 天空: 是飞行物 */
public class Sky extends FlyingObject {
	private static BufferedImage image;
	static {
		image = loadImage("background.png");
	}
	private int speed;//移动速度
	private int y1; //第2张图片的y坐标

	/** 构造方法 */
	public Sky() {
		super(World.WIDTH, World.HEIGHT, 0, 0);
		speed = 1;
		y1 = -this.height;
	}

	/** 重写step()移动 */
	public void step() {
		y += speed;//y+(向下)
		y1 += speed;//y1+(向下)
		if (y >= World.HEIGHT) {//若y>=窗口的高，表示已经移出去了
			y = -World.HEIGHT;//则将y修改为负的窗口的高(挪到最上面去)
		}
		if (y1 >= World.HEIGHT) {//若y1>=窗口的高，表示已经移出去了
			y1 = -World.HEIGHT;//则将y1修改为负的窗口的高(挪到最上面去)
		}
	}

	/** 重写getImage() */
	public BufferedImage getImage() {
		return image;//直接返回image即可
	}

	/** 重写paintObject()画对象 */
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y, null);// 第一张图片
		g.drawImage(getImage(), x, y1, null);// 第二张图片
	}

}
