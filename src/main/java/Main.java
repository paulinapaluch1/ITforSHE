import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {
    static final int MIN_HAND_VALUE = 21;
    private static final int MAX_HAND_SIZE = 36;
    private static final int MIN_AMOUNT_OF_TRICKS = 1;
    private static final int MAX_AMOUNT_OF_TRICKS = 7;
    private static final int COL_WITH_IMP_RESULT = 2;
    private static final int LAST_ROW_IN_IMP_TABLE = 23;
    private static final int MINIMAL_RANGE_OFFSET = 1;
    private static final int MAXIMUM_RANGE_OFFSET = 2;
    private static final int ROWS_IN_IMP_TABLE = 24;
    private static final int COLS_IN_INP_TABLE = 3;
    private static final int ROWS_IN_HAND_VALUES_TABLE = 16;
    private static final int COLS_IN_HAND_VALUE_TABLE = 3;
    private static final int ROWS_IN_GAMES_TABLES = 8;
    private static final int COLS_IN_GAMES_TABLES = 13;
    private static final String HAND_VALUES_FILE_PATH = "src/main/resources/HandValues.csv";
    private static final String IMP_VALUES_FILE_PATH = "src/main/resources/IMPValues.csv";
    private static final String GAME_VALUES_BEFORE_MATH_FILE_PATH = "src/main/resources/GameValuesBeforeMatch.csv";
    private static final String GAME_VALUES_AFTER_MATH_FILE_PATH = "src/main/resources/GameValuesAfterMatch.csv";

    static int handValue;
    static int amountOfBidedTricks;
    static int amountOfBloopers;
    static int amountOfOvertricks = 0;
    int margin;
    static String stageOfTheMatch;
    static Suit suit;
    static char veto;
    static char reveto = 'n';
    TableReader reader;
    static int tableOFPossibleHandValuesBeforeAndAfterMatch[][];
    static int[][] tableOfGameValuesBeforeMatch;
    static int[][] tableOfGameValuesAfterMatch;
    static int tableOfIMP[][];


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/sample.fxml"));
        primaryStage.setTitle("Wyliczanie punktacji sportowej na podstawie zapisu miltonowego");
        primaryStage.setScene(new Scene(root, 790, 523));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Main obj = new Main();
        obj.reader = new TableReader();
        obj.tableOFPossibleHandValuesBeforeAndAfterMatch = obj.reader.readTableFromFile(HAND_VALUES_FILE_PATH, ROWS_IN_HAND_VALUES_TABLE, COLS_IN_HAND_VALUE_TABLE);
        obj.tableOfIMP = obj.reader.readTableFromFile(IMP_VALUES_FILE_PATH, ROWS_IN_IMP_TABLE, COLS_IN_INP_TABLE);
        obj.tableOfGameValuesBeforeMatch = obj.reader.readTableFromFile(GAME_VALUES_BEFORE_MATH_FILE_PATH, ROWS_IN_GAMES_TABLES, COLS_IN_GAMES_TABLES);
        obj.tableOfGameValuesAfterMatch = obj.reader.readTableFromFile(GAME_VALUES_AFTER_MATH_FILE_PATH, ROWS_IN_GAMES_TABLES, COLS_IN_GAMES_TABLES);
        launch(args);
        int valueOfHand;
        int matchScore;
        int scoreIMP;

        try {
            obj.getHandValueAndStage();
            valueOfHand = obj.calculateValueOfHand();
            System.out.println("Ta reka warta jest: " + valueOfHand);
            obj.getMatchResults();
            matchScore = obj.calculateMatchScore();
            System.out.println("Ilosc zdobytych punktow: " + matchScore);
            scoreIMP = obj.getResultInInternationalMatchPoints(matchScore, valueOfHand);
            System.out.println("Ilosc zdobytych punktow w miedzynarodowej notacji sportowej: " + scoreIMP);
        } catch (IncorrectHandValueException | WrongStageOfTheMatchException | WrongSuitException
                | InvalidAmountOfTricksException | ImproperCharacterException e) {
            System.err.println(e.getMessage());
        }

    }

    private void getHandValueAndStage() {

        System.out.println("Wartosc punktowa reki: ");
        Scanner input = new Scanner(System.in);
        handValue = input.nextInt();

        if (handValue < MIN_HAND_VALUE)
            throw new IncorrectHandValueException();

        System.out.println("Przed partią - wpisz 'przed', Po partii - wpisz 'po'");
        stageOfTheMatch = input.next();

        if (!stageOfTheMatch.toLowerCase().equals("przed") && !stageOfTheMatch.toLowerCase().equals("po"))
            throw new WrongStageOfTheMatchException();


    }


    public static int calculateValueOfHand() {

        int j;
        if (stageOfTheMatch.toLowerCase().equals("przed"))
            j = 1;
        else j = 2;

        if (handValue <= MAX_HAND_SIZE)
            return tableOFPossibleHandValuesBeforeAndAfterMatch[handValue - MIN_HAND_VALUE][j];
        else
            return tableOFPossibleHandValuesBeforeAndAfterMatch[tableOFPossibleHandValuesBeforeAndAfterMatch.length - 1][j];

    }


    void getMatchResults() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ilosc wylicytowanych lew: ");
        amountOfBidedTricks = scanner.nextInt();
        if (amountOfBidedTricks < MIN_AMOUNT_OF_TRICKS || amountOfBidedTricks > MAX_AMOUNT_OF_TRICKS)
            throw new InvalidAmountOfTricksException();


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
                throw new WrongSuitException();

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
                throw new ImproperCharacterException();
        } else if (veto != 'n')
            throw new ImproperCharacterException();


    }

    static int calculateMatchScore() {
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





