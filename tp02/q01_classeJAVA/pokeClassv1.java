import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class pokeClassv1 {

    public static void main (String [] args) throws FileNotFoundException{

        //System.out.println("Current working directory: " + System.getProperty("user.dir"));

        String csvFilePath = "C:/Users/raque/Documents/2periodoCCpuc/algoritmos_dados_2/tp02/q01_classejava/pokemon.csv";

        File arquivo = new File(csvFilePath);

        Scanner sc = new Scanner(arquivo);

        //pular a linha do cabeçalho
        sc.nextLine();

        Pokemon pok = new Pokemon(sc, 7);

        pok.printPoke();

    }

}

class Pokemon{

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
    private String captureDate;

    //constutor que le a PRIMEIRA linha do csv e inicializa os atributos
    public Pokemon(Scanner scanner) {

        //le uma linha inteira do arquivo e guarda numa string gigante
        String line = scanner.nextLine();

        //está lendo ok
        //System.out.println(line);

        //depois, pega a linha que lemos e a separa/quebra em um vetor com os nossos dados iniciais (antes do [)
        String[] firstPart = line.split("\\[");

        //pega esse vetor de dados iniciais e separa usando as vírgulas do delimitador
        String[] fields = firstPart[0].split(",");

        /*TESTANDO se dividiu corretamente
        for (int i = 0; i < 3; i++){
            System.out.println(fields[i]);
        }*/

        //agora, convertemos as strings nesse vetor para os tipos necessários para serem armazenados
        //sabemos que as quatro primeiras certamente estao nessa ordem
        this.id = Integer.parseInt(fields[0]);
        this.generation = Integer.parseInt(fields[1]);
        this.name = fields[2];
        this.description = fields[3];

        /*TESTANDO -> ok, até aqui está certo
        System.out.println("id: " + this.id);
        System.out.println("generation: " + this.generation);
        System.out.println("name: " + this.name);
        System.out.println("description: " + this.description);
        */

        //sempre tem pelo menos 1 tipo
        this.types = new ArrayList<String>();
        this.types.add(fields[4]);

        if (fields[5].equals("")){

            this.types.add("0");

        }else{

            this.types.add(fields[5]);

        }

        //TESTANDO -> ok, até aqui
        //System.out.println("types: " + this.types);

        //dividindo a string original até achar apenas a parte entre os caracteres [ ], onde estão as habilidades
        String[] secondPart = firstPart[1].split("\\]");

        //achada a parte onde estão as habilidades, as dividimos entre si usando como delimitador a vírgula
        String[] habilidades = secondPart[0].split(",");

        //guardamos no atributo
        this.abilities = new ArrayList<String>(Arrays.asList(habilidades));

        //TESTE System.out.println("abilities: " + this.abilities);

        String [] lastData = secondPart[1].split(",");

        this.weight = Double.parseDouble(lastData[1]);
        this.height = Double.parseDouble(lastData[2]);
        this.captureRate = Integer.parseInt(lastData[3]);
        this.isLegendary = lastData[4].equals("1");

        /*converter a data do formato dd/MM/yyyy para LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.captureDate = LocalDate.parse(lastData[5], formatter);
        */

        this.captureDate = lastData[5];
        
        /*TESTE
        System.out.println("weight: " + this.weight);
        System.out.println("height: " + this.height);
        System.out.println("captureRate: " + this.captureRate);
        System.out.println("isLegendary: " + this.isLegendary);
        System.out.println("captureDate: " + this.captureDate); 
        */
    }

    //construtor que passamos como parametro o id do pokemon!
    public Pokemon(Scanner scanner, int myID) {

        for(int x = 0; x < myID - 1; x++){

            scanner.nextLine();

        }

        String line = scanner.nextLine();

        String[] firstPart = line.split("\\[");

        String[] fields = firstPart[0].split(",");

        this.id = Integer.parseInt(fields[0]);
        this.generation = Integer.parseInt(fields[1]);
        this.name = fields[2];
        this.description = fields[3];

        this.types = new ArrayList<String>();
        this.types.add(fields[4]);

        if (fields[5].equals("")){

            this.types.add("0");

        }else{

            this.types.add(fields[5]);

        }

        String[] secondPart = firstPart[1].split("\\]");

        String[] habilidades = secondPart[0].split(",");

        this.abilities = new ArrayList<String>(Arrays.asList(habilidades));

        String [] lastData = secondPart[1].split(",");

        this.weight = Double.parseDouble(lastData[1]);
        this.height = Double.parseDouble(lastData[2]);
        this.captureRate = Integer.parseInt(lastData[3]);
        this.isLegendary = lastData[4].equals("1");

        this.captureDate = lastData[5];
    }

    //método para printar o pokemon
    public void printPoke(){

        /*
        System.out.println("id: " + this.id);
        System.out.println("generation: " + this.generation);
        System.out.println("name: " + this.name);
        System.out.println("description: " + this.description);
        System.out.println("types: " + this.types);
        System.out.println("abilities: " + this.abilities);
        System.out.println("weight: " + this.weight);
        System.out.println("height: " + this.height);
        System.out.println("captureRate: " + this.captureRate);
        System.out.println("isLegendary: " + this.isLegendary);
        System.out.println("captureDate: " + this.captureDate);
        */
    }


}
