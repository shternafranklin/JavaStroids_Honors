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
	
	private int asteroidNum; //changed from hardcoded number to variable so can just change this
	
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

	//Controller constructor calls the controller member methods
	public Controller(){ 
		
		asteroidNum = 10; //how many asteroids to be created
		
		drawableObjects = new ArrayList<GameObject>();  //initialized list of game objects
		initShip();  //calls initship method 
		initAsteroids(asteroidNum); //calls this method to init Astroids and passes int param  for number of asteroids created
							
		initSound();  //call this method to start sound stuff
	}
	
	//initShip method to initialize the ship 
		private void initShip(){

				int w = Constants.SHIP_WIDTH; //setting H/W of ship via the constants class 
				int h = Constants.SHIP_HEIGHT; 
				screenHeight = Gdx.graphics.getHeight();
				Pixmap pmap = new Pixmap(w, h, Format.RGBA8888); // TODO: Check Image Format
				pmap.setColor(1, 1, 1, 1); //creating line color
				pmap.drawLine(0, h, w/2, 0);//creating shape via lines
				pmap.drawLine(w, h, w/2, 0);
				pmap.drawLine(1, h-1, w, h-1);
				ship = new Ship(new Texture(pmap), 100, 100);  //Initializing ship sending pmap tex, and pos
				drawableObjects.add(ship); //adding ship to drawable object arraylist 
	}
	
	//getter for shipCrashed boolean
	public boolean isShipCrashed() {
		return shipCrashed;
	}
	
	//initAsteroid
	private void initAsteroids(int num){ 
		
		Random rand = new Random(); //random set up
		for(int i = 0; i<num; i++){ //forloop for creating num astroids- num here is whatever asteroidNum is set to
			Asteroid asteroid = new Asteroid(new Texture("assets/Asteroid_tex.png")); //each iteration through forloop creates an instance of Asteroid through calling the constructor
			
			//randomly setting h/w of asteroid
			asteroid.sprite.setPosition(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
			
			//what is setorigin?
			asteroid.sprite.setOrigin(asteroid.sprite.getWidth() / 2, asteroid.sprite.getHeight() / 2);
			
			asteroid.setRotVel(rand.nextFloat()*8-4);
			drawableObjects.add(asteroid); //adding to drawableObjects list so renderer will know what to draw
		}
	}
		
	
	//initMissile
	public void initMissile(){
		
		int w = Constants.SHIP_WIDTH/3; //width
		int h = Constants.SHIP_HEIGHT/3; //height
		Pixmap pmap = new Pixmap(w, h, Format.RGB565); //creating pmap for missle
	
		pmap.setColor(1, 1, 1, 1); //set color
		pmap.drawLine(w/2, 0, w/2, h); //drawing line
		
		drawableObjects.add(new Missile(new Texture(pmap), ship.getDirection(), ship.getPosition())); //add to drawable objects
		//reminds me of anonymous inner class since no specific instances each instance created in game time
		//pos based on ship driection and postion
	}
	
	
	//initSound
	private void initSound(){
		
		//initializing thrusters sound with sound in assets
		thrustersSound = Gdx.audio.newSound(
				Gdx.files.internal("assets/125810__robinhood76__02578-rocket-start.wav"));
		
		//initializing background music with sound in assets
		backgroundNoise = Gdx.audio.newMusic(
				Gdx.files.internal("assets/132150__soundsodd__interior-spaceship.mp3"));
		
		//initializing explosion sound with sound in assets
		explosionSound = Gdx.audio.newSound(
				Gdx.files.internal("assets/235968__tommccann__explosion-01.wav"));
		
		//initializing missile sound with sound in assets
		missileSound = Gdx.audio.newSound(
				Gdx.files.internal("assets/171655__audionautics__rocket-shots.mp3"));
		
		//set background music to loop over and over by passing in true param to music instance called backgroundNoise
		backgroundNoise.setLooping(true);
		//play backgroundMusic
		backgroundNoise.play();
		//set volume
		backgroundNoise.setVolume(0.5f);
	}
	
	
	//method to process mouse inpt
	private void processMouseInput(){
		
		//use button listener if left mouse button clicked the ship should move in that directions
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			
			//ship face the coordinates of the mouse click subract for differ in screen x/y sys and sprite x/y sys
			ship.face(new Vector2(Gdx.input.getX()-ship.sprite.getX(), -(screenHeight - Gdx.input
					.getY()- ship.sprite.getY()))); //some vector calculation to get the ship
													//to face the target calculated in face(vector 2) in ship class
		}
	}
	
	//method to process input from keyboard, previous method processes from mouse
	private void processKeyboardInput(){
		
		if (Gdx.app.getType() != ApplicationType.Desktop) return; // Just in case not desktop app :)
		
		if (Gdx.input.isKeyPressed(Keys.UP)) { //when press up arrow moves the ship

			ship.moveForward(Gdx.graphics.getDeltaTime()); //call moveForward method moves  
			thrustersSound.play(0.5f); //and play sound 
			
			}
		
		
		
		if(Gdx.input.isKeyPressed(Keys.SPACE)){  //if user presses space
			
			/// add code to make creation one missile per second- done! test to make sure works
			
			//limit to one second
			do{ initMissile(); //create missile
				time = System.currentTimeMillis(); //get current time in milliseconds
				lastShot = time;  //lastshot set to the time the missile shot
				}
			while(time >= (lastShot + 1000)); //shoot next while time is greater than lastsot and 1000 miliseconds
	
		}
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			missileSound.play();  //play sound again why cant this be combined with the above if statement?
		}
		
		
		
	}
	
	
//update method
	public void update(){
		
		asteroidHit = false;   //change to false so can send message asteroidHit if hit in the next frame
		processKeyboardInput();  //process keyboard input
		processMouseInput();   //process mouse inuput
		
		// Update Asteroids
		for(int i = 0; i < drawableObjects.size(); i++) {  //iterate through drawable objects which is list of game objects
			GameObject gObg = drawableObjects.get(i); //initilaize a gameobject to the element indexed in the array list
			
					if(gObg instanceof Asteroid){ //check if it's an instance of the Asteroid class
						// then check if the ships bounding rectangle overlaps with game object cast to Asteroid since its an asteroid object
						// and make sure the ship is not crashed yet 
							if(ship.sprite.getBoundingRectangle().overlaps(((Asteroid)gObg).sprite.getBoundingRectangle()) && !shipCrashed){
										
										//then:
										shipCrashed = true;//ship is crashed change boolean to true
										explosionSound.play(); //play explosion sound
										thrustersSound.stop(); //stop thrusters sound since there is no more ship
										explosionX = ship.sprite.getX(); //explosion x is set to ship x coordinate
										explosionY = ship.sprite.getY(); //explosion y is set to shp y coordinate
										
							}	
						
						
						((Asteroid) gObg).update(Gdx.graphics.getDeltaTime()); //update asteroid
					
					}
					
					if (gObg instanceof Missile){ //if the gameobject is instance of missile class
						
								if(shipCrashed){  // if ship did crash
									
										drawableObjects.remove((Missile)gObg);  //remove the missile
								}
								//code to remove missiles when gets to near end of either direction of screen
								if( ((Missile) gObg).sprite.getX() > (screenW - 20) || ((Missile) gObg).sprite.getX() < 10 || ((Missile) gObg).sprite.getY() > (screenH- 20) ||
										((Missile) gObg).sprite.getY() < 0){
									
										drawableObjects.remove((Missile)gObg);}
								
								else{
										//otherwise update the missile
										((Missile) gObg).update(Gdx.graphics.getDeltaTime());
							}
				
				
				for(int j =0; j< drawableObjects.size(); j++){ //iterating through drawable objects again using another index
					
					GameObject astObj = drawableObjects.get(j);  //setting indexed element to astObj 
					
					if(astObj instanceof Asteroid){ //if its an instance of Asteroid
							
						//if asteroid overlaps with missile object the asteroid from the forloop with j index and 
						//the missile which stems from the forloop with i index
						//and asteroid not hit
							if(((Asteroid) astObj).sprite.getBoundingRectangle().
									overlaps(((Missile)gObg).sprite.
											getBoundingRectangle()) && !asteroidHit){
								//means missile hits asteroid (point of game :-)
																		explosionSound.play();//play explosion sound
																		drawableObjects.remove(i); //remove drawable object at index i which is the asteroid
																		drawableObjects.remove(j);//remove drawable object at index j which is the missile
																		asteroidHit = true; //asteroid is hit
																		MissileX = gObg.sprite.getX(); //missileX is set to x cooridnate of missile sprite x
																		MissileY = gObg.sprite.getY(); //missileY is set to y cooridnate of missile sprite y
																		
																}
							
													}
					
									} //closes second/inner forloop with j index
				
			
							}
					
				} //closes the first/outer forloop with i index
		
		
		// Update ship
		if(!shipCrashed){
			
			ship.update(Gdx.graphics.getDeltaTime());
			
		}else{
			//else remove ship cause then its crashed
			drawableObjects.remove(ship);
			
		}
	}
	
	
	

	//method to dispose sounds
	public void dispose(){
		if(thrustersSound != null){
			thrustersSound.dispose();
		}
		
		if(backgroundNoise != null){
			backgroundNoise.dispose();
		}
	}
	
	
	
	
	/////////////////Getter and Setters/////////////////////////////////////
	public float getMissileX() {
		return MissileX;
	}

	public float getMissileY() {
		return MissileY;
	}

	public boolean isAsteroidHit() {
		return asteroidHit;
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
	
	

}
