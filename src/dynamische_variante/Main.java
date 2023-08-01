package dynamische_variante;

public class Main {

  public static void main(String[] args) {


/*    List<Double> result = Newton_Dynamic.process();
    for (Double d : result) {
      System.out.println(d);
    }*/

//    System.out.println(Newton_Dynamic.tidyUpBrackets("x+(x-1)"));
//    System.out.println(Newton_Dynamic.prepareInput("x+(x-1)"));
//
//    System.out.println(Newton_Dynamic.tidyUpBrackets("x+(x-1)(x+1)"));
    System.out.println(Newton_Dynamic.prepareInput("x^2(+3x+1)(1)"));
    System.out.println(Newton_Dynamic.calc(
        Newton_Dynamic.prepareInput("x^2(+3x+1)(1)"), "1"));

/*    String[] str = new String[] {"x^2", "10", "1", "1,5", "2"};
    for (String s : str) {
      System.out.println(s + " = " + Newton_Dynamic.isDegree_1(s, 0));
    }*/
  }
}
