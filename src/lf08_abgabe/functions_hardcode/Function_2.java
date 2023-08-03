package lf08_abgabe.functions_hardcode;

public class Function_2 extends BaseFunction {

  @Override
  public double f(double d) {

    return (Math.pow(d,3) + 3 * Math.pow(d, 2) - 2);
  }

  @Override
  public double df(double d) {

    return (3 * Math.pow(d,2) + 6 * d);
  }

  @Override
  public String toString() {

    return "x^3 + 3x^2 - 2";
  }
}