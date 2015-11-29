package com.gmail.tamtyberg.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gmail.tamtyberg.gameobjects.GameObject;




public class Renderer {
	
///////////////////////////////////////////Member Variables//////////////////////////////////////////////////////////////////
		
	private SpriteBatch spriteBatch; //spritebatch
	private Controller control;  //controller object from Controller class
	OrthographicCamera view = new OrthographicCamera() ; //orthocamera obj for projection - may not use it
	BitmapFont font; //font object
	Texture bg1, bg2;  //textures for continuous background
	float bg1Xpos, bg2Xpos;  //floats for x pos of both backgrounds
	Animation explosionAnim; //animation object
	Texture explosionSheet; //texture for explosion sprite sheet
	TextureRegion [] explosionFrames; //array of frames for animation
	TextureRegion currentFrameExplosion; //not sure??
	float shipExplosionStateTime;  //keep track of state time
		
		
	
//////////////////////////////////////////////////Member Methods////////////////////////////////////////////////////////////////
	
	//Constructor recieves one parameter a Controller object
	public Renderer(Controller c){
		
			control = c; //initializes control to the Controller object passed
			bg1 = new Texture("assets/asteroid_bg2.jpg"); //sets bg1 to tex of background in assets
			System.out.println(bg1.getWidth()); //print bg1 width to screen for bug checking
			//bg1 = bg2;
			bg2 = new Texture("assets/asteroid_bg2.jpg"); //sets bg2 to tex of background in assets
			bg1Xpos = 0; //set x pos of bg1 to zero
			bg2Xpos = bg1.getWidth(); //set x pos of bg2 to end of bg1 which would be the width of bg1
			spriteBatch = new SpriteBatch(); //initialize the sprite batch
			font = new BitmapFont(); //initialize the font object
			
			explosionSheet = new Texture(Gdx.files.internal("assets/explosion17.png")); //set explosion sheet to explosion sprite shee in assets
			//dividing up sprite sheet into 25 regions/images 
			TextureRegion [][] tmp = TextureRegion.split(explosionSheet, explosionSheet.getWidth()/5,
					explosionSheet.getHeight()/5);
			explosionFrames = new TextureRegion[25]; //set explosionFrames to array of 25
			int index = 0;
			for(int i =0; i<5; i++){ //iterate 
				for(int j=0; j<5; j++){ //iterate to set explosion frames to elements of tmp
					explosionFrames[index++] = tmp[i][j];
				}
			}
			
			
			explosionAnim = new Animation(0.040f, explosionFrames); //set animation I think first param would be time/speed , second is the array of frames
			//so maybe how fast to iterate through the frames and the frames together makes the animation
			
			shipExplosionStateTime = 0f;	//set to zero
			}
	
		
	 //render method main part of game
		public void render(){
			
			spriteBatch.begin(); //calls sprit batch to begin/start
			 renderBackground(); //call to render background method 
			 font.setColor(Color.YELLOW); //set font color to yellow
			 font.draw(spriteBatch, "Tammy's JavaStroid Game", 50, (Gdx.graphics.getHeight()- 40)); //draw font set text and location
			 font.setColor(Color.GREEN);//set font color to green
			 font.draw(spriteBatch, "Press Space to Shoot and Up to move.", 50, (Gdx.graphics.getHeight()- 70));//draw font set text and location
			
			 for(GameObject gObj : control.getDrawableObjects()){ //iterate through drawable objects
				 	 
				 		gObj.sprite.draw(spriteBatch); //draw the object
			}
			
			//if ship crashed 
			if(control.isShipCrashed() &&
					!explosionAnim.isAnimationFinished(shipExplosionStateTime)){
				
							shipExplosionStateTime += Gdx.graphics.getDeltaTime(); //the state time is added delta time
							currentFrameExplosion = explosionAnim.getKeyFrame(shipExplosionStateTime, false); //currentframe explosion equals this ?? not sure
							spriteBatch.draw(currentFrameExplosion, control.getExplosionX() - Constants.SHIP_WIDTH,  //draw explosion by ship pos
									control.getExplosionY() - Constants.SHIP_HEIGHT);
							
							font.setColor(Color.RED); //set font color to red
							font.draw(spriteBatch, "Ship Crashed!!!", control.getExplosionX() - Constants.SHIP_WIDTH  //set font to location of where ship crashed
									, control.getExplosionY() - Constants.SHIP_HEIGHT);
			}
			
			//if asteroid hit and animation didnt happen yet
			if(control.isAsteroidHit() && 
					!explosionAnim.isAnimationFinished(shipExplosionStateTime)){
				
								shipExplosionStateTime += Gdx.graphics.getDeltaTime(); //update shipExplosionStateTime
								currentFrameExplosion = explosionAnim.getKeyFrame(shipExplosionStateTime, false);
								spriteBatch.draw(currentFrameExplosion, control.getMissileX(),control.getMissileY());//draw explosion by missile coordinates
				
			}
			
			spriteBatch.end(); //end sprite batch
			
			
		}
		
		//method to render background
		public void renderBackground(){
			
			spriteBatch.draw(bg1, bg1Xpos, 0); //draw bg1
			spriteBatch.draw(bg2, bg2Xpos, 0); //draw bg2
			
			if(bg2Xpos == 0){ //when bg2 x postion = 0
				
				bg1Xpos = bg2.getWidth(); //then bg1 x pos equals thw width of bg 2 
			}
		
			
			bg1Xpos -= 0.3; //move to left by -0.3
			bg2Xpos -= 0.3;//move to left by -0.3
		}
		
			
	}



