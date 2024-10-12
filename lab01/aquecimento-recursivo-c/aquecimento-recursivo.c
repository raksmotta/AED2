#include <stdio.h>
#include <string.h>
#include <ctype.h>

//nessa segunda versao, achei melhor separar a funcao da main para ficar mais organizado e claro. mantive a iterativa aqui só para comparação
void countMaiusculas (char* mystring){
	
	int tam = 0;
	
	//descobrindo o numero de caracteres (inclusive espaços em branco) 
	while(mystring[tam] != '\0'){
		
		tam ++;
		
	}
	
	//o ultimo caractere esta na posicao tamanho - 1 (já que começamos a contar na posicao 0)
	int end = tam - 1;
	
	int maiuscula = 0;
	
	//checa os caracteres um por um
	for (int pos = 0; pos <= end; pos++){
		
		//letras maiusculas estão entre os caracteres A e Z da tabela ASCII
		if('A' <= mystring[pos] && mystring[pos] <= 'Z'){
			
			maiuscula ++;
			
		}
		
	}
	
	printf("%d\n", maiuscula);
}

//a recursão da funcão será baseada em analisar cada vez o próximo caractere e retornar +1 se ele for maiusculo, obtendo a soma total no final
//o caso base, ou seja, quando a função para, será quando o '\0' for alcançado
//o inteiro pos é passado como parâmetro contador da recursividade. se eu declarasse int pos = 0; dentro da função, a variável seria resetada a 0 a cada chamada e as posições não avançariam
int countMaiusculasRecursiva (char* mystring, int pos){
	
	//se o caractere for igual a \0, interrompe a recursividade e não se chama de novo
	if (mystring[pos] == '\0'){
		
		return 0;
		
	}else{
		
		//posso declarar essa variavel dentro da função recursiva pois quero que ela seja resetada a cada ciclo
		int maiuscula = 0;
		
		//o if else a seguir poderia ser substituido por: int maiuscula = ('A' <= mystring[pos] && mystring[pos] <= 'Z') ? 1 : 0;
		//é um operador ternário, um if else mais enxuto.
		//o modelo é o seguinte: tipo nomevariavel = (condição) ? expressãoseverdadeiro : expressãosefalso;
		if('A' <= mystring[pos] && mystring[pos] <= 'Z'){
			
			maiuscula = 1;
			
		}else{
			
			maiuscula = 0;
			
		}
		
		//recursao para somar o resto das letras
		return maiuscula + countMaiusculasRecursiva(mystring, pos+1);
		
	}
	
}

int main(){
	
	char mystring[100];
	
	int fim = 0;
	
	while (fim == 0){
		
		// usar fgets para ler a linha inteira (lê espaços em branco tb, para só quando encontra \n)
		fgets(mystring, 100, stdin);
		
		//poderia ser scanf("%[^\n]", mystring); mas não funcionou 
		
		// remover o caractere de nova linha no final (se tiver) e trocar por \0 para termos uma string contínua
		mystring[strcspn(mystring, "\n")] = '\0';
		//obs.: The strcspn() function finds the first occurrence of a character in string1 that belongs to the set of characters that is specified by string2. Null characters are not considered in the search.
		
		//se a palavra for FIM, a condição do loop não é mais satisfeita
		if (strcmp(mystring, "FIM") == 0) {
			fim = 1;
		}
		
		//apenas prossegue SE a palavra NAO for FIM
		if (fim == 0){
			
			//chama a funcao que conta as maiusculas (a recursiva não imprime)
			int m = countMaiusculasRecursiva(mystring, 0);
			
			//imprimindo as maiusculas
			printf("%d\n", m);
			
		}
		
	}
	
	return 0;
}
