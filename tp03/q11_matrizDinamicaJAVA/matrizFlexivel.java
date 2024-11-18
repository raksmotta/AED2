import java.util.Scanner;

class Cell {

    Cell up;
    Cell down;
    Cell right;
    Cell left;
    int num;

    public Cell() {
        this.up = null;
        this.down = null;
        this.right = null;
        this.left = null;
        this.num = 0;
    }

    public Cell(int x) {
        this.up = null;
        this.down = null;
        this.right = null;
        this.left = null;
        this.num = x;
    }
}

class Matrix {

    //considerei que todas as matrizes serão quadradas
    int size;
    Cell start;

    public Matrix(int size) {

        this.size = size;
        this.start = new Cell();

        Cell current = start;
        Cell rowStart = start;
        Cell upRow = start;

        //primeira linha apenas
        for (int j = 1; j < size; j++) {
            current.right = new Cell();
            current.right.left = current;
            current = current.right;
        }

        for (int i = 1; i < size; i++) {
            current = new Cell();
            current.up = upRow;
            upRow.down = current;

            for (int j = 1; j < size; j++) {
                current.right = new Cell();
                current.right.left = current;
                upRow.right.down = current.right;
                current.right.up = upRow.right;
                current = current.right;
                upRow = upRow.right;
            }

            rowStart = rowStart.down;
            upRow = rowStart;
        }
    }

    
    public Matrix add(Matrix m1, Matrix m2) {
        if (m1.size != m2.size) {
            throw new IllegalArgumentException("as dimensoes das duas matrizes devem ser iguais para soma");
        }

        Matrix result = new Matrix(m1.size);

        //ponteiros que marcam as linhas de cada matriz
        Cell rCurrentRow = result.start;
        Cell m1CurrentRow = m1.start;
        Cell m2CurrentRow = m2.start;

        while (m1CurrentRow != null && m2CurrentRow != null) {

            //ponteiros que marcam a celula atual em cada matriz
            Cell rCurrent = rCurrentRow;
            Cell m1Current = m1CurrentRow;
            Cell m2Current = m2CurrentRow;

            //andando por cada linha
            while (m1Current != null && m2Current != null) {
                rCurrent.num = m1Current.num + m2Current.num;
                rCurrent = rCurrent.right;
                m1Current = m1Current.right;
                m2Current = m2Current.right;
            }

            //movendo para a linha de baixo
            rCurrentRow = rCurrentRow.down;
            m1CurrentRow = m1CurrentRow.down;
            m2CurrentRow = m2CurrentRow.down;
        }

        return result;
    }

    //preenche uma posicao espefica na matriz com um numero
    public void fillMatrix(int pos, int number) {

        if (pos < 0 || pos >= size * size) {
            throw new IllegalArgumentException("posicao invalida para preencher");
        }

        int row = pos / size;
        int col = pos % size;

        Cell current = start;

        //andando até a célula desejada
        for (int i = 0; i < row; i++) {
            current = current.down;
        }

        for (int j = 0; j < col; j++) {
            current = current.right;
        }

        current.num = number;
    }

    public Matrix multiply(Matrix m1, Matrix m2) {

        if (m1.size != m2.size) {
            throw new IllegalArgumentException("as dimensoes das duas matrizes devem ser iguais para multiplicacao");
        }

        Matrix result = new Matrix(m1.size);

        //ponteiros que marcam as linhas de cada matriz
        Cell rCurrentRow = result.start;
        Cell m1CurrentRow = m1.start;

        while (m1CurrentRow != null) {
            Cell m2CurrentColStart = m2.start;
            Cell rCurrent = rCurrentRow;

            while (m2CurrentColStart != null) {
                Cell m1Current = m1CurrentRow;
                Cell m2Current = m2CurrentColStart;
                int sum = 0;

                while (m1Current != null && m2Current != null) {
                    sum += m1Current.num * m2Current.num;
                    m1Current = m1Current.right;
                    m2Current = m2Current.down;
                }

                rCurrent.num = sum;
                rCurrent = rCurrent.right;
                m2CurrentColStart = m2CurrentColStart.right;
            }

            rCurrentRow = rCurrentRow.down;
            m1CurrentRow = m1CurrentRow.down;
        }

        return result;
    }

    //prints the main diagonal in a single row
    public void printMainDiagonal() {
        Cell currentRow = start;
        StringBuilder result = new StringBuilder();
        while (currentRow != null) {
            result.append(currentRow.num).append(" ");
            currentRow = currentRow.down;
            if (currentRow != null) {
                currentRow = currentRow.right;
            }
        }
        System.out.println(result.toString().trim());
    }

    public void printSecondaryDiagonal() {
        Cell current = start;
        while (current.right != null) {
            current = current.right; // Move to the far-right cell of the first row
        }
    
        StringBuilder result = new StringBuilder();
        while (current != null) {
            result.append(current.num).append(" ");
            if (current.down != null) {
                current = current.down.left; // Move down and left diagonally
            } else {
                current = null; // End the traversal when no further rows exist
            }
        }
        System.out.println(result.toString().trim());
    }    


    public void display() {
        Cell currentRow = start;
        while (currentRow != null) {
            Cell current = currentRow;
            while (current != null) {
                System.out.print(current.num + " ");
                current = current.right;
            }
            System.out.println();
            currentRow = currentRow.down;
        }
    }

}


public class matrizFlexivel {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt(); // Número de casos de teste

        for (int test = 0; test < n; test++) {

            int l1 = scanner.nextInt();
            int c1 = scanner.nextInt();
            Matrix m1 = new Matrix(l1);

            for (int i = 0; i < l1; i++) {

                for (int j = 0; j < c1; j++) {

                    int value = scanner.nextInt();
                    m1.fillMatrix(i * c1 + j, value);

                }
            }

            int l2 = scanner.nextInt();
            int c2 = scanner.nextInt();
            Matrix m2 = new Matrix(l2);

            for (int i = 0; i < l2; i++) {

                for (int j = 0; j < c2; j++) {

                    int value = scanner.nextInt();
                    m2.fillMatrix(i * c2 + j, value);

                }
            }

            m1.printMainDiagonal();
            m1.printSecondaryDiagonal();

            try {

                Matrix sum = m1.add(m1, m2);
                sum.display();

            } catch (Exception e) {
                System.out.println("Erro na soma das matrizes.");
            }

            try {

                Matrix product = m1.multiply(m1, m2);
                product.display();

            } catch (Exception e) {
                System.out.println("Erro na multiplicação das matrizes.");
            }
        }

        scanner.close();

    }
}
