import java.util.Random;

/**
 * This is the main class which contains the the function Online.
 * The data provided by the data in the assignment.
 * 
 * @author: Ofir Sasson and Amit Reuveni
 * @version: 2017
 */
public class Main
{
    static final int MIN = 0;      // The smallest number in each array
    static final int MAX = 1023;   // The largest number in each array
    

    // The main program
    public static void main(String arg[])
    {
        int [] test = {5 ,798, 142, 183, 800, 293, 640, 172, 21, 1};
        
        for(int i = 0; i<test.length; i++)
            System.out.print( test[i] + " ");

        System.out.print("\n");
        kSmallest(test, 4);
    

        /*// Set three arrays as described in the assignment.
        int [] A = new int [200];
        int [] B = new int [400];
        int [] C = new int [800];

        //  Fill the arrays with random numbers
        fillArray(A);
        fillArray(B);
        fillArray(C);

        // Find smallest k numbers with k configured as described in the assignment.
        kSmallest(A, 10);
        kSmallest(A, 50);
        kSmallest(A, 100);

        kSmallest(B, 10);
        kSmallest(B, 50);
        kSmallest(B, 100);

        kSmallest(C, 10);
        kSmallest(C, 50);
        kSmallest(C, 100);*/
    }   
   
    /**
     * The funtion puts random value in an array
     * 
     * @param: array, the array we want to fill
     */
    public static void fillArray(int [] array)
    {
        int rand;
        
        for (int i = 0; i< array.length; i++)
        {
            // Random number in the range [MIN, MAX]
            rand = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
            array[i] = rand;
        }
    }
      
    /**
     * The funtion get an array and a number k and print the smallest k numbers in the array sorted.
     * 
     * @param: array, filled with numbers.
     * @param: k, number.
     */
    public static void kSmallest(int [] array, int k)
    {
        // Set three points as described in the assignment.
        //int n1 = (array.length) / 4;
        //int n2 = (array.length) / 2;
        //int n3 = (3 * array.length) / 4;
        int n1 = 3;
        int n2 = 5;
        int n3 = 6;
        
        // Create red-black tree with the k in the input
        RedBlackTree<Integer> rbt = new RedBlackTree<Integer>(k);
        
        // Create value that indicate the current index in the array
        int i = 0;
    
        // While it's not the end of the array
        for (i = 0; i<array.length; i++)
        {
            // If we got to a check point, print the tree
            if (i == n1 || i == n2 || i == n3)
            {
                rbt.printkMin();
                System.out.print("\n");
            }

            // Insert the current value in the array to the tree and move i to the next index.
            rbt.insert(array[i]);
        }

        rbt.printkMin();
        /*for (int i = 0; i<array.length; i++)
        {
            // If we got to a check point, print the tree
            if (i == n1 || i == n2 || i == n3)
            {
                rbt.printkMin();
                System.out.print("\n");
            }
            if (i<k)
            {
                    rbt.insert(array[i]);
            }
            else
            {
                    RedBlackTree<Integer> max = TreeMaximum(rbt);
                    if (array[i]<max.key)
                    {
                        rbt.remove();
                        insert(array[i]);
            
                    }
             }
        }*/
    }
}
