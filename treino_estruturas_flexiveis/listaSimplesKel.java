public class listaSimplesKel{

    public static void main (String[] args){

        Lista myList = new Lista();
        myList.inserirFim(2);
        myList.inserirFim(3);
        myList.inserirFim(4);
        myList.inserirFim(6);
        myList.inserirInicio(7);
        myList.inserir(8,2);

        myList.mostrar();
        
        myList.remover(3);
        myList.mostrar();
    }
}

class Celula{

    public int elemento;
    public Celula prox;

    public Celula (int x){
        this.elemento = x;
        prox = null;
    }
}

class Lista{

    private Celula primeiro;
    private Celula ultimo;
    private int tam;

    public Lista (){
        this.primeiro = new Celula(-1);
        this.ultimo = primeiro;
        this.tam = 0;
    }

    /*. O nó sentinela é um nó especial que não contém um elemento válido da lista; ele serve apenas como um ponto de partida para simplificar a manipulação da lista (evitando verificar nulos em inserções e remoções no início da lista).*/

    void inserirInicio (int x){

        Celula tmp = new Celula(x);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;

        if(primeiro == ultimo){

            ultimo = tmp;
            
        }

        tmp = null;

        tam++;
    }

    //mesma coisa da fila
    int removerInicio (){

        if(primeiro == ultimo){

            System.out.println("ERRO. chamou removerInicio para lista vazia");
            return -1;

        }else{

            Celula tmp = primeiro.prox;
            primeiro.prox = tmp.prox; //ou primeiro.next.next
            int num = tmp.elemento;
            tmp = tmp.prox = null;
            if (primeiro.prox == null){
                ultimo = primeiro;
            }
            tam--;
            return num;
        }
    }

    void inserirFim (int x){

        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
        tam++;
    }

    int removerFim (){

        //tamanho igual à zero é mesma coisa que primeiro == ultimo. lista vazia.
        //tamanho igual à um é a mesma coisa que remover a primeira célula após a sentinela
        //chamamos o removerInicio para tratar as exceções nesses casos
        if(tam == 0 || tam == 1){

            return removerInicio();

        }else{

            int elemento = ultimo.elemento;

            Celula i = primeiro.prox;

            //queremos parar UM elemento ANTES do ULTIMO
            //a ultima celula tem seu prox apontando para null. a penultima celula (a que queremos apontar) tem seu prox apontando para um outro prox que aponta para null
            while (i.prox.prox != null){

                i = i.prox;

            }

            ultimo = i;

            ultimo.prox = null;

            i = null;

            tam --;

            return elemento;

        }

    }

    //o último elemento ocupa a posicao tam-1
    /*As posições válidas para inserção são de 0 até tam, inclusive. Isso significa que podemos inserir um novo elemento em qualquer posição existente ou após o último elemento (posição tam).*/
    /*No método inserir(int x, int pos), a condição if (pos < 0 || pos > tam) verifica se pos está fora dos limites válidos para inserção. Podemos inserir na posição tam (após o último elemento), mas não em uma posição maior que tam.*/
    void inserir (int x, int pos){

        if (pos < 0 || pos > tam){

            System.out.println("ERRO. tentativa de adicionar em posicao invalida");

        }else if (pos == 0){

            inserirInicio(x);

        }else if (pos == tam){

            inserirFim(x);

        }else{

            int count = 0;

            //mesma coisa que inicializar o count em -1
            Celula i = primeiro;

            //paramos uma celula ANTES da que queremos inserir
            //ou seja: percorre até o nó anterior à posição desejada
            while (count < pos){

                i = i.prox;
                count++;
            }

            Celula tmp = new Celula (x);
            tmp.prox = i.prox;
            i.prox = tmp;
            tmp = null;
            tam++;
        }
    }

    /*As posições válidas para remoção são de 0 até tam - 1. Se temos 5 células na lista (índices de 0 a 4), a última posição válida para remover é 4 (tam - 1).*/
    int remover (int pos){

        if (pos < 0 || pos >= tam){

            System.out.println("ERRO. tentativa de remover em posicao que nao existe na lista");
            return -1;

        }else if (pos == 0){

            return removerInicio();

        }else if (pos == tam-1){

            return removerFim();

        }else{

            int count = 0;

            //é como se a contagem das posicoes começasse no -1
            Celula i = primeiro;

            //paramos uma celula ANTES da que queremos remover
            while (count < pos){

                i = i.prox;
                count++;
            }

            Celula tmp = i.prox;
            int elemento = tmp.elemento;
            i.prox = tmp.prox;
            tmp = tmp.prox = null;
            tam--;
            return elemento;
        }
    }

    void mostrar(){

        System.out.print("[ ");

        for(Celula i = primeiro.prox; i != null; i = i.prox){
            System.out.print(i.elemento + " ");
        }

        System.out.print("]\n");
    }

}