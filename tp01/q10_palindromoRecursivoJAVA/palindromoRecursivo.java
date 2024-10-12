import java.util.Scanner;

public class palindromoRecursivo {

    public static boolean isPalindromoRecursivo (String s, int start, int end){

        //quando chegar no meio, é porque já analisou todos os pares "de dentro para fora".
        if (start == s.length()/2){
            return true;
        }

        //recursividade baseada em comparar 2 caracteres, em posições espelhadas, entre si por vez. se forem diferentes, já não é palíndromo, podemos interromper a função, sem analisar adiante, e retornar falso. por outro lado, se forem iguais, mudamos as posições de início e fim e passamos para avaliar o próximo par.
        if (s.charAt(start) != s.charAt(end)){
            return false;
        }

        return isPalindromoRecursivo(s, start+1, end-1);
        
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

                if(isPalindromoRecursivo(myString, 0, myString.length()-1)){

                    System.out.println("SIM");

                }else{

                    System.out.println("NAO");

                }

            }
        }

        scan.close();

    }
    
}
