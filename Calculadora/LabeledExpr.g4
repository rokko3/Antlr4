grammar LabeledExpr;
prog: stat+ ;

stat: expr NEWLINE              # printExpr
    | ID '=' expr NEWLINE       # assign
    | NEWLINE                   # blank
    ;

expr: expr op=('*'|'/'|'%'|'^') expr # MulDiv
    | expr op=('+'|'-') expr        # AddSub
    | INT                           # Int
    | ID                            # Id
    | '(' expr ')'                  # Parens
    ;

MUL : '*' ;
DIV : '/' ;
MOD : '%' ;
POW : '^' ;
ADD : '+' ;
SUB : '-' ;

INT : [0-9]+ ;
ID  : [a-zA-Z]+ ;
NEWLINE:'\r'? '\n' ;
WS  : [ \t]+ -> skip ;
