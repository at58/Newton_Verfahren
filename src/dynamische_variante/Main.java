package dynamische_variante;

public class Main {

  public static void main(String[] args) {


/*    List<Double> result = Newton_Dynamic.process();
    for (Double d : result) {
      System.out.println(d);
    }*/

    //System.out.println(Newton_Dynamic.getDerivation("x-2*x^3,5-x^2+x-10"));
    //System.out.println(Newton_Dynamic.getDerivation("x^2+x^2,5+2*x+3-x"));
    //System.out.println(Newton_Dynamic.getDerivation("x^2+x+x^1,5+x^1"));
    //System.out.println(Newton_Dynamic.getDerivation("x^2+(-1x)"));
    System.out.println(Newton_Dynamic.tidyUp("x^2+(-1x*(-1))"));

    System.out.println("---------------------------------------------------");
    System.out.println(Newton_Dynamic.calc("cos(x)*cos(x)", "1"));

/*    String[] str = new String[] {"x^2", "10", "1", "1,5", "2"};
    for (String s : str) {
      System.out.println(s + " = " + Newton_Dynamic.isDegree_1(s, 0));
    }*/
  }
}
