import duke.exception.DukeException;
import duke.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    public void testConvertStringToDate() {
        String testInput = "2/10/2019 1345";
        String testInput2 = "4/5/2000 0942";
        String testOutput = null;
        try {
            testOutput = Parser.accessConvertStringToDate(testInput);
        } catch (DukeException e) {
            e.printStackTrace();
        }
        String testOutput2 = null;
        try {
            testOutput2 = Parser.accessConvertStringToDate(testInput2);
        } catch (DukeException e) {
            e.printStackTrace();
        }
        assertEquals("2nd of October 2019, 145pm", testOutput);
        assertEquals("4th of May 2000, 942am", testOutput2);
    }

    @Test
    public void testConvertTime() {
        int testInput = 1643;
        try {
            String testOutput = Parser.accessConvertTime(testInput);
            assertEquals("443pm", testOutput);
        } catch (DukeException e) {
            System.out.println(e);
        }
    }

}
