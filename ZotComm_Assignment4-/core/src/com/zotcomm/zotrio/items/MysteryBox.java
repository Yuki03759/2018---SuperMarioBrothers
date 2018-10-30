package com.zotcomm.zotrio.items;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.zotcomm.zotrio.HUD;
import com.zotcomm.zotrio.Screens.MasterScreen;
import com.zotcomm.zotrio.Zotrio;

public class MysteryBox  {
    public BodyDef bodyDef = new BodyDef();
    public PolygonShape shape = new PolygonShape();
    public FixtureDef fixtureDef = new FixtureDef();
    public Body mainbody;
    public Fixture fixture;
    public MasterScreen screen;

    public MysteryBox(MasterScreen screen, MapObject object){
        this.screen = screen;
        World world = screen.getBox2DWorld();

        Rectangle rect1 = ((RectangleMapObject) object).getRectangle(); // Get the rectangle from the map, and set a pointer
        bodyDef.type = BodyDef.BodyType.StaticBody; // Define the body to be a static body
        bodyDef.position.set((rect1.getX() + rect1.getWidth() / 2)  / Zotrio.resizePPM, (rect1.getY() + rect1.getHeight() / 2)  / Zotrio.resizePPM); // Center the position
        mainbody = world.createBody(bodyDef); // Create and generate the main body

        shape.setAsBox(rect1.getWidth() / 2 / Zotrio.resizePPM, rect1.getHeight() / 2 / Zotrio.resizePPM); // Set the shape as a box and center it.
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Zotrio.MYSTERY_BOX_FILTER;
        fixture  = mainbody.createFixture(fixtureDef);
        fixture.setUserData(this);
    }

    public void hitByHead()
    {
        TiledMapTileLayer layer = (TiledMapTileLayer) screen.map.getLayers().get(1);
        TiledMapTileLayer.Cell cell = layer.getCell((int)(mainbody.getPosition().x * Zotrio.resizePPM / 16),
                    (int)(mainbody.getPosition().y * Zotrio.resizePPM / 16));
        cell.setTile(null);
        HUD.scoreAdder(200);

        Filter filter = new Filter();
        filter.categoryBits =  Zotrio.DESTROYED_BOX;
        fixture.setFilterData(filter);

    }

}
