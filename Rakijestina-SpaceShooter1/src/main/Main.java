package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import asteroid.Asteroid;
import bullet.Bullet;
import rafgfxlib.*;
import space_ship.Space_Ship;
import testPaket.Vatra;

public class Main extends GameFrame {

	private Space_Ship ship = new Space_Ship();
	
	private Bullet bullet = new Bullet();
	private ArrayList<Graphics2D> municija = new ArrayList<>();
	
	private ArrayList<Bullet> lista = new ArrayList<>();
	private ArrayList<Zvezda> zvezde = new ArrayList<Zvezda>();
	
	//prozor fiksne velicine, po potrebi se moze 
	//smanjiti da odgovara manjim rezolucijama
	private static int x = 1000;
	private static int y = 1000;
	private int k = 0;
	//za menu bitno da mozemo da mijenjamo view koji se prikazuje
	private String state = "Main menu";
	private boolean sg = false;
	private boolean bulletActive = false;
	private boolean bActive = false;
	private int mode = 0;
	private int pause = 0;
	private int x1,y1,x2,y2,x3,y3;
	private int score = 0;
	private int brojac = 0;
	
	private String s = "Start game";
	private int positionX = 220;
	private int positionX1 = 220;
	private int positionX2 = 220;
	
	private double bulletX, bulletY;
	private double bulletDX,bulletDY;
	
	private double q,p,q1,p1;

	//metak kote
	private int i = (int) ship.getPosX() + 50,i1;
	
	AffineTransform shipTransform = new AffineTransform();
	AffineTransform bulletTransform = new AffineTransform();
	AffineTransform ast = new AffineTransform();
	
	private static final int STAR_MAX = 1000;
	
	private Star[] stars = new Star[STAR_MAX];
	
	private Color[] grayscale = new Color[256];
	
	private float speed = 10.0f;
	private static final float MAX_Z = 2000.0f;
	
	public static final long RUNNING_TIME = 2000;
	
	private BufferedImage asteroidImg = Util.loadImage("asteroid.png");
	private BufferedImage drugaSlika = Util.loadImage("spawnovan.png");
	private BufferedImage trecaSlika = Util.loadImage("jedan.png");
	private BufferedImage vatra = Util.loadImage("fire_prev.png");
	private Asteroid asteroidObj;
	private ArrayList<Asteroid> sviAsteroidi = new ArrayList<>();
	private double startTIme = System.currentTimeMillis();
	private double endTime = System.currentTimeMillis();
	
	private WritableRaster source  = asteroidImg.getRaster();
	private WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);
	int rgb[] = new int[3];
	int pixel[] = new int[3];
	

    private BufferedImage nizSlika[] = new BufferedImage[9];
    private int brojPlaneta = 9;
    private float alpha = 0f;
    private long startTime = -1;
    private boolean kredits = false;
    private boolean sc = false;
    private int indexP = 0;
	public Main() {
		
		super("Space-Shooter", x, y);
		
		setUpdateRate(60);
		startThread();
		popuniAsteroide();
		
		for(int i = 0; i < STAR_MAX; ++i)
		{
			stars[i] = new Star();
			stars[i].posX = (float)(Math.random() * 2000.0) - 1000.0f;
			stars[i].posY = (float)(Math.random() * 2000.0) - 1000.0f;
			stars[i].posZ = (float)(Math.random() * MAX_Z);
		}
		
		for(int i = 0; i < 256; ++i)
			grayscale[i] = new Color(i, i, i);
		
		for(int i = 0 ; i < brojPlaneta; i++)
			nizSlika[i]=Util.loadImage("/slike/"+String.valueOf(i+1)+".png");
		
	}

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
			
		new Main().initGameWindow();

	}
	
	public void popuniAsteroide()
	{
		for(int i = 0; i < 5; i++)
		{
			sviAsteroidi.add(new Asteroid());
		}
		
	}
	int ax = 0;
	public void spawn(Asteroid a)
	{
		checkRange(a);
		if(a.isAlive()) {
		if(a.isuKoliziji())	
		{	
			//obojiAsteroide();
			if(a.getPosx() - a.getDestX() > 0)
			a.setUgao(a.getUgao() - (float)1.5708);
			else a.setUgao(a.getUgao() + (float)1.5708);
			a.setuKoliziji(false);
		}
		a.setPosx(a.getPosx() + (int)(a.getSpeed() * Math.cos(a.getUgao())));
		a.setPosy(a.getPosy() + (int)(a.getSpeed() * Math.sin(a.getUgao())));
		}
	
	}
	public void checkRange(Asteroid a) {
		if(a.getPosx() < 0 || a.getPosx() > 950) 
			{
			 a.setAlive(false);
			 asteroidReborn(a);
			}
		if(a.getPosy() > 950) 
			{
			 a.setAlive(false);	
			 asteroidReborn(a);
			}
	}
	public void asteroidReborn(Asteroid a)
	{
		a.setPosx((int) (Math.random()* 900));
		for(Asteroid b : sviAsteroidi)
		{
			if(!b.isAlive()) {
				while((int)Math.abs(a.getPosx() - b.getPosx()) != 0 && (int)Math.abs(a.getPosx() - b.getPosx()) < 80)
				{
					a.setPosx((int) (Math.random()* 900));
					a.setSlikaAsteroida(asteroidImg);
				}
			}
		}
		
		a.setPosy(0);
		a.setSpeed(Math.random() * 4 + 5);
		a.setDestX((int)(Math.random()* 900));
		a.setDestY(900);
		a.setDeltaX(a.getDestX() - a.getPosx());
		a.setDeltaY(Math.abs(a.getDestY() - a.getPosy()));
		a.setUgao((float)Math.atan2(a.getDeltaY(), a.getDeltaX()));
		
	}
	private double startTIme1 = System.currentTimeMillis();
	private double endTime2 = System.currentTimeMillis();
	public void kolizija()
	{
		for(int i = 0; i < sviAsteroidi.size(); i++)
		{
			for(int j = 0; j < sviAsteroidi.size(); j++)
			{
				
				if(i != j && sviAsteroidi.get(j).isAlive() && sviAsteroidi.get(i).isAlive())
				{
					
					if(Math.abs(sviAsteroidi.get(i).getPosx() - sviAsteroidi.get(j).getPosx())< 50 && Math.abs(sviAsteroidi.get(i).getPosy() - sviAsteroidi.get(j).getPosy()) < 50) {
						//System.out.println("Kolizija: " + sviAsteroidi.get(i).getPosx() + " - " + sviAsteroidi.get(j).getPosy() + " I : " + sviAsteroidi.get(j).getPosx() + " - " + sviAsteroidi.get(j).getPosy());
	
						endTime2 = System.currentTimeMillis();
						if(endTime2 - startTIme1 > 1000) {
						sviAsteroidi.get(i).setuKoliziji(true);
						sviAsteroidi.get(j).setuKoliziji(true);
						startTIme1 = endTime2;
						}
					}
				}
				
			}
		}
	}
	public ArrayList<Asteroid> getSviAsteroidi() {
		return sviAsteroidi;
	}

	public void setSviAsteroidi(ArrayList<Asteroid> sviAsteroidi) {
		this.sviAsteroidi = sviAsteroidi;
	}
	public void obojiAsteroide()
	{
		for(int y = 1; y < source.getHeight() - 1; y++)
		{
			for(int x = 1; x < source.getWidth() - 1; x++)
			{
				source.getPixel(x, y, rgb);
				rgb[0] += 50;
				target.setPixel(x, y, rgb);
			}
		}
	}
	
	public void pucanjeNaAsteroide()
	{

		
		if(bulletX < 1000 && bulletX > 0 && bulletY > 0 && bulletY < 1000)
		{
			for(Asteroid a : sviAsteroidi)
			{
				if(a.isAlive())
				{
					if(bulletX > a.getPosx() && bulletX < (a.getPosx() + 200) && bulletY > a.getPosy() && bulletY < (a.getPosy() + 100))
					{
						
						a.setSlikaAsteroida(vatra);
						a.setDaLiJeUnisten(true);
						score += 1;
					}
					

				}
				
				
			}
		}
	}	

	@Override
	public void handleKeyDown(int keyCode) {
		if(keyCode == KeyEvent.VK_B) {
			if(k % 3 == 0) {
			BufferedImage i = Util.loadImage("SpaceShip2.png");
			ship.setSpaceShip(i);
			} else if(k % 3 == 1) {
			BufferedImage i = Util.loadImage("SpaceShip3.png");
			ship.setSpaceShip(i);
			}else if(k % 3 == 2){
			BufferedImage i = Util.loadImage("SpaceShip.png");
			ship.setSpaceShip(i);
			}
			k++;
		}
		// na ESC se izlazi iz igre u glavni meni
		if(keyCode == KeyEvent.VK_ESCAPE) {
			state = "Main menu";
			positionX = 220;
			positionX1 = 220;
			sc=false;
			sg = false;
			koordinatax=-512;
			koordinatay=0;
			dx=1;
			dy=1;
			pozicijaString1 = 500;
			pozicijaString2 = 1000;
			pozicijaString3 = 500;
			pozicijaString4 = 1000;
			pozicijaString5 = 500;
			pozicijaString6 = 1000;
			pozicijaString7 = 500;
			pozicijaString8 = 1000;
		}
		
		
		//pucanje handler
		if(keyCode == KeyEvent.VK_M) {
			
			mode++;
		}
		if(keyCode == KeyEvent.VK_P) {
				if(pause == 0) {
					pause = 1;
				}else {
					pause = 0;
				}
		
		}
		
		if(keyCode == KeyEvent.VK_SPACE) {
		
			lista.add(new Bullet());
			
			
			Point2D firePositionRel = new Point2D.Double(ship.getSpaceShip().getWidth() / 2, 0);

			Point2D firePositionAbs = new Point2D.Double();
			
			shipTransform.transform(firePositionRel, firePositionAbs);
		
			bulletX = firePositionAbs.getX();
			bulletY = firePositionAbs.getY();

			double finalAngle = ship.getSpaceShipAngle();
			
			bulletDX = Math.cos(finalAngle) * bullet.getSpeed();
			bulletDY = Math.sin(finalAngle) * bullet.getSpeed();
			
			if(brojac == 0) {
			bulletActive = true;
			}
			brojac++;
			
			
			
		}
		
		
		
	}

	@Override
	public void handleKeyUp(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseDown(int arg0, int arg1, GFMouseButton arg2) {
		
		if(isMouseButtonDown(GFMouseButton.Left)) {
			if(getMouseX() > 220 && getMouseX() < 285) {
				if(getMouseY() > 210 && getMouseY() < 230) {
				
					sg = true;
					popuniAsteroide();
					mrtav=0;
					sc=false;
					//TODO ovde kad se doda foreach petlja za asteroide umre
			
				}
			}
		}
		if(isMouseButtonDown(GFMouseButton.Left))
			if(getMouseX()>220 && getMouseX() < 285 && getMouseY() > 260 && getMouseY() < 280)
				{
					sc = true;
					koordinatax=-512;
					koordinatay=0;
					dx=1;
					dy=1;
					pozicijaString1 = 500;
					pozicijaString2 = 1000;
					pozicijaString3 = 500;
					pozicijaString4 = 1000;
					pozicijaString5 = 500;
					pozicijaString6 = 1000;
					pozicijaString7 = 500;
					pozicijaString8 = 1000;
				}
		// na exit se izlazi iz aplikacije
		if(isMouseButtonDown(GFMouseButton.Left)) {
			if(getMouseX() > 220 && getMouseX() < 285) {
				if(getMouseY() > 310 && getMouseY() < 330) {
				
					System.exit(0);
					
					
				}
			}
		}
		
	}

	@Override
	public void handleMouseMove(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseUp(int arg0, int arg1, GFMouseButton arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleWindowDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleWindowInit() {
		
		//dodajemo lsiku za brod
		ship.setSpaceShip(Util.loadImage("SpaceShip.png"));
		
		
	}
	
	
	
	int smer = 1;
	int prva=0;
	int druga=1;
	int pozicijaString1 = 500;
	int pozicijaString2 = 1000;
	int pozicijaString3 = 500;
	int pozicijaString4 = 1000;
	int pozicijaString5 = 500;
	int pozicijaString6 = 1000;
	int pozicijaString7 = 500;
	int pozicijaString8 = 1000;
	int pozSlikeX[] = {-512,1000,0,0,};
	int pozSlikeY[] = {0,0,-512,1000};
	int d = 0;
	int dx = 1;
	int dy = 1;
	int koordinatax = -512;
	int koordinatay = 0;
	/* (non-Javadoc)
	 * @see rafgfxlib.GameFrame#render(java.awt.Graphics2D, int, int)
	 */
	/* (non-Javadoc)
	 * @see rafgfxlib.GameFrame#render(java.awt.Graphics2D, int, int)
	 */
	@Override
	public void render(Graphics2D g, int x, int y) {
		

		if(state.equalsIgnoreCase("credits"))
		{
			g.drawImage(Util.loadImage("doge.png"), koordinatax, koordinatay, null);
			if(dx>1512)
			{
				d=(d+1)%4;
				dx=0;
			}
			if(dy>1512)
			{
				d=(d+1)%4;
				dy=0;
			}
			if(d==0)
			{
				koordinatax=pozSlikeX[d]+(dx+=5);
				koordinatay=pozSlikeY[d];
			}else if (d==1)
			{
				dx+=5;
				koordinatax=pozSlikeX[d]-dx;
				koordinatay=pozSlikeY[d];
			}else if (d==2)
			{
				koordinatax=pozSlikeX[d];
				koordinatay=pozSlikeY[d]+(dy+=5);
			}else if(d==3)
			{
				koordinatax=pozSlikeX[d];
				koordinatay=pozSlikeY[d]-(dy+=5);
			}
			
			if(pozicijaString2>-100)
			{
				g.setColor(Color.WHITE);
				g.drawString("Credits", pozicijaString1, pozicijaString2);
				pozicijaString2-=4;
			}
			if(pozicijaString2<950 && pozicijaString4>-50)
			{
				g.setColor(Color.WHITE);
				g.drawString("Created by", pozicijaString3, pozicijaString4);
				pozicijaString4-=4;
			}
			if(pozicijaString4<950 && pozicijaString6>0)
			{
				g.setColor(Color.YELLOW);
				g.drawString("Rakijestina-tim",pozicijaString5, pozicijaString6);
				pozicijaString6-=4;
			}
			if(pozicijaString2<-99)
				pozicijaString2=1000;
			if(pozicijaString4<-49)
				pozicijaString4=1000;
			if(pozicijaString6<1)
				pozicijaString6=1000;
			
			AffineTransform transform = new AffineTransform();
			
			for(Zvezda p : zvezde) {
				transform.setToIdentity();
				transform.translate(p.posX, p.posY);
				transform.rotate(p.angle);
				transform.translate(-180, -180);
				g.drawImage(zvezde.get(p.getId()).img, transform, null);
			}
			
			
			
		}
		else if(state.equals("Game")) {
			
			if(mrtav == 1)
			{
				state = "credits";
				koordinatax=-512;
				koordinatay=0;
				dx=1;
				dy=1;
				pozicijaString1 = 500;
				pozicijaString2 = 1000;
				pozicijaString3 = 500;
				pozicijaString4 = 1000;
				pozicijaString5 = 500;
				pozicijaString6 = 1000;
				pozicijaString7 = 500;
				pozicijaString8 = 1000;
				sc=true;
			}
			else {

		
			//pozadina 
			g.setComposite(AlphaComposite.SrcOver.derive(alpha));
			g.drawImage(nizSlika[prva], 300, 300, 400, 400, null);
			g.setComposite(AlphaComposite.SrcOver.derive(1f-alpha));
			g.drawImage(nizSlika[druga], 300, 300, 400, 400, null);
			
			alpha=(float) (alpha+smer*0.005);
			if(alpha>1f)
			{
				smer=-1;
				alpha=1f;
				druga = (druga+2) % 9;
			}
			if(alpha<0f)
			{
				smer=1;
				alpha=0f;
				prva = (prva+2) % 9;
			}
			//System.out.println(((AlphaComposite)g.getComposite()).getAlpha());
			//g.setBackground(Color.black);
			//g.clearRect(0, 0, x, y);
			
			
			g.setComposite(AlphaComposite.SrcOver);
			g.drawImage(ship.getSpaceShip(), shipTransform, null);
			g.drawImage(ship.getSpaceShip(), shipTransform, null);
			//skor iscrtavanje
			String s = "Score " + score;
			g.setColor(Color.white);
			g.drawString(s, 10, 150);
			
			if(bulletActive) {
				
				
				if(mode % 3 == 0) {
					x1 = (int) bulletX - 5;
					y1 = (int) bulletY - 5;
				
					g.setColor(Color.red);
					g.fillOval(x1, y1, 10, 10);
					
				} 
				else if(mode % 3 == 1) {
					x1 = (int) bulletX - 10;
					y1 = (int) bulletY - 10;
					x2 = (int) bulletX + 10;
					y2 = (int) bulletY + 10;
					
					g.setColor(Color.blue);
					g.fillOval(x1, y1, 10, 10);
					g.fillOval(x2, y2, 10, 10);
				}
				else if(mode % 3 == 2) {
					x1 = (int) bulletX - 10;
					y1 = (int) bulletY - 10;
					x2 = (int) bulletX + 10;
					y2 = (int) bulletY + 10;
					x3 = (int) bulletX + 20;
					y3 = (int) bulletY - 5;
					
					g.setColor(Color.yellow);
					g.fillOval(x1, y1, 10, 10);
					g.fillOval(x2, y2, 10, 10);
					g.fillOval(x3, y3, 10, 10);
				}
			
				//g.setColor(Color.blue);
				//g.fillOval((int) bulletX - 20, (int) bulletY - 5, 10, 10);
				
				
				
				//moze i lsika ali ne radi bas kako treba
				//trebala bi dodatna podesavanja za ugao pod kojim se uspaljuje
				//g.drawImage(bullet.getImg(), (int) bulletX - 5, (int) bulletY - 5, null);
				for(Asteroid a : sviAsteroidi)
				{
					if(a.isAlive())
					g.drawImage(a.getSlikaAsteroida(), a.getPosx(), a.getPosy(), a.getWidth(), a.getHeight(), null);
					else if(sviAsteroidi.get(ax) == a) g.drawImage(trecaSlika, a.getPosx(), a.getPosy(), a.getWidth(), a.getHeight(), null);
					else {
						g.drawImage(drugaSlika, a.getPosx(), a.getPosy(), a.getWidth(), a.getHeight(), null);
						a.setSlikaAsteroida(asteroidImg);
						a.setDaLiJeUnisten(false);
					}
						
				}
			
			  }
			}
		
		
		
		
		}else if(state.equals("Main menu")){
			
			for(Star s : stars)
			{	
				float sX1 = x / 2 + s.posX * (400.0f / s.posZ);
				float sY1 = y / 2 + s.posY * (400.0f / s.posZ);
				
				float sX2 = x / 2 + s.posX * (400.0f / (s.posZ + speed));
				float sY2 = y / 2 + s.posY * (400.0f / (s.posZ + speed));
				
				int brightness = (int)(255 - (s.posZ / MAX_Z) * 255.0f);
				g.setColor(grayscale[brightness]);
				
				g.drawLine((int)sX1, (int)sY1, (int)sX2, (int)sY2);
				
			}
			
			g.setColor(Color.white);
			//stampa prvo slovo na 220 po x-osi da znas da namjestis bolje klik
			g.drawString(s, positionX, 220);
			
			g.drawString("About", positionX1, 270);
			
			g.drawString("Exit", positionX2, 320);
			
		
		}
		
		
		
	}

	@Override
	public void update() {
		
	//varijable za pozicije municije su globalne
	//tako da preko njih mozes naci poziciju
	//x1,x2,x3,y1,y2,y3 su varijable
		if(pause == 1) {
			System.out.println("pauza");
		}else {
		
		//na dnu zakomentarisano
		automaticRotation();
		
		
		asteroidUdaraUBrod();
		pucanjeNaAsteroide();
		
		//animacija teksta koji nestaje i prebacuje se u view 
		//u odnosu na izabrano
		if(sg == true) {
			if(positionX >= - 60) {
				positionX -= 3.7;
			}
			if(positionX == -60) {
				state = "Game";
				mrtav = 0;
				for(Asteroid a : sviAsteroidi)
				{
					a.setPosx((int) (Math.random()* 900));
					a.setPosy(0);
				}
				ship.setPosX(500);
				ship.setPosY(500);
			}
		}
		if(sc)
		{
			if(positionX1>=-60)
				positionX1-=3.7;
			if(positionX1 == -60)
			{
				state="credits";
			}
		}
		//provjera dokle je dosao brod i ogranicenje da ne moze izaci van granica ekrana
		
		if(ship.getPosX() < 0) {
			ship.setPosX(0);
		}
		
		if(ship.getPosX() > 1000) {
			// /40 je hard-kodovano da odgovara esteticki 
			ship.setPosX(1000 - ship.getSpaceShip().getWidth()/40);
		}
		
		if(ship.getPosY() < 0) {
			ship.setPosY(0);
		}
			// /40 je hard-kodovano da odgovara esteticki 
		if(ship.getPosY() >= 1000) {
			ship.setPosY(1000 - ship.getSpaceShip().getHeight()/40);
		}
		
		//
		//Radimo kretanje broda
		
		if(isKeyDown(KeyEvent.VK_A)) {
			
			ship.setSpaceShipAngle(ship.getSpaceShipAngle() - ship.getSpaceShipRotationSpeed());
		}
		if(isKeyDown(KeyEvent.VK_D)) {
			 ship.setSpaceShipAngle(ship.getSpaceShipAngle() + ship.getSpaceShipRotationSpeed());
		 }
		
		if(isKeyDown(KeyEvent.VK_W)) {
			 q = ship.getPosX() + Math.cos(ship.getSpaceShipAngle()) * ship.getSpaceShipMoveSpeed();
			 p = ship.getPosY() + Math.sin(ship.getSpaceShipAngle()) * ship.getSpaceShipMoveSpeed();
		//	ship.setPosX(ship.getPosX() + Math.cos(ship.getSpaceShipAngle()) * ship.getSpaceShipMoveSpeed());
		//	ship.setPosY(ship.getPosY() + Math.sin(ship.getSpaceShipAngle()) * ship.getSpaceShipMoveSpeed());	
			
		
			
			ship.setPosX(q);
			ship.setPosY(p);
			
			
		}
		
		if(isKeyDown(KeyEvent.VK_S)) {
			q1 = ship.getPosX() - Math.cos(ship.getSpaceShipAngle()) * ship.getSpaceShipMoveSpeed();
			p1 = ship.getPosY() - Math.sin(ship.getSpaceShipAngle()) * ship.getSpaceShipMoveSpeed();
			//ship.setPosX(ship.getPosX() - Math.cos(ship.getSpaceShipAngle()) * ship.getSpaceShipMoveSpeed());
			//ship.setPosY(ship.getPosY() - Math.sin(ship.getSpaceShipAngle()) * ship.getSpaceShipMoveSpeed());
		
			
			ship.setPosX(q1);
			ship.setPosY(p1);
			
			
		}
		
		if(bulletActive == true) {
			bulletX += bulletDX;
			bulletY += bulletDY;
		}
		
		
		
		//Podesavanja za iscrtavanje broda
		
		shipTransform.setToIdentity();
		
		//uzimamo koordinate za start
		shipTransform.translate(ship.getPosX(), ship.getPosY());
		
		//pozicioniramo brod na centar
		shipTransform.rotate(ship.getSpaceShipAngle() + Math.PI / 2.0);
		
		//iscrtavamo brod tacno oko centra
		shipTransform.translate(-ship.getSpaceShip().getWidth() /2, -ship.getSpaceShip().getHeight() / 2);
		
		
		bulletTransform.setTransform(shipTransform);
		bulletTransform.translate(ship.getSpaceShip().getWidth() /2, ship.getSpaceShip().getHeight() / 2);
		bulletTransform.rotate(ship.getSpaceShipAngle());
		bulletTransform.translate(-ship.getSpaceShip().getWidth() /2, -ship.getSpaceShip().getHeight() / 2);
		
		if(state.equalsIgnoreCase("main menu"))
		{
			for(Star s : stars)
			{
				s.posZ -= speed;
				if(s.posZ < 1.0)
				{
					s.posZ += MAX_Z;
					s.posX = (float)(Math.random() * 2000.0) - 1000.0f;
					s.posY = (float)(Math.random() * 2000.0) - 1000.0f;
				}
			}
		}
		
		if(sc)
		{
			if(Math.random() < 0.006) 
			{
				Zvezda p = new Zvezda();
				p.angle = (float)(Math.random() * Math.PI * 2.0);
				p.rot = (float)(Math.random() - 0.5) * 0.3f;
				
				if(Math.random() < 0.5)
					p.setType(0);
				else
					p.setType(1);
				p.setId(indexP);
				p.setPosY(-200);
				double x = Math.random()*1000;
				p.setPosX((float)x);
				zvezde.add(p);
				indexP++;
			}
			for(Zvezda p : zvezde) {
				p.posY += 5;
				if(p.getType() == 1){
					p.angle += p.rot;
					p.rot *= 0.99f;
				}
				else{
					p.angle -= p.rot;
					p.rot *= 0.99f;
				}
				
			}
		}
		if(bulletActive) {
			  endTime = System.currentTimeMillis();
						if(endTime - startTIme > 300)  {
							sviAsteroidi.get(ax).setAlive(true);
							ax++;
							if(ax > 4) ax = 0;
							startTIme = endTime;
						}
						for(Asteroid a : sviAsteroidi)
						{
							spawn(a);
							
						}
						kolizija();
			}
		
		}// ovo je za pauzu
	}
	
	public void pogodak()
	{
		
			for(Asteroid a : sviAsteroidi)
			{
				if(a.isAlive())
				{
					if(a.getPosx() - bulletX < 100 && a.getPosy() - bulletY < 50)
					{
						System.out.println(bulletX);
						
					}
				}
			}
		
	}
	private int mrtav = 0;
	public void asteroidUdaraUBrod()
	{
		for(Asteroid a : sviAsteroidi)
		{
			if(a.isAlive() && !a.isDaLiJeUnisten())
			{
				if(ship.getPosX() > a.getPosx() && ship.getPosX() < (a.getPosx() + 200) && ship.getPosY() > a.getPosy() && ship.getPosY() < (a.getPosy() + 100))
				{
					//System.out.println("Game Over");
					mrtav = 1;
					
				}
			}
		}
	}
	
	// marko evo ti rotacija broda, zamijeni asteroidima, mzd promijeni parametre i radice
	public void automaticRotation() {
		
		//ship.setSpaceShipAngle(ship.getSpaceShipAngle() - ship.getSpaceShipRotationSpeed());
	
		
	}
	public static class Star
	{
		public float posX;
		public float posY;
		public float posZ;
	}

}
