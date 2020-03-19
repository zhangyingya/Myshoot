package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 小敌机: 是飞行物，也是敌人能得分 */
public class Airplane extends FlyingObject implements Enemy {
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[4];
		for (int i = 0; i < images.length; i++) {
			images[i] = loadImage("airplane" + i + ".png");
		}
	}
	private int speed;//移动速度

	/** 构造方法 */
	public Airplane() {
		super(49, 36);
		speed = 2;
	}

	/** 重写step()移动 */
	public void step() {
		y += speed;//y+(向下)
	}

	int index = 1;
	/** 重写getImage()图片 */
	public BufferedImage getImage() {//每10毫秒走一次
		if (isLife()) {//若活着的
			return images[0];//直接返回images[0]
		} else if (isDead()) {// 死了的
			BufferedImage img = images[index++];//获取第2张到第5张图
			if (index == images.length) {//到最后一张图了
				state = REMOVE;//则将当前修改修改为删除的状态
			}
			return img;
		}
		return null;//删除状态时，返回null
	}
	/*
	 * index=1 10M img=image[1] index=2 返回image[1] 20M img=image[2] index=3
	 * 返回image[2] 30M img=image[3] index=4 返回image[3] 40M img=image[4] index=5
	 * 返回image[4] 50M 返回null
	 */
	
	/** 重写getScore()得分 */	
public int getScore(){
	return 1;//打掉小敌机，玩家得1分
}
}
