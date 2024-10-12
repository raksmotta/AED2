
public class cifraCesar {

    public static StringBuilder cesar (String antiga){

        //criando uma string vazia com capacidade de armazenas o mesmo número de caracteres da string a ser cifrada
        StringBuilder resultado = new StringBuilder(antiga.length());

        for (int i = 0; i < antiga.length(); i++){

            //o caractere retornado pela função que faz o shift é anexado na última posição da string de resultado
            resultado.append(cifrarCaractere(antiga.charAt(i), 3));

        }

        return resultado;

    }

    public static char cifrarCaractere (char c, int shift){

        //a adição de um inteiro com um caractere, em java, resulta em um inteiro. por isso, precisamos de fazer um type-casting e converter o inteiro resultante para o caractere equivalente na tabela ASCII antes de retornar
        return (char) (c + shift);

    }

    public static void main(String[] args) {

        MyIO.setCharset("ISO-8859-1");

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

            //se a palavra lida NÃO for fim, prosseguimos para a cifra
            if (fim == 0){

                StringBuilder cifrada = cesar (myString);
                
                //convertendo de StringBuilder para String
                String converter = cifrada.toString();

                MyIO.println(converter);
            }


        }

    }
    
}
