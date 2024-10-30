import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class Jcomplexidade{

    public static int[] selectionsort (int[] vetor, int n, KelComp complexidade){

        for (int i = 0; i < n-1; i++){

		    int menor = i;

		    for (int j = i+1; j < n; j++){

			    complexidade.comp();

			    if (vetor[j] < vetor[menor]){
				    menor = j;
			    }
		    }

            //swap
            complexidade.move(3);
            int tmp = vetor[i];
            vetor[i] = vetor[menor];
            vetor[menor] = tmp;
	    }

        return vetor;
    }

    public static void main(String[] args) {

        int[] myNumbers = new int [5];
        myNumbers[0] = 220;
        myNumbers[1] = 10;
        myNumbers[2] = 12;
        myNumbers[3] = 5;
        myNumbers[4] = 1;

        KelComp complexidade = new KelComp();

        int[] sortedNumbers = selectionsort(myNumbers, 5, complexidade);

        complexidade.end();

        System.out.println(Arrays.toString(sortedNumbers));

        try {
            complexidade.print("854017_selectionSortJAVA.txt");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class KelComp{

	private Instant start;
	private Instant end;
	private int comparisons;
	private int movements;

	KelComp(){
		this.start = Instant.now();
		this.comparisons = 0;
		this.movements = 0;
	}

	public void comp(){
		this.comparisons++;
	}

	public void comp(int x){
		this.comparisons += x;
	}

	public void move(){
		this.movements++;
	}

	public void move(int x){
		this.movements += x;
	}

	void end(){
		this.end = Instant.now();
	}

	double diff(){
		return Duration.between(this.start, this.end).toMillis() / 1000.0;
		//ou .getNano() / 1000000000.0;
	}

	void print(String fileName) throws Exception{
		PrintWriter write = new PrintWriter(new FileWriter(fileName));
		write.printf("Matrícula: 854017\t");
		write.printf("Tempo de execução: " + diff() + "\t");
		write.printf("Comparações: " + comparisons + "\t");
		write.printf("Movimentações: " + movements + "\t");
		write.close();
	}
	
}
