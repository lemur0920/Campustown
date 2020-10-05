package egovframework.com.campustown.counsel.legal_counsel.funtion;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * The type Iter.
 */
public abstract class  Iter {

    /**
     * The enum Map data id.
     */
    public enum TypeData {
        LAWYERASSIGNSAVE("변호사등록", Arrays.asList("id","lawyerId")),
        STARTUP("창업팀등록", Arrays.asList("title","realm","contents"))
        ;

        private String name;
        private List<String> valueList;

        TypeData(String name, List<String> valueList) {
            this.name = name;
            this.valueList = valueList;
        }

        /**
         * Find byvalue data list list.
         *
         * @param name the name
         * @return the list
         */
        public static List findByvalueDataList(String name){
            List<TypeData> list = new ArrayList();
            for (TypeData value : TypeData.values()) {
                System.out.println("value:::::"+value);
                System.out.println("value:::::"+TypeData.valueOf(String.valueOf(value)));
            }
            return list;
        }
    }

    /**
     * Enum iter.
     *
     * @param TypeData the map data
     * @param req     the req
     * @param typeName
     */
    public static void enumIter(HttpServletRequest req, String typeName) {
        System.out.println("typeName:::::::"+typeName);
        Enumeration<String> e = req.getParameterNames();
        while (e.hasMoreElements()){
            String name = e.nextElement();
            String[] values = req.getParameterValues(name);
            for (String value : values) {
                for (TypeData s : TypeData.values()) {
                    System.out.println("s::::::::::"+s.name);
                    if(s.name.equals(typeName)){
                        List<String> valueList = s.valueList;
                        for (String s1 : valueList) {
                            System.out.println("value:::::"+s1);
                        }
                    }
                    //findByvalueDataList(String.valueOf(s));


                }
            }
        }
    }
}
