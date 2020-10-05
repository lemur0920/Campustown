package egovframework.com.campustown.counsel.make_counsel.domain;

public class MakeCounselCodeValue {

    private String codeName;
    private String codeValue;

    public MakeCounselCodeValue(MakeCounselCode makeCounselCode) {
        this.codeName = makeCounselCode.getCodeName();
        this.codeValue = makeCounselCode.getCodeValue();
    }

    public String getCodeName() {

        return codeName;
    }

    public String getCodeValue() {
        return codeValue;
    }

    @Override
    public String toString() {
        return "MakeCounselCode{" +
                "codeName='" + codeName + '\'' +
                ", codeValue='" + codeValue + '\'' +
                '}';
    }
}
