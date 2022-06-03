/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.controller;

import introspector.model.Node;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This controller implements different ways of expanding tree views (JTrees).
 */
public class TreeMouseClickController implements MouseListener {


    /**
     * The tree view
     */
    private final JTree tree;


    /**
     * The tree view
     */
    private final JPopupMenu popupMenu;


    public TreeMouseClickController(JTree tree, JPopupMenu popupMenu) {
        this.tree = tree;
        this.popupMenu = popupMenu;
    }


    /**
     * When the mouse is right-clicked, a popup menu is shown
     * @param event the event that characterizes mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        if (SwingUtilities.isRightMouseButton(event)) {
            int row = tree.getClosestRowForLocation(event.getX(), event.getY());
            tree.setSelectionRow(row);
            TreePath path = this.tree.getSelectionPath();
            if (path != null) { // a node has been selected
                this.popupMenu.show(this.tree, event.getX(), event.getY());
            }
        }
    }

    /**
     * No action is performed when the mouse is pressed
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {}

    /**
     * No action is performed when the mouse is released
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * No action is performed when the mouse is entered
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * No action is performed when the mouse exits
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {}

}
