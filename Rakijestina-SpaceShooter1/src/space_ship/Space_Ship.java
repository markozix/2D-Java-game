package space_ship;

import java.awt.image.BufferedImage;

public class Space_Ship {

	//definisemo atrubite 
	private int health = 100;
	private int level = 1;
	
	
	//slika broda
	//treba naci sliku da se ubaci ovde
	private BufferedImage spaceShip;
	
	//Pozicija broda i ugao
	private double posX = 500, posY= 500, spaceShipAngle = 0.0;
	
	//brzine kretanja
	private double spaceShipRotationSpeed = 0.07;
	private double spaceShipMoveSpeed = 6.0;
	
	

	public Space_Ship() {
		super();
		this.health = health;
		this.level = level;
		this.spaceShip = spaceShip;
		this.posX = posX;
		this.posY = posY;
		this.spaceShipAngle = spaceShipAngle;
		this.spaceShipRotationSpeed = spaceShipRotationSpeed;
		this.spaceShipMoveSpeed = spaceShipMoveSpeed;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}

	public BufferedImage getSpaceShip() {
		return spaceShip;
	}
	public void setSpaceShip(BufferedImage spaceShip) {
		this.spaceShip = spaceShip;
	}
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	public double getSpaceShipAngle() {
		return spaceShipAngle;
	}
	public void setSpaceShipAngle(double spaceShipAngle) {
		this.spaceShipAngle = spaceShipAngle;
	}
	public double getSpaceShipRotationSpeed() {
		return spaceShipRotationSpeed;
	}
	public void setSpaceShipRotationSpeed(double spaceShipRotationSpeed) {
		this.spaceShipRotationSpeed = spaceShipRotationSpeed;
	}
	public double getSpaceShipMoveSpeed() {
		return spaceShipMoveSpeed;
	}
	public void setSpaceShipMoveSpeed(double spaceShipMoveSpeed) {
		this.spaceShipMoveSpeed = spaceShipMoveSpeed;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
	
	
	
	
	
}
