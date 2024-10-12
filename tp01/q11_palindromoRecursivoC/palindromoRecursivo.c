#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

bool is_palindromo_recursivo (char* s, int start, int end){
	
	//a recursão será baseada em analisar um par de caracteres por vez, passando de par em par até que a string inteira seja finalizada.
	//se os valores de start e end foram iguais, quer dizer que estamos comparando uma letra com ela mesma, o que não é necessário. se chegar até aqui sem retornar, sabemos que é um palíndromo
	if (start >= end){
		
		return true;
		
	}
	
	//caso caracteres em posições espelhadas não sejam iguais, já não é palíndromo e podemos retornar falso
	if (s[start] != s[end]){
		
		return false;
		
	}else{
		
		//caso os caracteres sejam iguais, passamos para analisar o próximo par, movendo o start 1 caractere para frente e o end 1 caractere para trás
		return is_palindromo_recursivo(s, start+1, end-1);
		
	}
	
	
}

//deixei a função iterativa aqui só para referência
int is_palindromo(char* s){
	
	//quantidade de caracteres na string
    int tamanho = strlen(s);
	
	//printf("%d", tamanho);
	
	//o último caractere da string está localizado na posição tamanho - 1;
    int end = tamanho - 1;

    for (int start = 0; start < end; start++){

        if (s[start] != s[end]){

            return -1;
			
        }else{

            end--;
			
        }
    }

    return 0;

}

int main (){

    char myString [800];

    //se as strings forem iguais, strcmp retorna 0
    while (strcmp(myString, "FIM") != 0){

        scanf("%[^\n]%*c", myString);
		
		//só prossegue SE a string lida não for FIM
		if (strcmp(myString, "FIM") != 0){
			
			int pos_last_char = strlen(myString) - 1;
			
			bool a = is_palindromo_recursivo(myString, 0, pos_last_char);
			
			if (a){
				printf("SIM\n");
			}else{
				printf("NAO\n");
			}
			
		}
		
    }

    return 0;
}
