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
	

		private SpriteBatch spriteBatch;
		private Controller control;
		OrthographicCamera view = new OrthographicCamera() ;
		BitmapFont font;
		Texture bg1, bg2;
		float bg1Xpos, bg2Xpos;
		Animation explosionAnim;
		Texture explosionSheet;
		TextureRegion [] explosionFrames;
		TextureRegion currentFrameExplosion;
		float shipExplosionStateTime;
		
		
		
		public Renderer(Controller c){
			control = c;
			bg1 = new Texture("assets/asteroid_bg2.jpg");
			System.out.println(bg1.getWidth());
			//bg1 = bg2;
			bg2 = new Texture("assets/asteroid_bg2.jpg");
			bg1Xpos = 0;
			bg2Xpos = bg1.getWidth();
			spriteBatch = new SpriteBatch(); 
			font = new BitmapFont();
			
			
			explosionSheet = new Texture(Gdx.files.internal("assets/explosion17.png"));
			TextureRegion [][] tmp = TextureRegion.split(explosionSheet, explosionSheet.getWidth()/5,
					explosionSheet.getHeight()/5);
			explosionFrames = new TextureRegion[25];
			int index = 0;
			for(int i =0; i<5; i++){
				for(int j=0; j<5; j++){
					explosionFrames[index++] = tmp[i][j];
				}
			}
			
			explosionAnim = new Animation(0.040f, explosionFrames);
			shipExplosionStateTime = 0f;	
			}
		
		public void render(){
			spriteBatch.begin();
			 renderBackground();
			 font.setColor(Color.YELLOW);
			 font.draw(spriteBatch, "Tammy's JavaStroid Game", 50, (Gdx.graphics.getHeight()- 40));
			 font.setColor(Color.GREEN);
			 font.draw(spriteBatch, "Press Space to Shoot and Up to move.", 50, (Gdx.graphics.getHeight()- 70));
			for(GameObject gObj : control.getDrawableObjects()){
				gObj.sprite.draw(spriteBatch);
			}
			
			
			if(control.isShipCrashed() &&
					!explosionAnim.isAnimationFinished(shipExplosionStateTime)){
				shipExplosionStateTime += Gdx.graphics.getDeltaTime();
				currentFrameExplosion = explosionAnim.getKeyFrame(shipExplosionStateTime, false);
				spriteBatch.draw(currentFrameExplosion, control.getExplosionX() - Constants.SHIP_WIDTH,
						control.getExplosionY() - Constants.SHIP_HEIGHT);
				
				font.setColor(Color.RED);
				font.draw(spriteBatch, "Ship Crashed!!!", control.getExplosionX() - Constants.SHIP_WIDTH
						, control.getExplosionY() - Constants.SHIP_HEIGHT);
			}
			
			
			if(control.isAsteroidHit() && 
					!explosionAnim.isAnimationFinished(shipExplosionStateTime)){
				shipExplosionStateTime += Gdx.graphics.getDeltaTime();
				currentFrameExplosion = explosionAnim.getKeyFrame(shipExplosionStateTime, false);
				spriteBatch.draw(currentFrameExplosion, control.getMissileX(),control.getMissileY());
				
			}
			
			spriteBatch.end();
			
			
		}
		
		public void renderBackground(){
			spriteBatch.draw(bg1, bg1Xpos, 0 );
			spriteBatch.draw(bg2, bg2Xpos, 0);
			
			if(bg2Xpos == 0){
				bg1Xpos = bg2.getWidth();
			}
		
			
			bg1Xpos -= 0.3;
			bg2Xpos -= 0.3;
		}
		
			
	}



