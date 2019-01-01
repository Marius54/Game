package com.mygdx.sonofrome.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sonofrome.Tools.Constants;

public class Water extends InteractiveTileObject {
    public Water(World world, TiledMap map, Rectangle bounds) {

        super(world, map, bounds);
        fixture.setUserData("water");
        setCategoryFilter(Constants.BIT_WATER);
    }

    @Override
    public void playerAction() {

    }
}
