package main;

import java.awt.image.BufferedImage;

import rafgfxlib.Util;

public class Zvezda{

	public float posX;
	public float posY;
	public float rot = 0.0f;
	public float angle = 0.0f;
	public int type;
	public int id = 0;
	public boolean alive;
	public BufferedImage img = Util.loadImage("/slike/slika.jpg");
	
	public Zvezda() {
		
	}

	public int getId() {
		return id;
	}
	public float getPosX() {
		return posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public void setPosY(float posY) {
		this.posY = posY;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getRot() {
		return rot;
	}
	public void setRot(float rot) {
		this.rot = rot;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}


}
