package egovframework.com.campustown.counsel.make_counsel.domain;

public class MakeCounselCodeType {

    public static enum MakeCounselIdea implements MakeCounselCode {
        A1("기본 컨셉 도출","0"),
        A2("기술 구현 가능 여부","1");

        private String ideaCodeName;
        private String ideaCodeValue;

        MakeCounselIdea(String ideaCodeName, String ideaCodeValue) {
            this.ideaCodeName = ideaCodeName;
            this.ideaCodeValue = ideaCodeValue;
        }
        @Override
        public String getCodeName() {
            return ideaCodeName;
        }

        @Override
        public String getCodeValue() {
            return ideaCodeValue;
        }
    }

    public static enum MakeCounselProductDevelopment implements MakeCounselCode {
        DESIGN("디자인","0"),
        CIRCUIT_DESIGN("회로설계","1"),
        MECHANISM_DESIGN("기구설계","2"),
        MACHINE_DESIGN("기계설계","3");

        private String productDevelopmentCodeName;
        private String productDevelopmentCodeValue;

        MakeCounselProductDevelopment(String productDevelopmentCodeName, String productDevelopmentCodeValue) {
            this.productDevelopmentCodeName = productDevelopmentCodeName;
            this.productDevelopmentCodeValue = productDevelopmentCodeValue;
        }


        @Override
        public String getCodeName() {
            return productDevelopmentCodeName;
        }

        @Override
        public String getCodeValue() {
            return productDevelopmentCodeValue;
        }
    }

    public static enum MakeCounselPrototypeProduct implements MakeCounselCode {
        THREE_D_PRINTER("3D프린터","0"),
        DESIGN_MOCKUP("디자인목업","1"),
        WORKING_MOCKUP("워킹목업","2"),
        MACHINE_DESIGN("기계설계","3");

        private String prototypeProductCodeName;
        private String prototypeProductCodeValue;

        MakeCounselPrototypeProduct(String prototypeProductCodeName, String prototypeProductCodeValue) {
            this.prototypeProductCodeName = prototypeProductCodeName;
            this.prototypeProductCodeValue = prototypeProductCodeValue;
        }


        @Override
        public String getCodeName() {
            return prototypeProductCodeName;
        }

        @Override
        public String getCodeValue() {
            return prototypeProductCodeValue;
        }
    }


}
