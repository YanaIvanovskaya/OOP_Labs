import java.awt.*;

public class Triangle implements ISolidShape {

    public Triangle(
            Color mOutlineColor,
            Color mFillColor,
            Point mVertex1,
            Point mVertex2,
            Point mVertex3
    ) {
    }

    @Override
    public double getArea() {
        return 0;
    }

    @Override
    public double getPerimeter() {
        return 0;
    }

    @Override
    public int getOutlineColor() {
        return 0;
    }

    @Override
    public int getFillColor() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Point getVertex1() {
        return null;
    }

    public Point getVertex2() {
        return null;
    }

    public Point getVertex3() {
        return null;
    }

}
