package asteroid;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import rafgfxlib.Util;

public class Asteroid {
	private double asteroidAngle = 0.0;
	private double asteroidRotationSpeed = 0.07;
	private double asteroidMoveSpeed = 6.0;
	private int posx;
	private int posy;
	//private BufferedImage slika;
	private boolean isAlive = false;
	private double speed;
	private int destX;
	private int destY;
	private int razlika;
	private double pocetak = System.currentTimeMillis();
	private double smrt = System.currentTimeMillis();
	private int width = 200;
	private int height = 100;
	private boolean daLiJeUnisten = false;
	//stack owerlof
	float deltaX;
	float deltaY;
	float ugao;
	private boolean uKoliziji = false;
	private BufferedImage slikaAsteroida = Util.loadImage("asteroid.png");
	public Asteroid() {
		super();
		//this.slika = Util.loadImage("C:\\Users\\Marko\\Desktop\\Asteroid generator (and a set of generated asteroids) _ OpenGameArt.org_files\\asteroid.png");
		this.posx = (int) (Math.random()* 900);
		this.posy = 0;
		this.speed= Math.random() * 4 + 5;
		this.destX = (int)(Math.random()* 900);
		this.destY = 900;
		this.deltaX = destX - posx;
		this.deltaY = Math.abs(destY - posy);
		this.ugao = (float)Math.atan2(deltaY, deltaX);
		this.asteroidMoveSpeed = asteroidMoveSpeed;
		this.asteroidRotationSpeed = asteroidRotationSpeed;
	}
/*Stare metode	
	public void asteroidReborn()
	{
		posx = (int) (Math.random()* 900);
		posy = 0;
		speed= Math.random() * 4 + 5;
		destX = (int)(Math.random()* 900);
		destY = 900;
		deltaX = destX - posx;
		deltaY = Math.abs(destY - posy);
		ugao = (float)Math.atan2(deltaY, deltaX);
		
	}
	
	public void spawn()
	{
		checkRange();
		if(this.isAlive) {
		posx+=speed * Math.cos(ugao);
		posy+= speed * Math.sin(ugao);
		}
	
	}
	public void checkRange() {
		if(this.getPosx() < 0 || this.getPosx() > 950) 
			{
			 this.setAlive(false);
			 asteroidReborn();
			}
		if(this.getPosy() > 950) 
			{
			 this.setAlive(false);	
			 asteroidReborn();
			}
	}
*/
	
	public int getPosx() {
		return posx;
	}

	public BufferedImage getSlikaAsteroida() {
		return slikaAsteroida;
	}

	public void setSlikaAsteroida(BufferedImage slikaAsteroida) {
		this.slikaAsteroida = slikaAsteroida;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public double getSpeed() {
		return speed;
	}



	public void setSpeed(double speed) {
		this.speed = speed;
	}



	public int getDestX() {
		return destX;
	}



	public void setDestX(int destX) {
		this.destX = destX;
	}



	public int getDestY() {
		return destY;
	}



	public double getAsteroidAngle() {
		return asteroidAngle;
	}

	public void setAsteroidAngle(double asteroidAngle) {
		this.asteroidAngle = asteroidAngle;
	}

	public void setDestY(int destY) {
		this.destY = destY;
	}

	

	public boolean isDaLiJeUnisten() {
		return daLiJeUnisten;
	}

	public void setDaLiJeUnisten(boolean daLiJeUnisten) {
		this.daLiJeUnisten = daLiJeUnisten;
	}

	public float getDeltaX() {
		return deltaX;
	}



	public void setDeltaX(float deltaX) {
		this.deltaX = deltaX;
	}



	public float getDeltaY() {
		return deltaY;
	}



	public void setDeltaY(float deltaY) {
		this.deltaY = deltaY;
	}



	public float getUgao() {
		return ugao;
	}



	public void setUgao(float ugao) {
		this.ugao = ugao;
	}
	public boolean isuKoliziji() {
		return uKoliziji;
	}
	public void setuKoliziji(boolean uKoliziji) {
		this.uKoliziji = uKoliziji;
	}

	public double getAsteroidRotationSpeed() {
		return asteroidRotationSpeed;
	}

	public void setAsteroidRotationSpeed(double asteroidRotationSpeed) {
		this.asteroidRotationSpeed = asteroidRotationSpeed;
	}

	public double getAsteroidMoveSpeed() {
		return asteroidMoveSpeed;
	}

	public void setAsteroidMoveSpeed(double asteroidMoveSpeed) {
		this.asteroidMoveSpeed = asteroidMoveSpeed;
	}






}
	
	
	
