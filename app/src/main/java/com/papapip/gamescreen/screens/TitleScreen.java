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

public class TitleScreen extends ScreenAdapter {

    BallGame game;

    private final String playTapMessage = "Tap to play";

    Rectangle playTextFrame;

    float playTextX = 0;

    float playTextY = 0;

    public TitleScreen(BallGame game) {
        this.game = game;

        playTextX = Gdx.graphics.getWidth() * .25f;
        playTextY = Gdx.graphics.getHeight() * .25f;
        GlyphLayout playFrameLayout = new GlyphLayout();
        playFrameLayout.setText(this.game.font, playTapMessage);
        playTextFrame = new Rectangle(playTextX, Gdx.graphics.getHeight() - (playTextY - playFrameLayout.height), playFrameLayout.width, playFrameLayout.height);
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (playTextFrame.contains(screenX, screenY)) {
                    game.setScreen(new GameScreen(game));
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
        game.font.draw(game.batch, "Title Screen!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Click the circle to win.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
        game.font.draw(game.batch, playTapMessage, playTextX, playTextY);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

}
