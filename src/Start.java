import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Start {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static ArrayList<Rate> ratenListe;

    private static double gehalt;
    private static double startGehaltFix;

    private static double abzuege;
    private static double sparziel;
    private static double sparkonto;

    private static int monate = 1;
    private static int sparrate;
    private static int anzahlraten;


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Sparrechner wird geladen... \n Bitte gib dein Sparziel ein: \n");
        sparziel = s.nextDouble();

        System.out.println("Gib dein monatliches Nettogehalt an (Ausgaben & Steuern, keine Raten): \n");
        gehalt = s.nextDouble();
        startGehaltFix = gehalt;

        System.out.println("Gib deine monatliche Sparrate (%) als ganze Zahl an: \n");
        sparrate = s.nextInt();


        System.out.println("Gib an, wie viele Raten du insgesamt im Moment am Laufen hast (Ganze Zahlen oder 0 falls keine): \n");
        anzahlraten = s.nextInt();

        if (anzahlraten != 0) {
            ratenListe = new ArrayList<>();
            for (int i = 1; i < anzahlraten + 1; i++) {

                System.out.println("Gib den " + i + ". Ratenbetrag an:\n");
                double betrag = s.nextDouble();
                System.out.println("Gib die " + i + ". Ratenlaufzeit an:\n");
                int laufzeit = s.nextInt();
                Rate r = new Rate(laufzeit, betrag);
                ratenListe.add(r);
            }
        }
        int laufzeit = berechneSparzeit(sparziel);
        System.out.println("Du braucht maximal " + laufzeit + " Monate um dein Sparziel zu erreichen.");

    }


    public static int berechneSparzeit(double ziel) {
        sparkonto = 0;
        double netto = 0;
        double half = 0;

        while (sparkonto < ziel) {
            abzuege = 0;
            half = 0;
            gehalt = startGehaltFix;
            ArrayList<Double> l = new ArrayList<>();


            if (anzahlraten == 0) {
                netto = gehalt;
                sparkonto = sparkonto + (netto * (sparrate / 100d));

            } else {

                for (Rate value : ratenListe) {
                    if (monate > value.getLaufzeit()) {
                        value.setStatus(false);
                    }
                }

                for (Rate rate : ratenListe) {
                    if (rate.getStatus(true)) {
                        abzuege = abzuege + rate.getKosten();
                    } else {

                        //gehalt = gehalt + (rate.getKosten() * (sparrate / 100d));
                        l.add(rate.getKosten() * (sparrate / 100d));
                    }
                }
                //.out.println("Gehalt: " + gehalt);
                netto = gehalt - abzuege;
                //System.out.println("Netto: " + netto);
                if (!l.isEmpty()) {
                    for (Double aDouble : l) {

                        half = half + aDouble;

                    }
                    //System.out.println(sparkonto + " + (" + netto + " - " + half  + " * " + sparrate + " / 100 - ) + " + half);
                    sparkonto = sparkonto + ((netto - half) * (sparrate / 100d)) + half;
                } else {
                    //System.out.println(sparkonto + " + " + netto + " * " + sparrate + " / 100");
                    sparkonto = sparkonto + (netto * (sparrate / 100d));
                }
            }


            monate++;
            System.out.println("Sparkonto nach " + (monate - 1) + " Monaten beträgt " + df.format(sparkonto) + "€");
        }
        return monate - 1;
    }
}
