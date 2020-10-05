package egovframework.com.campustown.counsel.legal_counsel.domain;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    A("법률자문신청",0),
    B("대학반려",1),
    C("서울시승인대기",2),
    D("서울시승인반려",3),
    E("담당변호사배정",4),
    F("자문완료",5),
    G("취소",6)
    ;

    private String statusCodeName;
    private int statusCodeVale;

    Status(String statusCodeName, int statusCodeVale) {
        this.statusCodeName = statusCodeName;
        this.statusCodeVale = statusCodeVale;
    }

    public static String findByCodeName(String value){
        String statusCodeName = "";
        for (Status s: Status.values()) {
            if (s.equals(Status.valueOf(String.valueOf(s)))){
                if (s.statusCodeVale == Integer.parseInt(value)){
                    statusCodeName = s.statusCodeName;
                }
            }
        }
        return statusCodeName;
    }

    public static int findByCodeValue(String key){
        int statusCodeValue = 0 ;
        for (Status s: Status.values()) {
            if (key.equals(Status.valueOf(key))){
                statusCodeValue = s.statusCodeVale;
            }
        }
        return statusCodeValue;
    }

    public static Map<String,Integer> findForCodeValue(){
        Map<String,Integer> codeData = new HashMap<>();
        for (Status s : Status.values()){
            codeData.put(s.statusCodeName,s.statusCodeVale);
        }
        return codeData;
    }

}
