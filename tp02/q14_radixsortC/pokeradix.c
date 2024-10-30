#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>
#include <locale.h>

#define MAX_TYPES 10
#define MAX_ABILITIES 10

typedef struct structpokemon {

	int id;
	int generation;
	char* name;
	char* description;

	//arrays de strings
	char* types [MAX_TYPES];
	char* abilities [MAX_ABILITIES];

	int types_size;
	int abilities_size;

	double weight;
	double height;
	int captureRate;
	bool isLegendary;
	char* captureDate;

} pokemon;

//construtor passando a linha do id desejado
pokemon* parse_poke_line (const char* line) {

	//fazendo uma cópia da linha. vamos manipular essa cópia
	char* line_copy = strdup(line);

	if (!line_copy) {
		fprintf(stderr, "alocação de memória para linha cópia na funcao parse_poke_line falhou\n");
		return NULL;
	}

	//criando novas strings e achando as posicoes dos caracteres '[' e ']'
	//A função strchr ()retorna um ponteiro para a primeira ocorrência do caracter procurado ou NULL caso não encontre.
	char* start_bracket = strchr(line_copy, '[');
	char* end_bracket = strchr(line_copy, ']');

	//extrair a parte de dentro dos couchetes
	size_t betweenbrackets_len = end_bracket - start_bracket - 1;

	char* betweenbrackets = (char*)malloc(betweenbrackets_len + 1);

	if (!betweenbrackets) {
		fprintf(stderr, "nao conseguiu alocar memoria para betweenbrackets na funcao parse_poke_line\n");
		free(line_copy);
		return NULL;
	}

	strncpy(betweenbrackets, start_bracket + 1, betweenbrackets_len);

	betweenbrackets[betweenbrackets_len] = '\0';

	//adicionar o terminador \0 nas partes antes E depois dos [ ]

	//esse ponteiro apontava para [. substituímos o caractere apontado por ele pelo \0 para finalizar a string line_copy
	*start_bracket = '\0';

	//fizemos o ponteiro mover 1 casa e apontar para uma posição depois do ]
	end_bracket++;

	//só mudamos o nome aqui
	char* after_bracket = end_bracket;

	//agora temos TRÊS STRINGS:
	//line_copy: ANTES de [
	//betweenbrackets: DENTRO de []
	//after_bracket: DEPOIS de ]


	//dividir a string antes do [ usando strtok e a vírgula como delimitador
	//array de strings (cabe 20 strings)
	char* tokens_before[20];
	int token_count_before = 0;
	char* token = strtok(line_copy, ",");

	while (token != NULL) {

		//token_count_before serve para acessar cada vez a posição seguinte do nosso array de strings
		tokens_before[token_count_before++] = token;

		token = strtok(NULL, ",");
	}

	//dividir a string depois do ] usando strtok e a vírgula como delimitador
	char* tokens_after[10];
	int token_count_after = 0;
	token = strtok(after_bracket, ",");
	while (token != NULL) {
		tokens_after[token_count_after++] = token;
		token = strtok(NULL, ",");
	}

	//criar um novo pokemon
	pokemon* p = (pokemon*)malloc(sizeof(pokemon));

	//inicializar os arranjos de strings
	for (int i = 0; i < MAX_TYPES; i++) p->types[i] = NULL;
	for (int i = 0; i < MAX_ABILITIES; i++) p->abilities[i] = NULL;

	int index = 0;

	//sabemos que os 4 primeiros elementos CERTAMENTE existem e estao nessa ordem

	//The C stdlib library atoi() function is used to convert a numeric string into an integer value.
	p->id = atoi(tokens_before[index++]);

	p-> generation = atoi(tokens_before[index++]);

	p->name = strdup(tokens_before[index++]);

	p->description = strdup(tokens_before[index++]);

	//sempre tem pelo menos um tipo
	int types_count = 0;

	while (index < token_count_before) {

		if (strcmp(tokens_before[index], "yy") == 0 || strcmp(tokens_before[index], "") == 0) {

			p->types[types_count++] = strdup("0");

		} else {

			p->types[types_count++] = strdup(tokens_before[index]);

		}

		index++;
	}

	//o último token que ficará guardado no array de tipos é simplesmente "
	p->types_size = types_count - 1;

	//agora vamos dividir a string ENTRE os [] com o strtok baseado na vírgula

	int abilities_count = 0;
	char* abilities_token = strtok(betweenbrackets, ",");
	
	while (abilities_token != NULL){
		
		//remover caracteres indesejados do INÍCIO movendo o ponteiro de início de cada token
		//*token: The character currently pointed to by the token.
		//Advances the token pointer past any leading spaces or quotes.
		//Result: food_token now points to the first character of the actual food item, with no leading spaces or quotes.
		while (*abilities_token == ' ' || *abilities_token == '\''|| *abilities_token == '"') abilities_token++;
		
		//remover caracteres indesejados do FINAL da palavra
		//end aponta para o último caractere do token atual (current token). calculamos o endereço somando a posicao do ponteiro de incio com o tamanho da string
		char* end = abilities_token + strlen(abilities_token) - 1;
		
		//enquanto nao chegamos no inicio da token, usamos o end para substituir o caractere indesejado final por um terminador nulo, encurtando efetivamente a string
		while (end > abilities_token && (*end == ' ' || *end == '\'' || *end == '"')) {
			*end = '\0';
			end--;
		}
		//resultado: todos os espaços e aspas finais são removidos e o token é devidamente finalizado com o \0
		
		//guardar o token limpo
		p->abilities[abilities_count++] = strdup(abilities_token);
		
		//vai para o próximo token
		abilities_token = strtok(NULL, ",");
	}
	
	p->abilities_size = abilities_count;
	
	//agora só faltam os dados finais! que sabemos que estão numa ordem específica com certeza
	//começamos no ínidice 1 pois sabemos que o primeiro token é "
	int after_index = 1;
	
	if (strcmp(tokens_after[after_index], "yy") != 0 && strcmp(tokens_after[after_index], "") != 0) {
		
		p->weight = atof(tokens_after[after_index]);
		
	} else {
		
		p->weight = 0.0;
		
	}
	
	after_index++;
	
	//altura
	if (strcmp(tokens_after[after_index], "yy") != 0 && strcmp(tokens_after[after_index], "") != 0) {
		
		p->height = atof(tokens_after[after_index]);
		
	} else {
		
		p->height = 0.0;
		
	}
	
	after_index++;
	
	//captureRate
	if (strcmp(tokens_after[after_index], "yy") != 0 && strcmp(tokens_after[after_index], "") != 0) {
		
		p->captureRate = atof(tokens_after[after_index]);
		
	} else {
		
		p->captureRate = 0;
		
	}
	
	after_index++;
	
	//legendário
	if (strcmp(tokens_after[after_index], "yy") != 0 && strcmp(tokens_after[after_index], "") != 0) {
		
		p->isLegendary = (atoi(tokens_after[after_index]) != 0);
		
	} else {
		
		p->isLegendary = 0;
		
	}
	
	after_index ++;
	
	//data
	if (strcmp(tokens_after[after_index], "yy") != 0 && strcmp(tokens_after[after_index], "") != 0) {
		p->captureDate = strdup(tokens_after[after_index]);
	} else {
		p->captureDate = strdup("0");
	}
	
	//LIMPANDO
	free(line_copy);
	free(betweenbrackets);
	
	return p;
}

void free_pokemon (pokemon* p){
	
	if(p){
		
		free(p->name);
		free(p->description);
		free(p->captureDate);
		
		for (int i = 0; i < p->types_size; i++) {
			if (p->types[i]) {
				free(p->types[i]);
			}
		}
		
		for (int i = 0; i < p->abilities_size; i++) {
			if (p->abilities[i]) {
				free(p->abilities[i]);
			}
		}
		
		free(p);
		
	}
}

void print_pokemon (pokemon* p){
	
	printf("[#%d -> %s: %s - [", p->id, p->name, p->description);
	
	
	for (int i = 0; i < p->types_size; i++){
		
		if (strcmp(p->types[i], "0") != 0){
			
			if(i != 0){
				printf(", ");
			}
			
			printf("'%s'", p->types[i]);
		
			
		}
		
	}
	
	printf("] - [");
	
	for (int i = 0; i < p->abilities_size; i++){
		printf("'%s'", p->abilities[i]);
		
		if(i != p->abilities_size - 1){
			printf(", ");
		}
		
	}
	
	printf("] - %.1lfkg - %.1lfm - %d%% - ", p->weight, p->height, p->captureRate);
	
	if(p->isLegendary){
		printf("true - ");
	}else{
		printf("false - ");
	}
	
	printf("%d gen] - %s\n", p->generation, p->captureDate);
	
}

//função para resolver o problema de duas vírgulas seguidas ,,
char* fill_empty_fields(const char* line) {

	int length = strlen(line);

	//conta quantos "campos vazios"
	int count = 0;

	//primeira iteração sobre a string para contar quantos campos vazios tem
	for (int i = 0; i < length; i++) {

		if (line[i] == ',') {

			int j = i + 1;
			int consecutive_commas = 0;

			while (j < length && line[j] == ',') {

				consecutive_commas++;
				j++;
				i++;
			}

			count += consecutive_commas;
		}
	}

	//preencherei os espaços vazios com "yy". Portanto temos que alocar 2 espaços + 1 para o \0
	int new_length = length + count * 2 + 1;

	//alocando memória para a nova string
	char* new_line = (char*)malloc(new_length);

	if (!new_line) {
		fprintf(stderr, "erro em alocar memória para newline\n");
		return NULL;
	}

	//segunda iteração sobre a string para construir a nova linha
	//é mais fácil construir uma nova linha do que editar a já existente, pois precisaríamos de ficar arredando os caracteres

	//index para a string de origem
	int src = 0;

	//index para a string de destino
	int dst = 0;

	//enquanto nao chegamos no final da linha original lida do arquivo
	while (line[src] != '\0') {

		//se acharmos uma vírgula
		if (line[src] == ',') {

			//copiamos essa vírgula para a mesma posição na nova linha e pós-incrementamos os index.
			//sempre mantemos a primeira vírgula normal
			new_line[dst++] = line[src++];

			//checando por vírgulas CONSECUTIVAS
			while (line[src] == ',') {

				// inserir "yy," na nova linha na posição em que estamos
				strcpy(&new_line[dst], "yy");

				//andar DUAS casas de posição na nova linha para os dois caracteres adicionados
				dst += 2;

				//adicionar a vírgula e pós incrementar a posição na nova linha
				new_line[dst++] = ',';

				//mover para a posição depois da vírgula consecutiva encontrada na linha original
				src++;
			}
		} else {

			//agora, se não acharmos nenhuma vírgula, é só copiar normal o caractere e passar para a próxima posição em ambas as strings
			new_line[dst++] = line[src++];
		}
	}

	//adicionar o null terminator à nova linha que construímos e retorná-la
	new_line[dst] = '\0';

	return new_line;
}

//funcao para ler a linha especifica desejada do arquivo de acordo com o id fornecido e processá-la para ser usada pela struct pokemon
//dentro dessa funcao chamamos a fill_empty_fields para tratar a linha!
char* read_and_fill_line(const char* fileName, int line_number) {

	FILE* file = fopen(fileName, "rt");

	if (!file) {
		fprintf(stderr, "erro ao abrir o arquivo");
		return NULL;
	}

	//buffer para guardar cada linha lida
	char buffer[1024];

	int current_line = 0;

	char* result = NULL;

	bool done = false;

	//ler linhas até que o ponteiro do arquivo alcance a posição desejada
	while (!done && fgets(buffer, sizeof(buffer), file)) {

		current_line++;

		if (current_line == line_number) {

			//se tiver \n, temos que removê-lo do final da string e garantir que ela termine com o \0
			size_t len = strlen(buffer);

			if (len > 0 && buffer[len - 1] == '\n') {

				buffer[len - 1] = '\0';
			}

			//processar os campos vazios da linha
			result = fill_empty_fields(buffer);

			//se lemos e processamos a linha que queríamos, the task is done! (coloquei essa condição p evitar de usar o break)
			done = true;
		}
	}

	//se por acaso nao sair do loop porque o done virou true e chegarmos até o final do arquivo
	if (current_line < line_number) {
		fprintf(stderr, "linha nao encontrada no arquivo");
	}

	fclose(file);

	//precisamos FREE essa memória onde a função for chamada!!!
	return result;
}

typedef struct structpokedex {

    int size;
    pokemon* arr;

} pokedex;

pokedex* create_pokedex (){

    pokedex* mypokedex = (pokedex*)malloc(sizeof(pokedex));

    mypokedex->arr = (pokemon*)malloc(300 * sizeof(pokemon));

    mypokedex->size = 0;
}

void add_pokedex (pokedex* mypokedex, pokemon poki){

    mypokedex->arr [mypokedex->size] = poki;

    mypokedex->size = mypokedex->size + 1;
}

void swap_pokedex (pokedex* mypokedex, int pos1, int pos2){
    pokemon tmp = mypokedex->arr[pos1];
    mypokedex->arr[pos1] = mypokedex->arr[pos2];
    mypokedex->arr[pos2] = tmp;
}

void insertionSortPokemonsByName(pokedex* mypokedex) {

    int n = mypokedex->size;

    for (int i = 1; i < n; i++) {

        pokemon key = mypokedex->arr[i];
        int j = i - 1;

        //comparar os nomes usando strcmp
        while (j >= 0 && strcmp(mypokedex->arr[j].name, key.name) > 0) {
            mypokedex->arr[j + 1] = mypokedex->arr[j];
            j--;
        }

        mypokedex->arr[j + 1] = key;
    }
}

//função para obter o comprimento máximo das primeiras habilidades no array
int getMaxLength(pokedex* mypokedex) {

    int maxLength = 0;

    for (int i = 0; i < mypokedex->size; i++) {

        int len = strlen(mypokedex->arr[i].abilities[0]);

        if (len > maxLength) {

            maxLength = len;
        }
    }

    return maxLength;
}

//função para realizar o counting sort baseado em uma posição específica de caractere na primeira habilidade
void countingSortByCharacter(pokedex* mypokedex, int n, int pos) {

    //considerando caracteres ASCII
    int count[256] = {0}; 

    //alocando array de output
    pokemon* output = (pokemon*)malloc(n * sizeof(pokemon));
    
    if (!output) {
        fprintf(stderr, "erro ao alocar output");
        exit(1);
    }

    //contar a ocorrência de cada caractere na posição 'pos'
    for (int i = 0; i < n; i++) {

        char currentChar;
        if (pos < strlen(mypokedex->arr[i].abilities[0])) {

            currentChar = mypokedex->arr[i].abilities[0][pos];

        } else {

            currentChar = '\0'; // Considerar '\0' para posições além do comprimento da string

        }

        count[(unsigned char)currentChar]++;
    }

    //transformar count[i] para conter a posição atual de cada caractere
    for (int i = 1; i < 256; i++) {

        count[i] += count[i - 1];

    }

    //construir o array de saída percorrendo de trás pra frente
    for (int i = n - 1; i >= 0; i--) {

        char currentChar;

        if (pos < strlen(mypokedex->arr[i].abilities[0])) {

            currentChar = mypokedex->arr[i].abilities[0][pos];

        } else {

            currentChar = '\0';

        }

        int index = count[(unsigned char)currentChar] - 1;

        output[index] = mypokedex->arr[i];

        count[(unsigned char)currentChar]--;
    }

    //copiar o array de saída para o array original
    for (int i = 0; i < n; i++) {
        mypokedex->arr[i] = output[i];
    }

    free(output);
}

//função para realizar o radix sort em um array de pokemons baseado na primeira habilidade
void radixSortPokemons(pokedex* mypokedex) {

    int n = mypokedex->size;

    if (n <= 1) {
        return; //já está ordenado
    }

    //obter o comprimento máximo das primeiras habilidades no array
    int maxLength = getMaxLength(mypokedex);

    //realizar Counting Sort para cada posição de caractere, começando pelo menos significativo
    for (int pos = maxLength - 1; pos >= 0; pos--) {
        countingSortByCharacter(mypokedex, n, pos);
    }

}

int main () {

	setlocale(LC_ALL, "Portuguese");
	setlocale(LC_NUMERIC, "C");

    const char* filename = "/tmp/pokemon.csv";
	//const char* filename = "pokemon.csv";
	
	//buffer para guardar input do usuário (ou do verde)
	char linha[10];
	
	int line_number = 0;
	
	//será usado de condição para interromper o loop
	int acabar = 0;

    pokedex* mypokedex =  create_pokedex();
	
	while(acabar == 0){
		
		fgets(linha, sizeof(linha), stdin);
		//remover o \n se tiver e substituir pelo terminador nulo
		linha[strcspn(linha, "\n")] = '\0';
		
		if (strcmp(linha, "FIM") == 0) {
			
			acabar = 1;
			
		} else {
			
			//se nao "FIM", processar o input e converter a string para inteiro
			line_number = atoi(linha);
			
			//manipular a linha para passar bonitinha para o pokemon
			char* processed_line = read_and_fill_line(filename, line_number + 1);
			
			if (processed_line) {

                add_pokedex(mypokedex, *parse_poke_line(processed_line));

                //mypokedex->arr[pos] = *parse_poke_line(processed_line);
				//pokemon* teste = parse_poke_line(processed_line);
				
				//liberar memória alocada
				free(processed_line);
			}
			
		}
		
	}

    //antes de fazer o radix, faço o insertion para ordenar por nome
    insertionSortPokemonsByName(mypokedex);
    radixSortPokemons(mypokedex);

    for(int i = 0; i < mypokedex->size; i++){

        print_pokemon(&mypokedex->arr[i]);

    }

	return 0;
}