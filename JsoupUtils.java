package egovframework.com.vietnam.crawler.service.JsoupUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author moondg
 * @since 2022
 * @
 */

public class JsoupUtils {
    private String url;
    private String query;
    private HashMap<String, String> map;
    Document doc = null;

    public JsoupUtils() {}

    public JsoupUtils(String url, String query) {
        this.url = url;
        this.query = query;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsoupUtils(String url, HashMap<String, String> map) {
        this.url = url;
        this.map = map;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * CSS 선택자로 선택한 요소의 텍스트를 가져온다.
     * @return
     * @throws NullPointerException
     */
    public String getText() throws NullPointerException {
        return doc.select(query).text();
    }

    /**
     * CSS 선택자로 선택한 요소의 속성을 가져온다.
     * @param attributeKey
     * @return
     * @throws NullPointerException
     */
    public String getAttr(String attributeKey) throws NullPointerException {
        return doc.select(query).attr(attributeKey);
    }

    /**
     * CSS 선택자로 선택한 요소의 텍스트들을 맵으로 가져온다.
     * @return
     */
    public HashMap<String, String> getTextMap() {
        HashMap<String, String> newMap = new HashMap<String, String>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            try {
                newMap.put(key, doc.select(value).text());
            } catch (Selector.SelectorParseException spe) {
                spe.printStackTrace();
            }
        }
        return newMap;
    }

    /**
     * CSS 선택자로 선택한 요소의 속성값들을 맵으로 가져온다.
     * @param attributeKey
     * @return
     */
    public HashMap<String, String> getAttrMap(String attributeKey) {
        HashMap<String, String> newMap = new HashMap<String, String>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            try {
                newMap.put(key, doc.select(value).attr(attributeKey));
            } catch (Selector.SelectorParseException spe) {
                spe.printStackTrace();
            }
        }
        return newMap;
    }

    /**
     *
     */
    public static class NoSuchElementException extends Exception {
        public NoSuchElementException(String msg, Object... params) {
            super(String.format(msg, params));
        }
    }
}
