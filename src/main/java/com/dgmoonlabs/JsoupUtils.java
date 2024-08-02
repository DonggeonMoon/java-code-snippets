package src.main.java.com.dgmoonlabs;

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
    static String url;
    static String query;
    static HashMap<String, String> map;
    static Document doc = null;

    public JsoupUtils() {}



    public static class SingleElement {
        /**
         * SingleElement는 웹사이트에서 하나의 요소만 가져올 때 사용하는 내부 클래스.
         * @param url
         * @param query
         */
        public SingleElement(String url, String query) {
            JsoupUtils.url = url;
            JsoupUtils.query = query;

            try {
                doc = Jsoup.connect(url).
                        userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 11_0_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36").
                        referrer("https://www.google.com").
                        get();
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

    }

    public static class MultipleElements {
        /**
         * MultipleElements는 웹사이트에서 여러 개의 요소를 한꺼번에 가져올 때 사용하는 내부 클래스.
         * @param url
         * @param map
         */
        public MultipleElements(String url, HashMap<String, String> map) {
            JsoupUtils.url = url;
            JsoupUtils.map = map;

            try {
                doc = Jsoup.connect(url).
                        userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 11_0_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36").
                        referrer("https://www.google.com").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
