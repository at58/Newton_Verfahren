package test;

import static org.junit.jupiter.api.Assertions.*;
import dynamische_variante.Newton_Dynamic;
import java.util.List;
import org.junit.jupiter.api.Test;

class UnitTest {

  private String input(String str) {
    return Newton_Dynamic.prepareInput(str);
  }

  @Test
  void roundValueTest() {
    //Arrange
    double value = 3.1415926;
    double expected = 3.1416;
    //Act
    double actual = Newton_Dynamic.round(value);
    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void derivationTest() {
    //Arrange
    String value = "3x^2+2x-5";
    String expected = "3*2*x +2*1";
    //Act
    String actual = Newton_Dynamic.getDerivation(input(value));
    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void functionPreperationTest() {
    //Arrange
    String value = "3x^2+2x-5";
    String expected = "3*x^2 +2*x-5";
    //Act
    String actual = Newton_Dynamic.prepareInput(input(value));
    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void isIntegerTest_1() {
    //Arrange
    String value = "2.009";
    //Act
    boolean number = Newton_Dynamic.isInteger(value);
    //Assert
    assertFalse(number);
  }

  @Test
  void isIntegerTest_2() {
    //Arrange
    String value = "2.15";
    //Act
    boolean number = Newton_Dynamic.isInteger(value);
    //Assert
    assertFalse(number);
  }

  @Test
  void isIntegerTest_3() {
    //Arrange
    String value = "2.0";
    //Act
    boolean number = Newton_Dynamic.isInteger(value);
    //Assert
    assertTrue(number);
  }

  @Test
  void isIntegerTest_4() {
    //Arrange
    String value = "1";
    //Act
    boolean number = Newton_Dynamic.isInteger(value);
    //Assert
    assertTrue(number);
  }

  @Test
  void extractExponent_1() {
    //Arrange
    String value = "2 +x...";
    String expected = "2";
    //Act
    String actual = Newton_Dynamic.getExponent(value);
    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void extractExponent_2() {
    //Arrange
    String value = "2,5 -1*x...";
    String expected = "2,5";
    //Act
    String actual = Newton_Dynamic.getExponent(value);
    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void constantElimination() {
    //Arrange
    String value = "2x-10";
    String expected = "2x";
    //Act
    String actual = Newton_Dynamic.eliminateConstants(value);
    //Assert
  }

  @Test
  void bracketTest() {
    //Arrange
    String value = "2x-10(3x+5)(2x-2)-5";
    String expected = "2*x-10*(3*x+5)*(2*x-2)-5";
    //Act
    String actual = Newton_Dynamic.processBrackets(input(value));
    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void zeroPointCandidatesTest() {
    //Arrange
    String function = "x^2 -2";
    int start = -5;
    int end = 5;
    double[] expected = new double[] {-1.5, 1.5};
    //Act
    List<Double> actual = Newton_Dynamic.getZeroPointCandidates(function, start, end);
    double[] actualArray = new double[actual.size()];
    for(Double d : actual) {
      actualArray[actual.indexOf(d)] = d;
    }
    //Assert
    assertArrayEquals(expected, actualArray);
  }
}