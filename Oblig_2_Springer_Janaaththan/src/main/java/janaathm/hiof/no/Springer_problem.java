package janaathm.hiof.no;

import java.util.Scanner;

public class Springer_problem {
    final long startTime = System.nanoTime();

    public static void main(String[] args) {
        //int n0 = 5;
        //int Board0[][] = makeBoard(n0);
        //System.out.println(sprinterRoute(n0, Board0,1,1));

        //int n1 = 6;
        //int Board1[][] = makeBoard(n1);
        //System.out.println(sprinterRoute(n1, Board1,2,2));

        //int n2 = 7;
        //int Board2[][] = makeBoard(n2);
        //System.out.println(sprinterRoute(n2, Board2,2,2));


        Scanner scanner = new Scanner(System.in);
        System.out.print("n: ");
        int n = scanner.nextInt();
        System.out.print("Start i: ");
        int starti = scanner.nextInt();
        System.out.print("Start j: ");
        int startj = scanner.nextInt();
        int Board[][] = makeBoard(n);
        System.out.println(sprinterRoute(n, Board, starti, startj));


    }

    //Lager et sjakkbrett med nxn ruter
    public static int[][] makeBoard(int n) {
        int Board[][];
        Board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Board[i][j] = 0;
            }
        }
        return Board;
    }


    public static boolean sprinterRoute(int n, int Board[][], int curri, int currj) {
        int sum = 0;

        //Mulige bevegelser for springeren gitt ved (i,j)
        int[] Posi = {-2, -2, 2, 2, 1, -1, 1, -1};
        int[] Posj = {1, -1, 1, -1, -2, -2, 2, 2};

        //Definerer possisjonen som tatt
        Board[curri][currj] = 1;


        //Sjekker om antall posisjoner som er tatt, og sammenligner det med antall tilgjengelige posisjoner
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (Board[i][j] != 0) {
                    sum++;
                }

                if (sum == n*n) {
                    Board[curri][currj] = sum;
                    for(int k = 0; k < Board.length ; k ++){
                        System.out.println();
                        for(int l = 0 ; l < Board[k].length ; l++){
                            System.out.print("  |  " + Board[k][l]  );
                        }
                        System.out.print(" |");
                        System.out.println();
                    }
                    return true;

                }
            }
        }

        //Definerer steg nmr
        Board[curri][currj] = sum;


        //Danner nye posisjoner ut ifra de forhåndsgitte bevegelsene
        for (int i = 0; i < Posi.length; i++) {
            int nyi = curri + Posi[i];
            int nyj = currj + Posj[i];

            //Sjekker om posisjonene er lovlig, og kaller på seg selv igjen
            if (nyi >= 0 && nyi < n && nyj >= 0 && nyj < n && Board[nyi][nyj] == 0) {
                boolean sR = sprinterRoute(n, Board, nyi, nyj);
                if (!sR) {
                   continue;
                }

                else if (sR) {
                    return true;
                }
            }
        }

        //Hvis den ikke finner flere steg vil tilstanden settes til 0 og returnere tilbake til nyi,nyj og se på neste steg
        Board[curri][currj] = 0;
        return false;

    }
}
