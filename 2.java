import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


class MaxSubarrayBruteForce {
    static class MaxSubarrayResult {
        int start;
        int end;
        int sum;

        public MaxSubarrayResult(int start, int end, int sum) {
            this.start = start;
            this.end = end;
            this.sum = sum;
        }
    }


    public static MaxSubarrayResult bruteForce(int[] A) {
        int maxSum = A[0];
        int maxStart = 0;
        int maxEnd = 0;

        for (int i = 0; i < A.length; i++)
            for (int j = i; j < A.length; j++) {
                int sum = 0;
                for (int s = i; s <= j; s++)
                    sum = sum + A[s];
                if (sum > maxSum) {
                    maxSum = sum;
                    maxStart = i;
                    maxEnd = j;
                }
            }
        return new MaxSubarrayResult(maxStart, maxEnd, maxSum);
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

    public static MaxSubarrayResult divide(int[] A, int low, int mid, int high) {

        int leftSum=0;
        int sum=0;
        int maxLeft=0;

        for(int i=mid;i>=low;i--){
            sum+=A[i];
            if(sum>leftSum){
                leftSum=sum;
                maxLeft=i;
            }
        }

        int rightSum =0;
        sum = 0;
        int maxRight = 0;
        for (int i = mid + 1; i <= high; i++) {
            sum += A[i];
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = i;
            }
        }

        return new MaxSubarrayResult(maxLeft, maxRight, leftSum + rightSum);
    }

    public  static MaxSubarrayResult findmax(int[] A, int low , int high){

        if(low == high){
            return new MaxSubarrayResult(low,high,A[low]);
        }
        else{
            int mid=(low+high)/2;

            MaxSubarrayResult LeftResult=findmax(A,low,mid);
            MaxSubarrayResult RightResult=findmax(A,mid+1,high);
            MaxSubarrayResult CrossingResult= divide(A,low,mid,high);

            if(LeftResult.sum >= RightResult.sum && LeftResult.sum >= CrossingResult.sum){
                return LeftResult;

            }
            else if(RightResult.sum>=LeftResult.sum && RightResult.sum>= CrossingResult.sum){
                return RightResult;
            }
            else
                return CrossingResult;
        }


    }

    public static void main(String[] args) throws FileNotFoundException {

        int[] A = readValues("C:\\Users\\PC\\IdeaProjects\\ada2\\src\\maxsub_10.txt");

        for (int i = 0; i < A.length; i++)
            System.out.print(A[i] + " ");
        System.out.println();

        //MaxSubarrayResult result = bruteForce(A);
        MaxSubarrayResult result = findmax(A,0,A.length-1);
        System.out.println("Maximum subarray sum: " + result.sum);
        System.out.println("Subarray indices: " + result.start + " to " + result.end);
    }
}