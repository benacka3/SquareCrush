package main.SquareCrushLIBGDX.gameLogic.stvorce;

import main.SquareCrushLIBGDX.gameLogic.Display;

/**
 * Trieda StvorecDispleja je abstraktná metóda, ktorá nám umožňuje implementovať do hry viac druhov štvorcov aby sme
 * vedeli každému štvorcu priradiť nejaké unikátne vlastnosti vymazávania
 *
 * @author Adam Beňačka
 * @version 0.1
 */
public abstract class StvorecDispleja {
    private String farba;
    private int x;
    private int y;
    private Boolean oznaceny;
    private Boolean vymazat;

    /**
     * Konštruktor triedy StvorecDispleja inicializuje počiatočné hodnoty
     * @param farba Farba štvorca
     * @param x Poloha v poly
     * @param y Poloha v poly
     */
    public StvorecDispleja(String farba , int x , int y) {
        this.farba = farba;
        this.vymazat = false;
        this.oznaceny = false;
        this.x = x;
        this.y = y;
    }

    /**
     * Setter, ktorý nám nastaví farbu
     * @param farba Farba štvorca na akú chceme zmeniť
     */
    public void setFarba(String farba) {
        this.farba = farba;
    }

    /**
     * Getter, ktorý nám vracia farbu štvorca
     * @return Vracia farbu štvorca
     */
    public String getFarba() {
        return this.farba;
    }

    /**
     * Getter, ktorý nám vracia plohu X štvorca
     * @return Vracia plohu X štvorca
     */
    public int getX() {
        return x;
    }

    /**
     * Getter, ktorý nám vracia plohu Y štvorca
     * @return Vracia plohu Y štvorca
     */
    public int getY() {
        return y;
    }

    /**
     * Setter, ktorý nám nastaví polohu X štvorca
     * @param x Hodnota v poly, ktorú chceme nastaviť
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter, ktorý nám nastaví polohu X štvorca
     * @param y Hodnota v poly, ktorú chceme nastaviť
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Abstraktná metóda, ktorú dedia a prepisujú potomkovia triedy StvorecDispleja aby sme dokázali
     * každému potomkovi priradiť unikátnu metódu na vymazávanie
     * @param display Display s ktorým pracujeme
     * @param x Poloha štvorca
     * @param y Poloha štvorca
     */
    public abstract void vymaz(Display display , int x , int y);

    public boolean getOznacenie() {
        return oznaceny;
    }

    public void setOznacene() {
        this.oznaceny = true;
    }

    public void zrusOznacenie() {
        this.oznaceny = false;
    }

    public void setVymaz(Boolean vymazat) {
        this.vymazat = vymazat;
    }
}
