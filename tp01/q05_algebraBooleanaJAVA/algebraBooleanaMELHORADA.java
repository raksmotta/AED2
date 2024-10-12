import java.util.Scanner;

public class algebraBooleanaMELHORADA{

    public static int ifAND(int ums, int zeros) {

        //se tiver pelo menos um zero na expressão operada por AND, já retornamos falso. para dar true, precisam ser todos os valores 1
        if (zeros >= 1){

            return 0;

        }else{

            return 1;

        }

    }

    public static int ifOR(int ums, int zeros) {

        //se tiver pelo menos um 1 no OR, já pode retornar true. só retorna false se for tudo 0
        if (ums >= 1){

            return 1;

        }else{

            return 0;

        }

    }

    public static int ifNOT(int ums) {

        //o not sempre tem apenas uma variável e retorna o oposto dela

        if (ums == 1) {

            return 0;

        } else {

            return 1;

        }

    }

    public static int analisar(StringBuilder expressao) {

        int temNumeros = 0;
        int temNot = 0;

        int ums = 0;
        int zeros = 0;

        String r = "2";

        int resultadoAND = 3;
        int resultadoOR = 3;
        int resultadoNOT = 3;

        int inicioSubstituicao = 0;
        int fimSubstituicao = 0;

        // variáveis para guardar a quantidade e as posições dos parênteses
        int QTDfechado = 0;

        int POSaberto = 0;
        int POSfechado = 0;

        int i = 0;

        do {

            temNumeros = 0;
            temNot = 0;

            //a expressao final contem apenas UM numero, 0 ou 1
            for (int y = 0; y < expressao.length(); y++) {

                if ('0' <= expressao.charAt(y) && expressao.charAt(y) <= '9') {

                    temNumeros ++;

                }

                if (expressao.charAt(y) == 't') {

                    temNot ++;

                }
            }

            if (temNumeros > 1 || temNot != 0) {

                QTDfechado = 0;

                POSaberto = 0;
                POSfechado = 0;

                i = 0;

                while (QTDfechado == 0 && i < expressao.length()) {

                    if (expressao.charAt(i) == '(') {

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

                ums = 0;
                zeros = 0;

                // agora, pegamos apenas o trecho de interesse iremos analisar apenas a parte de DENTRO dos parenteses
                String subExpressao = expressao.substring(POSaberto, POSfechado);

                for (int x = 0; x < subExpressao.length(); x++){

                    if (subExpressao.charAt(x) == '0'){
                        zeros ++;
                    }

                    if (subExpressao.charAt(x) == '1'){
                        ums ++;
                    }
                }

                // vamos descobrir a operacao logica ao analisar a ultima letra imediatamente na
                // frente do ultimo parenteses aberto registrado
                int POSoperacao = POSaberto - 1;

                // se a ultima letra antes do parenteses for D, entao temos AND
                // se a ultima letra antes do parenteses for R, entao temos OR
                // se a ultima letra antes do parenteses for T, entao temos NOT
                if (expressao.charAt(POSoperacao) == 'd') {

                    resultadoAND = ifAND(ums, zeros);

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

                    resultadoOR = ifOR(ums, zeros);

                    if (resultadoOR == 1) {

                        r = "1";

                    } else if (resultadoOR == 0) {

                        r = "0";

                    }

                    inicioSubstituicao = POSaberto - 2;

                    fimSubstituicao = POSfechado + 1;

                    expressao.replace(inicioSubstituicao, fimSubstituicao, r);

                } else if (expressao.charAt(POSoperacao) == 't') {

                    resultadoNOT = ifNOT(ums);

                    if (resultadoNOT == 1) {

                        r = "1";

                    } else if (resultadoNOT == 0) {

                        r = "0";

                    }

                    inicioSubstituicao = POSaberto - 3;

                    fimSubstituicao = POSfechado + 1;

                    expressao.replace(inicioSubstituicao, fimSubstituicao, r);

                } else {

                    System.out.println(
                            "ERRO. Nao foi encontrada operacao logica antes do ultimo parenteses aberto registrado");

                }

                //String teste = expressao.toString();

                //System.out.println(teste);

            }

        } while (temNumeros > 1 || temNot != 0);

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

                if (qtd == 2) {

                    aLido = sc.nextInt();
                    bLido = sc.nextInt();

                } else if (qtd == 3) {

                    aLido = sc.nextInt();
                    bLido = sc.nextInt();
                    cLido = sc.nextInt();

                }

                //convertendo os inteiros lidos para caracteres para que possamos substituí-los na string e ter uma expressão apenas com 0s e 1s
                char a = (char)(aLido + '0');
                char b = (char)(bLido + '0');
                char c = (char)(cLido + '0');

                // depois, vamos ler a string com a expressão algébrica
                String s = sc.nextLine();

                //transformando em SB para poder editar
                StringBuilder expressao = new StringBuilder(s);

                //substituindo as ocorrências das letras A, B e C na string por carcateres de seus valores lógicos equivalentes (1 ou 0)
                for (int p = 0; p < expressao.length(); p ++){

                    if (expressao.charAt(p) == 'A'){
                        expressao.setCharAt(p, a);
                    }

                    if (expressao.charAt(p) == 'B'){
                        expressao.setCharAt(p, b);
                    }

                    if (expressao.charAt(p) == 'C'){
                        expressao.setCharAt(p, c);
                    }

                }

                int result = analisar(expressao);

            }

        }

        sc.close();

    }

}