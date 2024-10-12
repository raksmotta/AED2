#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

//os caracteres a serem trocados tem que ser passados como parâmetros, pois caso contrário mudariam a cada vez que o loop mudasse de caractere numa mesma string
//a recursividade será baseada em analisar um caractere por vez. a condição de base é quando analisarmos todos os caracteres da string, ou seja, quando encontramos o \0
char* alterar_recursivo (char* s, int pos, char m1, char m2){
	
	char c = s[pos];
	
	//se chegamos na condição de base e já analisamos/modificamos a string toda, retorna o ponteiro para a string alterada
	if(c == '\0'){
		
		return s;
		
	}
	
	//SE o caractere analisado for o sorteado para ser substituído, fazemos a troca
	if(c == m1){
		
		s[pos] = m2;
		
	}
	
	//independente se a substituição ocorre ou não, passamos para o próximo caractere
	return alterar_recursivo(s, pos+1, m1, m2);
	
}


//deixei a função iterativa aqui só para referência
void alterar (char* s){
	
	char m1 = abs(rand()) % 26;
	
	char m2 = abs(rand()) % 26;
	
	//o último caractere está na posição qtd de caracteres - 1
	int tam = strlen(s) - 1;
	
	for (int i = 0; i <= tam; i ++){
		
		if (s[i] == m1){
			
			s[i] = m2;
			
		}
	}
	
	printf("%s\n", s);
	
}

int main (){
	
	srand(time(NULL));
	//srand(4);
	
	//alocando dinamicamente a string para que ela possa ser modificada por uma função ao ser passada como parâmetro
	char* text = (char*)malloc(800 * sizeof(char));
	//char myString [800] = "inicio";
	
	//se as strings forem iguais, strcmp retorna 0
	while (strcmp(text, "FIM") != 0){
		
		//somamos 'a' com um valor entre 0 e 25 para achar a posição do novo caractere
		//abs retorna o valor absoluto de um inteiro (não queremos números negativos)
		//ocorre um type casting implícito
		char m1 = 'a' + (abs(rand()) % 26);
		
		char m2 = 'a' + (abs(rand()) % 26);
		
		fgets(text, 800, stdin);
		//também poderia usar scanf("%[^\n]%*c", myString);
		
		//substituímos o \n (se existir) no final da linha pelo \0
		//a função strcspn encontra a primeira ocorrência do caractere na string
		text[strcspn(text, "\n")] = '\0';
		
		//só prossegue SE a string lida não for FIM
		if (strcmp(text, "FIM") != 0){
			
			char* resultado = alterar_recursivo(text, 0, m1, m2);
			
			printf("%s\n", resultado);
		}
		
	}
	
	free(text);
	
	return 0;
}
