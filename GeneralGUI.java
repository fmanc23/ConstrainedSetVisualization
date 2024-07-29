package view;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;

public class GeneralGUI implements KeyListener {

        private static final int JFRAMEWIDTH = 1400;
        private static final int JFRAMEHEIGHT = 640+75+36; // +36 is for adjustment only


        protected GeneralGUI() {
            // Creating a JFrame
            JFrame frame = new JFrame("Algoritmo");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setSize(new Dimension(JFRAMEWIDTH, JFRAMEHEIGHT));
            frame.setResizable(false);
            //frame.setIconImage();

            // Content pane
            Container contPane = frame.getContentPane();

            // JPanels for GameGUI and StatGUI
            MainGUI mainPane = new MainGUI();
            // Adding the JPanels to the Content Pane
            contPane.add(mainPane, BorderLayout.CENTER);

            // Key Listener
            frame.addMouseWheelListener(mainPane);
            frame.addMouseMotionListener(mainPane);
            frame.addMouseListener(mainPane);
            frame.setFocusable(true);
        }


        // KeyListener methods
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}     
}
