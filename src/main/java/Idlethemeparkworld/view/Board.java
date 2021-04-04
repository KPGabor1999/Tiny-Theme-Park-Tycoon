package Idlethemeparkworld.view;

import Idlethemeparkworld.Main;
import Idlethemeparkworld.misc.Assets;
import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.agent.Janitor;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel {

    private final GameManager gm;
    private Park park;
    private final int CELL_SIZE = 64;
    
    private boolean buildMode;
    private BuildType type;
    private boolean dragged;
    private final boolean[] canBuild;
    private final int[] pos;
    
    private final Main main;
    private final JButton buildButton;

    public Board(GameManager gm, JButton buildButton, Main main) {
        this.gm = gm;
        gm.setBoard(this);
        this.park = gm.getPark();
        this.buildMode = false;
        this.type = null;
        this.pos = new int[2];
        this.canBuild = new boolean[1];
        this.buildButton = buildButton;
        this.main = main;
        this.dragged = false;
        Timer timer = new Timer(18, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               Board.this.repaint();
            }    
        });
        timer.start();
        
        MouseAdapter ma = new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e) {
                if(buildMode){
                    updateGhost(e);
                }
            }
            
            @Override
            public void mouseDragged(MouseEvent e){
                if(buildMode){
                    updateGhost(e);
                    if(type == BuildType.PAVEMENT){
                        dragged = true;
                        if(canBuild[0]){
                            park.build(type, pos[0], pos[1], false);
                            gm.getFinance().pay(type.getBuildCost());
                            main.updateInfobar();
                        } 
                    }
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e){
                if(buildMode){
                    dragged = false;
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e){
                if(buildMode && dragged){
                    if(type == BuildType.PAVEMENT){
                        Board.this.exitBuildMode();
                    }
                }
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                Position mPos = retrieveCoords(e);
                if(buildMode){
                    if(canBuild[0]){
                        park.build(type, pos[0], pos[1], false);
                        gm.getFinance().pay(type.getBuildCost());
                        main.updateInfobar();
                    }
                    Board.this.exitBuildMode();
                } else {
                    if(park.getTile(mPos.x, mPos.y).getBuilding() != null){
                        JFrame parentFrame = (JFrame) getRootPane().getParent();
                        BuildingOptionsDialog buildingOptions = new BuildingOptionsDialog(parentFrame, Board.this, mPos.x, mPos.y);
                        buildingOptions.setLocationRelativeTo(Board.this);
                    }
                }
            }
        };
        
        this.addMouseListener(ma);
        this.addMouseMotionListener(ma);
        
        setBackground(new Color(0, 140, 14, 255));
        setBorder(BorderFactory.createEmptyBorder(0, -5, 0, -5));

        resizeMap(park.getHeight(), park.getWidth());
    }
    
    private void updateGhost(MouseEvent e){
        Position mPos = retrieveCoords(e);
        canBuild[0]=park.canBuild(type, mPos.x, mPos.y);
        canBuild[0]=canBuild[0]&&gm.getFinance().canAfford(type.getBuildCost());
        pos[0]=mPos.x;
        pos[1]=mPos.y;
        repaint();
    }
    
    private Position retrieveCoords(MouseEvent e){
        Dimension sizes = Board.this.getSize();
        int x = Math.floorDiv(e.getX(), sizes.width/park.getWidth());
        int y = Math.floorDiv(e.getY(), sizes.height/park.getHeight());
        return new Position(x,y);
    }

    public GameManager getGameManager() {
        return gm;
    }

    public Park getPark() {
        return park;
    }

    public int getCellSize() {
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

    public Main getMain() {
        return main;
    }

    public JButton getBuildButton() {
        return buildButton;
    }
    
    private void resizeMap(int rows, int columns){
        removeAll();
        Dimension dim = new Dimension(CELL_SIZE * columns, CELL_SIZE * rows);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
    }
    
    public void refresh() {
        repaint();
    }
    
    public void enterBuildMode(BuildType type){
        buildMode = true;
        this.type = type;
        pos[0]=0;
        pos[1]=0;
        canBuild[0]=false;
        buildButton.setEnabled(false);
        repaint();
    }
    
    public void exitBuildMode(){
        buildMode = false;
        this.type = null;
        buildButton.setEnabled(true);
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D gr = (Graphics2D) g;
        
        ArrayList<Building> buildings = park.getBuildings();
        for (int i = 0; i < buildings.size(); i++) {
            int x = buildings.get(i).getX();
            int y = buildings.get(i).getY();
            int h = buildings.get(i).getInfo().getLength();
            int w = buildings.get(i).getInfo().getWidth();
            
            gr.drawImage( buildings.get(i).getInfo().getTexture().getAsset(), x * CELL_SIZE, y * CELL_SIZE, w * CELL_SIZE, h * CELL_SIZE, null);
            if(buildings.get(i).getStatus() == BuildingStatus.FLOATING){
                gr.drawImage(Assets.Texture.NOPATH.getAsset(), x*CELL_SIZE+w*CELL_SIZE/2-15, y*CELL_SIZE+h*CELL_SIZE/2-15, 30, 30, null);
            }
        }
        
        ArrayList<Visitor> visitors = gm.getAgentManager().getVisitors();
        for (int i = 0; i < visitors.size(); i++) {
            Position position = visitors.get(i).calculateExactPosition(CELL_SIZE);
            
            gr.setColor(visitors.get(i).getColor());
            gr.drawRect(position.x, position.y, 2, 3);
        }
        
        ArrayList<Janitor> janitors = gm.getAgentManager().getJanitors();
        for (int i = 0; i < janitors.size(); i++) {
            Position position = janitors.get(i).calculateExactPosition(CELL_SIZE);
            
            gr.setColor(new Color(30,30,255,255));
            gr.drawOval(position.x, position.y, 6, 6);
        }
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
