package Idlethemeparkworld.view;

import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.LockedTile;
import Idlethemeparkworld.view.popups.Confirm;
import Idlethemeparkworld.view.popups.Notification;
import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.administration.Finance;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BuildingOptionsDialog extends JDialog {

    private Board board;
    private Building currentBuilding;

    private static int instanceCount;

    public BuildingOptionsDialog(Frame owner, Board board, int x, int y) {
        super(owner, "Building options");

        if (instanceCount == 0) {
            instanceCount++;
            this.board = board;
            this.currentBuilding = board.getGameManager().getPark().getTile(x, y).getBuilding();
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    instanceCount--;
                    BuildingOptionsDialog.this.dispose();
                }
            });
            this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
            this.setResizable(false);

            JLabel nameLabel;
            if (currentBuilding.getMaxLevel() != 0) {
                nameLabel = new JLabel(currentBuilding.getInfo().getName() + " (Level " + currentBuilding.getCurrentLevel() + ")");
            } else {
                nameLabel = new JLabel(currentBuilding.getInfo().getName());
            }
            nameLabel.setAlignmentX(CENTER_ALIGNMENT);
            this.getContentPane().add(nameLabel);

            if (!currentBuilding.getInfo().getDescription().isEmpty()) {
                JLabel descriptionLabel = new JLabel(currentBuilding.getInfo().getDescription());
                descriptionLabel.setAlignmentX(CENTER_ALIGNMENT);
                this.getContentPane().add(descriptionLabel);
            }

            ArrayList<Pair<String, String>> info = currentBuilding.getAllData();
            JPanel statsPanel = new JPanel(new GridLayout(info.size(), 2));
            for (Pair<String, String> p : info) {
                statsPanel.add(new JLabel(p.getKey()));
                statsPanel.add(new JLabel(p.getValue()));
            }
            this.getContentPane().add(statsPanel);

            if (currentBuilding.getMaxLevel() != 0 && !board.getGameManager().gameFroze()) {
                JButton upgradeButton = new JButton("Upgrade: costs " + currentBuilding.getUpgradeCost() + "$");
                upgradeButton.setAlignmentX(CENTER_ALIGNMENT);
                upgradeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BuildingOptionsDialog.this.upgradeBuilding();
                    }
                });
                upgradeButton.setEnabled(currentBuilding.canUpgrade());
                this.getContentPane().add(upgradeButton);
            }

            if (!(currentBuilding instanceof Entrance) && !(currentBuilding instanceof LockedTile) && !board.getGameManager().gameFroze()) {
                JButton demolishButton = new JButton("Demolish: returns " + currentBuilding.getValue() / 2 + "$");
                demolishButton.setAlignmentX(CENTER_ALIGNMENT);
                demolishButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BuildingOptionsDialog.this.demolishBuilding();
                    }
                });
                this.getContentPane().add(demolishButton);
            } else if (currentBuilding instanceof LockedTile && !board.getGameManager().gameFroze()) {
                JButton unlockButton = new JButton("Unlock for " + ((LockedTile) currentBuilding).getUnlockCost() + "$");
                unlockButton.setAlignmentX(CENTER_ALIGNMENT);
                unlockButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BuildingOptionsDialog.this.instanceCount--;
                        BuildingOptionsDialog.this.dispose();
                        BuildingOptionsDialog.this.board.getGameManager().getFinance().pay(((LockedTile) currentBuilding).getUnlockCost(), Finance.FinanceType.BUILDING);
                        BuildingOptionsDialog.this.board.getGameManager().getPark().demolish(currentBuilding.getX(), currentBuilding.getY());
                        BuildingOptionsDialog.this.board.refresh();
                        BuildingOptionsDialog.this.board.drawParkRender();
                    }
                });
                this.getContentPane().add(unlockButton);
            }
            this.pack();
            this.setVisible(true);
        }
    }

    private void upgradeBuilding() {
        int funds = board.getGameManager().getFinance().getFunds();
        int upgradeCost = currentBuilding.getUpgradeCost();
        if (funds <= upgradeCost) {
            new Notification(this.getOwner(), "Problem", "Insufficient funds for upgrade.");
        } else {
            board.getGameManager().getFinance().pay(upgradeCost, Finance.FinanceType.UPGRADE);
            currentBuilding.upgrade();
            instanceCount--;
            this.dispose();
            new Notification(this.getOwner(), "Success", "Building successfully upgraded.");
        }
    }

    private void demolishBuilding() {
        new Confirm(this.getOwner(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuildingOptionsDialog.this.instanceCount--;
                BuildingOptionsDialog.this.dispose();
                BuildingOptionsDialog.this.board.getGameManager().getFinance().earn(currentBuilding.getValue() / 2, Finance.FinanceType.DEMOLISH_BONUS);
                BuildingOptionsDialog.this.board.getGameManager().getPark().demolish(currentBuilding.getX(), currentBuilding.getY());
                BuildingOptionsDialog.this.board.drawParkRender();
                BuildingOptionsDialog.this.board.refresh();
            }
        });
    }
}
