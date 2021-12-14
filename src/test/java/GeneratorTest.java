import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GeneratorTest {
    final static String INPUT_FIRST_ISIN = "US90284B9624";
    Generator generator;

    @BeforeAll
    public void setUp(){
        generator = new Generator();
    }

    @Test
    void getCusipFromIsin() {
        String actual = generator.getCusipFromIsin(INPUT_FIRST_ISIN);

        Assertions.assertEquals("90284B962", actual);
    }

    @Test
    void generateNextAlphanumericNumber(){
        String actual = generator.generateNextAlphanumericNumber("90284B954");

        Assertions.assertEquals("90284B955", actual);
    }

    @Test
    void generateNextCusipLast2(){
        String actual = generator.generateNextAlphanumericNumber("90284B962");

        Assertions.assertEquals("90284B963", actual);
    }

    @Test
    void generateNextCusipLastA(){
        String actual = generator.generateNextAlphanumericNumber("1DDA01A");

        Assertions.assertEquals("1DDA01B", actual);
    }

    @Test
    void generateNextCusipLastZ(){
        String actual = generator.generateNextAlphanumericNumber("1DDA01Z");

        Assertions.assertEquals("1DDA02A", actual);
    }

    @Test
    void generateNextCusipLast9(){
        String actual = generator.generateNextAlphanumericNumber("1DDA019");

        Assertions.assertEquals("1DDA020", actual);
    }

    @Test
    void checkDigitCusipTest1(){
        String actual = generator.generateCusipCheckDigit("925524BF");

        Assertions.assertEquals("6", actual);
    }

    @Test
    void checkDigitCusipTest2(){
        String actual = generator.generateCusipCheckDigit("90284B96");

        Assertions.assertEquals("2", actual);
    }

    @Test
    void checkDigitCusipTest3(){
        String actual = generator.generateCusipCheckDigit("90284B97");

        Assertions.assertEquals("0", actual);
    }

    @Test
    void checkDigitIsinTest1(){
        String actual = generator.generateIsinCheckDigit("US90284B962");

        Assertions.assertEquals("4", actual);
    }

    @Test
    void checkDigitIsinTest2(){
        String actual = generator.generateIsinCheckDigit("US90284B970");

        Assertions.assertEquals("7", actual);
    }

    @Test
    void checkDigitIsinTest3(){
        String actual = generator.generateIsinCheckDigit("US90284B988");

        Assertions.assertEquals("9", actual);
    }

    @Test
    void checkDigitIsinTest4(){
        String actual = generator.generateIsinCheckDigit("US90284B897");

        Assertions.assertEquals("2", actual);
    }

    @Test
    void checkDigitIsinTestWalmart(){
        String actual = generator.generateIsinCheckDigit("US931142103");

        Assertions.assertEquals("9", actual);
    }

    @Test
    void checkDigitIsinTestTreasuryCorporationOfVictoria(){
        String actual = generator.generateIsinCheckDigit("AU0000XVGZA");

        Assertions.assertEquals("3", actual);
    }

    @Test
    void checkDigitIsinTestTreasuryCorporationOfApple(){
        String actual = generator.generateIsinCheckDigit("US037833100");

        Assertions.assertEquals("5", actual);
    }

    @Test
    void generatorTest0(){
        String actual = generator.generate("US90284B9475");

        Assertions.assertEquals("US90284B9541", actual);
    }

    @Test
    void generatorTest1(){
        String actual = generator.generate("US90284B8972");

        Assertions.assertEquals("US90284B9053", actual);
    }
}