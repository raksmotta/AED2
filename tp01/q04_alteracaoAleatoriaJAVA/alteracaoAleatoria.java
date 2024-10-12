import java.util.Random;

public class alteracaoAleatoria {

    public static String alterar (String myString, Random rand){

        //o método Math.abs() garante que o valor gerado seja não-negativo. em Java, o método nextInt() da classe Random pode retornar qualquer valor no intervalo de inteiros possíveis, incluindo números negativos.
        char m1 = (char) ('a' + (Math.abs(rand.nextInt()) % 26));
        //fazemos o caractere 'a' minúsculo somado ao RESTO de um número aleatório qualquer DIVIDIDO por 26. isso garante que teremos números entre 0 e 25. isso é importante para nos mantermos no range do alfabeto minúsculo: 'a' (posição zero) + 25 posições do alfabeto = 'z', que é a última letra do alfabeto na posição 25.
        //também poderia fazer levando em conta que o código ASCII para 'a' é 97 e para 'z' é 122, mas na hora da prova eu não saberia disso.
        //depois de somar, temos que fazer o type casting, pois quando somamos um caractere a um inteiro, temos um inteiro.

        char m2 = (char) ('a' + (Math.abs(rand.nextInt()) % 26));

        //System.out.println("Letra aleatoria m1: " + m1);
        //System.out.println("Letra aleatoria m2: " + m2);

        //criando uma StringBuilder com conteúdo idêntico ao da string recebida como parâmetro
        StringBuilder resultado = new StringBuilder(myString);

        //iterando sobre a string original e buscando ocorrências da primeira letra
        for (int i = 0; i < myString.length(); i++){

            //SE encontrar a letra 1 na posição i, a substitui pela letra 2 nessa posição
            if (resultado.charAt(i) == m1){

                resultado.setCharAt(i, m2);

            }

        }

        //convertendo de volta para string para retornar
        String retornar = resultado.toString();

        return retornar;

    }

    public static void main(String[] args) {
        
        MyIO.setCharset("ISO-8859-1");

        //criando um objeto da classe Random e definindo a seed como 4 para a correção automática
        Random rand = new Random();
        rand.setSeed(4);
        //exemplo de uso: int randInt = rand.nextInt(50); //inteiro entre 0 e 49

        //inicializando a string com uma palavra qualquer diferente de FIM
        String myString = "inicio";

        //variável de condição para o loop se encerrar sem o uso de break
        int fim = 0;

        while (fim == 0){

            myString = MyIO.readLine();

            //para sair do while
            if (myString.equals("FIM")){
                fim = 1;
            }

            //se a palavra lida NÃO for fim, prosseguimos
            if (fim == 0){

                //passo o Random como parâmetro ao invés de declarar dentro da função para manter consistência
                String alterada = alterar(myString, rand);

                MyIO.println(alterada);
            }

        }


    }
    
}
