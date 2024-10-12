#include <stdio.h>
#include <string.h>
#include <ctype.h>

int main(){
	
	char mystring[100];
	
	int fim = 0;
	
	while (fim == 0){
		
		// usar fgets para ler a linha inteira (lê espaços em branco tb, para só quando encontra \n)
		fgets(mystring, 100, stdin);
		
		//poderia ser scanf("%[^\n]", mystring); mas não funcionou 
		
		// remover o caractere de nova linha no final (se tiver) e trocar por \0
		mystring[strcspn(mystring, "\n")] = '\0';
		//obs.: The strcspn() function finds the first occurrence of a character in string1 that belongs to the set of characters that is specified by string2. Null characters are not considered in the search.
		
		//se a palavra for FIM, a condição do loop não é mais satisfeita
		if (strcmp(mystring, "FIM") == 0) {
			fim = 1;
		}
		
		//apenas prossegue SE a palavra NAO for FIM
		if (fim == 0){
			
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
		
	}
	
	
	return 0;
	
}
