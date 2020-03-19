package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** 小蜜蜂: 是飞行物，也是奖励 */
public class Bee extends FlyingObject implements Award{
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for (int i = 0; i < images.length; i++) {
			images[i] = loadImage("bee" + i + ".png");
		}
	}
	private int xSpeed; // x坐标移动速度
	private int ySpeed; // y坐标移动速度
	private int awardType; //奖励类型(0或1)

	/** 构造方法 */
	public Bee() {
		super(60, 50);
		xSpeed = 1;
		ySpeed = 2;
		awardType = (int) (Math.random()*2);
	}

	/** 重写step()移动 */
	public void step() {
		x += xSpeed;
		y += ySpeed;
		if (x <= 0 || x >= World.WIDTH - this.width) {// 若小蜜蜂到两边了
			xSpeed *= -1;// 则正变负，负变正(调换方向)
		}
	}

	int index = 1;
	/** 重写getImage()获取图片 */
	public BufferedImage getImage() {//每10毫秒走一次
		if (isLife()) {//若活着的
			return images[0];//直接返回images[0]
		} else if (isDead()) {//若死了的
			BufferedImage img = images[index++];//获取第2张到第5张图
			if (index == images.length) {//到最后一张图了
				state = REMOVE;//则将当前修改修改为删除的状态
			}
			return img;
		}
		return null;//删除状态时，返回null
	}
	
	/** 重写getAwardType()获取奖励 */
	public int getAwardType(){
		return awardType;//返回奖励类型
	}
}
