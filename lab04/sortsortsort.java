import java.util.Scanner;
//import java.util.ArrayList;
//import java.util.List;

public class sortsortsort {


    //literalmente igualzinho um insertion sort normal mas ao invés de trocar inteiros de posição em um array estou trocando estruturas de dados em um vetor do tipo lista
    public static void insertionSortCelulasResto(KelList lista, int n) {

        for (int i = 1; i < n; i++) {

            Cell current = lista.get(i);
            int j = i - 1;

            //comparar o elemento atual com o elemento antes dele e trocá-los se estiverem na ordem errada
            while (j >= 0 && lista.get(j).getResto() > current.getResto()) {

                lista.set(j + 1, lista.get(j));
                j--;

            }

            lista.set(j + 1, current);

        }

    }

    //DEPOIS de fazer o sort de acordo com os restos (que é estável e mantém a ordem original de registros iguais), agora vamos fazer o sort específico do exercício
    public static void insertionSortCelulasNum(KelList lista, int n){

        for (int i = 1; i < n; i++){

            Boolean cont = true;

            Cell current = lista.get(i);

            int j = i - 1;

            //mesmo esquema do anterior. MAS é importantíssimo interromper o loop se entrar no while (restos iguais) MAS nenhuma das condições de troca for satisfeita. caso contrário iria fazer comparações sem antes mover os elementos e terámos perda/duplicação de dados
            //ter em mente que, se não fizermos nada, current sempre vem antes de j
            while (j >= 0 && cont && lista.get(j).getResto() == current.getResto()){

                if ((current.getNum() % 2 != 0 && lista.get(j).getNum() % 2 != 0 && current.getNum() > lista.get(j).getNum()) ||
                    (current.getNum() % 2 == 0 && lista.get(j).getNum() % 2 == 0 && current.getNum() < lista.get(j).getNum()) ||
                    (current.getNum() % 2 != 0 && lista.get(j).getNum() % 2 == 0)) {
    
                    lista.set(j + 1, lista.get(j));
                    j--;

                } else if (current.getNum() % 2 == 0 && lista.get(j).getNum() % 2 != 0) {

                    //sair do loop se current for par e lista.get.(j) for ímpar, o que significa que o current já está na posição certa
                    cont = false;

                } else {

                    //se nenhuma das condições for satisfeita, parar de mover as células e sair do loop
                    cont = false;

                }
            }

            //colocar current (i) na posição correta após ter movido os outros
            lista.set(j + 1, current);
        }
    }
    

    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);

        int n = 21;
        int m = 21;
        //o "num" das células estou chamando de "a" aqui
        int a = 0;

        while (n != 0 && m != 0){

            n = sc.nextInt();
            m = sc.nextInt();
            sc.nextLine();

            System.out.println(n + " " + m);
            
            if (n != 0 && m != 0){

                KelList lista = new KelList(n);
    
                for (int i = 0; i < n; i ++){
        
                    a = sc.nextInt();
        
                    lista.add(new Cell(a, m));
        
                    sc.nextLine();
                }
        
                insertionSortCelulasResto(lista, n);
        
                insertionSortCelulasNum(lista, n);
        
                for (int x = 0; x < n; x ++){
        
                    Cell c = lista.get(x);
                    //teste: System.out.println("resto: " + c.getResto() + ", numero: " + c.getNum());
                    System.out.println(c.getNum());
        
                }

            }

        }

        sc.close();
        
    }

}

class Cell{

    public int num;
    public int resto;
    public int m;

    public Cell(int num, int m) {
        this.num = num;
        this.m = m;

        //JÁ calculo o módulo e deixo guardado aqui
        this.resto = num % m;
    }

    public int getNum() {
        return num;
    }

    public int getResto() {
        return resto;
    }

}

//criando minha própria LISTA
class KelList {

    //um array de células
    public Cell[] celulas;
    public int size;

    //construtor sem parâmetros
    public KelList() {
        //capacidade inicial padrão
        this.celulas = new Cell[10]; 
        this.size = 0;
    }

    //construtor com parâmetros (coloquei n + 10 só por precaução mesmo)
    public KelList(int n) {
        this.celulas = new Cell[n+10]; 
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public Cell get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return celulas[index];
    }

    public Cell set(int index, Cell c) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Cell antiga = celulas[index];
        celulas[index] = c;
        return antiga;
    }

    //para não tentarmos adicionar numa lista que nao tem capacidade para mais uma célula, fazemos essa verificação:
    public void ensureCapacity() {

        if (size == celulas.length) {
            int newSize = celulas.length * 2;

            Cell[] novasCelulas = new Cell[newSize];

            System.arraycopy(celulas, 0, novasCelulas, 0, celulas.length);
            celulas = novasCelulas;
        }
        
    }

    //a cada vez que adicionamos uma célula, PÓS INCREMENTAMOS o size
    public boolean add(Cell c) {
        ensureCapacity();
        celulas[size++] = c;
        return true;
    }

}