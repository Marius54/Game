package com.mygdx.sonofrome.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sonofrome.Screens.PlayScreen;
import com.mygdx.sonofrome.Tools.Constants;

public class WoodBlock extends InteractiveTileObject {
    int life = 50;

    public WoodBlock(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Constants.BIT_WOODBLOCK);
    }
    @Override
    public boolean playerAction() {
        if(life == 0) {
            getCell(4).setTile(null);
            setCategoryFilter(Constants.BIT_DEFAULT);
            super.destroyBody();
            PlayScreen.getInstance().getHud().addUndergroundResources(10);
            return true;
        }
        life--;
        return false;
    }
}
