import java.util.Scanner;

public class is{

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
                if (isVogais(myString, tam)){

                    System.out.print("SIM ");

                }else{

                    System.out.print("NAO ");

                }

                if (isConsoantes(myString, tam)){

                    System.out.print("SIM ");

                }else{

                    System.out.print("NAO ");

                }

                if (isInteiro(myString, tam)){

                    System.out.print("SIM ");

                }else{

                    System.out.print("NAO ");

                }

                if (isReal(myString, tam)){

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

    public static boolean isVogais (String s, int tam){


        for (int i = 0; i < tam; i++){

            //se o caractere NÃO é a, nem e, nem i, nem o, nem u (nem a versão maiúscula dessas letras), então é uma consoante e já podemos retornar falso, já que estamos procurando uma palavra com 100% vogais
            if (s.charAt(i) != 'a' && s.charAt(i) != 'e' && s.charAt(i) != 'i' && s.charAt(i) != 'o' && s.charAt(i) != 'u' && s.charAt(i) != 'A' && s.charAt(i) != 'E' && s.charAt(i) != 'I' && s.charAt(i) != 'O' && s.charAt(i) != 'U' ){
                
                return false;

            }

        }
        
        return true;

    }

    public static boolean isConsoantes (String s, int tam){

        for (int i = 0; i < tam; i++){

            //se o caractere É a, ou e, ou i, ou o, ou u (ou a versão maiúscula dessas letras) OU é um número real devemos retornar falso, já que estamos procurando uma palavra com 100% consoantes
            if (!(isVogais(s, tam) && isReal(s, tam))){
                
                return false;

            }

        }

        return true;

    }

    public static boolean isInteiro (String s, int tam){

        for (int i = 0; i < tam; i++){

            //se o caractere NÃO está posicionado entre 0 e 9 na tabela ASCII, então não é um algarismo. Já podemos retornar pois estamos buscando um número inteiro.
            if(!('0' <= s.charAt(i) && s.charAt(i) <= '9')){

                return false;

            }

        }

        return true;

    }

    public static boolean isReal (String s, int tam){

        int virgulasOuPontos = 0;

        int ok = 0;

        //se for um inteiro, já sabemos imediatamente que o real também deve retornar true
        if (isInteiro(s, tam)){

            return true;

        } 

        for (int i = 0; i < tam; i++){

            //SE não passou no teste de inteiro, temos que ver caractere por caractere. para ser um real, pode ser composto de algarismos E vírgula ou ponto para separar as casas decimais
            if('0' <= s.charAt(i) && s.charAt(i) <= '9' || s.charAt(i)=='.' || s.charAt(i)==','){

                ok ++;

            }

            //o número real pode apenas ter UM ponto ou UMA vírgula
            if(s.charAt(i) == '.' || s.charAt(i) == ','){
                virgulasOuPontos++;
            }


        }

        if (virgulasOuPontos > 1 || ok < tam-1){

            return false;

        }else{

            return true;

        }

    }

}