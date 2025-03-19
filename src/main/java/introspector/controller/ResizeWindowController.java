/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.controller;

import introspector.view.IntrospectorView;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * This controller implements the logic to resize the window and print the new size.
 * It changes the horizontal split panes to be relative to the number of trees.
 */
public class ResizeWindowController extends ComponentAdapter {

    /**
     * The view that triggers the resize event.
     */
    private final IntrospectorView introspectorView;

    public ResizeWindowController(IntrospectorView introspectorView) {
        this.introspectorView = introspectorView;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        // This method is triggered when the window is resized
        this.introspectorView.updateVerticalSplitPanes();
    }

}
