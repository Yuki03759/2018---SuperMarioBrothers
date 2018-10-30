package com.zotcomm.zotrio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.zotcomm.zotrio.items.Bricks;
import com.zotcomm.zotrio.items.Collectible;
import com.zotcomm.zotrio.items.MysteryBox;

public class Listener implements ContactListener {
    MarioChar mario;
    public Listener(MarioChar mario)
    {
        super();
        this.mario = mario;
    }


    @Override
    public void beginContact(Contact contact)
    {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int collisionItems =  fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        switch(collisionItems){
                //Case where mario hits a coin
            case Zotrio.MARIO_FILTER | Zotrio.COIN_FILTER:
            case Zotrio.MARIO_HEAD_FILTER | Zotrio.COIN_FILTER:
                if( fixtureA.getFilterData().categoryBits == Zotrio.COIN_FILTER )
                    mario.collect((Collectible) (fixtureA.getUserData()));
                else
                    mario.collect((Collectible) (fixtureB.getUserData()));
                break;
                //case where mario head hits mystery box
            case Zotrio.MARIO_HEAD_FILTER | Zotrio.MYSTERY_BOX_FILTER:
                    if( fixtureA.getFilterData().categoryBits == Zotrio.MYSTERY_BOX_FILTER )
                        ((MysteryBox) fixtureA.getUserData()).hitByHead();
                    else
                        ((MysteryBox) fixtureB.getUserData()).hitByHead();
                break;
            //case where mario head hits a brick
            case Zotrio.MARIO_HEAD_FILTER | Zotrio.BRICKS_FILTER:
                    if( fixtureA.getFilterData().categoryBits == Zotrio.BRICKS_FILTER )
                          ((Bricks) fixtureA.getUserData()).hitByHead();
                      else
                          ((Bricks) fixtureB.getUserData()).hitByHead();
                break;
            case Zotrio.MARIO_FILTER | Zotrio.MUSHROOM_FILTER:
            case Zotrio.MARIO_HEAD_FILTER | Zotrio.MUSHROOM_FILTER:
                if(fixtureA.getFilterData().categoryBits == Zotrio.MUSHROOM_FILTER)
                    mario.collect((Collectible)(fixtureA.getUserData()));
                else
                    mario.collect((Collectible)(fixtureB.getUserData()));
                break;

            case Zotrio.MARIO_FILTER | Zotrio.ENEMY_FILTER:
            case Zotrio.MARIO_HEAD_FILTER | Zotrio.ENEMY_FILTER:
                mario.hit();
                mario.die();
                break;
                default:
                    Gdx.app.log("Begin Contact", "Contact with something");
                break;

        }

    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("End Contact", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
