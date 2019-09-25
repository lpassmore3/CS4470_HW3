import java.util.ArrayList;

// Class for a rectangle ink object
public class RectangleInk extends Ink {

    Point mouseStart;
    Point mouseEnd;

    String color = "";

    public RectangleInk(Point mouseStart, Point mouseEnd) {
        this.mouseStart = mouseStart;
        this.mouseEnd = mouseEnd;
        this.type = "rectangle";
        this.color = "black";
    }

    public Point getMouseStart() {
        return this.mouseStart;
    }

    public Point getMouseEnd() {
        return this.mouseEnd;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

}