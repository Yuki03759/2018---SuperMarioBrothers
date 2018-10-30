package com.zotcomm.zotrio.Enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.Array;
import com.zotcomm.zotrio.Screens.MasterScreen;
import com.zotcomm.zotrio.Zotrio;

public class Goomba extends Enemy {

    private Array<TextureRegion> goombaFrame;
    private Animation goombaAnimation;
    private float timer;

    public Goomba(MasterScreen screen, MapObject object) {
        super(screen, object);

        goombaFrame = new Array<TextureRegion>();

        for(int i = 0; i < 2; i++)
            goombaFrame.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i*16, 0, 16, 16));
       goombaAnimation = new Animation(0.4f, goombaFrame);

        fixture.setUserData(this);

        Filter filter = new Filter();
        filter.categoryBits = Zotrio.ENEMY_FILTER;
        fixture.setFilterData(filter);

       setRegion(screen.getAtlas().findRegion("goomba"), 0,0,16,16);

        this.setPosition(mainBody.getPosition().x - getWidth()/2, mainBody.getPosition().y - getHeight()/2);
    }
}
