import java.util.Scanner;

public class Main {
    private int tab2[][] = {{21, 50, 50}, {22, 90, 90}, {23, 130, 130}, {24, 220, 260}, {25, 300, 400}, {26, 400, 600}, {27, 430, 630}, {28, 460, 660},
            {29, 490, 690}, {30, 520, 720}, {31, 700, 720}, {32, 900, 1350}, {33, 990, 1440}, {34, 1250, 1800}, {36, 1500, 2200}};
    private int wartoscReki;
    private String etap;

    public static void main(String[] args) {

        Main obj = new Main();
        obj.pobranieDanych(obj);



    }

    public void pobranieDanych(Main obj){

        System.out.println("Wartosc punktowa reki: ");
        Scanner input = new Scanner(System.in);
        obj.wartoscReki = input.nextInt();

        while (obj.wartoscReki < 21) {
            System.out.println("Wartosc nie moze byc mniejsza niz 21!");
            obj.wartoscReki = input.nextInt();
        }


        System.out.println("Przed partią - wpisz 'przed', Po partii - wpisz 'po'");
        obj.etap = input.next();

        switch(obj.etap.toLowerCase()){

            case "przed":
            case "po":
              obj.etap=etap.toLowerCase();
                break;

            default:
                System.out.println("Przed partią - wpisz 'przed', Po partii - wpisz 'po'");
                obj.etap = input.next();
                break;
        }



    }
}
