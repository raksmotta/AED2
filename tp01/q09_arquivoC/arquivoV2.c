#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>

//ler input do teclado e gravar em arquivo
int ImprimirInteirosArquivo(char *fileName) {
	
    //abre o arquivo para escrever os números reais que serão inputados
	FILE *file = fopen(fileName, "wt");

    //retorna -1 para indicar erro
    if (file == NULL) {
        return -1;
    }

    //variável para guardar a quantidade de números
    int qtd = 0;
    //ler do teclado
    scanf("%d", &qtd);
    getchar();
    
	double real = 0.0;

    int linha = 0;

    //imprime da linha 0 até a linha 79 (80 números)
    while (linha < qtd){

        //lê o input do número real
        scanf("%lf", &real);

        //imprime no arquivo
        fprintf(file, "%lf\n", real);

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
    FILE* arquivo = fopen(fileName, "rt");

    //retorna -1 para indicar erro
    if (arquivo == NULL) {
        return -1;
    }

    // Posiciona o ponteiro no final do arquivo para começar a ler de trás para frente
    fseek(arquivo, 0, SEEK_END);

    //ftell retorna a posição atual do ponteiro do arquivo. ftell após ter movido o ponteiro para o final do arquivo retornará o tamanho total do arquivo em bytes.
    long fileSize = ftell(arquivo);
    long position = fileSize;

    double real;

    //loop para ler de trás para frente
    for (int i = 0; i < qtd; i++) {

        //como não sabemos exatamente o número de dígitos/tamanho, usaremos o \n para saber quando começa um novo real
        bool quebraLinha = false;

        while (position > 0 && !quebraLinha) {

            //decrementamos a variável position em 1 antes de usá-la como argumento. o objetivo é mover o ponteiro do arquivo 1 byte (nao necessariamente 1 caractere) para trás cada vez que o loop executa. inicialmente, position é igual ao tamanho do arquivo (fileSize), então este comando começa do final do arquivo e vai retrocedendo.
            position --;
            fseek(arquivo, position, SEEK_SET);

            //fgetc() lê um único caractere do arquivo na posição atual do ponteiro do arquivo e avança o ponteiro para a próxima posição.
            //quando achamos um \n, queremos ler a linha logo abaixo, então o programa sai do while e para de retroceder posições.
            if (fgetc(arquivo) == '\n') {

                quebraLinha = true;                

            }

        }

        //lê o número APÓS a quebra de linha
        //o fscanf avança o ponteiro do arquivo para o final do número lido
        if (fscanf(arquivo, "%lf", &real) == 1) {
            printf("%lf\n", real);

            //recua um byte para compensar o avanço da leitura do número
            fseek(arquivo, -1, SEEK_CUR); 
        }

    }

   fclose(arquivo);

}

int main (){

    int a = ImprimirInteirosArquivo("saida.txt");

    if (a != -1){

        int b = readFileBackwards("saida.txt", a);

    }

    return 0;
}
