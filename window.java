import javax.swing.*;
import java.awt.event.*; 
import java.awt.Insets;
import java.awt.Color; 


public class window extends JFrame {
    private DrawingPanel drawingPanel;

    public window() {
        setTitle("jaikin");

        setSize(700, 700);
        setResizable(false);
        setLocation(1920 / 2 - 350, 1080 / 2 - 350);
        getContentPane().setBackground(Color.BLACK);
 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));

        JButton btnClean = new JButton("Clean");
        JButton btnExit = new JButton("Exit");

        btnClean.setBounds(5, 5, 60, 25);
        btnClean.setBackground(Color.white);
        btnClean.setBorderPainted(false);
        btnClean.setFocusPainted(false);
        Insets i = new Insets(0, 0, 0, 0);
        btnClean.setMargin(i);

        btnExit.setBounds(70, 5, 60, 25);
        btnExit.setBackground(Color.white);
        btnExit.setBorderPainted(false);

        add(btnClean);
        add(btnExit);
 
        drawingPanel = new DrawingPanel();
        drawingPanel.setBounds(0, 30, 700, 660);
        add(drawingPanel);

        btnClean.addActionListener(e -> {
            drawingPanel.clearPoints();
            drawingPanel.anim = false;
        });

        btnExit.addActionListener(e -> System.exit(0));
 

        InputMap map = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        map.put(KeyStroke.getKeyStroke("ESCAPE"), "escapePressed");
        map.put(KeyStroke.getKeyStroke("NUMPAD0"), "space");
        map.put(KeyStroke.getKeyStroke("ENTER"), "smooth");

        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put("escapePressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        actionMap.put("space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.clearPoints();
                drawingPanel.anim = false;

            }
        });

        actionMap.put("smooth", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.anim = true;

                drawingPanel.startAnimation();
            }
        });

        setVisible(true);
    }



}