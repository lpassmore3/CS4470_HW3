// This class is for the section of the right side of
// the Courier application that represents a page that
// can be drawn on.
//
// Author: Luke Austin Passmore, lpassmore3

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class MyPage extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;

    // Indicates what draw tool is selected
    // "stroke" = Free-Form Ink
    // "rect" = Rectangle
    // "oval" = Oval
    // "text" = Text
    private String drawTool = "";
    private String inkColor = "black";

    // The list of things to draw
    ArrayList<Ink> displayList = new ArrayList<Ink>();

    public MyPage() {
        //super();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public ArrayList<Ink> getDisplayList() {
        return displayList;
    }

    // The space between lines of text in sticky notes
    int leading = 5;
    // The font used in the sticky note text
    //Font font = new Font("Serif", Font.PLAIN, 24);
    // Indicates whether text in a sticky note is being created of not
    boolean editingText = false;


    @Override
    public void mousePressed(MouseEvent e) {
        int currentX = e.getX();
        int currentY = e.getY();

        if (drawTool == "stroke") {
            Stroke stroke = new Stroke(new Point(currentX, currentY));
            stroke.setColor(inkColor);
            displayList.add(stroke);
        } else if (drawTool == "rect") {
            Point p = new Point(currentX, currentY);
            RectangleInk rect = new RectangleInk(p, p);
            rect.setColor(inkColor);
            displayList.add(rect);
        } else if (drawTool == "oval") {
            Point p = new Point(currentX, currentY);
            OvalInk oval = new OvalInk(p, p);
            oval.setColor(inkColor);
            displayList.add(oval);
        } else if (drawTool == "text") {
            Point p = new Point(currentX, currentY);
            TextInk text = new TextInk(p, p);
            this.setFocusable(true);
            this.requestFocusInWindow();
            displayList.add(text);
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int currentX = e.getX();
        int currentY = e.getY();
        Point currPoint = new Point(currentX, currentY);
        int displayListSize = displayList.size();

        if (drawTool == "stroke") {
            Stroke stroke = (Stroke) displayList.get(displayListSize - 1);
            stroke.addToPointList(currPoint);
        } else if (drawTool == "rect") {
            RectangleInk oldRect = (RectangleInk) displayList.get(displayListSize - 1);
            Point startPoint = oldRect.getMouseStart();
            RectangleInk rect = new RectangleInk(startPoint, currPoint);
            rect.setColor(inkColor);
            displayList.remove(displayListSize - 1);
            displayList.add(rect);
        } else if (drawTool == "oval") {
            OvalInk oldOval = (OvalInk) displayList.get(displayListSize - 1);
            Point startPoint = oldOval.getMouseStart();
            OvalInk oval = new OvalInk(startPoint, currPoint);
            oval.setColor(inkColor);
            displayList.remove(displayListSize - 1);
            displayList.add(oval);
        } else if (drawTool == "text") {
            TextInk oldText = (TextInk) displayList.get(displayListSize - 1);
            Point startPoint = oldText.getMouseStart();
            TextInk text = new TextInk(startPoint, currPoint);
            displayList.remove(displayListSize - 1);
            displayList.add(text);
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int currentX = e.getX();
        int currentY = e.getY();
        Point currPoint = new Point(currentX, currentY);
        int displayListSize = displayList.size();

        if (drawTool == "stroke") {
            Stroke stroke = (Stroke) displayList.get(displayListSize - 1);
            stroke.addToPointList(currPoint);
        } else if (drawTool == "rect") {
            RectangleInk oldRect = (RectangleInk) displayList.get(displayListSize - 1);
            Point startPoint = oldRect.getMouseStart();
            RectangleInk rect = new RectangleInk(startPoint, currPoint);
            rect.setColor(inkColor);
            displayList.remove(displayListSize - 1);
            displayList.add(rect);
        } else if (drawTool == "oval") {
            OvalInk oldOval = (OvalInk) displayList.get(displayListSize - 1);
            Point startPoint = oldOval.getMouseStart();
            OvalInk oval = new OvalInk(startPoint, currPoint);
            oval.setColor(inkColor);
            displayList.remove(displayListSize - 1);
            displayList.add(oval);
        } else if (drawTool == "text") {
            TextInk oldText = (TextInk) displayList.get(displayListSize - 1);
            Point startPoint = oldText.getMouseStart();
            TextInk text = new TextInk(startPoint, currPoint);
            displayList.remove(displayListSize - 1);
            displayList.add(text);
            editingText = true;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        return;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        return;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        editingText = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (editingText && e.getKeyCode() == KeyEvent.VK_ENTER) {
            //System.out.println("Enter pressed");
            editingText = false;
            repaint();
        //}
        } else if (editingText && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            TextInk textInk = (TextInk) displayList.get(displayList.size() - 1);
            if (textInk.getText().length() > 0) {
                textInk.removeChar();
            }
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.println(e.getKeyChar());
        if (editingText && e.getExtendedKeyCode() != KeyEvent.VK_BACK_SPACE) {
            char c = e.getKeyChar();
            //System.out.println(e.getKeyChar());
            TextInk textInk = (TextInk) displayList.get(displayList.size() - 1);
            if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                textInk.addToText(c);
            }
            // ArrayList<String> lines = textInk.getLines();
            // if (lines.size() == 0) {
            //     String line = "" + c;
            //     textInk.addLine(line);
            // } else if ()
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // enable anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawPaperLines(g2);

        //g2.setColor(Color.BLACK);
        // if (inkColor == "black") {
        //     g2.setColor(Color.BLACK);
        // } else if (inkColor == "red") {
        //     g2.setColor(Color.RED);
        // } else if (inkColor == "green") {
        //     g2.setColor(Color.GREEN);
        // } else if (inkColor == "blue") {
        //     g2.setColor(Color.BLUE);
        // }

        // Iterate through the displayList and draw each object
        for (Ink o : displayList) {
            String type = o.getType();
            
            if (type == "stroke") {
                Stroke stroke = (Stroke) o;
                ArrayList<Point> pointList = stroke.getPointList();
                for (int n = 0; n < pointList.size() - 1; n++) {
                    Point startPoint = pointList.get(n);
                    Point endPoint = pointList.get(n + 1);
                    //g2.setColor(Color.BLACK);
                    String strokeColor = stroke.getColor();
                    if (strokeColor == "black") {
                        g2.setColor(Color.BLACK);
                    } else if (strokeColor == "red") {
                        g2.setColor(Color.RED);
                    } else if (strokeColor == "green") {
                        g2.setColor(Color.GREEN);
                    } else if (strokeColor == "blue") {
                        g2.setColor(Color.BLUE);
                    }
                    g2.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
                }
            } else if (type == "rectangle") {
                RectangleInk rect = (RectangleInk) o;
                Point startPoint = rect.getMouseStart();
                Point endPoint = rect.getMouseEnd();
                int width = endPoint.getX() - startPoint.getX();
                int height = endPoint.getY() - startPoint.getY();
                //g2.setColor(Color.BLACK);
                String rectColor = rect.getColor();
                if (rectColor == "black") {
                    g2.setColor(Color.BLACK);
                } else if (rectColor == "red") {
                    g2.setColor(Color.RED);
                } else if (rectColor == "green") {
                    g2.setColor(Color.GREEN);
                } else if (rectColor == "blue") {
                    g2.setColor(Color.BLUE);
                }
                g2.drawRect(startPoint.getX(), startPoint.getY(), width, height);
            } else if (type == "oval") {
                OvalInk oval = (OvalInk) o;
                Point startPoint = oval.getMouseStart();
                Point endPoint = oval.getMouseEnd();
                int width = endPoint.getX() - startPoint.getX();
                int height = endPoint.getY() - startPoint.getY();
                //g2.setColor(Color.BLACK);
                String ovalColor = oval.getColor();
                if (ovalColor == "black") {
                    g2.setColor(Color.BLACK);
                } else if (ovalColor == "red") {
                    g2.setColor(Color.RED);
                } else if (ovalColor == "green") {
                    g2.setColor(Color.GREEN);
                } else if (ovalColor == "blue") {
                    g2.setColor(Color.BLUE);
                }
                g2.drawOval(startPoint.getX(), startPoint.getY(), width, height);
            } else if (type == "text") {
                TextInk textInk = (TextInk) o;
                Point startPoint = textInk.getMouseStart();
                Point endPoint = textInk.getMouseEnd();
                int width = endPoint.getX() - startPoint.getX();
                int height = endPoint.getY() - startPoint.getY();
                g2.setColor(Color.GRAY);
                g2.fillRect(startPoint.getX() - (width / 10), startPoint.getY() + (height / 10), width - 1, height - 1);
                g2.setColor(Color.BLACK);
                g2.drawRect(startPoint.getX(), startPoint.getY(), width, height);
                g2.setColor(Color.YELLOW);
                g2.fillRect(startPoint.getX() + 1, startPoint.getY() + 1, width - 1, height - 1);

                g2.setColor(Color.BLACK);
                FontMetrics fm = g2.getFontMetrics();
                //System.out.println(fm.getAscent());
                //g2.drawString(textInk.getText(), startPoint.getX() + leading, startPoint.getY() + fm.getAscent() + leading);
                //g2.drawString(textInk.getText(), startPoint.getX() + leading, startPoint.getY() + fm.getAscent() + leading);
                String text = textInk.getText();
                ArrayList<String> lines = new ArrayList<String>();
                String textline = "";
                lines.add(textline);
                int y = startPoint.getY() + fm.getAscent() + leading;
                int dist = fm.getDescent() + leading + fm.getAscent();
                int lineWidth = 0;
                for (int i = 0; i < text.length(); i++) {
                    char c = text.charAt(i);
                    if (lineWidth + fm.charWidth(c) <= width - (leading * 2)) {
                        textline += c;
                        lines.remove(lines.size() - 1);
                        lines.add(textline);
                        lineWidth += fm.charWidth(c);
                    } else {
                        textline = "" + c;
                        lines.add(textline);
                        lineWidth = fm.charWidth(c);
                    }
                }
                //System.out.println("Height: " + height);
                for (int n = 0; n < lines.size(); n++) {
                    g2.setColor(Color.BLACK);
                    int newY = y + (dist * n);
                    //System.out.println("New Y: " + newY);
                    if (newY + fm.getDescent() + leading <= startPoint.getY() + height) {
                        g2.drawString(lines.get(n), startPoint.getX() + leading, newY);
                    } else {
                        editingText = false;
                        editingText = true;
                    }
                }
                //System.out.println("Text: " + textInk.getText() + ", " + Boolean.toString(editingText));
            } 
        }
    }

    // Creates the notebook paper backgroud
    private void drawPaperLines(Graphics2D g2) {

        int height = this.getHeight();
        int width = this.getWidth();

        int blueLineLead = 15;

        // draw the blue horizontal lines
        for (int i = height / 10; i <= height; i += blueLineLead) {
            g2.setColor(Color.blue);
            g2.drawLine(0, i, width, i);
        }

        // draw the red vertical line
        g2.setColor(Color.red);
        g2.drawLine(width / 10, 0, width / 10, height);
    }

    // Sets what tool is selected
    public void setTool(String tool) {
        drawTool = tool;
    }

    // Sets what ink color is selected
    public void setInkColor(String color) {
        inkColor = color;
    }


    // // Class for points used in ink objects
    // private class Point {

    //     int x;
    //     int y;

    //     public Point(int x, int y) {
    //         this.x = x;
    //         this.y = y;
    //     }

    //     public int getX() {
    //         return this.x;
    //     }

    //     public int getY() {
    //         return this.y;
    //     }

    // }

    // // Abstract class for all ink objects
    // private abstract class Ink {
    //     String type;

    //     public String getType() {
    //         return type;
    //     }
    // }

    // // Class for a free-form ink stroke object
    // private class Stroke extends Ink {

    //     ArrayList<Point> pointList = new ArrayList<Point>();

    //     public Stroke(Point p) {
    //         pointList.add(p);
    //         pointList.add(p);
    //         this.type = "stroke";
    //     }

    //     public ArrayList<Point> getPointList() {
    //         return this.pointList;
    //     }

    //     public void addToPointList(Point p) {
    //         pointList.add(p);
    //     }

    // }

    // // Class for a rectangle ink object
    // private class RectangleInk extends Ink {

    //     Point mouseStart;
    //     Point mouseEnd;

    //     public RectangleInk(Point mouseStart, Point mouseEnd) {
    //         this.mouseStart = mouseStart;
    //         this.mouseEnd = mouseEnd;
    //         this.type = "rectangle";
    //     }

    //     public Point getMouseStart() {
    //         return this.mouseStart;
    //     }

    //     public Point getMouseEnd() {
    //         return this.mouseEnd;
    //     }

    // }

    // // Class for a oval ink object
    // private class OvalInk extends Ink {

    //     Point mouseStart;
    //     Point mouseEnd;

    //     public OvalInk(Point mouseStart, Point mouseEnd) {
    //         this.mouseStart = mouseStart;
    //         this.mouseEnd = mouseEnd;
    //         this.type = "oval";
    //     }

    //     public Point getMouseStart() {
    //         return this.mouseStart;
    //     }

    //     public Point getMouseEnd() {
    //         return this.mouseEnd;
    //     }

    // }

    // // Class for a text ink object
    // private class TextInk extends Ink {

    //     Point mouseStart;
    //     Point mouseEnd;

    //     String text = "";
    //     ArrayList<String> lines;

    //     public TextInk(Point mouseStart, Point mouseEnd) {
    //         this.mouseStart = mouseStart;
    //         this.mouseEnd = mouseEnd;
    //         this.type = "text";
    //         lines = new ArrayList<String>();
    //     }

    //     public Point getMouseStart() {
    //         return this.mouseStart;
    //     }

    //     public Point getMouseEnd() {
    //         return this.mouseEnd;
    //     }

    //     public String getText() {
    //         return text;
    //     }

    //     public void addToText(char c) {
    //         text += c;
    //     }

    //     public void removeChar() {
    //         text = text.substring(0, text.length() - 1);
    //     }

    //     public ArrayList<String> getLines() {
    //         return lines;
    //     }

    //     public void addLine(String line) {
    //         lines.add(line);
    //     }

    // }

}