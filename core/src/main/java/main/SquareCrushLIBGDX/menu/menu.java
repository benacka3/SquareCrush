package main.SquareCrushLIBGDX.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import main.SquareCrushLIBGDX.Main;
import com.badlogic.gdx.math.Rectangle;
import main.SquareCrushLIBGDX.gameLogic.Display;

public class menu implements Screen {

    Rectangle rectMala,rectStredna,rectVelka,rectUkoncit;

    Pixmap pozadie;
    Pixmap pozadieResize;
    Texture pozadieText;

    Texture tlacitkoMala;
    Texture tlacitkoStredna;
    Texture tlacitkoVelka;
    Texture logo;
    Texture ukoncit;
    float xUkoncit;
    float yUkoncit;
    float xMala;
    float yMala;

    float xStredna;
    float yStredna;

    float xVelka;
    float yVelka;

    float xPozadie;
    float yPozadie;

    float xLogo;
    float yLogo;

    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    Main game;

    boolean viditelne;

    public menu(Main game) {
        this.game = game;

        this.viditelne = true;

        this.xPozadie = 0;
        this.yPozadie = 0;

        this.xLogo = (float) this.width / 16 * 4;
        this.yLogo = (float) this.height / 9 * 6;

        this.xMala = (float) this.width / 16 * 5f;
        this.yMala = (float) this.height / 9 * 1.5f;

        this.xStredna = (float) this.width / 16 * 5f;
        this.yStredna = (float) this.height / 9 * 3;

        this.xVelka = (float) this.width / 16 * 5f;
        this.yVelka = (float) this.height / 9 * 4.5f;

        this.xUkoncit = this.width / 16 * 5f;
        this.yUkoncit = this.height / 9 * 0f;

        this.pozadie = new Pixmap(Gdx.files.internal("pozadie.png"));
        this.pozadieResize = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), pozadie.getFormat());
        this.pozadieResize.drawPixmap(this.pozadie, 0, 0, this.pozadie.getWidth(), this.pozadie.getHeight(), 0, 0, this.pozadieResize.getWidth(), this.pozadieResize.getHeight());
        this.pozadieText = new Texture(pozadieResize);

        this.ukoncit = new Texture("ukoncit.png");
        this.tlacitkoMala = new Texture("mala.png");
        this.tlacitkoStredna = new Texture("stredna.png");
        this.tlacitkoVelka = new Texture("velka.png");
        this.logo = new Texture("logo.png");

        this.rectUkoncit = new Rectangle(this.xUkoncit,this.yUkoncit,this.ukoncit.getWidth() , this.ukoncit.getHeight());
        this.rectMala = new Rectangle(this.xMala, this.yMala, this.tlacitkoMala.getWidth(), this.tlacitkoMala.getHeight());
        this.rectStredna = new Rectangle(this.xStredna, this.yStredna, this.tlacitkoStredna.getWidth(), this.tlacitkoStredna.getHeight());
        this.rectVelka = new Rectangle(this.xVelka, this.yVelka, this.tlacitkoVelka.getWidth(), this.tlacitkoVelka.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        this.game.getBatch().begin();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (this.viditelne) {
            this.game.getBatch().draw(pozadieText, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            this.game.getBatch().draw(logo, xLogo, yLogo);
            this.game.getBatch().draw(tlacitkoMala, xMala, yMala);
            this.game.getBatch().draw(tlacitkoStredna, xStredna, yStredna);
            this.game.getBatch().draw(tlacitkoVelka, xVelka, yVelka);
            this.game.getBatch().draw(ukoncit,xUkoncit,yUkoncit);

            if (Gdx.input.justTouched()) {
                float touchX = Gdx.input.getX();
                float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (this.rectMala.contains(touchX, touchY)) {
                    this.game.setScreen(new menuHra(this.game , new Display(10)));
                    this.hide();
                }

                if (this.rectStredna.contains(touchX, touchY)) {
                    this.game.setScreen(new menuHra(this.game , new Display(15)));
                    this.hide();
                }

                if (this.rectVelka.contains(touchX, touchY)) {
                    this.game.setScreen(new menuHra(this.game , new Display(20)));
                    this.hide();
                }

                if (this.rectUkoncit.contains(touchX , touchY)) {
                    System.exit(0);
                }
            }
        }
        this.game.getBatch().end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (pozadieText != null) pozadieText.dispose();
        if (tlacitkoMala != null) tlacitkoMala.dispose();
        if (tlacitkoStredna != null) tlacitkoStredna.dispose();
        if (tlacitkoVelka != null) tlacitkoVelka.dispose();
        if (logo != null) logo.dispose();
    }

    public void restart(int velkost) {
        this.game.setScreen(new menuHra(this.game , new Display(velkost)));
    }
}
