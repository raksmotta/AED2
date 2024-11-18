import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class pokeListaFlexivel{

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

        Lista pokelist = new Lista();

        while (!control.equals("FIM")) {

            control = verde.nextLine();

            if (!control.equals("FIM")) {

                idPokemon = Integer.parseInt(control);

                Pokemon p = new Pokemon (csvFilePath, idPokemon);

                pokelist.inserir(p.clonePokemon(), posicao);

                posicao++;
            }

        }
        
        //System.out.println("LISTA ORIGINAL:");
        //pokelist.mostrar();

        //System.out.println("qual a quantidade de comandos?");
        int qtd = verde.nextInt();
        verde.nextLine();

        int numeroPoke = 0;
        int posicaoPoke = 0;

        while (qtd > 0){

            //System.out.println("digite o comando");
            String s = verde.nextLine();

            String[] partes = s.split(" ");

            //System.out.println("Comando: " + partes[0]);

            if (partes[0].equals("RI")){

                Pokemon removido = pokelist.removerInicio();
                //System.out.println("(RI)" + removido.get_name());
                System.out.println("(R) " + removido.get_name());

            }else if (partes[0].equals("RF")){

                Pokemon removido = pokelist.removerFim();
                //System.out.println("(RF)" + removido.get_name());
                System.out.println("(R) " + removido.get_name());

            }else if (partes[0].equals("II")){

                numeroPoke = Integer.parseInt(partes[1]);
                Pokemon inserido = new Pokemon (csvFilePath, numeroPoke);
                pokelist.inserirInicio(inserido);
                //System.out.println("(II)" + inserido.get_name());

            }else if (partes[0].equals("IF")){

                numeroPoke = Integer.parseInt(partes[1]);
                Pokemon inserido = new Pokemon (csvFilePath, numeroPoke);
                pokelist.inserirFim(inserido);
                //System.out.println("(IF)" + inserido.get_name());

            }else if (partes[0].equals("R*")){

                posicaoPoke = Integer.parseInt(partes[1]);
                Pokemon removido = pokelist.remover(posicaoPoke);
                //System.out.println("(R*)" + removido.get_name() + " na posicao " +  posicaoPoke);
                System.out.println("(R) " + removido.get_name());

            }else if (partes[0].equals("I*")){

                posicaoPoke = Integer.parseInt(partes[1]);
                numeroPoke = Integer.parseInt(partes[2]);
                Pokemon inserido = new Pokemon (csvFilePath, numeroPoke);
                pokelist.inserir(inserido, posicaoPoke);
                //System.out.println("(I*)" + inserido.get_name() + " na posicao " +  posicaoPoke);

            }else{

                System.out.println("nao leu o comando corretamente");
            }

            qtd--;
        }

        //System.out.println("LISTA MODIFICADA:");
        pokelist.mostrarVerde();

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

}

class Lista {

    private Pokemon primeiro;
    private Pokemon ultimo;
    private int tam;

    //construtor que implementa a lógica de NÓ CABEÇA (ou sentinela)
    public Lista(){
        this.primeiro = new Pokemon();
        this.ultimo = primeiro;
        this.tam = 0;
    }

    void inserirInicio (Pokemon poke){

        poke.prox = primeiro.prox;
        primeiro.prox = poke;

        if(primeiro == ultimo){
            ultimo = poke;
        }

        tam++;
    }

    Pokemon removerInicio (){

        if (primeiro == ultimo){

            System.out.println("ERRO. chamou removerInicio para lista vazia");
            return null;

        }else{

            Pokemon tmp = primeiro.prox;
            primeiro.prox = tmp.prox;
            tmp.prox = null;
            if (primeiro.prox == null){
                ultimo = primeiro;
            }
            tam --;
            return tmp;
        }
    }

    void inserirFim (Pokemon poke){

        ultimo.prox = poke;
        ultimo = ultimo.prox;
        tam++;
    }

    Pokemon removerFim (){

        if (tam==0 || tam==1){

            return removerInicio();
            
        }else{

            Pokemon tmp = ultimo;
            
            Pokemon i = primeiro.prox;

            //queremos parar na penúltima célula
            while (i.prox.prox != null){
                i = i.prox;
            }

            ultimo = i;

            ultimo.prox = null;

            i = null;

            tam--;

            return tmp;
        }
    }

    void inserir (Pokemon poke, int pos){

        if ((pos < 0) || (pos > tam)){

            System.out.println("ERRO. chamou inserir para posicao invalida");

        }else if (pos == 0){

            inserirInicio(poke);

        }else if (pos == tam){

            inserirFim(poke);

        }else{

            //a posicao do primeiro (que é o nó cabeça) é -1. A posicao 0 seria primeiro.prox. Mas como queremos parar um pokemon ANTES daquele que desejamos inserir, começamos contando a partir da sentinela
            Pokemon i = primeiro;
            int count = 0;

            while (count < pos){
                i = i.prox;
                count++;
            }

            poke.prox = i.prox;
            i.prox = poke;

            tam++;
        }

    }

    Pokemon remover (int pos){

        if ((pos < 0) || (pos >= tam)){

            System.out.println("ERRO. chamou remover para posicao invalida");
            return null;

        }else if (pos == 0){

            return removerInicio();

        }else if (pos == tam - 1){

            return removerFim();

        }else{

            Pokemon i = primeiro;
            int count = 0;

            while (count < pos){
                i = i.prox;
                count++;
            }

            Pokemon tmp = i.prox;
            i.prox = tmp.prox;
            tmp.prox = null;

            tam--;

            return tmp;
        }
        
    }

    void mostrar (){

        for (Pokemon i = primeiro.prox; i!=null; i=i.prox){
            System.out.println(i.pokemonString());
        }
    }

    void mostrarVerde (){

        int x = 0;

        for (Pokemon i = primeiro.prox; i!=null; i=i.prox){
            System.out.println("[" + x + "] " + i.pokemonString());
            x++;
        }
    }
}
