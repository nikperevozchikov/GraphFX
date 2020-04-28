package sample;

public class Point {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point(double x) {
        this.x = x;
        this.y = (2 * x * x) + 5;
    }

    public void setX(double x) {
        this.x = x;
        this.y = (2 * x * x) + 5;
    }

}
