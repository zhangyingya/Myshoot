package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 大敌机: 是飞行物，也是敌人能得分 */
public class BigAirplane extends FlyingObject implements Enemy {
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for (int i = 0; i < images.length; i++) {
			images[i] = loadImage("bigplane" + i + ".png");
		}
	}
	private int speed;//移动速度

	/** 构造方法 */
	public BigAirplane() {
		super(69, 99);
		speed = 2;
	}

	/** 重写step()移动 */
	public void step() {
		y += speed;//y+(向下)
	}

	int index = 1;
	/** 重写getImage()获取图片 */
	public BufferedImage getImage() {//每10毫秒走一次
		if (isLife()) {//若活着的
			return images[0];//直接返回images[0]
		} else if (isDead()) {//若死了的
			BufferedImage img = images[index++];//获取第2张到第5张图
			if (index == images.length) {//到最后一张图了
				state = REMOVE;
			}
			return img;
		}
		return null;//删除状态时，返回null
	}
	
	/** 重写getScore()得分 */
	public int getScore(){
		return 3;//打掉大敌机，玩家得3分
	}
}
