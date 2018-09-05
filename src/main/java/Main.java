import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int MIN_HAND_VALUE = 21;
    private static final int MAX_HAND_SIZE = 36;
    private static final int MIN_AMOUNT_OF_TRICKS = 1;
    private static final int MAX_AMOUNT_OF_TRICKS = 7;
    private static final int COL_WITH_IMP_RESULT = 2;
    private static final int LAST_ROW_IN_IMP_TABLE = 23;
    private static final int MINIMAL_RANGE_OFFSET = 1;
    private static final int MAXIMUM_RANGE_OFFSET = 2;
    private int handValue;
    private int amountOfBidedTricks;
    private int amountOfBloopers;
    private int amountOfOvertricks = 0;
    private int margin;
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

    private int tableOfIMP[][] = {{20, 40, 1}, {50, 80, 2}, {90, 120, 3}, {130, 160, 4}, {170, 210, 5},
            {220, 260, 6}, {270, 310, 7}, {320, 360, 8}, {370, 420, 9}, {430, 490, 10}, {500, 590, 11},
            {600, 740, 12}, {750, 890, 13}, {900, 1090, 14}, {1100, 1290, 15}, {1300, 1490, 16},
            {1500, 1740, 17}, {1750, 1990, 18}, {2000, 2240, 19}, {2250, 2490, 20}, {2500, 2990, 21},
            {3000, 3490, 22}, {3500, 3990, 23}, {0, 4000, 24}};


    public static void main(String[] args) {
        int valueOfHand;
        int matchScore;
        int scoreIMP;
        Main obj = new Main();
        obj.getHandValueAndStage();
        valueOfHand = obj.calculateValueOfHand();
        System.out.println("Ta reka warta jest: " + valueOfHand);
        obj.getMatchResults();
        matchScore = obj.calculateMatchScore();
        System.out.println("Ilosc zdobytych punktow: " + matchScore);
        scoreIMP = obj.getResultInInternationalMatchPoints(matchScore, valueOfHand);
        System.out.println("Ilosc zdobytych punktow w miedzynarodowej notacji sportowej: " + scoreIMP);


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
        if (stageOfTheMatch.toLowerCase().equals("przed"))
            j = 1;
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
        if (amountOfBidedTricks < MIN_AMOUNT_OF_TRICKS || amountOfBidedTricks > MAX_AMOUNT_OF_TRICKS)
            throw new RuntimeException("Podano niepoprawna ilosc wylicytowanych lew");


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

        if (stageOfTheMatch.toLowerCase().equals("po"))
            supplementaryTable = tableOfGameValuesAfterMatch;

        if (suit == Suit.TREFL || suit == Suit.KARO)
            startingCol = 4;
        else if (suit == Suit.KIER || suit == Suit.PIK)
            startingCol = 7;
        else startingCol = 10;

        if (veto == 't') {
            if (reveto == 't')
                offset = 2;
            else offset = 1;
        }

        sum += supplementaryTable[row][startingCol + offset];
        if (amountOfBloopers > 0)
            sum -= amountOfBloopers * supplementaryTable[row][offset + 1];
        else if (amountOfOvertricks > 0)
            sum += amountOfOvertricks * supplementaryTable[MAX_AMOUNT_OF_TRICKS][startingCol + offset];


        return sum;
    }

    private int getResultInInternationalMatchPoints(int matchScore, int valueOfHand) {
        margin = matchScore - valueOfHand;
        List<RangeOfIMP> listOfRanges = new ArrayList<>();
        int scoreIMP = 0;

        for (int i = 0; i <= LAST_ROW_IN_IMP_TABLE; i++) {
            RangeOfIMP range = new RangeOfIMP(tableOfIMP[i][COL_WITH_IMP_RESULT - MAXIMUM_RANGE_OFFSET], tableOfIMP[i][COL_WITH_IMP_RESULT - MINIMAL_RANGE_OFFSET], tableOfIMP[i][COL_WITH_IMP_RESULT]);
            listOfRanges.add(range);
        }

        if (margin < (listOfRanges.get(0).lowestValue))
            scoreIMP = 0;
        else if (margin > listOfRanges.get(listOfRanges.size() - 1).highestValue)
            scoreIMP = listOfRanges.get(listOfRanges.size() - 1).points;
        else

            for (RangeOfIMP range : listOfRanges) {
                if (range.checkIfRangeIsRight(margin) != -1) {
                    scoreIMP = range.checkIfRangeIsRight(margin);
                    break;
                }

            }

        return scoreIMP;
    }


}





