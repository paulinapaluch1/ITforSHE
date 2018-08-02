import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        int tab2[][]={{21,50,50},{22,90,90},{23,130,130},{24,220,260},{25,300,400},{26,400,600},{27,430,630},{28,460,660},
                {29,490,690},{30,520,720},{31,700,720},{32,900,1350},{33,990,1440},{34,1250,1800},{36,1500,2200}};
        int wartoscReki,etap;

        System.out.println("Wartosc punktowa reki: ");
        Scanner input=new Scanner(System.in);
        wartoscReki=input.nextInt();

        while(wartoscReki<21)
        {
            System.out.println("Wartosc nie moze byc mniejsza niz 21!");
            wartoscReki=input.nextInt();
        }

        System.out.println("Przed partią - 1, Po partii - 2");
        etap=input.nextInt();

        while((etap!=1)&&(etap!=2))
        {
            System.out.println("Wybierz 1 lub 2!");
            etap=input.nextInt();

        }





    }
}
