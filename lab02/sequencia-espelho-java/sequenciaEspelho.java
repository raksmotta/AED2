import java.util.Scanner;

public class sequenciaEspelho {

    public static void espelhar(int inicio, int fim) {


        //cria uma string sem caracteres e com tamanho adaptável
        StringBuilder result = new StringBuilder();

        // imprimir a primeira metade E guardar como caracteres numa string
        for (int a = inicio; a <= fim; a++) {

            result.append(a);
            System.out.print(a);

        }

        // imprimir a segunda metade a partir da string
        for (int i = result.length() - 1; i >=0; i--) {

            System.out.print(result.charAt(i));

        }

        // pular linha depois que terminar11
        System.out.println("");

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {

            int start = sc.nextInt();

            int end = sc.nextInt();

            // chama o método
            espelhar(start, end);

        }

        sc.close();

    }

}
