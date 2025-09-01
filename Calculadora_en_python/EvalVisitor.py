from LabeledExprVisitor import LabeledExprVisitor
from LabeledExprParser import LabeledExprParser
import math

class EvalVisitor(LabeledExprVisitor):
    def __init__(self):
        self.memory = {}

    # --------- STATEMENTS ----------
    def visitAssign(self, ctx:LabeledExprParser.AssignContext):
        id = ctx.ID().getText()
        value = self.visit(ctx.expr())
        self.memory[id] = value
        return value

    def visitPrintExpr(self, ctx:LabeledExprParser.PrintExprContext):
        value = self.visit(ctx.expr())
        print(value)
        return value

    def visitBlank(self, ctx:LabeledExprParser.BlankContext):
        return None

    # --------- ATOMS ----------
    def visitInt(self, ctx:LabeledExprParser.IntContext):
        return int(ctx.INT().getText())

    def visitDouble(self, ctx:LabeledExprParser.DoubleContext):
        return float(ctx.DOUBLE().getText())

    def visitId(self, ctx:LabeledExprParser.IdContext):
        id = ctx.ID().getText()
        return self.memory.get(id, 0)

    def visitParens(self, ctx:LabeledExprParser.ParensContext):
        return self.visit(ctx.expr())

    # --------- OPERACIONES ----------
    def visitUnaryMinus(self, ctx:LabeledExprParser.UnaryMinusContext):
        return -self.visit(ctx.expr())

    def visitUnaryPlus(self, ctx:LabeledExprParser.UnaryPlusContext):
        return +self.visit(ctx.expr())

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
        elif ctx.op.type == LabeledExprParser.MOD:
            return left % right
        elif ctx.op.type == LabeledExprParser.POW:
            return left ** right
        else:
            return None

    def visitFactf(self, ctx:LabeledExprParser.FactfContext):
        value = self.visit(ctx.expr())
        return math.factorial(int(value))

    # --------- FUNCIONES ----------
    def visitFuncCall(self, ctx:LabeledExprParser.FuncCallContext):
        func = ctx.func.text.lower()
        value = self.visit(ctx.expr())
        if func == "sin":
            return math.sin(math.radians(value))
        elif func == "cos":
            return math.cos(math.radians(value))
        elif func == "tan":
            return math.tan(math.radians(value))
        else:
            print("Función desconocida:", func)
            return 0

    def visitFuncRad(self, ctx:LabeledExprParser.FuncRadContext):
        func = ctx.func.text.lower()
        value = self.visit(ctx.expr())
        if func == "sin":
            return math.sin(value)   # aquí ya se interpreta en radianes
        elif func == "cos":
            return math.cos(value)
        elif func == "tan":
            return math.tan(value)
        else:
            print("Función desconocida con rad:", func)
            return 0

    def visitSqrtf(self, ctx:LabeledExprParser.SqrtfContext):
        if self.visit(ctx.expr()) < 0:
            print("Error: Raíz cuadrada de un número negativo")
            return 0
        return math.sqrt(self.visit(ctx.expr()))

    def visitLnf(self, ctx:LabeledExprParser.LnfContext):
        return math.log(self.visit(ctx.expr()))

    def visitLogf(self, ctx:LabeledExprParser.LogfContext):
        return math.log10(self.visit(ctx.expr()))

    # --------- PROGRAMA ----------
    def visitProg(self, ctx:LabeledExprParser.ProgContext):
        result = None
        for child in ctx.getChildren():
            result = self.visit(child)
        return result
