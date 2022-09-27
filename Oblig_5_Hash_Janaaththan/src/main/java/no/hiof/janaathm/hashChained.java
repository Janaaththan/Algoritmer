package no.hiof.janaathm;
import java.io.*;
import java.util.Scanner;

// Hashing av tekststrenger med kjeding i lenket liste
// Bruker Javas innebygde hashfunksjon for strenger
//
// Enkel og begrenset implementasjon:
//
// - Ingen rehashing ved full tabell/lange lister
// - Tilbyr bare innsetting og sÃ¸king
//
public class hashChained
{
    // Indre klasse:
    // Node med data, kjedes sammen i lenkede lister
    //
    private class hashNode
    {
        // Data, en tekststreng
        String data;

        // Neste node i listen
        hashNode neste;

        // KonstruktÃ¸r for listenoder
        public hashNode(String S, hashNode hN)
        {
            data = S;
            neste = hN;
        }
    }

    // Hashlengde
    private int hashLengde;

    // Hashtabell, pekere til lister
    private hashNode hashTabell[];

    // Antall elementer lagret i tabellen
    private int n;

    // Antall kollisjoner ved innsetting
    private int antKollisjoner;

    // KonstruktÃ¸r
    // Sjekker ikke for fornuftig verdi av hashlengden
    //
    public hashChained(int lengde)
    {
        hashLengde = lengde;
        hashTabell = new hashNode[lengde];
        n = 0;
        antKollisjoner = 0;
    }

    // Returnerer load factor
    public float loadFactor()
    {
        return ((float) n)/hashLengde;
    }

    // Returnerer antall data i tabellen
    public int antData()
    {
        return n;
    }

    // Returnerer antall kollisjoner ved innsetting
    public int antKollisjoner()
    {
        return antKollisjoner;
    }

    // Hashfunksjon
    int hash(String S)
    {
        int h = Math.abs(S.hashCode());
        return h % hashLengde;
    }

    // Innsetting av tekststreng med kjeding
    //
    void insert(String S)
    {
        // Beregner hashverdien
        int h = hash(S);

        // Ã˜ker antall elementer som er lagret
        n++;

        // Sjekker om kollisjon
        if (hashTabell[h] != null)
            antKollisjoner++;

        // Setter inn ny node fÃ¸rst i listen
        hashTabell[h] = new hashNode(S, hashTabell[h]);
    }

    //------------------------------------------------------------------------
    int sjekkNeste(String S) {
        hashNode hnode = hashTabell[hash(S)];
        int counter = 0;

        if (hnode != null){
            counter++;
            hnode = hnode.neste;
        }
        return counter;
    }
    //-------------------------------------------------------------------------

    // SÃ¸king etter tekststreng i hashtabell med kjeding
    // Returnerer true hvis strengen er lagret, false ellers
    //
    boolean search(String S)
    {
        // Finner listen som S skal ligge i
        hashNode hN = hashTabell[hash(S)];

        // Leter gjennom listen
        while (hN != null)
        {
            // Har vi funnet tekststrengen?
            if (hN.data.compareTo(S) == 0)
                return true;
            // PrÃ¸ver neste
            hN = hN.neste;
        }
        // Finner ikke strengen, har kommet til slutten av listen
        return false;
    }


    //---------------------------------------------------------------------------------------
    //https://www.geeksforgeeks.org/java-program-to-implement-hashtables-with-linear-probing/
    void Remove(String S, hashChained chain) {

        // Finner index for hash som S er
        hashNode hN = chain.hashTabell[hash(S)];

        //antall  noder som det er index for hash til S
        int antallNeste = chain.sjekkNeste(chain.hashTabell[hash(S)].data);

        if(antallNeste == 1) {
            // index  fjernes
            chain.hashTabell[hash(S)] = null;

            // Hvis det er flere enn 1 node i index for hash
        } else if(antallNeste > 1) {

            // blar gjennom listen
            while(hN != null) {

                // undersøker om neste node er null
                if(hN.neste != null) {
                    // Hvis rot er lik som S, og neste node ikke er null
                    if(hN.data.compareTo(S) == 0 && hN.neste != null) {

                        // Hele index for hash fjernes vekk
                        chain.hashTabell[hash(S)] = null;

                        // nå starter neste node på hN
                        hN = hN.neste;

                        // Oppdaterer noder
                        n = sjekkNeste(hN.data);

                        //  rehashes
                        if(hN != null) {
                            chain.insert(hN.data);
                            hN = hN.neste;
                        }
                        break;

                    } else if(hN.neste.data.compareTo(S) == 0 && hN.neste.neste == null) {

                        // Fjerner neste node
                        hN.neste = null;
                        break;
                    }
                }
                hN = hN.neste;
            }
        }
    }

    //------------------------------------------------------------------------------------------

    public static void main(String argv[])
    {
        // Hashlengde leses fra kommandolinjen
        int hashLengde = 5;

        // Lager ny hashTabell
        hashChained hC = new hashChained(hashLengde);

        try {
            File filetext = new File("inputText.txt");
            Scanner scan = new Scanner(filetext);
            for (int i = 0; i < hashLengde; i++) {
                hC.insert(scan.nextLine());
            }
        }
        catch (IOException e) {
            System.err.println("Filen ble ikke funnet. Sjekk filen og plassering");
            System.exit(1);
        }

        for(int i = 0; i < hC.hashLengde; i++) {
            if(hC.hashTabell[i] != null) {
                System.out.println("Nåværende index: " + i +
                        " Index plassering for hash: " + hC.hash(hC.hashTabell[i].data) +
                        " Ord: " + "\"" + hC.hashTabell[i] + "\"" +
                        " Hashkode: " + hC.hashTabell[i].hashCode());
               }
        }




        // Skriver ut hashlengde, antall data lest, antall kollisjoner
        // og load factor
        System.out.println("Hashlengde  : " + hashLengde);
        System.out.println("Elementer   : " + hC.antData());
        System.out.printf( "Load factor : %5.3f\n",  hC.loadFactor());
        System.out.println("Kollisjoner : " + hC.antKollisjoner());

        // Et par enkle sÃ¸k
        String S = "Tesla";
        if (hC.search(S))
            System.out.println("\"" + S + "\"" + " finnes i hashtabellen");
        S = "Il Tempo Gigante";
        if (!hC.search(S))
            System.out.println("\"" + S + "\"" + " finnes ikke i hashtabellen");

    }
}
