package be.kdg.mexen.model;

public class Beurt {
    private final int MAX_AANTAL_WORPEN = 3;
    private int aantalRondeWorpen;
    private int aantalKeerGeworpen = 0;
    private int scoreToBeat;
    private Speler spelerAanDeBeurt;
    private Worp worp;
    private int score = 0;
    private int mexDobbelsteenVorigeWorp;

    // Beurt aanmaken voor een speler
    public Beurt(Speler speler, int aantalRondeWorpen, int scoreToBeat){
        this.spelerAanDeBeurt = speler;
        this.aantalRondeWorpen = aantalRondeWorpen;
        this.scoreToBeat = scoreToBeat;
}

    public void doeWorp2Dobbelstenen(){
        aantalKeerGeworpen++;
        worp = new Worp();
        this.score = worp.berekenScore();
        this.mexDobbelsteenVorigeWorp = worp.kansOpMexDobbelsteen();
//        System.out.println(getSpelerAanDeBeurt().getNaam() + " gooit " + score + "\n");

    }

    public void doeWorp1Dobbelsteen(){
        worp = new Worp(mexDobbelsteenVorigeWorp);
        this.score = worp.berekenScore();
    }

    public boolean beurtGedaan(){
        if(aantalKeerGeworpen == MAX_AANTAL_WORPEN || aantalKeerGeworpen == aantalRondeWorpen){
            return true;
        } else return false;
    }

    // Geeft het aantal keer dat de speler in zijn beurt geworpen heeft terug
    public int getAantalKeerGeworpen() {
        return aantalKeerGeworpen;
    }

    // De speler zijn laatste gegooide score naar de speler zijn scorelijst doorgeven
    public void scoreBijhouden(Speler speler) {
        speler.setLaatsteScore(score);
    }

    // Geeft de score terug die de speler minimum probeert te halen
    public int getScoreToBeat() {
        return scoreToBeat;
    }

    // Geeft de speler terug die aan de beurt is
    public Speler getSpelerAanDeBeurt(){
        return spelerAanDeBeurt;
    }

    public int getScore(){
        return this.score;
    }

    // Geeft de Worp terug
    public Worp getWorp(){
        return worp;
    }

    public int getMexDobbelsteenVorigeWorp() {
        return mexDobbelsteenVorigeWorp;
    }
}
