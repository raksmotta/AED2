public class binaryTree {
    
}

class No {

    public int elemento;
    public No esq;
    public No dir;

    public No (int x){

        this.elemento = x;
        this.dir = null;
        this.esq = null;

    }

    public No (int x, No e, No d){

        this.elemento = x;
        this.esq = e;
        this.dir = d;
    }

}

class ArvoreBinaria {

    public No raiz;

    public ArvoreBinaria() {
        raiz = null;
    }

    //INÍCIO - INSERÇÃO EM JAVA COM RETORNO DE REFERÊNCIA
    public void inserirVoid (int x){
        raiz = inserirRetorna (x, raiz);
    }

    /*A raiz só é alterada quando inserimos o primeiro elemento, pois inicialmente raiz é null.
    Nas inserções subsequentes, a raiz já existe, e as alterações ocorrem nas subárvores (filhos esquerdo ou direito).
    A variável i nas chamadas recursivas é local àquela função, e mesmo que i mude dentro da função, não muda a raiz ou i das chamadas anteriores.
    - i é uma variável local em cada chamada de inserirRetorna.
    - A raiz é uma variável de instância da classe ArvoreBinaria.
    - Alterar i dentro de inserirRetorna não afeta diretamente raiz, a menos que estejamos na primeira chamada onde i é raiz.
    - A variável i é local e representa o nó atual em cada chamada recursiva.
    - O retorno de i é essencial para conectar os novos nós inseridos à árvore existente.
    */
    

    public No inserirRetorna(int x, No i) {

        if (i == null) {
            // Caso base: encontramos a posição para inserir o novo nó.
            i = new No(x);

        } else if (x < i.elemento) {
            // Se o valor a inserir é menor, vamos para a subárvore esquerda.
            i.esq = inserirRetorna(x, i.esq);

        } else if (x > i.elemento) {
            // Se o valor a inserir é maior, vamos para a subárvore direita.
            i.dir = inserirRetorna(x, i.dir);

        } else {
            // O valor já existe na árvore.
            System.out.println("ERRO em inserirRetorna");
        }

        // Retornamos o nó atual (possivelmente atualizado).
        return i;
    }
    
    //FIM - INSERÇÃO EM JAVA COM RETORNO DE REFERÊNCIA

    // INÍCIO - INSERÇÃO EM JAVA COM PASSAGEM DE PAI

    public void inserirPai01(int x){
        // Verifica se a árvore está vazia (raiz é null)
        if (raiz == null){
            // Se estiver vazia, cria um novo nó com o valor x e o define como raiz
            raiz = new No(x);
        } else if (x < raiz.elemento){
            // Se x é menor que o elemento da raiz, chama o método inserirPai02
            // passando o filho esquerdo da raiz e a raiz como pai
            inserirPai02(x, raiz.esq, raiz);
        } else if (x > raiz.elemento){
            // Se x é maior que o elemento da raiz, chama o método inserirPai02
            // passando o filho direito da raiz e a raiz como pai
            inserirPai02(x, raiz.dir, raiz);
        } else {
            // Se x é igual ao elemento da raiz, significa que o valor já existe na árvore
            System.out.println("ERRO em inserirPai01");
        }
    }

    public void inserirPai02(int x, No filho, No pai){
        // Verifica se o nó atual (filho) é null
        if (filho == null){
            // Caso base: encontramos a posição correta para inserir o novo nó
            if (x < pai.elemento){
                // Se x é menor que o elemento do pai, insere à esquerda
                pai.esq = new No(x);
            } else {
                // Se x é maior que o elemento do pai, insere à direita
                pai.dir = new No(x);
            }
        } else if (x < filho.elemento){
            // Se x é menor que o elemento do nó atual (filho),
            // chama recursivamente para o filho esquerdo
            // O nó atual (filho) se torna o novo pai
            inserirPai02(x, filho.esq, filho);
        } else if (x > filho.elemento){
            // Se x é maior que o elemento do nó atual (filho),
            // chama recursivamente para o filho direito
            // O nó atual (filho) se torna o novo pai
            inserirPai02(x, filho.dir, filho);
        } else {
            // Se x é igual ao elemento do nó atual, o valor já existe na árvore
            System.out.println("ERRO em inserirPai02");
        }
    }
    // FIM - INSERÇÃO EM JAVA COM PASSAGEM DE PAI

    // PESQUISAR

    public boolean pesquisarEncapsulado (int x){
        return pesquisar (x, raiz);
    }

    public boolean pesquisar (int x, No i){

        boolean resp;

        if (i == null){
            //chegou até o final da árvore e não achou o elemento OU a árvore estava vazia
            resp = false;
        }else if (x == i.elemento){
            resp = true;
        }else if (x < i.elemento){
            resp = pesquisar(x, i.esq);
        }else{
            resp = pesquisar(x, i.dir);
        }

        return resp;
    }

    //CAMINHAR CENTRAL
    /*A recursão desce até o nó mais à esquerda, chamando caminharCentral(i.esq) repetidamente.
    Quando i == null, a função retorna, e voltamos um nível na recursão.
    Imprimimos o elemento do nó atual.
    Chamamos caminharCentral(i.dir) para percorrer a subárvore direita.
    O processo se repete, percorrendo toda a árvore em ordem.
    */

    void caminharCentral (No i){

        if (i != null){

            caminharCentral(i.esq);

            System.out.print(i.elemento + " ");

            caminharCentral(i.dir);
        }
    }

    //PÓS-ORDEM OU PÓS-FIXADO
    //de baixo para cima. das folhas para a raiz.

    void caminharPos (No i){

        if (i != null){

            caminharPos(i.esq);

            caminharPos(i.dir);

            System.out.print(i.elemento + " ");
            
        }
    }

    //PRÉ-ORDEM OU PRÉ-FIXADO
    //caminhamento hierárquico. pais antes dos filhos.

    void caminharPre (No i){

        if (i != null){

            System.out.print(i.elemento + " ");

            caminharPre(i.esq);

            caminharPre(i.dir);

        }
    }


}