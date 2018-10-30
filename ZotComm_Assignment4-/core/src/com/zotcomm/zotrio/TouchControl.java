package com.zotcomm.zotrio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TouchControl {

    Viewport controlView;
    Stage controlStage, ABStage;

    boolean upActive, downActive, leftActive, rightActive, aPressed, bPressed;
    OrthographicCamera controlCam;

    public boolean isUpActive() {
        return upActive;
    }

    public boolean isDownActive() {
        return downActive;
    }

    public boolean isLeftActive() {
        return leftActive;
    }

    public boolean isRightActive() {
        return rightActive;
    }

    public boolean isaPressed() {
        return aPressed;
    }

    public boolean isbPressed() {
        return bPressed;
    }

    public void resize (int width, int height)
    {
        controlView.update(width, height);
    }

    public TouchControl (SpriteBatch sb)
    {

        controlCam = new OrthographicCamera();
        controlView = new FitViewport(Zotrio.V_WIDTH * 2, Zotrio.V_HEIGHT * 2, controlCam);
        controlStage = new Stage(controlView, sb);
        ABStage = new Stage(controlView, sb);
        Gdx.input.setInputProcessor(controlStage);

        Table padTable = new Table();
        Table ABTable = new Table();
        ABTable.left().bottom();
        padTable.left().bottom();

        Image upButton = new Image(new Texture("Up.png"));
        Image downButton = new Image(new Texture("Down.png"));
        Image leftButton = new Image(new Texture("Left.png"));
        Image rightButton = new Image(new Texture("Right.png"));
        Image centerFill = new Image(new Texture("Center.png"));
        Image A = new Image(new Texture("A.png"));
        Image B = new Image(new Texture("B.png"));

        A.setSize(50,50);
        B.setSize(50,50);
        upButton.setSize(50,50);
        downButton.setSize(50,50);
        leftButton.setSize(50,50);
        rightButton.setSize(50,50);
        centerFill.setSize(50,50);

        A.addListener(new InputListener()
        {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                aPressed = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                aPressed = true;
                return true;
            }
        });

        B.addListener(new InputListener()
        {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                bPressed = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                bPressed = true;
                return true;
            }
        });

        upButton.addListener(new InputListener()
        {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                upActive = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                upActive = true;
                return true;
            }
        });

        downButton.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                downActive = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                downActive = false;
            }


        });

        leftButton.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                leftActive = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                leftActive = false;
            }


        });

        rightButton.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                rightActive = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                rightActive = false;
            }


        });


        padTable.add();
        padTable.add(upButton).size(upButton.getWidth(), upButton.getHeight());
        padTable.add();
        padTable.row().pad(0, 0, 0, 0);
        padTable.add(leftButton).size(leftButton.getWidth(), leftButton.getHeight());
        padTable.add(centerFill).size(centerFill.getWidth(), centerFill.getHeight());
        padTable.add(rightButton).size(rightButton.getWidth(), rightButton.getHeight());
        padTable.row().padBottom(5);
        padTable.add();
        padTable.add(downButton).size(downButton.getWidth(), downButton.getHeight());
        padTable.add();

        ABTable.add().padLeft(680);
        ABTable.add(A).size(A.getWidth(), A.getHeight());
        ABTable.add();
        ABTable.row().pad(0, 0, 0, 0);
        ABTable.add();
        ABTable.add();
        ABTable.add(B).size(B.getWidth(), B.getHeight());
        ABTable.row().padBottom(5);
        ABTable.add();
        ABTable.add();
        ABTable.add();

        controlStage.addActor(padTable);
        controlStage.addActor(ABTable);



    }

    public void draw(){

        controlStage.draw();


    }
}