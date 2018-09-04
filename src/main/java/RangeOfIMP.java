public class RangeOfIMP {


    int lowestValue;
    int highestValue;
    int points;

    public RangeOfIMP(int lowestValue, int highestValue, int points) {
        this.lowestValue = lowestValue;
        this.highestValue = highestValue;
        this.points = points;
    }

    public int checkIfRangeIsRight(int margin) {

        if (margin > lowestValue && margin < highestValue)
            return points;
        else return -1;


    }
}