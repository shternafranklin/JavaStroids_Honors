package com.gmail.tamtyberg.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;


public class Missile extends GameObject implements Updatable {

	
//////////////////////////////////////////////Member variables//////////////////////////////////////////////////////////
	
	private Vector2 dirAndVel; //vector for direction and velocity
	private final float VELOCITY = 100; //constant velocity float of 100

	
////////////////////////////////////////////Member Methods//////////////////////////////////////////////////////////////////////////////
	
	//constructor takes three parameters; texture, direction vector, and position vector
public Missile(Texture tex, Vector2 dir, Vector2 pos){ 
		
			sprite = new Sprite(tex); //sprite initialized to texture parameter
			sprite.setOrigin(tex.getWidth()/2, tex.getHeight()/2); //setting origin to half w/h of text
			sprite.setPosition(pos.x, pos.y); //sprite position set to pos vector values
			dirAndVel = new Vector2(dir.x, -dir.y); //set to negative dir vector values
			dirAndVel.scl(VELOCITY); //same as dirandVel *= velocity - we are scaling dirandVel by velocity
			sprite.rotate(dirAndVel.angle() + 90); //rotating sprite by dirandVel.angle + 90
			setIsDrawable(true);	 //is drawable
		}

	
	//overriding update method accepts delta time
	@Override
	public void update(float deltaTime) {
		
		//sprite translates based on delta time,scaling dirAndVel vector components by deltaTime
		sprite.translate(dirAndVel.x * deltaTime, dirAndVel.y * deltaTime);
	}
	


	}