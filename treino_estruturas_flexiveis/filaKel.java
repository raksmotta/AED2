public class filaKel{

    public static void main(String[] args) {

        Fila myFila = new Fila();

        myFila.inserir(2);
        myFila.inserir(30);
        myFila.inserir(1);
        myFila.inserir(7);
        myFila.inserir(18);
        myFila.inserir(23);
        myFila.inserir(3);

        myFila.remover();
        myFila.remover();
        myFila.remover();
        myFila.remover();
        myFila.remover();
        myFila.remover();
        myFila.remover();

        /*System.out.println("minha fila:");
        myFila.mostrar();*/

        myFila.remover();

        System.out.println("removi um elemento. agora minha fila eh assim:");
        myFila.mostrar();

        System.out.println("maior elemento:" + myFila.maior());

        System.out.println("terceiro elemento:" + myFila.terceiroElemento());

        System.out.println("soma:" + myFila.somar());


    }

}

class Celula{

    public int elemento;
    public Celula prox;

    public Celula(int x){
        this.elemento = x;
        this.prox = null;
    }

}

class Fila{

    //início da fila
    private Celula primeiro;

    //o ultimo se refere ao final da fila, ao elemento inserido mais recentemente
    private Celula ultimo;

    private int tam;

    //no construtor, já inserimos uma célula na fila. ela é o nó cabeça ou SENTINELA. seu conteúdo é descartável, serve apenas para facilitar as operações no programa
    public Fila(){
        primeiro = new Celula(-1);
        ultimo = primeiro; 
        tam = 0;
    }

    //a inserção é sempre feita no FINAL. que nem em uma fila de pessoas, sempre entramos ATRÁS DO ÚLTIMO
    //ao contrario da pilha, em que cada célula nasce com seu prox apontando para outra célula, na fila as células nascem SENDO apontadas pelo prox da anterior e com o seu próprio prox permanecendo em null
    public void inserir(int x){

        ultimo.prox = new Celula (x);

        ultimo = ultimo.prox;

        tam++;
    }

    //a remoção é sempre feita no PRIMEIRO. primeiro a entrar é o primeiro a sair

    //essa remocao causa a perda do no cabeca. ela eh para ser usada quando NAO temos sentinela
    public int removerSemSentinela(){

        if(primeiro==null){

            System.out.println("ERRO. tentativa de remocao com lista vazia");
            return -1;

        }else{

            Celula tmp = primeiro;
            primeiro = primeiro.prox;
            int elemento = tmp.elemento;
            tmp.prox = null;
            tmp = null;
            tam --;
            return elemento;
        }

    }

    //remove fisicamente a celula logo apos a sentinela
    public int remover(){

        if(primeiro == ultimo){

            System.out.println("ERRO. tentativa de remocao com lista vazia");
            return -1;

        }else{

            Celula tmp = primeiro.prox;
            primeiro.prox = primeiro.prox.prox;
            int elemento = tmp.elemento;
            tmp.prox = null;
            tmp = null;
            tam --;
            if(primeiro.prox==null){
                ultimo = primeiro;
            }
            return elemento;
        }
    }

    public void mostrar(){

        //nao podemos fazer i=primeiro que nem na pilha pois primeiro aponta para a celula cabeca
        for (Celula i = primeiro.prox; i != null; i = i.prox){

            System.out.println(i.elemento);

        }
    }

    public int maior(){

        if(primeiro==ultimo){

            System.out.println("ERRO. tentativa de achar maior com lista vazia");
            return -1;

        }

        int big = primeiro.prox.elemento;

        for (Celula i = primeiro.prox.prox; i != null; i = i.prox){

            if(i.elemento > big){
                big = i.elemento;
            }

        }

        return big;
    }

    public int terceiroElemento (){

        int count = 0;
        Celula i = primeiro.prox;

        while (count < 3){
            i = i.prox;
            count ++;
        }

        return i.elemento;

        //ou poderia só fazer return(primeiro.prox.prox.prox.elemento)
    }

    public int somar(){

        int sum = 0;

        for (Celula i = primeiro.prox; i!=null; i=i.prox){

            sum += i.elemento;

        }

        return sum;
    }

    public void inverter(){

        Celula fim = ultimo;

        while (primeiro!=fim){

            Celula nova = new Celula (primeiro.prox.elemento);

            nova.prox = fim.prox;
            fim.prox = nova;

            Celula tmp = primeiro.prox;
            primeiro.prox = tmp.prox;

            nova = tmp = tmp.prox = null;

            if (primeiro == fim){
                ultimo = ultimo.prox;
            }

            fim = null;

        }

    }

    public int parEmultiplo5 (Celula i){

        int result;

        if (i == null){

            result = 0;

        }else if (i.elemento%2 == 0 && i.elemento%5 == 0){

            result = 1 + parEmultiplo5(i.prox);
            
        }else{

            result = parEmultiplo5(i.prox);
        }

        return result;
        
    }

}