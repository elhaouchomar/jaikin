import javax.swing.*;
import java.awt.event.*;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints; 
import java.awt.Color;
import java.util.*;
import javax.swing.Timer;

class DrawingPanel extends JPanel {
    private List<Point> points = new ArrayList<>();
    private java.util.List<List<Point>> chaikinSteps = new java.util.ArrayList<>();
    private int currentStep = 0;
    private Timer animationTimer;
    private final int MAX_STEPS = 7;
    public boolean anim = false;

    public DrawingPanel() {
        setOpaque(false);  

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                points.add(e.getPoint());
                repaint();  
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
            Color c = new Color(0, 237, 79,120 );

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.white); 
        for (Point p : points) {
            int radius = 4;
            g2.drawOval(p.x - radius / 2, p.y - radius / 2, radius, radius);
        }
        if (points.size() == 1) {
            return;
        } else if (points.size() == 2 && anim) {
            g2.setColor(c);
            g2.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
            return;
        } else if (!chaikinSteps.isEmpty()) {
            g2.setColor(c);

            List<Point> newPoitns = chaikinSteps.get(currentStep);
           
            for (int i = 0; i < newPoitns.size() - 1; i++) {
                Point p1 = newPoitns.get(i);
                Point p2 = newPoitns.get(i + 1);
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    private List<Point> chaikinStep(List<Point> input) {
        List<Point> output = new ArrayList<>();
        if (input.size() < 2)
            return output;

        output.add(input.get(0));  

        for (int i = 0; i < input.size() - 1; i++) {
            Point p0 = input.get(i);
            Point p1 = input.get(i + 1);

            int qx = (int) (0.75 * p0.x + 0.25 * p1.x);
            int qy = (int) (0.75 * p0.y + 0.25 * p1.y);

            int rx = (int) (0.25 * p0.x + 0.75 * p1.x);
            int ry = (int) (0.25 * p0.y + 0.75 * p1.y);

            output.add(new Point(qx, qy));
            output.add(new Point(rx, ry));
        }

        output.add(input.get(input.size() - 1));
        return output;
    }

    public void prepareChaikinSteps() {
        chaikinSteps.clear();
        currentStep = 0;

        if (points.size() == 1 || points.size() == 2)
            return;

        // Original points
        chaikinSteps.add(new ArrayList<>(points));

        List<Point> current = new ArrayList<>(points);
        for (int i = 0; i < MAX_STEPS; i++) {
            current = chaikinStep(current);
            chaikinSteps.add(current);
        }
    }

    public void startAnimation() {
        currentStep = 0;
        if (points.size() == 0) {
            JOptionPane.showMessageDialog(this, "Please draw at least one point first.", "No Points",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (points.size() == 1 || points.size() == 2) {
            repaint(); 
            return;
        }

        prepareChaikinSteps();
        currentStep = 0;
        repaint();

        animationTimer = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                currentStep++;
                if (currentStep >= chaikinSteps.size()) {
                    currentStep = 0;
                }
            }
        });
        animationTimer.start();
    }

    void clearPoints() {
        points.clear();
        chaikinSteps.clear();
        currentStep = 0;
        if (animationTimer != null)
            animationTimer.stop();
        repaint();
    }
}