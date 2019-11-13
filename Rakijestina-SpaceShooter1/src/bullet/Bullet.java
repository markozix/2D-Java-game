package bullet;

import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import rafgfxlib.Util;

public class Bullet {

	
	//mora se napraviti klasa 
	//pa probati pozicionirati iznad brodica nekako sa getx() i getY()
	
	private BufferedImage img = Util.loadImage("bullet.png");
	
	private int damage = 50;
	private double speed = 10.0;
	
	
	public Bullet() {
		super();
		this.img = img;
		this.damage = damage;
		this.speed = speed;
	}
	
	
	public BufferedImage getImg() {
		return img;
	}
	public void setImg(BufferedImage img) {
		this.img = img;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	
	
	
	
	
	
	
	
}
