package com.gmail.tamtyberg.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gmail.tamtyberg.game.Constants;



public class Asteroid extends GameObject implements Updatable{
	
///////////////////////////////////Class Variables/////////////////////////////////////////////////////////////////////////////
	
	private float rotationalVel;  //velocity for rotating
	private Vector2 dirAndVel;   //direction vector
	private int screenW;   //screen width
	private int screenH;   //screen height
	
	Random rand = new Random();  //random seed

	
///////////////////////////////////////////////Class Methods///////////////////////////////////////////////////////////	
	
	
	//constructor for asteroid object takes a texture param
	public Asteroid(Texture tex){  
		
			screenW = Gdx.graphics.getWidth(); //sets screen width
			screenH = Gdx.graphics.getHeight(); //sets screen height
			sprite = new Sprite(tex);			//setting sprite to the texture passed in
			
			sprite.setSize(Constants.ASTEROIDS_SIZE, Constants.ASTEROIDS_SIZE); //sets height and width of asteroid to constant values
			sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);  //setting origin
			
			
			//setting dirAndVel vector mod both height and width so won't go out of bounds
			dirAndVel = new Vector2( rand.nextFloat()%Gdx.graphics.getWidth(), rand.nextFloat()%Gdx.graphics.getWidth());
			setIsDrawable(true); //allowing it to be drawable
	}
	
	
	
	//overriding update method, passing in delta time, so when renderer updates this is what happens to the asteroids
	@Override
	public void update(float deltaTime) {
		
		
		sprite.rotate(getRotVel() ); // TODO: Student, use delta time here if use it then dont rotate
		
		//translating asteroid
		sprite.translate(dirAndVel.x, dirAndVel.y); //when scl by delta time doesnt move
		
		if(sprite.getX() < 10 || sprite.getX() > screenW-10){ //check cooridante of sprite not near
			//begining of x axis and screenW  using 10 because floats are not precise so need big range
			
							
							dirAndVel.x *= -1; //change to opposite direction
//			
			//the above check is to prevent the asteroids from leaving the visible screen
		}
			
		
		//same check as above if statement just for the Y coordinates
		if(sprite.getY() < 10 || sprite.getY() > screenH - 10){
			//check not going to high or low out of screen bounds
			
							dirAndVel.y *= -1; //change the dir and vel by the opposite direction
			
		}
			
	}
		
	//setter for RotVel float
	public void setRotVel(float vel){
		rotationalVel = vel;
	}
	
	//getter for RotVel float
	public float getRotVel(){
		return rotationalVel;
	}

}

