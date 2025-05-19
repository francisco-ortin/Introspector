/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples.kotlin;


import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;


/**
 * Shows how to use Introspector to visualize an object graph structure from a Kotlin program.
 */
public class KotlinASTExample {
    public static void main(String[] args) {
        Expression ast = ExpressionParserKt.parseArithmeticExpression("1.1 + 2.2 * -3");
        IntrospectorModel model = new IntrospectorModel("Arithmetic expression", ast);
        new IntrospectorView("AST", model);
    }
}