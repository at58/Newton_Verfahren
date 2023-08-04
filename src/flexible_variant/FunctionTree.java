/*
package dynamische_variante;

import java.util.Scanner;

public class FunctionTree {

  private static final Scanner scanner = new Scanner(System.in);

  public static void inputFormula() {

    System.out.println(
        "Geben eine Funktion ein fur die Suche nach Nullstellen mit dem <<Newton-Verfahren>>: ");
    System.out.print("f(x)= ");

    String formula = scanner.nextLine();
    formula = prepareFormula(formula);
    while (!validateFormula()) {
      System.out.println("Die Eingabe ist invalide. Wiederhole die Eingabe:");
      System.out.print("f(x)= ");
      formula = scanner.nextLine();
      System.out.println(formula);
    }
  }

  private static String prepareFormula(String formula) {
    String result = formula.strip();
    result = result.toLowerCase();
    StringBuilder old = new StringBuilder(result);
    while (old.toString().contains("x")) {
      int index = old.indexOf("x");
      if (old.charAt(index-1) != '*') {
        System.out.println(index);
        old.replace(index, (index + 1), "*X");
      }
    }
    result = old.toString();
    return result;
  }

  public static boolean validateFormula(String formula) {

    boolean isValid = false;
    String regex = "(-?[0-9]+X?|-?X|(-?[0-9]*(?<=[0-9]),?(?<=,)[0-9]+X?))";

    if(formula.matches(regex)) {
      isValid = true;
    }
    return isValid;
  }
}*/
