package no.hiof.janaathm;
import java.io.*;
import java.util.Scanner;

// Hashing av tekststrenger med lineÃ¦r probing
// Bruker Javas innebygde hashfunksjon for strenger
//
// Enkel og begrenset implementasjon:
//
// - Ingen rehashing ved full tabell
// - Tilbyr bare innsetting og sÃ¸king
//
public class hashLinear {

    int hashLengde; // Hashlengde
    String[] hashTabell; // Hashtabell
    public objectHash hashTabell2[];
    private int n; // Antall elementer lagret i tabellen
    private int antProbes; // Antall probes ved innsetting


    // KonstruktÃ¸r
    // Sjekker ikke for fornuftig verdi av hashlengden
    //
    public hashLinear(int lengde) {
        hashLengde = lengde;
        hashTabell = new String[lengde];
        hashTabell2 = new objectHash[lengde];
        n = 0;
        antProbes = 0;
    }

    // Returnerer load factor
    public float loadFactor() {
        return ((float) n) / hashLengde;
    }

    // Returnerer antall data i tabellen
    public int antData() {
        return n;
    }

    // Returnerer antall probes ved innsetting
    public int antProbes() {
        return antProbes;
    }


    // Hashfunksjon
    int hash(String S) {
        int h = Math.abs(S.hashCode());
        System.out.println(h);
        return h % hashLengde;
    }

    //------------------------------------------------------------
    //Funksjon som vil endre rekkefølgen slik at siste forekommede elementet ender i starten.
    //De eksisterende verdiene i tabellen vil vli flyttet et tak til høyre
    public void moveHash(String hashTable[], int step) {
        for (int i = 0; i < step; i++) {
            String lastHash;
            int amount;
            lastHash = hashTable[hashTable.length - 1];

            for (amount = hashTable.length - 1; amount > 0; amount--) {
                hashTable[amount] = hashTable[amount - 1];
            }

            hashTable[0] = lastHash;
        }
    }
    //-----------------------------------------------------------------


    // Innsetting av tekststreng med lineÃ¦r probing
    // Avbryter med feilmelding hvis ledig plass ikke finnes
    //
    public int insert(String S) {
        // Beregner hashverdien
        int h = hash(S);

        // LineÃ¦r probing
        int neste = h;

        while (hashTabell[neste] != null) {
            // Ny probe
            antProbes++;

            // Denne indeksen er opptatt, prøver neste
            neste++;

            // Wrap-around
            if (neste >= hashLengde)
                neste = 0;

            //----------------------------------------------------
            //Flytter verdiene i hashtabellen et tak til høyre
            //moveHash(hashTabell, 0);
            if (hashTabell[h] == null) {
                break;
            }
            //-----------------------------------------------------

            // Hvis vi er kommet tilbake til opprinnelig hashverdi, er
            // tabellen full og vi gir opp (her ville man normalt
            // doblet lengden pÃ¥ hashtabellen og gjort en rehashing)
            if (neste == h) {
                System.err.println("\nHashtabell full, avbryter");

                return 0;
            }
        }

        // Lagrer tekststrengen pÃ¥ funnet indeks
        hashTabell[neste] = S;

        // øker antall elementer som er lagret
        n++;
        return 1;
    }

    // SÃ¸king etter tekststreng med lineÃ¦r probing
    // Returnerer true hvis strengen er lagret, false ellers
    //
    boolean search(String S) {
        // Beregner hashverdien
        int h = hash(S);

        // LineÃ¦r probing
        int neste = h;

        while (hashTabell[neste] != null) {
            // Har vi funnet tekststrengen?
            if (hashTabell[neste].compareTo(S) == 0)
                return true;

            // PrÃ¸ver neste mulige
            neste++;

            // Wrap-around
            if (neste >= hashLengde)
                neste = 0;

            // Hvis vi er kommet tilbake til opprinnelig hashverdi,
            // finnes ikke strengen i tabellen
            if (neste == h)
                return false;
        }

        // Finner ikke strengen, har kommet til en probe som er null
        return false;
    }
















    public class objectHash {
        String ord;
        int countProbe;
        int keyOfHash;

        public objectHash(String S) {
            ord = S;
            countProbe = 0;
            keyOfHash = hash(S);
        }
    }

    public int RobinHood(String s) {
        objectHash objWord = new objectHash(s);
        int h = objWord.keyOfHash;
        objectHash S = objWord;

        int neste = h;

        while (hashTabell2[neste] != null) {
            objectHash curr;
            objectHash T;

            antProbes++;
            neste++;

            if (neste >= hashLengde) {
                neste = 0;
            }
            if (hashTabell2[h] == null) {
                break;
            }
            else {
                T = hashTabell2[h];

                if (S.countProbe > T.countProbe) {
                    curr = hashTabell2[h];
                    hashTabell2[h] = S;
                    S = curr;
                }
                else {
                    S.countProbe++;
                }
            }

            if (neste == h) {
                System.err.println("\nHashtabell full, avbryter");

                return 0;
            }

        }
        n++;
        return 1;
    }

}