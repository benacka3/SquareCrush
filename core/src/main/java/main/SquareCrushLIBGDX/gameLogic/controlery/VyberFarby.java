package main.SquareCrushLIBGDX.gameLogic.controlery;

import java.util.Random;

/**
 * T�to enumer�cia n�m pom�ha pri vybran� farby pre �tvorec, u�ah�uje vyberanie farby na ko�ko sme si vytvorili in�tancie pre
 * ka�d� farbu s jej pr�slu�nou hodnotou
 *
 * @author Adam Be�a�ka
 * @version 0.1
 */

public enum VyberFarby {
    RED("red"),
    YELLOW("yellow"),
    BLUE("blue"),
    GREEN("green"),
    MAGENTA("magenta"),
    WHITE("white");

    private String farba;

    /**
     * Met�da priradzuje parameter do atrib�tu farba
     * @param farba String
     */
    VyberFarby(String farba) {
        this.farba = farba;
    }

    /**
     * Met�da n�m vracia atrib�t farba
     * @return String
     */
    public String getFarba() {
        return this.farba;
    }

    /**
     * Statick� met�da, ktor� n�m vracia n�hodn� String hodnotu jednej z farieb aby sme vedeli spravi� n�hodn� farbu �tvorca,
     * met�da pou�ije random.nextInt z d�ky pola, ktor� vracia met�da values a potom ju vracia
     * @return String
     */
    public static String nahodnaFarba() {
        Random random = new Random();
        return values()[random.nextInt(values().length - 1)].farba;
    }
}
