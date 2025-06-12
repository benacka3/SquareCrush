package main.SquareCrushLIBGDX;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.SquareCrushLIBGDX.menu.menu;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.setScreen(new menu(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }

    public SpriteBatch getBatch() {
        return this.batch;
    }
}
