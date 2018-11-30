package com.mygdx.sonofrome.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sonofrome.Tools.Constants;

public class Ground extends InteractiveTileObject {
    public Ground(World world, TiledMap map, Rectangle bounds) {

        super(world, map, bounds);
        fixture.setUserData("ground");
        setCategoryFilter(Constants.BIT_GROUND);


    }

    @Override
    public void lovit() {
        System.out.println("Lovit");
        setCategoryFilter(Constants.BIT_DEFAULT);
    }
}
