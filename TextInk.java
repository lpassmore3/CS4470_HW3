import java.util.ArrayList;

// Class for a text ink object
public class TextInk extends Ink {

    Point mouseStart;
    Point mouseEnd;

    String text = "";
    ArrayList<String> lines;

    public TextInk(Point mouseStart, Point mouseEnd) {
        this.mouseStart = mouseStart;
        this.mouseEnd = mouseEnd;
        this.type = "text";
        lines = new ArrayList<String>();
    }

    public Point getMouseStart() {
        return this.mouseStart;
    }

    public Point getMouseEnd() {
        return this.mouseEnd;
    }

    public String getText() {
        return text;
    }

    public void addToText(char c) {
        text += c;
    }

    public void removeChar() {
        text = text.substring(0, text.length() - 1);
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void addLine(String line) {
        lines.add(line);
    }

}