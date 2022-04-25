import java.awt.*;

public class LineSegment implements IShape {

    public LineSegment(
            Color mOutlineColor,
            Point mStart,
            Point mEnd
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
    public String toString() {
        return super.toString();
    }

    public Point getStartPoint() {
        return null;
    }

    public Point getEndPoint() {
        return null;
    }

}
