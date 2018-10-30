package com.zotcomm.zotrio;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zotcomm.zotrio.Screens.MasterScreen;

import java.util.Random;


public class Zotrio extends Game {
	public SpriteBatch masterBatch;
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float resizePPM = 100;
	public static final float PLAYER_JUMP_RATE = 5;

	public static final short COIN_FILTER = 2;
    public static final short BRICKS_FILTER = 4;
    public static final short MYSTERY_BOX_FILTER = 8;
    public static final short MARIO_FILTER = 16;
    public static final short MARIO_HEAD_FILTER = 32;
    public static final short ENEMY_FILTER = 64;
	public static final short ENEMY_HEAD_FILTER = 128;
	public static final short MUSHROOM_FILTER = 256;
	public static final short DESTROYED_BOX = 1024;

	private String[] levelArray = new String[2];
	Random rand = new Random();
	private int value = 0;
	private int currentLevel;
	private String[] levels = new String[3];


	public void gameover(){
		setScreen(new MasterScreen(this, levels[currentLevel]));
    }


	@Override
	public void create () {
		masterBatch = new SpriteBatch();
		currentLevel = 0;
		levels[0] = "level1.tmx";
		levels[1] = "level2.tmx";
		levels[2] = "level3.tmx";
		setScreen(new MasterScreen(this, levels[currentLevel]));

            MasterScreen.bgMusic.play();

	}

	@Override
	public void render () {
	 super.render();
	}
	
	@Override
	public void dispose () {
		masterBatch.dispose();
		MasterScreen.bgMusic.dispose();
	}

	public void nextLevel() {

			currentLevel++;
			setScreen(new MasterScreen(this, levels[currentLevel%3]));

	}
}
