package notch.net.optifine.expr;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.optifine.Config;
import net.optifine.expr.ConstantFloat;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.FunctionBool;
import net.optifine.expr.FunctionFloat;
import net.optifine.expr.FunctionFloatArray;
import net.optifine.expr.FunctionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.IExpressionResolver;
import net.optifine.expr.ParseException;
import net.optifine.expr.Token;
import net.optifine.expr.TokenParser;
import net.optifine.expr.TokenType;

public class ExpressionParser {
  private IExpressionResolver expressionResolver;
  
  public ExpressionParser(IExpressionResolver expressionResolver) {
    this.expressionResolver = expressionResolver;
  }
  
  public IExpressionFloat parseFloat(String str) throws ParseException {
    IExpression expr = parse(str);
    if (!(expr instanceof IExpressionFloat))
      throw new ParseException("Not a float expression: " + String.valueOf(expr.getExpressionType())); 
    return (IExpressionFloat)expr;
  }
  
  public IExpressionBool parseBool(String str) throws ParseException {
    IExpression expr = parse(str);
    if (!(expr instanceof IExpressionBool))
      throw new ParseException("Not a boolean expression: " + String.valueOf(expr.getExpressionType())); 
    return (IExpressionBool)expr;
  }
  
  public IExpression parse(String str) throws ParseException {
    try {
      Token[] tokens = TokenParser.parse(str);
      if (tokens == null)
        return null; 
      Deque<Token> deque = new ArrayDeque<>(Arrays.asList(tokens));
      return parseInfix(deque);
    } catch (IOException e) {
      throw new ParseException(e.getMessage(), e);
    } 
  }
  
  private IExpression parseInfix(Deque<Token> deque) throws ParseException {
    if (deque.isEmpty())
      return null; 
    List<IExpression> listExpr = new LinkedList<>();
    List<Token> listOperTokens = new LinkedList<>();
    IExpression expr = parseExpression(deque);
    checkNull(expr, "Missing expression");
    listExpr.add(expr);
    while (true) {
      Token tokenOper = deque.poll();
      if (tokenOper == null)
        break; 
      if (tokenOper.getType() != TokenType.OPERATOR)
        throw new ParseException("Invalid operator: " + String.valueOf(tokenOper)); 
      IExpression expr2 = parseExpression(deque);
      checkNull(expr2, "Missing expression");
      listOperTokens.add(tokenOper);
      listExpr.add(expr2);
    } 
    return makeInfix(listExpr, listOperTokens);
  }
  
  private IExpression makeInfix(List<IExpression> listExpr, List<Token> listOper) throws ParseException {
    List<FunctionType> listFunc = new LinkedList<>();
    for (Iterator<Token> it = listOper.iterator(); it.hasNext(); ) {
      Token token = it.next();
      FunctionType type = FunctionType.parse(token.getText());
      checkNull(type, "Invalid operator: " + String.valueOf(token));
      listFunc.add(type);
    } 
    return makeInfixFunc(listExpr, listFunc);
  }
  
  private IExpression makeInfixFunc(List<IExpression> listExpr, List<FunctionType> listFunc) throws ParseException {
    if (listExpr.size() != listFunc.size() + 1)
      throw new ParseException("Invalid infix expression, expressions: " + listExpr.size() + ", operators: " + listFunc.size()); 
    if (listExpr.size() == 1)
      return listExpr.get(0); 
    int minPrecedence = Integer.MAX_VALUE;
    int maxPrecedence = Integer.MIN_VALUE;
    for (Iterator<FunctionType> it = listFunc.iterator(); it.hasNext(); ) {
      FunctionType type = it.next();
      minPrecedence = Math.min(type.getPrecedence(), minPrecedence);
      maxPrecedence = Math.max(type.getPrecedence(), maxPrecedence);
    } 
    if (maxPrecedence < minPrecedence || maxPrecedence - minPrecedence > 10)
      throw new ParseException("Invalid infix precedence, min: " + minPrecedence + ", max: " + maxPrecedence); 
    for (int i = maxPrecedence; i >= minPrecedence; i--)
      mergeOperators(listExpr, listFunc, i); 
    if (listExpr.size() != 1 || listFunc.size() != 0)
      throw new ParseException("Error merging operators, expressions: " + listExpr.size() + ", operators: " + listFunc.size()); 
    return listExpr.get(0);
  }
  
  private void mergeOperators(List<IExpression> listExpr, List<FunctionType> listFuncs, int precedence) throws ParseException {
    for (int i = 0; i < listFuncs.size(); i++) {
      FunctionType type = listFuncs.get(i);
      if (type.getPrecedence() == precedence) {
        listFuncs.remove(i);
        IExpression expr1 = listExpr.remove(i);
        IExpression expr2 = listExpr.remove(i);
        IExpression exprOper = makeFunction(type, new IExpression[] { expr1, expr2 });
        listExpr.add(i, exprOper);
        i--;
      } 
    } 
  }
  
  private IExpression parseExpression(Deque<Token> deque) throws ParseException {
    FunctionType type, operType;
    Token token = deque.poll();
    checkNull(token, "Missing expression");
    switch (null.$SwitchMap$net$optifine$expr$TokenType[token.getType().ordinal()]) {
      case 1:
        return makeConstantFloat(token);
      case 2:
        type = getFunctionType(token, deque);
        if (type != null)
          return makeFunction(type, deque); 
        return makeVariable(token);
      case 3:
        return makeBracketed(token, deque);
      case 4:
        operType = FunctionType.parse(token.getText());
        checkNull(operType, "Invalid operator: " + String.valueOf(token));
        if (operType == FunctionType.PLUS)
          return parseExpression(deque); 
        if (operType == FunctionType.MINUS) {
          IExpression exprNeg = parseExpression(deque);
          return makeFunction(FunctionType.NEG, new IExpression[] { exprNeg });
        } 
        if (operType == FunctionType.NOT) {
          IExpression exprNot = parseExpression(deque);
          return makeFunction(FunctionType.NOT, new IExpression[] { exprNot });
        } 
        break;
    } 
    throw new ParseException("Invalid expression: " + String.valueOf(token));
  }
  
  private static IExpression makeConstantFloat(Token token) throws ParseException {
    float val = Config.parseFloat(token.getText(), Float.NaN);
    if (val == Float.NaN)
      throw new ParseException("Invalid float value: " + String.valueOf(token)); 
    return (IExpression)new ConstantFloat(val);
  }
  
  private FunctionType getFunctionType(Token token, Deque<Token> deque) throws ParseException {
    Token tokenNext = deque.peek();
    if (tokenNext != null && tokenNext.getType() == TokenType.BRACKET_OPEN) {
      FunctionType functionType = FunctionType.parse(token.getText());
      checkNull(functionType, "Unknown function: " + String.valueOf(token));
      return functionType;
    } 
    FunctionType type = FunctionType.parse(token.getText());
    if (type == null)
      return null; 
    if (type.getParameterCount(new IExpression[0]) > 0)
      throw new ParseException("Missing arguments: " + String.valueOf(type)); 
    return type;
  }
  
  private IExpression makeFunction(FunctionType type, Deque<Token> deque) throws ParseException {
    if (type.getParameterCount(new IExpression[0]) == 0) {
      Token tokenNext = deque.peek();
      if (tokenNext == null || tokenNext.getType() != TokenType.BRACKET_OPEN)
        return makeFunction(type, new IExpression[0]); 
    } 
    Token tokenOpen = deque.poll();
    Deque<Token> dequeBracketed = getGroup(deque, TokenType.BRACKET_CLOSE, true);
    IExpression[] exprs = parseExpressions(dequeBracketed);
    return makeFunction(type, exprs);
  }
  
  private IExpression[] parseExpressions(Deque<Token> deque) throws ParseException {
    List<IExpression> list = new ArrayList<>();
    while (true) {
      Deque<Token> dequeArg = getGroup(deque, TokenType.COMMA, false);
      IExpression expr = parseInfix(dequeArg);
      if (expr == null)
        break; 
      list.add(expr);
    } 
    IExpression[] exprs = list.<IExpression>toArray(new IExpression[list.size()]);
    return exprs;
  }
  
  private static IExpression makeFunction(FunctionType type, IExpression[] args) throws ParseException {
    ExpressionType[] funcParamTypes = type.getParameterTypes(args);
    if (args.length != funcParamTypes.length)
      throw new ParseException("Invalid number of arguments, function: \"" + type.getName() + "\", count arguments: " + args.length + ", should be: " + funcParamTypes.length); 
    for (int i = 0; i < args.length; i++) {
      IExpression arg = args[i];
      ExpressionType argType = arg.getExpressionType();
      ExpressionType funcParamType = funcParamTypes[i];
      if (argType != funcParamType)
        throw new ParseException("Invalid argument type, function: \"" + type.getName() + "\", index: " + i + ", type: " + String.valueOf(argType) + ", should be: " + String.valueOf(funcParamType)); 
    } 
    if (type.getExpressionType() == ExpressionType.FLOAT)
      return (IExpression)new FunctionFloat(type, args); 
    if (type.getExpressionType() == ExpressionType.BOOL)
      return (IExpression)new FunctionBool(type, args); 
    if (type.getExpressionType() == ExpressionType.FLOAT_ARRAY)
      return (IExpression)new FunctionFloatArray(type, args); 
    throw new ParseException("Unknown function type: " + String.valueOf(type.getExpressionType()) + ", function: " + type.getName());
  }
  
  private IExpression makeVariable(Token token) throws ParseException {
    if (this.expressionResolver == null)
      throw new ParseException("Model variable not found: " + String.valueOf(token)); 
    IExpression expr = this.expressionResolver.getExpression(token.getText());
    if (expr == null)
      throw new ParseException("Model variable not found: " + String.valueOf(token)); 
    return expr;
  }
  
  private IExpression makeBracketed(Token token, Deque<Token> deque) throws ParseException {
    Deque<Token> dequeBracketed = getGroup(deque, TokenType.BRACKET_CLOSE, true);
    return parseInfix(dequeBracketed);
  }
  
  private static Deque<Token> getGroup(Deque<Token> deque, TokenType tokenTypeEnd, boolean tokenEndRequired) throws ParseException {
    Deque<Token> dequeGroup = new ArrayDeque<>();
    int level = 0;
    for (Iterator<Token> it = deque.iterator(); it.hasNext(); ) {
      Token token = it.next();
      it.remove();
      if (level == 0 && token.getType() == tokenTypeEnd)
        return dequeGroup; 
      dequeGroup.add(token);
      if (token.getType() == TokenType.BRACKET_OPEN)
        level++; 
      if (token.getType() == TokenType.BRACKET_CLOSE)
        level--; 
    } 
    if (tokenEndRequired)
      throw new ParseException("Missing end token: " + String.valueOf(tokenTypeEnd)); 
    return dequeGroup;
  }
  
  private static void checkNull(Object obj, String message) throws ParseException {
    if (obj == null)
      throw new ParseException(message); 
  }
}
