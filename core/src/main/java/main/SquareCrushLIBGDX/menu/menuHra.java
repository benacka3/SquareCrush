package main.SquareCrushLIBGDX.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import main.SquareCrushLIBGDX.Main;
import com.badlogic.gdx.math.Rectangle;
import main.SquareCrushLIBGDX.gameLogic.Display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class menuHra implements Screen {
    private Main game;
    private Display hra;
    private int score;

    private int width = Gdx.graphics.getWidth();
    private int height = Gdx.graphics.getHeight();

    private int widthHra = 1280;
    private int heightHra = 1080;

    private int pocetSquares;
    private int pocetRiadkov;
    private int pocetStlpcov;

    private int velkostStvorca;
    private int medzera = 10;

    private int pociatocnaX;
    private int pociatocnaY;
    private int moves;

    private Rectangle[][] squares;
    private Texture[][] obrazokSquares;

    private Pixmap[][] pixOriginal;
    private Pixmap[][] pixResize;

    private Texture[] scoreTex;
    private Texture scoreNadpis,movesNadpis,iconaMenu,gameOver,restart;

    private BitmapFont scoreFont;
    private Rectangle recIconaMenu , recRestart;
    private int timer = 0;

    private ArrayList<Integer> suradnice;

    private Map<String, Pixmap> pixCacheOriginal;
    private Map<String, Pixmap> pixCacheOznacene;
    private boolean jeZapnuta;

    public menuHra(Main game , Display hra) {

        this.pixCacheOriginal = new HashMap<>();
        this.pixCacheOznacene = new HashMap<>();

        this.pixCacheOriginal.put("blue", new Pixmap(Gdx.files.internal("blue.png")));
        this.pixCacheOriginal.put("red", new Pixmap(Gdx.files.internal("red.png")));
        this.pixCacheOriginal.put("green", new Pixmap(Gdx.files.internal("green.png")));
        this.pixCacheOriginal.put("yellow", new Pixmap(Gdx.files.internal("yellow.png")));
        this.pixCacheOriginal.put("magenta", new Pixmap(Gdx.files.internal("magenta.png")));
        this.pixCacheOriginal.put("white", new Pixmap(Gdx.files.internal("white.png")));

        pixCacheOznacene.put("blue", new Pixmap(Gdx.files.internal("blueOzn.png")));
        pixCacheOznacene.put("red", new Pixmap(Gdx.files.internal("redOzn.png")));
        pixCacheOznacene.put("green", new Pixmap(Gdx.files.internal("greenOzn.png")));
        pixCacheOznacene.put("yellow", new Pixmap(Gdx.files.internal("yellowOzn.png")));
        pixCacheOznacene.put("magenta", new Pixmap(Gdx.files.internal("magentaOzn.png")));

        this.hra = hra;

        this.jeZapnuta = true;

        this.suradnice = new ArrayList<>();

        this.score = this.hra.getScore();
        this.moves = this.hra.getMoves();

        this.gameOver = new Texture(Gdx.files.internal("gameover.png"));
        this.movesNadpis = new Texture(Gdx.files.internal("moves.png"));
        this.scoreNadpis = new Texture(Gdx.files.internal("score.png"));
        this.iconaMenu = new Texture(Gdx.files.internal("menu.png"));
        this.restart = new Texture(Gdx.files.internal("restart.png"));

        this.scoreTex = new Texture[10];
        this.scoreFont = new BitmapFont();
        this.scoreFont.setColor(Color.BLACK);

        this.game = game;

        this.pocetSquares = this.hra.getRozmer();
        this.pocetRiadkov = this.hra.getRozmer();
        this.pocetStlpcov = this.hra.getRozmer();

        this.velkostStvorca = this.heightHra / this.pocetSquares - this.medzera;

        this.pociatocnaX = (this.widthHra - (this.velkostStvorca * this.pocetSquares + this.medzera * (this.pocetSquares - 1))) / 2;
        this.pociatocnaY = (this.heightHra - (this.velkostStvorca * this.pocetSquares + this.medzera * (this.pocetSquares - 1))) / 2;

        this.squares = new Rectangle[this.pocetRiadkov][this.pocetStlpcov];
        this.obrazokSquares = new Texture[this.pocetRiadkov][this.pocetStlpcov];
        this.pixOriginal = new Pixmap[this.pocetRiadkov][this.pocetStlpcov];
        this.pixResize = new Pixmap[this.pocetRiadkov][this.pocetStlpcov];

        this.recIconaMenu = new Rectangle(this.width - (195 + 46), this.height - 440, this.iconaMenu.getWidth(), this.iconaMenu.getHeight());
        this.recRestart = new Rectangle(this.width - (185) , this.height - 440 , this.restart.getWidth() , this.restart.getHeight());

        // Pre vytváranie obrázkov a umiestnenie štvorcov
        for (int i = 0; i < this.pocetRiadkov; i++) {
            for (int j = 0; j < this.pocetStlpcov; j++) {
                // Otočenie oboch indexov (invertujeme `i` a `j`)
                int reversedI = this.pocetRiadkov - 1 - i;  // Pre riadky
                int reversedJ = this.pocetStlpcov - 1 - j;  // Pre stĺpce

                // Výpočet pozícií
                int x = this.pociatocnaX + reversedJ * (this.velkostStvorca + this.medzera);  // Otočenie stĺpcov
                int y = this.pociatocnaY + reversedI * (this.velkostStvorca + this.medzera);  // Otočenie riadkov

                // Vytváranie a kreslenie obrázkov
                this.pixOriginal[reversedI][reversedJ] = new Pixmap(Gdx.files.internal("blue.png"));
                this.pixResize[reversedI][reversedJ] = new Pixmap(this.velkostStvorca, this.velkostStvorca, this.pixOriginal[reversedI][reversedJ].getFormat());
                this.pixResize[reversedI][reversedJ].drawPixmap(this.pixOriginal[reversedI][reversedJ], 0, 0, this.pixOriginal[reversedI][reversedJ].getWidth(), this.pixOriginal[reversedI][reversedJ].getHeight(),
                    0, 0, this.velkostStvorca, this.velkostStvorca);
                this.obrazokSquares[reversedI][reversedJ] = new Texture(this.pixResize[reversedI][reversedJ]);

                this.squares[reversedI][reversedJ] = new Rectangle(x, y, this.velkostStvorca, this.velkostStvorca);
            }
        }


        for (int i = 0 ; i < 10 ; i++) {
            scoreTex[i] = new Texture(Gdx.files.internal(i + "cislo.png"));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        try {
            this.timer++;

            if (this.moves == this.pocetRiadkov * 3) {
                this.jeZapnuta = true;
            }

            int fps = Gdx.graphics.getFramesPerSecond();
            Gdx.graphics.setTitle("SquareCrush FPS: " + fps);

            this.hra.gameLoop();

            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            this.game.getBatch().begin();

            // Vykreslíme rôzne UI elementy
            this.game.getBatch().draw(this.scoreNadpis, this.width - 390, this.height - 90);
            this.game.getBatch().draw(this.iconaMenu, this.width - (195 + 46), this.height - 440);
            this.game.getBatch().draw(this.movesNadpis, this.width - 390, this.height - 290);
            this.game.getBatch().draw(this.restart , this.width - (185), this.height - 440);

            // Vykreslíme štvorce na obrazovke
            for (int i = 0; i < this.pocetRiadkov; i++) {
                for (int j = 0; j < this.pocetStlpcov; j++) {
                    int reversedI = this.pocetRiadkov - 1 - i;
                    int reversedJ = this.pocetStlpcov - 1 - j;

                    int x = this.pociatocnaX + reversedJ * (this.velkostStvorca + this.medzera);
                    int y = this.pociatocnaY + reversedI * (this.velkostStvorca + this.medzera);

                    this.game.getBatch().draw(this.obrazokSquares[reversedI][reversedJ], x, y);
                }
            }

            this.aktualizujTextury();

            this.score = this.hra.getScore();
            this.moves = this.hra.getMoves();

            this.vykresliScore();
            this.vykresliMoves();

            // Zistí, či bol obrazovke dotyk (kliknutie)
            if (Gdx.input.justTouched()) {
                float touchedX = Gdx.input.getX();
                float touchedY = Gdx.graphics.getHeight() - Gdx.input.getY();

                // Prechod cez kliknutú oblasť menu
                if (this.recIconaMenu.contains(touchedX, touchedY)) {
                    this.game.getBatch().end();
                    this.game.setScreen(new menu(this.game));
                    return;
                }

                if (this.recRestart.contains(touchedX, touchedY)) {
                    this.gameOver.dispose();
                    this.hra.restart();
                    this.hra.getMoves();
                    this.hra.getScore();
                    this.jeZapnuta = true;
                    this.vykresliScore();
                    this.vykresliMoves();
                }

                // Ak už boli vybrané 2 štvorce, zresetujeme zoznam a stav kliknutia
                if (this.suradnice.size() >= 4) {
                    this.suradnice.clear(); // Reset súradníc pre nový výber
                }

                if (this.jeZapnuta) {
                    for (int i = 0; i < this.pocetRiadkov; i++) {
                        for (int j = 0; j < this.pocetStlpcov; j++) {
                            if (this.squares[i][j].contains(touchedX, touchedY)) {
                                this.handleClickStvorec(i, j);
                                this.vykresliScore();
                            }
                        }
                    }
                }
            }

            // Skontrolujeme, či skončil čas na ťahy
            if (this.moves == 0) {
                this.jeZapnuta = false;

                this.game.getBatch().draw(this.gameOver, (float) this.widthHra / 2 - (float) this.gameOver.getWidth() / 2, (float) this.heightHra / 2 - (float) this.gameOver.getHeight() / 2);

                this.vykresliScore();
                this.vykresliMoves();
            }

        } catch (Exception e) {
            Gdx.app.log("ERROR", "Hra padla pri renderi", e);
        } finally {
            if (this.game.getBatch().isDrawing()) {
                this.game.getBatch().end();  // Uistíme sa, že end() sa zavolá vždy
            }
        }
    }

    private void handleClickStvorec(int i, int j) {
        Gdx.app.postRunnable(() -> {
            System.out.println("Kliknutie na štvorec [" + i + ", " + j + "].");
            this.suradnice.add(i);
            this.suradnice.add(j);
            this.hra.setOznacenie(i, j);

            if (this.suradnice.size() == 4) {
                boolean vymenaPrebehla = this.hra.vymenStvorec(
                    this.suradnice.get(0),
                    this.suradnice.get(1),
                    this.suradnice.get(2),
                    this.suradnice.get(3)
                );

                if (vymenaPrebehla) {
                    this.score = this.hra.getScore();
                    this.moves = this.hra.getMoves();
                }

                this.hra.zrusOznacenie();
                this.suradnice.clear();
            }

            this.aktualizujTextury();
        });
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        this.jeZapnuta = true;
        this.hra = null;
        System.out.println(this.hra);
        this.hra = new Display(this.pocetRiadkov);
        System.out.println(this.hra);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        for (int i = 0; i < pocetRiadkov; i++) {
            for (int j = 0; j < pocetStlpcov; j++) {
                if (obrazokSquares[i][j] != null) obrazokSquares[i][j].dispose();
                if (pixOriginal[i][j] != null) pixOriginal[i][j].dispose();
                if (pixResize[i][j] != null) pixResize[i][j].dispose();
            }
        }

        // Vyčistenie všetkých cache Pixmap
        pixCacheOriginal.values().forEach(Pixmap::dispose);
        pixCacheOriginal.clear();

        pixCacheOznacene.values().forEach(Pixmap::dispose);
        pixCacheOznacene.clear();

        // Uvoľnenie fontov a textúr
        this.scoreFont.dispose();
        for (Texture texture : scoreTex) {
            if (texture != null) texture.dispose();
        }
        if (movesNadpis != null) movesNadpis.dispose();
        if (scoreNadpis != null) scoreNadpis.dispose();
        if (iconaMenu != null) iconaMenu.dispose();

        this.gameOver.dispose();
        // Explicitne uzavrie hru
        Gdx.app.exit();
    }

    private void aktualizujTextury() {
        for (int i = 0; i < this.pocetRiadkov; i++) {
            for (int j = 0; j < this.pocetStlpcov; j++) {
                String farba = this.hra.getStvorecFarba(i, j);
                boolean oznacene = this.hra.getStvorec(i, j).getOznacenie();

                Pixmap pixmap = ziskajPixmap(farba, oznacene);

                if (pixmap != null) {
                    if (this.pixResize[i][j] != null)  {
                        this.pixResize[i][j].dispose();
                        this.obrazokSquares[i][j].dispose();// Uvoľni staré
                    }
                    this.pixResize[i][j] = new Pixmap(this.velkostStvorca, this.velkostStvorca, pixmap.getFormat());
                    this.pixResize[i][j].drawPixmap(pixmap, 0, 0, pixmap.getWidth(), pixmap.getHeight(),
                        0, 0, this.velkostStvorca, this.velkostStvorca);
                    this.obrazokSquares[i][j] = new Texture(this.pixResize[i][j]);
                }
            }
        }
    }

    private Pixmap ziskajPixmap(String farba, boolean oznacene) {
        if (oznacene) {
            return pixCacheOznacene.get(farba);
        }
        return pixCacheOriginal.get(farba);
    }

    public void vykresliScore() {
        // Vykreslenie skóre
        String scoreCislo = String.format("%06d", this.score);

        int posX = this.width - 390;
        int posY = this.height - 190;

        // Vykreslíme číslice skóre
        for (int i = 0; i < scoreCislo.length(); i++) {
            char digit = scoreCislo.charAt(i);
            int digitValue = Character.getNumericValue(digit);
            this.game.getBatch().draw(scoreTex[digitValue], posX, posY);
            posX += 65;
        }
    }

    public void vykresliMoves() {
        // Vykreslenie skóre
        String movesCislo = String.format("%06d", this.moves);

        int posX = this.width - 390;
        int posY = this.height - 190;

        posX = this.width - 390;
        posY = this.height - 390;

        // Vykreslíme číslice počtu ťahov (moves)
        for (int i = 0; i < movesCislo.length(); i++) {
            char digit = movesCislo.charAt(i);
            int digitValue = Character.getNumericValue(digit);
            this.game.getBatch().draw(scoreTex[digitValue], posX, posY);
            posX += 65;
        }
    }
}
