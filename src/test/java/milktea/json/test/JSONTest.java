package milktea.json.test;

import java.util.concurrent.TimeUnit;

public final class JSONTest {

  private static final String TEST_1 = "{\"n\": 0}";
  private static final String TEST_2 = "{\"name\":[2,3,4,5,6,true,false,{\"a\\u3042\":\"\\u3042\\u3042\\u3042\\u3042\"}]}";
  private static final int COUNT = 10000;

  private static void awaitConnection() throws Exception {
    System.in.read();
  }

  public static void testFastJSON() {
    // Warm-up
    com.alibaba.fastjson.JSON.parseObject(TEST_1);

    long t1 = 0;
    for (int i = COUNT; i != 0; --i) {
      var b1 = System.nanoTime();
      com.alibaba.fastjson.JSON.parseObject(TEST_2);
      var e1 = System.nanoTime();
      t1 += e1 - b1;
    }
    System.out.println(TimeUnit.NANOSECONDS.toMillis(t1));
  }

  public static void testMilktea() {
    // Warm-up
    milktea.json.JSON.parseObject(TEST_1);

    long t2 = 0;
    for (int i = COUNT; i != 0; --i) {
      var b2 = System.nanoTime();
      milktea.json.JSON.parseObject(TEST_2);
      var e2 = System.nanoTime();
      t2 += e2 - b2;
    }
    System.out.println(TimeUnit.NANOSECONDS.toMillis(t2));
  }

  public static void main(String[] args) throws Exception {
    awaitConnection();
    testFastJSON();
    testMilktea();
  }

}
