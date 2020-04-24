package sample;

public class Point {
    public double getX() {
        return x;
    }

    public Point(double x) {
        this.x = x;
        double u = (2*x * x)  + 5;
        this.y = round(u,5) ;
    }

    public void setX(double x) {
        this.x = x;
        this.y = (2*x * x)  + 5;
    }

    private double x;

    public double getY() {
        return y;
    }

    private double y;

    private double round(double number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        double tmp = number * pow;
        return (double) (int) ((tmp - (int) tmp) >= 0.5 ? tmp + 1 : tmp) / pow;
    }

}
