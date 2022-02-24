package be.kdg.mexen.model;

import java.util.Random;

public class Worp {
    Random random = new Random();
    private final int dobbelsteen1;
    private final int dobbelsteen2;

    // Werpen van 2 dobbelstenen
    public Worp() {
        this.dobbelsteen1 = random.nextInt(6) + 1;
        this.dobbelsteen2 = random.nextInt(6) + 1;
    }

    // Werpen van 1 dobbelsteen
    // Is eventueel van toepassing als er een 2 of een 1 geworpen werd
    public Worp(int dobbelsteen) {
        switch (dobbelsteen){
            case 11: this.dobbelsteen1 = 1; this.dobbelsteen2 = random.nextInt(6)+1; break;
            case 12: this.dobbelsteen1 = 2; this.dobbelsteen2 = random.nextInt(6)+1; break;
            case 21: this.dobbelsteen1 = random.nextInt(6)+1; this.dobbelsteen2 = 1; break;
            default: this.dobbelsteen1 = random.nextInt(6)+1; this.dobbelsteen2 = 2; break;
        }
    }

    // Score berekenen adhv de gegooide dobbelstenen
    public int berekenScore() {
        int score;
        if (dobbelsteen1 > dobbelsteen2) {
            score = Integer.parseInt(dobbelsteen1 + String.valueOf(dobbelsteen2));
        } else if (dobbelsteen1 < dobbelsteen2) {
            score = Integer.parseInt(dobbelsteen2 + String.valueOf(dobbelsteen1));
        } else {
            score = dobbelsteen1 * 100;
        }
        return score;
    }

    public boolean kansOpMex() {
        return (dobbelsteen1 == 1 || dobbelsteen1 == 2 || dobbelsteen2 == 1 || dobbelsteen2 == 2);
    }

    public int getDobbelsteen1() {
        return this.dobbelsteen1;
    }

    public int getDobbelsteen2() {
        return this.dobbelsteen2;
    }

    public int kansOpMexDobbelsteen() { //decimale getal is de dobbelsteen, de eenheid is de waarde
        if (dobbelsteen1 == 1) return 11;
        else if (dobbelsteen2 == 1) return 21;
        else if (dobbelsteen1 == 2) return 12;
        else return 22;
    }


}
