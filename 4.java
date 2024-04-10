import java.io.File;
import java.util.Scanner;

class InversionCount{
    public static int inversionCount(int[] arr){
        return mergeSort(arr, 0, arr.length-1);
    }
    public static int mergeSort(int[] arr, int l, int r){
        int count = 0;
        if(l<r){
            int m = (l+r)/2;
            count += mergeSort(arr, l, m);
            count += mergeSort(arr, m+1, r);
            count += merge(arr, l, m, r);
        }
        return count;
    }
    public static int merge(int[] arr, int l, int m, int r){
        int n1 = m-l+1;
        int n2 = r-m;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for(int i=0; i<n1; i++){
            L[i] = arr[l+i];
        }
        for(int i=0; i<n2; i++){
            R[i] = arr[m+1+i];
        }
        int i=0, j=0, k=l, count=0;
        while(i<n1 && j<n2){
            if(L[i]<=R[j]){
                arr[k] = L[i];
                i++;
            }else{
                arr[k] = R[j];
                j++;
                count += n1-i;
            }
            k++;
        }
        while(i<n1){
            arr[k] = L[i];
            i++;
            k++;
        }
        while(j<n2){
            arr[k] = R[j];
            j++;
            k++;
        }
        return count;
    }

    public static int inversionCountBruteForce(int[] arr){
        int count = 0;
        for(int i=0; i<arr.length-1; i++){
            for(int j=i+1; j<arr.length; j++){
                if(arr[i]>arr[j]){
                    count++;
                }
            }
        }
        return count;
    }

}

class Main{
    public static void main(String[] args){
        int[] arr;

        try{
            File file = new File("C:\\Users\\Mihai\\Desktop\\ADA\\lab6\\inversion_1000.txt");
            Scanner sc = new Scanner(file);
            int n = sc.nextInt();
            arr = new int[n];
            for(int i=0; i<n; i++){
                arr[i] = sc.nextInt();
            }
            sc.close();
        }
        catch(Exception e){
            arr = new int[0];
            System.out.println(e);
        }
        int[] tmp = arr.clone();
        System.out.println(InversionCount.inversionCount(arr));
        System.out.println(InversionCount.inversionCountBruteForce(tmp));
    }
}