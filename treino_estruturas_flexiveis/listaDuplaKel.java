public class listaDuplaKel{

    public static void main (String[] args){

        Lista myDoubleList = new Lista();
        myDoubleList.inserirFim(2);
        myDoubleList.inserirFim(3);
        myDoubleList.inserirFim(4);
        myDoubleList.inserirFim(6);
        myDoubleList.inserirInicio(7);
        myDoubleList.inserir(8,2);

        myDoubleList.mostrar();

        myDoubleList.remover(3);
        myDoubleList.mostrar();
    }
}

class Celula{

    public int elemento;
    public Celula prox;
    public Celula ant;

    public Celula (int x){
        this.elemento = x;
        this.prox = null;
        this.ant = null;
    }
}

class Lista{

    private Celula primeiro;
    private Celula ultimo;
    private int tam;

    public Lista() {
        this.primeiro = new Celula(-1);
        this.ultimo = primeiro;
        this.tam = 0;
    }

    /*. O nó sentinela é um nó especial que não contém um elemento válido da lista; ele serve apenas como um ponto de partida para simplificar a manipulação da lista (evitando verificar nulos em inserções e remoções no início da lista).*/
    public void inserirInicio (int x){

        Celula tmp = new Celula(x);
        tmp.ant = primeiro;
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;

        if(primeiro == ultimo){

            ultimo = tmp;
            
        }else{

            tmp.prox.ant = tmp;

        }

        tmp = null;

        tam++;
    }

    //mesma coisa da fila
    public int removerInicio (){

        if(primeiro == ultimo){

            System.out.println("ERRO. chamou removerInicio para lista vazia");
            return -1;

        }else{

            Celula tmp = primeiro;
            primeiro = primeiro.prox;
            int elemento = primeiro.elemento;
            tmp.prox = primeiro.ant = null;
            tmp = null;
            return elemento;

        }
    }

    public void inserirFim (int x){

        ultimo.prox = new Celula(x);
        ultimo.prox.ant = ultimo;
        ultimo = ultimo.prox;
        tam++;
    }

    public int removerFim (){

        //tamanho igual à zero é mesma coisa que primeiro == ultimo. lista vazia.
        //tamanho igual à um é a mesma coisa que remover a primeira célula após a sentinela
        //chamamos o removerInicio para tratar as exceções nesses casos
        if(tam == 0 || tam == 1){

            return removerInicio();

        }else{

            int elemento = ultimo.elemento;

            ultimo = ultimo.ant;

            ultimo.prox.ant = null;

            ultimo.prox = null;

            tam --;

            return elemento;

        }

    }

    //o último elemento ocupa a posicao tam-1
    /*As posições válidas para inserção são de 0 até tam, inclusive. Isso significa que podemos inserir um novo elemento em qualquer posição existente ou após o último elemento (posição tam).*/
    /*No método inserir(int x, int pos), a condição if (pos < 0 || pos > tam) verifica se pos está fora dos limites válidos para inserção. Podemos inserir na posição tam (após o último elemento), mas não em uma posição maior que tam.*/
    public void inserir (int x, int pos){

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

            tmp.ant = i;

            tmp.prox = i.prox;

            //tb poderia fazer i.prox = tmp ja que i eh igual a tmp.ant
            tmp.ant.prox = tmp.prox.ant = tmp;

            tmp = null;
            tam++;
        }
    }

    /*As posições válidas para remoção são de 0 até tam - 1. Se temos 5 células na lista (índices de 0 a 4), a última posição válida para remover é 4 (tam - 1).*/
    public int remover (int pos){

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
            tmp.prox.ant = i;
            tmp = tmp.prox = tmp.ant = null;
            tam--;
            return elemento;
        }
    }

    public void mostrar(){

        System.out.print("[ ");

        for(Celula i = primeiro.prox; i != null; i = i.prox){
            System.out.print(i.elemento + " ");
        }

        System.out.print("]\n");
    }

}