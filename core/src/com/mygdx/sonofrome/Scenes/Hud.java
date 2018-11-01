package com.mygdx.sonofrome.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sonofrome.SonOfRome;


public class Hud {
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

    public Hud(SpriteBatch sb){
        //define our tracking variables
        life = 100;
        water = 300;
        food = 300;
        wood = 300;
        udergroundResources = 300;


        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(SonOfRome.V_WIDTH,SonOfRome.V_HEIGHT , new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
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

    }
}
