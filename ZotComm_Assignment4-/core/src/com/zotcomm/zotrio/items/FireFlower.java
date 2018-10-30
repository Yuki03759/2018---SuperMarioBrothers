package com.zotcomm.zotrio.items;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Filter;
import com.zotcomm.zotrio.Screens.MasterScreen;
import com.zotcomm.zotrio.Zotrio;

public class FireFlower extends Collectible{

    public FireFlower(MasterScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);

        Filter filter = new Filter();
        filter.categoryBits = Zotrio.MUSHROOM_FILTER;
        fixture.setFilterData(filter);

        setRegion(screen.getAtlas().findRegion("fireball"), 1,1,8,8);

        this.setPosition(mainBody.getPosition().x - getWidth()/2, mainBody.getPosition().y - getHeight()/2);

    }

}
