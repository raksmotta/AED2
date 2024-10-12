import java.util.Scanner;

public class algebraBooleana {

    public static int ifAND(int variavelA, int variavelB, int variavelC, int um, int zero, int a, int b, int c) {

        if (zero == 1 || zero == 2 || zero == 3) {

            return 0;

        } else if (variavelA == 1 && variavelB == 1 && variavelC == 1) {

            if (a == 0 || b == 0 || c == 0) {
                return 0;
            } else {
                return 1;
            }

        } else if (variavelA == 1 && variavelB == 1 && um == 1) {

            if (a == 0 || b == 0) {
                return 0;
            } else {
                return 1;
            }

        } else if (variavelA == 1 && variavelC == 1 && um == 1) {

            if (a == 0 || c == 0) {
                return 0;
            } else {
                return 1;
            }

        } else if (variavelB == 1 && variavelC == 1 && um == 1) {

            if (b == 0 || c == 0) {
                return 0;
            } else {
                return 1;
            }

        } else if ((variavelA == 1 && um == 1) || (variavelA == 1 && um == 2)) {

            return a;

        } else if ((variavelB == 1 && um == 1) || (variavelB == 1 && um == 2)) {

            return b;

        } else if ((variavelC == 1 && um == 1) || (variavelC == 1 && um == 2)) {

            return c;

        } else if (variavelA == 1 && variavelB == 1) {

            return a * b;

        } else if (variavelA == 1 && variavelC == 1) {

            return a * c;

        } else if (variavelB == 1 && variavelC == 1) {

            return b * c;

        } else if (um == 2 || um == 3) {

            return 1;
        
        } else {

            System.out.println("ERRO AND na hora de achar quais variaveis estao dentro dos parenteses");
            return 2;

        }

    }

    public static int ifOR(int variavelA, int variavelB, int variavelC, int um, int zero, int a, int b, int c) {

        int soma = 0;

        if (um == 1 || um == 2 || um == 3 || um == 4) {

            return 1;

        } else if (zero == 4) {

            return 0;

        } else if (variavelA == 1 && variavelB == 1 && variavelC == 1 && zero == 1) {

            if (a == 1 || b == 1 || c == 1) {
                return 1;
            } else {
                return 0;
            }

        } else if (variavelA == 1 && variavelB == 1 && zero == 2) {

            if (a == 1 || b == 1) {
                return 1;
            } else {
                return 0;
            }

        } else if (variavelA == 1 && variavelC == 1 && zero == 2) {

            if (a == 1 || c == 1) {
                return 1;
            } else {
                return 0;
            }

        } else if (variavelB == 1 && variavelC == 1 && zero == 2) {

            if (b == 1 || c == 1) {
                return 1;
            } else {
                return 0;
            }

        } else if (variavelA == 1 && variavelB == 1 && variavelC == 1) {

            if (a == 1 || b == 1 || c == 1) {
                return 1;
            } else {
                return 0;
            }

        } else if (variavelA == 1 && variavelB == 1 && zero == 1) {

            if (a == 1 || b == 1) {
                return 1;
            } else {
                return 0;
            }

        } else if (variavelA == 1 && variavelC == 1 && zero == 1) {

            if (a == 1 || c == 1) {
                return 1;
            } else {
                return 0;
            }

        } else if (variavelB == 1 && variavelC == 1 && zero == 1) {

            if (b == 1 || c == 1) {
                return 1;
            } else {
                return 0;
            }

        } else if ((variavelA == 1 && zero == 1) || (variavelA == 1 && zero == 2) || (variavelA == 1 && zero == 3)) {

            return a;

        } else if ((variavelB == 1 && zero == 1) || (variavelB == 1 && zero == 2) || (variavelB == 1 && zero == 3)) {

            return b;

        } else if ((variavelC == 1 && zero == 1) || (variavelC == 1 && zero == 2) || (variavelC == 1 && zero == 3)) {

            return c;

        } else if (variavelA == 1 && variavelB == 1) {

            soma = a + b;

            if (soma == 2) {
                return 1;
            } else {
                return soma;
            }

        } else if (variavelA == 1 && variavelC == 1) {

            soma = a + c;

            if (soma == 2) {
                return 1;
            } else {
                return soma;
            }

        } else if (variavelB == 1 && variavelC == 1) {

            soma = b + c;

            if (soma == 2) {
                return 1;
            } else {
                return soma;
            }

        } else if (zero == 2 || zero == 3) {

            return 0;

        } else {

            System.out.println("ERRO OR na hora de achar quais variaveis estao dentro dos parenteses");
            return 2;

        }

    }

    public static int ifNOT(int variavelA, int variavelB, int variavelC, int um, int zero, int a, int b, int c) {

        if (um == 1) {

            return 0;

        } else if (zero == 1) {

            return 1;

        } else if (variavelA == 1) {

            if (a == 0) {
                return 1;
            } else {
                return 0;
            }

        } else if (variavelB == 1) {

            if (b == 0) {
                return 1;
            } else {
                return 0;
            }

        } else if (variavelC == 1) {

            if (c == 0) {
                return 1;
            } else {
                return 0;
            }

        } else {

            System.out.println("ERRO NOT na hora de achar quais variaveis estao dentro dos parenteses");
            return 2;

        }

    }

    public static int analisar(int a, int b, int c, String s) {

        Boolean temLetras = true;

        int variavelA = 0;
        int variavelB = 0;
        int variavelC = 0;
        int um = 0;
        int zero = 0;

        String r = "2";

        int resultadoAND = 3;
        int resultadoOR = 3;
        int resultadoNOT = 3;

        int inicioSubstituicao = 0;
        int fimSubstituicao = 0;

        // inicializando uma StringBuilder com exatamente o mesmo conteúdo que a string
        // passada como parâmetro
        StringBuilder expressao = new StringBuilder(s);

        // variáveis para guardar a quantidade e as posições dos parênteses
        int QTDaberto = 0;
        int QTDfechado = 0;

        int POSaberto = 0;
        int POSfechado = 0;

        int i = 0;

        do {

            temLetras = false;

            for (int y = 0; y < expressao.length(); y++) {

                if (('a' <= expressao.charAt(y) && expressao.charAt(y) <= 'z')
                        || ('A' <= expressao.charAt(y) && expressao.charAt(y) <= 'Z')) {

                    temLetras = true;

                }
            }

            if (temLetras) {

                QTDaberto = 0;
                QTDfechado = 0;

                POSaberto = 0;
                POSfechado = 0;

                i = 0;

                while (QTDfechado == 0 && i < expressao.length()) {

                    if (expressao.charAt(i) == '(') {

                        QTDaberto++;
                        POSaberto = i;

                    }

                    // se encontramos um parênteses fechado, quer dizer que o último parenteses
                    // aberto encontrado forma um par com ele e já podemos analisar a expressão ali
                    // dentro
                    if (expressao.charAt(i) == ')') {

                        QTDfechado++;
                        POSfechado = i;

                    }

                    i++;

                }

                // agora, analisamos quais as variaveis OU valores DENTRO do parenteses
                variavelA = 0;
                variavelB = 0;
                variavelC = 0;
                um = 0;
                zero = 0;

                String quaisVariaveis = expressao.substring(POSaberto, POSfechado);

                for (int x = 0; x < quaisVariaveis.length(); x++) {

                    if (quaisVariaveis.charAt(x) == 'A') {
                        variavelA++;
                    }

                    if (quaisVariaveis.charAt(x) == 'B') {
                        variavelB++;
                    }

                    if (quaisVariaveis.charAt(x) == 'C') {
                        variavelC++;
                    }

                    if (quaisVariaveis.charAt(x) == '1') {
                        um++;
                    }

                    if (quaisVariaveis.charAt(x) == '0') {
                        zero++;
                    }

                }

                // vamos descobrir a operacao logica ao analisar a ultima letra imediatamente na
                // frente do ultimo parenteses aberto registrado
                int POSoperacao = POSaberto - 1;

                // se a ultima letra antes do parenteses for D, entao temos AND
                // se a ultima letra antes do parenteses for R, entao temos OR
                // se a ultima letra antes do parenteses for T, entao temos NOT

                if (expressao.charAt(POSoperacao) == 'd') {

                    resultadoAND = ifAND(variavelA, variavelB, variavelC, um, zero, a, b, c);

                    if (resultadoAND == 1) {

                        r = "1";

                    } else if (resultadoAND == 0) {

                        r = "0";

                    }

                    inicioSubstituicao = POSaberto - 3;

                    fimSubstituicao = POSfechado + 1;

                    // replace(int start, int end, String str)
                    expressao.replace(inicioSubstituicao, fimSubstituicao, r);

                } else if (expressao.charAt(POSoperacao) == 'r') {

                    resultadoOR = ifOR(variavelA, variavelB, variavelC, um, zero, a, b, c);

                    if (resultadoOR == 1) {

                        r = "1";

                    } else if (resultadoOR == 0) {

                        r = "0";

                    }

                    inicioSubstituicao = POSaberto - 2;

                    fimSubstituicao = POSfechado + 1;

                    // replace(int start, int end, String str)
                    expressao.replace(inicioSubstituicao, fimSubstituicao, r);

                } else if (expressao.charAt(POSoperacao) == 't') {

                    resultadoNOT = ifNOT(variavelA, variavelB, variavelC, um, zero, a, b, c);

                    if (resultadoNOT == 1) {

                        r = "1";

                    } else if (resultadoNOT == 0) {

                        r = "0";

                    }

                    inicioSubstituicao = POSaberto - 3;

                    fimSubstituicao = POSfechado + 1;

                    // replace(int start, int end, String str)
                    expressao.replace(inicioSubstituicao, fimSubstituicao, r);

                } else {

                    System.out.println(
                            "ERRO. Nao foi encontrada operacao logica antes do ultimo parenteses aberto registrado");

                }

                //String teste = expressao.toString();

                //System.out.println(teste);

            }

        } while (temLetras);

        int finalzinho = 5;

        String finalmente = expressao.toString();

        for (int w = 0; w < finalmente.length(); w++) {

            if (finalmente.charAt(w) == '0') {

                finalzinho = 0;

            }

            if (finalmente.charAt(w) == '1') {

                finalzinho = 1;

            }

        }
        System.out.println(finalzinho);

        return 0;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int a = 5;
        int b = 5;
        int c = 5;

        Boolean continuar = true;

        while (continuar) {

            // coletando qtd de variáveis lógicas e os seus valores
            int qtd = sc.nextInt();

            if (qtd == 0) {

                continuar = false;

            }

            if (continuar) {

                if (qtd == 2) {

                    a = sc.nextInt();
                    b = sc.nextInt();

                } else if (qtd == 3) {

                    a = sc.nextInt();
                    b = sc.nextInt();
                    c = sc.nextInt();

                }

                // depois, vamos ler a string com a expressão algébrica
                String s = sc.nextLine();

                int result = analisar(a, b, c, s);

            }

        }

        sc.close();

    }

}