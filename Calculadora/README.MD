# Ejecucion de Calculadora

## Compilamos la gramatica g4 
```bash
antlr4 -visitor LabeledExpr.g4
```
La flag -visitor, permite que antlr, ademas de compilar el parser y el scanner, permite la creacion de las interfaces para utilizar con EvalVisitor
## Compilamos los archivos .java
```bash
javac -cp .:antlr-4.13.1-complete.jar *.java
```
## Ejecutamos el main class y agregamos el input

```bash
java -cp .:antlr-4.13.1-complete.jar Calc input.txt
```
---
# Caso de uso
Si ingresamos los siguientes statements para verificar que EvalVisitor este funcionando:
```txt
a = 10
b = a * 2 + 5
b
10 + 2 * 3
a + 5 * (2 + 3)
```