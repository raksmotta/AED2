import java.util.Scanner;

public class isRecursivo {

    public static boolean isVogaisRecursivo(String s, int pos) {

        // a condição de base para a função parar de chamar a si mesma é ter percorrido
        // todos os caracteres da string. nesse caso, retorna true, pois analisou todos
        // os caracteres da string e nenhum NÃO era vogal
        if (pos == s.length()) {

            return true;

        }

        // a recursividade da função está baseada em pular de um caractere para outro da
        // string, analisando um por vez
        char c = s.charAt(pos);

        // se o caractere NÃO é a, nem e, nem i, nem o, nem u (nem a versão maiúscula
        // dessas letras), então já podemos interromper a recursão e retornar falso, não
        // precisamos analisar mais caracteres.
        if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u' && c != 'A' && c != 'E' && c != 'I' && c != 'O'
                && c != 'U') {

            return false;

        }

        // agora, se o caractere na posição pos de fato for uma vogal, precisamos chamar
        // a função novamente para analisar o próximo caractere (até que uma NÃO-vogal
        // seja encontrada ou até que a string termine)

        return isVogaisRecursivo(s, pos + 1);

    }

    public static boolean isConsoantesRecursivo(String s, int pos) {

        // mesma lógica de base da função das vogais
        if (pos == s.length()) {

            return true;

        }

        char c = s.charAt(pos);

        // se encontrar uma vogal, já retorna falso, não prossegue a análise
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {

            return false;

        }

        // mesma coisa se encontrar um algarismo
        if ('0' <= c && c <= '9') {

            return false;

        }

        // se não encontrar uma letra vogal ou um algarismo, continua analisando e se
        // chama para analisar o próximo caractere na posição adjascente
        return isConsoantesRecursivo(s, pos + 1);

    }

    public static boolean isInteiroRecursivo(String s, int pos) {

        // terminou de percorrer tudo sem retornar falso, obviamente só resta retornar true
        if (pos == s.length()) {

            return true;

        }

        char c = s.charAt(pos);

        if (!('0' <= c && c <= '9')) {

            return false;

        }

        return isInteiroRecursivo(s, pos + 1);

    }

    public static boolean isRealRecursivo(String s, int pos, int virgulasOuPontos) {

        if (pos == s.length()) {

            //OU SEJA: se, ao final da análise da string inteira, for detectado que o número de vírgulas/pontos é 0 ou 1, retorna TRUE
            //no entanto, se o número de vírgulas/pontos for MAIOR que 1, então NÃO é um número real e retorna false
            return virgulasOuPontos <= 1;

        }

        char c = s.charAt(pos);

        // um numero real pode ser composto por algarismos de 0 a 9 e uma vírgula ou
        // ponto para separar as casas decimais. caso o caractere analisado não seja
        // nenhuma dessas opções, pode retornar falso
        if (!(('0' <= c && c <= '9') || c == '.' || c == ',')) {

            return false;

        }

        if (c == '.' || c == ',') {
            virgulasOuPontos++;
        }

        //se mais de uma vírgula/ponto for encontrado, já retorna falso
        if (virgulasOuPontos > 1) {
            return false;
        }

        return isRealRecursivo(s, pos + 1, virgulasOuPontos);

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

                int tam = myString.length();

                //chama as funcoes e imprime baseado no booleano retornado
                if (isVogaisRecursivo(myString, 0)){

                    System.out.print("SIM ");

                }else{

                    System.out.print("NAO ");

                }

                if (isConsoantesRecursivo(myString, 0)){

                    System.out.print("SIM ");

                }else{

                    System.out.print("NAO ");

                }

                if (isInteiroRecursivo(myString, 0)){

                    System.out.print("SIM ");

                }else{

                    System.out.print("NAO ");

                }

                if (isRealRecursivo(myString, 0, 0)){

                    System.out.print("SIM");

                }else{

                    System.out.print("NAO");

                }

                //pular linha após analisar uma string inteira
                System.out.println(""); 

            }

        }

        scan.close();
    }

}
