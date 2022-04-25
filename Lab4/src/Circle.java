import java.awt.*;

public class Circle implements ISolidShape {

    public Circle(
            Color mOutlineColor,
            Color mFillColor,
            Point mCenter,
            double mRadius
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

    public Point getCenter() {
        return null;
    }

    public double getRadius() {
        return 0;
    }
}
