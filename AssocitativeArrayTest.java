import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import structures.*;

public class AssocitativeArrayTest {
  /**
   * Tests on set() on a <String, String> associative array
   */
  @Test
  public void hyeonKim1(){
    /**
     * Create an empty test array
     */
    AssociativeArray<String, String> testArray = new AssociativeArray<String, String>();

    /**
     * Add elements to the testArray
     */
    testArray.set("hello", "world");
    /**
     * value correctivity example
     */
    try {
      assertEquals("world", testArray.get("hello"), "hello world example");
    } catch (Exception e) {
      fail("hello world example");
    }
    /**
     * setting existing key with another value
     */
    testArray.set("hello", "hello");
    /**
     * checking whether it was correctly set.
     */
    try {
      assertEquals("hello", testArray.get("hello"), "existing key example");
    } catch (Exception e) {
      fail("existing key example");
    }
    /**
     * checking the length difference
     */
    testArray.set("hello", "a");
    assertEquals(1, testArray.size(), "existing key length example");
  }
  /**
   * Tests on set() on a <String, String> associative array
   */
  @Test
  public void hyeonKim2(){
    AssociativeArray<String, String> testArray = new AssociativeArray<String, String>();
    AssociativeArray<String, String> copiedArray = new AssociativeArray<String, String>();
    testArray.set("hello", "world!");
    copiedArray = testArray.clone();
    copiedArray.set("hello", "hi");
    copiedArray.set("world", "hi");
    try {
      assertEquals("world!", testArray.get("hello"), "cloned array example");
    } catch (Exception e) {
      fail("cloned array example");
    }
    assertEquals(2, copiedArray.size(), "cloned array length example");
  }
  @Test
  public void hyeonKimEdgeCases(){
    AssociativeArray<String, String> testArray = new AssociativeArray<String, String>();
     /**
     * null example 1
     */
    testArray.set(null, null);
    try {
      assertEquals(null, testArray.get(null), "null example");
    } catch (Exception e) {
      fail("null example");
    }

    /**
     * null example 2
     */
    testArray.set("hello", null);
    try {
      assertEquals(null, testArray.get("hello"), "null example 2");
    } catch (Exception e) {
      fail("null example 2");
    }

    /**
     * Checking whether null hasKey works
     */
    assertTrue(testArray.hasKey(null), "null hasKey");
  }
} // AssocitativeArrayTest
