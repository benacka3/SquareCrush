package main.SquareCrushLIBGDX.gameLogic.stvorce;

import main.SquareCrushLIBGDX.gameLogic.controlery.Display;
import main.SquareCrushLIBGDX.gameLogic.controlery.VyberFarby;

/**
 * Trieda Riadkovy je potomkom triedy StvorecDispleja, ktorá má v sebe metódu vymaz a tým dochádza k polymorfizmu.
 * Pokiaľ sa jedná o inštanciu triedy Riadkovy dochádza k vymazávaniu jedného riadku z poľa
 *
 * @author Adam Beňačka
 * @version 0.1
 */
public class Riadkovy extends StvorecDispleja {
    /**
     * Koštruktor triedy Riadkovy
     * @param farba Má priradenú svoju špeciálnu farbu aby sme ich vedeli odlíšiť
     * @param x Poloha v poly this.display, na ktorom sa štvorec nachádza
     * @param y Poloha v poly this.display, na ktorom sa štvorec nachádza
     */
    public Riadkovy(String farba, int x, int y) {
        super(farba, x, y);
    }

    /**
     * Metóda vymaz slúži na vymazávanie štvorcov z poľa this.display v triede Display, vymaže jeden riadok
     * @param display Display s ktorým pracujeme
     * @param x Poloha v poly this.display, na ktorom sa štvorec nachádza
     * @param y Poloha v poly this.display, na ktorom sa štvorec nachádza
     */
    @Override
    public void vymaz(Display display, int x , int y) {
        int pocitadlo = 0;
        for (int i = 0; i < display.getRiadky(); i++) {
            pocitadlo++;
            display.setStvorec(new Zakladny(VyberFarby.WHITE.getFarba(), x, i), x, i);
        }
        display.setScore(pocitadlo * 100);
    }
}
