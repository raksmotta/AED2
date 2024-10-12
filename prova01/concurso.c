#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

int* insertion (int* candidatos, int n){

    for (int i = 0; i < n; i++){

        int tmp = candidatos [i];

        int j = i - 1;

        while (j >= 0 && candidatos[j] > tmp){

            candidatos [j+1] = candidatos [j];

            j--;

        }

        candidatos [j+1] = tmp;

    }

    return candidatos;

}

int main (){

    int n = 0; //numero de candidatos
    int k = 0; //numero minimo de aprovados

    scanf ("%d", &n); getchar();

    scanf ("%d", &k); getchar();

    int* candidatos = (int*)malloc(n*sizeof(int));

    int c = 0;

    for (int x = 0; x < n; x++){

        scanf ("%d", &c);
        candidatos[x] = c;

    }

    int pos = n - k;

    //esta lendo tudo ok
    /*for (int x = 0; x < n; x++){

        printf("%d\n", candidatos[x]);

    }*/

    int* candidatos_sorteados = insertion(candidatos, n);

    /*for (int x = 0; x < n; x++){

        printf("%d\n", candidatos_sorteados[x]);

    }*/

    int notacorte = candidatos_sorteados[pos];

    printf ("%d\n", notacorte);

    return 0;
}