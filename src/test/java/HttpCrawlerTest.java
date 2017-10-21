import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.webcrawler.parser.HttpCrawler;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class HttpCrawlerTest {

    @Parameterized.Parameter(0)
    public List<String> selectorsContent;

    @Parameterized.Parameter(1)
    public String wanted;

    @Parameterized.Parameter(2)
    public boolean expected;

    @Parameterized.Parameter(3)
    public String errorDescription;


    @Parameterized.Parameters(name="{3}")
    public static Collection<Object[]> dataProvider() {

        List<String> selectorsContent = Arrays.asList("Hallo", "TSCHÜSS", "konichiva");

        Object[][] data = new Object[][] {
                { selectorsContent, "Hallo", true, "Exact value"},
                { selectorsContent, "all", true, "Wanted is part of the actual string"},
                { selectorsContent, "HALLO", true, "Upper cased value"},
                { selectorsContent, "hallo", true, "Lower case value"},
                { selectorsContent, "nein", false, "no match"  },
                { selectorsContent, "KONICHIVa", true, "Matching of the nth element" },
                { selectorsContent, "tschüss", true, "Non unicode symbols" },

        };
        List<Object[]>result = Arrays.asList(data);
        return result;
    }

    @Test
    public void testIfContentFound()
    {
        HttpCrawler crawler = new HttpCrawler();
        boolean result = crawler.ifContentFound(selectorsContent, wanted);
        assertEquals(errorDescription, expected, result);

    }
}
