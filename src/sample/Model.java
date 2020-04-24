package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Model {

        private static ArrayList<Point> arrayList= new ArrayList<>();

        public static ArrayList<Point> getArrayList() {
            return arrayList;
        }

        public static boolean addPoint (Point point){
            for(Point p:arrayList){
                if (p.getX() == point.getX()) {
                    return false;
                }
            }
            arrayList.add(point);
            arrayList.sort(new Comparator<Point>(){
                @Override
                public int compare(Point p1, Point p2)
                {
                    return  Double.compare(p1.getX(),p2.getX());
                }
            });
            return true;
        }

        public static void setPoint (double old, double newV){
            for(Point p:arrayList){
                if (p.getX() == old) {
                    p.setX(newV);
                }
            }
        }
        public static void removePoint (Point point){
            arrayList.remove(point);
        }

    }
