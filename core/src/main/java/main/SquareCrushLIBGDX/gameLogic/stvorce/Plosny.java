package main.SquareCrushLIBGDX.gameLogic.stvorce;

import main.SquareCrushLIBGDX.gameLogic.controlery.Display;
import main.SquareCrushLIBGDX.gameLogic.controlery.VyberFarby;

/**
 * Trieda Plosny je potomkom triedy StvorecDispleja, ktorá má v sebe metódu vymaz a tým dochádza k polymorfizmu.
 * Pokiaľ sa jedná o inštanciu triedy Plosny dochádza k vymazávaniu z celého poľa rovnakej ale náhodnej farby
 *
 * @author Adam Beňačka
 * @version 0.1
 */
public class Plosny extends StvorecDispleja {
    /**
     * Koštruktor triedy Plosny
     * @param farba Má priradenú svoju špeciálnu farbu aby sme ich vedeli odlíšiť
     * @param x Poloha v poly this.display, na ktorom sa štvorec nachádza
     * @param y Poloha v poly this.display, na ktorom sa štvorec nachádza
     */
    public Plosny(String farba, int x, int y) {
        super(farba, x, y);
    }

    /**
     * Metóda vymaz slúži na vymazávanie štvorcov z poľa this.display v triede Display, náhodne vymaže jednu farbu z poľa
     * @param display Display s ktorým pracujeme
     * @param x Poloha v poly this.display, na ktorom sa štvorec nachádza
     * @param y Poloha v poly this.display, na ktorom sa štvorec nachádza
     */
    @Override
    public void vymaz(Display display, int x , int y) {
        int pocitadlo = 0;
        for (int i = 0; i < display.getRiadky(); i++) {
            for (int j = 0; j < display.getRiadky(); j++) {
                if (display.getStvorec(i, j).getFarba().equals(VyberFarby.nahodnaFarba())) {
                    display.setStvorec(new Zakladny(VyberFarby.WHITE.getFarba(), i, j), i, j);
                    pocitadlo++;
                }
            }
        }
        display.setStvorec(new Zakladny(VyberFarby.WHITE.getFarba(), x , y) , x , y);
        pocitadlo++;
        display.setScore(pocitadlo * 100);
    }
}
