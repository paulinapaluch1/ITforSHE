import java.util.Scanner;

public class Main {
    private static final int MIN_HAND_VALUE = 21;
    private static final int MAX_HAND_SIZE = 36;
    private static final int MIN_AMOUNT_OF_TRICKS = 1;
    private static final int MAX_AMOUNT_OF_TRICKS = 7;
    private int handValue;
    private int amountOfBidedTricks;
    private int amountOfBloopers;
    private int amountOfOvertricks = 0;
    private String stageOfTheMatch;
    private Suit suit;
    private char veto;
    private char reveto = 'n';

    private int tableOFPossibleHandValuesBeforeAndAfterMatch[][] = {{21, 50, 50}, {22, 90, 90}, {23, 130, 130}, {24, 220, 260},
            {25, 300, 400}, {26, 400, 600}, {27, 430, 630}, {28, 460, 660},
            {29, 490, 690}, {30, 520, 720}, {31, 700, 720}, {32, 900, 1350},
            {33, 990, 1440}, {34, 1250, 1800}, {35, 1400, 2100}, {36, 1500, 2200}};

    private int tableOfGameValuesBeforeMatch[][] = {{1, 50, 100, 200, 70, 140, 230, 80, 160, 520, 90, 180, 560},
            {2, 100, 300, 600, 90, 180, 560, 110, 470, 640, 120, 490, 680},
            {3, 150, 500, 1000, 110, 470, 640, 140, 530, 760, 400, 550, 800},
            {4, 200, 800, 1600, 130, 510, 720, 420, 590, 880, 430, 610, 920},
            {5, 250, 1100, 2200, 400, 550, 800, 450, 650, 1000, 460, 670, 1040},
            {6, 300, 1400, 2800, 920, 1090, 1380, 980, 1210, 1620, 990, 1230, 1660},
            {7, 350, 1700, 3400, 1440, 1630, 1960, 1510, 1770, 2240, 1520, 1790, 2280},
            {0, 0, 0, 0, 20, 100, 200, 30, 100, 200, 30, 100, 200}};

    private int tableOfGameValuesAfterMatch[][] = {{1, 100, 200, 400, 70, 140, 230, 80, 160, 720, 90, 180, 760},
            {2, 200, 500, 1000, 90, 180, 760, 110, 670, 840, 120, 690, 880},
            {3, 300, 800, 1600, 110, 670, 840, 140, 730, 960, 600, 750, 1000},
            {4, 400, 1100, 2200, 130, 710, 920, 620, 790, 1080, 630, 810, 1120},
            {5, 600, 1400, 2800, 600, 750, 1000, 650, 850, 1200, 660, 870, 1240},
            {6, 600, 1700, 3400, 1370, 1510, 1830, 1430, 1660, 2070, 1440, 1680, 2110},
            {7, 700, 2000, 4000, 2140, 2330, 2660, 2210, 2470, 2290, 2200, 2490, 2980},
            {0, 0, 0, 0, 20, 100, 200, 30, 400, 400, 30, 200, 400}

    };


    public static void main(String[] args) {

        Main obj = new Main();
        obj.getHandValueAndStage();
        System.out.println("Ta reka warta jest: " + obj.calculateValueOfHand());
        obj.getMatchResults();
        System.out.println("Ilosc zdobytych punktow: " + obj.calculateMatchScore());

    }

    private void getHandValueAndStage() {

        System.out.println("Wartosc punktowa reki: ");
        Scanner input = new Scanner(System.in);
        handValue = input.nextInt();

        while (handValue < MIN_HAND_VALUE) {
            System.out.println("Wartosc nie moze byc mniejsza niz " + MIN_HAND_VALUE + "!");
            handValue = input.nextInt();
        }


        System.out.println("Przed partią - wpisz 'przed', Po partii - wpisz 'po'");
        stageOfTheMatch = input.next();

        while (!stageOfTheMatch.toLowerCase().equals("przed") && !stageOfTheMatch.toLowerCase().equals("po")) {
            System.out.println("Przed partią - wpisz 'przed', Po partii - wpisz 'po'");
            stageOfTheMatch = input.next();
        }


    }


    private int calculateValueOfHand() {

        int j;
        if (stageOfTheMatch.toLowerCase().equals("przed")) j = 1;
        else j = 2;

        if (handValue <= MAX_HAND_SIZE)
            return tableOFPossibleHandValuesBeforeAndAfterMatch[handValue - MIN_HAND_VALUE][j];
        else
            return tableOFPossibleHandValuesBeforeAndAfterMatch[tableOFPossibleHandValuesBeforeAndAfterMatch.length - 1][j];

    }


    private void getMatchResults() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ilosc wylicytowanych lew: ");
        amountOfBidedTricks = scanner.nextInt();
        if (amountOfBidedTricks < MIN_AMOUNT_OF_TRICKS || amountOfBidedTricks > MAX_AMOUNT_OF_TRICKS) {
            throw new RuntimeException("Podano niepoprawna ilosc wylicytowanych lew");
        }

        System.out.println("Podaj kolor: ");
        String suit;
        suit = scanner.next();

        switch (suit.toLowerCase()) {
            case "trefl":
                this.suit = Suit.TREFL;
                break;
            case "karo":
                this.suit = Suit.KARO;
                break;
            case "kier":
                this.suit = Suit.KIER;
                break;
            case "pik":
                this.suit = Suit.PIK;
                break;
            case "ba":
                this.suit = Suit.BA;
                break;
            default:
                throw new RuntimeException("Niepoprawny kolor!");

        }

        System.out.println("Ilosc wpadek: ");
        amountOfBloopers = scanner.nextInt();
        if (amountOfBloopers == 0) {
            System.out.println("Ilosc nadrobek:");
            amountOfOvertricks = scanner.nextInt();
        }


        System.out.println("Czy byla kontra? t-tak, n-nie");
        veto = scanner.next().charAt(0);

        if (veto == 't') {
            System.out.println("Czy byla rekontra? t-tak, n-nie");
            reveto = scanner.next().charAt(0);
            if ((reveto != 't') && (reveto != 'n'))
                throw new RuntimeException("Podano niepoprawna litere");
        } else if (veto != 'n')
            throw new RuntimeException("Podano niepoprawna litere");


    }

    private int calculateMatchScore() {
        int startingCol;
        int offset = 0;
        int row = amountOfBidedTricks - 1;
        int sum = 0;
        int supplementaryTable[][] = tableOfGameValuesBeforeMatch;

        if (stageOfTheMatch.toLowerCase().equals("po")) supplementaryTable = tableOfGameValuesAfterMatch;

        if (suit == Suit.TREFL || suit == Suit.KARO) startingCol = 4;
        else if (suit == Suit.KIER || suit == Suit.PIK) startingCol = 7;
        else startingCol = 10;

        if (veto == 't') {
            if (reveto == 't') offset = 2;
            else offset = 1;
        }
        sum += supplementaryTable[row][startingCol + offset];
        if (amountOfBloopers > 0) sum -= amountOfBloopers * supplementaryTable[row][offset + 1];
        else if (amountOfOvertricks > 0)
            sum += amountOfOvertricks * supplementaryTable[MAX_AMOUNT_OF_TRICKS][startingCol + offset];


        return sum;
    }


}





