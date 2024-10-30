#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>
#include <locale.h>
#include <time.h>

//variáveis globais que serão incrementadas dentro do algoritmo cuja complexidade desejamos analisar
int COMP = 0;
int MOVE = 0;

//CONTAR O NÚMERO DE COMPARAÇÕES, O NÚMERO DE MOVIMENTAÇÕES DE REGISTROS NO VETOR E O TEMPO DE EXECUÇÃO DE UM ALGORITMO.
double diff(clock_t start, clock_t end){
	return ((double)(end - start)) / CLOCKS_PER_SEC;
}

void kelComplex(const char* fileName, double time){
	FILE* fil = fopen(fileName, "w");
	if(fil){
		fprintf(fil, "Matrícula: 854017\t");
		fprintf(fil, "Tempo de Execução: %lf\t", time);
		fprintf(fil, "Comparações: %d\t", COMP);
		fprintf(fil, "Movimentações: %d", MOVE);
		fclose(fil);
	}
	else printf("Erro ao abrir o arquivo");
}

//exemplo de uso:
void selectionSort (int* vetor, int n){

	for (int i = 0; i < n-1; i++){

		int menor = i;

		for (int j = i+1; j < n; j++){

			COMP++;

			if (vetor[j] < vetor[menor]){
				menor = j;
			}
		}

		//swap
		MOVE += 3;
		int tmp = vetor[i];
		vetor[i] = vetor[menor];
		vetor[menor] = tmp;
	}
}

int main () {

	setlocale(LC_ALL, "Portuguese");
	setlocale(LC_NUMERIC, "C");

	int myNumbers[] = {25, 50, 75, 100};

	clock_t start = clock();

	clock_t end = clock();

	kelComplex("854017_selectionSortC.txt", diff(start, end));

	printf("vetor ordenado:");

	for (int x = 0; x < 4; x++){

		printf("%d ", myNumbers[x]);

	}

	return 0;
}
