# Antlr4
## Instalacion
Descargar e. antlr4 complete.jar en la pagina oficial de antlr https://www.antlr.org/download.html

Copiar el archivo antlr-4.13.2-complete.jar a la carpeta /usr/local/lib
´´´bash
cp /Carpeta_de_descargars/antlr-4.13.2-complete. jar /usr/local/lib
´´´
## Configuracion de terminal
Dependiendo del terminal para ejecutar el programa, debemos agregar el classpath en nuestra configuracion de terminal y agregar alias para ejecutar el antlr.
´´´bash
nano ~./zshrc # puede ser zsh o bash
´´´
Dentro de la configuracion de nano, escribimos las siguientes lineas al final del documento

´´´bash

# Configuración ANTLR4
export CLASSPATH=".:/usr/local/lib/antlr-4.13.2-complete.jar:$CLASSPATH"

# Alias para ANTLR4
alias antlr4='java -Xmx500M -cp "$CLASSPATH" org.antlr.v4.Tool'

# Alias para la herramienta de ejecucion
alias grun='java -Xmx500M -cp "$CLASSPATH" org.antlr.v4.gui.TestRig'

´´´bash

## Uso de calculadora

Definimos la gramatica utilizando stat, exp y prog.

´´´bash
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

´´´
Significa que la gramatica que abarca todas las reglas consta de un conjunto de stat+, es decir un conjunto de "statements", en el que definimos la asignacion de variables, salto de linea y impresion de expresion. Cada expresion puede ser un operador mateamtico, un numero o una variable.
---
En la siguiente estructura se define el lexico a usar con sus respectivos tokens:
´´´bash
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

´´´