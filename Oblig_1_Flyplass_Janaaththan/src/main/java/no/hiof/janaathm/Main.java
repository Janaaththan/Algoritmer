package no.hiof.janaathm;

import no.hiof.janaathm.model.Airport;
import no.hiof.janaathm.model.Plane;

import java.util.*;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class Main {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        //Tar inn inputdata fra brukeren. PS Forventet antall skal skrives med ","//
        Scanner sc = new Scanner(System.in);
        System.out.print("Hvor mange tidsenheter skal simuleringen gå? : ");
        int time = sc.nextInt();

        System.out.print("\n" + "Forventet antall ankomster pr. tidsenhet ?: ");
        double meanArrival = sc.nextDouble();

        System.out.print("\n" + "Forventet antall avganger pr. tidsenhet ?: ");
        double meanDeparted = sc.nextDouble();

        Airport haldenAir = new Airport(1, "Halden Airport");
        runwayControll(time, haldenAir, meanArrival, meanDeparted);
    }

    private static int getPoissonRandom(double mean)
    {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do
        {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

    public static void runwayControll(int time, Airport airport, double meanArrival, double meanDeparted) {
        int idCount = 0;
        int arrived = 0;
        int departed = 0;
        int canceled = 0;
        int empty = 0;
        int sumWaitTimeArrived = 0;
        int sumWaitTimeDeparted = 0;

        //Lager en tidsbasert forløkke som skal håndtere rullebanene //
        for (int t = 0; t < time; t++) {
            System.out.println("\n" + t + " :");
            airport.setRunwayInUse(false);

            //Danner fly som skal lande basert på antall som kommer fra possion fordeling.//
            for (int planeCount = 0; planeCount < getPoissonRandom(meanArrival); planeCount++){
                Plane plane = new Plane(idCount);
                idCount++;
                System.out.println("Fly " + plane.getId() + " klar for landing.");

                //Sjekker om landingskø og rullebanen er ledig//
                if (airport.getLandingQue().isEmpty() & !airport.getRunwayInUse()) {
                    airport.setRunwayInUse(true);
                    arrived++;
                    System.out.println("Fly " + plane.getId() + " landet, ventetid " + plane.getWaitTime() + " enheter.");

                }

                //Sjekker om køen er full//
                else if (airport.getLandingQue().size() == 10) {
                    canceled++;
                    System.out.println("Fly " + plane.getId() + " blir avvist grunnet begrenset antall plasser i køen.");

                }

                //Legger til i landingskø//
                else {
                    plane.setInitialTime(t);
                    airport.addLandingQue(plane);
                    System.out.println("Fly " + plane.getId() + " blir lagt til i landings køen.");
                }
            }

            //Danner fly som skal avta basert på antall som kommer fra possion fordeling.//
            for (int planeCount = 0; planeCount < getPoissonRandom(meanDeparted); planeCount++) {
                Plane plane = new Plane(idCount);
                idCount++;
                System.out.println("Fly " + plane.getId() + " klar for avgang.");

                //Sjekker om landingskø og rullebanen er ledig//
                if (airport.getTakeoffQue().isEmpty() & !airport.getRunwayInUse()) {
                    airport.setRunwayInUse(true);
                    departed++;
                    System.out.println("Fly " + plane.getId() + " avtar, ventetid " + plane.getWaitTime() + " enheter.");
                }

                //Sjekker om køen er full//
                else if (airport.getTakeoffQue().size() == 10) {
                    canceled++;
                    System.out.println("Fly " + plane.getId() + " blir avvist grunnet begrenset antall plasser i avgangskøen.");
                }

                //Legger til i landingskø//
                else {
                    plane.setInitialTime(t);
                    airport.addTakeoffQue(plane);
                    System.out.println("Fly " + plane.getId() + " blir lagt til i avgangskøen.");
                }
            }

            //Sjekker om landingskøen ikke er tom og rullebanen ikke er i bruk//
            if (!airport.getLandingQue().isEmpty() & !airport.getRunwayInUse()) {
                airport.setRunwayInUse(true);
                Plane plane = airport.getLandingQue().poll();
                plane.setWaitTime(t - plane.getInitialTime());
                arrived++;
                sumWaitTimeArrived += plane.getWaitTime();
                System.out.println("Fly " + plane.getId() + " har landet, ventetid " + plane.getWaitTime() + " enheter.");
            }

            //Sjekker om avgangskøen ikke er tom og rullebanen ikke er i bruk//
            else if (!airport.getTakeoffQue().isEmpty() & !airport.getRunwayInUse()) {
                airport.setRunwayInUse(true);
                Plane plane = airport.getTakeoffQue().poll();
                plane.setWaitTime(t - plane.getInitialTime());
                departed++;
                sumWaitTimeDeparted += plane.getWaitTime();
                System.out.println("Fly " + plane.getId() + " avtar, ventetid " + plane.getWaitTime() + " enheter.");
            }

            //Hvis rullebanen ikke er i bruk i slutten av tidsenheten vil flyplassen være tom//
            if (!airport.getRunwayInUse()) {
                empty++;
                System.out.println("Flyplassen er tom.");
            }

        }

        System.out.println("\n");
        System.out.println("Simuleringen ferdig etter " + time + " tidsenheter");
        System.out.println("Totalt antall fly behandlet: " + (arrived+departed+canceled));
        System.out.println("Antall fly landet: " + arrived);
        System.out.println("Antall fly tatt av: " + departed);
        System.out.println("Antall fly avvist: " + canceled);
        System.out.println("Antall fly klare for landing: " + airport.getLandingQue().size());
        System.out.println("Antall fly klare til å ta av: " + airport.getTakeoffQue().size());
        System.out.println("Prosent ledig tid: " +  (double) 100*empty/time);
        System.out.println("Gj.snitt. ventetid, landing: " + (double) sumWaitTimeArrived/arrived + " tidsenheter");
        System.out.println("Gj.snitt. ventetid, avgang: " + (double) sumWaitTimeDeparted/departed + " tidsenheter");

    }

}
