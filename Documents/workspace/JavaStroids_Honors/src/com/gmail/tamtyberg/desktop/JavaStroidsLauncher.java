package com.gmail.tamtyberg.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gmail.tamtyberg.game.Game;



public class JavaStroidsLauncher {
	
	public static void main (String[] arg) {
		
		//Lwjgl object and initializes it
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "JavaStroids"; //gives title to frame
		
		config.width = 800; //frame width
		
		config.height = 480;  //frame height
		
		//this one line is the whole game
		new LwjglApplication(new Game(), config); //lgwjglApp with Game passed in and config object
	}
}
