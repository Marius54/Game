package com.mygdx.sonofrome.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sonofrome.SonOfRome;

public class Tree  extends InteractiveTileObject {

    private World world;
    private TiledMap map;
    private TiledMapTile tile;
    private Rectangle bounds;
    private Body body;

    public Tree(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2)/ SonOfRome.PPM, (bounds.getY() + bounds.getHeight() / 2)/ SonOfRome.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / SonOfRome.PPM, bounds.getHeight() / 2 / SonOfRome.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
