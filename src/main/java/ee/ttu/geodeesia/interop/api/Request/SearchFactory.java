package ee.ttu.geodeesia.interop.api.Request;

import java.util.Map;

/**
 * Created by 48707222248 on 21.02.2017.
 */
public class SearchFactory {
    public static AbstractSearch create(String searchName) {
        AbstractSearch search = null;
        switch (searchName) {
            case "speciment":
                search = new SpecimentSearch(searchName);
                break;
            case "sample":
                search = new SampleSearch(searchName);
                break;
            default: break;
        }

        return search;
    }
}
