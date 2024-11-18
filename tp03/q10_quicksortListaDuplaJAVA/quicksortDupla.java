import java.time.*;
import java.io.*;
import java.nio.charset.Charset;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.*;

public class quicksortDupla{

    public static void main(String[] args) throws IOException{

        //String csvFilePath = "pokemon.csv";
        //VERDE
        String csvFilePath = "/tmp/pokemon.csv";

        //System.out.println("Current working directory: " + System.getProperty("user.dir"));
        //System.out.println("System Charset: " + Charset.defaultCharset());
        Scanner verde = new Scanner(System.in);

        String control = "start";

        int idPokemon = 0;

        int posicao = 0;

        ListaDupla pokelist = new ListaDupla();

        while (!control.equals("FIM")) {

            control = verde.nextLine();

            if (!control.equals("FIM")) {

                idPokemon = Integer.parseInt(control);

                Pokemon p = new Pokemon (csvFilePath, idPokemon);

                pokelist.inserir(p.clonePokemon(), posicao);

                posicao++;
            }

        }
        
        KEL kel = new KEL();

        pokelist.sortQuick();

        //System.out.println("LISTA MODIFICADA:");
        pokelist.mostrar();
        
        kel.end();

        try {

            kel.print("854017_quicksort3.txt");

        } catch (Exception e) {

            System.out.println(e);
        }

        verde.close();
    }

}

class Pokemon implements Cloneable{

    private int id;
    private int generation;
    private String name;
    private String description;
    private ArrayList<String> types;
    private ArrayList<String> abilities;
    private double weight;
    private double height;
    private int captureRate;
    private boolean isLegendary;
    private Date captureDate;

    //ADICIONANDO PONTEIRO PARA MANIPULACAO EM ESTRUTURAS DE DADOS FLEXIVEIS
    public Pokemon prox;
    public Pokemon ant;

    //construtor SEM parametros
    public Pokemon() {
        this.id = 0;
        this.generation = 0;
        this.name = "";
        this.description = "";
        this.types = new ArrayList<>();
        this.abilities = new ArrayList<>();
        this.weight = 0.0;
        this.height = 0.0;
        this.captureRate = 0;
        this.isLegendary = false;
        this.captureDate = new Date();
        this.prox = null;
        this.ant = null;
    }

    //construtor que passamos como parametro o id do pokemon!
    public Pokemon(String csvFilePath, int myID) {

        Charset charset = Charset.forName("UTF-8");

        try {

            Scanner scanner = new Scanner(new File(csvFilePath), charset.name());

            //simplesmente pulando linhas até alcançar a do ID desejado
            for (int x = 0; x < myID; x++) {

                scanner.nextLine();

            }

            //le uma linha inteira do arquivo e guarda numa string gigante
            String line = scanner.nextLine();
            //System.out.println(line);

            //depois, pega a linha que lemos e a separa/quebra em um vetor com os nossos dados iniciais (antes do [)
            String[] firstPart = line.split("\\[");

            //pega esse vetor de dados iniciais e separa usando as vírgulas como delimitador
            String[] fields = firstPart[0].split(",");

            // Por ter atributos vazios, setar tudo com 0 para nao ocorrer problemas de Parse
            for (int i = 0; i < fields.length; i++) if (fields[i].isEmpty()) fields[i] = "0";

            //agora, convertemos as strings nesse vetor para os tipos necessários para serem armazenados
            //sabemos que as quatro primeiras certamente estao nessa ordem:
            this.id = Integer.parseInt(fields[0]);
            this.generation = Integer.parseInt(fields[1]);
            this.name = fields[2];
            this.description = fields[3];

            //sempre tem pelo menos um tipo
            this.types = new ArrayList<>();
            this.types.add("\'" + fields[4] + "\'");

            //se nao tiver um quinto campo (string vazia), é porque temos apenas 1 tipo
            if (! fields[5].equals("0")) {

                this.types.add("\'" + fields[5] + "\'");

            }

            //dividindo a string original até achar apenas a parte entre os caracteres [ ], onde estão as habilidades
            String[] secondPart = firstPart[1].split("\\]");

            //achada a parte onde estão as habilidades, as dividimos entre si usando como delimitador a vírgula
            String[] habilidades = secondPart[0].split(",");

            //guardamos no atributo
            this.abilities = new ArrayList<>(Arrays.asList(habilidades));

            //vamos para os atributos finais
            String[] lastData = secondPart[1].split(",");
            for (int i = 0; i < lastData.length; i++) if (lastData[i].isEmpty()) lastData[i] = "0";

            this.weight = Double.parseDouble(lastData[1]);
            this.height = Double.parseDouble(lastData[2]);
            this.captureRate = Integer.parseInt(lastData[3]);
            this.isLegendary = lastData[4].equals("1");

            //this.captureDate = lastData[5];

            if (lastData[5].isEmpty()) {

                this.captureDate = null;

            } else {

                SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                this.captureDate = inputDateFormat.parse(lastData[5]);

            }

            scanner.close();

        } catch (Exception e) {

            System.out.println("Error reading file: " + e.getMessage());

        }
    }

    // LER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public Pokemon readPokemon(String csvFilePath, int myID) {

        Pokemon poke = new Pokemon();

        Charset charset = Charset.forName("UTF-8");

        try {

            Scanner scanner = new Scanner(new File(csvFilePath), charset.name());

            //simplesmente pulando linhas até alcançar a do ID desejado
            for (int x = 0; x < myID; x++) {

                scanner.nextLine();

            }

            String line = scanner.nextLine();

            String[] firstPart = line.split("\\[");

            String[] fields = firstPart[0].split(",");
            for (int i = 0; i < fields.length; i++) if (fields[i].isEmpty()) fields[i] = "0";

            poke.id = Integer.parseInt(fields[0]);
            poke.generation = Integer.parseInt(fields[1]);
            poke.name = fields[2];
            poke.description = fields[3];

            poke.types = new ArrayList<>();
            poke.types.add("\'" + fields[4] + "\'");

            if (! fields[5].equals("0")) {

                poke.types.add("\'" + fields[5] + "\'");

            }

            String[] secondPart = firstPart[1].split("\\]");

            String[] habilidades = secondPart[0].split(",");

            poke.abilities = new ArrayList<>(Arrays.asList(habilidades));

            String[] lastData = secondPart[1].split(",");
            for (int i = 0; i < lastData.length; i++) if (lastData[i].isEmpty()) lastData[i] = "0";

            poke.weight = Double.parseDouble(lastData[1]);
            poke.height = Double.parseDouble(lastData[2]);
            poke.captureRate = Integer.parseInt(lastData[3]);
            poke.isLegendary = lastData[4].equals("1");

            //poke.captureDate = lastData[5];
            if (lastData[5].isEmpty()) {

                poke.captureDate = null;

            } else {

                SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                poke.captureDate = inputDateFormat.parse(lastData[5]);

            }

            scanner.close();

            this.prox = null;
            this.ant = null;

            return poke;

        } catch (Exception e) {

            System.out.println("Error reading file: " + e.getMessage());

            return null;

        }
    }

    // IMPRIMIR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public String pokemonString() {

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = (captureDate != null) ? outputDateFormat.format(captureDate) : "date not available";

        return "[#" + this.id + " -> " + this.name + ": " + this.description + " - " + this.types + " - " + this.abilities + " - " + this.weight + "kg - " + this.height + "m - " + this.captureRate + "% - " + this.isLegendary + " - " + this.generation + " gen] - " + formattedDate;

    }

    // GET !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public int get_id() {
        return id;
    }

    public int get_generation() {
        return generation;
    }

    public String get_name() {
        return name;
    }

    public String get_description() {
        return description;
    }

    public ArrayList<String> get_types() {
        return types;
    }

    public ArrayList<String> get_abilities() {
        return abilities;
    }

    public double get_weight() {
        return weight;
    }

    public double get_height() {
        return height;
    }

    public int get_captureRate() {
        return captureRate;
    }

    public boolean get_isLegendary() {
        return isLegendary;
    }

    public Date get_captureDate() {
        return captureDate;
    }

    // SET !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public int set_id(int myid) {
        int tmp = this.id;
        this.id = myid;
        return tmp;
    }

    public int set_generation(int mygen) {
        int tmp = this.generation;
        this.generation = mygen;
        return tmp;
    }

    public String set_name(String myname) {
        String tmp = this.name;
        this.name = myname;
        return tmp;
    }

    public String set_description(String mydesc) {
        String tmp = this.description;
        this.description = mydesc;
        return tmp;
    }

    public ArrayList<String> set_types(ArrayList<String> mytypes) {
        ArrayList<String> tmp = this.types;
        this.types = mytypes;
        return tmp;
    }

    public ArrayList<String> set_abilities(ArrayList<String> myabilities) {
        ArrayList<String> tmp = this.abilities;
        this.abilities = myabilities;
        return tmp;
    }

    public double set_weight(double myweight) {
        double tmp = this.weight;
        this.weight = myweight;
        return tmp;
    }

    public double set_height(double myheight) {
        double tmp = this.height;
        this.height = myheight;
        return tmp;
    }

    public int set_captureRate(int myrate) {
        int tmp = this.captureRate;
        this.captureRate = myrate;
        return tmp;
    }

    public boolean set_isLegendary(boolean mylegend) {
        boolean tmp = this.isLegendary;
        this.isLegendary = mylegend;
        return tmp;
    }

    public Date set_captureDate(Date mydate) {
        Date tmp = this.captureDate;
        this.captureDate = mydate;
        return tmp;
    }

    /*Deep vs. Shallow Copy: The default clone() method performs a shallow copy. If your object contains mutable fields (like ArrayList), you need to perform a deep copy to prevent shared references between the original and cloned objects. Cloning Mutable Fields: In the overridden clone() method, explicitly clone the mutable fields to ensure each Pokemon object has its own copy of these fields. */

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Pokemon cloned = (Pokemon) super.clone();
        // If you have mutable fields (like ArrayList), clone them as well
        cloned.types = new ArrayList<>(this.types);
        cloned.abilities = new ArrayList<>(this.abilities);
        // Clone other mutable fields if necessary
        return cloned;
    }

    public Pokemon clonePokemon() {
        try {
            return (Pokemon) this.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not supported.");
            return null;
        }
    }

    public void swapData(Pokemon other) {
        // Swap primitive and immutable types directly
        int tempId = this.id;
        this.id = other.id;
        other.id = tempId;
    
        int tempGeneration = this.generation;
        this.generation = other.generation;
        other.generation = tempGeneration;
    
        String tempName = this.name;
        this.name = other.name;
        other.name = tempName;
    
        String tempDescription = this.description;
        this.description = other.description;
        other.description = tempDescription;
    
        double tempWeight = this.weight;
        this.weight = other.weight;
        other.weight = tempWeight;
    
        double tempHeight = this.height;
        this.height = other.height;
        other.height = tempHeight;
    
        int tempCaptureRate = this.captureRate;
        this.captureRate = other.captureRate;
        other.captureRate = tempCaptureRate;
    
        boolean tempIsLegendary = this.isLegendary;
        this.isLegendary = other.isLegendary;
        other.isLegendary = tempIsLegendary;
    
        Date tempCaptureDate = this.captureDate;
        this.captureDate = other.captureDate;
        other.captureDate = tempCaptureDate;
    
        // For mutable types, create new instances to prevent shared references
        ArrayList<String> tempTypes = new ArrayList<>(this.types);
        this.types = new ArrayList<>(other.types);
        other.types = tempTypes;
    
        ArrayList<String> tempAbilities = new ArrayList<>(this.abilities);
        this.abilities = new ArrayList<>(other.abilities);
        other.abilities = tempAbilities;
    }    

}

class ListaDupla {

    private Pokemon primeiro;
    private Pokemon ultimo;
    private int tam;

    //construtor que implementa a lógica de NÓ CABEÇA (ou sentinela)
    public ListaDupla(){
        this.primeiro = new Pokemon();
        this.ultimo = primeiro;
        this.tam = 0;
    }

    void inserirInicio(Pokemon poke){

        poke.ant = primeiro;
        poke.prox = primeiro.prox;
        primeiro.prox = poke;

        if (primeiro == ultimo) {
            ultimo = poke;
        } else {
            poke.prox.ant = poke;
        }

        tam++;
    }

    Pokemon removerInicio(){

        if (primeiro == ultimo) {
            System.out.println("ERRO. chamou removerInicio para lista vazia");
            return null;

        } else {
            Pokemon tmp = primeiro.prox;
            primeiro.prox = tmp.prox;

            if (tmp.prox != null) {

                tmp.prox.ant = primeiro;

            } else {

                //ajuste caso seja o ultimo elemento removido da lista
                ultimo = primeiro;

            }

            tmp.prox = tmp.ant = null;
            tam--;
            return tmp;
        }
    }

    void inserirFim(Pokemon poke){

        poke.prox = null;
        poke.ant = ultimo;
        ultimo.prox = poke;
        ultimo = poke;
        tam++;
    }

    Pokemon removerFim(){

        if (primeiro == ultimo) {
            return removerInicio();

        } else {

            Pokemon tmp = ultimo;
            ultimo = ultimo.ant;
            ultimo.prox = null;
            tmp.ant = null;
            tam--;
            return tmp;
        }
    }

    void inserir(Pokemon poke, int pos){

        if ((pos < 0) || (pos > tam)) {

            System.out.println("ERRO. chamou inserir para posicao invalida");

        } else if (pos == 0) {

            inserirInicio(poke);

        } else if (pos == tam) {

            inserirFim(poke);

        } else {

            int count = 0;

            Pokemon i = primeiro;

            while (count < pos) {
                i = i.prox;
                count++;
            }

            poke.ant = i;
            poke.prox = i.prox;

            if (i.prox != null) {
                i.prox.ant = poke;
            }

            i.prox = poke;

            tam++;
        }
    }

    Pokemon remover(int pos){

        if ((pos < 0) || (pos >= tam)) {

            System.out.println("ERRO. chamou remover para posicao invalida");
            return null;

        } else if (pos == 0) {

            return removerInicio();

        } else if (pos == tam - 1) {

            return removerFim();

        } else {

            int count = 0;
            Pokemon i = primeiro;
            while (count < pos) {
                i = i.prox;
                count++;
            }

            Pokemon tmp = i.prox;
            i.prox = tmp.prox;

            if (tmp.prox != null) {
                tmp.prox.ant = i;
            }

            tmp.prox = tmp.ant = null;
            tam--;
            return tmp;
        }
    }

    void mostrar(){

        for (Pokemon i = primeiro.prox; i != null; i = i.prox) {
            System.out.println(i.pokemonString());
        }
    }

    void mostrarVerde(){

        int x = 0;
        for (Pokemon i = primeiro.prox; i != null; i = i.prox) {
            System.out.println("[" + x + "] " + i.pokemonString());
            x++;
        }
    }
    
    private int comparePokemons(Pokemon a, Pokemon b) {

        KEL.comp++;
        //se as geracoes forem diferentes, ordenamos com base nelas
        if (a.get_generation() != b.get_generation()) {

            return Integer.compare(a.get_generation(), b.get_generation());

        } else {

            //mas se os pokemons tiverem a mesma geracao, ordenamos alfabeticamente pelo nome
            return a.get_name().compareTo(b.get_name());
        }
    }

    private Pokemon partition(Pokemon low, Pokemon high) {

        //o pivo será o elemento na direita
        Pokemon pivot = high;
        Pokemon i = low.ant;

        KEL.comp++;
        for (Pokemon j = low; j != high; j = j.prox) {

            KEL.comp++;
            if (comparePokemons(j, pivot) <= 0) {

                i = (i == null) ? low : i.prox;

                KEL.move+=3;
                swap(i, j);
            }
        }

        i = (i == null) ? low : i.prox;

        KEL.move+=3;
        swap(i, high);

        return i;
    }

    //metodo que chama recursivamente o quicksort
    private void quicksort(Pokemon low, Pokemon high) {

        KEL.comp++;
        if (high != null && low != high && low != high.prox) {

            Pokemon pivot = partition(low, high);
            quicksort(low, pivot.ant);
            quicksort(pivot.prox, high);
        }
    }

    private void swap(Pokemon a, Pokemon b) {

        //se os pokemons forem iguais, nao precisa fazer nada
        if (a == b) return;

        a.swapData(b);
    }

    //encapsulamento para facilitar chamar na main e tambem prevenir chamar em lista vazia
    public void sortQuick() {

        if (primeiro.prox != null) {

            quicksort(primeiro.prox, ultimo);

        } else {

            System.err.println("ERRO ao chamar o quicksort");

        }
    }

}

class KEL{

	private Instant start;
	private Instant end;
    public static int comp = 0;
    public static int move = 0;

	KEL(){
		this.start = Instant.now();
	}

	void end(){
		this.end = Instant.now();
	}

	double diff() {
        return Duration.between(this.start, this.end).toNanos() / 1_000_000_000.0;
    }

	void print(String fileName) throws Exception{
		PrintWriter write = new PrintWriter(new FileWriter(fileName));
		write.printf("Matrícula: 854017\t");
		write.printf("Tempo de execução: " + diff() + "\t");
		write.printf("Comparações: " + comp + "\t");
		write.printf("Movimentações: " + move + "\t");
		write.close();
	}
	
}
