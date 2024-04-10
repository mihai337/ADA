import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Point{
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    private static double getDistance(Point p1, Point p2){
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    public static double getSmallestDistanceBruteForce(List<Point> list){
        double smallestDistance = Double.MAX_VALUE;
        int mini=0;
        int minj=0;
        for(int i = 0; i < list.size(); i++){
            for(int j = i + 1; j < list.size(); j++){
                double distance = getDistance(list.get(i), list.get(j));
                if(distance < smallestDistance){
                    mini = i;
                    minj = j;
                    smallestDistance = distance;
                }
            }
        }
        System.out.println("The closest points are: (" + list.get(mini).getX() + " " + list.get(mini).getY() + ") and (" + list.get(minj).getX() + " " + list.get(minj).getY()+")");
        return smallestDistance;
    }

    public static double getSmallestDistanceDivideAndConquer(List<Point> list){
        if(list.size() == 3){
            return getSmallestDistanceBruteForce(list);
        }
        if(list.size() == 2){
            return getDistance(list.get(0), list.get(1));
        }

        int mid = list.size() / 2;
        double dl = getSmallestDistanceDivideAndConquer(list.subList(0, mid));
        double dr = getSmallestDistanceDivideAndConquer(list.subList(mid, list.size()));
        double d = Math.min(dl, dr);

        //add points to strip
        ArrayList<Point> strip = new ArrayList<Point>();
        for(int i = 0; i < list.size(); i++){   
            if(Math.abs(list.get(i).getX() - list.get(mid).getX()) < d){
                strip.add(list.get(i));
            }
        }

        return Math.min(d, getSmallestDistanceBruteForce(strip));
    }
}
 

class Main{
    public static void main(String[] args) {
        ArrayList<Point> points = new ArrayList<Point>();
        try{
            File file = new File("C:\\Users\\Mihai\\Desktop\\ADA\\lab6\\points_1000.txt");
            Scanner scanner = new Scanner(file);
            int size = scanner.nextInt();

            while(scanner.hasNext()){
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                points.add(new Point(x, y));
            }
            scanner.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }

        System.out.println(Point.getSmallestDistanceBruteForce(points));
        points.sort((p1, p2) -> p1.getX() - p2.getX());
        System.out.println(Point.getSmallestDistanceDivideAndConquer(points));
    }
}