import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

//input (1,11,5), (2,6,7), (3,13,9), (12,7,16), (14,3,25), (19,18,22), (23,13,29), (24,4,28)
//output (1, 11), (3, 13), (9, 0), (12, 7), (16, 3), (19, 18), (22, 3), (23, 13), (29, 0)

class Building{
    private int left;
    private int right;
    private int height;
    private int max;


    public Building(int left, int height, int right){
        this.left = left;
        this.right = right;
        this.height = height;

        if(right > max)
            max = right;
    }

    public String toString(){
        return "(" + left + ", " + height + ", " + right + ")";
    }

    public int getLeft(){
        return left;
    }

    public int getRight(){
        return right;
    }

    public int getHeight(){
        return height;
    }

    public int getMax(){
        return max;
    }
}

class Skyline{
    
    private ArrayList<Integer> skyline = new ArrayList<Integer>();

    private ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right){
        ArrayList<Integer> result = new ArrayList<Integer>();
        int i = 0;
        int j = 0;
        while(i < left.size() && j < right.size()){
            if(left.get(i) > right.get(j)){
                result.add(left.get(i));
            }
            else{
                if(left.get(i) < right.get(j)){
                    result.add(right.get(j));
                }
                else{
                    result.add(left.get(i));
                }
            }
            i++;
            j++;
        }
        while(i < left.size()){
            result.add(left.get(i));
            i++;
        }
        while(j < right.size()){
            result.add(right.get(j));
            j++;
        }
        return result;
    }

    public ArrayList<Integer> getSkyline(ArrayList<Building> buildings){
        if(buildings.size() == 1){
            ArrayList<Integer> result = new ArrayList<Integer>();
            for(int i=0;i<buildings.get(0).getMax();i++)
                result.add(0);
            for(int i=buildings.get(0).getLeft();i<=buildings.get(0).getRight();i++)
                result.add(i,buildings.get(0).getHeight());
            return result;
        }
        int mid = buildings.size() / 2;
        ArrayList<Integer> left = getSkyline(new ArrayList<Building>(buildings.subList(0, mid)));
        ArrayList<Integer> right = getSkyline(new ArrayList<Building>(buildings.subList(mid, buildings.size())));

        skyline = merge(left, right);
        return skyline;
    }

    public String toString(){
        String output = "";
        for(int i=1;i<skyline.size();i++){
            if(skyline.get(i) != skyline.get(i-1)){
                output += "(" + i + ", " + skyline.get(i) + "), ";
            }
        } 
        return output;
    }
}

class Main{
    public static void main(String[] args){
        Skyline skyline = new Skyline();
        ArrayList<Building> buildings = new ArrayList<Building>();
        try{
            File file = new File("C:\\Users\\Mihai\\Desktop\\ADA\\lab6\\skyline_8.txt");
            Scanner scanner = new Scanner(file);
            int size = scanner.nextInt();
            while(scanner.hasNext()){
                int left = scanner.nextInt();
                int height = scanner.nextInt();
                int right = scanner.nextInt();
                buildings.add(new Building(left, height, right));
            }
            scanner.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        skyline.getSkyline(buildings);
        System.out.println(skyline);

        int[] nums = {44,-11,23,-49,20,31};
        System.out.println(maxSubArray(nums));
    }

    public static int maxSubArray(int[] nums) {
        int max = nums[0];
        int sum = nums[0];
        for(int i=1;i<nums.length;i++){
            sum = Math.max(nums[i], sum + nums[i]);
            max = Math.max(max, sum);
        }
        return max;
    }
}
