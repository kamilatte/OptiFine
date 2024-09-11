package notch.net.optifine.expr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IParameters;

public class ParametersVariable implements IParameters {
  private ExpressionType[] first;
  
  private ExpressionType[] repeat;
  
  private ExpressionType[] last;
  
  private int maxCount = Integer.MAX_VALUE;
  
  private static final ExpressionType[] EMPTY = new ExpressionType[0];
  
  public ParametersVariable() {
    this(null, null, null);
  }
  
  public ParametersVariable(ExpressionType[] first, ExpressionType[] repeat, ExpressionType[] last) {
    this(first, repeat, last, 2147483647);
  }
  
  public ParametersVariable(ExpressionType[] first, ExpressionType[] repeat, ExpressionType[] last, int maxCount) {
    this.first = normalize(first);
    this.repeat = normalize(repeat);
    this.last = normalize(last);
    this.maxCount = maxCount;
  }
  
  private static ExpressionType[] normalize(ExpressionType[] exprs) {
    if (exprs == null)
      return EMPTY; 
    return exprs;
  }
  
  public ExpressionType[] getFirst() {
    return this.first;
  }
  
  public ExpressionType[] getRepeat() {
    return this.repeat;
  }
  
  public ExpressionType[] getLast() {
    return this.last;
  }
  
  public int getCountRepeat() {
    if (this.first == null)
      return 0; 
    return this.first.length;
  }
  
  public ExpressionType[] getParameterTypes(IExpression[] arguments) {
    int countFixedParams = this.first.length + this.last.length;
    int countVarArgs = arguments.length - countFixedParams;
    int countRepeat = 0;
    int countVarParams = 0;
    while (countVarParams + this.repeat.length <= countVarArgs && countFixedParams + countVarParams + this.repeat.length <= this.maxCount) {
      countRepeat++;
      countVarParams += this.repeat.length;
    } 
    List<ExpressionType> list = new ArrayList<>();
    list.addAll(Arrays.asList(this.first));
    for (int i = 0; i < countRepeat; i++)
      list.addAll(Arrays.asList(this.repeat)); 
    list.addAll(Arrays.asList(this.last));
    ExpressionType[] ets = list.<ExpressionType>toArray(new ExpressionType[list.size()]);
    return ets;
  }
  
  public net.optifine.expr.ParametersVariable first(ExpressionType... first) {
    return new net.optifine.expr.ParametersVariable(first, this.repeat, this.last);
  }
  
  public net.optifine.expr.ParametersVariable repeat(ExpressionType... repeat) {
    return new net.optifine.expr.ParametersVariable(this.first, repeat, this.last);
  }
  
  public net.optifine.expr.ParametersVariable last(ExpressionType... last) {
    return new net.optifine.expr.ParametersVariable(this.first, this.repeat, last);
  }
  
  public net.optifine.expr.ParametersVariable maxCount(int maxCount) {
    return new net.optifine.expr.ParametersVariable(this.first, this.repeat, this.last, maxCount);
  }
}
