package com.zotcomm.zotrio.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zotcomm.zotrio.Enemy.Enemy;
import com.zotcomm.zotrio.HUD;
import com.zotcomm.zotrio.Listener;
import com.zotcomm.zotrio.MarioChar;
import com.zotcomm.zotrio.TouchControl;
import com.zotcomm.zotrio.WorldCreator;
import com.zotcomm.zotrio.Zotrio;
import com.zotcomm.zotrio.items.Bricks;
import com.zotcomm.zotrio.items.Coin;
import com.zotcomm.zotrio.items.FireFlower;
import com.zotcomm.zotrio.items.Mushroom;
import com.badlogic.gdx.audio.Music;

public class MasterScreen implements Screen {

    // Camera & View Variables
    private TextureAtlas atlas;
    private OrthographicCamera mainCamView;
    private Viewport mainWindow;
    public Zotrio mainGame;
    private HUD mainHud;
    private TouchControl mainControl;

    // Collectible Objects and Enemy Arrays
    public Array<Coin> coinsArray;
    public Array<Mushroom> mushroomsArray;
    public Array<Bricks> bricksArray;
    public Array<FireFlower> fireFlowerArray;
    public Array<Enemy> goomba;
    public Array<Enemy> turtle;
    public Array<Enemy> turtleShell;

    // Music Loader
    public static Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/bgMusic.mp3"));

    // Map Loaders
    private TmxMapLoader mainMapLoader;
    public TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;


    //Box2D World Variables
    private World box2DWorld;
    private Box2DDebugRenderer renderer2D;
    public MarioChar mario;
    public String levelName;


    public MasterScreen (Zotrio game, String levelName)
    {
        this.levelName = levelName;
        atlas = new TextureAtlas("Characters.pack");
        this.mainGame = game;

        coinsArray = new Array<Coin>();
        mushroomsArray = new Array<Mushroom>();
        bricksArray = new Array<Bricks>();
        fireFlowerArray = new Array<FireFlower>();

        goomba = new Array<Enemy>();
        turtle = new Array<Enemy>();
        turtleShell = new Array<Enemy>();

        // Create new world view camera to track player
        mainCamView = new OrthographicCamera();

        //Maintain Virtual Aspect Ratio
        mainWindow = new FitViewport(Zotrio.V_WIDTH / Zotrio.resizePPM, Zotrio.V_HEIGHT / Zotrio.resizePPM, mainCamView);

        //Create new HUD Object
        mainHud = new HUD(game.masterBatch);

        // Make new Controller
        mainControl = new TouchControl(game.masterBatch);

        // Load map and stuff like rendering
        mainMapLoader = new TmxMapLoader();
        map = mainMapLoader.load(levelName);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Zotrio.resizePPM);

        // Center game camera
        mainCamView.position.set(mainWindow.getWorldWidth() / 2, mainWindow.getWorldHeight() / 2, 0);

        box2DWorld = new World(new Vector2(0, -10), true);
        renderer2D = new Box2DDebugRenderer();

        mario = new MarioChar(this);

        box2DWorld.setContactListener(new Listener(mario));

        new WorldCreator(this, map);
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public World getBox2DWorld() {
        return box2DWorld;
    }

    public void handleInput(float timechange) {
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || mainControl.isRightActive() && mario.box2Body.getLinearVelocity().x <= 1){
            mario.box2Body.applyLinearImpulse(new Vector2(0.1f,0), mario.box2Body.getWorldCenter() ,true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || mainControl.isLeftActive() && mario.box2Body.getLinearVelocity().x >= -1){
            mario.box2Body.applyLinearImpulse(new Vector2(-0.1f,0), mario.box2Body.getWorldCenter() ,true);
        }
        if((Gdx.input.isKeyJustPressed(Input.Keys.UP) || mainControl.isUpActive() || mainControl.isbPressed() || mainControl.isaPressed()) && mario.box2Body.getLinearVelocity().y == 0) {
            mario.box2Body.setLinearVelocity(mario.box2Body.getLinearVelocity().x, 4);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            //fireball
        }

    }

    public synchronized void update(float timechange)
    {
        // Handles the user input
        handleInput(timechange);
        // Progress 1 step for world physics
        box2DWorld.step(1/60f, 6, 2);
        mario.marioUpdate(timechange);
        mainHud.update(timechange);

        for(Coin coin: coinsArray)
        {
            if(coin.setToDestroy) {
                coinsArray.removeValue(coin, true);
                coin.destroy();
                HUD.scoreAdder(200);
            }
        }

        for(Mushroom mushroom: mushroomsArray){
            if(mushroom.setToDestroy) {
                mushroomsArray.removeValue(mushroom, true);
                mushroom.destroy();
                HUD.scoreAdder(1000);
                //make mario big
                if (!mario.isBig){
                    mario.makeBig();
                }
            }
        }

        for(FireFlower fireFlower: fireFlowerArray){
            if(fireFlower.setToDestroy){
                fireFlowerArray.removeValue(fireFlower, true);
                fireFlower.destroy();
                HUD.scoreAdder(1000);
            }
        }

        for(Bricks brick: bricksArray){
            if(brick.setToDestroy){
                bricksArray.removeValue(brick, true);
                brick.destroy();
            }
        }

        mainCamView.position.x = mario.box2Body.getPosition().x ;
        mainCamView.update();
        mapRenderer.setView(mainCamView);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Update + Render Logic
        update(delta);

        // Clear Game Screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render game map
        mapRenderer.render();

        // Draw Debug Lines
        renderer2D.render(box2DWorld, mainCamView.combined);

        // Set the batch to draw HUD Camera
        mainGame.masterBatch.setProjectionMatrix(mainCamView.combined);
        mainGame.masterBatch.begin();
        mario.draw(mainGame.masterBatch);

        for (Coin coin : coinsArray) {
            coin.draw(mainGame.masterBatch);
        }

        for (Mushroom mushroom : mushroomsArray) {
            mushroom.draw(mainGame.masterBatch);
        }

        for (FireFlower fireFlower: fireFlowerArray){
            fireFlower.draw(mainGame.masterBatch);
        }

        for(Enemy enemyGoomba: goomba) {
            enemyGoomba.draw(mainGame.masterBatch);
        }

        for(Enemy enemyTurtle: turtle) {
            enemyTurtle.draw(mainGame.masterBatch);
        }

        for(Enemy enemyTurtleShell: turtleShell) {
            enemyTurtleShell.draw(mainGame.masterBatch);
        }

        mainGame.masterBatch.end();
        mainGame.masterBatch.setProjectionMatrix(mainHud.hudStage.getCamera().combined);
        mainHud.hudStage.draw();
        mainControl.draw();
    }

    @Override
    public void resize(int width, int height) {
        /* Adjust the window to a standard viewing size relative to the screen */
        mainWindow.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bgMusic.dispose();
    }
}


