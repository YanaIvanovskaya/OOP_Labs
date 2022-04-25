import java.awt.*;

public class Rectangle implements ISolidShape {

    public Rectangle(
            Color mOutlineColor,
            Color mFillColor,
            Point mLeftTop,
            double mWidth,
            double mHeight
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

    public Point getLeftTop() {
        return null;
    }

    public Point getRightBottom() {
        return null;
    }

    public double getWidth() {
        return 0;
    }

    public double getHeight() {
        return 0;
    }
}
