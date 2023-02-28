package com.papapip.gamescreen;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SimpleGameActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.r = cfg.g = cfg.b = cfg.a - 8;
        setContentView(initializeForView(new SimpleGame()));
    }


    static class SimpleGame extends ApplicationAdapter {
        enum Screen {
            TITLE, MAIN_GAME, GAME_OVER;
        }

        Screen currentScreen = Screen.TITLE;

        SpriteBatch batch;
        ShapeRenderer shapeRenderer;
        BitmapFont font;

        float circleX = 300;
        float circleY = 150;
        float circleRadius = 50;

        float xSpeed = 4;
        float ySpeed = 3;

        private final String playTapMessage = "Tap to play";
        Rectangle playTextFrame;

        float playTextX = 0;

        float playTextY = 0;

        private final String restartTabMessage = "Tap to restart.";

        Rectangle restartTextFrame;

        float restartTextX = 0;

        float restartTextY = 0;

        @Override
        public void create() {
            batch = new SpriteBatch();
            shapeRenderer = new ShapeRenderer();

            font = new BitmapFont();
            font.getData().setScale(5);

            playTextX = Gdx.graphics.getWidth() * .25f;
            playTextY = Gdx.graphics.getHeight() * .25f;
            GlyphLayout playFrameLayout = new GlyphLayout();
            playFrameLayout.setText(font, playTapMessage);
            playTextFrame = new Rectangle(playTextX, Gdx.graphics.getHeight() - (playTextY - playFrameLayout.height), playFrameLayout.width, playFrameLayout.height);

            restartTextX = Gdx.graphics.getWidth() * .25f;
            restartTextY = Gdx.graphics.getHeight() * .25f;
            GlyphLayout restartLayout = new GlyphLayout();
            restartLayout.setText(font, restartTabMessage);
            restartTextFrame = new Rectangle(restartTextX, Gdx.graphics.getHeight() - (restartTextY - restartLayout.height), restartLayout.width, restartLayout.height);

            Gdx.input.setInputProcessor(new InputAdapter() {

                @Override
                public boolean touchDown(int x, int y, int pointer, int button) {
                    if (currentScreen == Screen.MAIN_GAME) {
                        int renderY = Gdx.graphics.getHeight() - y;
                        if (Vector2.dst(circleX, circleY, x, renderY) < circleRadius) {
                            currentScreen = Screen.GAME_OVER;
                        }
                    } else if (currentScreen == Screen.TITLE) {
                        if (playTextFrame.contains(x, y)) {
                            currentScreen = Screen.MAIN_GAME;
                        }
                    } else if (currentScreen == Screen.GAME_OVER) {
                        if (restartTextFrame.contains(x, y)) {
                            currentScreen = Screen.TITLE;
                        }
                    }
                    return true;
                }
            });
        }

        @Override
        public void render() {

            if (currentScreen == Screen.TITLE) {
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.begin();

                font.draw(batch, "Title Screen!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
                font.draw(batch, "Tap the circle to win.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
                font.draw(batch, playTapMessage, playTextX, playTextY);

                batch.end();
            } else if (currentScreen == Screen.MAIN_GAME) {
                circleX += xSpeed;
                circleY += ySpeed;

                if (circleX < 0 || circleX > Gdx.graphics.getWidth()) {
                    xSpeed *= -1;
                }

                if (circleY < 0 || circleY > Gdx.graphics.getHeight()) {
                    ySpeed *= -1;
                }

                Gdx.gl.glClearColor(0, 0, .25f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.circle(circleX, circleY, 75);
                shapeRenderer.end();
            } else if (currentScreen == Screen.GAME_OVER) {
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                batch.begin();
                font.draw(batch, "You win!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
                font.draw(batch, restartTabMessage, restartTextX, restartTextY);
                batch.end();
            }

        }

        @Override
        public void dispose() {
            shapeRenderer.dispose();
        }
    }
}