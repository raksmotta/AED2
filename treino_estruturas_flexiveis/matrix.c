#include <stdio.h>
#include <stdlib.h>

// Print com [\n]
void println (const char* const line)
{
	printf ("%s\n", line);
}

// Definir estrutura
typedef struct cell_s
{
	int data;
	struct cell_s* U;
	struct cell_s* D;
	struct cell_s* L;
	struct cell_s* R;
}
Cell;

// "Construtor" da celula da matriz
Cell* newCell(int x)
{
	Cell* this = (Cell*)malloc(sizeof(Cell));

	if (this)
	{
		this->data = x;
		this->U = NULL;
		this->D = NULL;
		this->L = NULL;
		this->R = NULL;
	}
	return (this);
}

// "Construtor" sem parametros
Cell* newCellD ()
{
	return (newCell(0));
}

// Criar nova fileira
Cell* newRow (int n)
{
	// Criar nova celula
	Cell* head = newCellD();

	Cell* ptr = head;

	if (head)
	{
		// Criar mais n-1 celulas
		for (int i = 0; i < n-1; i++)
		{
			Cell* tmp = newCellD();
			ptr->R = tmp;
			tmp->L = ptr;
			ptr = tmp;
		}
	}
	return (head);
}

// Criar nova coluna
Cell* newCol (int n)
{
	// Criar nova celula
	Cell* head = newCellD();

	Cell* ptr = head;

	if (head)
	{
		// Criar mais n-1 celulas
		for (int i = 0; i < n-1; i++)
		{
			Cell* tmp = newCellD();
			ptr->D = tmp;
			tmp->U = ptr;
			ptr = tmp;
		}
	}
	return (head);
}

// Definir estrutura
typedef struct matrix_s
{
	Cell* head;
	int size;
}
Matrix;

// "Construtor" da matriz
Matrix* newMatrix (int d)
{
	// Definir resultado
	Matrix* this = NULL;

	if (d > 1)
	{
		// Alocar espaco na memoria
		this = (Matrix*)malloc(sizeof(Matrix));

		if (this)
		{
			// Criar primeira fileira
			this->head = newRow(d);


			Cell* start = this->head;

			// Criar e ligar [d] fileiras
			for (int c = 0; c < d; c++)
			{
				Cell* new = newRow(d);
				Cell* tmp = new;

				// Ligar fileiras
				for (Cell* i = start; i != NULL; i = i->R)
				{
					i->D = tmp;
					tmp->U = i;
					tmp = tmp->R;
				}

				// Descer referencia para a proxima fileira
				start = start->D;
			}

			this->size = d;
		}
	}
	else
	{
		println ("Tamanho invalido");
	}
	return (this);
}

void insert (Matrix* this, int x, int r, int c)
{
	if (this && this->head)
	{
		if (r >= 0 && c >= 0 && r < this->size && c < this->size)
		{
			Cell* ptr = this->head;
			
			// Navegar linhas (vertical)
			for (int i = 0; i < r; i++)
			{
				ptr = ptr->D;
			}
			
			// Navegar colunas (horizontal)
			for (int i = 0; i < c; i++)
			{
				ptr = ptr->R;
			}
			
			ptr->data = x;
			ptr = NULL;
		}
		else
		{
			println ("Posicao invalida");
		}
	}
	else
	{
		println ("Dados invalidos");
	}
}


// Mostrar todos os elementos da matriz
void print (Matrix* m)
{
	if (m && m->head)
	{
		// Definir ponto inicial
		Cell* row = m->head;
		Cell* ptr = row;

		// Linhas
		for (int y = 0; y < m->size; y++)
		{
			// Colunas
			for (int x = 0; x < m->size; x++)
			{
				printf ("[%d] ", ptr->data);
				ptr = ptr->R;
			}
			println ("");

			row = row->D;
			ptr = row;
		}
		println ("");
	}
	else
	{
		println ("Dados invalidos");
	}
}

// Mostrar diagonal principal
void mdR (Cell* ptr)
{
	printf ("[%d]\n", ptr->data);
	if (ptr->R && ptr->R->D)
	{
		mdR(ptr->R->D);
	}
}

// Encapsular (mdR)
void MainDiagonal (Matrix* m)
{
	if (m && m->head)
	{
		mdR(m->head);
		println ("");
	}
	else
	{
		println ("Dados invalidos");
	}
}

// Mostrar diagonal secundaria
void sdR (Cell* ptr)
{
	printf ("[%d]\n", ptr->data);
	if (ptr->L && ptr->L->D)
	{
		sdR (ptr->L->D);
	}
}

// Encapsular (sdR)
void SeccondaryDiagonal (Matrix* m)
{
	if (m && m->head)
	{
		// Ir para a celula mais a direita
		Cell* ptr = NULL;
		for (ptr = m->head; ptr->R; ptr = ptr->R);
		sdR(ptr);
		println ("");
	}
	else
	{
		println ("Dados invalidos");
	}
}

int main (void)
{
	// Criar nova matriz
	Matrix* x = newMatrix(2);

	// Inserir valores
	println ("Inserir valores -");

	insert(x, 2, 0, 0);
 	print(x);

	insert(x, 6, 1, 1);
 	print(x);

	insert(x, 3, 0, 1);
 	print(x);

	// Mostrar diagonais
	println ("Mostrar diagonais- \n");

	println ("Principal: ");
	MainDiagonal(x);
	println ("Secundaria: ");
	SeccondaryDiagonal(x);

	// Mostrar matriz inteira
	println ("Mostar matriz -");
 	print(x);

	return (0);
}
