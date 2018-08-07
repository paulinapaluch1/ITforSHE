import java.util.Scanner;

public class Main {
    private int tab2[][] = {{21, 50, 50}, {22, 90, 90}, {23, 130, 130}, {24, 220, 260}, {25, 300, 400}, {26, 400, 600}, {27, 430, 630}, {28, 460, 660},
            {29, 490, 690}, {30, 520, 720}, {31, 700, 720}, {32, 900, 1350}, {33, 990, 1440}, {34, 1250, 1800}, {35, 1400, 2100}, {36, 1500, 2200}};
    private int wartoscReki, iloscWylicytowanychLew, iloscWpadek, iloscNadrobek;
    private String etap;
    private static final int MIN_WARTOSC_REKI = 21;
    private static final int PRZELOMOWA_WARTOSC_REKI = 36;
    private static final int MIN_ILOSC_LEW = 1;
    private static final int MAX_ILOSC_LEW = 7;
    private int tab3[][] = {{1, 50, 100, 200, 70, 140, 230, 80, 160, 520, 90, 180, 560},
            {2, 100, 300, 600, 90, 180, 560, 110, 470, 640, 120, 490, 680},
            {3, 150, 500, 1000, 110, 470, 640, 140, 530, 760, 400, 550, 800},
            {4, 200, 800, 1600, 130, 510, 720, 420, 590, 880, 430, 610, 920},
            {5, 250, 1100, 2200, 400, 550, 800, 450, 650, 1000, 460, 670, 1040},
            {6, 300, 1400, 2800, 920, 1090, 1380, 980, 1210, 1620, 990, 1230, 1660},
            {7, 350, 1700, 3400, 1440, 1630, 1960, 1510, 1770, 2240, 1520, 1790, 2280},
            {0, 0, 0, 0, 20, 100, 200, 30, 100, 200, 30, 100, 200}};

    private char kontra, rekontra;

    public enum Kolor {
        TREFL, KARO, KIER, PIK
    }

    Kolor kolor;

    public static void main(String[] args) {

        Main obj = new Main();
        // obj.pobranieDanych();
        // System.out.println("Ta reka warta jest: " + obj.obliczWartoscReki());
        obj.pobierzWynikiPartii();
    }

    private void pobranieDanych() {

        System.out.println("Wartosc punktowa reki: ");
        Scanner input = new Scanner(System.in);
        wartoscReki = input.nextInt();

        while (wartoscReki < MIN_WARTOSC_REKI) {
            System.out.println("Wartosc nie moze byc mniejsza niz " + MIN_WARTOSC_REKI + "!");
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
        else return tab2[tab2.length - 1][j];

    }


    private void pobierzWynikiPartii() {

        Scanner s = new Scanner(System.in);
        System.out.println("Ilosc wylicytowanych lew: ");
        iloscWylicytowanychLew = s.nextInt();
        while ((iloscWylicytowanychLew < MIN_ILOSC_LEW) || (iloscWylicytowanychLew > MAX_ILOSC_LEW)) {
            System.out.println("Ilosc wylicytowanych lew nalezy do przedzialu 1-7! ");
            iloscWylicytowanychLew = s.nextInt();
        }

        System.out.println("Podaj kolor: ");
        String color;
        color = s.next();

        switch (color.toLowerCase()) {
            case "trefl":
                kolor = Kolor.TREFL;
                break;
            case "karo":
                kolor = Kolor.KARO;
                break;
            case "kier":
                kolor = Kolor.KIER;
                break;
            case "pik":
                kolor = Kolor.PIK;
                break;
            default:
                System.out.println("Bledne dane");
        }

        System.out.println("Ilosc wpadek: ");
        iloscWpadek = s.nextInt();
        if (iloscWpadek == 0) {
            System.out.println("Ilosc nadrobek:");
            iloscNadrobek = s.nextInt();

        } else iloscNadrobek = 0;


        System.out.println("Czy byla kontra? t-tak, n-nie");
        kontra = s.next().charAt(0);
        if (kontra == 't') {
            System.out.println("Czy byla rekntra? t-tak, n-nie");
            rekontra = s.next().charAt(0);
        } else rekontra = 'n';


    }

}
