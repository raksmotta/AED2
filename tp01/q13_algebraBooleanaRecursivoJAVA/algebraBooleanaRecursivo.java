import java.util.Scanner;

public class algebraBooleanaRecursivo {

    public static int ifAND(int ums, int zeros) {

        // se tiver pelo menos um zero na expressão operada por AND, já retornamos
        // falso. para dar true, precisam ser todos os valores 1
        return zeros >= 1 ? 0 : 1;

    }

    public static int ifOR(int ums, int zeros) {

        // se tiver pelo menos um 1 no OR, já pode retornar true. só retorna false se
        // for tudo 0
        return ums >= 1 ? 1 : 0;

    }

    public static int ifNOT(int ums) {

        // o not sempre tem apenas uma variável e retorna o oposto dela
        return ums == 1 ? 0 : 1;

    }

    public static int analisar(StringBuilder expressao) {

        int temNumeros = 0;
        int temNot = 0;

        //contar a quantidade de numeros e caracteres 't' (de noT) (ainda) presentes na string

        for (int y = 0; y < expressao.length(); y++) {

            if (Character.isDigit(expressao.charAt(y))) {
                temNumeros++;
            }
            if (expressao.charAt(y) == 't') {
                temNot++;
            }

        }

        //CONDIÇÃO DE BASE
        //a recursividade só para SE:
        //se a expressão for reduzida a apenas 1 digito E nao houver not a ser operado. nesse caso, retornamos como está como o resultado
        if (temNumeros == 1 && temNot == 0) {
            //a posicao 0 da string é um caractere em branco
            return Character.getNumericValue(expressao.charAt(1));
        }

        //Posso declarar essas variáveis dentro da função recursiva pois elas serão RESETADAS a cada chamada
        int QTDfechado = 0;
        int POSaberto = 0;
        int POSfechado = 0;

        int i = 0;

        while (QTDfechado == 0 && i < expressao.length()) {

            if (expressao.charAt(i) == '(') {
            POSaberto = i;
            }

            // se encontramos um parênteses fechado, quer dizer que o último parenteses aberto encontrado forma um par com ele e já podemos analisar a expressão ali dentro
            if (expressao.charAt(i) == ')') {

                QTDfechado++;
                POSfechado = i;

            }

            i++;

        }

        //fazer subExpressao para analisar apenas o que está dentro do par de parênteses encontrado 
        String subExpressao = expressao.substring(POSaberto + 1, POSfechado);
        int ums = 0;
        int zeros = 0;

        //contar quantas vezes o 1 e o 0 aparecem dentro da expressão analisada
        for (int x = 0; x < subExpressao.length(); x++) {
            if (subExpressao.charAt(x) == '0') {
                zeros++;
            } else if (subExpressao.charAt(x) == '1') {
                ums++;
            }
        }

        //a letra imediatamente antes do parênteses ( analisado nos indica qual a operação a ser realizada 
        int POSoperacao = POSaberto - 1;

        String r = "2";

        //anD, oR e noT
        if (expressao.charAt(POSoperacao) == 'd') {
            r = String.valueOf(ifAND(ums, zeros));
            expressao.replace(POSaberto - 3, POSfechado + 1, r);

        } else if (expressao.charAt(POSoperacao) == 'r') {
            r = String.valueOf(ifOR(ums, zeros));
            expressao.replace(POSaberto - 2, POSfechado + 1, r);

        } else if (expressao.charAt(POSoperacao) == 't') {
            r = String.valueOf(ifNOT(ums));
            expressao.replace(POSaberto - 3, POSfechado + 1, r);
        }
        
        //CHAMADA RECURSIVA
        //a recursividade consiste em analisar novamente a expressão, agora alterada, com uma das operações inteiramente substituída por apenas o dígito de seu valor lógico resultante na string
        return analisar(expressao);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int aLido = 5;
        int bLido = 5;
        int cLido = 5;

        Boolean continuar = true;

        while (continuar) {

            // coletando qtd de variáveis lógicas e os seus valores
            int qtd = sc.nextInt();

            if (qtd == 0) {

                continuar = false;

            }

            if (continuar) {

                aLido = sc.nextInt();

                //SE for TRUE que qtd é maior ou igual a 2, lê o inteiro e guarda na variável b. Se for falso, apenas atribui o valor 5 a b
                bLido = qtd >= 2 ? sc.nextInt() : 5;
                cLido = qtd == 3 ? sc.nextInt() : 5;

                // convertendo os inteiros lidos para caracteres para que possamos substituí-los
                // na string e ter uma expressão apenas com 0s e 1s
                char a = (char) (aLido + '0');
                char b = (char) (bLido + '0');
                char c = (char) (cLido + '0');

                // depois, vamos ler a string com a expressão algébrica
                String s = sc.nextLine();

                // transformando em SB para poder editar
                StringBuilder expressao = new StringBuilder(s);

                // substituindo as ocorrências das letras A, B e C na string por carcateres de
                // seus valores lógicos equivalentes (1 ou 0)
                for (int p = 0; p < expressao.length(); p++) {

                    if (expressao.charAt(p) == 'A') {
                        expressao.setCharAt(p, a);
                    }

                    if (expressao.charAt(p) == 'B') {
                        expressao.setCharAt(p, b);
                    }

                    if (expressao.charAt(p) == 'C') {
                        expressao.setCharAt(p, c);
                    }

                }

                int result = analisar(expressao);
                System.out.println(result);

            }

        }

        sc.close();

    }

}