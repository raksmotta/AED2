#include <stdio.h>
#include <string.h>
#include <ctype.h>

int main() {
	
	char s1 [100];
	char s2 [100];
	
	//ler duas palavras na mesma linha, que estão separadas apenas por um espaço (não precisamos do & para ler string, pois s1 já é um ponteiro para array de caracteres)
	//se a leitura for bem sucedida, retorna 2 (1 para cada string lida). Para no final do arquivo (EOF)
	while (scanf("%s %s", s1, s2) == 2){
		
		//descobrindo o tamanho das strings
		int tam1 = strlen(s1);
		int tam2 = strlen(s2);
		
		//printf("\ntamanho da string 1: %d", tam1);
		
		//printf("\ntamanho da string 2: %d", tam2);
		
		//o tamanho da string de resultado será a soma da qtd de caracteres da s1 com a s2 SOMADO a 1, que será o '\0'.
		//é importante alocar um espaço extra para o caractere nulo
		int tamfinal = tam1 + tam2 + 1;
		char result [tamfinal];
		//printf("\ntamanho final: %d", tamfinal);
		
		//contadores de posições
		int a = 0;
		int b = 0;
		int c = 0;
		
		//usar contadores diferentes para s1 e s2 nos permite implementar uma condição composta, que interrompe o loop quando os caracteres da string menor acabarem
		while (a < tam1 && b < tam2){
			
			result[c] = s1[a];
			
			c++;
			
			result[c] = s2[b];
			
			a++;
			b++;
			c++;
			
		}
		
		//SE a condição a<tam1 ainda não tiver sido declarada como falsa, a s1 é a string maior. Anexamos seus caracteres em sequência no final da string de resultado
		while (a < tam1){
			result[c] = s1[a];
			c++;
			a++;
		}
		
		//SE a condição b<tam2 ainda não tiver sido declarada como falsa, a s2 é a string maior. Anexamos seus caracteres em sequência no final da string de resultado
		while (b < tam2){
			result[c] = s2[b];
			c++;
			b++;
		}
		
		//adicionamos o terminador nulo no final para que a string possa ser lida e impressa corretamente
		result[c] = '\0';
		
		printf("%s", result);
		
		printf("\n");
		
	}
	
	return 0;
	
}
