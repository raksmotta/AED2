import java.util.Scanner;

public class placa{

    public static Boolean is_brasil (String placa){

        char temp;

        Boolean result = true;

        for (int a=0; a < 3; a++){

            temp = placa.charAt(a);

            if (Character.isUpperCase(temp)){
                result = true;
            }else{
                return false;
            }

        }

        if (placa.charAt(3)!= '-') return false;

        for (int b = 4; b < 8; b++){

            temp = placa.charAt(b);

            if (Character.isDigit(temp)){
                result = true;
            }else{
                return false;
            }

        }

        return result;
        //se chegar ate o final sem entrar em condicao que retorna falso. temos ctz q a placa eh br

    }

    public static Boolean is_merco (String placa){

        char temp;

        Boolean result;

        for (int a=0; a < 3; a++){

            temp = placa.charAt(a);

            if (Character.isUpperCase(temp)){
                result = true;
            }else{
                return false;
            }

        }

        temp = placa.charAt(3);

        if (Character.isDigit(temp)){
            result = true;
        }else{
            return false;
        }

        temp = placa.charAt(4);

        if (Character.isUpperCase(temp)){
            result = true;
        }else{
            return false;
        }

        for (int b = 5; b < 7; b++){

            temp = placa.charAt(b);

            if (Character.isDigit(temp)){
                result = true;
            }else{
                return false;
            }

        }

        return result;

    }

    public static void main (String[] args){

        Scanner sc = new Scanner(System.in);

        String placa = sc.nextLine();

        int tam = placa.length();

        //se o tam nao for 7 nem 8, ja eh falsa
        if ((tam != 7) && (tam != 8)){

            System.out.println("0");

        }else if(tam == 8){

            //se tiver 8, tem que ser brasileira
            if (is_brasil(placa)){
                System.out.println("1");
            }else{
                System.out.println("0");
            }

        }else if(tam == 7){

            //se tiver 7, tem que ser mercosul
            if (is_merco(placa)){
                System.out.println("2");
            }else{
                System.out.println("0");
            }

        }

        

    }


}