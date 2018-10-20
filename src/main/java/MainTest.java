import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainTest {


    @Before
    public void setUp() {
        Main.reader = new TableReader();
        Main.tableOFPossibleHandValuesBeforeAndAfterMatch = Main.reader.readTableFromFile(Main.HAND_VALUES_FILE_PATH, Main.ROWS_IN_HAND_VALUES_TABLE, Main.COLS_IN_HAND_VALUE_TABLE);
        Main.tableOfIMP = Main.reader.readTableFromFile(Main.IMP_VALUES_FILE_PATH, Main.ROWS_IN_IMP_TABLE, Main.COLS_IN_INP_TABLE);
        Main.tableOfGameValuesBeforeMatch = Main.reader.readTableFromFile(Main.GAME_VALUES_BEFORE_MATH_FILE_PATH, Main.ROWS_IN_GAMES_TABLES, Main.COLS_IN_GAMES_TABLES);
        Main.tableOfGameValuesAfterMatch = Main.reader.readTableFromFile(Main.GAME_VALUES_AFTER_MATH_FILE_PATH, Main.ROWS_IN_GAMES_TABLES, Main.COLS_IN_GAMES_TABLES);
    }


    @Test
    public void shouldCalculateCorrectValueOfHand() {
        Main.handValue = 21;
        Main.stageOfTheMatch = "przed";
        Assert.assertEquals(50, Main.calculateValueOfHand());
    }


    @Test
    public void shouldCalculateCorrectMatchScore() {
        Main.stageOfTheMatch = "przed";
        Main.amountOfBidedTricks = 3;
        Main.suit = Suit.TREFL;
        Main.amountOfBloopers = 0;
        Main.amountOfOvertricks = 1;
        Main.veto = 't';
        Main.reveto = 't';
        Assert.assertEquals(840, Main.calculateMatchScore());

    }

    @org.junit.Test
    public void shouldCalculateCorrectResultInInternationalMatchPoints() {
        Assert.assertEquals(14, Main.getResultInInternationalMatchPoints(2000, 1000));

    }
}