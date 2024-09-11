package srg.net.optifine.expr;

import net.optifine.expr.FunctionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionFloat;
import net.optifine.shaders.uniform.Smoother;

public class FunctionFloat implements IExpressionFloat {
  private FunctionType type;
  
  private IExpression[] arguments;
  
  private int smoothId = -1;
  
  public FunctionFloat(FunctionType type, IExpression[] arguments) {
    this.type = type;
    this.arguments = arguments;
  }
  
  public float eval() {
    IExpression expr0, args[] = this.arguments;
    switch (null.$SwitchMap$net$optifine$expr$FunctionType[this.type.ordinal()]) {
      case 1:
        expr0 = args[0];
        if (!(expr0 instanceof net.optifine.expr.ConstantFloat)) {
          float valRaw = evalFloat(args, 0);
          float valFadeUp = (args.length > 1) ? evalFloat(args, 1) : 1.0F;
          float valFadeDown = (args.length > 2) ? evalFloat(args, 2) : valFadeUp;
          if (this.smoothId < 0)
            this.smoothId = Smoother.getNextId(); 
          float valSmooth = Smoother.getSmoothValue(this.smoothId, valRaw, valFadeUp, valFadeDown);
          return valSmooth;
        } 
        break;
    } 
    return this.type.evalFloat(this.arguments);
  }
  
  private static float evalFloat(IExpression[] exprs, int index) {
    IExpressionFloat ef = (IExpressionFloat)exprs[index];
    float val = ef.eval();
    return val;
  }
  
  public String toString() {
    return String.valueOf(this.type) + "()";
  }
}
