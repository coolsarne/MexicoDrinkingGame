import be.kdg.mexen.model.Drank;
import be.kdg.mexen.model.Spel;
import be.kdg.mexen.model.Speler;

import java.util.Scanner;

public class MainSpelVerloop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Spel spel = new Spel();
        // Instellingen fixen
        spel.setNaamMensspeler("Jos");
        spel.setInstellingen(6, Drank.COLA,"/instellingen/profielfoto/jongen.png");
        spel.maakSpelersAan();

//PROCEDURE 1 RONDE:

        //ronde starten
        spel.startRonde();
        //printen hoeveel glazen elke speler heeft (optioneel)
        System.out.println(spel.getVolgordeSpelers().get(0).toString() + " " + spel.getVolgordeSpelers().get(1).toString() + " " + spel.getVolgordeSpelers().get(2).toString() + " ");
        boolean tweedeWorp = false;
        boolean derdeWorp = false;
        //voor elke speler een nieuwe beurt aanmaken in de juiste volgorde


    //BEURT 1 BEGIN

        System.out.println("score to beat is: " + spel.getRonde().getScoreToBeat());
        spel.getRonde().nieuweBeurt(spel.getVolgordeSpelers().get(0));
        System.out.println(spel.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam() + " is aan de beurt");
        if(!spel.getRonde().getBeurt().beurtGedaan()){
            spel.getRonde().getBeurt().doeWorp2Dobbelstenen();
        }
        System.out.printf("Dobbelsteen 1: %d     Dobbelsteen 2: %d%n",spel.getRonde().getBeurt().getWorp().getDobbelsteen1(),spel.getRonde().getBeurt().getWorp().getDobbelsteen2());
        System.out.println("Geworpen score: " + spel.getRonde().getBeurt().getScore());
        tweedeWorp = false;
        if(!spel.getRonde().getBeurt().beurtGedaan()){
            if(spel.getRonde().isMensSpelerAanDeBeurt()){
                System.out.print("Wil je nog een keer gooien? ");
                tweedeWorp = scanner.next().equalsIgnoreCase("ja");
            } else {
                tweedeWorp = spel.getRonde().getBeurt().getSpelerAanDeBeurt().nogEenWorp(spel.getRonde().getBeurt().getScore(),spel.getRonde().getBeurt().getScoreToBeat());
            }
        }
        if(tweedeWorp){
            if (!spel.getRonde().isMensSpelerAanDeBeurt()){
                System.out.println(spel.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam() + " gooit nog een keer" );
            }
            spel.getRonde().getBeurt().doeWorp2Dobbelstenen();
            System.out.println("Geworpen score: " + spel.getRonde().getBeurt().getScore());
        }
        derdeWorp = false;
        if(!spel.getRonde().getBeurt().beurtGedaan() && tweedeWorp){
            if(spel.getRonde().isMensSpelerAanDeBeurt()){
                System.out.print("Wil je nog een keer gooien? ");
                derdeWorp = scanner.next().equalsIgnoreCase("ja");
            } else {
                derdeWorp = spel.getRonde().getBeurt().getSpelerAanDeBeurt().nogEenWorp(spel.getRonde().getBeurt().getScore(),spel.getRonde().getBeurt().getScoreToBeat());
            }
        }
        if(derdeWorp){
            if (!spel.getRonde().isMensSpelerAanDeBeurt()){
                System.out.println(spel.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam() + " gooit nog een keer" );
            }
            spel.getRonde().getBeurt().doeWorp2Dobbelstenen();
            System.out.println("Geworpen score: " + spel.getRonde().getBeurt().getScore());
        }

        spel.getRonde().getBeurt().scoreBijhouden(spel.getVolgordeSpelers().get(0));
        spel.getRonde().berekenBeurtWaarden(spel.getVolgordeSpelers().get(0));

    //BEURT 1 EINDE

    //BEURT 2 BEGIN

        System.out.println("score to beat is: " + spel.getRonde().getScoreToBeat());
        spel.getRonde().nieuweBeurt(spel.getVolgordeSpelers().get(1));
        System.out.println(spel.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam() + " is aan de beurt");
        if(!spel.getRonde().getBeurt().beurtGedaan()){
            spel.getRonde().getBeurt().doeWorp2Dobbelstenen();
        }
        System.out.printf("Dobbelsteen 1: %d     Dobbelsteen 2: %d%n",spel.getRonde().getBeurt().getWorp().getDobbelsteen1(),spel.getRonde().getBeurt().getWorp().getDobbelsteen2());
        System.out.println("Geworpen score: " + spel.getRonde().getBeurt().getScore());
        tweedeWorp = false;
        if(!spel.getRonde().getBeurt().beurtGedaan()){
            if(spel.getRonde().isMensSpelerAanDeBeurt()){
                System.out.print("Wil je nog een keer gooien? ");
                tweedeWorp = scanner.next().equalsIgnoreCase("ja");
            } else {
                tweedeWorp = spel.getRonde().getBeurt().getSpelerAanDeBeurt().nogEenWorp(spel.getRonde().getBeurt().getScore(),spel.getRonde().getBeurt().getScoreToBeat());
            }
        }
        if(tweedeWorp){
            if (!spel.getRonde().isMensSpelerAanDeBeurt()){
                System.out.println(spel.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam() + " gooit nog een keer" );
            }
            spel.getRonde().getBeurt().doeWorp2Dobbelstenen();
            System.out.println("Geworpen score: " + spel.getRonde().getBeurt().getScore());
        }
        derdeWorp = false;
        if(!spel.getRonde().getBeurt().beurtGedaan() && tweedeWorp){
            if(spel.getRonde().isMensSpelerAanDeBeurt()){
                System.out.print("Wil je nog een keer gooien? ");
                derdeWorp = scanner.next().equalsIgnoreCase("ja");
            } else {
                derdeWorp = spel.getRonde().getBeurt().getSpelerAanDeBeurt().nogEenWorp(spel.getRonde().getBeurt().getScore(),spel.getRonde().getBeurt().getScoreToBeat());
            }
        }
        if(derdeWorp){
            if (!spel.getRonde().isMensSpelerAanDeBeurt()){
                System.out.println(spel.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam() + " gooit nog een keer" );
            }
            spel.getRonde().getBeurt().doeWorp2Dobbelstenen();
            System.out.println("Geworpen score: " + spel.getRonde().getBeurt().getScore());
        }

        spel.getRonde().getBeurt().scoreBijhouden(spel.getVolgordeSpelers().get(1));
        spel.getRonde().berekenBeurtWaarden(spel.getVolgordeSpelers().get(1));

    //BEURT 2 EINDE

    //BEURT 3 BEGIN

        System.out.println("score to beat is: " + spel.getRonde().getScoreToBeat());
        spel.getRonde().nieuweBeurt(spel.getVolgordeSpelers().get(2));
        System.out.println(spel.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam() + " is aan de beurt");
        if(!spel.getRonde().getBeurt().beurtGedaan()){
            spel.getRonde().getBeurt().doeWorp2Dobbelstenen();
        }
        System.out.printf("Dobbelsteen 1: %d     Dobbelsteen 2: %d%n",spel.getRonde().getBeurt().getWorp().getDobbelsteen1(),spel.getRonde().getBeurt().getWorp().getDobbelsteen2());
        System.out.println("Geworpen score: " + spel.getRonde().getBeurt().getScore());
        tweedeWorp = false;
        if(!spel.getRonde().getBeurt().beurtGedaan()){
            if(spel.getRonde().isMensSpelerAanDeBeurt()){
                System.out.print("Wil je nog een keer gooien? ");
                tweedeWorp = scanner.next().equalsIgnoreCase("ja");
            } else {
                tweedeWorp = spel.getRonde().getBeurt().getSpelerAanDeBeurt().nogEenWorp(spel.getRonde().getBeurt().getScore(),spel.getRonde().getBeurt().getScoreToBeat());
            }
        }
        if(tweedeWorp){
            if (!spel.getRonde().isMensSpelerAanDeBeurt()){
                System.out.println(spel.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam() + " gooit nog een keer" );
            }
            spel.getRonde().getBeurt().doeWorp2Dobbelstenen();
            System.out.println("Geworpen score: " + spel.getRonde().getBeurt().getScore());
        }
        derdeWorp = false;
        if(!spel.getRonde().getBeurt().beurtGedaan() && tweedeWorp){
            if(spel.getRonde().isMensSpelerAanDeBeurt()){
                System.out.print("Wil je nog een keer gooien? ");
                derdeWorp = scanner.next().equalsIgnoreCase("ja");
            } else {
                derdeWorp = spel.getRonde().getBeurt().getSpelerAanDeBeurt().nogEenWorp(spel.getRonde().getBeurt().getScore(),spel.getRonde().getBeurt().getScoreToBeat());
            }
        }
        if(derdeWorp){
            if (!spel.getRonde().isMensSpelerAanDeBeurt()){
                System.out.println(spel.getRonde().getBeurt().getSpelerAanDeBeurt().getNaam() + " gooit nog een keer" );
            }
            spel.getRonde().getBeurt().doeWorp2Dobbelstenen();
            System.out.println("Geworpen score: " + spel.getRonde().getBeurt().getScore());
        }

        spel.getRonde().getBeurt().scoreBijhouden(spel.getVolgordeSpelers().get(2));
        spel.getRonde().berekenBeurtWaarden(spel.getVolgordeSpelers().get(2));

    //BEURT 3 EINDE


        //Nadat elke speler gegooid heeft, kijken of er een knock-out ronde gespeeld moet worden
        if (!spel.getRonde().knockOutRondeNodig(spel.getVolgordeSpelers())){
            //indien er geen knock out ronde gespeeld word, direct de verloren speler berekenen en glazen drinken
            Speler verlorenSpeler;
            verlorenSpeler = spel.getRonde().getVerlorenSpeler(spel.getVolgordeSpelers());
            spel.getRonde().drinkGlazenLeeg(verlorenSpeler);
        } else {
            //indien er wel knock out ronde gespeeld word
            //TODO speel knock out ronde
        }
        //printen hoeveel glazen elke speler heeft (optioneel)
        System.out.println(spel.getVolgordeSpelers().get(0).toString() + " " + spel.getVolgordeSpelers().get(1).toString() + " " + spel.getVolgordeSpelers().get(2).toString() + " ");


    }
}
