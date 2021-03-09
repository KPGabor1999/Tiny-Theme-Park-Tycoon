package Idlethemeparkworld.view;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Board extends JPanel {

    private Park park;
    private GridButton[][] buttonGrid;
    private final int CELL_SIZE = 32;
    
    private boolean buildMode;
    private BuildType type;
    private boolean[] canBuild;
    private int[] pos;

    public Board(Park park) {
        this.park = park;
        this.buildMode = false;
        this.type = null;
        this.pos = new int[2];
        this.canBuild = new boolean[1];
        
        setOpaque(false);
        setBackground(Color.BLACK);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        
        this.park.build(BuildType.CAROUSEL, 0, 0);
        this.park.build(BuildType.FERRISWHEEL, 0, 1);
        this.park.build(BuildType.HAUNTEDMANSION, 0, 2);
        this.park.build(BuildType.ROLLERCOASTER, 0, 3);
        this.park.build(BuildType.SWINGINGSHIP, 0, 4);
        
        this.park.build(BuildType.BURGERJOINT, 1, 0);
        this.park.build(BuildType.HOTDOGSTAND, 1, 1);
        this.park.build(BuildType.ICECREAMPARLOR, 1, 2);
        
        this.park.build(BuildType.PAVEMENT, 2, 0);
        this.park.build(BuildType.TOILET, 2, 1);
        this.park.build(BuildType.TRASHCAN, 2, 2);
        
        resizeMap(park.getHeight(), park.getWidth());
        updateMap();
    }
    
    private void resizeMap(int rows, int columns){
        removeAll();
        setLayout(new GridLayout(rows, columns));
        buttonGrid = new GridButton[rows][columns];
        for(int y=0; y<buttonGrid.length; y++){
            for(int x=0; x<buttonGrid[0].length; x++){
                addGridButton(x, y);
            }
        }
    }

    private void addGridButton(int x, int y){
        buttonGrid[y][x] = new GridButton();
        buttonGrid[y][x].setSize(CELL_SIZE, CELL_SIZE);
        buttonGrid[y][x].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                showBuildingOptions(x, y);
            }
        });
        buttonGrid[y][x].addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                if(buildMode){
                    canBuild[0]=park.canBuild(type, x, y);
                    pos[0]=x;
                    pos[1]=y;
                    System.out.println("("+x+","+y+") --- "+type.toString()+" --- "+(canBuild[0]?"Can build":"Can't build"));
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)){
                    exitBuildMode();
                } else {
                    if(buildMode){
                        if(canBuild[0]){
                            park.build(type, pos[0], pos[1]);
                            updateMap();
                            System.out.println("can build");
                        } else {
                            System.out.println("cannot build");
                        }
                        exitBuildMode();
                    }
                }
            }
        });
        add(buttonGrid[y][x]);
    }
    
    private void showBuildingOptions(int x, int y){
        Building currentBuilding = park.getTile(x, y).getBuilding();
        JFrame parentFrame = (JFrame) getRootPane().getParent();
        BuildingOptionsDialog buildingOptions = new BuildingOptionsDialog(parentFrame, currentBuilding);
        buildingOptions.setLocationRelativeTo(this);
        buildingOptions.setVisible(true);
    }
    
    private void updateMap() {
        for (int y = 0; y < park.getHeight(); y++) {
            for (int x = 0; x < park.getWidth(); x++) {
                GridButton gd = buttonGrid[y][x];
                if(park.getTile(x, y).isEmpty()){
                    gd.setColor(new Color(49, 215, 0, 255));
                    gd.changeTile("Grass");
                } else {
                    Building b = park.getTile(x, y).getBuilding();
                    if(b instanceof Pavement){
                        gd.setColor(Color.LIGHT_GRAY);
                    }else if(b instanceof Attraction){
                        gd.setColor(Color.RED);
                    } else if(b instanceof FoodStall){
                        gd.setColor(Color.ORANGE);
                    } else if(b instanceof Infrastructure){
                        gd.setColor(Color.CYAN);
                    }
                    gd.changeTile(park.getTile(x, y).getBuilding().getName());
                }
            }
        }
    }
    
    public void enterBuildMode(BuildType type){
        buildMode = true;
        this.type = type;
        pos[0]=0;
        pos[1]=0;    
        Component[] comps = getComponents();
        for (Component component : comps) {
            component.setEnabled(false);
            //((GridButton)component).setOpaque(false);
            ((GridButton)component).darken();
            //((GridButton)component).setContentAreaFilled(false);
            //((GridButton)component).setBorderPainted(false);
            repaint();
        }
    }
    
    public void exitBuildMode(){
        buildMode = false;
        this.type = null;
        Component[] comps = getComponents();
        for (Component component : comps) {
            component.setEnabled(true);
            //((GridButton)component).setOpaque(true);
            ((GridButton)component).resetColor();
            //((GridButton)component).setContentAreaFilled(true);
            //((GridButton)component).setBorderPainted(true);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
   public void drawCenteredString(Graphics2D gr, String text, Rectangle rect, Font font) {
       FontMetrics metrics = gr.getFontMetrics(font);
       int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
       int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
       gr.setFont(font);
       gr.drawString(text, x, y);
   }
    
    private void drawGhost(Graphics2D gr){
        if(canBuild[0]){
            gr.setColor(new Color(79, 157, 0, 200));
        } else {
            gr.setColor(new Color(157, 0, 0, 200));
        }
        
        int sizeX = this.getWidth() / park.getWidth();
        int sizeY = this.getHeight() / park.getHeight();
        gr.fillRect(sizeX*pos[0], sizeY*pos[1], sizeX, sizeY);
        
        gr.setColor(Color.WHITE);
        Font font = gr.getFont().deriveFont( 10f );
        Rectangle rec = new Rectangle(sizeX*pos[0], sizeY*pos[1], sizeX, sizeY);
        drawCenteredString(gr, type.toString(), rec, font);
    }
    
    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        Graphics2D gr = (Graphics2D) g;
        if(buildMode){
            gr.setColor(new Color(0,0,0,100));
            gr.fillRect(0, 0, this.getWidth(), this.getHeight());
            drawGhost(gr);
        }
    }
}
