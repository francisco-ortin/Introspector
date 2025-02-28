package introspector.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class OptionalNode extends AbstractNode implements Node {

    public OptionalNode(String name, Object value, Class<?> type) {
        super(name,value,type);
    }

    @Override
	protected List<Node> getChildren() {
        List<Node> childs = new ArrayList<>();
        Optional<?> opt = (Optional<?>) getValue();
        childs.add(NodeFactory.createNode("IsPresent", opt.isPresent()));
        opt.map(inner -> NodeFactory.createNode("Value", inner))
           .ifPresent(inner -> childs.add(inner));
        return childs;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

}
