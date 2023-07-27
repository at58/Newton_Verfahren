package dynamische_variante;

public class Main {

  public static void main(String[] args) {

    //MyFunction.inputFormula();
    String[] terms = new String[] {"-2,5x","0,5x","-2", "-1,009", "2", "2,5490", "-x", "x"};

    for (String s : terms) {
      System.out.println(s + "\t\t" + MyFunction.validateFormula(s));
    }
  }
}
