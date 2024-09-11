package srg.net.optifine.expr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import net.optifine.expr.ExpressionParser;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;

public class TestExpressions {
  public static void main(String[] args) throws Exception {
    ExpressionParser ep = new ExpressionParser(null);
    while (true) {
      try {
        InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(ir);
        String line = br.readLine();
        if (line.length() <= 0)
          break; 
        IExpression expr = ep.parse(line);
        if (expr instanceof IExpressionFloat) {
          IExpressionFloat ef = (IExpressionFloat)expr;
          float val = ef.eval();
          System.out.println("" + val);
        } 
        if (expr instanceof IExpressionBool) {
          IExpressionBool eb = (IExpressionBool)expr;
          boolean val = eb.eval();
          System.out.println("" + val);
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
}
