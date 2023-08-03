package lf08_abgabe.functions_hardcode;

public class Function_3 extends BaseFunction {

  @Override
  public double f(double d) {

    return (Math.pow(d, 2) - 2);
  }

  @Override
  public double df(double d) {

    return 2 * d;
  }

  @Override
  public String toString() {

    return "x^2 - 2";
  }

}
