import java.util.Scanner;

public class palindromo {
    
    public static boolean isPalindromo (String s){

        int end = s.length() - 1;

        for (int start = 0; start < s.length() - 1; start++){

            //posições espelhadas na string precisam ser ocupadas exatamente pelo mesmo caractere ('a' e 'A' são considerados diferentes, assim como 'a' e ' ' também)
            if (s.charAt(start) != s.charAt(end)){

                return false;

            }else{

                end --;

            }

        }

        //caso saia do for sem retornar falso, ou seja, sem comparar posições que tenham caracteres diferentes, temos um palíndromo e retornamos true
        return true;

    }

    public static void main(String[] args) {
        
        Scanner scan = new Scanner (System.in);

        //variável que será usada como condição para parar o loop no meio sem usar o break
        int fim = 0;

        String myString = "inicio";

        while (fim == 0){

            myString = scan.nextLine();

            //se a palavra for FIM, incrementamos a variável fim e a condição do loop não é mais satisfeita
            if (myString.equals("FIM")){
                fim = 1;
            }

            //só chama a função se a palavra NÃO for FIM
            if (fim == 0){

                if(isPalindromo(myString)){

                    System.out.println("SIM");

                }else{

                    System.out.println("NAO");

                }

            }
        }

        scan.close();

    }
}
