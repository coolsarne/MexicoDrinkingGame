package be.kdg.mexen.model;

import java.awt.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class Spel {
    private int aantalRondes;
    // Spelers aanmaken
    private Speler speler1;
    private Speler speler2;
    private Speler speler3;
    // Lijst aanmaken waarin de volgorde van de spelers wordt bepaald
    private List<Speler> volgordeSpelers;
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
    private LocalDateTime now;
    //instellingen spel
    private String naamMensspeler;
    private int aantalSpelGlazen;
    private Drank typeDrank;
    private String profielFotoMensspeler;
    // Informatie voor view
    private Ronde ronde;
    private StringBuilder logFile;

    // Constructor
    public Spel() {
        aantalRondes = 0;
        volgordeSpelers = new LinkedList<>();
        now = LocalDateTime.now();
        logFile = new StringBuilder();

    }

    //spelers worden aangemaakt nadat het spel alle instellingen heeft doorgekregen
    public void maakSpelersAan() {
        speler1 = new MensSpeler(naamMensspeler, getAantalSpelGlazen(), getTypeDrank(), profielFotoMensspeler);
        speler2 = new ComputerSpeler("Maarten", getAantalSpelGlazen(), getTypeDrank(), "/instellingen/profielfoto/jongen.png", 2);
        speler3 = new ComputerSpeler("Birgit", getAantalSpelGlazen(), getTypeDrank(), "/instellingen/profielfoto/meisje.png", 3);

        // Spelers toevoegen aan lijst
        volgordeSpelers.add(speler1);
        volgordeSpelers.add(speler2);
        volgordeSpelers.add(speler3);
        System.out.print(toString());
    }

    // 1 ronde aanmaken
    // Wordt uitgevoerd na de spelers zijn aangemaakt
    public void startRonde() {
        //Rondenummer verhogen
        aantalRondes++;
        // Rondenummer afdrukken
        System.out.println("\n" + getAantalRondes() + "e ronde:");
        // Volgorde bepalen
        volgordeBepalen();

        // Nieuwe ronde aanmaken
        ronde = new Ronde();

    }


    //checken of er spelers zijn die geen glazen meer hebben
    public boolean isGedaan() {
        return speler1.getAantalGlazen() <= 0 || speler2.getAantalGlazen() <= 0 || speler3.getAantalGlazen() <= 0;
    }

    // Het teruggeven van het aantal gespeelde rondes (int)
    public int getAantalRondes() {
        return this.aantalRondes;
    }

    //Vanuit startscherm wordt de naam van de menselijke speler geset
    public void setNaamMensspeler(String naamMensspeler) {
        this.naamMensspeler = naamMensspeler;
    }

    // Keuzes van de Instellingen View oplaan in instellingen.txt
    public void setInstellingen(int aantalSpelGlazen, Drank typeDrank, String profielFoto) {
        this.aantalSpelGlazen = aantalSpelGlazen;
        this.typeDrank = typeDrank;
        this.profielFotoMensspeler = profielFoto;
    }

    public int getAantalSpelGlazen() {
        return aantalSpelGlazen;
    }

    public Drank getTypeDrank() {
        return typeDrank;
    }

    @Override
    public String toString() {
        return String.format("Het spel wordt gestart op %s om %su. De spelers zijn: %s, %s en %s.%n"
                , df.format(now), tf.format(now), speler2.getNaam(), speler3.getNaam(), speler1.getNaam());
    }

    public void voegToeLogfile(String zin) {
        logFile.append(zin);
    }

    // Volgorde bepalen van de spelers
    public void volgordeBepalen() {
        if (aantalRondes > 1) {
            Collections.sort(volgordeSpelers);
        }
    }

    // De methode die het toelaat om na elke ronde te wachten
    public static void wait(int seconden) {
        try {
            Thread.sleep(seconden * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    //Namen spelers teruggeven
    public String getNaamSpeler1() {
        return speler1.getNaam();
    }

    public String getNaamSpeler2() {
        return speler2.getNaam();
    }

    public String getNaamSpeler3() {
        return speler3.getNaam();
    }

    //Aantal glazen spelers teruggeven
    public int getAantalGlazenSpeler1() {
        return speler1.getAantalGlazen();
    }

    public int getAantalGlazenSpeler2() {
        return speler2.getAantalGlazen();
    }

    public int getAantalGlazenSpeler3() {
        return speler3.getAantalGlazen();
    }

    //Type drank waarmee gespeeld wordt teruggeven
    public String getDrank() {
        return typeDrank.getNaam();
    }

    //Score to beat teruggeven
    public int getScoreToBeat() {
        return ronde.getScoreToBeat();
    }

    //De speler die het spel verloren heeft teruggeven
    public String getVerlorenSpeler() {
        for (Speler speler : volgordeSpelers) {
            if (speler.getAantalGlazen() <= 0) {
                //System.out.println("\n\nDe speler die heeft verloren is " + speler.getNaam() + "\n");
                return speler.getNaam();
            }
        }
        return "geen";
    }

    public void sorteerSpelersAantalGlazen() {
        Collections.sort(volgordeSpelers, new Comparator<Speler>() {
            @Override
            public int compare(Speler o1, Speler o2) {
                return o2.getAantalGlazen() - o1.getAantalGlazen();
            }
        });
    }

    public void sorteerTotaleScores() {
        Collections.sort(volgordeSpelers, new Comparator<Speler>() {
            @Override
            public int compare(Speler o1, Speler o2) {
                return o1.getTotaleScore() - o2.getTotaleScore();
            }
        });
    }

    public void sorteerPodium() {
        Collections.sort(volgordeSpelers, new Comparator<Speler>() {
            @Override
            public int compare(Speler o1, Speler o2) {
                if (o2.getAantalGlazen() - o1.getAantalGlazen() == 0) {
                    return o2.getTotaleScore() - o1.getTotaleScore();
                } else {
                    return o2.getAantalGlazen() - o1.getAantalGlazen();
                }
            }
        });
    }

    // Profielfotos van de spelers teruggeven
    public String getProfielFotoSpeler1() {
        return speler1.getProfielfoto();
    }

    public String getProfielFotoSpeler2() {
        return speler2.getProfielfoto();
    }

    public String getProfielFotoSpeler3() {
        return speler3.getProfielfoto();
    }

    //Totale scores van de spelers teruggeven
    public int getTotaleScoreSpeler1() {
        return speler1.getTotaleScore();
    }

    public int getTotaleScoreSpeler2() {
        return speler2.getTotaleScore();
    }

    public int getTotaleScoreSpeler3() {
        return speler3.getTotaleScore();
    }

    public List<Speler> getVolgordeSpelers() {
        return volgordeSpelers;
    }

    public Ronde getRonde() {
        return ronde;
    }

    public StringBuilder getLogFile() {
        return logFile;
    }

    public int getGlazenSpeler1() {
        return speler1.getAantalGlazen();
    }
}
