package be.kdg.mexen.model;

import java.util.Random;

public class ComputerSpeler extends Speler {
    private Random random = new Random();

    public ComputerSpeler(String naam, int aantalStartGlazen, Drank drank, String profielfoto, int nummer) {
        super(naam, aantalStartGlazen, drank, profielfoto, nummer);
    }

    // Berekent of de computerspeler nog een keer wil werpen
    @Override
    public boolean nogEenWorp(int score, int scoreToBeat) {
        boolean nogEenKeer = true;
        if (getDomheidsgraad() >= 6) {                  // Als zijn domheidsgraad groter of gelijk aan 6 is
            nogEenKeer = random.nextBoolean();
        } else {
            if (scoreToBeat == 0) {                     // Als hij de eerste worp heeft
                if (score == 21) {                      // Als hij 21 gooit stoppen
                    nogEenKeer = false;
                } else if (score >= 62) {               // Als hij meer dan 62 gooi stoppen
                    nogEenKeer = false;
                }
            } else {                                    // Als hij niet de eerste worp heeft
                if (getDomheidsgraad() >= 4) {          // Als zijn domheidsgraad groter of gelijk aan 4 is
                    nogEenKeer = random.nextBoolean();
                } else {
                    if (score == 21) {                  // Als hij 21 gooit, stoppen
                        nogEenKeer = false;
                    } else if (score > scoreToBeat) {   // Als hij meer gooit dan de score to beat
                        nogEenKeer = false;
                    } else if (score >= 62) {           // Als score kleiner is dan score to beat maar de score is groter of gelijk aan 62
                        nogEenKeer = false;
                    }
                    // nogEenKeer is true als hij minder gooit dan de score to beat en minder dan 62
                }
            }
        }

        return nogEenKeer;
    }

    // Berekent de domheidsgraad waarop men kan berekenen of de ComputerSpeler nog een keer wil werpen
    // Wordt in de toekomst geïmplementeerd
    public int getDomheidsgraad() {
        return (AANTAL_START_GLAZEN - getAantalGlazen()) * super.getDrank().getBeInvloedingsGraad();
    }
}
