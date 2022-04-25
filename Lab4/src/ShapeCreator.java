import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Vector;

public final class ShapeCreator {

    private ShapeCreator() {
    }

    public static Vector<IShape> create(@NotNull String description) {
        if (description.isEmpty()) {
            return null;
        }
        Vector<IShape> shapes = new Vector<>();
        String[] shapeDescriptions = description.split(",");

        for (String desc : shapeDescriptions) {
            shapes.add(createShape(desc));
        }
        return shapes;
    }

    private static IShape createShape(String description) {
        //// rectangle x10 y8 w4 h12 o#456766 f#78686,
//// line 10 8 6 2 #456766,
//// triangle 10 8 4 6 7 8 #456766 #456766,
//// circle 10 8 4 #456766 #456766,

        String[] param = description.trim().split("( )+");

        try {
            IShape shape = switch (getShapeTypeOrNull(param[0])) {
                case LINE -> new LineSegment(
                        Color.decode(param[5]),
                        new Point(Integer.parseInt(param[1]), Integer.parseInt(param[2])),
                        new Point(Integer.parseInt(param[3]), Integer.parseInt(param[4]))
                );
                default -> null;
            };

        } catch (IndexOutOfBoundsException | NumberFormatException ex) {
            return null;
        }

        return null;
    }

    private static ShapeType getShapeTypeOrNull(String type) {
        return switch (type) {
            case "rectangle" -> ShapeType.RECTANGLE;
            case "line" -> ShapeType.LINE;
            case "triangle" -> ShapeType.TRIANGLE;
            case "circle" -> ShapeType.CIRCLE;
            default -> null;
        };
    }

    private enum ShapeType {
        TRIANGLE,
        RECTANGLE,
        LINE,
        CIRCLE
    }

}
