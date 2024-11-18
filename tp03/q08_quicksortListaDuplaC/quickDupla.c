#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>
#include <locale.h>
#include <time.h>

#define MAX_TYPES 10
#define MAX_ABILITIES 10
//variaveis globais para ver a complexidade do algoritmo
int COMP = 0;
int MOVE = 0;

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

    struct structpokemon* prox;
	struct structpokemon* ant;

}pokemon;

//construtor vazio
pokemon* create_empty_pokemon (){

    //criar um novo pokemon
	pokemon* p = (pokemon*)malloc(sizeof(pokemon));

    //inicializar os dados
    p->id = -1;
    p->generation = -1;
    p->name = "empty";
    p->description = "empty";
    p->weight = 0.0;
    p->height = 0.0;
    p->captureRate = 0;
    p->isLegendary = true;
    p->captureDate = "empty";
    p->prox = NULL;
	p->ant = NULL;

    return p;
}

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

    p->prox = NULL;
	p->ant = NULL;
	
	//LIMPANDO
	free(line_copy);
	free(betweenbrackets);
	
	return p;
}

void freePokemon(pokemon* p) {

    if (p == NULL) return;

    free(p->name);
    free(p->description);

    for (int i = 0; i < p->types_size; i++) {
        free(p->types[i]);
    }

    for (int i = 0; i < p->abilities_size; i++) {
        free(p->abilities[i]);
    }

    free(p->captureDate);
    free(p);
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

void swapData(pokemon* a, pokemon* b) {
    // Swap integer fields
    int tempId = a->id;
    a->id = b->id;
    b->id = tempId;

    int tempGeneration = a->generation;
    a->generation = b->generation;
    b->generation = tempGeneration;

    int tempTypesSize = a->types_size;
    a->types_size = b->types_size;
    b->types_size = tempTypesSize;

    int tempAbilitiesSize = a->abilities_size;
    a->abilities_size = b->abilities_size;
    b->abilities_size = tempAbilitiesSize;

    int tempCaptureRate = a->captureRate;
    a->captureRate = b->captureRate;
    b->captureRate = tempCaptureRate;

    bool tempIsLegendary = a->isLegendary;
    a->isLegendary = b->isLegendary;
    b->isLegendary = tempIsLegendary;

    // Swap double fields
    double tempWeight = a->weight;
    a->weight = b->weight;
    b->weight = tempWeight;

    double tempHeight = a->height;
    a->height = b->height;
    b->height = tempHeight;

    // Swap pointers to strings
    char* tempName = a->name;
    a->name = b->name;
    b->name = tempName;

    char* tempDescription = a->description;
    a->description = b->description;
    b->description = tempDescription;

    char* tempCaptureDate = a->captureDate;
    a->captureDate = b->captureDate;
    b->captureDate = tempCaptureDate;

    // Swap arrays of strings up to the maximum of the two sizes
    int maxTypes = (a->types_size > b->types_size) ? a->types_size : b->types_size;
    for (int i = 0; i < maxTypes; i++) {
        char* tempType = a->types[i];
        a->types[i] = b->types[i];
        b->types[i] = tempType;
    }

    int maxAbilities = (a->abilities_size > b->abilities_size) ? a->abilities_size : b->abilities_size;
    for (int i = 0; i < maxAbilities; i++) {
        char* tempAbility = a->abilities[i];
        a->abilities[i] = b->abilities[i];
        b->abilities[i] = tempAbility;
    }

    // If one Pokémon has more types or abilities, set the extra slots to NULL
    if (a->types_size < b->types_size) {
        for (int i = a->types_size; i < b->types_size; i++) {
            a->types[i] = NULL;
        }
    } else if (b->types_size < a->types_size) {
        for (int i = b->types_size; i < a->types_size; i++) {
            b->types[i] = NULL;
        }
    }

    if (a->abilities_size < b->abilities_size) {
        for (int i = a->abilities_size; i < b->abilities_size; i++) {
            a->abilities[i] = NULL;
        }
    } else if (b->abilities_size < a->abilities_size) {
        for (int i = b->abilities_size; i < a->abilities_size; i++) {
            b->abilities[i] = NULL;
        }
    }

    // Do NOT swap 'prox' and 'ant' pointers
}

//LISTA DUPLAMENTE ENCADEADA
pokemon* primeiro;
pokemon* ultimo;
int tam = 0;

void criarLista(){
    primeiro = create_empty_pokemon();
    ultimo = primeiro;
}

void inserirInicio (pokemon* poke){

	poke->ant = primeiro;
	poke->prox = primeiro->prox;
	primeiro->prox = poke;

	if (primeiro == ultimo) {
		ultimo = poke;
	} else {
		poke->prox->ant = poke;
	}

	tam++;

    //NÃO POSSO DAR UM FREE NO POKE pois não quero deletar a memória para a qual ele apontava, quero simplesmente descartar o ponteiro!
    //poke = NULL;
}

pokemon removerInicio (){

    if (primeiro == ultimo){

        printf("ERRO. chamou removerInicio para lista vazia");
        pokemon p_vazio;
        return p_vazio;

    }else{

        pokemon* tmp = primeiro->prox;
        primeiro->prox = tmp->prox;
        
		if (tmp->prox){

			tmp->prox->ant = primeiro;

		}else{

			ultimo = primeiro;

		}
        tam --;
        
        //deletando a célula FISICAMENTE
		tmp->prox = NULL;
		tmp->ant = NULL;
		pokemon result = *tmp;
        freePokemon(tmp);
        tmp = NULL;
        return result;
    }
}

void inserirFim (pokemon* poke){

	poke->ant = ultimo;
    ultimo->prox = poke;
    ultimo = ultimo->prox;
    tam++;
    //poke = NULL;
}

pokemon removerFim (){

    if (tam==0 || tam==1){

        return removerInicio();
        
    }else{

        pokemon* tmp = ultimo;
        
        ultimo = ultimo->ant;

		ultimo->prox = NULL;

		tmp->ant = NULL;
		tmp->prox = NULL;
		pokemon result = *tmp;
		freePokemon(tmp);

        tam--;

        return result;
    }
}

void inserir (pokemon* poke, int pos){

    if ((pos < 0) || (pos > tam)){

        printf("ERRO. chamou inserir para posicao invalida");

    }else if (pos == 0){

        inserirInicio(poke);

    }else if (pos == tam){

        inserirFim(poke);

    }else{

        //a posicao do primeiro (que é o nó cabeça) é -1. A posicao 0 seria primeiro.prox. Mas como queremos parar um pokemon ANTES daquele que desejamos inserir, começamos contando a partir da sentinela
        pokemon* i = primeiro;
        int count = 0;

        while (count < pos){
            i = i->prox;
            count++;
        }

		poke->ant = i;
        poke->prox = i->prox;
		if (i->prox) {
            i->prox->ant = poke;
        }
        i->prox = poke;

        //nao damos free pois queremos preservar o conteúdo
        //nao eh necessario poke = NULL;

        tam++;
    }

}

pokemon remover (int pos){

    if ((pos < 0) || (pos >= tam)){

        printf("ERRO. chamou remover para posicao invalida");
        pokemon p_vazio;
        return p_vazio;

    }else if (pos == 0){

        return removerInicio();

    }else if (pos == tam - 1){

        return removerFim();

    }else{

        pokemon* i = primeiro;
        int count = 0;

        while (count < pos){
            i = i->prox;
            count++;
        }

        pokemon* tmp = i->prox;
        i->prox = tmp->prox;
		if (tmp->prox) {
            tmp->prox->ant = i;
        }
        
		tmp->ant = NULL;
		tmp->prox = NULL;
        pokemon result = *tmp;
        freePokemon(tmp);
        tmp = NULL;

        tam--;

        return result;
    }
    
}

//function to swap two Pokémon nodes by swapping their data
void swap(pokemon* a, pokemon* b) {
    if (a == b) return;
    swapData(a, b);
}

int comparePokemons(pokemon* a, pokemon* b) {

	COMP++;
    if (a->generation != b->generation) {
        return (a->generation - b->generation);
    } else {
        return strcmp(a->name, b->name);
    }
}

//funcao de partiçao para o quicksort
pokemon* partition(pokemon* low, pokemon* high) {
    pokemon* pivot = high;
    pokemon* i = low->ant;

    for (pokemon* j = low; j != high; j = j->prox) {

		COMP++;
        if (comparePokemons(j, pivot) <= 0) {
            if (i == NULL){
                i = low;
			}else{
                i = i->prox;
			}
			MOVE+=3;
            swap(i, j);
        }
    }

    if (i == NULL){
        i = low;
	}else{
        i = i->prox;
	}

	MOVE+=3;
    swap(i, high);
    return i;
}

//faz as chamadas recursivas do quicksort 
void quicksort(pokemon* low, pokemon* high) {

	COMP++;
    if (high != NULL && low != high && low != high->prox) {

        pokemon* pivot = partition(low, high);
        quicksort(low, pivot->ant);
        quicksort(pivot->prox, high);

    }
}

//encapsulamento
void sortQuick() {
    if (primeiro->prox != NULL) {

        quicksort(primeiro->prox, ultimo);

    } else {

        fprintf(stderr, "ERRO ao chamar o quicksort\n");

    }
}

void mostrar_lista_verde (){

    int x = 0;

    for (pokemon* i = primeiro->prox; i!=NULL; i=i->prox){
        printf("[");
        printf("%d", x);
        printf("] ");
        print_pokemon(i);
        x++;
    }

}

void freeList() {
    pokemon* current = primeiro;
    while (current != NULL) {
        pokemon* next = current->prox;
        freePokemon(current);
        current = next;
    }
    primeiro = NULL;
    ultimo = NULL;
    tam = 0;
}

void ler_e_executar_comandos(const char* filename) {

    char s[15]; // Buffer para armazenar a linha de entrada
    int numeroPoke = 0;
    int posicaoPoke = 0;

    // Lendo uma linha do stdin
    if (fgets(s, sizeof(s), stdin) != NULL) {

        // Remover o caractere de nova linha '\n' caso esteja presente
        size_t len = strlen(s);

        if (len > 0 && s[len - 1] == '\n') {
            s[len - 1] = '\0';
        }

        // Dividindo a string com base no espaço
        char *partes[3]; // Array de ponteiros para armazenar as partes
        int count = 0;

        char *token = strtok(s, " ");

        while (token != NULL) {
            partes[count++] = token;
            token = strtok(NULL, " ");
        }

        // EXECUTAR
        if (strcmp(partes[0], "RI") == 0) {

            pokemon removido = removerInicio();
            printf("(R) %s\n", removido.name);

        } else if (strcmp(partes[0], "RF") == 0) {

            pokemon removido = removerFim();
            printf("(R) %s\n", removido.name);

        } else if (strcmp(partes[0], "II") == 0) {

            numeroPoke = atoi(partes[1]);
            char* processed_line = read_and_fill_line(filename, numeroPoke + 1);
            pokemon* inserido = parse_poke_line(processed_line);
            inserirInicio(inserido);
            free(processed_line);
            processed_line = NULL;

        } else if (strcmp(partes[0], "IF") == 0) {

            numeroPoke = atoi(partes[1]);
            char* processed_line = read_and_fill_line(filename, numeroPoke + 1);
            pokemon* inserido = parse_poke_line(processed_line);
            inserirFim(inserido);  // Corrigido para inserir no fim
            free(processed_line);
            processed_line = NULL;

        } else if (strcmp(partes[0], "R*") == 0) {

            posicaoPoke = atoi(partes[1]);
            pokemon removido = remover(posicaoPoke);
            printf("(R) %s\n", removido.name);

        } else if (strcmp(partes[0], "I*") == 0) {

            posicaoPoke = atoi(partes[1]);
            numeroPoke = atoi(partes[2]);
            char* processed_line = read_and_fill_line(filename, numeroPoke + 1);
            pokemon* inserido = parse_poke_line(processed_line);
            inserir(inserido, posicaoPoke);
            free(processed_line);
            processed_line = NULL;

        } else {

            printf("Comando não reconhecido: %s\n", partes[0]);

        }

    } else {

        fprintf(stderr, "Erro ao ler a linha.\n");

    }

}

double diff(clock_t start, clock_t end){
	return ((double)(end - start)) / CLOCKS_PER_SEC;
}

void kel(const char* fileName, double time){
	FILE* fil = fopen(fileName, "w");
	if(fil){
		fprintf(fil, "Matrícula: 854017\t");
		fprintf(fil, "Tempo de Execução: %lf\t", time);
		fprintf(fil, "Comparações: %d\t", COMP);
		fprintf(fil, "Movimentações: %d", MOVE);
		fclose(fil);
	}
	else printf("Erro ao abrir o arquivo");
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

    criarLista();
	
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

                inserirFim(parse_poke_line(processed_line));
				
				//liberar memória alocada
				free(processed_line);
                processed_line = NULL;
			}
			
		}
		
	}

	clock_t start = clock();

	sortQuick();

	mostrar_lista_verde();

	clock_t end = clock();

	kel("854017_quicksort2.txt", diff(start, end));

	return 0;
}
