#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>

//ler input do teclado e gravar em arquivo
int ImprimirInteirosArquivo(char *fileName) {
	
    //abre o arquivo para escrever os números reais que serão inputados
	FILE *file = fopen(fileName, "wb");

    //retorna -1 para indicar erro
    if (file == NULL) {
        return -1;
    }

    //variável para guardar a quantidade de números
    int qtd = 0;

    //printf("quantos numeros vc quer imprimir no arquivo?");

    //o while garante que a quantidade seja positiva
    while (qtd <= 0 ){
        scanf("%d", &qtd);
        getchar();
    }
    
	double real = 0.0;

    int linha = 0;

    //imprime da linha 0 até a linha 79 (80 números)
    while (linha < qtd){

        //lê o input do número real
        scanf("%lf", &real);

        //imprime no arquivo
        fwrite(&real, sizeof(double), 1, file);

        linha ++;
    }

    //fecha o arquivo após escrita
    fflush(file); 
	fclose(file);
	
	return qtd;
}

//ler arquivo de trás para frente e mostrar na tela os números lidos
int readFileBackwards (char *fileName, int qtd){

    //reabre o arquivo para leitura
    //vamos ler no modo binário pois não sabemos a quantidade de dígitos de cada número
    /*OBSERVAÇÃO: Funções como fseek() e ftell() são usadas para mover o ponteiro de arquivo e determinar a posição atual no arquivo, respectivamente. Em binário, calcular o deslocamento é simples porque cada double sempre ocupa o mesmo número de bytes (tipicamente 8 bytes em muitas plataformas).*/
    FILE* arquivo = fopen(fileName, "rb");

    //retorna -1 para indicar erro
    if (arquivo == NULL) {
        return -1;
    }

    double real = 1.2;

    //i = 80 - 1, pois o último real está na posição 79 e o primeiro está na posição 0
    for (int i = qtd - 1; i >= 0; i--) {

        fseek(arquivo, i * sizeof(double), SEEK_SET);
        fread(&real, sizeof(double), 1, arquivo);
        printf("%g\n", real);
        //o %g remove zeros desnecessários
    
    }

    /*OBSERVAÇÃO - funcionamento da fseek
    int fseek(FILE *stream, long offset, int whence);
    stream: O ponteiro para o arquivo (FILE *) onde você deseja mover o ponteiro de posição.
    offset: Um número que representa quantos bytes você quer mover a partir da posição especificada por whence.
    whence: A posição inicial a partir da qual o offset é aplicado. Pode ser uma das seguintes constantes:
        SEEK_SET: Início do arquivo.
        SEEK_CUR: Posição atual do ponteiro do arquivo.
        SEEK_END: Final do arquivo.
    */

   fclose(arquivo);

}

int main (){

    int a = ImprimirInteirosArquivo("saida.txt");

    if (a != -1){

        int b = readFileBackwards("saida.txt", a);

    }

    return 0;
}
