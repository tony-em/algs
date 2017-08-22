package algs.optimization;

public class Function {

    private Expression expression;

    public enum TypeLocalization {
        MIN_LOCALIZATION, MAX_LOCALIZATION
    }

    public Function(Expression expression) {
        this.expression = expression;
    }

    public interface Expression {
        double expression(double[] args);
    }

    public double calculate(double[] args) {
        return expression.expression(args);
    }
}