public class pilhaKel{

    public static void main(String[] args) {

        Pilha myPilha = new Pilha();

        myPilha.inserir(5);
        myPilha.inserir(6);
        myPilha.inserir(2);
        myPilha.inserir(10);
        myPilha.inserir(8);
        myPilha.inserir(15);

        myPilha.mostrar();

        int elementoRemovido = myPilha.remover();

        System.out.println("elemento removido:" + elementoRemovido);

        int soma = myPilha.somarRecursivo(myPilha.topo);

        System.out.println("soma:" + soma);

        //int big = myPilha.maior();
        int big = myPilha.maiorRecursivo(myPilha.topo.elemento, myPilha.topo);

        System.out.println("maior:" + big);

        System.out.println("mostrar recursivo:");

        myPilha.mostrarRecursivo(myPilha.topo);

        System.out.println("printando em ordem inversa recursivo:");

        myPilha.mostrarInverso(myPilha.topo);

        System.out.println("printando em ordem inversa iterativo:");

        myPilha.mostrarInversoIterativo();
        
    }

}

//quando criamos uma celula, apontamos o seu ponteiro para null. prox só vai apontar para alguma outra coisa no momento de inserção da céulula na estrutura de dados. isso é útil para usarmos prox == null como condição da parada para iterar sobre a pilha
class Celula{

    public int elemento;
    public Celula prox;

    public Celula(int x){
        this.elemento = x;
        this.prox = null;
    }

}

class Pilha{

    //referencia que aponta para algo do tipo celula
    public Celula topo;

    public Pilha(){
        topo = null;
    }

    public void inserir (int x){
        Celula tmp = new Celula (x);
        tmp.prox = topo;
        topo = tmp;
        tmp = null;
    }

    public int remover(){

        //condicao de pilha vazia
        if(topo == null){

            System.out.println("ERRO ao remover");
            return 0;

        }else{

            int elemento = topo.elemento;

            //como aqui estou codando em java, poderia simplesmente fazer topo = topo.prox; para remover LOGICAMENTE a celula
            //estou removendo FISICAMENTE para poder desalocar a célula e liberá-la (obrigatório em c). o tmp permite fazer o free na célula q nao usaremos mais
            Celula tmp = topo;
            topo = topo.prox;
            tmp.prox = null;
            tmp = null;
            return elemento;

        }
    }

    //para iterar sobre a estrutura, criamos um ponteiro descartável. o iterable permite iterar sobre a estrutura estando fora dela
    public void mostrar(){

        for (Celula i = topo; i!=null; i=i.prox){
            System.out.println(i.elemento);
        }
    }

    public int somar(){

        int sum = 0;

        for (Celula i = topo; i!=null; i=i.prox){
            sum += i.elemento;
        }

        return sum;
    }

    //faca um metodo recursivo que retorna a soma dos elementos contidos na pilha
    //topo deve ser passado como parametro (COMO VALOR, aqui n passamos uma referencia a topo e portanto n alteramos para onde topo aponta)
    public int somarRecursivo(Celula i){

        int resp = 0;

        if (i != null){
            resp = i.elemento + somarRecursivo(i.prox);
        }

        return resp;
    }

    //metodo que retorna o maior elemento da pilha
    public int maior(){

        int big = topo.elemento;

        for (Celula i = topo.prox; i!=null; i=i.prox){

            if(i.elemento > big){
                big = i.elemento;
            }
        }

        return big;
    }

    //os parametros passados devem ser myPilha.topo.elemento, myPilha.topo
    public int maiorRecursivo(int big, Celula i){

        if(i==null){

            return big;

        }else{

            if (i.elemento > big){
                big = i.elemento;
            }

            return maiorRecursivo(big, i.prox);
        }
    }

    //método recursivo para mostrar os elementos da pilha na ordem em que serão removidos (lógica de last in last out). ou seja: printa TOP DOWN
    //passar topo como parametro
    public void mostrarRecursivo(Celula i){

        if(i!=null){

            System.out.println(i.elemento);

            mostrarRecursivo(i.prox);
        }
    }

    //metodo recursivo para mostrar os elementos na ordem CONTRÁRIA à ordem em que eles serão removidos. Ou seja: mostra os elementos na ordem em que eles foram inseridos
    //passar topo como parametro
    public void mostrarInverso(Celula i){

        if(i!=null){

            mostrarInverso(i.prox);

            System.out.println(i.elemento);

        }

    }

    //metodo iterativo para mostrar os elementos na ordem inversa à lógica natural de pop da pilha
    public void mostrarInversoIterativo(){

        int tam = 0;

        for (Celula i = topo; i!=null; i=i.prox){
            tam++;
        }

        //fazendo um vetor auxiliar para guardarmos os elementos para permitir a leitura de trás para frente
        int[] array = new int [tam];

        int index = 0;

        for (Celula i = topo; i!=null; i=i.prox){

            array[index] = i.elemento;

            index++;
        }

        for (int x = tam-1; x>=0; x--){

            System.out.println(array[x]);

        }

    }


}