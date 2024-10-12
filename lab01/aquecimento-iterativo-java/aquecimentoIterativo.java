import java.util.Scanner;

public class aquecimentoIterativo {

    //método para contar e imprimir o numero de maiusculas em 1 linha lida
    public static void contarMaiusculas(String myString) {

        //obtendo o tamanho (numero de caracteres) da string. Obs.: strings em Java não terminam com '\0' como em C
        int tam = myString.length();

        int maiuscula = 0;

        // checa os caracteres um por um
        for (int pos = 0; pos < tam; pos++) {

            // letras maiusculas estão entre os caracteres A e Z da tabela ASCII
            if ('A' <= myString.charAt(pos) && myString.charAt(pos) <= 'Z') {

                maiuscula++;

            }

        }

        System.out.println(maiuscula);
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        String myString = "inicio";
        
        //condição para parar o loop no meio sem usar o break
        int fim = 0;

        while (fim == 0){
            
            //lê a linha toda (inclusive espaços em branco) e também consome o \n, não deixa restar nada no buffer
            myString = scan.nextLine();

            //se a palavra for FIM, a condição do loop não é mais satisfeita
            if (myString.equals("FIM")){
                fim = 1;
            }

            //apenas prossegue SE a palavra NAO for FIM. poderia ser um else também mas acho que assim fica mais claro de visualizar
            if (fim == 0){

                //chama a função que conta e imprime a qtd de maiusculas
                contarMaiusculas(myString);

            }


        }

        scan.close();

    }
}