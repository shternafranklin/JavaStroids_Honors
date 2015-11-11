package com.gmail.tamtyberg.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

///////////////////////////////////////// CONSTANTS//////////////////////////////////////////////////////////////////////////
	
	public final static int ASTEROIDS_SIZE = 32; //asteroid size
	public static final int SHIP_WIDTH = 16; //ship width
	public static final int SHIP_HEIGHT = 16; //ship height
	
	
	public static final ArrayList<String> positiveList = new ArrayList<>(Arrays.asList("Way to go!", "You're on Fire!", "Alright!", "Rock n Roll!", 
			"Super!", "You Rock!", "Sizzle!", "Asteroid CRUSH!!"));
	public static final ArrayList<String> negativeList = new ArrayList<>(Arrays.asList("Seriously?", "You suck!!", "Can't you do anything right!!", "Slow poke!!", 
			"Apple shoots faster than that!", "GO HOME!!!", "LOSER!!!!", "DOUBLE LOSER!!"));
	
	public static final ArrayList<String> mixedList = new ArrayList<>(Arrays.asList("Seriously?", "You're on Fire!", "Rock n Roll!", "Slow poke!!", 
			"Apple shoots faster than that!", "GO HOME!!!", "Super!", "Asteroid CRUSH!!"));
	
}
