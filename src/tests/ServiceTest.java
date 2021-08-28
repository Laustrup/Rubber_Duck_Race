package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import services.Analyst;
import services.GameService;
import services.Printer;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private GameService service;
    private Analyst analyst;
    private Printer printer;

    @BeforeEach
    public void setup() {
        service = new GameService();
        analyst = new Analyst();
        printer = new Printer();
    }

    @ParameterizedTest
    @CsvSource(value = {"1|true","0|false","100|true","101|false","asdaf|false"},delimiter = '|')
    public void analyseChosenDuckIdTest(String typed, boolean expected) {
        // Act
        boolean actual = analyst.analyseChosenDuckId(typed);

        // Assert
        assertEquals(actual,expected);
    }

}