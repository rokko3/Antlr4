# Calculadora en python
## Requerimientos para el entorno

```bash
python -m venv venv
# Activar el entorno en linux
source venv/bin/activate
# En windows
.\venv\Scripts\Activate.ps1

pip install antlr4-tools antlr4-python3-runtime
```
## Creamos la gramatica para la calculadora

```bash
grammar LabeledExpr;
prog: stat+ ;

stat: expr NEWLINE             # printExpr
    | ID '=' expr NEWLINE      # assign
    | NEWLINE                  # blank
    ;

expr: '-' expr                      # unaryMinus
    | '+' expr                      # unaryPlus
    | expr op=('*'|'/'|'%'|'^') expr # MulDiv
    | expr op=('+'|'-') expr        # AddSub
    | INT                           # int 
    | ID                            # id 
    | DOUBLE                        # double
    | '(' expr ')'                  # parens 
    | RAD func=ID '(' expr ')'       # funcRad
    | func=ID '(' expr ')'          # funcCall
    | SQRT '(' expr ')'             # sqrtf   
    | LN '(' expr ')'               # lnf
    | LOG '(' expr ')'              # logf  
    | expr FACT                     # factf
    ;

MUL : '*' ;
DIV : '/' ;
MOD : '%' ;
POW : '^' ;
ADD : '+' ;
SUB : '-' ;
DOUBLE : [0-9]+ '.' [0-9]+ ;
INT : [0-9]+ ;
RAD : 'rad' ;
SQRT : 'sqrt' ;
LN : 'ln' ;
LOG : 'log' ;
FACT : '!' ;
ID  : [a-zA-Z]+ ;
NEWLINE:'\r'? '\n' ;
WS  : [ \t]+ -> skip ;
```

## Compilamos el antlr con python como lenguaje objetivo

```bash
antlr4 -Dlanguage=Python3 LabeledExpr.g4

```