package main.SquareCrushLIBGDX.gameLogic.stvorce;

import main.SquareCrushLIBGDX.gameLogic.Display;
import main.SquareCrushLIBGDX.gameLogic.VyberFarby;

/**
 * Trieda Stlpcovy je potomkom triedy StvorecDispleja, ktorá má v sebe metódu vymaz a tým dochádza k polymorfizmu.
 * Pokiaľ sa jedná o inštanciu triedy Stlpcovy dochádza k vymazávaniu jedného stĺpcu z poľa
 *
 * @author Adam Beňačka
 * @version 0.1
 */
public class Stlpcovy extends StvorecDispleja {
    /**
     * Koštruktor triedy Stlpcovy
     * @param farba Má priradenú svoju špeciálnu farbu aby sme ich vedeli odlíšiť
     * @param x Poloha v poly this.display, na ktorom sa štvorec nachádza
     * @param y Poloha v poly this.display, na ktorom sa štvorec nachádza
     */
    public Stlpcovy(String farba, int x, int y) {
        super(farba, x, y);
    }

    /**
     * Metóda vymaz slúži na vymazávanie štvorcov z poľa this.display v triede Display, vymaže jeden stĺpec
     * @param display Display s ktorým pracujeme
     * @param x Poloha v poly this.display, na ktorom sa štvorec nachádza
     * @param y Poloha v poly this.display, na ktorom sa štvorec nachádza
     */
    @Override
    public void vymaz(Display display, int x , int y) {
        int pocitadlo = 0;
        for (int i = 0; i < display.getRiadky(); i++) {
            pocitadlo++;
            display.setStvorec(new Zakladny(VyberFarby.WHITE.getFarba(), i, y), i, y);
        }
        display.setScore(pocitadlo * 100);
    }
}
