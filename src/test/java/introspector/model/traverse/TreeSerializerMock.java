/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model.traverse;

import introspector.model.Node;
import introspector.model.traverse.TreeSerializer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock of the TreeSerializer interface
 */
public class TreeSerializerMock implements TreeSerializer {

    record TraverseDTO(Node node, int depth, boolean hasBeenVisited) {}

    int beginTraverseCount = 0, endTraverseCount = 0;
    List<TraverseDTO> beforeTraversingList = new ArrayList<>(), traversingList = new ArrayList<>(),
                    afterTraversingList = new ArrayList<>();

    @Override
    public void beginTraverse() {
        this.beginTraverseCount++;
    }
    
    @Override
    public void endTraverse() {
        this.endTraverseCount++;
    }

    @Override
    public void beforeTraversing(Node node, int depth, boolean hasBeenVisited) {
        this.beforeTraversingList.add(new TraverseDTO(node, depth, hasBeenVisited));
    }

    @Override
    public void traversing(Node node, int depth, boolean hasBeenVisited) {
        this.traversingList.add(new TraverseDTO(node, depth, hasBeenVisited));
    }

    @Override
    public void afterTraversing(Node node, int depth, boolean hasBeenVisited) {
        this.afterTraversingList.add(new TraverseDTO(node, depth, hasBeenVisited));
    }

}
