import java.util.Scanner;

public class balancoParenteses {

    public static Boolean estaBalanceado (String s){

        //conta a quantidade de parenteses "que abrem" ((((
        int pA = 0;
        
        //iteramos sobre a string. analisamos quando achamos um parenteses "que fecha" ))))
        for (int i = 0; i < s.length(); i ++){

            if (s.charAt(i) == '('){

                pA++;

            }

            if (s.charAt(i) == ')'){

                //se nao tiver pelo menos 1 parenteses aberto contabilizado para que o parenteses fechado encontrado possa completar, ja podemos retornar falso
                if (pA < 1){

                    return false;

                }else{

                    //diminui um na contagem de parenteses abertos disponiveis para serem fechados, um deles "ja achou a sua metade"
                    pA --;

                }

            }

        }

        //se ao chegar ao final da string nao tivermos retornado falso ainda, sabemos que nao há parenteses fechados extras. porem podem haver parenteses abertos que nao foram fechados. portanto, só retornaremos true se a quantidade de parenteses abertos disponiveis for 0, o que significa que encontramos o par de cada parenteses aberto contabilizado
        if (pA == 0){

            return true;

        }else{

            return false;

        }

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);

        String myString = "inicio";
    
        while (!(myString.equals("FIM"))){

            myString = sc.nextLine();

            if(!(myString.equals("FIM"))){

                if(estaBalanceado(myString)){

                    System.out.println("correto");
    
                }else{
    
                    System.out.println("incorreto");
    
                }

            }
    
        }

        sc.close();
        
    }


    
}
