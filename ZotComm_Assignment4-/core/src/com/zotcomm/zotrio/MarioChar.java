package com.zotcomm.zotrio;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.zotcomm.zotrio.Screens.MasterScreen;
import com.zotcomm.zotrio.items.Collectible;

public class MarioChar extends Sprite {

    private TextureRegion smallMario;
    public World box2World;
    public Body box2Body;
    public MasterScreen screen;
    public float timer;
    public boolean isBig;

    public enum marioState {
        JUMP,
        STAND,
        RUN
    }

    public marioState currentState;

    private Animation marioRun;
    private Animation marioJump;

    public MarioChar(MasterScreen screen) {
        super(screen.getAtlas().findRegion("little_mario"));

        this.screen = screen;
        this.box2World = screen.getBox2DWorld();
        isBig = false;

        Array<TextureRegion> marioFrames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++)
            marioFrames.add(new TextureRegion(getTexture(), i * 16, 11, 16, 16));

        marioRun = new Animation(0.1f, marioFrames);

        for (int i = 1; i < 4; i++)
            marioFrames.add(new TextureRegion(getTexture(), (i+3) * 16, 15, 16, 16));

        marioJump = new Animation(0.1f, marioFrames);

        MarioShape();
        smallMario = new TextureRegion(getTexture(), 1, 11, 16, 16);
        setBounds(0, 0, 16 / Zotrio.resizePPM, 16 / Zotrio.resizePPM);
        setRegion(smallMario);

    }

    public void makeBig() {
        BigMarioShape();
        setBounds(0, 0, 32 / Zotrio.resizePPM, 32 / Zotrio.resizePPM);
        setRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 48);
        isBig = true;
    }

    public synchronized void marioUpdate(float timechange) {
        setPosition(box2Body.getPosition().x - getWidth() / 2, box2Body.getPosition().y - getHeight() / 2);
        setRegion(getMarioFrame(timechange));

        if (box2Body.getPosition().y < 0) {
            die();
        }

        if (box2Body.getPosition().x > 3584 / Zotrio.resizePPM) {
            screen.mainGame.nextLevel();
        }
    }

    public void collect(Collectible item) {
        item.setToDestroy = true;

    }

    public TextureRegion getMarioFrame(float delta){
        currentState = getMarioState();
        TextureRegion currentMarioRegion;

        if(currentState == marioState.JUMP){
            currentMarioRegion = (TextureRegion)marioJump.getKeyFrame(timer);
        }
        else if(currentState == marioState.RUN){
            currentMarioRegion = (TextureRegion)marioRun.getKeyFrame(timer, true);
        }
        else{
            currentMarioRegion = smallMario;
        }

        return currentMarioRegion;
    }

    public void MarioShape() {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();

        bodyDef.position.set(32 / Zotrio.resizePPM, 32 / Zotrio.resizePPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        box2Body = box2World.createBody(bodyDef);

        circleShape.setRadius(5 / Zotrio.resizePPM);

        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = Zotrio.MARIO_FILTER;
        fixtureDef.friction = 0.7f;
        box2Body.createFixture(fixtureDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / Zotrio.resizePPM, 7 / Zotrio.resizePPM), new Vector2(2 / Zotrio.resizePPM, 7 / Zotrio.resizePPM));
        fixtureDef.isSensor = true;
        fixtureDef.shape = head;
        fixtureDef.filter.categoryBits = Zotrio.MARIO_HEAD_FILTER;
        box2Body.createFixture(fixtureDef).setUserData("head");
    }

    public boolean isBig() {
        return this.isBig;
    }

    public void BigMarioShape() {

        Vector2 currentPosition = box2Body.getPosition();
        box2World.destroyBody(box2Body);

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();

        bodyDef.position.set(currentPosition.add(0, 10 / Zotrio.resizePPM));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        box2Body = box2World.createBody(bodyDef);

        circleShape.setRadius(10 / Zotrio.resizePPM);

        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = Zotrio.MARIO_FILTER;
        fixtureDef.friction = 0.7f;
        box2Body.createFixture(fixtureDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / Zotrio.resizePPM, 20 / Zotrio.resizePPM), new Vector2(2 / Zotrio.resizePPM, 20 / Zotrio.resizePPM));
        fixtureDef.isSensor = true;
        fixtureDef.shape = head;
        fixtureDef.filter.categoryBits = Zotrio.MARIO_HEAD_FILTER;
        box2Body.createFixture(fixtureDef).setUserData("head");

        isBig = true;
    }

    public void hit(){

    }
    public void die() {
        screen.mainGame.gameover();
    }

    public marioState getMarioState() {
        if (box2Body.getLinearVelocity().y > 0 || box2Body.getLinearVelocity().y < 0)
            return marioState.JUMP;
        else if (box2Body.getLinearVelocity().x != 0)//box2Body.getLinearVelocity().x > 0 || box2Body.getLinearVelocity().x < 0)
            return marioState.RUN;
        else
            return marioState.STAND;
    }
}
