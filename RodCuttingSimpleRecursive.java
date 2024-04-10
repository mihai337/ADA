import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Rod cutting problem: we have a rod of a given length.
 * We can cut pieces of any size in the range 1..length.
 * Every piece length has a price,  prices[i-1] is the profit
 * for a piece of size i.
 * The length of array prices ie equal with the length of the rod.
 * Which is the maximum profit that can be obtained by cutting the rod?
 */
public class RodCuttingSimpleRecursive {

    public static int rodCutting(int[] prices, int length) {
        if (length==0) {
            return 0;
        }

        int maxProfit=Integer.MIN_VALUE;
        for (int i=1; i<=length; i++) {
            // max profit is obtained with or without cutting in this step a piece of size i?
            maxProfit = Math.max(maxProfit, prices[i-1] + rodCutting(prices, length - i));
        }

        return maxProfit;
    }

    public static int rodCuttingDP(int[] prices, int length) {
        int[] dp = new int[length+1];
        dp[0]=0;

        for (int i=1; i<=length; i++) {
            int maxProfit=Integer.MIN_VALUE;
            for (int j=1; j<=i; j++) {
                maxProfit = Math.max(maxProfit, prices[j-1] + dp[i-j]);
            }
            dp[i]=maxProfit;
        }

        return dp[length];
    }

    private static int[] readValues(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int n = scanner.nextInt();

        int[] all = new int[n];
        for (int i = 0; i < n; i++) {
            all[i] = scanner.nextInt();
        }
        scanner.close();
        return all;
    }

    public static void executeTest(String filename) throws FileNotFoundException {

        int[] prices =readValues(filename);
        // prices[i-1] holds the price of a piece of size i

        // length of rod equals number of prices
        int n=prices.length;

        System.out.println("length of rod n="+n);

        long start =System.currentTimeMillis();

        int maxProfit = rodCuttingDP(prices,n);

        long end = System.currentTimeMillis();
        System.out.println("Computation time =" + (end - start) + " ms");

        System.out.println("Result: The maximum profit is: " + maxProfit);
    }

    public static void main(String[] args) throws FileNotFoundException {
        executeTest("C:\\Users\\Mihai\\Desktop\\ADA\\lab7\\rodCut_10.txt");
        executeTest("C:\\Users\\Mihai\\Desktop\\ADA\\lab7\\rodCut_20.txt");
        executeTest("C:\\Users\\Mihai\\Desktop\\ADA\\lab7\\rodCut_30.txt");

        // next ones take very long time!
        // implement the dynamic programming version to make them work!
        executeTest("C:\\Users\\Mihai\\Desktop\\ADA\\lab7\\rodCut_40.txt");
        // executeTest("C:\Users\Mihai\Desktop\ADA\lab7\Cut_50.txt");
        // executeTest("C:\Users\Mihai\Desktop\ADA\lab7\RodCuttingSimpleRecursive.javarodCut_100.txt");
    }
}
