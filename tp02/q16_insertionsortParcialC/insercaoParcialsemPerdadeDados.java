import java.util.Arrays;

public class insercaoParcialsemPerdadeDados {

    public static void main(String[] args) {

        String[] nomes = new String [7];

        nomes[0] = "Charlie";
        nomes[1] = "Bob";
        nomes[2] = "Robert";
        nomes[3] = "Donald";
        nomes[4] = "Ana";
        nomes[5] = "Ben";
        nomes[6] = "Aia";

        int n = 7;
        int k = 3;
        int j = 0;

        for (int i = 1; i < n; i++) {

            String current = nomes[i];
            boolean shouldInsert = false;
            String displaced = null; // Initialize outside to ensure accessibility
            
            /*Note: Ensure that the array has enough space to accommodate array[k]. 
            If n is the size of the array, k should be less than n (k < n) to prevent 
            ArrayIndexOutOfBoundsException.*/
        
            if (i < k) {

                j = i - 1;
                shouldInsert = true;

            } else {

                j = k - 1;
                // If current is smaller than the largest in top k, prepare to insert
                if (nomes[j].compareTo(current) > 0) {
                    shouldInsert = true;
                    displaced = nomes[k]; // Save the element that will be overwritten
                }
            }

            System.out.println(displaced);
        
            if (shouldInsert) {
                // Shift elements greater than current to the right within first k elements
                while (j >= 0 && nomes[j].compareTo(current) > 0) {
                    nomes[j + 1] = nomes[j];
                    j--;
                }
        
                // Insert current element into the correct position
                nomes[j + 1] = current;
        
                // If insertion was from beyond k, place displaced element back into array[i]
                if (i > k && displaced != null) {
                    nomes[i] = displaced;
                }
            }

            System.out.println(Arrays.toString(nomes));
        }


    }


}