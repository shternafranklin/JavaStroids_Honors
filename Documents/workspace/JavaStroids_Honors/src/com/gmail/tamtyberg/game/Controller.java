package com.gmail.tamtyberg.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector2;
import com.gmail.tamtyberg.gameobjects.Asteroid;
import com.gmail.tamtyberg.gameobjects.GameObject;
import com.gmail.tamtyberg.gameobjects.Missile;
import com.gmail.tamtyberg.gameobjects.Ship;


public class Controller {
	
////////////////////////////////////////////////////////Member Variables//////////////////////////////////////////////////////////////////
	
	ArrayList<GameObject> drawableObjects; //arraylist of game object instances called drawableObjects
	Ship ship; //main player instance of ship class
	private float screenHeight; //screen height
	public long time;  //time
	public long lastShot;
	public float screenH = Gdx.graphics.getHeight();  //screen height
	public float screenW = Gdx.graphics.getWidth(); //screen width
	
	public boolean shipCrashed;  //boolean to see if ship crashed
	private boolean asteroidHit; //boolean if asteroid is hit
	
	public Sound explosionSound;  //sound for explosion
	private Sound thrustersSound; //sound for thrusters
	private Sound missileSound;   //sound for missiles
	private Music backgroundNoise;  //background music
	
	private float explosionX;   //explosion x coordinate
	private float explosionY;   //explosion y coordinate
	private float MissileX;    //missile x coordinate
	private float MissileY;   //missile y coordinate


	
//////////////////////////////////////////Member Methods/////////////////////////////////////////////////////////////////////

	//Controller constructor calls the controller memeber methods
	public Controller(){ 
		
		drawableObjects = new ArrayList<GameObject>();  //creates list of game objects
		initShip();  //calls initship functionto initialize starting things
		initAsteroids(10); //calls this method to init Astroids and passes in 10 for number of
							//astroids
		
		initSound();
	}
	
	private void initShip(){
		int w = Constants.SHIP_WIDTH; //setting H/W of ship via the constants class 
		int h = Constants.SHIP_HEIGHT; 
		screenHeight = Gdx.graphics.getHeight();
		Pixmap pmap = new Pixmap(w, h, Format.RGBA8888); // TODO: Check Image Format
		pmap.setColor(1, 1, 1, 1); //creating line color
		pmap.drawLine(0, h, w/2, 0);//creating shape via lines
		pmap.drawLine(w, h, w/2, 0);
		pmap.drawLine(1, h-1, w, h-1);
		ship = new Ship(new Texture(pmap), 100, 100); 
		drawableObjects.add(ship); //adding ship to drawable object arraylist
	}
	
	public boolean isShipCrashed() {
		return shipCrashed;
	}
	
	private void initAsteroids(int num){ 
		Random rand = new Random(); //random set up
		for(int i = 0; i<num; i++){ //forloop for creating num astroids
			Asteroid asteroid = new Asteroid(new Texture("assets/Asteroid_tex.png")); 
			//randomly setting h/w of asteroid
			asteroid.sprite.setPosition(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
			//what is setorigin?
			asteroid.sprite.setOrigin(asteroid.sprite.getWidth() / 2, asteroid.sprite.getHeight() / 2);
			asteroid.setRotVel(rand.nextFloat()*8-4);
			drawableObjects.add(asteroid);
		}
	}
		
	public void initMissile(){
		int w = Constants.SHIP_WIDTH/3;
		int h = Constants.SHIP_HEIGHT/3;
		Pixmap pmap = new Pixmap(w, h, Format.RGB565);
	
		pmap.setColor(1, 1, 1, 1);
		pmap.drawLine(w/2, 0, w/2, h);
		drawableObjects.add(new Missile(new Texture(pmap), ship.getDirection(), ship.getPosition()));
	}
	
	
	
	private void initSound(){
		
		thrustersSound = Gdx.audio.newSound(
				Gdx.files.internal("assets/125810__robinhood76__02578-rocket-start.wav"));
		backgroundNoise = Gdx.audio.newMusic(
				Gdx.files.internal("assets/132150__soundsodd__interior-spaceship.mp3"));
		explosionSound = Gdx.audio.newSound(
				Gdx.files.internal("assets/235968__tommccann__explosion-01.wav"));
		missileSound = Gdx.audio.newSound(
				Gdx.files.internal("assets/171655__audionautics__rocket-shots.mp3"));
		
		backgroundNoise.setLooping(true);
		backgroundNoise.play();
		backgroundNoise.setVolume(0.5f);
	}
	
	private void processMouseInput(){
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			ship.face(new Vector2(Gdx.input.getX()-ship.sprite.getX(), -(screenHeight - Gdx.input
					.getY()- ship.sprite.getY()))); //some vector calculation to get the ship
													//to face the target
		}
	}
	
	
	

	public void update(){
		asteroidHit = false;   //change to false so can send message asteroidHit if hit in the next frame
		processKeyboardInput();
		processMouseInput();
		// Update Asteroids
		for(int i = 0; i < drawableObjects.size(); i++) {
			GameObject gObg = drawableObjects.get(i);
			if(gObg instanceof Asteroid){
				if(ship.sprite.getBoundingRectangle().overlaps(((Asteroid)gObg).sprite.getBoundingRectangle()) && !shipCrashed){
					shipCrashed = true;
					explosionSound.play();
					thrustersSound.stop();
					explosionX = ship.sprite.getX();
					explosionY = ship.sprite.getY();
					
				}	
				
				//if(((Asteroid) gObg).sprite.getBoundingRectangle().overlaps(((Missile)gObg).sprite.getBoundingRectangle()) && !asteroidHit){
					//asteroidHit = true;
				//}
				//if(!asteroidHit){
				((Asteroid) gObg).update(Gdx.graphics.getDeltaTime()); 
			//}else{
				//drawableObjects.remove((Asteroid)gObg);
			//}
			}
			if (gObg instanceof Missile){
				if(shipCrashed){
					drawableObjects.remove((Missile)gObg);
				}
				//code to remove missiles when gets to near end of either direction of screen
				if( ((Missile) gObg).sprite.getX() > (screenW - 20) || ((Missile) gObg).sprite.getX() < 10 || ((Missile) gObg).sprite.getY() > (screenH- 20) ||
						((Missile) gObg).sprite.getY() < 0){
					
					drawableObjects.remove((Missile)gObg);}
				else{
					((Missile) gObg).update(Gdx.graphics.getDeltaTime());
			}
				
				for(int j =0; j< drawableObjects.size(); j++){
					GameObject astObj = drawableObjects.get(j);
					if(astObj instanceof Asteroid){
						
						if(((Asteroid) astObj).sprite.getBoundingRectangle().
								overlaps(((Missile)gObg).sprite.
										getBoundingRectangle()) && !asteroidHit){
							explosionSound.play();
							drawableObjects.remove(i);
							drawableObjects.remove(j);
							asteroidHit = true;
							MissileX = gObg.sprite.getX();
							MissileY = gObg.sprite.getY();
						}
					}
				}
			
		}
		}
		
		
		// Update ship
		if(!shipCrashed){
			ship.update(Gdx.graphics.getDeltaTime());
		}else{
			drawableObjects.remove(ship);
			
		}
	}

	
	
	public float getMissileX() {
		return MissileX;
	}

	public float getMissileY() {
		return MissileY;
	}

	public boolean isAsteroidHit() {
		return asteroidHit;
	}

	private void processKeyboardInput(){
		if (Gdx.app.getType() != ApplicationType.Desktop) return; // Just in case :)
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			ship.moveForward(Gdx.graphics.getDeltaTime());}
		if(Gdx.input.isKeyPressed(Keys.UP)){
			thrustersSound.play(0.5f);
		}
		
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			
			/// add code to make creation one missile per second
			
			do{ initMissile();//limit to one second
				time = System.currentTimeMillis();
				lastShot = time;}
			while(time >= (lastShot + 1000));
	
		}
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			missileSound.play();
		}
	}
	public float getExplosionX() {
		return explosionX;
	}

	public float getExplosionY() {
		return explosionY;
	}

	public ArrayList<GameObject> getDrawableObjects(){
		return drawableObjects;
	}
	
	
	public void dispose(){
		if(thrustersSound != null){
			thrustersSound.dispose();
		}
		
		if(backgroundNoise != null){
			backgroundNoise.dispose();
		}
	}
}
