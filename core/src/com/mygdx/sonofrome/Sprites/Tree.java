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
import com.mygdx.sonofrome.Tools.Constants;

public class Tree  extends InteractiveTileObject {

    public Tree(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData("Tree");
        setCategoryFilter(Constants.BIT_TREE);
    }

    @Override
    public void lovit() {
        System.out.println("Lovit");
        setCategoryFilter(Constants.BIT_DEFAULT);
    }

    public void mor() {
        System.out.println("Lovit");
        setCategoryFilter(Constants.BIT_DEFAULT);
    }
}
