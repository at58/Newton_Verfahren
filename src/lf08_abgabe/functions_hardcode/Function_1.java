package lf08_abgabe.functions_hardcode;

public class Function_1 extends BaseFunction {


  @Override
  public double f(double d) {

    return (Math.pow(d,3) - 4 * d + 10);
  }

  @Override
  public double df(double d) {

    return (3 * Math.pow(d,2) - 4);
  }

  @Override
  public String toString() {

    return "x^3 - 4x + 10";
  }
}