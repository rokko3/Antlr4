from LabeledExprVisitor import LabeledExprVisitor
from LabeledExprParser import LabeledExprParser
import math

class EvalVisitor(LabeledExprVisitor):
    def __init__(self):
        self.memory = {}

    def visitAssign(self, ctx:LabeledExprParser.AssignContext):
        id = ctx.ID().getText()
        value = self.visit(ctx.expr())
        self.memory[id] = value
        return value

    def visitPrintExpr(self, ctx:LabeledExprParser.PrintExprContext):
        value = self.visit(ctx.expr())
        print(value)
        return value

    def visitInt(self, ctx:LabeledExprParser.IntContext):
        return int(ctx.INT().getText())

    def visitId(self, ctx:LabeledExprParser.IdContext):
        id = ctx.ID().getText()
        return self.memory.get(id, 0)

    def visitAddSub(self, ctx:LabeledExprParser.AddSubContext):
        left = self.visit(ctx.expr(0))
        right = self.visit(ctx.expr(1))
        if ctx.op.type == LabeledExprParser.ADD:
            return left + right
        return left - right

    def visitMulDiv(self, ctx:LabeledExprParser.MulDivContext):
        left = self.visit(ctx.expr(0))
        right = self.visit(ctx.expr(1))
        if ctx.op.type == LabeledExprParser.MUL:
            return left * right
        elif ctx.op.type == LabeledExprParser.DIV:
            return left / right
        return left % right

    def visitFuncCall(self, ctx:LabeledExprParser.FuncCallContext):
        func = ctx.func.text.lower()
        value = self.visit(ctx.expr())
        if func == "sin":
            return math.sin(math.radians(value))
        elif func == "cos":
            return math.cos(math.radians(value))
        elif func == "tan":
            return math.tan(math.radians(value))
        elif func == "log":
            return math.log10(value)
        elif func == "ln":
            return math.log(value)
        elif func == "sqrt":
            return math.sqrt(value)
        else:
            print("Función desconocida:", func)
            return 0
