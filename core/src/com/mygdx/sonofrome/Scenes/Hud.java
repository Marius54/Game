package com.mygdx.sonofrome.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sonofrome.SonOfRome;
import com.mygdx.sonofrome.Tools.Constants;


public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private static Integer life;
    private static Integer water;
    private static Integer food;
    private static Integer wood;
    private static Integer udergroundResources;

    private Label lifeLabel;
    private Label lifeTextLabel;
    private Label waterLabel;
    private Label waterTextLabel;
    private Label foodLabel;
    private Label foodTextLabel;
    private Label woodLabel;
    private Label woodTextLabel;
    private Label udergroundResourcesLabel;
    private Label udergroundResourcesTextLabel;

    boolean upPressed, downPressed, leftPressed, rightPressed, actionPressed;

    public Hud(SpriteBatch sb){
        //define our tracking variables
        life = 100;
        water = 300;
        food = 300;
        wood = 300;
        udergroundResources = 300;


        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(Constants.V_WIDTH,Constants.V_HEIGHT , new OrthographicCamera());
        stage = new Stage(viewport, sb);

        stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                    case Input.Keys.SPACE:
                        actionPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                    case Input.Keys.SPACE:
                        actionPressed = false;
                        break;
                }
                return true;
            }
        });
        Gdx.input.setInputProcessor(stage);

        //define a table used to organize our hud's labels
        Table table = new Table();
        Table controls = new Table();
//        Table controls2 = new Table();
        //Top-Align table
        table.top();
        controls.left().bottom();
//        controls2.right().bottom();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        lifeLabel =new Label(String.format("%03d", life), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        waterLabel =new Label(String.format("%03d", water), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        foodLabel =new Label(String.format("%03d", food), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        woodLabel =new Label(String.format("%03d", wood), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        udergroundResourcesLabel =new Label(String.format("%03d", udergroundResources), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lifeTextLabel = new Label("Life", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        waterTextLabel = new Label("Water", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        foodTextLabel = new Label("Food", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        woodTextLabel = new Label("Wood", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        udergroundResourcesTextLabel = new Label("Underground resources", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Image upImg = new Image(new Texture("controls//up.png"));
        upImg.setSize(32, 32);
        upImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image downImg = new Image(new Texture("controls//down.png"));
        downImg.setSize(32, 32);
        downImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        Image rightImg = new Image(new Texture("controls//right.png"));
        rightImg.setSize(32, 32);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("controls//left.png"));
        leftImg.setSize(32, 32);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        Image attackImg = new Image(new Texture("controls//attack.png"));
        attackImg.setSize(32, 32);
        attackImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                actionPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                actionPressed = false;
            }
        });

        controls.add();
        controls.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        controls.add();
        controls.row().pad(5, 5, 5, 5);
        controls.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        controls.add(attackImg).size(attackImg.getWidth(), attackImg.getHeight());
        controls.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        controls.row().padBottom(5);
        controls.add();
        controls.add(downImg).size(downImg.getWidth(), downImg.getHeight());
        controls.add();

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(lifeTextLabel).expandX().padTop(10);
        table.add(waterTextLabel).expandX().padTop(10);
        table.add(foodTextLabel).expandX().padTop(10);
        table.add(woodTextLabel).expandX().padTop(10);
        table.add(udergroundResourcesTextLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(lifeLabel).expandX();
        table.add(waterLabel).expandX();
        table.add(foodLabel).expandX();
        table.add(woodLabel).expandX();
        table.add(udergroundResourcesLabel).expandX();

        //add our table to the stage
        stage.addActor(table);
        stage.addActor(controls);
//        stage.addActor(controls2);

    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isActionPressed() {
        return actionPressed;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
