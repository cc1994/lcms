package lcms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.ListSelectionEvent;

public class interview {
//    "harry potter site:amazon.com year:(2003 2005)"
//        keywords: ["harry", "potter"],
//
//        filters: {
//
//            site: "amazon.com",
//
//            year: [2003, 2005]
//
//        }
//
//    }
    public static void main(String[] args) {
 
    }
    public static void foo(String input) {
        List<String> keywords = new ArrayList<String>();
        Map<String, List<String>> filterMap = new HashMap<>();
        String[] block = input.split(" ");
        int i = 0;
        while (i < block.length) {
            // normal keywords
            if(!block[i].contains(":") && !block[i].contains("(") && !block[i].contains(")")) {
                keywords.add(block[i]);
            }
            // key:value 多个冒号,只取1st作为有效分隔符
            else if (block[i].contains(":") && !block[i].contains("(")) {
                int firstIndex = block[i].indexOf(':');
                filterMap.put(block[i].substring(0, firstIndex), Arrays.asList(block[i].substring(firstIndex + 1)));
            }
            // key:(value 多个左括号，只取第一个作为有效分隔符
            // key:(value) 多个右括号，取最后一个作为有效结束符
            else if (block[i].contains(":(") ) {
                int firstIndex = block[i].indexOf(":(");
                String key = block[i].substring(0, firstIndex);
                String value = block[i].substring(firstIndex + 2);
                if (value.contains(")")) {
                    filterMap.put(key, Arrays.asList(value));
                }
            }
            // value)
            
            i++;
        }
    }
}
