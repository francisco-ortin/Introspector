/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.view;


import java.io.IOException;
import java.io.PrintWriter;

/**
 * A serializer to show a tree in the console
 */
public class ConsoleTreeSerializer extends TxtTreeSerializer {

    ConsoleTreeSerializer()  {
        super(new PrintWriter(System.out));
    }

    /**
     * @param allInfo if all the info in the nodes must be displayed (i.e., toString() method of objects wrapped by nodes)
     */
    public ConsoleTreeSerializer(boolean allInfo)  {
        super(new PrintWriter(System.out), allInfo);
    }

    /**
     * @see TreeSerializer#endTraverse
     */
    @Override
    public void endTraverse() throws IOException {
        this.write("\n");
    }


}
