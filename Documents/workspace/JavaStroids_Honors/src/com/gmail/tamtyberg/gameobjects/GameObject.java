package com.gmail.tamtyberg.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;


//All objects in game will inherit from this class so they will all have option to be updatable, drawable
//and contain a sprite instance

public abstract class GameObject {
	
///////////////////////////Class/member Variables//////////////////////////////////////////////////////////////	
	
	
	private boolean drawable; //boolean to set if object is drawable
	private boolean updatable;//boolean to set if object is updatable
	public Sprite sprite;   //sprite member variables
	
	
///////////////////////////Member Methods//////////////////////////////////////////////////////////////	
	
	public GameObject(){  //default constructor
	}
	
	
//////////Getters and Setters/////////	
	public boolean isDrawable(){
		return drawable;
	}
	public boolean isUpdatable(){
		return updatable;
	}
	public void setIsDrawable(boolean val){
		drawable = val;
	}
	public void setIsUpdatable(boolean val){
		updatable = val;
	}
}