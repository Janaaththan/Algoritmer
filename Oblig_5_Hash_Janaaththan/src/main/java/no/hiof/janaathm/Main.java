package no.hiof.janaathm;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int hashLengde = 5;
        hashLinear hl = new hashLinear(hashLengde);

        try {
            File file = new File("inputText.txt");
            Scanner scan = new Scanner(file);
            for(int i = 0; i < hashLengde; i++){
                scan.hasNextLine();
                if (hl.insert(scan.nextLine()) == 0 || !scan.hasNextLine()) {
                    scan.close();
                    break;
                } else {
                    hl.insert(scan.nextLine());
                }
            }
        } catch (IOException e) {
            System.err.println("Filen ble ikke funnet. Sjekk filen og plassering");
            System.exit(1);
        }


        for (int j = 0; j < hl.hashLengde; j++) {
            if (hl.hashTabell[j] != null) {
                System.out.println("Nåværende index: " + j +
                        " Index plassering for hash: " + hl.hash(hl.hashTabell[j]) +
                        " Ord: " + "\"" + hl.hashTabell[j] + "\"" +
                        " Hashkode: " + hl.hashTabell[j].hashCode());
            }
        }

        System.out.println("Hashlengde  : " + hashLengde);
        System.out.println("Elementer   : " + hl.antData());


        String S1 = "Audi";
        if (hl.search(S1))
            System.out.println("\"" + S1 + "\"" + " finnes i hashtabellen");
        S1 = "Subaru";
        if (!hl.search(S1))
            System.out.println("\"" + S1 + "\"" + " finnes ikke i hashtabellen");

    }

}
