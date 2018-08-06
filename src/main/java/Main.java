import java.util.Scanner;

public class Main {
    private int tab2[][] = {{21, 50, 50}, {22, 90, 90}, {23, 130, 130}, {24, 220, 260}, {25, 300, 400}, {26, 400, 600}, {27, 430, 630}, {28, 460, 660},
            {29, 490, 690}, {30, 520, 720}, {31, 700, 720}, {32, 900, 1350}, {33, 990, 1440}, {34, 1250, 1800}, {35, 1400, 2100}, {36, 1500, 2200}};
    private int wartoscReki;
    private String etap;
    private static final int  MIN_WARTOSC_REKI=21;
    private static final int PRZELOMOWA_WARTOSC_REKI=36;

    public static void main(String[] args) {

        Main obj = new Main();
        obj.pobranieDanych();
        System.out.println("Ta reka warta jest: " + obj.obliczWartoscReki());

    }

    private void pobranieDanych() {

        System.out.println("Wartosc punktowa reki: ");
        Scanner input = new Scanner(System.in);
        wartoscReki = input.nextInt();

        while (wartoscReki < MIN_WARTOSC_REKI) {
            System.out.println("Wartosc nie moze byc mniejsza niz "+MIN_WARTOSC_REKI+"!");
            wartoscReki = input.nextInt();
        }


        System.out.println("Przed partią - wpisz 'przed', Po partii - wpisz 'po'");
        etap = input.next();

        while (!etap.toLowerCase().equals("przed") && !etap.toLowerCase().equals("po")) {
            System.out.println("Przed partią - wpisz 'przed', Po partii - wpisz 'po'");
            etap = input.next();
        }


    }


    private int obliczWartoscReki() {

        int j;
        if (etap.toLowerCase().equals("przed")) j = 1;
        else j = 2;

        if (wartoscReki <= PRZELOMOWA_WARTOSC_REKI) return tab2[wartoscReki - MIN_WARTOSC_REKI][j];
        else return tab2[tab2.length-1][j];

    }


}
