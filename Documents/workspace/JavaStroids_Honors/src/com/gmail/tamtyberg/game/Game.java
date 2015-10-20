package com.gmail.tamtyberg.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


public class Game extends ApplicationAdapter {

//////////////////////////////////////////////Member Variables/////////////////////////////////////////////////////////////////

		private Controller control;  //instance of Controller
		private Renderer render; 	//instance of Renderer
	
		
//////////////////////////////////////////////////Member Methods////////////////////////////////////////////////////////////////////////
		
		//create method- creates controller object and then using that a renderer object
		@Override
		public void create () {
			control = new Controller();  //initializes control
			render = new Renderer(control); //initialzies render by passing control in as a parameter to Renderer constructor
		}
		
		//render method - updates control and then renders render
		@Override
		public void render () {
			
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
			control.update(); // Process inputs and update game world.
			render.render();  //renders render 
		}
		
		//dispose method
		@Override
		public void dispose(){
			control.dispose();
		}
		
	}


