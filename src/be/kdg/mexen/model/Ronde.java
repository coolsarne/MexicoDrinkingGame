package be.kdg.mexen.model;

import java.util.*;

public class Ronde {
    private int aantalWorpen;
    private int scoreToBeat;
    private Beurt beurt;
    private int beurtenTeller;
    private int aantalGlazenDrinken;
    private boolean mensSpelerAanDeBeurt;
    private List<Speler> knockOutRondeSpelers;
    private Map<Speler, Integer> knockoutScores;


    // Ronde aanmaken
    public Ronde() {
        aantalWorpen = 3;
        scoreToBeat = 0;
        aantalGlazenDrinken = 1;
        mensSpelerAanDeBeurt = false;
        beurtenTeller = 0;
        knockOutRondeSpelers = new LinkedList<>();
        knockoutScores = new HashMap<>();

    }

    public void nieuweBeurt(Speler speler) {

        //Checken of mens speler aan de beurt is
        mensSpelerAanDeBeurt = speler instanceof MensSpeler;

        //Nieuwe beurt aanmaken
        beurt = new Beurt(speler, getAantalRondeWorpen(), scoreToBeat);
    }

    public void berekenBeurtWaarden(Speler speler) {
        //Score die speler gegooid heeft ophalen, andere variabele die 21 als hoogste waarde geeft (2100 ipv 21)
        int laatsteScore = speler.getLaatsteScore();
        int scoreWaarde = (laatsteScore == 21) ? 2100 : laatsteScore;

        //Als de eerste speler van de ronde aan de beurt is, het max aantal worpen aan zijn aantal worpen gelijk stellen
        //Score to beat gelijkstellen aan worp vd eerste speler (2100 als speler 21 gegooid heeft)
        if (beurtenTeller == 0) {
            setAantalRondeWorpen(beurt.getAantalKeerGeworpen());
            scoreToBeat = scoreWaarde;
        }

        //Aantal te drinken glazen verhogen als speler 21 gegooid heeft
        if (laatsteScore == 21) aantalGlazenDrinken++;

        //Score to beat updaten
        if (scoreWaarde < scoreToBeat) scoreToBeat = scoreWaarde;

        //beurtenteller verhogen, zodat de volgorde waarin de spelers aan de beurt zijn klopt
        beurtenTeller++;
    }

    // Geeft de speler terug die de ronde heeft verloren
    public Speler getVerlorenSpeler(List<Speler> spelers) {
        Speler verlorenSpeler;
        if (spelers.get(0).getLaatsteScore() == getScoreToBeat()) verlorenSpeler = spelers.get(0);
        else if (spelers.get(1).getLaatsteScore() == getScoreToBeat()) verlorenSpeler = spelers.get(1);
        else verlorenSpeler = spelers.get(2);
        return verlorenSpeler;
    }

    public void knockOutBeurt(Speler speler){
        beurt = new Beurt(speler,1, 0);

    }

    public void berekenKnockOutWaarden(Speler speler){
        //speler zijn score uit knockout ronde mag niet meetellen in de totale score op het einde en aantal ronde worpen is altijd 1 bij knockout ronde, de 21 mag ook niet meetellen als extra ad fundum

        //Score die speler gegooid heeft ophalen, andere variabele die 21 als hoogste waarde geeft (2100 ipv 21)
        int laatsteScore = speler.getLaatsteScore();
        int scoreWaarde = (laatsteScore == 21) ? 2100 : laatsteScore;

        //Als de eerste speler van de ronde aan de beurt is, het max aantal worpen aan zijn aantal worpen gelijk stellen
        //Score to beat gelijkstellen aan worp vd eerste speler (2100 als speler 21 gegooid heeft)
        if (beurtenTeller == 0) {
            setAantalRondeWorpen(beurt.getAantalKeerGeworpen());
            scoreToBeat = scoreWaarde;
        }
        //Score to beat updaten
        if (scoreWaarde < scoreToBeat) scoreToBeat = scoreWaarde;

        //laatste score toevoegen aan hashmap (met 21 als hoogste score)
        knockoutScores.put(speler,scoreWaarde);

        //laatste score die opgeslagen is in speler verwijderen
        speler.verwijderLaatsteScore();


    }

    public Speler getKnockOutVerliezer(){
        Speler verliezer = null;
        int hoogsteScore = 2500;
        for (Speler speler : knockoutScores.keySet()) {
            if (knockoutScores.get(speler) < hoogsteScore){
                verliezer = speler;
                hoogsteScore = knockoutScores.get(speler);
            } else if (knockoutScores.get(speler) == hoogsteScore){
                //return null als beide spelers dezelfde score hebben gegooid, zodat er niemand moet drinken
                return null;
            }
        }
        return verliezer;
    }

    public void setKnockOutRondeSpelers(List<Speler> spelers){
        for (Speler speler : spelers) {
            int scoreWaarde = (speler.getLaatsteScore() == 21) ? 2100 : speler.getLaatsteScore();
            if(scoreWaarde == scoreToBeat){
                knockOutRondeSpelers.add(speler);
            }
        }
    }

    public List<Speler> getKnockOutRondeSpelers() {
        return knockOutRondeSpelers;

    }

    //Geeft terug of de verloren speler al dan niet berekend kan worden, bij false moet er dus een knock out ronde gespeeld worden
    public boolean knockOutRondeNodig(List<Speler> spelers) {
        int laagsteScore = 2500;
        for (Speler speler : spelers) {
            int scoreWaarde = (speler.getLaatsteScore() == 21) ? 2100 : speler.getLaatsteScore();
            if (scoreWaarde < laagsteScore) {
                laagsteScore = scoreWaarde;
            }
        }
        if (spelers.get(0).getLaatsteScore() == spelers.get(1).getLaatsteScore() && spelers.get(0).getLaatsteScore() == laagsteScore)
            return true;
        else if (spelers.get(0).getLaatsteScore() == spelers.get(2).getLaatsteScore() && spelers.get(0).getLaatsteScore() == laagsteScore)
            return true;
        else if (spelers.get(1).getLaatsteScore() == spelers.get(2).getLaatsteScore() && spelers.get(1).getLaatsteScore() == laagsteScore)
            return true;
        else return false;

    }

    public void drinkGlazenLeeg(Speler speler) {
        speler.drinkGlas(getAantalGlazenDrinken());
    }

    public boolean isMensSpelerAanDeBeurt() {
        return mensSpelerAanDeBeurt;
    }

    // Geeft het aantal worpen terug die deze ronde maximum gegooid mogen worden
    public int getAantalRondeWorpen() {
        return aantalWorpen;
    }

    // Zet het aantal worpen die deze ronde gegooid mogen worden
    public void setAantalRondeWorpen(int aantalWorpen) {
        this.aantalWorpen = aantalWorpen;
    }

    // Geeft het aantal glazen terug dat de verliezer moet drinken
    public int getAantalGlazenDrinken() {
        return aantalGlazenDrinken;
    }

    // Geeft de score to beat terug
    public int getScoreToBeat() {
        return scoreToBeat;
    }

    // Geeft de huidige beurt terug
    public Beurt getBeurt() {
        return beurt;
    }

    public String toString(int rondeNummer) {
        return String.format("%de ronde: ", rondeNummer);
    }
}
