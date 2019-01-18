package com.mygdx.sonofrome.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sonofrome.Screens.PlayScreen;
import com.mygdx.sonofrome.Tools.Constants;

public class Tree  extends InteractiveTileObject {
    int life = 50;
    private Sound axeSound;
    boolean first = true;

    public Tree(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
//        fixture.setDensity((float) 1000);
        setCategoryFilter(Constants.BIT_TREE);
        axeSound = Gdx.audio.newSound(Gdx.files.internal("audio/axeSoundEffect.mp3"));
    }

    @Override
    public boolean playerAction() {
        if(first == true){
            axeSound.play(0.3f);
            first = false;
        }
        if(life == 0) {
            getCell(4).setTile(null);
            setCategoryFilter(Constants.BIT_DEFAULT);
            super.destroyBody();
            PlayScreen.getInstance().getHud().addWood(20);
            PlayScreen.getInstance().getHud().addFood(10);
            PlayScreen.getInstance().getHud().addFood(5);
            return true;
        }
        life--;
        return false;
    }
}
