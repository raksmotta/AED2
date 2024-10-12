public class cifraCesarRecursivo {

    //a recursividade da função consistirá em lidar com cada caractere por vez na string e ir passando ao próximo até que não restem mais caracteres a serem cifrados
    public static StringBuilder cesarRecursivo (String antiga){

        //base da recursão: se a string está vazia, retorna um StringBuilder vazio. obs.: não é isso que a função RETORNA no final, é apenas o PONTO DE PARADA da recursão
        if (antiga.isEmpty()) {
            return new StringBuilder();
        }
        //pegamos o primeiro caractere da string "antiga" e o processamos aplicando a função cifrarCaractere. adicionamos o caractere cifrado à StringBuilder que criamos através do append().
        //depois, precisamos fazer uma chamada recusiva com o resto da string para analisar o próximo caractere. esse primeiro caractere fica "esperando" na pilha para ser adicionado ao StringBUilder
        //para chamar mais uma vez cesarRecursivo, criamos uma nova string que é uma sub-string de antiga, começando do segundo caractere (que está na posição 1) até o final. OU SEJA: removemos o primeiro caractere.
        //o resultado dessa chamada recursiva (que é um StringBuilder contendo o resto da string cifrada) é adicionado ao StringBuilder original.
        return new StringBuilder().append(cifrarCaractere(antiga.charAt(0), 3)).append(cesarRecursivo(antiga.substring(1)));

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

                StringBuilder cifrada = cesarRecursivo (myString);
                
                //convertendo de StringBuilder para String
                String converter = cifrada.toString();

                MyIO.println(converter);
            }


        }

    }
    
}
