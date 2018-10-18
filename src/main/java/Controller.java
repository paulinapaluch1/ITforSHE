import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    ObservableList<String> tricks = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7");


    @FXML
    private RadioButton afterMatch;

    @FXML
    private ToggleButton karo;

    @FXML
    private Button getValueOfHand;

    @FXML
    private Button matchResult;

    @FXML
    private ComboBox<String> amountOfBidedTricks;

    @FXML
    private TextField bloopers;

    @FXML
    private TextArea result;

    @FXML
    private ToggleGroup etap;

    @FXML
    private RadioButton beforeMatch;

    @FXML
    private ToggleButton pik;

    @FXML
    private CheckBox reveto;

    @FXML
    private ToggleButton trefl;

    @FXML
    private Button impResult;

    @FXML
    private TextField handValue;

    @FXML
    private ToggleButton kier;

    @FXML
    private ToggleGroup grupa;

    @FXML
    private CheckBox veto;

    @FXML
    private TextField overtricks;

    @FXML
    private ToggleButton BA;

    int valueOfHand;
    int matchScore;

    @FXML
    private void initialize() {
        amountOfBidedTricks.setItems(tricks);
        valueOfHand = 0;
        matchScore = 0;
        reveto.setDisable(true);

    }

    @FXML
    void calculateValueOfHand(ActionEvent event) {
        try {
            if (beforeMatch.isSelected()) Main.stageOfTheMatch = "przed";
            else if (afterMatch.isSelected()) Main.stageOfTheMatch = "po";
            else throw new WrongStageOfTheMatchException();

            if (Integer.parseInt(handValue.getText()) >= Main.MIN_HAND_VALUE)
                Main.handValue = Integer.parseInt(handValue.getText());
            else throw new IncorrectHandValueException();
            valueOfHand = Main.calculateValueOfHand();

            result.setText("Ta reka jest warta: " + valueOfHand);

        } catch (WrongStageOfTheMatchException | IncorrectHandValueException e) {
            result.setText(e.getMessage());
        } catch (NumberFormatException e) {
            result.setText("Wprowadzono niepoprawnie wartosc liczbowa!");
        }
    }

    @FXML
    void calculateMatchScore(ActionEvent event) {
        try {
            Main.amountOfBidedTricks = Integer.parseInt(amountOfBidedTricks.getValue());

            if (pik.isSelected()) Main.suit = Suit.PIK;
            else if (kier.isSelected()) Main.suit = Suit.KIER;
            else if (karo.isSelected()) Main.suit = Suit.KARO;
            else if (trefl.isSelected()) Main.suit = Suit.TREFL;
            else if (BA.isSelected()) Main.suit = Suit.BA;
            else throw new WrongSuitException();


            Main.amountOfBloopers = Integer.parseInt(bloopers.getText());
            Main.amountOfOvertricks = Integer.parseInt(bloopers.getText());


            if (veto.isSelected())
                Main.veto = 't';
            else Main.veto = 'n';

            if (reveto.isSelected())
                Main.reveto = 't';
            else Main.reveto = 'n';
            calculateValueOfHand(event);

        } catch (
                WrongSuitException e) {
            result.setText(e.getMessage());
        } catch (
                NumberFormatException e) {
            result.setText("Wprowadzono niepoprawnie wartosc liczbowa!");
        }

        matchScore = Main.calculateMatchScore();

        result.setText("Ilosc zdobytych punktow: " + matchScore);

    }

    @FXML
    void getResultInInternationalMatchPoints(ActionEvent event) {
        result.setText("Ilosc zdobytych punktow w miedzynarodowej notacji sportowej: " +
                Main.getResultInInternationalMatchPoints(matchScore, valueOfHand));
    }

    @FXML
    void changeDisabilityOfReveto(ActionEvent event) {
        reveto.setDisable(!reveto.isDisabled());
        if (veto.isSelected() == false)
            reveto.setSelected(false);

    }


    @FXML
    void showBloopers(ActionEvent event) {
        bloopers.setVisible(true);
        overtricks.setText("0");
        overtricks.setVisible(false);
    }

    @FXML
    void showOvertricks(ActionEvent event) {
        overtricks.setVisible(true);
        bloopers.setText("0");
        bloopers.setVisible(false);
    }


}
