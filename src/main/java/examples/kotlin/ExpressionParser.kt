/*
This is an example Kotlin code that defines a simple arithmetic expression parser.
The parseArithmeticExpression function takes a string representation of an arithmetic expression and returns an
Expression object representing the parsed expression.
It can parse expressions with numbers, binary operators (+, -, *, /, ^), and unary minus.
 */

package examples.kotlin;


// Public API function
fun parseArithmeticExpression(expression: String): Expression {
    return Parser(expression).parse()
}

sealed class Expression {
    data class Number(val value: Double) : Expression()
    data class ArithmeticExpression(val left: Expression, val operator: Operator, val right: Expression) : Expression()
    data class UnaryMinus(val operator: Operator, val expr: Expression) : Expression()
    
    enum class Operator(val symbol: String, val precedence: Int) {
        PLUS("+", 1),
        MINUS("-", 1),
        TIMES("*", 2),
        DIV("/", 2),
        POW("^", 3),
        UNARY_MINUS("-", 4);
        
        companion object {
            fun fromSymbol(symbol: String): Operator? = values().find { it.symbol == symbol }
        }
    }
}

class Parser(private val input: String) {
    private var pos = 0
    
    fun parse(): Expression {
        val expr = parseExpression()
        if (pos < input.length) {
            throw IllegalArgumentException("Unexpected character '${input[pos]}' at position $pos")
        }
        return expr
    }
    
    private fun parseExpression(): Expression = parseBinaryOp(0)
    
    private fun parseBinaryOp(minPrecedence: Int): Expression {
        var left = parseUnaryOp()
        
        while (true) {
            val op = peekOperator() ?: break
            if (op.precedence < minPrecedence) break
            
            // Consume the operator
            pos += op.symbol.length
            skipWhitespace()
            
            // Special handling for right-associative operators (like ^)
            val right = if (op == Expression.Operator.POW) {
                parseBinaryOp(op.precedence)
            } else {
                parseBinaryOp(op.precedence + 1)
            }
            
            left = Expression.ArithmeticExpression(left, op, right)
        }
        
        return left
    }
    
    private fun parseUnaryOp(): Expression {
        skipWhitespace()
        
        val op = peekOperator()
        if (op == Expression.Operator.MINUS) {
            pos += op.symbol.length
            skipWhitespace()
            val expr = parseUnaryOp()
            return Expression.UnaryMinus(Expression.Operator.UNARY_MINUS, expr)
        }
        
        return parsePrimary()
    }
    
    private fun parsePrimary(): Expression {
        skipWhitespace()
        
        when {
            peek() == '(' -> {
                pos++ // consume '('
                val expr = parseExpression()
                skipWhitespace()
                if (peek() != ')') {
                    throw IllegalArgumentException("Expected ')' at position $pos")
                }
                pos++ // consume ')'
                return expr
            }
            peek().isDigit() -> {
                return parseNumber()
            }
            else -> {
                throw IllegalArgumentException("Unexpected character '${peek()}' at position $pos")
            }
        }
    }
    
    private fun parseNumber(): Expression.Number {
        val start = pos
        while (pos < input.length && (input[pos].isDigit() || input[pos] == '.')) {
            pos++
        }
        val numberStr = input.substring(start, pos)
        return Expression.Number(numberStr.toDouble())
    }
    
    private fun peekOperator(): Expression.Operator? {
        skipWhitespace()
        for (op in Expression.Operator.values()) {
            if (input.startsWith(op.symbol, pos)) {
                return op
            }
        }
        return null
    }
    
    private fun peek(): Char = if (pos < input.length) input[pos] else '\u0000'
    
    private fun skipWhitespace() {
        while (pos < input.length && input[pos].isWhitespace()) {
            pos++
        }
    }
}

