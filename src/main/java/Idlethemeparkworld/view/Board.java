package Idlethemeparkworld.view;

import Idlethemeparkworld.Main;
import Idlethemeparkworld.misc.Assets;
import Idlethemeparkworld.misc.utils.Position;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.agent.Janitor;
import Idlethemeparkworld.model.agent.Maintainer;
import Idlethemeparkworld.model.agent.Visitor;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.BuildingStatus;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Board extends JPanel implements MouseWheelListener {

    private final GameManager gm;
    private Park park;
    private final int CELL_SIZE = 64;
    private double scale;
    private int scaledCellSize;
    private Dimension scaledAgentSize;

    private boolean buildMode;
    private BuildType type;
    private boolean dragged;
    private final boolean[] canBuild;
    private final int[] pos;
    
    private BufferedImage parkRender;
    private JPanel gameArea;

    public Board(GameManager gm, Main main, JPanel gameArea) {
        this.gm = gm;
        
        this.park = gm.getPark();
        this.buildMode = false;
        this.type = null;
        this.pos = new int[2];
        this.canBuild = new boolean[1];
        this.dragged = false;
        this.gameArea = gameArea;
        Timer timer = new Timer(19, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Board.this.repaint();
            }
        });
        timer.start();

        MouseAdapter ma = new MouseAdapter() {
            private Point origin;

            @Override
            public void mouseMoved(MouseEvent e) {
                if (buildMode) {
                    updateGhost(e);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                
                if (buildMode) {
                    updateGhost(e);
                    if(!(SwingUtilities.isRightMouseButton(e))){
                        if (type == BuildType.PAVEMENT) {
                            dragged = true;
                            if (canBuild[0]) {
                                park.build(type, pos[0], pos[1], false);
                                gm.getFinance().pay(type.getBuildCost());
                                main.getInfoBar().updateInfobar();
                                drawParkRender();
                            }
                        }
                    }
                } else if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, gameArea);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        gameArea.scrollRectToVisible(view);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
                if (buildMode) {
                    dragged = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (buildMode && dragged) {
                    if (type == BuildType.PAVEMENT) {
                        Board.this.exitBuildMode();
                    }
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Position mPos = retrieveCoords(e);
                if (buildMode) {
                    if (!(SwingUtilities.isRightMouseButton(e)) && canBuild[0]) {
                        park.build(type, pos[0], pos[1], false);
                        gm.getFinance().pay(type.getBuildCost());
                        main.getInfoBar().updateInfobar();
                        drawParkRender();
                    }
                    Board.this.exitBuildMode();
                } else {
                    if (park.getTile(mPos.x, mPos.y).getBuilding() != null) {
                        JFrame parentFrame = (JFrame) getRootPane().getParent();
                        BuildingOptionsDialog buildingOptions = new BuildingOptionsDialog(parentFrame, Board.this, mPos.x, mPos.y);
                        buildingOptions.setLocationRelativeTo(Board.this);
                    }
                }
            }
        };

        this.addMouseListener(ma);
        this.addMouseMotionListener(ma);
        this.addMouseWheelListener(this);

        setBackground(new Color(0, 140, 14, 255));
        setBorder(BorderFactory.createEmptyBorder(0, -5, 0, -5));

        setScale(1);
    }
    
    public void mouseWheelMoved(MouseWheelEvent e) {
       int notches = e.getWheelRotation();
       if (notches < 0) {
           if(scale < 2){
               setScale(scale+0.05);
           }
       } else {
           if(0.5 < scale){
               setScale(scale-0.05);
           }
       }
    }
    
    private void setScale(double scale){ 
        JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, gameArea);
        Rectangle view = null;
        if(viewPort != null){
            view = viewPort.getViewRect();
            view.x = (int)Math.floor(view.x*(scale/this.scale));
            view.y = (int)Math.floor(view.y*(scale/this.scale));
        }
        this.scale = scale;
        this.scaledCellSize = (int)Math.floor(this.CELL_SIZE * this.scale);
        resizeMap(park.getHeight(), park.getWidth());
        this.parkRender = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        scaledAgentSize = new Dimension((int)Math.floor(6*scale), (int)Math.floor(18*scale));
        Dimension d = getPreferredSize();
        d.height *= 1.5;
        d.width *= 1.5;
        gameArea.setPreferredSize(d);
        drawParkRender();
        if(viewPort != null){
            gameArea.scrollRectToVisible(view);
        }
        
    }
    
    private boolean legalPos(Position pos){
        return 0 <= pos.x && pos.x < park.getWidth() && 0 <= pos.y && pos.y < park.getHeight();
    }

    private void updateGhost(MouseEvent e) {
        Position mPos = retrieveCoords(e);
        if(legalPos(mPos)){
            canBuild[0] = park.canBuild(type, mPos.x, mPos.y);
            canBuild[0] = canBuild[0] && gm.getFinance().canAfford(type.getBuildCost());
            pos[0] = mPos.x;
            pos[1] = mPos.y;
            repaint();
        }
    }

    private Position retrieveCoords(MouseEvent e) {
        Dimension sizes = Board.this.getSize();
        int x = Math.floorDiv(e.getX(), sizes.width / park.getWidth());
        int y = Math.floorDiv(e.getY(), sizes.height / park.getHeight());
        
        return new Position(x, y);
    }

    public GameManager getGameManager() {
        return gm;
    }

    private void resizeMap(int rows, int columns) {
        removeAll();
        Dimension dim = new Dimension(scaledCellSize * columns, scaledCellSize * rows);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
    }

    public void refresh() {
        repaint();
    }

    public void enterBuildMode(BuildType type) {
        if (!buildMode) {
            buildMode = true;
            this.type = type;
            pos[0] = 0;
            pos[1] = 0;
            canBuild[0] = false;
            repaint();
        }
    }

    public void exitBuildMode() {
        buildMode = false;
        this.type = null;
        repaint();
    }
    
    public void drawParkRender(){
        Graphics2D gr = parkRender.createGraphics();
        
        for (int i = 0; i < park.getHeight(); i++) {
            for (int j = 0; j < park.getWidth(); j++) {
                if(park.getTile(i, j).getBuilding() == null){
                    gr.drawImage(Assets.Texture.GRASS.getAsset(), i * scaledCellSize, j * scaledCellSize, scaledCellSize, scaledCellSize, null);
                }
            }
        }
        
        ArrayList<Building> buildings = park.getBuildings();
        for (int i = 0; i < buildings.size(); i++) {
            int x = buildings.get(i).getX();
            int y = buildings.get(i).getY();
            int h = buildings.get(i).getInfo().getLength();
            int w = buildings.get(i).getInfo().getWidth();

            gr.drawImage(buildings.get(i).getInfo().getTexture().getAsset(), x * scaledCellSize, y * scaledCellSize, w * scaledCellSize, h * scaledCellSize, null);
            if (buildings.get(i).getStatus() == BuildingStatus.FLOATING) {
                gr.drawImage(Assets.Texture.NOPATH.getAsset(), x * scaledCellSize + w * scaledCellSize / 2 - 15, y * scaledCellSize + h * scaledCellSize / 2 - 15, 30, 30, null);
            }
            if (buildings.get(i).getStatus() == BuildingStatus.DECAYED) {
                gr.setColor(new Color(0, 0, 0, 100));
                gr.fillRect(x * scaledCellSize, y * scaledCellSize, w * scaledCellSize, h * scaledCellSize);
                gr.setColor(Color.WHITE);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D gr = (Graphics2D) g;
        
        gr.drawImage(parkRender, 0, 0, null);

        ArrayList<Visitor> visitors = gm.getAgentManager().getVisitors();
        for (int i = 0; i < visitors.size(); i++) {
            if (visitors.get(i).shouldRender()) {
                Position position = visitors.get(i).calculateExactPosition(scaledCellSize);
                
                gr.drawImage(Assets.Texture.valueOf("NPC"+visitors.get(i).getSkinID()).getAsset(), (int)Math.floor((position.x-3)*scale), (int)Math.floor((position.y-18)*scale), scaledAgentSize.width, scaledAgentSize.height, null);
            }
        }
        
        Visitor activeVisitor = gm.getAgentManager().getActiveVisitor();
        if(activeVisitor != null){
            Position position = activeVisitor.calculateExactPosition(scaledCellSize);
                
            gr.setColor(new Color(0, 0, 100, 150));
            gr.fillOval((int)Math.floor((position.x-12)*scale), (int)Math.floor((position.y-21)*scale), (int)Math.floor(24*scale), (int)Math.floor(24*scale));
            gr.drawImage(Assets.Texture.valueOf("NPC"+activeVisitor.getSkinID()).getAsset(), (int)Math.floor((position.x-3)*scale), (int)Math.floor((position.y-18)*scale), scaledAgentSize.width, scaledAgentSize.height, null);
        }

        ArrayList<Janitor> janitors = gm.getAgentManager().getJanitors();
        for (int i = 0; i < janitors.size(); i++) {
            Position position = janitors.get(i).calculateExactPosition(scaledCellSize);

            gr.drawImage(Assets.Texture.JANITOR.getAsset(), (int)Math.floor((position.x-3)*scale), (int)Math.floor((position.y-18)*scale), scaledAgentSize.width, scaledAgentSize.height, null);
        }

        ArrayList<Maintainer> maintainers = gm.getAgentManager().getMaintainers();
        for (int i = 0; i < maintainers.size(); i++) {
            Position position = maintainers.get(i).calculateExactPosition(scaledCellSize);

            gr.drawImage(Assets.Texture.MAINTAINER.getAsset(), (int)Math.floor((position.x-3)*scale), (int)Math.floor((position.y-18)*scale), scaledAgentSize.width, scaledAgentSize.height, null);
        }
    }

    private void drawGhost(Graphics2D gr) {
        if (canBuild[0]) {
            gr.setColor(new Color(79, 157, 0, 100));
        } else {
            gr.setColor(new Color(157, 0, 0, 100));
        }

        int x = pos[0];
        int y = pos[1];
        int h = type.getLength();
        int w = type.getWidth();

        gr.drawImage(type.getTexture().getAsset(), x * scaledCellSize, y * scaledCellSize, w * scaledCellSize, h * scaledCellSize, null);
        gr.fillRect(x * scaledCellSize, y * scaledCellSize, w * scaledCellSize, h * scaledCellSize);
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        Graphics2D gr = (Graphics2D) g;
        if (buildMode) {
            gr.setColor(new Color(0, 0, 0, 100));
            gr.fillRect(0, 0, this.getWidth(), this.getHeight());
            drawGhost(gr);
        }
    }
}
