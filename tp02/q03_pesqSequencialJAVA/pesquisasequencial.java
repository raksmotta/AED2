import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class pesquisasequencial{

    public static boolean pesqSeq(Pokedex mypokedex, String name) {

        boolean resp = false;

        int n = mypokedex.size();

        //System.out.println(mypokedex.size());

        for (int i = 0; i < n; i++) {

            Pokemon currentPokemon = mypokedex.get(i);

            if (mypokedex.get(i) != null && currentPokemon.get_name() != null && currentPokemon.get_name().equals(name)) {

                resp = true;
                i = n;

            }

        }

        return resp;
    }

    public static void main(String[] args) {

        //WINDOWS
        //String csvFilePath = "C:/Users/raque/Documents/2periodoCCpuc/algoritmos_dados_2/tp02/q01_classejava/pokemon.csv";
        //LINUX 
        //String csvFilePath = "pokemon.csv";
        //VERDE
        String csvFilePath = "/tmp/pokemon.csv";

        //System.out.println("Current working directory: " + System.getProperty("user.dir"));
        //System.out.println("System Charset: " + Charset.defaultCharset());
        Scanner verde = new Scanner(System.in);

        String control = "start";

        int idPokemon = 0;

        Pokedex mypokedex = new Pokedex();

        while (!control.equals("FIM")) {

            control = verde.nextLine();

            if (!control.equals("FIM")) {

                idPokemon = Integer.parseInt(control);

                mypokedex.add(new Pokemon(csvFilePath, idPokemon));
            }

        }

        control = "start again";

        boolean sim = true;

        while (!control.equals("FIM")) {

            control = verde.nextLine();

            if (!control.equals("FIM")) {

                sim = pesqSeq(mypokedex, control);

                if(sim){

                    System.out.println("SIM");

                }else{

                    System.out.println("NAO");
                }

            }

        }

        /*
        for (int x = 0; x < mypokedex.size(); x++){
            System.out.println(mypokedex.get(x).pokemonString());
        }*/

        verde.close();
    }
}

class Pokemon {

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

    public Pokemon clonePokemon() {

        try {
            return (Pokemon) super.clone();

        } catch (CloneNotSupportedException e) {

            System.out.println("Cloning not supported.");
            return null;

        }
    }

}

//criando minha própria LISTA DE POKEMONS
class Pokedex {

    //um array de "células" (cada célula é um objeto da classe Pokemon)
    public Pokemon[] arr;
    public int size;

    //construtor sem parâmetros
    public Pokedex() {
        //capacidade inicial padrão
        this.arr = new Pokemon[300]; 
        this.size = 0;
    }

    //construtor com parâmetros
    public Pokedex(int n) {
        this.arr = new Pokemon[n]; 
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public Pokemon get(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        return arr[index];
    }

    public Pokemon set(int index, Pokemon poki) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Pokemon old = arr[index];
        arr[index] = poki;
        return old;
    }

    //para não aumentar o tamanho automaticamente e nao add numa lista que nao tem capacidade para mais um pokemon, fazemos essa verificação:
    public void ensureCapacity() {

        if (size == arr.length) {
            int newSize = arr.length * 2;

            Pokemon[] newArr = new Pokemon[newSize];

            System.arraycopy(arr, 0, newArr, 0, arr.length);
            
            arr = newArr;
        }
        
    }

    //a cada vez que adicionamos um novo pokemon, PÓS INCREMENTAMOS o size
    public void add(Pokemon poki) {
        ensureCapacity();
        arr[size] = poki;
        size++;
    }

}