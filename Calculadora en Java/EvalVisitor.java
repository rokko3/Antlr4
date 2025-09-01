import com.sun.jdi.IntegerType;
import java.awt.geom.FlatteningPathIterator;
import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends LabeledExprBaseVisitor<Double> {
    Map<String, Double> memory = new HashMap<>();

    @Override
    public Double visitAssign(LabeledExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Double value = visit(ctx.expr());
        memory.put(id, value);
        return value;
    }

    @Override
    public Double visitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
        Double value = visit(ctx.expr());
        System.out.println(value);
        return 0.0;
    }
    @Override
    public Double visitDouble(LabeledExprParser.DoubleContext ctx) {
        return Double.valueOf(ctx.DOUBLE().getText());
    }
    @Override
    public Double visitUnaryMinus(LabeledExprParser.UnaryMinusContext ctx) {
        return -visit(ctx.expr());
    }

    @Override
    public Double visitUnaryPlus(LabeledExprParser.UnaryPlusContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Double visitInt(LabeledExprParser.IntContext ctx) {
        return Double.valueOf(ctx.INT().getText());
    }

    @Override
    public Double visitId(LabeledExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        return memory.getOrDefault(id, 0.0);
    }

    @Override
    public Double visitParens(LabeledExprParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Double visitMulDiv(LabeledExprParser.MulDivContext ctx) {
        double left = visit(ctx.expr(0));
        double right = visit(ctx.expr(1));

        return switch (ctx.op.getType()) {
            case LabeledExprParser.MUL -> left * right;
            case LabeledExprParser.DIV -> left / right;
            case LabeledExprParser.MOD -> left % right;
            case LabeledExprParser.POW -> Math.pow(left, right);
            default -> 0.0;
        };
    }

    @Override
    public Double visitAddSub(LabeledExprParser.AddSubContext ctx) {
        double left = visit(ctx.expr(0));
        double right = visit(ctx.expr(1));

        return switch (ctx.op.getType()) {
            case LabeledExprParser.ADD -> left + right;
            case LabeledExprParser.SUB -> left - right;
            default -> 0.0;
        };
    }

    @Override
    public Double visitFuncCall(LabeledExprParser.FuncCallContext ctx) {
        String funcName = ctx.func.getText().toLowerCase();
        double value = visit(ctx.expr());

        return switch (funcName) {
            case "sin" -> Math.sin(Math.toRadians(value));
            case "cos" -> Math.cos(Math.toRadians(value));
            case "tan" -> Math.tan(Math.toRadians(value));
            default -> {
                System.err.println("Funcion desconocida: " + funcName);
                yield 0.0;
            }
        };
    }
    @Override
    public Double visitFuncRad(LabeledExprParser.FuncRadContext ctx) {
        String funcName = ctx.func.getText().toLowerCase();
        double value = visit(ctx.expr());

        return switch (funcName) {
            case "sin" -> Math.sin(value);
            case "cos" -> Math.cos(value);
            case "tan" -> Math.tan(value);
            default -> {
                System.err.println("Funcion desconocida: " + funcName);
                yield 0.0;
            }
        };
    }
    @Override
    public Double visitLnf(LabeledExprParser.LnfContext ctx) {
        double value = visit(ctx.expr());
        if (value <= 0) {
            System.err.println("Error: logaritmo no definido para " + value);
            return Double.NaN;
        }
        return Math.log(value);
    }
    @Override
    public Double visitLogf(LabeledExprParser.LogfContext ctx) {
        double value = visit(ctx.expr());
        if (value <= 0) {
            System.err.println("Error: logaritmo no definido para " + value);return Double.NaN;
            
        }
        return Math.log10(value);
    }
    @Override
    public Double visitSqrtf(LabeledExprParser.SqrtfContext ctx) {
        double value = visit(ctx.expr());
        if (value < 0) {
            System.err.println("Error: raiz cuadrada no definida para " + value);
            return Double.NaN;
        }
        return Math.sqrt(value);
    }
    @Override
    public Double visitFactf(LabeledExprParser.FactfContext ctx) {
        double value = visit(ctx.expr());
        if (value < 0) {
            System.err.println("Error: factorial no definido para " + value);
            return Double.NaN;
        }
        return factorial(value);
    }

    private double factorial(double n) {
        if (n == 0) return 1;
        return n * factorial(n - 1);
    }
}