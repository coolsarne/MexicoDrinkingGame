package be.kdg.mexen.model;

/**
 * Arne Cools
 * 01/03/2021
 */

//De mogelijke dranken waarmee het spel gespeeld kan worden
public enum Drank {
    COLA(1, "Cola"), BIER(2, "Bier"), STERKEDRANK(3, "Sterkedrank");

    // invloedsgraad is de mate waarin de keuze van de drank invloed heeft op het gedrag van de computer (0 geen, 1 gemiddeld, 2 hoog)
    private int beInvloedingsGraad;
    private String naam;

    Drank(int invloedsGraad, String naam) {
        this.beInvloedingsGraad = invloedsGraad;
        this.naam = naam;
    }

    public int getBeInvloedingsGraad() {
        return beInvloedingsGraad;
    }
    public String getNaam() {
        return naam;
    }
}
