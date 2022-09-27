package hiof.no.janaaththan;

import java.io.*;
import java.util.Scanner;

public class Analyse {
    public static void main(String[] args) {
        try {

            //Leser fra fil i mappen og benytter scanner
            //Mulig å legge til og endre i input.txt filen
            File text = new File("input.txt");
            //System.out.println(text.getAbsolutePath());
            Scanner fileScanner = new Scanner(text);

            //Danner et binært søke tree
            BSearchTree textTree = new BSearchTree();

            //Kjører en while loop som vil loope så lenge det eksisterer en ny token i filen
            while (fileScanner.hasNext()) {
                textTree.insert(fileScanner.next().toUpperCase());
            }
            textTree.printInorder();

            //NB får ikke tatt vekk , og . ?
        }
        catch (FileNotFoundException e) {
            System.out.println("Feil ved filen/finner ikke filen");
            e.printStackTrace();
        }
    }
}
