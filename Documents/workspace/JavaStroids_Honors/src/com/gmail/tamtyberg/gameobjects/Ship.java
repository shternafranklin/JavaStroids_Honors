package com.gmail.tamtyberg.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Ship extends GameObject implements Updatable{
	
////////////////////////////////////////Member Variables//////////////////////////////////////////////////////////
	
	private Vector2 direction; //direction vector
	private Vector2 targetDirection;   //target direction vector for when user clicks mouse to move ship in that direction
	private Vector2 velocity;  //velocity vector
	private final float MIN_VELOCITY = 20;  //constant of minimum velocity
	double deg; //double value for degree for vector rotations
	

	
/////////////////////////////////////Member Methods//////////////////////////////////////////////////////////////////
	
	//constructor accepts three parameters; texture, and two int variables
	public Ship(Texture texture, int x, int y) {
		
			sprite = new Sprite(texture); //initialize sprite with texture passed in
			sprite.setOrigin(texture.getWidth()/2, texture.getHeight()/2); //set origin of sprite using half tex w/h
			sprite.setPosition(x, y); //set to pos of passed in x and y values
			direction = new Vector2(0, -1); //direction start a (0, -1)
			targetDirection = new Vector2(0, -1); //initialized to same as direction vector
			velocity =  new Vector2(0, MIN_VELOCITY); //initialize velocity vector to 0 and minimum vel
			setIsDrawable(true); //is drawable
	}
	
	//getter for direction vector
	public Vector2 getDirection(){
		return direction;
	}
	
	
	//getter for position vector, returns vector with pos coordinates of the ship sprite
	public Vector2 getPosition(){
		return new Vector2(sprite.getX(), sprite.getY());
	}
	
	
	//override update method receives deltaTime
	@Override
	public void update(float deltaTime) {

//////////////????WE NEED TO RE-READ VECTOR OPERATIONS AND RULES TO MAKE POSSIBLE CHANGES AND UPDATE COMMENTS!!!////////////////////////////////////////
		
		//get theta by dot product of vectors  (targetDirection and direction vectors) over targetDirection normalized
		double theta = Math.acos(targetDirection.dot(direction)/targetDirection.len());  
		
		//convert to degrees (maybe it's in radians?????)
		theta = Math.toDegrees(theta);
		
		//so if cross product of direction and targetDirection is greater than 0  than muliply theta by -1 so opposite
		if (direction.crs(targetDirection) > 0)
			theta *= -1;
		
		//then rotate the sprite around theta times deltaT to slow to game time
	    sprite.rotate((float)theta * deltaTime); 
	    
		direction.rotate(-(float)theta * deltaTime);// why negative -- i think because of screen coordinates vs. sprite coordinate system
	
		//if normlaized vel vector is greater than min vel scale it by the differ of 1 and deltaT
		if(velocity.len() > MIN_VELOCITY){
			velocity.scl(1 - deltaTime);
		}
		//translate sprite by possibly updated vector
		sprite.translate( velocity.x * deltaTime, velocity.y * deltaTime);
		
		
		
		}
		
		
		
//method to move ship forward when press space button	

	public void moveForward(float deltaTime) {

			velocity.x += (direction.x * velocity.len() * 2 * deltaTime); //x comp is += driection.x and normalized vel vector * 2 and of course deltaT
			velocity.y -= (direction.y * velocity.len() * 2 * deltaTime); //same as with X comp but for Y
			velocity.clamp(10,80);  //clamp values between 10 and 80
	}
	
//when user clicks mouse the ship faces that direction this updates the driection, a vector is passed in	
	public void face(Vector2 targetPos){
		targetDirection = targetPos; //targetDirection gets values of passed in vector
	}

}
