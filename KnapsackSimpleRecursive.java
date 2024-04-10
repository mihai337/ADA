import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class KnapsackSimpleRecursive {

    /**
     *
     * @param weights the weights of candidate items.
     *                Only the weights at indexes [0..n-1] are considered.
     * @param n number of item weights considered.
     * @param k target sum of weights
     * @return
     */
    public static boolean hasSolution(int[] weights, int n, int k) {
        if (n == 1) { // only one candidate item in weights
            if (weights[n-1] == k)  //last item is at index [n-1]
                return true;
            else
                return false;
        }
        // more than one candidate item in weights
        if (hasSolution(weights, n - 1, k))
            return true;
        else {
            if (weights[n-1] == k)
                return true;
            else if (k - weights[n-1] > 0)
                return hasSolution(weights, n - 1,
                        k - weights[n-1]);
            else
                return false;
        }
    }

    public static boolean hasSolutionDP(int[] weights, int n, int k) {
        boolean[][] dp = new boolean[n+1][k+1];
        for (int i=0; i<=n; i++) {
            dp[i][0] = true;
        }
        for (int i=1; i<=n; i++) {
            for (int j=1; j<=k; j++) {
                if (j-weights[i-1] >= 0) {
                    dp[i][j] = dp[i-1][j] || dp[i-1][j-weights[i-1]];
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[n][k];
    }

    public static void executeTest(String filename) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(filename));

        int K = scanner.nextInt(); // target value for sum of weights

        int n = scanner.nextInt(); // number of weights

        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }
        scanner.close();
        System.out.println("K="+K+" n="+n);

        long start =System.currentTimeMillis();

        boolean isPossible = hasSolutionDP(weights,n, K);

        long end = System.currentTimeMillis();
        System.out.println("Computation time =" + (end - start) + " ms");

        System.out.println("Result: The knapsack is possible is " + isPossible);
        System.out.println();
    }

    public static void main(String[] args) throws FileNotFoundException {
        executeTest("C:\\Users\\Mihai\\Desktop\\ADA\\lab7\\knapsack_6_15.txt");
        executeTest("C:\\Users\\Mihai\\Desktop\\ADA\\lab7\\knapsack_6_26.txt");
        executeTest("C:\\Users\\Mihai\\Desktop\\ADA\\lab7\\knapsack_100_785.txt");

        // next ones take very long time!
        // implement the dynamic programming version to make them work!
        executeTest("C:\\Users\\Mihai\\Desktop\\ADA\\lab7\\knapsack_1000_3189.txt");

    }

}
