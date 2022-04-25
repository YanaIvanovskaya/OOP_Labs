import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Vector;

class ShapeCreatorTest {

    private static Vector<IShape> testShapes = new Vector<>();

    @BeforeEach
    void clearTestData() {
        testShapes.clear();
    }

    @Test
    @DisplayName("Если в описании 1 фигура - результатом парсинга будет список из 1 фигуры")
    void case_1() {
        IShape expected = new LineSegment(
                Color.decode("#123456"),
                new Point(1, 2),
                new Point(3, 4)
        );
        testShapes.add(expected);
        Assertions.assertEquals(testShapes, ShapeCreator.create("line 1 2 3 4 #123456"));
    }

    @Test
    @DisplayName("Если в описании 4 фигуры - результатом парсинга будет список из 4 фигур")
    void case_2() {
        IShape expected1 = new LineSegment(
                Color.decode("#123456"),
                new Point(1, 2),
                new Point(3, 4)
        );
        IShape expected2 = new Rectangle(
                Color.decode("#123453"),
                Color.decode("#12FF89"),
                new Point(5, 6),
                10,
                12
        );
        IShape expected3 = new Triangle(
                Color.decode("#FFFFFF"),
                Color.decode("#11FFFF"),
                new Point(1, 2),
                new Point(3, 4),
                new Point(5, 6)
        );
        IShape expected4 = new Circle(
                Color.decode("#FFFFFF"),
                Color.decode("#11FFFF"),
                new Point(5, 8),
                15
        );
        testShapes.add(expected1);
        testShapes.add(expected2);
        testShapes.add(expected3);
        testShapes.add(expected4);
        Assertions.assertEquals(testShapes,
                ShapeCreator.create("line 1 2 3 4 #123456, rectangle 5 6 10 12 #123453 #12FF89, triangle 1 2 3 4 5 6 #FFFFFF #11FFFF, circle 5 8 15 #FFFFFF #11FFFF"));
    }

    @Test
    @DisplayName("Если описание пустое, возвращаем null")
    void case_3() {

    }

    @Test
    @DisplayName("Если в описании фигуры есть много пробелов, они не учитываются при парсинге")
    void case_4() {

    }

    @Test
    @DisplayName("В описании фигуры могут быть целые числа")
    void case_5() {

    }

    @Test
    @DisplayName("В описании фигуры могут быть десятичные числа")
    void case_6() {

    }

    @Test
    @DisplayName("В описании фигуры могут быть отрицательные числа")
    void case_7() {

    }

    @Test
    @DisplayName("Если описание фигур некорректно, выбрасываем исключение")
    void case_8() {

    }


}