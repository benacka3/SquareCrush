package main.SquareCrushLIBGDX.gameLogic.stvorce;

import main.SquareCrushLIBGDX.gameLogic.Display;

/**
 * Trieda Zakladny je potomkom triedy StvorecDispleja, ktorá má v sebe metódu vymaz a tým dochádza k polymorfizmu.
 * Pokiaľ sa jedná o inštanciu triedy Zakladný dochádza k vymazávaniu jedného štvorca
 *
 * @author Adam Beňačka
 * @version 0.1
 */
public class Zakladny extends StvorecDispleja {
    /**
     * Koštruktor triedy Zakladny
     * @param farba Má priradenú svoju špeciálnu farbu aby sme ich vedeli odlíšiť
     * @param x Poloha v poly this.display, na ktorom sa štvorec nachádza
     * @param y Poloha v poly this.display, na ktorom sa štvorec nachádza
     */
    public Zakladny(String farba, int x, int y) {
        super(farba, x, y);
    }
    /**
     * Metóda vymaz slúži na vymazávanie štvorcov z poľa this.display v triede Display, vymaže jeden štvorec
     * @param display Display s ktorým pracujeme
     * @param x Poloha v poly this.display, na ktorom sa štvorec nachádza
     * @param y Poloha v poly this.display, na ktorom sa štvorec nachádza
     */
    @Override
    public void vymaz(Display display , int x , int y) {
        display.getStvorec(x,y).setFarba("white");
    }
}
