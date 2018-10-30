package com.zotcomm.zotrio;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.zotcomm.zotrio.Enemy.Goomba;
import com.zotcomm.zotrio.Enemy.Turtle;
import com.zotcomm.zotrio.Enemy.TurtleShell;
import com.zotcomm.zotrio.Screens.MasterScreen;
import com.zotcomm.zotrio.items.Bricks;
import com.zotcomm.zotrio.items.Coin;
import com.zotcomm.zotrio.items.FireFlower;
import com.zotcomm.zotrio.items.Mushroom;
import com.zotcomm.zotrio.items.MysteryBox;

public class WorldCreator {
    public WorldCreator(MasterScreen screen, TiledMap map){

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body mainbody;
        World world = screen.getBox2DWorld();
        // Loop to create the bodies and fixtures for the entire ground

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) // For each object in layer 2 of the map
        {
            Rectangle rect1 = ((RectangleMapObject) object).getRectangle(); // Get the rectangle from the map, and set a pointer
            bodyDef.type = BodyDef.BodyType.StaticBody; // Define the body to be a static body
            bodyDef.position.set((rect1.getX() + rect1.getWidth() / 2)  / Zotrio.resizePPM, (rect1.getY() + rect1.getHeight() / 2)  / Zotrio.resizePPM); // Center the position
            mainbody = world.createBody(bodyDef); // Create and generate the main body

            shape.setAsBox(rect1.getWidth() / 2 / Zotrio.resizePPM, rect1.getHeight() / 2 / Zotrio.resizePPM); // Set the shape as a box and center it.
            fixtureDef.shape = shape;
            mainbody.createFixture(fixtureDef);

        }

        // Loop to create the bodies and fixtures for the pipes
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) // For each object in layer 2 of the map
        {
            Rectangle rect1 = ((RectangleMapObject) object).getRectangle(); // Get the rectangle from the map, and set a pointer
            bodyDef.type = BodyDef.BodyType.StaticBody; // Define the body to be a static body
            bodyDef.position.set((rect1.getX() + rect1.getWidth() / 2)  / Zotrio.resizePPM, (rect1.getY() + rect1.getHeight() / 2)  / Zotrio.resizePPM); // Center the position
            mainbody = world.createBody(bodyDef); // Create and generate the main body

            shape.setAsBox(rect1.getWidth() / 2 / Zotrio.resizePPM, rect1.getHeight() / 2 / Zotrio.resizePPM); // Set the shape as a box and center it.
            fixtureDef.shape = shape;
            mainbody.createFixture(fixtureDef);

        }
        // Loop to create the bodies and fixtures for mystery box
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) // For each object in layer 2 of the map
        {
          new MysteryBox(screen, object);
        }

        // Loop to create the bodies and fixtures for bricks
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) // For each object in layer 2 of the map
        {
            //new Bricks(screen, object);
            screen.bricksArray.add(new Bricks(screen, object));
        }
        // Loop to create the bodies and fixtures for the coins
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) // For each object in layer 2 of the map
        {
           screen.coinsArray.add( new Coin(screen, object) );
        }
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) // For each object in layer 2 of the map
        {
           screen.goomba.add(new Goomba(screen, object));
        }
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) // For each object in layer 2 of the map
        {
            screen.mushroomsArray.add(new Mushroom(screen, object));
        }
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) // For each object in layer 2 of the map
        {
            screen.turtle.add(new Turtle(screen, object));
        }
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) // For each object in layer 2 of the map
        {
            screen.turtleShell.add(new TurtleShell(screen, object));
        }
    }
}
