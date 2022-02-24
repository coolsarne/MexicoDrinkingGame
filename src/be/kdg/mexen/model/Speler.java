package be.kdg.mexen.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Speler implements Comparable<Speler> {
    private String naam;
    protected final int AANTAL_START_GLAZEN;
    private int aantalGlazen;
    private Drank drank;
    List<Integer> scores = new LinkedList<>();
    private String profielfoto;
    private int nummer;

    // Constructor
    public Speler(String naam, int aantalStartGlazen, Drank drank, String profielfoto, int nummer) {
        this.naam = naam;
        this.AANTAL_START_GLAZEN = aantalStartGlazen;
        aantalGlazen = aantalStartGlazen;
        this.drank = drank;
        this.profielfoto = profielfoto;
        this.nummer = nummer;
    }

    // Geeft de naam van de speler terug
    public String getNaam() {
        return naam;
    }

    // Geeft de profielfoto van de speler terug
    public String getProfielfoto() {
        return profielfoto;
    }

    // Geeft het aantal volle glazen terug die de speler nog heeft
    public int getAantalGlazen() {
        if (this.aantalGlazen > 0) {
            return this.aantalGlazen;
        } else return 0;
    }

    // Geeft het aantal glazen terug waarmee de speler begint
    public int getAANTAL_START_GLAZEN() {
        return AANTAL_START_GLAZEN;
    }

    // Vermiderd het aantal glazen van een speler
    public void drinkGlas(int aantalGlazen) {
        this.aantalGlazen -= aantalGlazen;
    }

    // Geeft de som van alle geworpen scores van de speler terug
    public int getTotaleScore() {
        int totaleScore = 0;
        for (int score : scores) {
            totaleScore += score;
        }
        return totaleScore;
    }

    // Geeft terug of de speler nog een keer wilt werpen of niet
    public boolean nogEenWorp(int score, int scoreToBeat) {
        return false;
    }

    public Drank getDrank() {
        return drank;
    }

    // Geeft de laatst geworpen score terug van de speler
    public int getLaatsteScore() {
        return scores.get(scores.size() - 1);
    }

    public int getNummer() {
        return nummer;
    }

    // Voegt de geworpen score toe aan de list van geworpen scores
    public void setLaatsteScore(int score) {
        scores.add(score);
    }

    // Verwijdert de laatste geworpen score van de speler
    public void verwijderLaatsteScore() {
        scores.remove(scores.size() - 1);
    }

    @Override
    public int compareTo(Speler o) {
        // Sorteren op score (laag naar hoog), maar 21 is de hoogste score
        if (this.getLaatsteScore() == 21) {
            return this.getLaatsteScore() * 100 - o.getLaatsteScore();
        } else if (o.getLaatsteScore() == 21) {
            return this.getLaatsteScore() - o.getLaatsteScore() * 100;
        } else {
            return this.getLaatsteScore() - o.getLaatsteScore();
        }
    }

    @Override
    public String toString() {
        return String.format("%s heeft %d %s", getNaam(), getAantalGlazen(), (getAantalGlazen() == 1 ? "glas" : "glazen"));
    }
}
