import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends LabeledExprBaseVisitor<Integer> {
    Map<String, Integer> memory = new HashMap<>();

    @Override
    public Integer visitAssign(LabeledExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        int value = visit(ctx.expr());
        memory.put(id, value);
        return value;
    }

    @Override
    public Integer visitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
        Integer value = visit(ctx.expr());
        System.out.println(value);
        return 0;
    }

    @Override
    public Integer visitInt(LabeledExprParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    @Override
    public Integer visitId(LabeledExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        return memory.getOrDefault(id, 0);
    }

    @Override
    public Integer visitParens(LabeledExprParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Integer visitMulDiv(LabeledExprParser.MulDivContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));

        return switch (ctx.op.getType()) {
            case LabeledExprParser.MUL -> left * right;
            case LabeledExprParser.DIV -> left / right;
            case LabeledExprParser.MOD -> left % right;
            case LabeledExprParser.POW -> (int) Math.pow(left, right);
            default -> 0;
        };
    }

    @Override
    public Integer visitAddSub(LabeledExprParser.AddSubContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));

        return switch (ctx.op.getType()) {
            case LabeledExprParser.ADD -> left + right;
            case LabeledExprParser.SUB -> left - right;
            default -> 0;
        };
    }

    @Override
    public Integer visitFuncCall(LabeledExprParser.FuncCallContext ctx) {
        String funcName = ctx.func.getText().toLowerCase();
        int value = visit(ctx.expr());

        return switch (funcName) {
            case "sin" -> (int) Math.round(Math.sin(Math.toRadians(value)));
            case "cos" -> (int) Math.round(Math.cos(Math.toRadians(value)));
            case "tan" -> (int) Math.round(Math.tan(Math.toRadians(value)));
            case "asin" -> (int) Math.round(Math.toDegrees(Math.asin(value)));
            case "acos" -> (int) Math.round(Math.toDegrees(Math.acos(value)));
            case "atan" -> (int) Math.round(Math.toDegrees(Math.atan(value)));
            default -> {
                System.err.println("Función desconocida: " + funcName);
                yield 0;
            }
        };
    }
}
