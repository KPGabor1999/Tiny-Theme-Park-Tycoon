package Idlethemeparkworld.view;

import Idlethemeparkworld.Main;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Board extends JPanel {

    private GameManager gm;
    private Park park;
    private GridButton[][] buttonGrid;
    private final int CELL_SIZE = 64;
    
    private boolean buildMode;
    private BuildType type;
    private final boolean[] canBuild;
    private final int[] pos;
    
    private Main main;
    private JButton buildButton;

    public Board(GameManager gm, JButton buildButton, Main main) {
        this.gm = gm;
        this.park = gm.getPark();
        this.buildMode = false;
        this.type = null;
        this.pos = new int[2];
        this.canBuild = new boolean[1];
        this.buildButton = buildButton;
        this.main = main;
        
        //setOpaque(false);
        setBackground(new Color(0, 140, 14, 255));
        setBorder(BorderFactory.createEmptyBorder(0, -5, 0, -5));

        resizeMap(park.getHeight(), park.getWidth());
        updateMap();
    }

    public GameManager getGameManager() {
        return gm;
    }

    public Park getPark() {
        return park;
    }

    public GridButton[][] getButtonGrid() {
        return buttonGrid;
    }

    public int getCELL_SIZE() {
        return CELL_SIZE;
    }

    public boolean isBuildMode() {
        return buildMode;
    }

    public BuildType getType() {
        return type;
    }

    public boolean[] getCanBuild() {
        return canBuild;
    }

    public int[] getPos() {
        return pos;
    }

    public Main getMain() {
        return main;
    }

    public JButton getBuildButton() {
        return buildButton;
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
        Dimension dim = new Dimension(CELL_SIZE * columns, CELL_SIZE * rows);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
    }
    
    public void refresh() {
        updateMap();
        repaint();
    }

    private void addGridButton(int x, int y){
        buttonGrid[y][x] = new GridButton();
        buttonGrid[y][x].setSize(CELL_SIZE, CELL_SIZE);
        buttonGrid[y][x].setOpaque(false);
        buttonGrid[y][x].setContentAreaFilled(false);
        buttonGrid[y][x].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //showBuildingOptions(x, y);
                JFrame parentFrame = (JFrame) getRootPane().getParent();
                BuildingOptionsDialog buildingOptions = new BuildingOptionsDialog(parentFrame, Board.this, x, y);
                buildingOptions.setLocationRelativeTo(Board.this);
                //buildingOptions.setVisible(true);
            }
        });
        /*private void showBuildingOptions(int x, int y){
            JFrame parentFrame = (JFrame) getRootPane().getParent();
            BuildingOptionsDialog buildingOptions = new BuildingOptionsDialog(parentFrame, this, x, y);
            buildingOptions.setLocationRelativeTo(this);
            buildingOptions.setVisible(true);
        }*/
        
        buttonGrid[y][x].addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                if(buildMode){
                    canBuild[0]=park.canBuild(type, x, y);
                    canBuild[0]=canBuild[0]&&gm.getFinance().canAfford(type.getBuildCost());
                    pos[0]=x;
                    pos[1]=y;
                    //System.out.println("("+x+","+y+") --- "+type.toString()+" --- "+(canBuild[0]?"Can build":"Can't build"));
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
                            park.build(type, pos[0], pos[1], false);
                            updateMap();
                            gm.getFinance().pay(type.getBuildCost());
                            main.updateInfobar();
                            //System.out.println("can build");
                        } else {
                            //System.out.println("cannot build");
                        }
                        exitBuildMode();
                    }
                }
            }
        });
        add(buttonGrid[y][x]);
    }
    
    private void updateMap() {
        /*for (int y = 0; y < park.getHeight(); y++) {
            for (int x = 0; x < park.getWidth(); x++) {
                GridButton gd = buttonGrid[y][x];
                if(park.getTile(x, y).isEmpty()){
                    gd.setColor(new Color(49, 215, 0, 255));
                    //gd.changeTile("Grass");
                } else {
                    Building b = park.getTile(x, y).getBuilding();
                    if(b instanceof Pavement){
                        gd.setColor(Color.LIGHT_GRAY);
                    }else if(b instanceof Attraction){
                        gd.setColor(Color.RED);
                    } else if(b instanceof FoodStall){
                        gd.setColor(Color.ORANGE);
                    } else if(b instanceof Entrance){
                        gd.setColor(Color.DARK_GRAY);
                        gd.setForeground(Color.WHITE);
                    } else if(b instanceof Infrastructure){
                        gd.setColor(Color.CYAN);
                    }
                    gd.changeTile(park.getTile(x, y).getBuilding().getInfo().getName());
                }
            }
        }*/
    }
    
    public void enterBuildMode(BuildType type){
        buildMode = true;
        this.type = type;
        pos[0]=0;
        pos[1]=0;
        canBuild[0]=false;
        Component[] comps = getComponents();
        for (Component component : comps) {
            component.setEnabled(false);
            //((GridButton)component).setOpaque(false);
            ((GridButton)component).darken();
            //((GridButton)component).setContentAreaFilled(false);
            //((GridButton)component).setBorderPainted(false);
        }
        buildButton.setEnabled(false);
        repaint();
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
        buildButton.setEnabled(true);
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D gr = (Graphics2D) g;
        
        ArrayList<Building> buildings = park.getBuildings();
        for (int i = 0; i < buildings.size(); i++) {
            int x = buildings.get(i).getxLocation();
            int y = buildings.get(i).getyLocation();
            int h = buildings.get(i).getInfo().getLength();
            int w = buildings.get(i).getInfo().getWidth();
            
            gr.drawImage( buildings.get(i).getInfo().getTexture().getAsset(), x * CELL_SIZE, y * CELL_SIZE, w * CELL_SIZE, h * CELL_SIZE, null);
        }
    }
    
    public void drawCenteredString(Graphics2D gr, String text, Rectangle rect, Font font) {
        FontMetrics metrics = gr.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        gr.setFont(font);
        gr.drawString(text, x, y);
    }
    
    private void drawGhost(Graphics2D gr) {
        if(canBuild[0]) {
            gr.setColor(new Color(79, 157, 0, 100));
        } else {
            gr.setColor(new Color(157, 0, 0, 100));
        }
        
        int x = pos[0];
        int y = pos[1];
        int h = type.getLength();
        int w = type.getWidth();

        gr.drawImage(type.getTexture().getAsset(), x * CELL_SIZE, y * CELL_SIZE, w * CELL_SIZE, h * CELL_SIZE, null);
        gr.fillRect(x * CELL_SIZE, y * CELL_SIZE, w * CELL_SIZE, h * CELL_SIZE);
    }
    
    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        Graphics2D gr = (Graphics2D) g;
        if(buildMode) {
            gr.setColor(new Color(0,0,0,100));
            gr.fillRect(0, 0, this.getWidth(), this.getHeight());
            drawGhost(gr);
        }
    }
}
