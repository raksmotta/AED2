#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <locale.h>

void is_palindromo(char *str)
{

	int tam = 0;

	// descobrindo o numero de caracteres (inclusive espaços em branco)
	while (str[tam] != '\0')
	{
		tam++;
	}

	// o ultimo caractere esta na posicao tamanho - 1 (já que começamos a contar na posicao 0)
	int end = tam - 1;

	//variavel para condição de imprimir o SIM após a análise da string
	int nao = 0;


	// vai comparando os caracteres "de fora para dentro"
	for (int start = 0; start < tam - 1; start++)
	{

		// os caracteres precisam ser letras diferentes do alfabeto (uma letra em sua versão minúscula, quando comparada com a maiúscula, são considerados dois caracteres DIFERENTES. por isso NÃO precisamos de adicionar  && tolower(mystring[start]) != mystring[end] && tolower(mystring[end]) != mystring[start] na condição
		if (str[start] != str[end])
		{
			printf("NAO\n");

			nao = 1;

			//para NÃO CUMPRIR mais a condição do loop e encerrar as repetições (se 2 letras são diferentes, já não é um palíndromo e não precisamos analisar o restante da string)
			start = tam + 2;
		}
		else
		{

			end--;
		}
	}

	//após sair do FOR de cima, imprime SIM se a variável não permanecer igual a zero
	if (nao == 0)
	{

		printf("SIM\n");
	}
}

int main()
{
	setlocale(LC_ALL, "Portuguese");

	char mystring[800];

	int fim = 0;

	while (fim == 0)
	{

		// usar fgets para ler a linha inteira (lê espaços em branco tb, para só quando encontra \n)
		fgets(mystring, 800, stdin);
		// poderia ser scanf("%[^\n]", mystring); mas não funcionou

		// remover o caractere de nova linha no final (se tiver) e trocar por \0
		mystring[strcspn(mystring, "\n")] = '\0';
		// obs.: The strcspn() function finds the first occurrence of a character in string1 that belongs to the set of characters that is specified by string2. Null characters are not considered in the search.

		// se a palavra for FIM, a condição do loop não é mais satisfeita
		if (strcmp(mystring, "FIM") == 0)
		{
			fim = 1;
		}

		// apenas prossegue e chama a funcao para checar palindromo SE a palavra NAO for FIM
		if (fim == 0)
		{
			is_palindromo(mystring);
		}
	}

	return 0;
}
