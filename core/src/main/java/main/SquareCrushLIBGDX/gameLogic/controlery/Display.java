package main.SquareCrushLIBGDX.gameLogic.controlery;

import com.badlogic.gdx.Gdx;
import main.SquareCrushLIBGDX.gameLogic.stvorce.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Trieda Display nám spravuje logické rozhranie hry, v tejto triede
 * spravujeme pozeranie či nastala zhoda farieb v poly s štvorcami a spravujeme
 * ich výmeny
 *
 * @author Adam Beňačka
 * @version 0.1
 */
public class Display {
    private StvorecDispleja[][] display;
    private StvorecDispleja[][] ulozenaHra;
    private StvorecDispleja[][] pozriFarbu;
    private ArrayList<Integer> ulozenaPoloha;
    private int pocitadlo;
    private int tahy;
    private int riadky;
    private int tempScore;
    private static final int MAX_POKUSOV = 10;
    private final AtomicInteger score = new AtomicInteger(0);
    private boolean jeZapnuta;
    private Consumer<String> zobrazovac = s -> {};
    private AtomicGridFlat grid;
    private GridPoint gridPoint;

    /**
     * Konštruktor triedy Display nám inicializuje počiatočné hodnoty pre vytvorenie dvojrozmerného poľa,
     * v ktorom sú uložené inštancie štvorcov
     *
     * @param riadky Veľkosť hry
     */
    public Display(int riadky) {
        this.tahy = riadky * 3;
        this.pocitadlo = 0;
        this.riadky = riadky;
        this.score.set(0);
        this.display = new StvorecDispleja[this.riadky][this.riadky];
        this.pozriFarbu = new StvorecDispleja[this.riadky][this.riadky];
        this.ulozenaPoloha = new ArrayList<>();
        this.jeZapnuta = true;
        this.grid = new AtomicGridFlat(this.riadky , this.riadky);

        for (int i = 0; i < this.riadky; i++) {
            for (int j = 0; j < this.riadky; j++) {
                String novaFarba = null;
                int pokusy = 0;

                do {
                    novaFarba = VyberFarby.nahodnaFarba();
                    pokusy++;
                } while (pokusy < MAX_POKUSOV && (
                    (i > 0 && novaFarba.equals(display[i - 1][j].getFarba())) || // Skontroluj hore
                        (j > 0 && novaFarba.equals(display[i][j - 1].getFarba()))    // Skontroluj vľavo
                ));
                display[i][j] = new Zakladny(novaFarba, i, j);
            }
        }
    }

    // Pridaj setter alebo do konštruktora
    public void setZobrazovac(Consumer<String> zobrazovac) {
        this.zobrazovac = zobrazovac;
    }

    public void gameLoop() {
        try {
            if (this.jeZapnuta) {
                this.pozriRiadky(this.display , true , null);
                this.pozriStlpce(this.display , true , null);
                this.vykresli();
                this.posunDole(this.display);
                this.paralelnaHeuristikaRiadky();
                this.paralelnaHeuristikaStlpce();

                if (this.tahy == (this.riadky * 3)) {
                    this.score.set(0);
                    this.jeZapnuta = true;
                }
                if (this.tahy == 0) {
                    this.jeZapnuta = false;
                }
            }
        } catch (Exception e) {
            Gdx.app.log("ERROR", "Hra padla!", e);
        }
    }

    /**
     * Metóda výmena na slúži na výmenu štvorcov pri ich obnove (vymazaní)
     *
     * @param riadok1 Poloha v poly prvého štvorca
     * @param stlpec1 Poloha v poly prvého štvorca
     * @param riadok2 Poloha v poly druhého štvorca
     * @param stlpec2 Poloha v poly druhého štvorca
     */
    public void vymena(int riadok1, int stlpec1, int riadok2, int stlpec2) {
        StvorecDispleja s1 = this.display[riadok1][stlpec1];
        StvorecDispleja s2 = this.display[riadok2][stlpec2];

        this.display[riadok1][stlpec1] = s2;
        this.display[riadok2][stlpec2] = s1;
    }

    /**
     * Metóda nám slúži na kopírovanie údajov z hlavného poľa do pomocného
     * aby sme si potom vedeli určiť či sa daná výmena štvorcov môže uskutočniť
     */
    public void pozerac() {
        for (int i = 0; i < this.riadky; i++) {
            for (int j = 0; j < this.riadky; j++) {
                this.pozriFarbu[i][j] = this.display[i][j];
            }
        }
    }

    /**
     * Metóda nám prechádza riadky a ak sa nájde zhoda zvýši sa premenná počítadlo a tým
     * vieme zistiť či sa daná výmena môže uskutočniť aby hráč nemohol vymeniť štvorce hala bala
     */
    public int pozriRiadkyVymena() {
        for (int riadok = 0; riadok < this.riadky; riadok++) {
            for (int stlpec = 0; stlpec < this.riadky - 2; stlpec++) {
                if (!(this.pozriFarbu[riadok][stlpec].getFarba().equals("white"))) {
                    if (this.pozriFarbu[riadok][stlpec].getFarba().equals(this.pozriFarbu[riadok][stlpec + 1].getFarba()) && this.pozriFarbu[riadok][stlpec].getFarba().equals(this.pozriFarbu[riadok][stlpec + 2].getFarba())) {
                        if (stlpec + 3 < this.riadky && this.pozriFarbu[riadok][stlpec].getFarba().equals(this.pozriFarbu[riadok][stlpec + 3].getFarba())) {
                            if (stlpec + 4 < this.riadky && this.pozriFarbu[riadok][stlpec].getFarba().equals(this.pozriFarbu[riadok][stlpec + 4].getFarba())) {
                                this.pozriStlpceVymena();
                                this.pocitadlo++;
                            }
                            this.pozriStlpceVymena();
                            this.pocitadlo++;
                        }
                        this.pozriStlpceVymena();
                        this.pocitadlo++;
                    }
                }
            }
        }
        return this.pocitadlo;
    }

    /**
     * Metóda nám prechádza stĺpce a ak sa nájde zhoda zvýši sa premenná počítadlo a tým
     * vieme zistiť či sa daná výmena môže uskutočniť aby hráč nemohol vymeniť štvorce hala bala
     */
    public int pozriStlpceVymena() {
        for (int riadok = 0; riadok < this.riadky; riadok++) {
            for (int stlpec = 0; stlpec < this.riadky - 2; stlpec++) {
                if (!(this.pozriFarbu[stlpec][riadok].getFarba().equals("white"))) {
                    if (this.pozriFarbu[stlpec][riadok].getFarba().equals(this.pozriFarbu[stlpec + 1][riadok].getFarba()) && this.pozriFarbu[stlpec][riadok].getFarba().equals(this.pozriFarbu[stlpec + 2][riadok].getFarba())) {
                        if (stlpec + 3 < this.riadky && this.pozriFarbu[stlpec][riadok].getFarba().equals(this.pozriFarbu[stlpec + 3][riadok].getFarba())) {
                            if (stlpec + 4 < this.riadky && this.pozriFarbu[stlpec][riadok].getFarba().equals(this.pozriFarbu[stlpec + 4][riadok].getFarba())) {
                                this.pocitadlo++;
                            }
                            this.pocitadlo++;
                        }
                        this.pocitadlo++;
                    }
                }
            }
        }
        return this.pocitadlo;
    }

    /**
     * Táto metóda vyhodnocuje či je možné spraviť výmenu
     *
     * @param riadok1 Poloha v poly prvého štvorca
     * @param stlpec1 Poloha v poly prvého štvorca
     * @param riadok2 Poloha v poly druhého štvorca
     * @param stlpec2 Poloha v poly druhého štvorca
     * @return Vracia hodnotu True alebo False na základe zistenej zhody
     */
    public boolean vymenStvorec(int riadok1, int stlpec1, int riadok2, int stlpec2) {
        if (riadok1 < 0 || riadok1 >= this.pozriFarbu.length || stlpec1 < 0 || stlpec1 >= this.pozriFarbu[riadok1].length ||
            riadok2 < 0 || riadok2 >= this.pozriFarbu.length || stlpec2 < 0 || stlpec2 >= this.pozriFarbu[riadok2].length) {
            System.out.println("Neplatný index do poľa.");
            return false;
        }

        // Pridáme kontrolu, či sú kliknuté rôzne políčka
        if (riadok1 == riadok2 && stlpec1 == stlpec2) {
            System.out.println("Klikol si na rovnaké políčko. Výmena sa neuskutoční.");//Spraviť pop-out možno?
            return false;
        }

        this.pozerac();
        this.tempScore = score.get();

        // Skontrolujeme, že oba štvorec nie sú biele
        if (!(this.pozriFarbu[riadok1][stlpec1].getFarba().equals("white") && this.pozriFarbu[riadok2][stlpec2].getFarba().equals("white"))) {
            // Overenie, že výmena je susedná (buď vertikálne, alebo horizontálne)
            if ((Math.abs(riadok1 - riadok2) == 1 && stlpec1 == stlpec2) || (Math.abs(stlpec1 - stlpec2) == 1 && riadok1 == riadok2)) {

                // Výmena iba atribútov (farieb)
                String tempFarba = this.pozriFarbu[riadok1][stlpec1].getFarba();
                this.pozriFarbu[riadok1][stlpec1].setFarba(this.pozriFarbu[riadok2][stlpec2].getFarba());
                this.pozriFarbu[riadok2][stlpec2].setFarba(tempFarba);

                // Výpis po výmene
                this.pocitadlo = 0;
                this.pozriRiadkyVymena();
                this.pozriStlpceVymena();

                if (this.pocitadlo > 0) {
                    this.tahy--;
                    return true;
                }

                // Ak nebola výmena platná, vrátime naspäť
                String originalFarba = this.pozriFarbu[riadok1][stlpec1].getFarba();
                this.pozriFarbu[riadok1][stlpec1].setFarba(this.pozriFarbu[riadok2][stlpec2].getFarba());
                this.pozriFarbu[riadok2][stlpec2].setFarba(originalFarba);
                return false;
            }
        }
        return false;
    }

    /**
     * Getter, ktorý nám vracia jeden štvorec z pola this.display
     */
    public StvorecDispleja getStvorec(int x, int y) {
        return this.display[x][y];
    }

    /**
     * Metóda, ktorá pozerá či nastala zhoda v riadku tým, že prechádza celé pole štvorcov z ľavého horného horu až po pravý
     * dolný roh, vynecháva štvorce, ktoré sú biele, pozerá aktuálny a ďalšie dva nasledujúce, ak sa nájde zhoda
     * pre 4 a 5 štvorcov, tak sa vytvoria špeciálne štvorce
     */
    private void pozriRiadky(StvorecDispleja[][] pole , boolean flag , AtomicInteger localScore) {
        if (pole == null) {
            System.out.println("nemáme pole");
            return;
        }

        IntStream.range(0, riadky) // Iterujeme cez všetky riadky
            .parallel() // Paralelné spracovanie
            .forEach(i -> {
                for (int j = 0; j < this.riadky - 2; j++) {
                    // Kontrola trojice zhodných farieb v riadku
                    if (!pole[i][j].getFarba().equals(pole[i][j + 1].getFarba()) ||
                        !pole[i][j].getFarba().equals(pole[i][j + 2].getFarba())) {
                        continue;
                    }

                    // Ak nájdeme tri alebo viac zhodných, začneme vyhľadávať ďalšie
                    String farba = pole[i][j].getFarba();
                    int k = j; // Začiatočný index
                    while (k < this.riadky && pole[i][k].getFarba().equals(farba)) {
                        k++; // Posúvame sa doprava, kým nachádzame zhodné
                    }

                    if (flag) {
                        // Zvýš skóre podľa počtu nájdených zhod
                        score.addAndGet((k - j) * 100);

                        // Vymaž všetky zhody v rade od indexu j po k
                        for (int x = j; x < k; x++) {
                            pole[i][x].vymaz(this, i, x);
                        }
                    } else {
                        localScore.addAndGet((k - j));
                    }

                    // Nastav ukazovateľ na koniec vymazanej sekvencie (k - 1)
                    j = k - 1;
                }
            });
    }

    /**
     * Metóda, ktorá pozerá či nastala zhoda v stĺpci tým, že prechádza celé pole štvorcov z ľavého horného horu až po pravý
     * dolný roh, vynecháva štvorce, ktoré sú biele, pozerá aktuálny a ďalšie dva nasledujúce, ak sa náhdou nájde zhoda
     * pre 4 a 5 štvorcov, tak sa vykonajú špeciálne štvorce, rozdiel je v tom, že oproti metóde pozriRiadky
     * sú otočené iterátory a tým sa pozerá pole pozerá z hora na dol
     */
    private void pozriStlpce(StvorecDispleja[][] pole, boolean flag, AtomicInteger localScore) {
        if (pole == null) {
            System.out.println("nemáme pole");
            return;
        }

        IntStream.range(0, this.riadky) // správne: stĺpce iterujeme cez j
            .parallel()
            .forEach(j -> {
                for (int i = 0; i < riadky - 2; i++) {
                    if (!pole[i][j].getFarba().equals(pole[i + 1][j].getFarba()) ||
                        !pole[i][j].getFarba().equals(pole[i + 2][j].getFarba())) {
                        continue;
                    }

                    String farba = pole[i][j].getFarba();
                    int k = i;
                    while (k < this.riadky && pole[k][j].getFarba().equals(farba)) {
                        k++;
                    }

                    if (flag) {
                        score.addAndGet((k - i) * 100);
                        for (int x = i; x < k; x++) {
                            pole[x][j].vymaz(this, x, j);
                        }
                    } else {
                        localScore.addAndGet((k - i));  // počet zhôd, nie len 1
                    }
                    i = k - 1;
                }
            });
    }

    public void paralelnaHeuristikaRiadky() {
        IntStream.range(0, this.riadky)
            .parallel()
            .forEach(i -> {
                for (int j = 0; j < this.riadky - 1; j++) {
                    StvorecDispleja[][] kopia = vytvorKopiuHry();

                    StvorecDispleja temp = kopia[i][j];
                    kopia[i][j] = kopia[i][j + 1];
                    kopia[i][j + 1] = temp;

                    AtomicInteger localScore = new AtomicInteger();
                    this.pozriRiadky(kopia, false, localScore);

                    if (localScore.get() > 0 && localScore.get() > 3) {
                        this.grid.addScore(j , i , localScore.get());
                    }
                }
            });
    }




    public void paralelnaHeuristikaStlpce() {
        IntStream.range(0, this.riadky)
            .parallel()
            .forEach(j -> {
                for (int i = 0; i < this.riadky - 1; i++) {
                    StvorecDispleja[][] kopia = vytvorKopiuHry();

                    // Prehodenie prvkov v stĺpci na riadkoch i a i+1
                    StvorecDispleja temp = kopia[i][j];
                    kopia[i][j] = kopia[i + 1][j];
                    kopia[i + 1][j] = temp;

                    AtomicInteger localScore = new AtomicInteger();
                    this.pozriStlpce(kopia, false, localScore);

                    if (localScore.get() > 0 && localScore.get() > 3) {
                        this.grid.addScore(i , j , localScore.get());
                    }
                }
            });
    }


    public void vykresli() {
        try {
            for (int i = 0; i < this.riadky; i++) {
                if (this.display[this.riadky - 1][i].getFarba().equals("white")) {
                    this.display[this.riadky - 1][i] = new Zakladny(VyberFarby.nahodnaFarba(), 0, i);
                }
            }
        } catch (Exception e) {
            Gdx.app.log("ERROR", "Hra padla vykreslenie", e);
        }
    }

    /**
     * Metóda, ktorá posúva štvorce s bielou farbou na vrch pola
     * aby ich mohla metóda vykresli() zmeniť na iné
     */
    public Boolean posunDole(StvorecDispleja[][] pole) {
        IntStream.range(0, this.riadky) // Iterujeme cez všetky stĺpce
            .parallel() // Paralelné spracovanie
            .forEach(j -> {
                for (int i = this.riadky - 1; i >= 0; i--) {
                    // Kontrola trojice zhodných farieb v stĺpci
                    if (pole[i][j].getFarba().equals("white")) {
                        this.vymena(i , j , i + 1, j);
                    }
                }
            });
        return false;
    }

    public int getScore() {
        return this.score.get();
    }

    /**
     * Getter, ktorý vracia veľkosť hry
     * @return int Veľkosť hry
     */
    public int getRiadky() {
        return this.riadky;
    }

    /**
     * Nastaví v poly this.display štvorec aký mu zadáme v podmienke
     * @param stvorec Ľubovoľný štvorec, ktorý je potomkom StvorecDispleja
     * @param x Poloha v poly
     * @param y Poloha v poly
     */
    public void setStvorec(StvorecDispleja stvorec , int x , int y) {
        this.display[x][y] = stvorec;
    }

    /**
     * Setter, ktorý pridá hodnotu k skóre
     * @param score int Skóre, ktoré chcem pridať
     */
    public void setScore(int score) {
        this.score.addAndGet(score);
    }

    public void vypisStvorcov() {
        for (int i = 0 ; i < this.riadky ; i++) {
            for (int j = 0 ; j < this.riadky ; j++) {
                System.out.print(this.display[i][j].getFarba() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Metóda nám nahrá hru na display s pôvodnými nastaveniami aby sme sa vedeli vrátiť o krok späť
     */
    public void nahrajHru() {
        if (this.ulozenaHra != null) {
            for (int i = 0 ; i < this.riadky ; i++) {
                for (int j = 0 ; j < this.riadky ; j++) {
                    this.display[i][j] = this.ulozenaHra[i][j];
                }
            }
        }
    }

    /**
     * Metóda nám uloží pôvodnú hru aby sme sa vedeli vrátiť o krok späť
     */
    private StvorecDispleja[][] vytvorKopiuHry() {
        StvorecDispleja[][] kopia = new StvorecDispleja[this.riadky][this.riadky];
        for (int i = 0; i < this.riadky; i++) {
            for (int j = 0; j < this.riadky; j++) {
                kopia[i][j] = new Zakladny(display[i][j].getFarba(), display[i][j].getX(), display[i][j].getY());
            }
        }
        return kopia;
    }



    public void zrusOznacenie() {
        for (int i = 0 ; i < this.riadky ; i++) {
            for (int j = 0 ; j < this.riadky ; j++) {
                this.display[i][j].zrusOznacenie();
            }
        }
    }

    public int getMoves() {
        return this.tahy;
    }

    public int getRozmer() {
        return this.riadky;
    }

    public void setOznacenie(int x , int y) {
        this.display[x][y].setOznacene();
    }

    public String getStvorecFarba(int i, int j) {
        return this.display[i][j].getFarba();
    }

    public void restart() {
        for (int i = 0 ; i < this.riadky ; i++) {
            for (int j = 0 ; j < this.riadky ; j++) {
                this.display[i][j].setFarba(VyberFarby.nahodnaFarba());
            }
        }

        this.tahy = this.riadky * 3;
        this.score.set(0);
        this.jeZapnuta = true;

        if (this.tahy == this.riadky * 3) {
            this.score.set(0);
        }
    }

    public void printTopPoints(List<GridPoint> topPoints) {
        for (GridPoint gp : topPoints) {
            System.out.printf("x=%d, y=%d, score=%d%n", gp.getX(), gp.getY(), gp.getValue());
        }
    }

}
