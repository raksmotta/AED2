import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.io.IOException;

public class arquivov2 {

    public static int imprimirArquivoTexto (){

        Scanner input = new Scanner(System.in);

        PrintWriter pw = null;

        int x = 0;

        try{

            //criando objeto de arquivo
            File file = new File("saida.txt"); 
            
            //criando objeto para escrever no arquivo
            //o PW adiciona funcionalidades à classe FW. Enquanto o FW só aceita strings como parâmetro, o PW converte diversos tipos de variáveis para string antes de imprimir no arquivo
            pw = new PrintWriter(new FileWriter(file));

            //consome a linha inteira e depois transforma em inteiro, garante que o \n também seja consumido
            int qtd = Integer.valueOf(input.nextLine( ));

            x = qtd;

            double real = 1.1;

            //lê a qtd de reais do teclado e imprime no arquivo
            for (int i = 0; i<qtd; i++){

                real = Double.valueOf(input.nextLine( ));

                //imprime o número real no arquivo e já salta uma linha
                pw.println(real);

            }

        }catch (IOException e) {

            //retorna -1 para indicar erro
            return -1;

        } finally { 

            //fechando o Scanner e o PrintWriter
            if (pw != null) {
                pw.flush();
                pw.close(); 
            }

            input.close(); 
        }

        //retorna a quantidade de reais impressa se tudo ocorrer corretamente
        return x;

    }

    //No final das contas, achei péssimo tentar fazer a questão usando a classe RandomAccessFile. Achei muito melhor ler a linha que eu quero através de uma função que usa a classe Scanner para ler do arquivo
    public static void lerLinhaArquivo(String filePath, int lineNumber) {

        try (Scanner scanner = new Scanner(new File(filePath))) {

            //linha atual onde o ponteiro está lendo
            int currentLine = 1;

            //uso essa variável para interromper o ciclo do while quando já tiver lido a linha que quero (para não precisar usar break)
            boolean linha = false;

            //uso para remover o '.0' depois do número ao imprimir e a saída ficar igual a do verde
            DecimalFormat format = new DecimalFormat("0.#");
    
            while (scanner.hasNextLine() && !linha) {

                Double x = Double.valueOf(scanner.nextLine());

                //SE estou na linha que quero (passada como parâmetro), imprimo o número real que li e saio do loop
                if (currentLine == lineNumber) {

                    //se o número dividido por 1 tiver resto 0, quer dizer que é um inteiro
                    if (x % 1 == 0){
                        System.out.println(format.format(x));
                    }else{
                        System.out.println(x);
                    }
                    

                    linha = true;

                }

                //SE não estou na linha que quero ainda, o scanner.hasNextLine(), que é o motor desse loop, tenta ler a próxima linha e conto a linha que li
                currentLine++;
            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }

    }


    public static void main(String[] args) {

        int a = imprimirArquivoTexto();

        //System.out.println("a= " + a);

        for (int i = a; i > 0; i--){

            lerLinhaArquivo("saida.txt", i);

        }

        //System.out.println("b= " + b);

    }

}
