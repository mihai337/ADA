import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class KeyFrequency{
    int key;
    int freq;
    KeyFrequency(int key, int freq){
        this.key=key;
        this.freq=freq;
    }
}

public class OptimalBSTSimpleRecursive {

/*
Computes and returns the cost of the Optimal BST
built for frequencies between index i and  j.
Since we do not keep the tree the values of the keys
do not matter and are ignored.
However, we know that the array is given sorted in
ascending order of th keys.
 */
    public static int optCost(KeyFrequency[] keys, int i, int j) {
        if (i > j) {
            return 0;
        }
        if (i == j) {
            return keys[i].freq;
        }
        int minCost = Integer.MAX_VALUE;
        for (int r = i; r <= j; r++) { // for every node r between i and j
            // try to make r the root of the optimal BST, with recursively
            // left and right subtrees optimal BSTs;
            // calculate the cost, keep minimum
            int cost = optCost( keys, i, r - 1) + optCost(keys, r + 1, j) + freqSum( keys, i, j);
            if (cost < minCost) {
                minCost = cost;
            }
        }
        return minCost;
    }

    private static int freqSum(KeyFrequency[] keys,  int i, int j) {
        int sum = 0;
        for (int k = i; k <= j; k++) {
            sum += keys[k].freq;
        }
        return sum;
    }

    public static int optCostDP(KeyFrequency[] keys,int i , int j){
        int n = keys.length;
        int[][] dp = new int[n][n];
        for(int l=0;l<n;l++){
            for(int k=0;k<n;k++){
                dp[l][k] = 0;
            }
        }
        for(int l=0;l<n;l++){
            dp[l][l] = keys[l].freq;
        }
        for(int l=1;l<n;l++){
            for(int k=0;k<n-l;k++){
                int min = Integer.MAX_VALUE;
                for(int r=k;r<=k+l;r++){
                    int left = r-1>=k?dp[k][r-1]:0;
                    int right = r+1<=k+l?dp[r+1][k+l]:0;
                    int sum = freqSum(keys,k,k+l);
                    int cost = left+right+sum;
                    if(cost<min){
                        min = cost;
                    }
                }
                dp[k][k+l] = min;
            }
        }
        return dp[0][n-1];
    }


    private static KeyFrequency[] readValues(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int n = scanner.nextInt();

        KeyFrequency[] all = new KeyFrequency[n];
        for (int i = 0; i < n; i++) {
            int key = scanner.nextInt();
            int freq = scanner.nextInt();
            all[i] = new KeyFrequency(key, freq);
        }
        scanner.close();
        return all;
    }

    public static void executeTest(String filename) throws FileNotFoundException {
        KeyFrequency[] keys = readValues(filename);
        System.out.println("Optimal BST with input from " + filename);
        System.out.println("n=" + keys.length);
        long ts = System.currentTimeMillis();
        int rez = optCostDP(keys, 0, keys.length - 1);
        long tf = System.currentTimeMillis();
        System.out.println("Done in time[ms]=" + (tf - ts));
        System.out.println("Result: Cost of optimal BST is "+rez);
        System.out.println();
    }

    public static void main(String[] args) throws FileNotFoundException {

        executeTest("C:\\Users\\Mihai\\Desktop\\ADA\\lab7\\optbst_10.txt");
        executeTest("C:\\Users\\Mihai\\Desktop\\ADA\\lab7\\optbst_15.txt");
        executeTest("C:\\Users\\Mihai\\Desktop\\ADA\\lab7\\optbst_20.txt");

        // next ones take very long time!
        // implement the dynamic programming version to make them work!
        // executeTest("optbst_100.txt");
        // executeTest("optbst_1000.txt");
    }


}
