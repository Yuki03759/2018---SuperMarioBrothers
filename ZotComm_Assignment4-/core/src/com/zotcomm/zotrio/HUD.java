package com.zotcomm.zotrio;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD {

    private Integer masterTimer;
    private float timeCount;
    private static int playerScore;
    private static int life;

    public Stage hudStage;
    private Viewport hudWindow;


    private Label scoreName;
    private Label lifeName;
    private Label timeName;

    private static Label totalScore;
    private static Label totalLife;
    private Label totalTime;

    private boolean dead = false;

    public HUD(SpriteBatch sb)
    {
        masterTimer = 300;
        playerScore = 0;
        timeCount = 0;
        life = 3;
        hudWindow = new FitViewport(Zotrio.V_WIDTH, Zotrio.V_HEIGHT, new OrthographicCamera());
        hudStage = new Stage(hudWindow, sb);

        /* Create a layout table in the stage */
        Table defaultLayout = new Table();
        defaultLayout.top();
        defaultLayout.setFillParent(true);

       Label.LabelStyle defaultLabelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        scoreName = new Label(String.format("ZOTRIO"), defaultLabelStyle);
        lifeName = new Label(String.format("Life"), defaultLabelStyle);
        timeName = new Label(String.format("Time"), defaultLabelStyle);
        totalScore = new Label(String.format("%06d", playerScore), defaultLabelStyle);
        totalLife = new Label(String.format("%d", life), defaultLabelStyle);
        totalTime = new Label(String.format("%03d", masterTimer), defaultLabelStyle);

        /* Add labels to table */
        defaultLayout.add(scoreName).expandX().padTop(10);
        defaultLayout.add(lifeName).expandX().padTop(10);
        defaultLayout.add(timeName).expandX().padTop(10);

        /* Add new row and additional elements */
        defaultLayout.row();
        defaultLayout.add(totalScore).expandX();
        defaultLayout.add(totalLife).expandX();
        defaultLayout.add(totalTime).expandX();

        hudStage.addActor(defaultLayout);
    }

    public static void scoreAdder(int score) {
        playerScore += score;
        totalScore.setText(String.format("%06d", playerScore));
    }

    public static void lifeKeeper(int damage){
        life -= damage;
        totalLife.setText(String.format("%d", life));
    }

    public int getLife(){
        return life;
    }

    public void update (float timeChange)
    {
        timeCount += timeChange;
        com.badlogic.gdx.utils.StringBuilder timerTextA = totalTime.getText();
        String timerTextB = "0";
        timerTextB = timerTextA.toString();

        if (timeCount >= 1 && Integer.parseInt(timerTextB) != 0)
        {
            masterTimer--;
            totalTime.setText(String.format("%03d", masterTimer));
            timeCount = 0;
        }
    }

}
