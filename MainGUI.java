package view;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;


public class MainGUI extends JPanel implements ActionListener, MouseWheelListener, MouseMotionListener, MouseListener {

    private static int[][] draggablePointsCoordinates = new int[View.getAlgorithm().getGraphicNodeList().size()][3];
    private static int[][] oldDraggablePointsCoordinates = new int[View.getAlgorithm().getGraphicNodeList().size()][3];
    private static int[][] draggableLinesCoordinates = new int[View.getAlgorithm().getGraphicEdgeList().size()][5];
    private static int[][] oldDraggableLinesCoordinates = new int[View.getAlgorithm().getGraphicEdgeList().size()][5];
    private static Rectangle zoomIn = new Rectangle(1300, 651, 50, 20);
    private static Rectangle zoomOut = new Rectangle(1250, 651, 50, 20);
    private static Rectangle reset = new Rectangle(1200, 651, 50, 20);
    private static Rectangle[] checkBoxsRectangles = new Rectangle[View.getAlgorithm().getNumberOfColors()];
    private static double scale = 1.0;
    private static int mouseX = 0;
    private static int mouseY = 0;
    private static JLabel zoomInLabel = new JLabel("   +");
    private static JLabel zoomOutLabel = new JLabel("    -");
    private static JLabel resetLabel = new JLabel("reset");
    private static JCheckBox[] checkBoxs = new JCheckBox[View.getAlgorithm().getNumberOfColors()];
    private static Color[] colorsColor = new Color[]{null, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA,
        Color.ORANGE, Color.PINK, Color.LIGHT_GRAY, Color.BLACK, Color.CYAN};
    private static final Font FONT = new Font("SANS SERIF",0,20);
    private static final String[] colorsString = new String[]{"", "blu", "rosso", "verde", "giallo", "magenta",
        "arancione", "rosa", "grigio", "nero", "ciano"};
    private static Color[] colorsColorStatic = new Color[]{null, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA,
        Color.ORANGE, Color.PINK, Color.LIGHT_GRAY, Color.BLACK, Color.CYAN};

    // Creating the GameGUI for the Game to appear
    protected MainGUI() {
        // Setting the size of the JPanel
        super.setSize(new Dimension(1400, 640+75+36));
        // JFrame must not modify the layout
        super.setLayout(null);
        super.isFocusable();
        super.setBackground(Color.WHITE);
        // Add the labels
        super.add(resetLabel, BorderLayout.CENTER);
        super.add(zoomOutLabel);
        super.add(zoomInLabel);
        for(int i=1; i<checkBoxs.length; i++){
            //Rectangles for checkBoxs
            checkBoxsRectangles[i] = new Rectangle(20, 480+(20*i), 200, 30);
            //checkBoxs
            checkBoxs[i] = new JCheckBox(colorsString[i]);
            super.add(checkBoxs[i], BorderLayout.CENTER);
            checkBoxs[i].setFont(FONT);
            checkBoxs[i].setBounds(checkBoxsRectangles[i]);
            checkBoxs[i].doClick();
        }
        
        // Set the font o the labels
        resetLabel.setFont(FONT);
        zoomInLabel.setFont(FONT);
        zoomOutLabel.setFont(FONT);
        
        // Set the position of the labels
        resetLabel.setBounds(reset);
        zoomOutLabel.setBounds(zoomOut);
        zoomInLabel.setBounds(zoomIn);
        
        resetDraggableCoordinatesArray();
        // For repaint the panel
        new Timer(16,this).start();
    }

    // Paint the game
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        drawZoomButtons(g2d);
        drawLines(g2d);
        drawPoints(g2d);
        clickingInCheckBoxs();
    }

    // Draw the points
    private void drawPoints(Graphics2D g2d){
        for(int i=0; i< View.getAlgorithm().getGraphicNodeList().size(); i++){
            double grade = 360/View.getAlgorithm().getGraphicNodeList().get(i).getColors().length;
            for(int j=0; j<View.getAlgorithm().getGraphicNodeList().get(i).getColors().length; j++){
                Arc2D arc = new Arc2D.Double();
                arc.setArcByCenter((draggablePointsCoordinates[i][0]*scale), (draggablePointsCoordinates[i][1]*scale),
                    (10*scale), grade*j,
                    grade*(View.getAlgorithm().getGraphicNodeList().get(i).getColors().length-j), Arc2D.PIE);
                if(View.getAlgorithm().getGraphicNodeList().get(i).getColors()[j] == 1){
                    g2d.setColor(colorsColorStatic[1]);
                }else if(View.getAlgorithm().getGraphicNodeList().get(i).getColors()[j] == 2){
                    g2d.setColor(colorsColorStatic[2]);
                }else if(View.getAlgorithm().getGraphicNodeList().get(i).getColors()[j] == 3){
                    g2d.setColor(colorsColorStatic[3]);
                }else if(View.getAlgorithm().getGraphicNodeList().get(i).getColors()[j] == 4){
                    g2d.setColor(colorsColorStatic[4]);
                }else if(View.getAlgorithm().getGraphicNodeList().get(i).getColors()[j] == 5){
                    g2d.setColor(colorsColorStatic[5]);
                }else if(View.getAlgorithm().getGraphicNodeList().get(i).getColors()[j] == 6){
                    g2d.setColor(colorsColorStatic[6]);
                }else if(View.getAlgorithm().getGraphicNodeList().get(i).getColors()[j] == 7){
                    g2d.setColor(colorsColorStatic[7]);
                }else if(View.getAlgorithm().getGraphicNodeList().get(i).getColors()[j] == 8){
                    g2d.setColor(colorsColorStatic[8]);
                }else if(View.getAlgorithm().getGraphicNodeList().get(i).getColors()[j] == 9){
                    g2d.setColor(colorsColorStatic[9]);
                } else if(View.getAlgorithm().getGraphicNodeList().get(i).getColors()[j] == 10){
                    g2d.setColor(colorsColorStatic[10]);
                }
                g2d.fill(arc);
                g2d.setColor(Color.BLACK);
            }
        }
    }

    // Draw the lines
    private void drawLines(Graphics2D g2d){
        for(int i=0; i<View.getAlgorithm().getGraphicEdgeList().size(); i++){
            Line2D line = new Line2D.Double((draggableLinesCoordinates[i][0])*scale, (draggableLinesCoordinates[i][1])*scale,
                (draggableLinesCoordinates[i][2])*scale, (draggableLinesCoordinates[i][3])*scale);
            if(View.getAlgorithm().getGraphicEdgeList().get(i).getColor() == 1){
                g2d.setColor(colorsColor[1]);
            }else if(View.getAlgorithm().getGraphicEdgeList().get(i).getColor() == 2){
                g2d.setColor(colorsColor[2]);
            }else if(View.getAlgorithm().getGraphicEdgeList().get(i).getColor() == 3){
                g2d.setColor(colorsColor[3]);
            }else if(View.getAlgorithm().getGraphicEdgeList().get(i).getColor() == 4){                       
                g2d.setColor(colorsColor[4]);
            }else if(View.getAlgorithm().getGraphicEdgeList().get(i).getColor() == 5){
                g2d.setColor(colorsColor[5]);
            }else if(View.getAlgorithm().getGraphicEdgeList().get(i).getColor() == 6){
                g2d.setColor(colorsColor[6]);
            }else if(View.getAlgorithm().getGraphicEdgeList().get(i).getColor() == 7){
                g2d.setColor(colorsColor[7]);
            }else if(View.getAlgorithm().getGraphicEdgeList().get(i).getColor() == 8){
                g2d.setColor(colorsColor[8]);
            }else if(View.getAlgorithm().getGraphicEdgeList().get(i).getColor() == 9){
                g2d.setColor(colorsColor[9]);
            }else if(View.getAlgorithm().getGraphicEdgeList().get(i).getColor() == 10){
                g2d.setColor(colorsColor[10]);
            }
            if(View.getAlgorithm().getGraphicEdgeList().get(i).getDashed()){
                BasicStroke dash = new BasicStroke((float)(3.0f*scale), BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, (float)(10.0f*scale), new float[]{(float)(10.0f*scale)}, (int)(5*i*scale));
                g2d.setStroke(dash);
            } else {
                BasicStroke stroke = new BasicStroke((float)(3.0f*scale),1,1);
                g2d.setStroke(stroke);
            }
            g2d.draw(line);
            g2d.setColor(Color.BLACK);
        }
    }

    // Draw the rectangles
    private void drawZoomButtons(Graphics2D g2d){
        g2d.drawRect(1300,651,50,20);
        g2d.drawRect(1250, 651, 50, 20);
        g2d.drawRect(1200, 651, 50, 20);
    }

    //metodo che controlla se i checkBoxs sono stati modificati, in caso affermativo rende trasparente un colore o lo fa ritornare opaco
    private void clickingInCheckBoxs(){
        for(int i=1; i<checkBoxs.length; i++){
            if(!checkBoxs[i].isSelected()){
                //operazione bit a bit per rendere il colore trasparente
                int rgb = colorsColor[i].getRGB() & 0x00ffffff;
                Color transparentColor = new Color(rgb, true);
                colorsColor[i] = transparentColor;
            } else {
                colorsColor[i] = colorsColorStatic[i];
            }
        }
    }


    // Reset the coordinates and scale to default
    private void resetDraggableCoordinatesArray(){
        for(int i=0; i<View.getAlgorithm().getGraphicNodeList().size(); i++){
            draggablePointsCoordinates[i][0] = (int)View.getAlgorithm().getGraphicNodeList().get(i).getX();
            draggablePointsCoordinates[i][1] = (int)View.getAlgorithm().getGraphicNodeList().get(i).getY();
            oldDraggablePointsCoordinates[i][0] = (int)View.getAlgorithm().getGraphicNodeList().get(i).getX();
            oldDraggablePointsCoordinates[i][1] = (int)View.getAlgorithm().getGraphicNodeList().get(i).getY();
        }
        for(int i=0; i<View.getAlgorithm().getGraphicEdgeList().size(); i++){
            draggableLinesCoordinates[i][0] = (int)View.getAlgorithm().getGraphicEdgeList().get(i).getStartNode().getX();
            draggableLinesCoordinates[i][1] = (int)View.getAlgorithm().getGraphicEdgeList().get(i).getStartNode().getY();
            oldDraggableLinesCoordinates[i][0] = (int)View.getAlgorithm().getGraphicEdgeList().get(i).getStartNode().getX();
            oldDraggableLinesCoordinates[i][1] = (int)View.getAlgorithm().getGraphicEdgeList().get(i).getStartNode().getY(); 
            draggableLinesCoordinates[i][2] = (int)View.getAlgorithm().getGraphicEdgeList().get(i).getEndNode().getX();
            draggableLinesCoordinates[i][3] = (int)View.getAlgorithm().getGraphicEdgeList().get(i).getEndNode().getY();
            oldDraggableLinesCoordinates[i][2] = (int)View.getAlgorithm().getGraphicEdgeList().get(i).getEndNode().getX();
            oldDraggableLinesCoordinates[i][3] = (int)View.getAlgorithm().getGraphicEdgeList().get(i).getEndNode().getY();
        }
        scale = 1;
    }

    // Action Listener Functions (to repaint the panel)
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    // MouseWheelMoved methods
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            scale *= 1.1;
        } else if(scale > 0.2){
            scale /= 1.1;
        }
    }

    // MouseMotionListener methods
    @Override
    public void mouseDragged(MouseEvent e) {
        for(int i=0; i<draggablePointsCoordinates.length; i++){
            draggablePointsCoordinates[i][0] = oldDraggablePointsCoordinates[i][0] + (int)((e.getX() - mouseX)/scale);
            draggablePointsCoordinates[i][1] = oldDraggablePointsCoordinates[i][1] + (int)((e.getY() - mouseY)/scale);
        }
        for(int i=0; i<draggableLinesCoordinates.length; i++){
            draggableLinesCoordinates[i][0] = oldDraggableLinesCoordinates[i][0] + (int)((e.getX() - mouseX)/scale);
            draggableLinesCoordinates[i][1] = oldDraggableLinesCoordinates[i][1] + (int)((e.getY() - mouseY)/scale);
            draggableLinesCoordinates[i][2] = oldDraggableLinesCoordinates[i][2] + (int)((e.getX() - mouseX)/scale);
            draggableLinesCoordinates[i][3] = oldDraggableLinesCoordinates[i][3] + (int)((e.getY() - mouseY)/scale);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY(); 
    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
        if(zoomIn.contains(getMousePosition())){
            scale *= 1.1;
        } else if(zoomOut.contains(getMousePosition()) && scale > 0.2){
            scale /= 1.1;
        } else if(reset.contains(getMousePosition())){
            resetDraggableCoordinatesArray();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(int i=0; i<draggablePointsCoordinates.length; i++){
            oldDraggablePointsCoordinates[i][0] = draggablePointsCoordinates[i][0];
            oldDraggablePointsCoordinates[i][1] = draggablePointsCoordinates[i][1];
        }
        for(int i=0; i<draggableLinesCoordinates.length; i++){
            oldDraggableLinesCoordinates[i][0] = draggableLinesCoordinates[i][0];
            oldDraggableLinesCoordinates[i][1] = draggableLinesCoordinates[i][1];
            oldDraggableLinesCoordinates[i][2] = draggableLinesCoordinates[i][2];
            oldDraggableLinesCoordinates[i][3] = draggableLinesCoordinates[i][3];
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}