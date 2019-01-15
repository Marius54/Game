package com.mygdx.sonofrome.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sonofrome.Screens.PlayScreen;
import com.mygdx.sonofrome.Tools.Constants;

public class Tree  extends InteractiveTileObject {
    int life = 50;

    public Tree(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
//        fixture.setDensity((float) 1000);
        setCategoryFilter(Constants.BIT_TREE);
    }

    @Override
    public boolean playerAction() {
        if(life == 0) {
            getCell(4).setTile(null);
            setCategoryFilter(Constants.BIT_DEFAULT);
            PlayScreen.getInstance().getHud().addWood(20);
            return true;
        }
        life--;
        return false;
    }
}
