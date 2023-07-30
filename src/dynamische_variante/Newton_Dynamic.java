package dynamische_variante;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Newton_Dynamic {

  static final Scanner scanner = new Scanner(System.in);
  static final double epsilon = 0.01;
  static String function;

  public static List<Double> process() {

    System.out.println("Funktion eingeben: ");
    System.out.print("f(x)= ");
    String unpreparedFunc = scanner.nextLine().strip();
    function = prepareInput(unpreparedFunc);

    List<Double> zeroPointAreas;
    List<Double> zeroPoints = new ArrayList<>();

    boolean repeat = true;
    while(repeat) {
      int[] intervall = getIntervall();
      int start = intervall[0];
      int end = intervall[1];

      zeroPointAreas = getZeroPointCandidates(start, end);

      if (zeroPointAreas.isEmpty()) {
        System.out.println("Es gibt keine Nullstellen im vorgegebenen Intervall!");
        System.out.println("Soll ein neues Intervall fur f(x) = " + function
                               + " untersucht werden?\nj = ja\nn = nein");
        String yesOrNo = scanner.nextLine();
        if (!yesOrNo.equals("j")) {
          repeat = false;
        }
      }
      else {
        System.out.println("Folgende Xo-Werte werden fur die Nullstellensuche verwendet: ");
        for (double point : zeroPointAreas) {
          System.out.print(point + " ");
        }
        System.out.println();
        for (double x : zeroPointAreas) {
          zeroPoints.add(newtonMethod(x));
        }
        repeat = false;
      }
    }
    return zeroPoints;
  }

  public static String getDerivation(String function) {

    String derivation;

    if (function.contains("x")) {
      // eliminate all constants without x
      String constantFree = eliminateConstants(function);
      // differentiate by x
      String differentiated = differentiate_X(constantFree);

      derivation = deriveDegree_1(differentiated);
    } else {
      derivation = "0";
    }
    return derivation;
  }

  private static String differentiate_X(String function) {

    StringBuilder exponentDerived = new StringBuilder();
    int length = (function.length() - 1);

    for (int i = 0; i <= length; i++) {
      exponentDerived.append(function.charAt(i));
      if (function.charAt(i) == 'x') {
        if (i != length) {
          if (function.charAt(i+1) == '^' ) { // x^...
            i++;
            String exponent = getExponent(function.substring(i + 1))
                .replace(",",".");
            double newExp = calc(exponent + "-1","0");
            String newExpStr = String.valueOf(newExp);
            if (isInteger(newExpStr)) {
              newExpStr = newExpStr.substring(0, newExpStr.indexOf('.'));
            } else {
              newExpStr = newExpStr.replace(".", ",");
            }
            exponentDerived.deleteCharAt(exponentDerived.length()-1);
            exponentDerived.append(exponent.replace(".", ","))
                           .append("*x^")
                           .append(newExpStr);
            i = i + exponent.length();
          }
        }
      }
    }
    return exponentDerived.toString();
  }

  private static String deriveDegree_1(String function) {

    StringBuilder builder = new StringBuilder();
    int length = (function.length() -1);
    int index = 0;

    while (index <= length) {
      char character = function.charAt(index);
      builder.append(character);
      if (character == 'x') {
        int currentBuilderIndex = builder.length() - 1;
        if (index == length) {
          builder.replace(currentBuilderIndex, currentBuilderIndex + 1, "1");
        } else {
          if (function.charAt(index+1) != '^') {
            builder.replace(currentBuilderIndex, currentBuilderIndex + 1, "1");
          } else if (function.charAt(index+1) == '^') {
            String exponent = getExponent(function.substring(index+2));
            if (exponent.equals("0")) {
              builder.replace(currentBuilderIndex, currentBuilderIndex + 1, "1");
              index += 2;
            }
            if (exponent.equals("1")) {
              index += 2;
            }
          }
        }
      }
      index++;
    }
    return builder.toString();
  }

  public static boolean isDegree_1(String function, int start) {
    boolean isDegree_1;
    int index = start;
    if (function.charAt(index) == '1') {
      isDegree_1 = true;
      index++;
      if(index != function.length()-1) {
        if (Character.isDigit(function.charAt(index))) {
          isDegree_1 = false;
        }
      }
    } else {
      isDegree_1 = false;
    }
    return isDegree_1;
  }

  private static boolean isInteger(String number) {
    String decimal = number.substring(number.indexOf(".") + 1);
    for (char c : decimal.toCharArray()) {
      if (c != '0') {
        return false;
      }
    }
    return true;
  }

  // Bug free
  private static String getExponent(String subString) {
    StringBuilder exponentBuilder;
    if (subString.length() == 1) {
      return subString;
    } else {
      exponentBuilder = new StringBuilder();
      int index = 0;
      while (Character.isDigit(subString.charAt(index)) || subString.charAt(index) == ',') {
        exponentBuilder.append(subString.charAt(index));
        index++;
        if (index == subString.length()-1) {
          break;
        }
      }
    }
    return exponentBuilder.toString();
  }

  // Bug free
  private static String eliminateConstants(String function) {

    StringBuilder derivation = new StringBuilder();

    int leftBorder = 0;

    for (int i = function.startsWith("-") ? 1 : 0; i < function.length(); i++) {
      if (function.charAt(i) == '+'
          || function.charAt(i) == '-') {

        String subString = function.substring(leftBorder, i);
        if (subString.contains("x")) {
          derivation.append(subString);
        }
        leftBorder = i++;
      }
    }
    String lastPart = function.substring(leftBorder);
    if (lastPart.contains("x")) {
      derivation.append(lastPart);
    }
    return derivation.toString();
  }

  private static double newtonMethod(double Xo) {
    double xn = Xo;
    double xn_min_1;

    while (true) {
      xn_min_1 = xn;
      xn = xn_min_1 - (calc(function, String.valueOf(xn_min_1)) / calc(function, String.valueOf(xn_min_1)));
      if (deltaX(xn, xn_min_1) < epsilon
          || isAlmostZero(calc(function, String.valueOf(xn)))) {
        break;
      }
    }
    return round(xn);
  }

  private static double round(double value) {

    double factor = Math.pow(10, 4);
    return (Math.round(value * factor) / factor);
  }

  private static double deltaX(double x0, double x1) {
    return Math.abs(x0-x1);
  }

  private static boolean isAlmostZero(double value) {

    if (value <= 0.001 && value >= -0.001) {
      return true;
    } else {
      return false;
    }
  }

  private static List<Double> getZeroPointCandidates(int start, int end) {

    List<Double> zeroPointAreas = new ArrayList<>();
    String beginn = String.valueOf(start);
    String ende = String.valueOf(end);
    /*
    Math.signum() zeigt an, ob der als Argument übergebene Wert eine positive oder negative Zahl
    oder 0 ist.
    */
    double sign_1 = Math.signum(calc(function, beginn));
    for (int i = (start + 1); i <= end; i++) {
      double sign_2 = Math.signum(calc(function, String.valueOf(i)));
      if (sign_2 != sign_1) {
        zeroPointAreas.add(i - 0.5);
        sign_1 = sign_2;
      }
    }
    return zeroPointAreas;
  }

  private static int[] getIntervall() {

    int[] intervall = new int[2];
    System.out.println("Gebe das Intervall an fur die Nullstellensuche von f(x) = " + function);

    System.out.print("Start: ");
    String start = scanner.nextLine().strip().replace(" ", "");
    while (isNotInteger(start)) {
      System.out.println("Fehlerhafte Eingabe! Geben Sie einen ganzzahligen Wert ein.");
      System.out.print("Start: ");
      start = scanner.nextLine().strip().replace(" ", "");
    }

    System.out.print("Ende: ");
    String end = scanner.nextLine().strip().replace(" ", "");
    while (isNotInteger(end)) {
      System.out.println("Fehlerhafte Eingabe! Geben Sie einen ganzzahligen Wert ein.");
      System.out.print("Ende: ");
      end = scanner.nextLine().strip().replace(" ", "");
    }
    intervall[0] = Integer.parseInt(start);
    intervall[1] = Integer.parseInt(end);
    return intervall;
  }

  private static String prepareInput(String function) {
    String prepared = function.toLowerCase()
                              .replaceAll("e","2.718282")
                              .replaceAll("π", "3.14159")
                              .replaceAll("pi", "3.14159");
    int index = 0;
    if(prepared.startsWith("-")) {
      index++;
      if(prepared.charAt(1) == 'x') {
        index++;
      }
    } else {
      if(prepared.startsWith("x")) {
        index++;
      }
    }
    for (int i = index; i < prepared.length(); i++) {
      if (prepared.charAt(i) == 'x') {
        if(isNumber(prepared.charAt(i-1))) {
          StringBuilder builder = new StringBuilder();
          builder.append(prepared, 0, i);
          builder.append("*");
          builder.append(prepared, i, (prepared.length()));
          prepared = builder.toString();
          i++;
        }
      }
    }
    return prepared;
  }

  private static boolean isNumber(char c) {
    return Character.isDigit(c);
  }

  private static boolean isNotInteger(String str) {

    boolean isNotInt = false;

    for (int i = 0; i < str.length() ; i++) {
      if (i == 0) {
        if (Character.isDigit(str.charAt(i))) {
          continue;
        } else if (str.charAt(i) == '-' || str.charAt(i) == '+') {
          continue;
        } else {
          isNotInt = true; // is not integer
        }
      }
      if (!Character.isDigit(str.charAt(i))) {
        isNotInt = true;
      }
    }
    if(str.isEmpty()) {
      isNotInt = true;
    }
    if(str.contains("-") && str.length() == 1) {
      isNotInt = true;
    }
    if (str.contains("+") && str.length() == 1) {
      isNotInt = true;
    }
    return isNotInt;
  }

  private static double calc(String function, String argument) {

    final String str = function.replaceAll("x", argument);

    return new Object() {
      int pos = -1, ch;

      void nextChar() {

        ch = (++pos < str.length()) ? str.charAt(pos) : -1;
      }

      boolean eat(int charToEat) {

        while (ch == ' ') nextChar();
        if (ch == charToEat) {
          nextChar();
          return true;
        }
        return false;
      }

      double parse() {

        nextChar();
        double x = parseExpression();
        if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
        return x;
      }

      // Grammar:
      // expression = term | expression `+` term | expression `-` term
      // term = factor | term `*` factor | term `/` factor
      // factor = `+` factor | `-` factor | `(` expression `)` | number
      //        | functionName `(` expression `)` | functionName factor
      //        | factor `^` factor

      double parseExpression() {

        double x = parseTerm();
        for (; ; ) {
          if (eat('+')) x += parseTerm(); // addition
          else if (eat('-')) x -= parseTerm(); // subtraction
          else return x;
        }
      }

      double parseTerm() {

        double x = parseFactor();
        for (; ; ) {
          if (eat('*')) x *= parseFactor(); // multiplication
          else if (eat('/')) x /= parseFactor(); // division
          else return x;
        }
      }

      double parseFactor() {

        if (eat('+')) return +parseFactor(); // unary plus
        if (eat('-')) return -parseFactor(); // unary minus

        double x;
        int startPos = this.pos;
        if (eat('(')) { // parentheses
          x = parseExpression();
          if (!eat(')')) throw new RuntimeException("Missing ')'");
        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
          while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
          x = Double.parseDouble(str.substring(startPos, this.pos));
        } else if (ch >= 'a' && ch <= 'z') { // functions
          while (ch >= 'a' && ch <= 'z') nextChar();
          String func = str.substring(startPos, this.pos);
          if (eat('(')) {
            x = parseExpression();
            if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
          } else {
            x = parseFactor();
          }
          if (func.equals("sqrt")) x = Math.sqrt(x);
          else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
          else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
          else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
          else throw new RuntimeException("Unknown function: " + func);
        } else {
          throw new RuntimeException("Unexpected: " + (char) ch);
        }

        if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

        return x;
      }
    }.parse();
  }
}