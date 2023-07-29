package dynamische_variante;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

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

    String derivation = "";

    if (function.contains("x")) {
      // eliminate all constants without x
      String constantsFreeTerm = eliminateConstants(function);

      StringBuilder functionBuilder = new StringBuilder();

      // differentiate x
      int length = (constantsFreeTerm.length() - 1);
      for (int i = 0; i <= length; i++) {

        functionBuilder.append(constantsFreeTerm.charAt(i));
        if (constantsFreeTerm.charAt(i) == 'x') {
          if (i == length) {
            functionBuilder.replace(i, i+1, "1");
            break;
          }
          if (constantsFreeTerm.charAt(i + 1) != '^') {
            functionBuilder.replace(i, i+1, "1");
          }
          else { // x^...
            i++;
            String exponent = getExponent(constantsFreeTerm.substring(i + 1));
            Double newDoubleExp = null;
            Integer newIntExp = null;
            if (exponent.contains(",")) {
              String adapted = exponent.replace(",", ".");
              newDoubleExp = Double.parseDouble(adapted) - 1;
            } else {
              newIntExp = Integer.parseInt(exponent) - 1;
            }
            int exponentSize = (exponent.length());
            functionBuilder.deleteCharAt(functionBuilder.length()-1);
            functionBuilder.append(exponent).append("*x^");
            if (newIntExp == null) {
              functionBuilder.append(newDoubleExp);
            } else {
              functionBuilder.append(newIntExp);
            }
            i += exponentSize;
          }
        }
      }
      derivation = functionBuilder.toString();
    } else {
      derivation = "0";
    }
    return derivation;
  }

  private static String getExponent(String subString) {

    int rightBorder = 0;
    for (int i = 0; i < subString.length(); i++) {
      if (!Character.isDigit(subString.charAt(i))
          && subString.charAt(i) != ',') {
        rightBorder = i;
        break;
      }
    }
    String result = subString.substring(0, rightBorder);

    return result;
  }

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

  private static String joinTerm(String[] additions, String[] subtractions) {
    StringBuilder constantsFreeTerm = new StringBuilder();

    for (String add : additions) {
      constantsFreeTerm.append(add).append("+");
    }
    int lastIndex = constantsFreeTerm.length()-1;

    constantsFreeTerm.replace(lastIndex, lastIndex+1, "-");

    for (String sub: subtractions) {
      constantsFreeTerm.append(sub).append("-");
    }
    lastIndex = constantsFreeTerm.length()-1;
    constantsFreeTerm.replace(lastIndex, lastIndex+1, "");

    return constantsFreeTerm.toString();
  }

  private static String[] removeConstants(String[] terms, char operator) {
    String[] result = new String[terms.length];
    String splitter;
    if (operator == '+') {
      splitter = "-";
    }
    else {
      splitter = "\\+";
    }

    for (int i = 0; i < terms.length; i++) {
      if (terms[i].contains("x")) {
        if (terms[i].contains(splitter)) {
          String[] spitted = terms[i].split(splitter);
          result[i] = spitted[0];
        } else {
          result[i] = terms[i];
        }
      } else {
        terms[i] = "";
      }
    }
    int counter = 0;
    for (String s: result) {
      if (s != null) {
        counter++;
      }
    }
    int index = 0;
    String[] finalResult = new String[counter];
    for (String s : result) {
      if (s != null) {
        finalResult[index++] = s;
      }
    }
    return finalResult;
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

  public static String prepareInput(String function) {
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

  @Deprecated
  private static boolean isValid(String function) {

    String blacklist = "^[0-9xX+\\-*/\\^(),e]";
    Pattern pattern = Pattern.compile(blacklist);
    if (function.matches(blacklist)){
      return true;
    } else return false;
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