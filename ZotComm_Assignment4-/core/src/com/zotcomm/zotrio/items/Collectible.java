package com.zotcomm.zotrio.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.zotcomm.zotrio.Screens.MasterScreen;
import com.zotcomm.zotrio.Zotrio;

public abstract class Collectible extends Sprite {
  public  BodyDef bodyDef = new BodyDef();
  public  PolygonShape shape = new PolygonShape();
  public  FixtureDef fixtureDef = new FixtureDef();
  public  Body mainBody;
  public  World world;
  public   Fixture fixture;
  public boolean isDestroyed ;
  public MasterScreen screen;
  public boolean setToDestroy;

    public Collectible(MasterScreen screen, MapObject object) {

            this.screen = screen;
            this.world = screen.getBox2DWorld();
            setToDestroy = false;
            isDestroyed = false;
            Rectangle rect1 = ((RectangleMapObject) object).getRectangle(); // Get the rectangle from the map, and set a pointer
            bodyDef.type = BodyDef.BodyType.StaticBody; // Define the body to be a static body

            bodyDef.position.set((rect1.getX() + rect1.getWidth() / 2)  / Zotrio.resizePPM, (rect1.getY() + rect1.getHeight() / 2)  / Zotrio.resizePPM); // Center the position
            mainBody = world.createBody(bodyDef); // Create and generate the main body

            shape.setAsBox(rect1.getWidth() / 2 / Zotrio.resizePPM, rect1.getHeight() / 2 / Zotrio.resizePPM); // Set the shape as a box and center it.
            fixtureDef.shape = shape;
            fixtureDef.isSensor = true;
            fixture =  mainBody.createFixture(fixtureDef);


            this.setPosition(mainBody.getPosition().x, mainBody.getPosition().y);
            this.setBounds(getX(),getY(), 16/Zotrio.resizePPM, 16/Zotrio.resizePPM);

        }

       public void  destroy()
       {
           if (!isDestroyed) {

               world.destroyBody(mainBody);
               isDestroyed = true;

           }
       }

       @Override
        public void draw(Batch batch){
            if(!isDestroyed)
            {
                super.draw(batch);
            }
       }

}


