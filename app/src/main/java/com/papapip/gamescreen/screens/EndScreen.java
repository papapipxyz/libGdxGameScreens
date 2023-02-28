package com.papapip.gamescreen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.papapip.gamescreen.BallGame;
import com.papapip.gamescreen.SimpleGameActivity;

public class EndScreen extends ScreenAdapter {

    BallGame game;

    private final String restartTabMessage = "Tap to restart.";

    Rectangle restartTextFrame;

    float restartTextX = 0;

    float restartTextY = 0;

    public EndScreen(BallGame game) {
        this.game = game;
        restartTextX = Gdx.graphics.getWidth() * .25f;
        restartTextY = Gdx.graphics.getHeight() * .25f;
        GlyphLayout restartLayout = new GlyphLayout();
        restartLayout.setText(this.game.font, restartTabMessage);
        restartTextFrame = new Rectangle(restartTextX, Gdx.graphics.getHeight() - (restartTextY - restartLayout.height), restartLayout.width, restartLayout.height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (restartTextFrame.contains(screenX, screenY)) {
                    game.setScreen(new TitleScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "You win!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, restartTabMessage, restartTextX, restartTextY);
        game.batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}