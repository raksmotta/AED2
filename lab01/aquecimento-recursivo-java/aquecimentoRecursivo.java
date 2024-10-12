import java.util.Scanner;

public class aquecimentoRecursivo {

    //método para contar e imprimir o numero de maiusculas em 1 linha lida
    public static int contarMaiusculasRecursivo(String myString, int pos) {

        int maiuscula = 0;

        //a condicao de base é satisfeita quando a posicao é igual ao numero de caracteres da string
        //como começamos a contar da posicao 0, o ultimo caractere da string esta na posicao (qtd de caracteres - 1)
        if (pos == myString.length()){

            return 0;

        }else{

            if ('A' <= myString.charAt(pos) && myString.charAt(pos) <= 'Z') {

                maiuscula = 1;

            }

            //vai incrementando +1 se a condição de a letra maiúscula for satisfeita. se não for, apenas soma 0
            return maiuscula + contarMaiusculasRecursivo(myString, pos + 1);

        }

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
                int m = contarMaiusculasRecursivo(myString, 0);

                System.out.println(m);

            }


        }

        scan.close();

    }
}