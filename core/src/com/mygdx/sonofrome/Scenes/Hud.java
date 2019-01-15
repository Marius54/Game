package com.mygdx.sonofrome.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sonofrome.Screens.PlayScreen;
import com.mygdx.sonofrome.Tools.BackgroundColor;
import com.mygdx.sonofrome.Tools.Constants;


public class Hud implements Disposable {
    private Table buildMenuTable, controlTable,table;
    public Stage stage;
    private Viewport viewport;
    private static Integer life,water,food,wood,udergroundResources;
    private Label lifeLabel,lifeTextLabel,waterLabel,waterTextLabel,foodLabel,foodTextLabel,woodLabel,
            woodTextLabel,undergroundResourcesLabel,undergroundResourcesTextLabel,playerLabel;
    boolean upPressed, downPressed, leftPressed, rightPressed, actionPressed,buildPressed;

    public Hud(SpriteBatch sb){
        life = 100;
        water = 300;
        food = 300;
        wood = 300;
        udergroundResources = 300;

        viewport = new FitViewport(Constants.V_WIDTH,Constants.V_HEIGHT , new OrthographicCamera());
        stage = new Stage(viewport, sb);
        defineKeyLIstener();
        createControlsTable();
        createInventoryTable();
        createbuildingtable();
    }

    public void defineKeyLIstener(){
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
    }

    public void createbuildingtable(){
        buildMenuTable = new Table();
        BackgroundColor backgroundColor = new BackgroundColor("white.png");
        backgroundColor.setColor(75, 43, 14, 255); // r, g, b, a

        buildMenuTable.setPosition(150,260);
        buildMenuTable.setBackground(backgroundColor);

        Image exitImg = new Image(new Texture("controls//exit.png"));
        exitImg.setSize(32, 32);
        exitImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setBuildMenuTableFalse();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        Image woodImg = new Image(new Texture("Map//wood.png"));
        woodImg.setSize(24, 24);
        woodImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PlayScreen.getInstance().setCurrentBlock(1);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        Image woodImg2 = new Image(new Texture("Map//wood.png"));
        woodImg2.setSize(24, 24);

        playerLabel = new Label( "Player", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        buildMenuTable.add(playerLabel).size(50,32).padLeft(20);
        buildMenuTable.add().size(32,32);
        buildMenuTable.add().size(32,32);
        buildMenuTable.add().size(32,32);
        buildMenuTable.add().size(32,32);
        buildMenuTable.add().size(32,32);
        buildMenuTable.add().size(32,32);
        buildMenuTable.add().size(32,32);
        buildMenuTable.add().size(32,32);
        buildMenuTable.add().size(32,32);
        buildMenuTable.add().size(32,32);
        buildMenuTable.add(exitImg).size(32,32);
        buildMenuTable.row();
        buildMenuTable.add();
        buildMenuTable.add(woodImg).pad(6);
        buildMenuTable.add(woodImg2).pad(6);
        buildMenuTable.setVisible(false);
        buildMenuTable.pack();

        stage.addActor(buildMenuTable);
    }

    public void createInventoryTable(){
        table = new Table();
        table.top();
        table.setFillParent(true);

        lifeLabel =new Label(String.format("%03d", life), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        waterLabel =new Label(String.format("%03d", water), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        foodLabel =new Label(String.format("%03d", food), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        woodLabel =new Label(String.format("%03d", wood), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        undergroundResourcesLabel =new Label(String.format("%03d", udergroundResources), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lifeTextLabel = new Label("Life", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        waterTextLabel = new Label("Water", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        foodTextLabel = new Label("Food", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        woodTextLabel = new Label("Wood", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        undergroundResourcesTextLabel = new Label("Underground resources", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(lifeTextLabel).expandX().padTop(10);
        table.add(waterTextLabel).expandX().padTop(10);
        table.add(foodTextLabel).expandX().padTop(10);
        table.add(woodTextLabel).expandX().padTop(10);
        table.add(undergroundResourcesTextLabel).expandX().padTop(10);

        table.row();
        table.add(lifeLabel).expandX();
        table.add(waterLabel).expandX();
        table.add(foodLabel).expandX();
        table.add(woodLabel).expandX();
        table.add(undergroundResourcesLabel).expandX();

        stage.addActor(table);
    }

    public void createControlsTable(){
        Image upImg = new Image(new Texture("controls//up.png"));
        upImg.setSize(32, 32);
        upImg.setPosition(64,64);
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
        downImg.setPosition(64,0);
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
        rightImg.setPosition(96,32);
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
        leftImg.setPosition(32,32);
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

        Image actionImg = new Image(new Texture("controls//attack.png"));
        actionImg.setSize(48, 48);
        actionImg.setPosition(540, 24);
        actionImg.addListener(new InputListener() {

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

        final Image buildImg = new Image(new Texture("controls//build.png"));
        buildImg.setSize(48, 48);
        buildImg.setPosition(576,80);
        buildImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildPressed = true;
                setBuildMenuTableTrue();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        stage.addActor(buildImg);
        stage.addActor(actionImg);
        stage.addActor(upImg);
        stage.addActor(downImg);
        stage.addActor(leftImg);
        stage.addActor(rightImg);
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

    public boolean isBuildPressed() {
        return buildPressed;
    }

    public void setBuildMenuTableTrue(){
        buildMenuTable.setVisible(true);
    }

    public void setBuildMenuTableFalse(){
        buildMenuTable.setVisible(false);
    }


    public void addWood(int wood){
        this.wood = this.wood + wood;
        woodLabel.setText(""+this.wood);
    }

    public void addFood(int food){
        this.food = this.food + food;
        foodLabel.setText(""+this.food);
    }

    public void addWater(int water){
        this.water = this.water + water;
        waterLabel.setText(""+this.water);
    }

    public void addLife(int life){
        this.life = this.life + life;
        lifeLabel.setText(""+this.life);
    }

    public void addUndergroundResources(int udergroundResources){
        this.udergroundResources = this.udergroundResources +udergroundResources;
        undergroundResourcesLabel.setText(""+this.udergroundResources);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
