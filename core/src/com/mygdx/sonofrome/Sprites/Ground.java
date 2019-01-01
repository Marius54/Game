package com.mygdx.sonofrome.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sonofrome.Tools.Constants;

public class Ground extends InteractiveTileObject {
    int life = 50;

    public Ground(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Constants.BIT_GROUND);


    }

    @Override
    public void playerAction() {
        if(life == 0) {
            getCell(4).setTile(null);
            setCategoryFilter(Constants.BIT_DEFAULT);
        }else{
            life--;
        }
    }
}
