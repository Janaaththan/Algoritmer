package janaaththan.hiof.no;


import janaaththan.hiof.no.model.logarithmicSorting;
import janaaththan.hiof.no.model.radixSort2;
import janaaththan.hiof.no.model.sequentialSort;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("n: ");
        int n = scanner.nextInt();
        System.out.println("Metoder: ");
        System.out.println("1: Innstikksortering");
        System.out.println("2: Quicksort");
        System.out.println("3: Flettesortering");
        System.out.println("4: Radixsortering");
        System.out.print("Metode nummer: ");
        int metodeNmr = scanner.nextInt();

        int arr[];
        long time = 0;
        sequentialSort sS = new sequentialSort();
        logarithmicSorting lS = new logarithmicSorting();
        radixSort2 rS = new radixSort2();

        arr = new int[n];
        randomize(arr);

        switch (metodeNmr) {
            case 1:
                time = System.currentTimeMillis();
                sS.insertionSort(arr);
                time = System.currentTimeMillis() - time;
                float estimated1 = (float) time/(n * n);
                System.out.println("Estimated time: " + estimated1 );
                System.out.println("Real time: " + time);

                break;

            case 2:
                time = System.currentTimeMillis();
                lS.quickSort(arr, 0 ,n-1);
                time = System.currentTimeMillis() - time;
                float estimated2 = (float) (time/(n * Math.log(n)));
                System.out.println("Estimated time: " + estimated2 );
                System.out.println("Real time: " + time);
                break;

            case 3:
                time = System.currentTimeMillis();
                lS.mergeSort(arr, 0, n-1);
                time = System.currentTimeMillis() - time;
                float estimated3 = (float) (time/(n * Math.log(n)));
                System.out.println("Estimated time: " + estimated3 );
                System.out.println("Real time: " + time);
                break;

            case 4:
                time = System.currentTimeMillis();
                rS.sort(arr, (int) Math.log10(arr.length) + 1);
                float estimated4 = (float) time/n;
                System.out.println("Estimated time: " + estimated4 );
                System.out.println("Real time: " + time);
                break;

            default:
                System.out.println("Metode nummer må være mellom 1-4");

        }

    }

    public static void randomize(int A[]) {
        Random r = new Random();
        int n = A.length;
        for (int i = 0; i < n; i++)
            A[i] = r.nextInt(2 * n);
    }

}




