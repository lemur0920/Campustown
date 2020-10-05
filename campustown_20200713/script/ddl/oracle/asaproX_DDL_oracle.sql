--------------------------------------------------------
--  DDL for Table ASA_CODE_CATEGORY
--------------------------------------------------------
CREATE TABLE ASA_CODE_CATEGORY (
  CAT_ID VARCHAR2(30) DEFAULT '',
  CAT_NAME VARCHAR2(50 CHAR) DEFAULT NULL,
  CAT_NAME_EN VARCHAR2(100) DEFAULT '',
  CAT_DESCRIPTION VARCHAR2(1000 CHAR) DEFAULT NULL,
  CAT_REGDATE DATE DEFAULT SYSDATE,
  PRIMARY KEY (CAT_ID)
) ;

   COMMENT ON COLUMN ASA_CODE_CATEGORY.CAT_ID IS '코드 분류 아이디';
 
   COMMENT ON COLUMN ASA_CODE_CATEGORY.CAT_NAME IS '코드 분류 이름';
 
   COMMENT ON COLUMN ASA_CODE_CATEGORY.CAT_NAME_EN IS '코드 분류 영문 이름';
 
   COMMENT ON COLUMN ASA_CODE_CATEGORY.CAT_DESCRIPTION IS '코드 분류 설명';
 
   COMMENT ON COLUMN ASA_CODE_CATEGORY.CAT_REGDATE IS '등록일시';
 
   COMMENT ON TABLE ASA_CODE_CATEGORY  IS '코드분류';


--------------------------------------------------------------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ASA_CODE
--------------------------------------------------------
CREATE TABLE ASA_CODE (
  CODE_ID VARCHAR2(30) DEFAULT '',
  CAT_ID VARCHAR2(30) DEFAULT '',
  CODE_NAME VARCHAR2(100 CHAR) DEFAULT NULL,
  CODE_NAME_EN VARCHAR2(100) DEFAULT '',
  CODE_DESCRIPTION VARCHAR2(1000 CHAR) DEFAULT NULL,
  CODE_USE NUMBER DEFAULT '1',
  CODE_REGDATE DATE DEFAULT NULL,
  CODE_ORDER NUMBER DEFAULT 1,
  PRIMARY KEY (CODE_ID,CAT_ID)
) ;

   COMMENT ON COLUMN ASA_CODE.CODE_ID IS '코드 아이디';
 
   COMMENT ON COLUMN ASA_CODE.CAT_ID IS '코드 분류 아이디';
 
   COMMENT ON COLUMN ASA_CODE.CODE_NAME IS '코드 이름';
 
   COMMENT ON COLUMN ASA_CODE.CODE_NAME_EN IS '코드 영문 이름';
 
   COMMENT ON COLUMN ASA_CODE.CODE_DESCRIPTION IS '코드 설명';
 
   COMMENT ON COLUMN ASA_CODE.CODE_USE IS '사용여부';
 
   COMMENT ON COLUMN ASA_CODE.CODE_REGDATE IS '등록일시';
   
   COMMENT ON COLUMN ASA_CODE.CODE_ORDER IS '순서';
 
   COMMENT ON TABLE ASA_CODE  IS '코드아이템';


--------------------------------------------------------------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ASA_ADMIN_MEMBER
--------------------------------------------------------
CREATE TABLE ASA_ADMIN_MEMBER (	
	ADMIN_ID VARCHAR2(50), 
	ADMIN_PASSWORD VARCHAR2(200), 
	ADMIN_NAME VARCHAR2(200), 
	ADMIN_SEX VARCHAR2(10), 
	ADMIN_EMAIL VARCHAR2(200), 
	ADMIN_TEL1 VARCHAR2(100), 
	ADMIN_TEL2 VARCHAR2(100), 
	ADMIN_TEL3 VARCHAR2(100), 
	ADMIN_MOBILE1 VARCHAR2(100), 
	ADMIN_MOBILE2 VARCHAR2(100), 
	ADMIN_MOBILE3 VARCHAR2(100), 
	ADMIN_FAX VARCHAR2(50), 
	ADMIN_ZIPCODE VARCHAR2(100), 
	ADMIN_ADDRESS VARCHAR2(200), 
	ADMIN_ADDRESS_DETAIL VARCHAR2(200), 
	ADMIN_REGDATE DATE, 
	ADMIN_ACTIVE NUMBER DEFAULT 1, 
	ADMIN_LOGIN_LAST_DATE DATE, 
	ADMIN_PW_LAST_UPDATE DATE, 
	ADMIN_ORGANIZATION VARCHAR2(100), 
	ADMIN_DEPARTMENT VARCHAR2(100), 
	ADMIN_POSITION VARCHAR2(100), 
	ADMIN_ROLE VARCHAR2(100),
	ADMIN_LOCK NUMBER DEFAULT 0, 
	ADMIN_LOGIN_FAIL_COUNT NUMBER DEFAULT 0, 
	ADMIN_LOGIN_FAIL_DATE DATE
) ;
 

   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_ID IS '관리자 아이디';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_PASSWORD IS '로그인비밀번호';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_NAME IS '관리자 성명';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_SEX IS '관리자 성별(코드)';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_EMAIL IS '관리자 이메일';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_TEL1 IS '관리자 전화번호1';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_TEL2 IS '관리자 전화번호2';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_TEL3 IS '관리자 전화번호3';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_MOBILE1 IS '관리자 휴대폰번호1';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_MOBILE2 IS '관리자 휴대폰번호2';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_MOBILE3 IS '관리자 휴대폰번호3';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_FAX IS '관리자 팩스번호';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_ZIPCODE IS '관리자 우편번호';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_ADDRESS IS '관리자 주소';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_ADDRESS_DETAIL IS '관리자 상세주소';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_REGDATE IS '가입일시';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_ACTIVE IS '관리자 상태';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_LOGIN_LAST_DATE IS '마지막 로그인 일시';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_PW_LAST_UPDATE IS '비밀번호 마지막 수정일시';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_ORGANIZATION IS '소속 기관명';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_DEPARTMENT IS '소속 부서명';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_POSITION IS '직급명';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_ROLE IS '역할';
    
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_LOCK IS '로그인 제한 여부';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_LOGIN_FAIL_COUNT IS '로그인 실패 횟수';
 
   COMMENT ON COLUMN ASA_ADMIN_MEMBER.ADMIN_LOGIN_FAIL_DATE IS '로그인 마지막 실패 일시';
 
   COMMENT ON TABLE ASA_ADMIN_MEMBER  IS '관리자 정보' ;

--------------------------------------------------------
--  DDL for Index ASA_ADMIN_MEMBER_PK
--------------------------------------------------------
  CREATE UNIQUE INDEX ASA_ADMIN_MEMBER_PK ON ASA_ADMIN_MEMBER (ADMIN_ID);
--------------------------------------------------------
--  Constraints for Table ASA_ADMIN_MEMBER
--------------------------------------------------------
  ALTER TABLE ASA_ADMIN_MEMBER ADD CONSTRAINT ASA_ADMIN_MEMBER_PK PRIMARY KEY (ADMIN_ID) ENABLE;
 
  ALTER TABLE ASA_ADMIN_MEMBER MODIFY (ADMIN_ID NOT NULL ENABLE);
 
  ALTER TABLE ASA_ADMIN_MEMBER MODIFY (ADMIN_PASSWORD NOT NULL ENABLE);
 
  ALTER TABLE ASA_ADMIN_MEMBER MODIFY (ADMIN_ACTIVE NOT NULL ENABLE);
  
  
--------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_ORG_ORGANIZATION
--------------------------------------------------------

  CREATE TABLE "ASA_ORG_ORGANIZATION" 
   (	"ORG_ID" VARCHAR2(50), 
	"ORG_NAME" VARCHAR2(100), 
	"ORG_NAME_EN" VARCHAR2(100), 
	"ORG_DESCRIPTION" VARCHAR2(1000), 
	"ORG_USE" NUMBER DEFAULT 1, 
	"ORG_REGDATE" DATE,
	"ORG_ORDER" NUMBER
   ) ;
 

   COMMENT ON COLUMN "ASA_ORG_ORGANIZATION"."ORG_ID" IS '기관코드';
 
   COMMENT ON COLUMN "ASA_ORG_ORGANIZATION"."ORG_NAME" IS '기관명';
 
   COMMENT ON COLUMN "ASA_ORG_ORGANIZATION"."ORG_NAME_EN" IS '기관명(영문)';
 
   COMMENT ON COLUMN "ASA_ORG_ORGANIZATION"."ORG_DESCRIPTION" IS '기관 소개';
 
   COMMENT ON COLUMN "ASA_ORG_ORGANIZATION"."ORG_USE" IS '사용유무';
 
   COMMENT ON COLUMN "ASA_ORG_ORGANIZATION"."ORG_REGDATE" IS '등록일';

   COMMENT ON COLUMN "ASA_ORG_ORGANIZATION"."ORG_ORDER" IS '순서';
   
   COMMENT ON TABLE "ASA_ORG_ORGANIZATION"  IS '기관/조직';

--------------------------------------------------------
--  DDL for Index ASA_ORG_ORGANIZATION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_ORG_ORGANIZATION_PK" ON "ASA_ORG_ORGANIZATION" ("ORG_ID");
--------------------------------------------------------
--  Constraints for Table ASA_ORG_ORGANIZATION
--------------------------------------------------------

  ALTER TABLE "ASA_ORG_ORGANIZATION" ADD CONSTRAINT "ASA_ORG_ORGANIZATION_PK" PRIMARY KEY ("ORG_ID") ENABLE;
 
  ALTER TABLE "ASA_ORG_ORGANIZATION" MODIFY ("ORG_ID" NOT NULL ENABLE)  ;
  
  
  
--------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_ORG_DEPARTMENT
--------------------------------------------------------

  CREATE TABLE "ASA_ORG_DEPARTMENT" 
   (	"DEP_ID" VARCHAR2(50), 
	"ORG_ID" VARCHAR2(50), 
	"DEP_NAME" VARCHAR2(100), 
	"DEP_NAME_EN" VARCHAR2(100), 
	"DEP_TEL" VARCHAR2(100), 
	"DEP_DESCRIPTION" VARCHAR2(2000), 
	"DEP_USE" NUMBER DEFAULT 1, 
	"DEP_REGDATE" DATE, 
	"DEP_ORDER" NUMBER
   ) ;
 

   COMMENT ON COLUMN "ASA_ORG_DEPARTMENT"."DEP_ID" IS '부서코드';
 
   COMMENT ON COLUMN "ASA_ORG_DEPARTMENT"."ORG_ID" IS '기관코드';
 
   COMMENT ON COLUMN "ASA_ORG_DEPARTMENT"."DEP_NAME" IS '부서명';
 
   COMMENT ON COLUMN "ASA_ORG_DEPARTMENT"."DEP_NAME_EN" IS '부서명(영문)';
 
   COMMENT ON COLUMN "ASA_ORG_DEPARTMENT"."DEP_TEL" IS '부서 전화번호';
 
   COMMENT ON COLUMN "ASA_ORG_DEPARTMENT"."DEP_DESCRIPTION" IS '부서 소개';
 
   COMMENT ON COLUMN "ASA_ORG_DEPARTMENT"."DEP_USE" IS '사용유무';
 
   COMMENT ON COLUMN "ASA_ORG_DEPARTMENT"."DEP_REGDATE" IS '등록일';
 
   COMMENT ON COLUMN "ASA_ORG_DEPARTMENT"."DEP_ORDER" IS '순서';
 
   COMMENT ON TABLE "ASA_ORG_DEPARTMENT"  IS '부서';

--------------------------------------------------------
--  DDL for Index ASA_ORG_DEPARTMENT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_ORG_DEPARTMENT_PK" ON "ASA_ORG_DEPARTMENT" ("DEP_ID");
--------------------------------------------------------
--  Constraints for Table ASA_ORG_DEPARTMENT
--------------------------------------------------------

  ALTER TABLE "ASA_ORG_DEPARTMENT" ADD CONSTRAINT "ASA_ORG_DEPARTMENT_PK" PRIMARY KEY ("DEP_ID") ENABLE;
 
  ALTER TABLE "ASA_ORG_DEPARTMENT" MODIFY ("DEP_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ASA_ORG_DEPARTMENT
--------------------------------------------------------

  ALTER TABLE "ASA_ORG_DEPARTMENT" ADD CONSTRAINT "ASA_ORG_DEPARTMENT_FK1" FOREIGN KEY ("ORG_ID")
	  REFERENCES "ASA_ORG_ORGANIZATION" ("ORG_ID") ON DELETE CASCADE ENABLE;
  
  
--------------------------------------------------------------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ASA_CAPABILITY
--------------------------------------------------------

  CREATE TABLE "ASA_CAPABILITY" 
   (	"CAP_ID" VARCHAR2(100), 
	"CAP_NAME" VARCHAR2(100), 
	"CAP_DESCRIPTION" VARCHAR2(1000), 
	"CAP_VALUE" VARCHAR2(255), 
	"CAP_PARAM1_KEY" VARCHAR2(50), 
	"CAP_PARAM1_VALUE" VARCHAR2(255), 
	"CAP_PARAM2_KEY" VARCHAR2(50), 
	"CAP_PARAM2_VALUE" VARCHAR2(255), 
	"CAP_HTTP_METHOD" VARCHAR2(10), 
	"CAP_REGDATE" DATE, 
	"CAP_DEFAULT" NUMBER DEFAULT 0, 
	"CAP_PRIORITY" NUMBER, 
	"CAP_HIDDEN" NUMBER
   ) ;
 

   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_ID" IS '권한 아이디';
 
   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_NAME" IS '권한 이름';
 
   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_DESCRIPTION" IS '권한 설명';
 
   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_VALUE" IS '권한 값';
 
   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_PARAM1_KEY" IS '추가 파라메터1 키';
 
   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_PARAM1_VALUE" IS '추가 파라메터1 값';
 
   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_PARAM2_KEY" IS '추가 파라메터2 키';
 
   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_PARAM2_VALUE" IS '추가 파라메터2 값';
 
   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_HTTP_METHOD" IS 'HTTP 메소드';
 
   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_DEFAULT" IS '시스템기본권한 여부';
 
   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_PRIORITY" IS '우선순위';
 
   COMMENT ON COLUMN "ASA_CAPABILITY"."CAP_HIDDEN" IS '숨김여부';
 
   COMMENT ON TABLE "ASA_CAPABILITY"  IS '권한';

--------------------------------------------------------
--  DDL for Index ASA_CAPABILITY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_CAPABILITY_PK" ON "ASA_CAPABILITY" ("CAP_ID");
--------------------------------------------------------
--  Constraints for Table ASA_CAPABILITY
--------------------------------------------------------

  ALTER TABLE "ASA_CAPABILITY" ADD CONSTRAINT "ASA_CAPABILITY_PK" PRIMARY KEY ("CAP_ID") ENABLE;
 
  ALTER TABLE "ASA_CAPABILITY" MODIFY ("CAP_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_CAPABILITY" MODIFY ("CAP_DEFAULT" NOT NULL ENABLE);
	  
  
----------------------------------------------------------------------------------------------------------------	  
--------------------------------------------------------
--  DDL for Table ASA_ROLE
--------------------------------------------------------

  CREATE TABLE "ASA_ROLE" 
   (	"ROLE_CODE" VARCHAR2(100), 
	"ROLE_NAME" VARCHAR2(100), 
	"ROLE_DESCRIPTION" VARCHAR2(1000), 
	"ROLE_REGDATE" DATE, 
	"ROLE_DEFAULT" NUMBER DEFAULT 0, 
	"ROLE_ADMIN" NUMBER DEFAULT 0, 
	"ROLE_JOIN" NUMBER DEFAULT 0
   ) ;
 

   COMMENT ON COLUMN "ASA_ROLE"."ROLE_CODE" IS '역할 코드';
 
   COMMENT ON COLUMN "ASA_ROLE"."ROLE_NAME" IS '역할 이름';
 
   COMMENT ON COLUMN "ASA_ROLE"."ROLE_DESCRIPTION" IS '역할 설명';
 
   COMMENT ON COLUMN "ASA_ROLE"."ROLE_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_ROLE"."ROLE_DEFAULT" IS '기본역할 여부';
 
   COMMENT ON COLUMN "ASA_ROLE"."ROLE_ADMIN" IS '최고관리자 역할 여부';
 
   COMMENT ON COLUMN "ASA_ROLE"."ROLE_JOIN" IS '가입시 역할 여부';
 
   COMMENT ON TABLE "ASA_ROLE"  IS '역할'	;
   
--------------------------------------------------------
--  DDL for Index ASA_ROLE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_ROLE_PK" ON "ASA_ROLE" ("ROLE_CODE");
--------------------------------------------------------
--  Constraints for Table ASA_ROLE
--------------------------------------------------------

  ALTER TABLE "ASA_ROLE" ADD CONSTRAINT "ASA_ROLE_PK" PRIMARY KEY ("ROLE_CODE") ENABLE;
 
  ALTER TABLE "ASA_ROLE" MODIFY ("ROLE_CODE" NOT NULL ENABLE);  
	  

----------------------------------------------------------------------------------------------------------------	  
--------------------------------------------------------
--  DDL for Table ASA_ROLE_CAP
--------------------------------------------------------

  CREATE TABLE "ASA_ROLE_CAP" 
   (	"ROLE_CODE" VARCHAR2(100), 
	"CAP_ID" VARCHAR2(100)
   ) ;
 

   COMMENT ON COLUMN "ASA_ROLE_CAP"."ROLE_CODE" IS '역할 코드';
 
   COMMENT ON COLUMN "ASA_ROLE_CAP"."CAP_ID" IS '권한 아이디';
 
   COMMENT ON TABLE "ASA_ROLE_CAP"  IS '역할 - 권한 릴레이션';  
   
--------------------------------------------------------
--  DDL for Index ASA__PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA__PK" ON "ASA_ROLE_CAP" ("ROLE_CODE", "CAP_ID");
--------------------------------------------------------
--  Constraints for Table ASA_ROLE_CAP
--------------------------------------------------------

  ALTER TABLE "ASA_ROLE_CAP" ADD CONSTRAINT "ASA__PK" PRIMARY KEY ("ROLE_CODE", "CAP_ID") ENABLE;
 
  ALTER TABLE "ASA_ROLE_CAP" MODIFY ("ROLE_CODE" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_ROLE_CAP" MODIFY ("CAP_ID" NOT NULL ENABLE);
   
   
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------   
--------------------------------------------------------
--  DDL for Table ASA_BOARD_CONFIG
--------------------------------------------------------

  CREATE TABLE "ASA_BOARD_CONFIG" 
   (	"BC_ID" VARCHAR2(100), 
	"BC_NAME" VARCHAR2(100), 
	"BC_USE" NUMBER DEFAULT 1, 
	"BC_TYPE" VARCHAR2(50), 
	"BC_CATEGORY1" VARCHAR2(50), 
	"BC_CATEGORY1_NAME" VARCHAR2(100), 
	"BC_CATEGORY2" VARCHAR2(50), 
	"BC_CATEGORY2_NAME" VARCHAR2(100), 
	"BC_CATEGORY3" VARCHAR2(50), 
	"BC_CATEGORY3_NAME" VARCHAR2(100), 
	"BC_STATUS_CODE" VARCHAR2(500), 
	"BC_LIST_FILE" VARCHAR2(500), 
	"BC_VIEW_FILE" VARCHAR2(500), 
	"BC_FORM_FILE" VARCHAR2(500), 
	"BC_DEPARTMENT" VARCHAR2(4000), 
	"BC_GROUP" VARCHAR2(50), 
	"BC_ALLOW_MEMBER_LIST" NUMBER DEFAULT 1, 
	"BC_ALLOW_MEMBER_VIEW" NUMBER DEFAULT 1, 
	"BC_ALLOW_MEMBER_FORM" NUMBER DEFAULT 1, 
	"BC_ALLOW_MEMBER_DOWNLOAD" NUMBER DEFAULT 1, 
	"BC_ALLOW_GUEST_LIST" NUMBER DEFAULT 1, 
	"BC_ALLOW_GUEST_VIEW" NUMBER DEFAULT 1, 
	"BC_ALLOW_GUEST_FORM" NUMBER DEFAULT 1, 
	"BC_ALLOW_GUEST_DOWNLOAD" NUMBER DEFAULT 1, 
	"BC_SUPPORT_NOTICE" NUMBER DEFAULT 1, 
	"BC_NOTICE_EVERY_PAGE" NUMBER DEFAULT 0, 
	"BC_SUPPORT_SECRET" NUMBER DEFAULT 1, 
	"BC_SUPPORT_COMMENT" NUMBER DEFAULT 1, 
	"BC_SUPPORT_ANSWER" NUMBER DEFAULT 0, 
	"BC_SUPPORT_RECOMMEND" NUMBER DEFAULT 0, 
	"BC_SUPPORT_THUMBNAIL" NUMBER DEFAULT 1, 
	"BC_THUMBNAIL_CROP" NUMBER DEFAULT 0, 
	"BC_THUMBNAIL_WIDTH" NUMBER DEFAULT 200, 
	"BC_THUMBNAIL_HEIGHT" NUMBER DEFAULT 150, 
	"BC_UPLOAD_FILE_NUM" NUMBER DEFAULT 5, 
	"BC_UPLOAD_SIZE_MAX" NUMBER DEFAULT 10, 
	"BC_PAGE_SIZE" NUMBER DEFAULT 10, 
	"BC_REGDATE" DATE, 
	"BC_ORGANIZATION" VARCHAR2(50), 
	"BC_SUPPORT_NURI" NUMBER DEFAULT 0, 
	"BC_SUPPORT_IMAGE" NUMBER DEFAULT 1, 
	"BC_SUPPORT_HIT_DAY" NUMBER DEFAULT 1,
	"BC_SUPPORT_MAIN_SELEC" NUMBER DEFAULT 0, 
	"BC_SUPPORT_COMM_SELEC" NUMBER DEFAULT 0, 
	"BC_SUPPORT_OPEN_DAY" NUMBER DEFAULT 0,
	"BC_CUSTOM_FIELD_1" VARCHAR2(50), 
	"BC_CUSTOM_FIELD_2" VARCHAR2(50), 
	"BC_CUSTOM_FIELD_3" VARCHAR2(50), 
	"BC_CUSTOM_FIELD_4" VARCHAR2(50), 
	"BC_CUSTOM_FIELD_5" VARCHAR2(50), 
	"BC_CUSTOM_FIELD_6" VARCHAR2(50), 
	"BC_CUSTOM_FIELD_7" VARCHAR2(50), 
	"BC_CUSTOM_FIELD_8" VARCHAR2(50), 
	"BC_CUSTOM_FIELD_9" VARCHAR2(50), 
	"BC_CUSTOM_FIELD_10" VARCHAR2(50), 
	"BC_CUSTOM_FIELD_USE" VARCHAR2(100)
	
   ) ;
 

   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_ID" IS '게시판 아이디';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_NAME" IS '게시판 이름';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_USE" IS '게시판 사용상태';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_TYPE" IS '게시판 유형';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CATEGORY1" IS '구분1 코드';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CATEGORY1_NAME" IS '구분1 이름';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CATEGORY2" IS '구분2 코드';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CATEGORY2_NAME" IS '구분2 이름';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CATEGORY3" IS '구분3 코드';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CATEGORY3_NAME" IS '구분3 이름';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_STATUS_CODE" IS '진행상태 코드';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_LIST_FILE" IS '목록 템플릿';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_VIEW_FILE" IS '뷰 템플릿';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_FORM_FILE" IS '폼 템플릿';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_DEPARTMENT" IS '관리 부서 코드';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_GROUP" IS '관리 그룹 아이디';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_ALLOW_MEMBER_LIST" IS '회원 목록접근 허용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_ALLOW_MEMBER_VIEW" IS '회원 글조회 허용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_ALLOW_MEMBER_FORM" IS '회원 글쓰기/수정 허용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_ALLOW_MEMBER_DOWNLOAD" IS '회원 첨부파일 다운로드 허용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_ALLOW_GUEST_LIST" IS '비회원 목록접근 허용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_ALLOW_GUEST_VIEW" IS '비회원 글조회 허용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_ALLOW_GUEST_FORM" IS '비회원 글쓰기/수정 허용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_ALLOW_GUEST_DOWNLOAD" IS '비회원 첨부파일 다운로드 허용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_SUPPORT_NOTICE" IS '공지글 기능 사용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_NOTICE_EVERY_PAGE" IS '공지글 표시방법(모든페이지/첫페이지)';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_SUPPORT_SECRET" IS '비밀글기능 사용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_SUPPORT_COMMENT" IS '댓글기능 사용여부';
   
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_SUPPORT_ANSWER" IS '답글기능 사용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_SUPPORT_RECOMMEND" IS '추천기능 사용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_SUPPORT_THUMBNAIL" IS '썸네일 사용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_THUMBNAIL_CROP" IS '썸네일 생성시 이미지 자르기 여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_THUMBNAIL_WIDTH" IS '썸네일 생성시 가로길이';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_THUMBNAIL_HEIGHT" IS '썸네일 생성시 세로길이';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_UPLOAD_FILE_NUM" IS '첨부가능한 파일갯수';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_UPLOAD_SIZE_MAX" IS '첨부파일 업로드 사이즈 제한';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_PAGE_SIZE" IS '목록 페이지 크기';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_ORGANIZATION" IS '기관코드';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_SUPPORT_NURI" IS '공공누리 사용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_SUPPORT_IMAGE" IS '대표이미지 사용여부';
 
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_SUPPORT_HIT_DAY" IS '조회수 증가방법';
   
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_SUPPORT_MAIN_SELEC" IS '메인페이지 추출기능 사용여부';
   
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_SUPPORT_COMM_SELEC" IS '공통게시판 추출기능 사용여부';
   
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_SUPPORT_OPEN_DAY" IS '게시날짜 설정기능 사용여부';
   
   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CUSTOM_FIELD_1" IS '사용자정의 필드명 1';

   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CUSTOM_FIELD_2" IS '사용자정의 필드명 2';

   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CUSTOM_FIELD_3" IS '사용자정의 필드명 3';

   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CUSTOM_FIELD_4" IS '사용자정의 필드명 4';

   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CUSTOM_FIELD_5" IS '사용자정의 필드명 5';

   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CUSTOM_FIELD_6" IS '사용자정의 필드명 6';

   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CUSTOM_FIELD_7" IS '사용자정의 필드명 7';

   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CUSTOM_FIELD_8" IS '사용자정의 필드명 8';

   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CUSTOM_FIELD_9" IS '사용자정의 필드명 9';

   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CUSTOM_FIELD_10" IS '사용자정의 필드명 10';

   COMMENT ON COLUMN "ASA_BOARD_CONFIG"."BC_CUSTOM_FIELD_USE" IS '사용자정의 필드 사용유무 리스트';

 
   COMMENT ON TABLE "ASA_BOARD_CONFIG"  IS '게시판 설정';
   
--------------------------------------------------------
--  DDL for Index ASA_BOARD_CONFIG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_BOARD_CONFIG_PK" ON "ASA_BOARD_CONFIG" ("BC_ID");
--------------------------------------------------------
--  Constraints for Table ASA_BOARD_CONFIG
--------------------------------------------------------

  ALTER TABLE "ASA_BOARD_CONFIG" ADD CONSTRAINT "ASA_BOARD_CONFIG_PK" PRIMARY KEY ("BC_ID") ENABLE;
 
  ALTER TABLE "ASA_BOARD_CONFIG" MODIFY ("BC_ID" NOT NULL ENABLE);
   
   
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------   
--------------------------------------------------------
--  DDL for Table ASA_BOARD_ARTICLE
--------------------------------------------------------

  CREATE TABLE "ASA_BOARD_ARTICLE" 
   (	"BA_ID" NUMBER, 
	"BC_ID" VARCHAR2(100), 
	"BA_TITLE" VARCHAR2(1000), 
	"BA_CONTENT_HTML" CLOB, 
	"BA_CONTENT_PLAIN" VARCHAR2(4000), 
	"BA_NOTICE" NUMBER, 
	"BA_NOTICE_STARTDATE" VARCHAR2(20), 
	"BA_NOTICE_ENDDATE" VARCHAR2(20), 
	"BA_SECRET" NUMBER, 
	"BA_SECRET_PASSWORD" VARCHAR2(200), 
	"BA_CATEGORY1" VARCHAR2(50), 
	"BA_CATEGORY2" VARCHAR2(50), 
	"BA_CATEGORY3" VARCHAR2(50), 
	"BA_STATUS" VARCHAR2(50), 
	"MEM_ID" VARCHAR2(50), 
	"DEP_ID" VARCHAR2(50), 
	"BA_GUEST_NAME" VARCHAR2(200), 
	"BA_GUEST_PASSWORD" VARCHAR2(200), 
	"BA_EMAIL" VARCHAR2(200), 
	"BA_IP" VARCHAR2(20), 
	"BA_REGDATE" DATE, 
	"BA_LAST_MODIFIED" DATE, 
	"BA_UPDATER_ID" VARCHAR2(50), 
	"BA_HIT" NUMBER DEFAULT 0, 
	"BA_RECOMMEND" NUMBER DEFAULT 0, 
	"BA_COMMENT_TOTAL" NUMBER DEFAULT 0, 
	"BA_THUMB" NUMBER, 
	"BA_NURI" VARCHAR2(50), 
	"BA_USE" NUMBER, 
	"BA_MAIN_SELEC" NUMBER DEFAULT 0, 
	"BA_COMM_SELEC" NUMBER DEFAULT 0, 
	"BA_START_DATE" VARCHAR2(20), 
	"BA_START_TIME" VARCHAR2(20), 
	"BA_END_DATE" VARCHAR2(20), 
	"BA_COM_SEL_CAT" VARCHAR2(50),
	"BA_THUMB_TEXT" VARCHAR2(500),
	"BA_ANSWER" VARCHAR2(4000),
	"BA_CUSTOM_FIELD_1" VARCHAR2(500),
	"BA_CUSTOM_FIELD_2" VARCHAR2(500),
	"BA_CUSTOM_FIELD_3" VARCHAR2(500),
	"BA_CUSTOM_FIELD_4" VARCHAR2(500),
	"BA_CUSTOM_FIELD_5" VARCHAR2(500),
	"BA_CUSTOM_FIELD_6" VARCHAR2(500),
	"BA_CUSTOM_FIELD_7" VARCHAR2(500),
	"BA_CUSTOM_FIELD_8" VARCHAR2(500),
	"BA_CUSTOM_FIELD_9" VARCHAR2(500),
	"BA_CUSTOM_FIELD_10" VARCHAR2(500)
   ) ;
 

   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_ID" IS '게시글 아이디';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BC_ID" IS '게시판 아이디';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_TITLE" IS '제목';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CONTENT_HTML" IS '내용(html)';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CONTENT_PLAIN" IS '내용(text)';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_NOTICE" IS '공지글 여부';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_NOTICE_STARTDATE" IS '공지 시작일-사용안함';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_NOTICE_ENDDATE" IS '공지 종료일';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_SECRET" IS '비밀글 여부';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_SECRET_PASSWORD" IS '비밀글 비밀번호';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CATEGORY1" IS '분류1(코드)';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CATEGORY2" IS '분류2(코드)';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CATEGORY3" IS '분류3(코드)';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_STATUS" IS '진행상태(코드)';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."MEM_ID" IS '작성자 ID';

   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."DEP_ID" IS '작성자 부서 코드';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_GUEST_NAME" IS '비회원 작성자 이름';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_GUEST_PASSWORD" IS '비회원 작성 비밀번호';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_EMAIL" IS '작성자 이메일';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_IP" IS '작성자 IP';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_LAST_MODIFIED" IS '마지막 수정일시';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_UPDATER_ID" IS '수정자 아이디';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_HIT" IS '조회수';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_RECOMMEND" IS '추천수';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_COMMENT_TOTAL" IS '댓글수';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_THUMB" IS '대표이미지 ';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_NURI" IS '공공누리';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_USE" IS '게시유무';
   
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_MAIN_SELEC" IS '메인페이지 추출';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_COMM_SELEC" IS '공통게시판 추출';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_START_DATE" IS '게시 시작일';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_START_TIME" IS '게시 시작시간';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_END_DATE" IS '게시 종료일';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_COM_SEL_CAT" IS '공통추출대상 분류코드';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_THUMB_TEXT" IS '대표이미지 대체텍스트';
   
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_ANSWER" IS '답글';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CUSTOM_FIELD_1" IS '사용자 정의 필드 1';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CUSTOM_FIELD_2" IS '사용자 정의 필드 2';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CUSTOM_FIELD_3" IS '사용자 정의 필드 3';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CUSTOM_FIELD_4" IS '사용자 정의 필드 4';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CUSTOM_FIELD_5" IS '사용자 정의 필드 5';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CUSTOM_FIELD_6" IS '사용자 정의 필드 6';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CUSTOM_FIELD_7" IS '사용자 정의 필드 7';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CUSTOM_FIELD_8" IS '사용자 정의 필드 8';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CUSTOM_FIELD_9" IS '사용자 정의 필드 9';
 
   COMMENT ON COLUMN "ASA_BOARD_ARTICLE"."BA_CUSTOM_FIELD_10" IS '사용자 정의 필드 10';
 
   COMMENT ON TABLE "ASA_BOARD_ARTICLE"  IS '게시글';
   
   
--------------------------------------------------------
--  DDL for Index ASA_BOARD_ARTICLE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_BOARD_ARTICLE_PK" ON "ASA_BOARD_ARTICLE" ("BA_ID");
--------------------------------------------------------
--  Constraints for Table ASA_BOARD_ARTICLE
--------------------------------------------------------

  ALTER TABLE "ASA_BOARD_ARTICLE" ADD CONSTRAINT "ASA_BOARD_ARTICLE_PK" PRIMARY KEY ("BA_ID") ENABLE;
 
  ALTER TABLE "ASA_BOARD_ARTICLE" MODIFY ("BA_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_BOARD_ARTICLE" MODIFY ("BC_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ASA_BOARD_ARTICLE
--------------------------------------------------------

  ALTER TABLE "ASA_BOARD_ARTICLE" ADD CONSTRAINT "ASA_BOARD_ARTICLE_FK1" FOREIGN KEY ("BC_ID")
	  REFERENCES "ASA_BOARD_CONFIG" ("BC_ID") ON DELETE CASCADE ENABLE;
  
----------------------------------------------------------------------------------------------------------------   
---------------------------------------------------------------------------------------------------------------- 
--------------------------------------------------------
--  DDL for Table ASA_FILEINFO
--------------------------------------------------------

  CREATE TABLE "ASA_FILEINFO" 
   (	"FILE_ID" NUMBER, 
	"MEMBER_ID" VARCHAR2(50), 
	"FILE_MODULE" VARCHAR2(50), 
	"FILE_MODULE_ID" VARCHAR2(50), 
	"FILE_MODULE_SUB" VARCHAR2(50), 
	"FILE_MODULE_SUB_ID" VARCHAR2(50), 
	"FILE_ORIGINAL_NAME" VARCHAR2(500), 
	"FILE_PATH" VARCHAR2(1000), 
	"FILE_THUMBNAIL_PATH" VARCHAR2(200), 
	"FILE_EXT" VARCHAR2(20), 
	"FILE_MIME_TYPE" VARCHAR2(200), 
	"FILE_MEDIA_TYPE" VARCHAR2(20), 
	"FILE_SIZE" NUMBER, 
	"FILE_REGDATE" DATE, 
	"FILE_ALT_TEXT" VARCHAR2(500), 
	"FILE_DOWNLOAD_COUNT" NUMBER DEFAULT 0, 
	"FILE_UUID" VARCHAR2(100), 
	"FILE_ORI_IMAGE_WIDTH" NUMBER, 
	"FILE_ORI_IMAGE_HEIGHT" NUMBER, 
	"FILE_ATTACHMENT_TYPE" VARCHAR2(50),
	"FILE_SERVLET_URL" VARCHAR2(100),
	"FILE_SERVLET_THUMBNAIL_URL" VARCHAR2(100)
   ) ;
 

   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_ID" IS '파일아이디';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."MEMBER_ID" IS '등록회원 아이디';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_MODULE" IS '첨부된 모듈명';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_MODULE_ID" IS '첨부된 모듈 아이디';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_MODULE_SUB" IS '첨부된 하위 모듈';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_MODULE_SUB_ID" IS '첨부된 하위 모듈 아이디';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_ORIGINAL_NAME" IS '원본파일명';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_PATH" IS '저장위치';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_THUMBNAIL_PATH" IS '썸네일파일 저장위치';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_EXT" IS '파일확장자';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_MIME_TYPE" IS 'MIME 유형';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_MEDIA_TYPE" IS '파일 종류';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_SIZE" IS '파일크기';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_ALT_TEXT" IS '대체텍스트';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_DOWNLOAD_COUNT" IS '다운로드 횟수';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_UUID" IS '파일 렌덤아이디';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_ORI_IMAGE_WIDTH" IS '원 이미지 가로크기';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_ORI_IMAGE_HEIGHT" IS '원 이미지 세로크기';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_ATTACHMENT_TYPE" IS '첨부유형';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_SERVLET_URL" IS '서블릿 호출 url';
 
   COMMENT ON COLUMN "ASA_FILEINFO"."FILE_SERVLET_THUMBNAIL_URL" IS '썸네일 서블릿 호출 url';
 
   COMMENT ON TABLE "ASA_FILEINFO"  IS '파일정보';
  
--------------------------------------------------------
--  DDL for Index ASA_FILEINFO_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_FILEINFO_PK" ON "ASA_FILEINFO" ("FILE_ID");
--------------------------------------------------------
--  Constraints for Table ASA_FILEINFO
--------------------------------------------------------

  ALTER TABLE "ASA_FILEINFO" ADD CONSTRAINT "ASA_FILEINFO_PK" PRIMARY KEY ("FILE_ID") ENABLE;
 
  ALTER TABLE "ASA_FILEINFO" MODIFY ("FILE_ID" NOT NULL ENABLE)  ;
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------     
--------------------------------------------------------
--  DDL for Table ASA_MENU
--------------------------------------------------------

  CREATE TABLE "ASA_MENU" 
   (	"MENU_ID" VARCHAR2(100), 
	"MENU_NAME" VARCHAR2(200), 
	"MENU_TYPE" VARCHAR2(20), 
	"MENU_GNB_TYPE" VARCHAR2(20), 
	"MENU_GNB_EXT_ON" VARCHAR2(20), 
	"MENU_GNB_EXT_OFF" VARCHAR2(20), 
	"MENU_SNB_TYPE" VARCHAR2(20), 
	"MENU_SNB_EXT_ON" VARCHAR2(20), 
	"MENU_SNB_EXT_OFF" VARCHAR2(20), 
	"MENU_TITLE_TYPE" VARCHAR2(20), 
	"MENU_TITLE_EXT" VARCHAR2(20), 
	"MENU_TITLE_SUB_TEXT" VARCHAR2(2000), 
	"MENU_LINK" VARCHAR2(1000), 
	"MENU_NEW_WIN" NUMBER, 
	"MENU_PARENT" VARCHAR2(100), 
	"MENU_DEPTH" NUMBER, 
	"MENU_ORDER" NUMBER, 
	"MENU_HEADER" VARCHAR2(1000), 
	"MENU_TEMPLATE" VARCHAR2(1000), 
	"MENU_FOOTER" VARCHAR2(1000), 
	"MENU_STATUS" VARCHAR2(20), 
	"MENU_USE_MANAGER_INFO" NUMBER DEFAULT 0,
	"MENU_MANAGER" VARCHAR2(100), 
	"MENU_DEPARTMENT" VARCHAR2(100), 
	"MENU_PHONE" VARCHAR2(100), 
	"MENU_EMAIL" VARCHAR2(100), 
	"MENU_ETC" VARCHAR2(1000), 
	"MENU_REGDATE" DATE, 
	"MENU_USE_SATISFACTION" NUMBER DEFAULT 1, 
	"MENU_LAST_MODIFIED" DATE, 
	"MENU_LOCATION" VARCHAR2(500),
	"SITE_PREFIX" VARCHAR2(50 BYTE),
	"TAG_CODE" VARCHAR2(1000 BYTE)
   ) ;
 

   COMMENT ON COLUMN "ASA_MENU"."MENU_ID" IS '메뉴 아이디';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_NAME" IS '메뉴 이름';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_TYPE" IS '메뉴 유형';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_GNB_TYPE" IS '메인메뉴 이미지/텍스트 여부';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_GNB_EXT_ON" IS '메인메뉴 활성화 이미지 확장자';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_GNB_EXT_OFF" IS '메인메뉴 비활성화 이미지 확장자';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_SNB_TYPE" IS '서브메뉴 이미지/텍스트 여부';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_SNB_EXT_ON" IS '서브메뉴 활성화 이미지 확장자';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_SNB_EXT_OFF" IS '서브메뉴 비활성화 이미지 확장자';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_TITLE_TYPE" IS '페이지 제목 이미지/텍스트 여부';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_TITLE_EXT" IS '페이지 제목 이미지 확장자';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_TITLE_SUB_TEXT" IS '페이지 제목 부가 텍스트';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_LINK" IS '메뉴 링크';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_NEW_WIN" IS '메뉴 링크 새창열림 여부';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_PARENT" IS '부모메뉴 아이디';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_DEPTH" IS '메뉴 깊이';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_ORDER" IS '메뉴 순서';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_HEADER" IS '헤더파일이름';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_TEMPLATE" IS '템플릿 파일이름';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_FOOTER" IS '푸터파일 이름';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_STATUS" IS '메뉴 상태';
   
   COMMENT ON COLUMN "ASA_MENU"."MENU_USE_MANAGER_INFO" IS '메뉴 관리자정보 노출 여부';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_MANAGER" IS '메뉴담당자 이름';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_DEPARTMENT" IS '메뉴 담당부서명';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_PHONE" IS '메뉴 담당부서 전화번호';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_EMAIL" IS '메뉴 담당부서 이메일주소';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_ETC" IS '기타 정보';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_REGDATE" IS '메뉴 등록일시';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_USE_SATISFACTION" IS '만족도조사 사용여부';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_LAST_MODIFIED" IS '마지막 수정일시';
 
   COMMENT ON COLUMN "ASA_MENU"."MENU_LOCATION" IS '메뉴위치';
   
   COMMENT ON COLUMN "ASA_MENU"."SITE_PREFIX" IS '사이트 프리픽스';
 
   COMMENT ON COLUMN "ASA_MENU"."TAG_CODE" IS '해시태그 코드';

   COMMENT ON TABLE "ASA_MENU"  IS '메뉴 정보';
  
--------------------------------------------------------
--  DDL for Index ASA_PARENT_INDEX1
--------------------------------------------------------

  CREATE INDEX "ASA_PARENT_INDEX1" ON "ASA_MENU" ("MENU_PARENT");
--------------------------------------------------------
--  DDL for Index ASA_MENU_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_MENU_PK" ON "ASA_MENU" ("MENU_ID", "SITE_PREFIX");
--------------------------------------------------------
--  Constraints for Table ASA_MENU
--------------------------------------------------------

  ALTER TABLE "ASA_MENU" ADD CONSTRAINT "ASA_MENU_PK" PRIMARY KEY ("MENU_ID", "SITE_PREFIX") ENABLE;
 
  ALTER TABLE "ASA_MENU" MODIFY ("MENU_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_MENU" MODIFY ("SITE_PREFIX" NOT NULL ENABLE);
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    
--------------------------------------------------------
--  DDL for Table ASA_MENU_CONT_REL
--------------------------------------------------------

  CREATE TABLE "ASA_MENU_CONT_REL" 
   (	"MENU_ID" VARCHAR2(100), 
	"SITE_PREFIX" VARCHAR2(50), 
	"CONTENT_ROOT" NUMBER
   ) ;
 

   COMMENT ON COLUMN "ASA_MENU_CONT_REL"."MENU_ID" IS '메뉴아이디';
 
   COMMENT ON COLUMN "ASA_MENU_CONT_REL"."SITE_PREFIX" IS '사이트 프리픽스';
 
   COMMENT ON COLUMN "ASA_MENU_CONT_REL"."CONTENT_ROOT" IS '최초 콘텐츠 아이디';
   
   COMMENT ON TABLE "ASA_MENU_CONT_REL"  IS '메뉴-콘텐츠 릴레이션';
--------------------------------------------------------
--  DDL for Index ASA_MENU_CONT_REL_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_MENU_CONT_REL_PK" ON "ASA_MENU_CONT_REL" ("MENU_ID", "SITE_PREFIX", "CONTENT_ROOT");
--------------------------------------------------------
--  Constraints for Table ASA_MENU_CONT_REL
--------------------------------------------------------

  ALTER TABLE "ASA_MENU_CONT_REL" ADD CONSTRAINT "ASA_MENU_CONT_REL_PK" PRIMARY KEY ("MENU_ID", "SITE_PREFIX", "CONTENT_ROOT") ENABLE;
 
  ALTER TABLE "ASA_MENU_CONT_REL" MODIFY ("MENU_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_MENU_CONT_REL" MODIFY ("SITE_PREFIX" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_MENU_CONT_REL" MODIFY ("CONTENT_ROOT" NOT NULL ENABLE);
  
  
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_CONTENT
--------------------------------------------------------

  CREATE TABLE "ASA_CONTENT" 
   (	"CONTENT_ID" NUMBER, 
	"CONTENT_ROOT" NUMBER,
	"CONTENT_TITLE" VARCHAR2(500), 
	"MENU_ID" VARCHAR2(50), 
	"CONTENT" CLOB, 
	"CONTENT_PLAIN" VARCHAR2(1500 CHAR), 
	"CONTENT_STATUS" VARCHAR2(20), 
	"CONTENT_VER" NUMBER, 
	"CONTENT_REGDATE" DATE, 
	"CONTENT_LAST_MODIFIED" DATE, 
	"CONTENT_LAST_WORKER" VARCHAR2(100), 
	"CONTENT_MEMO" VARCHAR2(1000), 
	"SITE_PREFIX" VARCHAR2(50)
   ) ;
 
 
   COMMENT ON COLUMN "ASA_CONTENT"."CONTENT_ID" IS 'PK';
 
   COMMENT ON COLUMN "ASA_CONTENT"."CONTENT_ROOT" IS '최초 콘텐츠 아이디';
 
   COMMENT ON COLUMN "ASA_CONTENT"."CONTENT_TITLE" IS '콘텐츠 타이틀';

   COMMENT ON COLUMN "ASA_CONTENT"."MENU_ID" IS '메뉴 아이디';
 
   COMMENT ON COLUMN "ASA_CONTENT"."CONTENT" IS '콘텐츠 내용(HTML)';
 
   COMMENT ON COLUMN "ASA_CONTENT"."CONTENT_PLAIN" IS '콘텐츠 내용(TEXT)';
 
   COMMENT ON COLUMN "ASA_CONTENT"."CONTENT_STATUS" IS '콘텐츠 상태';
 
   COMMENT ON COLUMN "ASA_CONTENT"."CONTENT_VER" IS '콘텐츠 버전';
 
   COMMENT ON COLUMN "ASA_CONTENT"."CONTENT_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_CONTENT"."CONTENT_LAST_MODIFIED" IS '마지막 수정일시';
 
   COMMENT ON COLUMN "ASA_CONTENT"."CONTENT_LAST_WORKER" IS '마지막 작성자 아이디';
 
   COMMENT ON COLUMN "ASA_CONTENT"."CONTENT_MEMO" IS '메모';
 
   COMMENT ON COLUMN "ASA_CONTENT"."SITE_PREFIX" IS '사이트 프리픽스';
 
   COMMENT ON TABLE "ASA_CONTENT"  IS '메뉴 콘텐츠';
--------------------------------------------------------
--  DDL for Index ASA_CONTENT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_CONTENT_PK" ON "ASA_CONTENT" ("CONTENT_ID");
--------------------------------------------------------
--  Constraints for Table ASA_CONTENT
--------------------------------------------------------

  ALTER TABLE "ASA_CONTENT" ADD CONSTRAINT "ASA_CONTENT_PK" PRIMARY KEY ("CONTENT_ID") ENABLE;
 
  ALTER TABLE "ASA_CONTENT" MODIFY ("CONTENT_ID" NOT NULL ENABLE);
  

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------     
--------------------------------------------------------
--  DDL for Table ASA_BANNER
--------------------------------------------------------

  CREATE TABLE "ASA_BANNER" 
   (	"BN_ID" NUMBER, 
	"BN_TYPE" VARCHAR2(30), 
	"BN_ORDER" NUMBER, 
	"BN_NAME" VARCHAR2(600), 
	"BN_DESCRIPTION" VARCHAR2(2000), 
	"BN_LINK" VARCHAR2(1000), 
	"BN_NEW_WIN" NUMBER, 
	"BN_START_DATE" VARCHAR2(20), 
	"BN_END_DATE" VARCHAR2(20), 
	"BN_REGDATE" DATE, 
	"BN_USE" NUMBER, 
	"BN_EXT" VARCHAR2(20), 
	"BN_WIDTH" NUMBER, 
	"BN_HEIGHT" NUMBER, 
	"BN_TOP" NUMBER, 
	"BN_LEFT" NUMBER,
	"BN_THUMB" NUMBER, 
	"SITE_PREFIX" VARCHAR2(50 BYTE)
   ) ;
 

   COMMENT ON COLUMN "ASA_BANNER"."BN_ID" IS '아이디';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_TYPE" IS '유형';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_ORDER" IS '순서';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_NAME" IS '이름';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_DESCRIPTION" IS '설명';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_LINK" IS '링크주소';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_NEW_WIN" IS '링크대상 새창여부';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_START_DATE" IS '개시 시작일';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_END_DATE" IS '개시 종료일';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_USE" IS '사용유무';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_EXT" IS '파일확장자';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_WIDTH" IS '팝업창 가로';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_HEIGHT" IS '팝업창 세로';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_TOP" IS '팝업창 TOP위치';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_LEFT" IS '팝업창 LEFT위치';
 
   COMMENT ON COLUMN "ASA_BANNER"."BN_THUMB" IS '이미지 정보';
 
   COMMENT ON COLUMN "ASA_BANNER"."SITE_PREFIX" IS '사이트 프리픽스';
   
   COMMENT ON TABLE "ASA_BANNER"  IS '배너, 메인비주얼, 팝업존, 팝업';

--------------------------------------------------------
--  DDL for Index ASA_BANNER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_BANNER_PK" ON "ASA_BANNER" ("BN_ID");
--------------------------------------------------------
--  Constraints for Table ASA_BANNER
--------------------------------------------------------

  ALTER TABLE "ASA_BANNER" ADD CONSTRAINT "ASA_BANNER_PK" PRIMARY KEY ("BN_ID") ENABLE;
 
  ALTER TABLE "ASA_BANNER" MODIFY ("BN_ID" NOT NULL ENABLE);

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------   

  --------------------------------------------------------
--  DDL for Table ASA_CONFIG_OPTION
--------------------------------------------------------

  CREATE TABLE "ASA_CONFIG_OPTION" 
   (	"CONF_ID" VARCHAR2(30), 
	"OPT_KEY" VARCHAR2(100), 
	"OPT_VALUE" VARCHAR2(4000), 
	"OPT_NAME" VARCHAR2(100), 
	"OPT_TYPE" VARCHAR2(20), 
	"OPT_HIDDEN" NUMBER DEFAULT 0, 
	"OPT_UNIT_TEXT" VARCHAR2(30), 
	"OPT_HELP" VARCHAR2(500),
	"SITE_PREFIX" VARCHAR2(50 BYTE)
   );   

   
   COMMENT ON COLUMN "ASA_CONFIG_OPTION"."CONF_ID" IS '설정 아이디';
 
   COMMENT ON COLUMN "ASA_CONFIG_OPTION"."OPT_KEY" IS '설정 키';
 
   COMMENT ON COLUMN "ASA_CONFIG_OPTION"."OPT_VALUE" IS '설정 값';
 
   COMMENT ON COLUMN "ASA_CONFIG_OPTION"."OPT_NAME" IS '설정 이름';
 
   COMMENT ON COLUMN "ASA_CONFIG_OPTION"."OPT_TYPE" IS '설정 유형';
 
   COMMENT ON COLUMN "ASA_CONFIG_OPTION"."OPT_HIDDEN" IS '숨김여부';
 
   COMMENT ON COLUMN "ASA_CONFIG_OPTION"."OPT_UNIT_TEXT" IS '단위텍스트';
 
   COMMENT ON COLUMN "ASA_CONFIG_OPTION"."OPT_HELP" IS '도움말';
   
   COMMENT ON COLUMN "ASA_CONFIG_OPTION"."SITE_PREFIX" IS '사이트 프리픽스';
 
   COMMENT ON TABLE "ASA_CONFIG_OPTION"  IS '사이트 환경설정 정보'  ;
   
--------------------------------------------------------
--  DDL for Index ASA_CONFIG_OPTION_INDEX1
--------------------------------------------------------

  CREATE INDEX "ASA_CONFIG_OPTION_INDEX1" ON "ASA_CONFIG_OPTION" ("SITE_PREFIX", "CONF_ID", "OPT_KEY");
--------------------------------------------------------
--  DDL for Index ASA_CONFIG_OPTION_PK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_CONFIG_OPTION_PK1" ON "ASA_CONFIG_OPTION" ("CONF_ID", "OPT_KEY", "SITE_PREFIX");
--------------------------------------------------------
--  Constraints for Table ASA_CONFIG_OPTION
--------------------------------------------------------

  ALTER TABLE "ASA_CONFIG_OPTION" ADD CONSTRAINT "ASA_CONFIG_OPTION_PK" PRIMARY KEY ("CONF_ID", "OPT_KEY", "SITE_PREFIX") ENABLE;
 
  ALTER TABLE "ASA_CONFIG_OPTION" MODIFY ("CONF_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_CONFIG_OPTION" MODIFY ("OPT_KEY" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_CONFIG_OPTION" MODIFY ("SITE_PREFIX" NOT NULL ENABLE);

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------   

--------------------------------------------------------
--  DDL for Table ASA_ALLOW_IP
--------------------------------------------------------

  CREATE TABLE "ASA_ALLOW_IP" 
   (	"IP_ID" NUMBER, 
	"IP_TYPE" VARCHAR2(50), 
	"IP_START" VARCHAR2(20), 
	"IP_END" VARCHAR2(20), 
	"IP_REGDATE" DATE, 
	"IP_RULE_NAME" VARCHAR2(500), 
	"IP_USE" NUMBER,
	"SITE_PREFIX" VARCHAR2(50 BYTE)
   ) ;
 

   COMMENT ON COLUMN "ASA_ALLOW_IP"."IP_ID" IS 'PK';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP"."IP_TYPE" IS '허용, 차단 구분';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP"."IP_START" IS '1건차단, 패턴차단, 구간시작 IP';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP"."IP_END" IS '구간 종료 아이디';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP"."IP_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP"."IP_RULE_NAME" IS '규칙명';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP"."IP_USE" IS '사용유무';   
   
   COMMENT ON COLUMN "ASA_ALLOW_IP"."SITE_PREFIX" IS '사이트프리픽스';
 
   COMMENT ON TABLE "ASA_ALLOW_IP"  IS '관리자 접근 허용, 차단 아이피';
  
   
--------------------------------------------------------
--  DDL for Index ASA_ALLOW_IP_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_ALLOW_IP_PK" ON "ASA_ALLOW_IP" ("IP_ID");
--------------------------------------------------------
--  Constraints for Table ASA_ALLOW_IP
--------------------------------------------------------

  ALTER TABLE "ASA_ALLOW_IP" ADD CONSTRAINT "ASA_ALLOW_IP_PK" PRIMARY KEY ("IP_ID") ENABLE;
 
  ALTER TABLE "ASA_ALLOW_IP" MODIFY ("IP_TYPE" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_ALLOW_IP" MODIFY ("IP_ID" NOT NULL ENABLE);
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ASA_ALLOW_IP_TEMP
--------------------------------------------------------

  CREATE TABLE "ASA_ALLOW_IP_TEMP" 
   (	"ADMIN_ID" VARCHAR2(50), 
	"TEMP_IP" VARCHAR2(20), 
	"ADMIN_HP" VARCHAR2(20), 
	"CERTI_NUM" VARCHAR2(20), 
	"TEMP_REGDATE" DATE, 
	"CERTI_REQUEST_DATE" DATE, 
	"CERTI_COMPLET_DATE" DATE, 
	"AUTHENTICATION" NUMBER, 
	"SMS_SEND_CNT" NUMBER DEFAULT 0, 
	"AUTHENTI_FAIL_CNT" NUMBER DEFAULT 0
   ) ;
 

   COMMENT ON COLUMN "ASA_ALLOW_IP_TEMP"."ADMIN_ID" IS '관리자 아이디';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP_TEMP"."TEMP_IP" IS '임시허용 아이피';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP_TEMP"."ADMIN_HP" IS '관리자 휴대폰번호';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP_TEMP"."CERTI_NUM" IS '인증번호';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP_TEMP"."TEMP_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP_TEMP"."CERTI_REQUEST_DATE" IS '인증번호 요청일시';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP_TEMP"."CERTI_COMPLET_DATE" IS '인증완료일시';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP_TEMP"."AUTHENTICATION" IS '인증완료여부';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP_TEMP"."SMS_SEND_CNT" IS 'SMS발송 횟수';
 
   COMMENT ON COLUMN "ASA_ALLOW_IP_TEMP"."AUTHENTI_FAIL_CNT" IS '인증실패 횟수';
 
   COMMENT ON TABLE "ASA_ALLOW_IP_TEMP"  IS '임시 아이피 허용 정보';
--------------------------------------------------------
--  DDL for Index ASA_TEMP_ALLOW_IP_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_TEMP_ALLOW_IP_PK" ON "ASA_ALLOW_IP_TEMP" ("ADMIN_ID");
--------------------------------------------------------
--  Constraints for Table ASA_ALLOW_IP_TEMP
--------------------------------------------------------

  ALTER TABLE "ASA_ALLOW_IP_TEMP" ADD CONSTRAINT "ASA_TEMP_ALLOW_IP_PK" PRIMARY KEY ("ADMIN_ID") ENABLE;
 
  ALTER TABLE "ASA_ALLOW_IP_TEMP" MODIFY ("ADMIN_ID" NOT NULL ENABLE);
  
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ASA_ALLOW_MAC
--------------------------------------------------------

  CREATE TABLE "ASA_ALLOW_MAC" 
   (	"MAC_ID" NUMBER, 
	"MAC_TYPE" VARCHAR2(50), 
	"MAC_ADDRESS" VARCHAR2(50), 
	"MAC_REGDATE" DATE, 
	"MAC_RULE_NAME" VARCHAR2(500), 
	"MAC_USE" NUMBER, 
	"SITE_PREFIX" VARCHAR2(50)
   ) ;
 

   COMMENT ON COLUMN "ASA_ALLOW_MAC"."MAC_ID" IS 'PK';
 
   COMMENT ON COLUMN "ASA_ALLOW_MAC"."MAC_TYPE" IS '허용, 차단';
 
   COMMENT ON COLUMN "ASA_ALLOW_MAC"."MAC_ADDRESS" IS 'Mac 주소';
 
   COMMENT ON COLUMN "ASA_ALLOW_MAC"."MAC_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_ALLOW_MAC"."MAC_RULE_NAME" IS '규칙명';
 
   COMMENT ON COLUMN "ASA_ALLOW_MAC"."MAC_USE" IS '사용유무';
 
   COMMENT ON COLUMN "ASA_ALLOW_MAC"."SITE_PREFIX" IS '사이트프리픽스';
 
   COMMENT ON TABLE "ASA_ALLOW_MAC"  IS '관리자 접근 허용, 차단 Mac 주소';
   
--------------------------------------------------------
--  DDL for Index ASA_ALLOW_MAC_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_ALLOW_MAC_PK" ON "ASA_ALLOW_MAC" ("MAC_ID");
--------------------------------------------------------
--  Constraints for Table ASA_ALLOW_MAC
--------------------------------------------------------

  ALTER TABLE "ASA_ALLOW_MAC" ADD CONSTRAINT "ASA_ALLOW_MAC_PK" PRIMARY KEY ("MAC_ID") ENABLE;
 
  ALTER TABLE "ASA_ALLOW_MAC" MODIFY ("MAC_ID" NOT NULL ENABLE);
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------   
--------------------------------------------------------
--  DDL for Table ASA_COMMON_CONTENT
--------------------------------------------------------

  CREATE TABLE "ASA_COMMON_CONTENT" 
   (	"COM_CONT_ID" NUMBER, 
	"COM_CONT_MODULE" VARCHAR2(50), 
	"COM_CONT_MODULE_SUB" VARCHAR2(50), 
	"COM_CONT_CATE1" VARCHAR2(500), 
	"COM_CONT_CATE2" VARCHAR2(500), 
	"COM_CONT_CATE3" VARCHAR2(500),
	"COM_CONT_TITLE" VARCHAR2(300),
	"COM_CONT_SUB_TITLE" VARCHAR2(2000),
	"COM_CONT_CONTENT" CLOB, 
	"COM_CONT_STATUS" VARCHAR2(20), 
	"COM_CONT_REGDATE" DATE, 
	"COM_CONT_MODIFY_DATE" DATE, 
	"COM_CONT_LAST_WORKER" VARCHAR2(50),
	"SITE_PREFIX" VARCHAR2(50)
   ) ;
 

   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_ID" IS 'PK';
 
   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_MODULE" IS '적용모듈';
 
   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_MODULE_SUB" IS '적용 서브 모듈';   
    
   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_CATE1" IS '카테고리1';
 
   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_CATE2" IS '카테고리2';
 
   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_CATE3" IS '카테고리3';
 
   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_TITLE" IS '제목';
   
   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_SUB_TITLE" IS '간단설명';

   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_CONTENT" IS '콘텐츠';
 
   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_STATUS" IS '노출 상태';
 
   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_MODIFY_DATE" IS '마지막 수정일시';
 
   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."COM_CONT_LAST_WORKER" IS '마지막 수정자';
   
   COMMENT ON COLUMN "ASA_COMMON_CONTENT"."SITE_PREFIX" IS '사이트프리픽스';
 
   COMMENT ON TABLE "ASA_COMMON_CONTENT"  IS '공통 콘텐츠 정보';
   
--------------------------------------------------------
--  DDL for Index ASA_COMMON_CONTENT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_COMMON_CONTENT_PK" ON "ASA_COMMON_CONTENT" ("COM_CONT_ID");
--------------------------------------------------------
--  Constraints for Table ASA_COMMON_CONTENT
--------------------------------------------------------

  ALTER TABLE "ASA_COMMON_CONTENT" ADD CONSTRAINT "ASA_COMMON_CONTENT_PK" PRIMARY KEY ("COM_CONT_ID") ENABLE;
 
  ALTER TABLE "ASA_COMMON_CONTENT" MODIFY ("COM_CONT_ID" NOT NULL ENABLE);

  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------   

--------------------------------------------------------
--  DDL for Table ASA_COMMON_CONTENT_REL
--------------------------------------------------------

  CREATE TABLE "ASA_COMMON_CONTENT_REL" 
   (	"COM_CONT_ID" NUMBER, 
	"PROGRAM_ID" VARCHAR2(50), 
	"MODULE_CODE" VARCHAR2(50)
   ) ;
 

   COMMENT ON COLUMN "ASA_COMMON_CONTENT_REL"."COM_CONT_ID" IS '공통콘텐츠 아이디';
 
   COMMENT ON COLUMN "ASA_COMMON_CONTENT_REL"."PROGRAM_ID" IS '프로그램 아이디';
 
   COMMENT ON COLUMN "ASA_COMMON_CONTENT_REL"."MODULE_CODE" IS '모듈 코드';
 
   COMMENT ON TABLE "ASA_COMMON_CONTENT_REL"  IS '공통콘텐츠 릴레이션';

--------------------------------------------------------
--  DDL for Index ASA_COMMON_CONTENT_REL_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_COMMON_CONTENT_REL_PK" ON "ASA_COMMON_CONTENT_REL" ("COM_CONT_ID", "PROGRAM_ID", "MODULE_CODE");
--------------------------------------------------------
--  Constraints for Table ASA_COMMON_CONTENT_REL
--------------------------------------------------------

  ALTER TABLE "ASA_COMMON_CONTENT_REL" ADD CONSTRAINT "ASA_COMMON_CONTENT_REL_PK" PRIMARY KEY ("COM_CONT_ID", "PROGRAM_ID", "MODULE_CODE") ENABLE;
 
  ALTER TABLE "ASA_COMMON_CONTENT_REL" MODIFY ("COM_CONT_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_COMMON_CONTENT_REL" MODIFY ("PROGRAM_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_COMMON_CONTENT_REL" MODIFY ("MODULE_CODE" NOT NULL ENABLE);

  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------   
--------------------------------------------------------
--  DDL for Table ASA_SITE
--------------------------------------------------------

  CREATE TABLE "ASA_SITE" 
   (	"SITE_ID" VARCHAR2(20), 
	"SITE_NAME" VARCHAR2(200), 
	"SITE_DESCRIPTION" VARCHAR2(1000), 
	"SITE_DOMAIN" VARCHAR2(500), 
	"SITE_TYPE" VARCHAR2(50) DEFAULT 'domain', 
	"SITE_THEME" VARCHAR2(100), 
	"SITE_MAIN" NUMBER, 
	"SITE_LOCALE" VARCHAR2(20) DEFAULT 'ko_KR', 
	"SITE_LOGO" NUMBER, 
	"SITE_REGDATE" DATE
   );

   
   COMMENT ON COLUMN "ASA_SITE"."SITE_ID" IS '사이트 아이디';
 
   COMMENT ON COLUMN "ASA_SITE"."SITE_NAME" IS '사이트 이름';
 
   COMMENT ON COLUMN "ASA_SITE"."SITE_DESCRIPTION" IS '사이트 설명';
 
   COMMENT ON COLUMN "ASA_SITE"."SITE_DOMAIN" IS '사이트 도메인';
 
   COMMENT ON COLUMN "ASA_SITE"."SITE_TYPE" IS '사이트 유형(도메인/디렉토리)';
 
   COMMENT ON COLUMN "ASA_SITE"."SITE_THEME" IS '사이트 테마';
 
   COMMENT ON COLUMN "ASA_SITE"."SITE_MAIN" IS '메인사이트 여부';
 
   COMMENT ON COLUMN "ASA_SITE"."SITE_LOCALE" IS '사이트 운영 위치';
 
   COMMENT ON COLUMN "ASA_SITE"."SITE_LOGO" IS '사이트 로고';
 
   COMMENT ON COLUMN "ASA_SITE"."SITE_REGDATE" IS '사이트 등록일시';
 
   COMMENT ON TABLE "ASA_SITE"  IS '사이트 정보';

--------------------------------------------------------
--  DDL for Index ASA_SITE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_SITE_PK" ON "ASA_SITE" ("SITE_ID");
--------------------------------------------------------
--  Constraints for Table ASA_SITE
--------------------------------------------------------

  ALTER TABLE "ASA_SITE" MODIFY ("SITE_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_SITE" ADD PRIMARY KEY ("SITE_ID") ENABLE;
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_ADMIN_SITE_ROLE_REL
--------------------------------------------------------

  CREATE TABLE "ASA_ADMIN_SITE_ROLE_REL" 
   (	"ADMIN_ID" VARCHAR2(50), 
	"SITE_PREFIX" VARCHAR2(20), 
	"ROLE_CODE" VARCHAR2(100)
   );
 

   COMMENT ON COLUMN "ASA_ADMIN_SITE_ROLE_REL"."ADMIN_ID" IS '관리자 아이디';
 
   COMMENT ON COLUMN "ASA_ADMIN_SITE_ROLE_REL"."SITE_PREFIX" IS '사이트 프리픽스';
 
   COMMENT ON COLUMN "ASA_ADMIN_SITE_ROLE_REL"."ROLE_CODE" IS '역할 코드';
 
   COMMENT ON TABLE "ASA_ADMIN_SITE_ROLE_REL"  IS '관리자 사이트별 롤 릴레이션';
   
--------------------------------------------------------
--  DDL for Index ASA_MEM_SITE_ROLE_REL_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_MEM_SITE_ROLE_REL_PK" ON "ASA_ADMIN_SITE_ROLE_REL" ("ADMIN_ID", "SITE_PREFIX");
--------------------------------------------------------
--  Constraints for Table ASA_ADMIN_SITE_ROLE_REL
--------------------------------------------------------

  ALTER TABLE "ASA_ADMIN_SITE_ROLE_REL" ADD CONSTRAINT "ASA_MEM_SITE_ROLE_REL_PK" PRIMARY KEY ("ADMIN_ID", "SITE_PREFIX") ENABLE;
 
  ALTER TABLE "ASA_ADMIN_SITE_ROLE_REL" MODIFY ("ADMIN_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_ADMIN_SITE_ROLE_REL" MODIFY ("SITE_PREFIX" NOT NULL ENABLE);
   
   
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------   
--------------------------------------------------------
--  DDL for Table ASA_RENTAL
--------------------------------------------------------

  CREATE TABLE "ASA_RENTAL" 
   (	"RENT_ID" NUMBER, 
	"RENT_CATE1" VARCHAR2(50), 
	"RENT_CATE2" VARCHAR2(200), 
	"RENT_MANAGER" VARCHAR2(50), 
	"RENT_MANATER_TEL" VARCHAR2(20), 
	"RENT_TITLE" VARCHAR2(200), 
	"RENT_DIV" VARCHAR2(20), 
	"RENT_CHARGE" NUMBER, 
	"RENT_RESERV_TYPE" VARCHAR2(500), 
	"RENT_PAYMENT_TYPE" VARCHAR2(500), 
	"RENT_DESCRIPTION" CLOB, 
	"RENT_ETC" VARCHAR2(1000 CHAR), 
	"RENT_USE" NUMBER, 
	"RENT_THUMB" NUMBER, 
	"RENT_POSSIBLE_DAY_TYPE" VARCHAR2(50), 
	"RENT_PERIOD_SDATE" VARCHAR2(20), 
	"RENT_PERIOD_EDATE" VARCHAR2(20), 
	"RENT_RESERV_SDATE_AFTER" NUMBER, 
	"RENT_RESERV_EDATE_AFTER" NUMBER, 
	"RENT_POSSIBLE_DAYOFWEEK" VARCHAR2(50), 
	"RENT_RESERV_TIME" VARCHAR2(500), 
	"RENT_IMPOSSIBLE_DATE" VARCHAR2(1000), 
	"RENT_REGDATE" DATE, 
	"RENT_STEP" VARCHAR2(20), 
	"RENT_PARKING" NUMBER DEFAULT 0, 
	"RENT_ORDER" NUMBER, 
	"RENT_VR" VARCHAR2(50), 
	"RENT_APPROVE" NUMBER DEFAULT 0, 
	"RENT_MEMBERSHIP" NUMBER DEFAULT 0, 
	"RENT_MEMBER_DISCOUNT" NUMBER DEFAULT 0, 
	"RENT_RES_MIN_TIME" NUMBER DEFAULT 1, 
	"RENT_RES_MIN_NUMBER" NUMBER DEFAULT 1, 
	"RENT_RES_MAX_NUMBER" NUMBER DEFAULT 1, 
	"RENT_RESERV_TIME_SAT" VARCHAR2(500), 
	"RENT_RESERV_TIME_SUN" VARCHAR2(500), 
	"RENT_REFUND_DATE_BEFORE" NUMBER DEFAULT 1, 
	"RENT_REFUND_RATE" NUMBER DEFAULT 100, 
	"RENT_PAYMENT_TIME_LIMIT" NUMBER DEFAULT 0, 
	"RENT_RENTING_METHOD" VARCHAR2(50), 
	"RENT_SHORT_DESCRIPTION" VARCHAR2(4000 CHAR), 
	"RENT_FACILITY_INFO" VARCHAR2(4000 CHAR), 
	"RENT_PRECAUTIONS" VARCHAR2(4000 CHAR), 
	"RENT_REFUND_POKICY" VARCHAR2(400 CHAR), 
	"RENT_AVAILABLE_TIME" VARCHAR2(300)
   ) ;
 

   COMMENT ON COLUMN "ASA_RENTAL"."RENT_ID" IS '대관/대여 아이디';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_CATE1" IS '구분 카테고리 1 (코드)';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_CATE2" IS '구분 카테고리 2 (코드)';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_MANAGER" IS '담당자';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_MANATER_TEL" IS '담당자 연락처';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_TITLE" IS '대관/대여 제목';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_DIV" IS '대관/대여 구분 (시설/물품 - facility / item)';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_CHARGE" IS '대관/대여 요금';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_RESERV_TYPE" IS '예약유형 (온라인 접수, 현장 접수... 등) (코드)';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_PAYMENT_TYPE" IS '결제방법 (온라인 결제, 현장결제... 등) (코드)';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_DESCRIPTION" IS '공간소개';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_ETC" IS '기타 정보';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_USE" IS '게시여부';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_THUMB" IS '이미지 파일정보 아이디';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_POSSIBLE_DAY_TYPE" IS '대관/대여 가능일 구분 (상시/기간  - always/period)';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_PERIOD_SDATE" IS '대관/대여 기간 시작일';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_PERIOD_EDATE" IS '대관/대여 기간 종료일';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_RESERV_SDATE_AFTER" IS '예약가능 시작일(오늘로 부터 x일 이후 시작)';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_RESERV_EDATE_AFTER" IS '예약가능 종료일(오늘로 부터 x일 이전 종료)';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_POSSIBLE_DAYOFWEEK" IS '예약가능 요일';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_RESERV_TIME" IS '예약가능 시간대(평일)';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_IMPOSSIBLE_DATE" IS '예약불가능 날짜';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_REGDATE" IS '등록일';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_STEP" IS '등록처리단계';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_PARKING" IS '주차지원대수';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_ORDER" IS '정렬순서';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_VR" IS 'VR폴더명';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_APPROVE" IS '승인기능사용여부';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_MEMBERSHIP" IS '회원제할인 사용여부';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_MEMBER_DISCOUNT" IS '회원제 할인률';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_RES_MIN_TIME" IS '최소예약시간';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_RES_MIN_NUMBER" IS '최소예약인원';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_RES_MAX_NUMBER" IS '최대수용인원';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_RESERV_TIME_SAT" IS '예약가능 시간대(토요일)';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_RESERV_TIME_SUN" IS '예약가능 시간대(일요일)';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_REFUND_DATE_BEFORE" IS '환불 가능일';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_REFUND_RATE" IS '환불률';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_PAYMENT_TIME_LIMIT" IS '온라인결제 제한시간';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_RENTING_METHOD" IS '대관료 책정방식(시간/패키지-time/package)';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_SHORT_DESCRIPTION" IS '한줄설명';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_FACILITY_INFO" IS '시설안내';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_PRECAUTIONS" IS '주의사항';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_REFUND_POKICY" IS '환불규정';
 
   COMMENT ON COLUMN "ASA_RENTAL"."RENT_AVAILABLE_TIME" IS '예약가능시간';
 
   COMMENT ON TABLE "ASA_RENTAL"  IS '대관/대여 프로그램 정보';
--------------------------------------------------------
--  DDL for Index ASA_RENTAL_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_RENTAL_PK" ON "ASA_RENTAL" ("RENT_ID");
--------------------------------------------------------
--  Constraints for Table ASA_RENTAL
--------------------------------------------------------

  ALTER TABLE "ASA_RENTAL" ADD CONSTRAINT "ASA_RENTAL_PK" PRIMARY KEY ("RENT_ID") ENABLE;
 
  ALTER TABLE "ASA_RENTAL" MODIFY ("RENT_ID" NOT NULL ENABLE);
  
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------     
--------------------------------------------------------
--  DDL for Table ASA_RENTAL_RESERVATION
--------------------------------------------------------

  CREATE TABLE "ASA_RENTAL_RESERVATION" 
   (	"RESERV_ID" VARCHAR2(50), 
	"RESERV_RENT_ID" NUMBER, 
	"RESERV_NAME" VARCHAR2(50), 
	"RESERV_POSITION" VARCHAR2(50), 
	"RESERV_TEL" VARCHAR2(50), 
	"RESERV_HP" VARCHAR2(50), 
	"RESERV_EMAIL" VARCHAR2(200), 
	"RESERV_ORGANIZATION" VARCHAR2(200), 
	"RESERV_ORG_DIV_CODE" VARCHAR2(50), 
	"RESERV_ZIPCODE" VARCHAR2(50), 
	"RESERV_ADDRESS" VARCHAR2(400), 
	"RESERV_ADDRESS_DETAIL" VARCHAR2(400), 
	"RESERV_TOTAL" NUMBER DEFAULT 0, 
	"RESERV_DATE" VARCHAR2(20), 
	"RESERV_TIME" VARCHAR2(1000), 
	"RESERV_TIME_ADD" NUMBER DEFAULT 0, 
	"RESERV_PAID_TYPE" VARCHAR2(50), 
	"RESERV_PRICE" NUMBER DEFAULT 0, 
	"RESERV_DISCOUNT_RATE" NUMBER DEFAULT 0, 
	"RESERV_PAYMENT_AMOUNT" NUMBER DEFAULT 0, 
	"RESERV_TITLE" VARCHAR2(500), 
	"RESERV_CONTENT" VARCHAR2(4000 CHAR), 
	"RESERV_PARKING" NUMBER DEFAULT 0, 
	"RESERV_FILE" NUMBER, 
	"RESERV_STATUS" VARCHAR2(50), 
	"RESERV_MEMO" VARCHAR2(4000 CHAR), 
	"RESERV_REGDATE" DATE, 
	"RESERV_MEMBERSHIP" NUMBER DEFAULT 0, 
	"RESERV_REQUESTS" VARCHAR2(1000 CHAR), 
	"RESERV_USER_ID" VARCHAR2(50), 
	"RESERV_USAGE_TIME" NUMBER, 
	"RESERV_CANCEL_DATE" VARCHAR2(20), 
	"RESERV_APPROV_DATE" VARCHAR2(20), 
	"RESERV_PAYMENT_DATE" VARCHAR2(20), 
	"RESERV_PAY_CANCEL_DATE" VARCHAR2(20), 
	"RESERV_TID" VARCHAR2(50)
   ) ;
 
   
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_ID" IS '신청정보 아이디';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_RENT_ID" IS '대관대여 아이디';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_NAME" IS '신청자 명';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_POSITION" IS '신청자 직위/직책';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_TEL" IS '신청자 전화번호';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_HP" IS '신청자 휴대폰번호';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_EMAIL" IS '신청자 이메일주소';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_ORGANIZATION" IS '신청기관명';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_ORG_DIV_CODE" IS '신청기관 구분 코드';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_ZIPCODE" IS '신청자 우편번호';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_ADDRESS" IS '신청자 주소';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_ADDRESS_DETAIL" IS '신청자 상세주소';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_TOTAL" IS '사용 인원';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_DATE" IS '예약일';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_TIME" IS '예약시간';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_TIME_ADD" IS '추가시간';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_PAID_TYPE" IS '결제방법 코드';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_PRICE" IS '대관/대여료';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_DISCOUNT_RATE" IS '할인률';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_PAYMENT_AMOUNT" IS '결제예정금액';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_TITLE" IS '행사명';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_CONTENT" IS '행사/전시 내용';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_PARKING" IS '주차인원';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_FILE" IS '서업자등록증 첨부파일 번호';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_STATUS" IS '신청상태';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_MEMO" IS '비고';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_REGDATE" IS '등록일';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_MEMBERSHIP" IS '멤버쉽가입여부';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_REQUESTS" IS '요청사항';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_USER_ID" IS '신청자 아이디';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_USAGE_TIME" IS '사용시간';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_CANCEL_DATE" IS '예약취소날짜';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_APPROV_DATE" IS '승인날짜';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_PAYMENT_DATE" IS '결제날짜';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_PAY_CANCEL_DATE" IS '결제취소날짜';
 
   COMMENT ON COLUMN "ASA_RENTAL_RESERVATION"."RESERV_TID" IS '온라인 결제 코드';
 
   COMMENT ON TABLE "ASA_RENTAL_RESERVATION"  IS '대관/대여 예약정보';
--------------------------------------------------------
--  DDL for Index ASA_RENTAL_RESERV_PK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_RENTAL_RESERV_PK1" ON "ASA_RENTAL_RESERVATION" ("RESERV_ID");
--------------------------------------------------------
--  Constraints for Table ASA_RENTAL_RESERVATION
--------------------------------------------------------

  ALTER TABLE "ASA_RENTAL_RESERVATION" ADD CONSTRAINT "ASA_RENTAL_RESERV_PK" PRIMARY KEY ("RESERV_ID") ENABLE;
 
  ALTER TABLE "ASA_RENTAL_RESERVATION" MODIFY ("RESERV_ID" NOT NULL ENABLE);
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    
--------------------------------------------------------
--  DDL for Table ASA_RENTAL_CLOSE_TIME
--------------------------------------------------------

  CREATE TABLE "ASA_RENTAL_CLOSE_TIME" 
   (	"RESERV_DATE" VARCHAR2(20), 
	"RESERV_CLOSE_TIME" VARCHAR2(50), 
	"RENT_ID" NUMBER
   ) ;
 

   COMMENT ON COLUMN "ASA_RENTAL_CLOSE_TIME"."RESERV_DATE" IS '예약일자';
 
   COMMENT ON COLUMN "ASA_RENTAL_CLOSE_TIME"."RESERV_CLOSE_TIME" IS '예약시간';
 
   COMMENT ON COLUMN "ASA_RENTAL_CLOSE_TIME"."RENT_ID" IS '대관 아이디';
 
   COMMENT ON TABLE "ASA_RENTAL_CLOSE_TIME"  IS '대관 시간 막음처리 데이터';
--------------------------------------------------------
--  DDL for Index ASA_RENTAL_CLOSE_TIME_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_RENTAL_CLOSE_TIME_PK" ON "ASA_RENTAL_CLOSE_TIME" ("RESERV_DATE", "RESERV_CLOSE_TIME", "RENT_ID");
--------------------------------------------------------
--  Constraints for Table ASA_RENTAL_CLOSE_TIME
--------------------------------------------------------

  ALTER TABLE "ASA_RENTAL_CLOSE_TIME" ADD CONSTRAINT "ASA_RENTAL_CLOSE_TIME_PK" PRIMARY KEY ("RESERV_DATE", "RESERV_CLOSE_TIME", "RENT_ID") ENABLE;
 
  ALTER TABLE "ASA_RENTAL_CLOSE_TIME" MODIFY ("RESERV_DATE" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_RENTAL_CLOSE_TIME" MODIFY ("RESERV_CLOSE_TIME" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_RENTAL_CLOSE_TIME" MODIFY ("RENT_ID" NOT NULL ENABLE);
  
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    
--------------------------------------------------------
--  DDL for Table ASA_VIEWING_RESERVATION
--------------------------------------------------------

  CREATE TABLE "ASA_VIEWING_RESERVATION" 
   (	"RESERV_ID" NUMBER, 
	"RESERV_NAME" VARCHAR2(50), 
	"RESERV_GROUP" VARCHAR2(200), 
	"RESERV_ADDRESS" VARCHAR2(400), 
	"RESERV_EMAIL" VARCHAR2(200), 
	"RESERV_HP" VARCHAR2(50), 
	"RESERV_PEOPLE" NUMBER, 
	"RESERV_DATE" VARCHAR2(20), 
	"RESERV_STIME" VARCHAR2(20), 
	"RESERV_ETIME" VARCHAR2(20), 
	"RESERV_REGDATE" DATE, 
	"RESERV_MEMO" VARCHAR2(4000), 
	"RESERV_STATUS" VARCHAR2(50), 
	"RESERV_AGREE" NUMBER
   ) ;
 

   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_ID" IS 'PK';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_NAME" IS '신청자명';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_GROUP" IS '신청자 소속 그룹';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_ADDRESS" IS '신청자 주소';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_EMAIL" IS '신청자 이메일';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_HP" IS '신청자 연락처';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_PEOPLE" IS '관람인원';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_DATE" IS '관람 날짜';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_STIME" IS '관람 시작시간';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_ETIME" IS '관람 종료시간';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_REGDATE" IS '신청일자';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_MEMO" IS '메모';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_STATUS" IS '진행상태';
 
   COMMENT ON COLUMN "ASA_VIEWING_RESERVATION"."RESERV_AGREE" IS '개인정보이용동의';
 
   COMMENT ON TABLE "ASA_VIEWING_RESERVATION"  IS '관람 신청정보  ';

--------------------------------------------------------
--  DDL for Index VIEWING_RESERVATION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_VIEWING_RESERV_PK" ON "ASA_VIEWING_RESERVATION" ("RESERV_ID");
--------------------------------------------------------
--  Constraints for Table ASA_VIEWING_RESERVATION
--------------------------------------------------------

  ALTER TABLE "ASA_VIEWING_RESERVATION" MODIFY ("RESERV_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_VIEWING_RESERVATION" ADD CONSTRAINT "ASA_VIEWING_RESERV_PK" PRIMARY KEY ("RESERV_ID") ENABLE;
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    

--------------------------------------------------------
--  DDL for Table ASA_EDUCATION_RESERVATION
--------------------------------------------------------

  CREATE TABLE "ASA_EDUCATION_RESERVATION" 
   (	"RESERV_ID" NUMBER, 
	"RESERV_NAME" VARCHAR2(50), 
	"RESERV_GROUP" VARCHAR2(200), 
	"RESERV_ADDRESS" VARCHAR2(400), 
	"RESERV_EMAIL" VARCHAR2(200), 
	"RESERV_HP" VARCHAR2(50), 
	"RESERV_PEOPLE" NUMBER, 
	"RESERV_DATE" VARCHAR2(20), 
	"RESERV_STIME" VARCHAR2(20), 
	"RESERV_ETIME" VARCHAR2(20), 
	"RESERV_REGDATE" DATE, 
	"RESERV_MEMO" VARCHAR2(4000), 
	"RESERV_STATUS" VARCHAR2(50), 
	"RESERV_AGREE" NUMBER
   ) ;
 

   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_ID" IS 'PK';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_NAME" IS '신청자명';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_GROUP" IS '신청자 소속 그룹';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_ADDRESS" IS '신청자 주소';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_EMAIL" IS '신청자 이메일';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_HP" IS '신청자 연락처';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_PEOPLE" IS '교육 인원';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_DATE" IS '교육 날짜';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_STIME" IS '교육 시작시간';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_ETIME" IS '교육 종료시간';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_REGDATE" IS '신청일자';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_MEMO" IS '메모';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_STATUS" IS '진행상태';
 
   COMMENT ON COLUMN "ASA_EDUCATION_RESERVATION"."RESERV_AGREE" IS '개인정보이용동의';
 
   COMMENT ON TABLE "ASA_EDUCATION_RESERVATION"  IS '교육프로그램 신청정보';
   
--------------------------------------------------------
--  DDL for Index ASA_EDUCATION_RESERVATION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_EDUCATION_RESERV_PK" ON "ASA_EDUCATION_RESERVATION" ("RESERV_ID");
--------------------------------------------------------
--  Constraints for Table ASA_EDUCATION_RESERVATION
--------------------------------------------------------

  ALTER TABLE "ASA_EDUCATION_RESERVATION" ADD CONSTRAINT "ASA_EDUCATION_RESERV_PK" PRIMARY KEY ("RESERV_ID") ENABLE;
 
  ALTER TABLE "ASA_EDUCATION_RESERVATION" MODIFY ("RESERV_ID" NOT NULL ENABLE);
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    

--------------------------------------------------------
--  DDL for Table ASA_VOLUNTEER_RESERVATION
--------------------------------------------------------

  CREATE TABLE "ASA_VOLUNTEER_RESERVATION" 
   (	"RESERV_ID" NUMBER, 
	"RESERV_NAME" VARCHAR2(50), 
	"RESERV_GROUP" VARCHAR2(200), 
	"RESERV_ADDRESS" VARCHAR2(400), 
	"RESERV_EMAIL" VARCHAR2(200), 
	"RESERV_HP" VARCHAR2(50), 
	"RESERV_PEOPLE" NUMBER, 
	"RESERV_PURPOSE" VARCHAR2(4000), 
	"RESERV_DATE" VARCHAR2(20), 
	"RESERV_STIME" VARCHAR2(20), 
	"RESERV_ETIME" VARCHAR2(20), 
	"RESERV_SUGGEST" VARCHAR2(4000), 
	"RESERV_REGDATE" DATE, 
	"RESERV_STATUS" VARCHAR2(50), 
	"RESERV_AGREE" NUMBER
   ); 
 

   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_ID" IS 'PK';

   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_NAME" IS '이름';
 
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_GROUP" IS '소속단체';
 
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_ADDRESS" IS '주소';
 
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_EMAIL" IS '이메일';
    
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_HP" IS '연락처';
 
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_PEOPLE" IS '인원';
 
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_PURPOSE" IS '목적';
 
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_DATE" IS '봉사일';
 
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_STIME" IS '봉사시작시간';
 
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_ETIME" IS '봉사종료시간';
 
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_SUGGEST" IS '건의사항';
 
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_REGDATE" IS '등록일';
 
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_STATUS" IS '진행상태';
 
   COMMENT ON COLUMN "ASA_VOLUNTEER_RESERVATION"."RESERV_AGREE" IS '갠인정보이용동의';
 
   COMMENT ON TABLE "ASA_VOLUNTEER_RESERVATION"  IS '자원봉사 신청정보';
   
--------------------------------------------------------
--  DDL for Index ASA_VOLUNTEER_RESERV_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_VOLUNTEER_RESERV_PK" ON "ASA_VOLUNTEER_RESERVATION" ("RESERV_ID");
--------------------------------------------------------
--  Constraints for Table ASA_VOLUNTEER_RESERVATION
--------------------------------------------------------

  ALTER TABLE "ASA_VOLUNTEER_RESERVATION" ADD CONSTRAINT "ASA_VOLUNTEER_RESERV_PK" PRIMARY KEY ("RESERV_ID") ENABLE;
 
  ALTER TABLE "ASA_VOLUNTEER_RESERVATION" MODIFY ("RESERV_ID" NOT NULL ENABLE);
  

  ----------------------------------------------------------------------------------------------------------------   
---------------------------------------------------------------------------------------------------------------- 

--------------------------------------------------------
--  DDL for Table ASA_ARCHIVE_CATEGORY
--------------------------------------------------------

  CREATE TABLE "ASA_ARCHIVE_CATEGORY" 
   (	"CAT_ID" VARCHAR2(50), 
	"CAT_NAME" VARCHAR2(200), 
	"CAT_PAGE_SIZE" NUMBER, 
	"CAT_USER_LIST_TEMPLATE" VARCHAR2(100), 
	"CAT_USER_VIEW_TEMPLATE" VARCHAR2(100), 
	"CAT_ADMIN_LIST_TEMPLATE" VARCHAR2(100), 
	"CAT_ADMIN_FORM_TEMPLATE" VARCHAR2(100), 
	"CAT_META_CODE1" VARCHAR2(50), 
	"CAT_META_CODE2" VARCHAR2(50), 
	"CAT_META_CODE3" VARCHAR2(50), 
	"CAT_SORT_ORDER" VARCHAR2(50), 
	"CAT_SORT_DIRECTION" VARCHAR2(50), 
	"CAT_CUSTOM_TYPE" VARCHAR2(50), 
	"CAT_REGDATE" DATE,
	"CAT_SUPPORT_SELECT1" NUMBER, 
	"CAT_SUPPORT_NURI" NUMBER, 
	"CAT_SUPPORT_RECOMMEND" NUMBER, 
	"CAT_SUPPORT_FIXING" NUMBER, 
	"CAT_FIXING_NUM" NUMBER, 
	"CAT_SUPPORT_IMAGE" NUMBER, 
	"CAT_SUPPORT_THUMBNAIL" NUMBER, 
	"CAT_THUMBNAIL_CROP" NUMBER, 
	"CAT_THUMBNAIL_WIDTH" NUMBER, 
	"CAT_THUMBNAIL_HEIGHT" NUMBER, 
	"CAT_UPLOAD_FILE_NAME" NUMBER, 
	"CAT_UPLOAD_SIZE_MAX" NUMBER
   ) ;
 

   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_ID" IS 'PK';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_NAME" IS '카테고리 이름';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_PAGE_SIZE" IS '페이지 크기';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY.CAT_USER_LIST_TEMPLATE" IS '사용자 목록 템플릿';
	
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY.CAT_USER_VIEW_TEMPLATE" IS '사용자 뷰 템플릿';
	
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY.CAT_ADMIN_LIST_TEMPLATE" IS '관리자 목록 템플릿';
	
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY.CAT_ADMIN_FORM_TEMPLATE" IS '관리자 폼 템플릿';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_META_CODE1" IS '메타코드1';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_META_CODE2" IS '메타코드2';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_META_CODE3" IS '메타코드3';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_SORT_ORDER" IS '정렬필드';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_SORT_DIRECTION" IS '정렬방향';

   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_CUSTOM_TYPE" IS '아카이브 타입';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_REGDATE" IS '등록일';
   
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_SUPPORT_SELECT1" IS '추출1 기능 사용여부';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_SUPPORT_NURI" IS '공공누리 사용여부';

   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_SUPPORT_RECOMMEND" IS '추천기능 사용여부';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_SUPPORT_FIXING" IS '상단 별도분리 출력 사용여부';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_FIXING_NUM" IS '상단 별도분리 출력 갯수';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_SUPPORT_IMAGE" IS '대표이미지 사용여부';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_SUPPORT_THUMBNAIL" IS '썸네일 사용여부';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_THUMBNAIL_CROP" IS '썸네일 생성시 이미지 자르기 여부';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_THUMBNAIL_WIDTH" IS '썸네일 생성시 가로길이';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_THUMBNAIL_HEIGHT" IS '썸네일 생성시 세로길이';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_UPLOAD_FILE_NAME" IS '첨부가능한 파일갯수';
 
   COMMENT ON COLUMN "ASA_ARCHIVE_CATEGORY"."CAT_UPLOAD_SIZE_MAX" IS '첨부파일 업로드 사이즈 제한 / MB단위';
 
   COMMENT ON TABLE "ASA_ARCHIVE_CATEGORY"  IS '아카이브 카테고리 정보';
   
   
--------------------------------------------------------
--  DDL for Index ASA_ARCHIVE_CATEGORY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_ARCHIVE_CATEGORY_PK" ON "ASA_ARCHIVE_CATEGORY" ("CAT_ID");
--------------------------------------------------------
--  Constraints for Table ASA_ARCHIVE_CATEGORY
--------------------------------------------------------

  ALTER TABLE "ASA_ARCHIVE_CATEGORY" ADD CONSTRAINT "ASA_ARCHIVE_CATEGORY_PK" PRIMARY KEY ("CAT_ID") ENABLE;
 
  ALTER TABLE "ASA_ARCHIVE_CATEGORY" MODIFY ("CAT_ID" NOT NULL ENABLE);
  
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    

--------------------------------------------------------
--  DDL for Table ASA_ARCHIVE
--------------------------------------------------------

  CREATE TABLE "ASA_ARCHIVE" 
   (	"ARC_ID" NUMBER, 
	"ARC_CATEGORY" VARCHAR2(50), 
	"ARC_TITLE" VARCHAR2(500), 
	"ARC_PRODUCT_DIV" VARCHAR2(100), 
	"ARC_CUSTOM_TYPE" VARCHAR2(50), 
	"ARC_CONTENT" CLOB, 
	"ARC_PLAN_TEXT" VARCHAR2(4000), 
	"MEM_ID" VARCHAR2(50), 
	"ARC_REGDATE" DATE, 
	"ARC_LAST_UPDATE" DATE, 
	"ARC_HIT" NUMBER DEFAULT 0, 
	"ARC_RECOMMEND" NUMBER DEFAULT 0, 
	"ARC_NURI" VARCHAR2(50), 
	"ARC_USE" NUMBER, 
	"ARC_THUMB" NUMBER, 
	"ARC_META_CODE1" VARCHAR2(50), 
	"ARC_META_CODE2" VARCHAR2(50), 
	"ARC_META_CODE3" VARCHAR2(50), 
	"ARC_MEDIA" VARCHAR2(200), 
	"ARC_YEAR" VARCHAR2(50), 
	"ARC_ADVERTISER" VARCHAR2(100), 
	"ARC_PRODUCT" VARCHAR2(100), 
	"ARC_ACTOR" VARCHAR2(50), 
	"ARC_PERIOD" VARCHAR2(50), 
	"ARC_THUMB_TEXT" VARCHAR2(200), 
	"ARC_SELECTED1" NUMBER, 
	"ARC_TAG" VARCHAR2(500)
   ) ;
 

   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_ID" IS 'PK';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_CATEGORY" IS '카테고리 아이디';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_TITLE" IS '제목';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_PRODUCT_DIV" IS '제품분류';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_CUSTOM_TYPE" IS '커스텀 타입분류';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_CONTENT" IS '내용';

   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_PLAN_TEXT" IS '내용 택스트';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."MEM_ID" IS '작성자 아이디';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_REGDATE" IS '등록일자';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_LAST_UPDATE" IS '마지막 수정일자';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_HIT" IS '조회수';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_RECOMMEND" IS '추천수';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_NURI" IS '공공누리 코드';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_USE" IS '게시여부';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_THUMB" IS '썸네일 파일 번호';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_META_CODE1" IS '메타코드1';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_META_CODE2" IS '메타코드2';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_META_CODE3" IS '메타코드3';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_MEDIA" IS '매체';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_YEAR" IS '제작년도';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_ADVERTISER" IS '광고주';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_PRODUCT" IS '제품명';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_ACTOR" IS '배우,성우';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_PERIOD" IS '기간';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_THUMB_TEXT" IS '썸내일 대체텍스트';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_SELECTED1" IS '선택여부';
 
   COMMENT ON COLUMN "ASA_ARCHIVE"."ARC_TAG" IS '테그(키워드)';
 
   COMMENT ON TABLE "ASA_ARCHIVE"  IS '디지털 아카이브';

--------------------------------------------------------
--  DDL for Index ASA_ARCHIVE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_ARCHIVE_PK" ON "ASA_ARCHIVE" ("ARC_ID");
--------------------------------------------------------
--  Constraints for Table ASA_ARCHIVE
--------------------------------------------------------

  ALTER TABLE "ASA_ARCHIVE" ADD CONSTRAINT "ASA_ARCHIVE_PK" PRIMARY KEY ("ARC_ID") ENABLE;
 
  ALTER TABLE "ASA_ARCHIVE" MODIFY ("ARC_ID" NOT NULL ENABLE);
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    
--------------------------------------------------------
--  DDL for Table ASA_SPACE
--------------------------------------------------------

  CREATE TABLE "ASA_SPACE" 
   (	"ARC_ID" NUMBER,
	"SPA_LOCATION" VARCHAR2(500), 
	"SPA_TEL" VARCHAR2(50), 
	"SPA_USE_HOURS" VARCHAR2(200), 
	"SPA_BUSINESS_HOURS" VARCHAR2(200), 
	"SPA_AGENCY" VARCHAR2(100), 
	"SPA_SITE_URL" VARCHAR2(300)
   ) ;
 

   COMMENT ON COLUMN "ASA_SPACE"."ARC_ID" IS '아카이브 아이디';
 
   COMMENT ON COLUMN "ASA_SPACE"."SPA_LOCATION" IS '위치';
 
   COMMENT ON COLUMN "ASA_SPACE"."SPA_TEL" IS '연락처';
 
   COMMENT ON COLUMN "ASA_SPACE"."SPA_USE_HOURS" IS '이용시간';
 
   COMMENT ON COLUMN "ASA_SPACE"."SPA_BUSINESS_HOURS" IS '업무시간';
 
   COMMENT ON COLUMN "ASA_SPACE"."SPA_AGENCY" IS '운영기관';
 
   COMMENT ON COLUMN "ASA_SPACE"."SPA_SITE_URL" IS '홈페이지 url';
 
   COMMENT ON TABLE "ASA_SPACE"  IS '공간정보';
   
--------------------------------------------------------
--  DDL for Index ASA_SPACE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_SPACE_PK" ON "ASA_SPACE" ("ARC_ID");
--------------------------------------------------------
--  Constraints for Table ASA_SPACE
--------------------------------------------------------

  ALTER TABLE "ASA_SPACE" ADD CONSTRAINT "ASA_SPACE_PK" PRIMARY KEY ("ARC_ID") ENABLE;
 
  ALTER TABLE "ASA_SPACE" MODIFY ("ARC_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ASA_SPACE
--------------------------------------------------------

  ALTER TABLE "ASA_SPACE" ADD CONSTRAINT "ASA_SPACE_FK1" FOREIGN KEY ("ARC_ID")
	  REFERENCES "ASA_ARCHIVE" ("ARC_ID") ON DELETE CASCADE ENABLE   ;
   
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    
--------------------------------------------------------
--  DDL for Table ASA_RESEARCH
--------------------------------------------------------

  CREATE TABLE "ASA_RESEARCH" 
   (	"ARC_ID" NUMBER,
	"RES_RESEARCHER" VARCHAR2(200), 
	"RES_YEAR" VARCHAR2(50), 
	"RES_SUPPORT" VARCHAR2(200), 
	"RES_SUPP_PROJECT" VARCHAR2(200)
   ) ;
 

   COMMENT ON COLUMN "ASA_RESEARCH"."ARC_ID" IS '아카이브아이디';
 
   COMMENT ON COLUMN "ASA_RESEARCH"."RES_RESEARCHER" IS '연구자,단체명';
 
   COMMENT ON COLUMN "ASA_RESEARCH"."RES_YEAR" IS '연도';
 
   COMMENT ON COLUMN "ASA_RESEARCH"."RES_SUPPORT" IS '지원처';
 
   COMMENT ON COLUMN "ASA_RESEARCH"."RES_SUPP_PROJECT" IS '지원사업명';
 
   COMMENT ON TABLE "ASA_RESEARCH"  IS '연구자료';
   
--------------------------------------------------------
--  DDL for Index ASA_RESEARCH_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_RESEARCH_PK" ON "ASA_RESEARCH" ("ARC_ID");
--------------------------------------------------------
--  Constraints for Table ASA_RESEARCH
--------------------------------------------------------

  ALTER TABLE "ASA_RESEARCH" ADD CONSTRAINT "ASA_RESEARCH_PK" PRIMARY KEY ("ARC_ID") ENABLE;
 
  ALTER TABLE "ASA_RESEARCH" MODIFY ("ARC_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ASA_RESEARCH
--------------------------------------------------------

  ALTER TABLE "ASA_RESEARCH" ADD CONSTRAINT "ASA_RESEARCH_FK1" FOREIGN KEY ("ARC_ID")
	  REFERENCES "ASA_ARCHIVE" ("ARC_ID") ON DELETE CASCADE ENABLE;
   
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    

--------------------------------------------------------
--  DDL for Table ASA_ADVERTISING
--------------------------------------------------------

  CREATE TABLE "ASA_ADVERTISING" 
   (	"ARC_ID" NUMBER, 
	"ADT_MANUFACTURE_YEAR" VARCHAR2(20), 
	"ADT_PRODUCER" VARCHAR2(50), 
	"ADT_DIRECTOR" VARCHAR2(50), 
	"ADT_BACK_MUSIC" VARCHAR2(200), 
	"ADT_PLAN_INTENTION" VARCHAR2(2000), 
	"ADT_COMPOSITION" VARCHAR2(2000), 
	"ADT_PRODUCTION_REVIEW" VARCHAR2(2000), 
	"ADT_TV_CF" VARCHAR2(4000), 
	"ADT_RADIO_CF" VARCHAR2(4000), 
	"ADT_VIDEO" NUMBER, 
	"ADT_RADIO" NUMBER, 
	"ADT_MEDIA" VARCHAR2(100), 
	"ADT_AWARD_TYPE" VARCHAR2(50), 
	"ADT_WINNER" VARCHAR2(200), 
	"ADT_UCC_URL" VARCHAR2(200)
   ) ;
 

   COMMENT ON COLUMN "ASA_ADVERTISING"."ARC_ID" IS '아카이브아이디';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_MANUFACTURE_YEAR" IS '제작년도';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_PRODUCER" IS '광고회사/제작사';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_DIRECTOR" IS '감독';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_BACK_MUSIC" IS '배경음악';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_PLAN_INTENTION" IS '기획의도';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_COMPOSITION" IS '구성 및 표현';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_PRODUCTION_REVIEW" IS '제작후기';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_TV_CF" IS 'TV CF Copy';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_RADIO_CF" IS 'Radio CF Copy';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_VIDEO" IS '동영상';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_RADIO" IS '라디오';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_MEDIA" IS '매체';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_AWARD_TYPE" IS '수상종류';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_WINNER" IS '수상자';
 
   COMMENT ON COLUMN "ASA_ADVERTISING"."ADT_UCC_URL" IS 'ucc url';
 
   COMMENT ON TABLE "ASA_ADVERTISING"  IS '광고자료';
--------------------------------------------------------
--  DDL for Index ASA_ADVERTISING_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_ADVERTISING_PK" ON "ASA_ADVERTISING" ("ARC_ID");
--------------------------------------------------------
--  Constraints for Table ASA_ADVERTISING
--------------------------------------------------------

  ALTER TABLE "ASA_ADVERTISING" ADD CONSTRAINT "ASA_ADVERTISING_PK" PRIMARY KEY ("ARC_ID") ENABLE;
 
  ALTER TABLE "ASA_ADVERTISING" MODIFY ("ARC_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ASA_ADVERTISING
--------------------------------------------------------

  ALTER TABLE "ASA_ADVERTISING" ADD CONSTRAINT "ASA_ADVERTISING_FK1" FOREIGN KEY ("ARC_ID")
	  REFERENCES "ASA_ARCHIVE" ("ARC_ID") ON DELETE CASCADE ENABLE;

  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    
--------------------------------------------------------
--  DDL for Table ASA_POLICY
--------------------------------------------------------

  CREATE TABLE "ASA_POLICY" 
   (	"ARC_ID" NUMBER, 
	"POLI_SHORT_DESCRIPTION" VARCHAR2(1000)
   ) ;
 

   COMMENT ON COLUMN "ASA_POLICY"."ARC_ID" IS '아카이브아이디';
 
   COMMENT ON COLUMN "ASA_POLICY"."POLI_SHORT_DESCRIPTION" IS '간단설명';
 
   COMMENT ON TABLE "ASA_POLICY"  IS '정책자료';
--------------------------------------------------------
--  DDL for Index ASA_POLICY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_POLICY_PK" ON "ASA_POLICY" ("ARC_ID");
--------------------------------------------------------
--  Constraints for Table ASA_POLICY
--------------------------------------------------------

  ALTER TABLE "ASA_POLICY" ADD CONSTRAINT "ASA_POLICY_PK" PRIMARY KEY ("ARC_ID") ENABLE;
 
  ALTER TABLE "ASA_POLICY" MODIFY ("ARC_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ASA_POLICY
--------------------------------------------------------

  ALTER TABLE "ASA_POLICY" ADD CONSTRAINT "ASA_POLICY_FK1" FOREIGN KEY ("ARC_ID")
	  REFERENCES "ASA_ARCHIVE" ("ARC_ID") ON DELETE CASCADE ENABLE;
	  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    

--------------------------------------------------------
--  DDL for Table ASA_ADMIN_ACCESS_LOG
--------------------------------------------------------

  CREATE TABLE "ASA_ADMIN_ACCESS_LOG" 
   (	"LOG_ID" NUMBER, 
	"ADMIN_ID" VARCHAR2(50), 
	"ADMIN_NAME" VARCHAR2(50), 
	"LOG_REMOTE_IP" VARCHAR2(50), 
	"SITE_ID" VARCHAR2(50), 
	"LOG_REGDATE" DATE
   ) ;
 

   COMMENT ON COLUMN "ASA_ADMIN_ACCESS_LOG"."LOG_ID" IS '로그 아이디';
 
   COMMENT ON COLUMN "ASA_ADMIN_ACCESS_LOG"."ADMIN_ID" IS '관리자 아이디';
 
   COMMENT ON COLUMN "ASA_ADMIN_ACCESS_LOG"."ADMIN_NAME" IS '관리자 이름';
 
   COMMENT ON COLUMN "ASA_ADMIN_ACCESS_LOG"."LOG_REMOTE_IP" IS '접속 아이디';
 
   COMMENT ON COLUMN "ASA_ADMIN_ACCESS_LOG"."SITE_ID" IS '사이트 아이디';
 
   COMMENT ON COLUMN "ASA_ADMIN_ACCESS_LOG"."LOG_REGDATE" IS '등록일자';
 
   COMMENT ON TABLE "ASA_ADMIN_ACCESS_LOG"  IS '관리자 접속 로그';
   
--------------------------------------------------------
--  DDL for Index ASA_ADMIN_LOGIN_LOG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_ADMIN_LOGIN_LOG_PK" ON "ASA_ADMIN_ACCESS_LOG" ("LOG_ID");
--------------------------------------------------------
--  Constraints for Table ASA_ADMIN_ACCESS_LOG
--------------------------------------------------------

  ALTER TABLE "ASA_ADMIN_ACCESS_LOG" ADD CONSTRAINT "ASA_ADMIN_LOGIN_LOG_PK" PRIMARY KEY ("LOG_ID") ENABLE;
 
  ALTER TABLE "ASA_ADMIN_ACCESS_LOG" MODIFY ("LOG_ID" NOT NULL ENABLE); 

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------   
--------------------------------------------------------
--  DDL for Table ASA_ADMIN_INQUIRE_LOG
--------------------------------------------------------

  CREATE TABLE "ASA_ADMIN_INQUIRE_LOG" 
   (	"INQ_ID" NUMBER, 
	"INQ_WORKER_ID" VARCHAR2(50), 
	"INQ_WORKER_NAME" VARCHAR2(50), 
	"INQ_TARGET_ID" VARCHAR2(50), 
	"INQ_TARGET_NAME" VARCHAR2(50), 
	"INQ_URL" VARCHAR2(200), 
	"INQ_WORK" VARCHAR2(100), 
	"INQ_REMORT_IP" VARCHAR2(100), 
	"INQ_REGDATE" DATE
   ) ;
 

   COMMENT ON COLUMN "ASA_ADMIN_INQUIRE_LOG"."INQ_ID" IS '조회 로그 아이디';
 
   COMMENT ON COLUMN "ASA_ADMIN_INQUIRE_LOG"."INQ_WORKER_ID" IS '접속자 아이디';
 
   COMMENT ON COLUMN "ASA_ADMIN_INQUIRE_LOG"."INQ_WORKER_NAME" IS '접속자 이름';
 
   COMMENT ON COLUMN "ASA_ADMIN_INQUIRE_LOG"."INQ_TARGET_ID" IS '피검색자 아이디';
 
   COMMENT ON COLUMN "ASA_ADMIN_INQUIRE_LOG"."INQ_TARGET_NAME" IS '피검색자 이름';
 
   COMMENT ON COLUMN "ASA_ADMIN_INQUIRE_LOG"."INQ_URL" IS '요청 url';
 
   COMMENT ON COLUMN "ASA_ADMIN_INQUIRE_LOG"."INQ_WORK" IS '작업내역';
 
   COMMENT ON COLUMN "ASA_ADMIN_INQUIRE_LOG"."INQ_REMORT_IP" IS '접속자 아이피';
 
   COMMENT ON COLUMN "ASA_ADMIN_INQUIRE_LOG"."INQ_REGDATE" IS '작업일시';
 
   COMMENT ON TABLE "ASA_ADMIN_INQUIRE_LOG"  IS '관리자 회원 정보 조회, 수정, 삭제 내역 로그';
   
--------------------------------------------------------
--  DDL for Index ASA_ADMIN_INQUIRE_LOG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_ADMIN_INQUIRE_LOG_PK" ON "ASA_ADMIN_INQUIRE_LOG" ("INQ_ID");
--------------------------------------------------------
--  Constraints for Table ASA_ADMIN_INQUIRE_LOG
--------------------------------------------------------

  ALTER TABLE "ASA_ADMIN_INQUIRE_LOG" ADD CONSTRAINT "ASA_ADMIN_INQUIRE_LOG_PK" PRIMARY KEY ("INQ_ID") ENABLE;
 
  ALTER TABLE "ASA_ADMIN_INQUIRE_LOG" MODIFY ("INQ_ID" NOT NULL ENABLE) ;  
   
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------   
--------------------------------------------------------
--  DDL for Table ASA_USER_INQUIRE_LOG
--------------------------------------------------------

  CREATE TABLE "ASA_USER_INQUIRE_LOG" 
   (	"INQ_ID" NUMBER, 
	"INQ_WORKER_ID" VARCHAR2(50), 
	"INQ_WORKER_NAME" VARCHAR2(50), 
	"INQ_TARGET_ID" VARCHAR2(50), 
	"INQ_TARGET_NAME" VARCHAR2(50), 
	"INQ_URL" VARCHAR2(200), 
	"INQ_WORK" VARCHAR2(100), 
	"INQ_REMORT_IP" VARCHAR2(100), 
	"INQ_REGDATE" DATE
   ) ;
 

   COMMENT ON COLUMN "ASA_USER_INQUIRE_LOG"."INQ_ID" IS '조회 로그 아이디';
 
   COMMENT ON COLUMN "ASA_USER_INQUIRE_LOG"."INQ_WORKER_ID" IS '접속자 아이디';
 
   COMMENT ON COLUMN "ASA_USER_INQUIRE_LOG"."INQ_WORKER_NAME" IS '접속자 이름';
 
   COMMENT ON COLUMN "ASA_USER_INQUIRE_LOG"."INQ_TARGET_ID" IS '피검색자 아이디';
 
   COMMENT ON COLUMN "ASA_USER_INQUIRE_LOG"."INQ_TARGET_NAME" IS '피검색자 이름';
 
   COMMENT ON COLUMN "ASA_USER_INQUIRE_LOG"."INQ_URL" IS '요청 url';
 
   COMMENT ON COLUMN "ASA_USER_INQUIRE_LOG"."INQ_WORK" IS '작업내역';
 
   COMMENT ON COLUMN "ASA_USER_INQUIRE_LOG"."INQ_REMORT_IP" IS '접속자 아이피';
 
   COMMENT ON COLUMN "ASA_USER_INQUIRE_LOG"."INQ_REGDATE" IS '작업일시';
 
   COMMENT ON TABLE "ASA_USER_INQUIRE_LOG"  IS '회원 정보 조회, 수정, 삭제 내역 로그';
--------------------------------------------------------
--  DDL for Index ASA_USER_INQUIRE_LOG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_USER_INQUIRE_LOG_PK" ON "ASA_USER_INQUIRE_LOG" ("INQ_ID");
--------------------------------------------------------
--  Constraints for Table ASA_USER_INQUIRE_LOG
--------------------------------------------------------

  ALTER TABLE "ASA_USER_INQUIRE_LOG" ADD CONSTRAINT "ASA_USER_INQUIRE_LOG_PK" PRIMARY KEY ("INQ_ID") ENABLE;
 
  ALTER TABLE "ASA_USER_INQUIRE_LOG" MODIFY ("INQ_ID" NOT NULL ENABLE);

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    
--------------------------------------------------------
--  DDL for Table ASA_API_INFO
--------------------------------------------------------

  CREATE TABLE "ASA_API_INFO" 
   (	"API_SEQ" NUMBER, 
	"API_ID" VARCHAR2(60), 
	"API_TITLE" VARCHAR2(200), 
	"API_CONTENT" VARCHAR2(4000), 
	"API_REGDATE" DATE, 
	"API_PROVIDER" VARCHAR2(50), 
	"API_DOMAIN" VARCHAR2(100), 
	"API_KEY" VARCHAR2(200), 
	"API_RETURN_TYPE" VARCHAR2(20), 
	"API_NUM_OF_ROW" NUMBER, 
	"API_TABLE_NAME" VARCHAR2(60), 
	"API_LAST_BATCH" DATE, 
	"API_INPUT_TYPE" VARCHAR2(50)
   ) ;
 

   COMMENT ON COLUMN "ASA_API_INFO"."API_SEQ" IS '연번';
 
   COMMENT ON COLUMN "ASA_API_INFO"."API_ID" IS 'API 영문명( 영문 서비스명)';
 
   COMMENT ON COLUMN "ASA_API_INFO"."API_TITLE" IS 'API 한글명';
 
   COMMENT ON COLUMN "ASA_API_INFO"."API_CONTENT" IS 'API 설명';
 
   COMMENT ON COLUMN "ASA_API_INFO"."API_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_API_INFO"."API_PROVIDER" IS 'API 제공 사이트';
 
   COMMENT ON COLUMN "ASA_API_INFO"."API_DOMAIN" IS 'API 도메인';
 
   COMMENT ON COLUMN "ASA_API_INFO"."API_KEY" IS 'API 키';
 
   COMMENT ON COLUMN "ASA_API_INFO"."API_RETURN_TYPE" IS 'API 결과 유형(XML, JSON)';
 
   COMMENT ON COLUMN "ASA_API_INFO"."API_NUM_OF_ROW" IS '한번에 가져올 결과 수';
 
   COMMENT ON COLUMN "ASA_API_INFO"."API_TABLE_NAME" IS '배치할 테이블 명';
 
   COMMENT ON COLUMN "ASA_API_INFO"."API_LAST_BATCH" IS '마지막 배치일';
 
   COMMENT ON COLUMN "ASA_API_INFO"."API_INPUT_TYPE" IS '배치실행구분(auto, manual)';
 
   COMMENT ON TABLE "ASA_API_INFO"  IS 'OPEN API 등록 정보';
--------------------------------------------------------
--  DDL for Index ASA_API_INFO_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_API_INFO_PK" ON "ASA_API_INFO" ("API_SEQ");
--------------------------------------------------------
--  Constraints for Table ASA_API_INFO
--------------------------------------------------------

  ALTER TABLE "ASA_API_INFO" ADD CONSTRAINT "ASA_API_INFO_PK" PRIMARY KEY ("API_SEQ") ENABLE;
 
  ALTER TABLE "ASA_API_INFO" MODIFY ("API_SEQ" NOT NULL ENABLE);
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    
--------------------------------------------------------
--  DDL for Table ASA_SATISFACTION
--------------------------------------------------------

  CREATE TABLE "ASA_SATISFACTION" 
   (	"MENU_ID" VARCHAR2(100), 
	"SITE_PREFIX" VARCHAR2(50), 
	"SATIS_SCORE_5" NUMBER, 
	"SATIS_SCORE_4" NUMBER, 
	"SATIS_SCORE_3" NUMBER, 
	"SATIS_SCORE_2" NUMBER, 
	"SATIS_SCORE_1" NUMBER, 
	"SATIS_LAST_PARTI_DATE" DATE, 
	"SATIS_RESET_DATE" DATE
   ) ;
 

   COMMENT ON COLUMN "ASA_SATISFACTION"."MENU_ID" IS '메뉴 아이디';
 
   COMMENT ON COLUMN "ASA_SATISFACTION"."SITE_PREFIX" IS '사이트 프리픽스';
 
   COMMENT ON COLUMN "ASA_SATISFACTION"."SATIS_SCORE_5" IS '매우만족 횟수';
 
   COMMENT ON COLUMN "ASA_SATISFACTION"."SATIS_SCORE_4" IS '만족 횟수';
 
   COMMENT ON COLUMN "ASA_SATISFACTION"."SATIS_SCORE_3" IS '보통 횟수';
 
   COMMENT ON COLUMN "ASA_SATISFACTION"."SATIS_SCORE_2" IS '불만 횟수';
 
   COMMENT ON COLUMN "ASA_SATISFACTION"."SATIS_SCORE_1" IS '매우불만 횟수';
 
   COMMENT ON COLUMN "ASA_SATISFACTION"."SATIS_LAST_PARTI_DATE" IS '마지막 참여일시';
 
   COMMENT ON COLUMN "ASA_SATISFACTION"."SATIS_RESET_DATE" IS '조사내용 리셋 일시';
 
   COMMENT ON TABLE "ASA_SATISFACTION"  IS '메뉴 만족도';
--------------------------------------------------------
--  DDL for Index ASA_SATISFACTION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_SATISFACTION_PK" ON "ASA_SATISFACTION" ("MENU_ID", "SITE_PREFIX");
--------------------------------------------------------
--  Constraints for Table ASA_SATISFACTION
--------------------------------------------------------

  ALTER TABLE "ASA_SATISFACTION" ADD CONSTRAINT "ASA_SATISFACTION_PK" PRIMARY KEY ("MENU_ID", "SITE_PREFIX") ENABLE;
 
  ALTER TABLE "ASA_SATISFACTION" MODIFY ("MENU_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_SATISFACTION" MODIFY ("SITE_PREFIX" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ASA_SATISFACTION
--------------------------------------------------------

  ALTER TABLE "ASA_SATISFACTION" ADD CONSTRAINT "ASA_SATISFACTION_FK1" FOREIGN KEY ("MENU_ID", "SITE_PREFIX")
	  REFERENCES "ASA_MENU" ("MENU_ID", "SITE_PREFIX") ON DELETE CASCADE ENABLE;
	  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------    
--------------------------------------------------------
--  DDL for Table ASA_SATIS_OPINION
--------------------------------------------------------

  CREATE TABLE "ASA_SATIS_OPINION" 
   (	"MENU_ID" VARCHAR2(100), 
	"SITE_PREFIX" VARCHAR2(50), 
	"SATIS_OPINION" VARCHAR2(4000), 
	"SATIS_OPI_DATE" DATE
   ) ;
 

   COMMENT ON COLUMN "ASA_SATIS_OPINION"."MENU_ID" IS '메뉴 아이디';
 
   COMMENT ON COLUMN "ASA_SATIS_OPINION"."SITE_PREFIX" IS '사이트 프리픽스';
 
   COMMENT ON COLUMN "ASA_SATIS_OPINION"."SATIS_OPINION" IS '평가의견';
 
   COMMENT ON COLUMN "ASA_SATIS_OPINION"."SATIS_OPI_DATE" IS '평가 일시';
 
   COMMENT ON TABLE "ASA_SATIS_OPINION"  IS '만족도 평가의견';
--------------------------------------------------------
--  Constraints for Table ASA_SATIS_OPINION
--------------------------------------------------------

  ALTER TABLE "ASA_SATIS_OPINION" MODIFY ("MENU_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_SATIS_OPINION" MODIFY ("SITE_PREFIX" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ASA_SATIS_OPINION
--------------------------------------------------------

  ALTER TABLE "ASA_SATIS_OPINION" ADD CONSTRAINT "ASA_SATIS_OPINION_FK1" FOREIGN KEY ("MENU_ID", "SITE_PREFIX")
	  REFERENCES "ASA_SATISFACTION" ("MENU_ID", "SITE_PREFIX") ON DELETE CASCADE ENABLE;
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_STATISTICS_TEMP
--------------------------------------------------------

  CREATE TABLE "ASA_STATISTICS_TEMP" 
   (	"STATIS_YEAR" NUMBER, 
	"STATIS_MONTH" NUMBER, 
	"STATIS_DAY" NUMBER, 
	"STATIS_HOUR" NUMBER, 
	"STATIS_OS" VARCHAR2(100), 
	"STATIS_BROWSER" VARCHAR2(100), 
	"STATIS_IP" VARCHAR2(20), 
	"SITE_PREFIX" VARCHAR2(20), 
	"STATIS_COUNTRY" VARCHAR2(100), 
	"STATIS_ISO_CODE" VARCHAR2(20), 
	"STATIS_MENU_ID" VARCHAR2(100), 
	"STATIS_IS_MENU" NUMBER DEFAULT 0
   ) ;
 

   COMMENT ON COLUMN "ASA_STATISTICS_TEMP"."STATIS_YEAR" IS '년';
 
   COMMENT ON COLUMN "ASA_STATISTICS_TEMP"."STATIS_MONTH" IS '월';
 
   COMMENT ON COLUMN "ASA_STATISTICS_TEMP"."STATIS_DAY" IS '일';
 
   COMMENT ON COLUMN "ASA_STATISTICS_TEMP"."STATIS_HOUR" IS '시간';
 
   COMMENT ON COLUMN "ASA_STATISTICS_TEMP"."STATIS_OS" IS 'OS';
 
   COMMENT ON COLUMN "ASA_STATISTICS_TEMP"."STATIS_BROWSER" IS '브라우저';
 
   COMMENT ON COLUMN "ASA_STATISTICS_TEMP"."STATIS_IP" IS '리모트 IP';
 
   COMMENT ON COLUMN "ASA_STATISTICS_TEMP"."SITE_PREFIX" IS '사이트 프리픽스';
 
   COMMENT ON COLUMN "ASA_STATISTICS_TEMP"."STATIS_COUNTRY" IS '접속지 국가명';
 
   COMMENT ON COLUMN "ASA_STATISTICS_TEMP"."STATIS_ISO_CODE" IS '접속지 ISO코드';
 
   COMMENT ON COLUMN "ASA_STATISTICS_TEMP"."STATIS_MENU_ID" IS '메뉴아이디';
 
   COMMENT ON COLUMN "ASA_STATISTICS_TEMP"."STATIS_IS_MENU" IS '통계타입이 메뉴인지 여부';
 
   COMMENT ON TABLE "ASA_STATISTICS_TEMP"  IS '사이트 접속통계 기본데이터 테이블';

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_STATIS_SESSION
--------------------------------------------------------

  CREATE TABLE "ASA_STATIS_SESSION" 
   (	"STATIS_YEAR" NUMBER, 
	"STATIS_MONTH" NUMBER, 
	"STATIS_DAY" NUMBER, 
	"SITE_PREFIX" VARCHAR2(20), 
	"STATIS_SESSION_CNT" NUMBER DEFAULT 0, 
	"STATIS_HOUR" NUMBER, 
	"WEEK_START" VARCHAR2(20), 
	"WEEK_END" VARCHAR2(20), 
	"WEEK_OF_YEAR_ISO" VARCHAR2(20), 
	"WEEK_OF_MONTH" VARCHAR2(20)
   ) ;
 

   COMMENT ON COLUMN "ASA_STATIS_SESSION"."STATIS_YEAR" IS '년';
 
   COMMENT ON COLUMN "ASA_STATIS_SESSION"."STATIS_MONTH" IS '월';
 
   COMMENT ON COLUMN "ASA_STATIS_SESSION"."STATIS_DAY" IS '일';
 
   COMMENT ON COLUMN "ASA_STATIS_SESSION"."SITE_PREFIX" IS '사이트프리픽스';
 
   COMMENT ON COLUMN "ASA_STATIS_SESSION"."STATIS_SESSION_CNT" IS '접속수';
 
   COMMENT ON COLUMN "ASA_STATIS_SESSION"."STATIS_HOUR" IS '시';
 
   COMMENT ON COLUMN "ASA_STATIS_SESSION"."WEEK_START" IS '해당주의 첫째일자(일요일)';
 
   COMMENT ON COLUMN "ASA_STATIS_SESSION"."WEEK_END" IS '해당주의 마지막일자';
 
   COMMENT ON COLUMN "ASA_STATIS_SESSION"."WEEK_OF_YEAR_ISO" IS '년도별 주차';
 
   COMMENT ON COLUMN "ASA_STATIS_SESSION"."WEEK_OF_MONTH" IS '월별 주차';
 
   COMMENT ON TABLE "ASA_STATIS_SESSION"  IS '세션 통계 테이블';
--------------------------------------------------------
--  DDL for Index ASA_STATIS_DAY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_STATIS_DAY_PK" ON "ASA_STATIS_SESSION" ("STATIS_YEAR", "STATIS_MONTH", "STATIS_DAY", "SITE_PREFIX", "STATIS_HOUR");
--------------------------------------------------------
--  Constraints for Table ASA_STATIS_SESSION
--------------------------------------------------------

  ALTER TABLE "ASA_STATIS_SESSION" ADD CONSTRAINT "ASA_STATIS_DAY_PK" PRIMARY KEY ("STATIS_YEAR", "STATIS_MONTH", "STATIS_DAY", "SITE_PREFIX", "STATIS_HOUR") ENABLE;
 
  ALTER TABLE "ASA_STATIS_SESSION" MODIFY ("STATIS_YEAR" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_SESSION" MODIFY ("STATIS_MONTH" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_SESSION" MODIFY ("STATIS_DAY" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_SESSION" MODIFY ("SITE_PREFIX" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_SESSION" MODIFY ("STATIS_HOUR" NOT NULL ENABLE);

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_STATIS_OS
--------------------------------------------------------

  CREATE TABLE "ASA_STATIS_OS" 
   (	"STATIS_YEAR" NUMBER, 
	"STATIS_MONTH" NUMBER, 
	"STATIS_DAY" NUMBER, 
	"STATIS_OS" VARCHAR2(50), 
	"SITE_PREFIX" VARCHAR2(20), 
	"STATIS_OS_CNT" NUMBER DEFAULT 0
   ) ;
 

   COMMENT ON COLUMN "ASA_STATIS_OS"."STATIS_YEAR" IS '년';
 
   COMMENT ON COLUMN "ASA_STATIS_OS"."STATIS_MONTH" IS '월';
 
   COMMENT ON COLUMN "ASA_STATIS_OS"."STATIS_DAY" IS '일';
 
   COMMENT ON COLUMN "ASA_STATIS_OS"."STATIS_OS" IS 'OS';
 
   COMMENT ON COLUMN "ASA_STATIS_OS"."SITE_PREFIX" IS '사이트프리픽스';
 
   COMMENT ON COLUMN "ASA_STATIS_OS"."STATIS_OS_CNT" IS '접속수';
 
   COMMENT ON TABLE "ASA_STATIS_OS"  IS '운영체제별 접속통계';
--------------------------------------------------------
--  DDL for Index ASA_STATIS_OS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_STATIS_OS_PK" ON "ASA_STATIS_OS" ("STATIS_YEAR", "STATIS_MONTH", "STATIS_DAY", "STATIS_OS", "SITE_PREFIX");
--------------------------------------------------------
--  Constraints for Table ASA_STATIS_OS
--------------------------------------------------------

  ALTER TABLE "ASA_STATIS_OS" ADD CONSTRAINT "ASA_STATIS_OS_PK" PRIMARY KEY ("STATIS_YEAR", "STATIS_MONTH", "STATIS_DAY", "STATIS_OS", "SITE_PREFIX") ENABLE;
 
  ALTER TABLE "ASA_STATIS_OS" MODIFY ("STATIS_YEAR" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_OS" MODIFY ("STATIS_MONTH" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_OS" MODIFY ("STATIS_DAY" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_OS" MODIFY ("STATIS_OS" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_OS" MODIFY ("SITE_PREFIX" NOT NULL ENABLE);

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_STATIS_BROWSER
--------------------------------------------------------

  CREATE TABLE "ASA_STATIS_BROWSER" 
   (	"STATIS_YEAR" NUMBER, 
	"STATIS_MONTH" NUMBER, 
	"STATIS_DAY" NUMBER, 
	"STATIS_BROWSER" VARCHAR2(50), 
	"SITE_PREFIX" VARCHAR2(20), 
	"STATIS_BROWSER_CNT" NUMBER DEFAULT 0
   ) ;
 

   COMMENT ON COLUMN "ASA_STATIS_BROWSER"."STATIS_YEAR" IS '년';
 
   COMMENT ON COLUMN "ASA_STATIS_BROWSER"."STATIS_MONTH" IS '월';
 
   COMMENT ON COLUMN "ASA_STATIS_BROWSER"."STATIS_DAY" IS '일';
 
   COMMENT ON COLUMN "ASA_STATIS_BROWSER"."STATIS_BROWSER" IS '브라우저';
 
   COMMENT ON COLUMN "ASA_STATIS_BROWSER"."SITE_PREFIX" IS '사이트프리픽스';
 
   COMMENT ON COLUMN "ASA_STATIS_BROWSER"."STATIS_BROWSER_CNT" IS '접속수';
 
   COMMENT ON TABLE "ASA_STATIS_BROWSER"  IS '브라우저별 접속통계';
--------------------------------------------------------
--  DDL for Index ASA_STATIS_BROWSER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_STATIS_BROWSER_PK" ON "ASA_STATIS_BROWSER" ("STATIS_YEAR", "STATIS_MONTH", "STATIS_DAY", "STATIS_BROWSER", "SITE_PREFIX");
--------------------------------------------------------
--  Constraints for Table ASA_STATIS_BROWSER
--------------------------------------------------------

  ALTER TABLE "ASA_STATIS_BROWSER" ADD CONSTRAINT "ASA_STATIS_BROWSER_PK" PRIMARY KEY ("STATIS_YEAR", "STATIS_MONTH", "STATIS_DAY", "STATIS_BROWSER", "SITE_PREFIX") ENABLE;
 
  ALTER TABLE "ASA_STATIS_BROWSER" MODIFY ("STATIS_YEAR" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_BROWSER" MODIFY ("STATIS_MONTH" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_BROWSER" MODIFY ("STATIS_DAY" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_BROWSER" MODIFY ("STATIS_BROWSER" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_BROWSER" MODIFY ("SITE_PREFIX" NOT NULL ENABLE);

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_STATIS_COUNTRY
--------------------------------------------------------

  CREATE TABLE "ASA_STATIS_COUNTRY" 
   (	"STATIS_YEAR" NUMBER, 
	"STATIS_MONTH" NUMBER, 
	"STATIS_DAY" NUMBER, 
	"STATIS_COUNTRY_KOR" VARCHAR2(100), 
	"STATIS_COUNTRY_ENG" VARCHAR2(100), 
	"STATIS_COUNTRY_CNT" NUMBER DEFAULT 0, 
	"SITE_PREFIX" VARCHAR2(50)
   ) ;
 

   COMMENT ON COLUMN "ASA_STATIS_COUNTRY"."STATIS_YEAR" IS '년';
 
   COMMENT ON COLUMN "ASA_STATIS_COUNTRY"."STATIS_MONTH" IS '월';
 
   COMMENT ON COLUMN "ASA_STATIS_COUNTRY"."STATIS_DAY" IS '일';
 
   COMMENT ON COLUMN "ASA_STATIS_COUNTRY"."STATIS_COUNTRY_KOR" IS '접속국 한글명';
 
   COMMENT ON COLUMN "ASA_STATIS_COUNTRY"."STATIS_COUNTRY_ENG" IS '접속국 영문명';
 
   COMMENT ON COLUMN "ASA_STATIS_COUNTRY"."STATIS_COUNTRY_CNT" IS '접속국별 접속수';
 
   COMMENT ON COLUMN "ASA_STATIS_COUNTRY"."SITE_PREFIX" IS '사아트프리픽스';
 
   COMMENT ON TABLE "ASA_STATIS_COUNTRY"  IS '국가별 접속통계';
--------------------------------------------------------
--  DDL for Index ASA_STATIS_COUNTRY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_STATIS_COUNTRY_PK" ON "ASA_STATIS_COUNTRY" ("STATIS_YEAR", "STATIS_MONTH", "STATIS_DAY", "SITE_PREFIX", "STATIS_COUNTRY_KOR", "STATIS_COUNTRY_ENG");
--------------------------------------------------------
--  Constraints for Table ASA_STATIS_COUNTRY
--------------------------------------------------------

  ALTER TABLE "ASA_STATIS_COUNTRY" ADD CONSTRAINT "ASA_STATIS_COUNTRY_PK" PRIMARY KEY ("STATIS_YEAR", "STATIS_MONTH", "STATIS_DAY", "SITE_PREFIX", "STATIS_COUNTRY_KOR", "STATIS_COUNTRY_ENG") ENABLE;
 
  ALTER TABLE "ASA_STATIS_COUNTRY" MODIFY ("STATIS_YEAR" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_COUNTRY" MODIFY ("STATIS_MONTH" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_COUNTRY" MODIFY ("STATIS_DAY" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_COUNTRY" MODIFY ("STATIS_COUNTRY_KOR" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_COUNTRY" MODIFY ("STATIS_COUNTRY_ENG" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_COUNTRY" MODIFY ("SITE_PREFIX" NOT NULL ENABLE);

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_STATIS_MENU
--------------------------------------------------------

  CREATE TABLE "ASA_STATIS_MENU" 
   (	"STATIS_YEAR" NUMBER, 
	"STATIS_MONTH" NUMBER, 
	"STATIS_DAY" NUMBER, 
	"STATIS_MENU_ID" VARCHAR2(100), 
	"STATIS_MENU_CNT" NUMBER DEFAULT 0, 
	"SITE_PREFIX" VARCHAR2(50)
   ) ;
 

   COMMENT ON COLUMN "ASA_STATIS_MENU"."STATIS_YEAR" IS '년';
 
   COMMENT ON COLUMN "ASA_STATIS_MENU"."STATIS_MONTH" IS '월';
 
   COMMENT ON COLUMN "ASA_STATIS_MENU"."STATIS_DAY" IS '일';
 
   COMMENT ON COLUMN "ASA_STATIS_MENU"."STATIS_MENU_ID" IS '메뉴아이디';
 
   COMMENT ON COLUMN "ASA_STATIS_MENU"."STATIS_MENU_CNT" IS '접속수';
 
   COMMENT ON COLUMN "ASA_STATIS_MENU"."SITE_PREFIX" IS '사이트 프리픽스';
 
   COMMENT ON TABLE "ASA_STATIS_MENU"  IS '메뉴별 접속통계';
--------------------------------------------------------
--  DDL for Index ASA_STATIS_MENU_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_STATIS_MENU_PK" ON "ASA_STATIS_MENU" ("STATIS_YEAR", "STATIS_MONTH", "STATIS_DAY", "STATIS_MENU_ID", "SITE_PREFIX");
--------------------------------------------------------
--  Constraints for Table ASA_STATIS_MENU
--------------------------------------------------------

  ALTER TABLE "ASA_STATIS_MENU" MODIFY ("STATIS_YEAR" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_MENU" MODIFY ("STATIS_MONTH" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_MENU" MODIFY ("STATIS_DAY" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_MENU" MODIFY ("STATIS_MENU_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_MENU" MODIFY ("SITE_PREFIX" NOT NULL ENABLE);
 
  ALTER TABLE "ASA_STATIS_MENU" ADD CONSTRAINT "ASA_STATIS_MENU_PK" PRIMARY KEY ("STATIS_YEAR", "STATIS_MONTH", "STATIS_DAY", "STATIS_MENU_ID", "SITE_PREFIX") ENABLE;

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_STATIS_ISO_3166_1
--------------------------------------------------------

  CREATE TABLE "ASA_STATIS_ISO_3166_1" 
   (	"NUM_CODE" VARCHAR2(20), 
	"ALPHA_3" VARCHAR2(20), 
	"ALPHA_2" VARCHAR2(20), 
	"ENG_NAME" VARCHAR2(100), 
	"KOR_NAME" VARCHAR2(100)
   ) ;
 

   COMMENT ON COLUMN "ASA_STATIS_ISO_3166_1"."NUM_CODE" IS '넘버코드';
 
   COMMENT ON COLUMN "ASA_STATIS_ISO_3166_1"."ALPHA_3" IS '3자리 코드';
 
   COMMENT ON COLUMN "ASA_STATIS_ISO_3166_1"."ALPHA_2" IS '2자리 코드';
 
   COMMENT ON COLUMN "ASA_STATIS_ISO_3166_1"."ENG_NAME" IS '영문 국가명';
 
   COMMENT ON COLUMN "ASA_STATIS_ISO_3166_1"."KOR_NAME" IS '한글 국가명';
 
   COMMENT ON TABLE "ASA_STATIS_ISO_3166_1"  IS 'ISO 3166-1 코드 테이블';

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_COMMENT
--------------------------------------------------------

  CREATE TABLE "ASA_COMMENT" 
   (	"CMT_ID" NUMBER, 
	"CMT_PARENT_ID" NUMBER, 
	"CMT_TITLE" VARCHAR2(200 CHAR), 
	"CMT_CONTENT" VARCHAR2(1000 CHAR), 
	"CMT_MODULE" VARCHAR2(100), 
	"CMT_MODULE_SUB" VARCHAR2(100), 
	"CMT_MOD_CATEGORY" VARCHAR2(100), 
	"CMT_MOD_ITEM_ID" VARCHAR2(100), 
	"CMT_PAGE_URL" VARCHAR2(2000), 
	"CMT_LOGIN_TYPE" VARCHAR2(50), 
	"CMT_USER_ID" VARCHAR2(100), 
	"CMT_SNS_USER_ID" VARCHAR2(100), 
	"CMT_SNS_USER_NAME" VARCHAR2(100), 
	"CMT_SNS_USER_HOME" VARCHAR2(200), 
	"CMT_PROFILE_IMAGE" VARCHAR2(1000), 
	"CMT_AGREE" NUMBER DEFAULT 0, 
	"CMT_DISAGREE" NUMBER DEFAULT 0, 
	"CMT_RECOMMEND" NUMBER DEFAULT 0, 
	"CMT_REPORT" NUMBER DEFAULT 0, 
	"CMT_REG_IP" VARCHAR2(20), 
	"CMT_REGDATE" DATE, 
	"CMT_STATUS" VARCHAR2(50)
   ) ;
 

   COMMENT ON COLUMN "ASA_COMMENT"."CMT_ID" IS '댓글 아이디';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_PARENT_ID" IS '부모댓글 아이디';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_TITLE" IS '원본글 제목';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_CONTENT" IS '댓글 내용';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_MODULE" IS '댓글 사용 모듈';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_MODULE_SUB" IS '댓글 사용 하위모듈';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_MOD_CATEGORY" IS '댓글 사용 모듈 카테고리';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_MOD_ITEM_ID" IS '댓글 사용 아이템 아이디';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_PAGE_URL" IS '댓글 작성된 페이지 URL';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_LOGIN_TYPE" IS '로그인 타입';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_USER_ID" IS '작성자 아이디';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_SNS_USER_ID" IS 'SNS 계정 아이디';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_SNS_USER_NAME" IS 'SNS 계정 이름(닉네임)';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_SNS_USER_HOME" IS 'SNS 작성자 홈';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_PROFILE_IMAGE" IS '작성자 프로필 이미지';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_AGREE" IS '찬성수';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_DISAGREE" IS '반대수';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_RECOMMEND" IS '추천수';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_REPORT" IS '신고수';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_REG_IP" IS '댓글 작성자 아이피';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_REGDATE" IS '댓글 작성일시';
 
   COMMENT ON COLUMN "ASA_COMMENT"."CMT_STATUS" IS '댓글 상태';
 
   COMMENT ON TABLE "ASA_COMMENT"  IS '공통 댓글 테이블';
--------------------------------------------------------
--  DDL for Index ASA_COMMENT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_COMMENT_PK" ON "ASA_COMMENT" ("CMT_ID");
--------------------------------------------------------
--  Constraints for Table ASA_COMMENT
--------------------------------------------------------

  ALTER TABLE "ASA_COMMENT" ADD CONSTRAINT "ASA_COMMENT_PK" PRIMARY KEY ("CMT_ID") ENABLE;
 
  ALTER TABLE "ASA_COMMENT" MODIFY ("CMT_ID" NOT NULL ENABLE);

   
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  

  --------------------------------------------------------
--  DDL for Table ASA_EMAIL_TARGET
--------------------------------------------------------

  CREATE TABLE "ASA_EMAIL_TARGET" 
   (	"ET_ID" VARCHAR2(50), 
	"ET_CATE" VARCHAR2(50), 
	"ET_TITLE" VARCHAR2(200), 
	"ET_TARGET" VARCHAR2(2000), 
	"ET_CONTENTS" VARCHAR2(4000), 
	"ET_FORM" VARCHAR2(100), 
	"ET_REGDATE" DATE
   ) ;
 

   COMMENT ON COLUMN "ASA_EMAIL_TARGET"."ET_ID" IS '메일대상 아이디';
 
   COMMENT ON COLUMN "ASA_EMAIL_TARGET"."ET_CATE" IS '구분코드';
 
   COMMENT ON COLUMN "ASA_EMAIL_TARGET"."ET_TITLE" IS '제목';
 
   COMMENT ON COLUMN "ASA_EMAIL_TARGET"."ET_TARGET" IS '수신자';
 
   COMMENT ON COLUMN "ASA_EMAIL_TARGET"."ET_CONTENTS" IS '내용';
 
   COMMENT ON COLUMN "ASA_EMAIL_TARGET"."ET_FORM" IS '발송폼';
 
   COMMENT ON COLUMN "ASA_EMAIL_TARGET"."ET_REGDATE" IS '등록일시';
 
   COMMENT ON TABLE "ASA_EMAIL_TARGET"  IS '이메일대상 정보';
--------------------------------------------------------
--  DDL for Index ASA_EMAIL_TARGET_PK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_EMAIL_TARGET_PK1" ON "ASA_EMAIL_TARGET" ("ET_ID");
--------------------------------------------------------
--  Constraints for Table ASA_EMAIL_TARGET
--------------------------------------------------------

  ALTER TABLE "ASA_EMAIL_TARGET" ADD CONSTRAINT "ASA_EMAIL_TARGET_PK" PRIMARY KEY ("ET_ID") ENABLE;
 
  ALTER TABLE "ASA_EMAIL_TARGET" MODIFY ("ET_ID" NOT NULL ENABLE);
  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_CONTENT_STATIS_DAY
--------------------------------------------------------

  CREATE TABLE "ASA_CONTENT_STATIS_DAY" 
   (	"SITE_PREFIX" VARCHAR2(50), 
	"CS_MODIUL_CODE" VARCHAR2(50), 
	"CS_MODIUL_SUB_CODE" VARCHAR2(50), 
	"CS_CATE_CODE" VARCHAR2(50), 
	"CS_CONTENT_ID" VARCHAR2(50), 
	"CS_YEAR" NUMBER, 
	"CS_MONTH" NUMBER, 
	"CS_DAY" NUMBER, 
	"CS_COUNT" NUMBER, 
	"CS_WEEK_START" VARCHAR2(20), 
	"CS_WEEK_END" VARCHAR2(20), 
	"WEEK_OF_YEAR_ISOUMN1" VARCHAR2(20), 
	"WEEK_OF_MONTH" VARCHAR2(20)
   ) ;
 

   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."SITE_PREFIX" IS '사이트 프리픽스';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."CS_MODIUL_CODE" IS '모듈 코드';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."CS_MODIUL_SUB_CODE" IS '모듈 서브 코드';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."CS_CATE_CODE" IS '카테고리 코드';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."CS_CONTENT_ID" IS '콘텐츠 아이디';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."CS_YEAR" IS '년';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."CS_MONTH" IS '월';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."CS_DAY" IS '일';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."CS_COUNT" IS '추천 수';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."CS_WEEK_START" IS '주 시작일자';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."CS_WEEK_END" IS '주 종료일자';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."WEEK_OF_YEAR_ISOUMN1" IS '년도별 주차';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_DAY"."WEEK_OF_MONTH" IS '월별 주차';
 
   COMMENT ON TABLE "ASA_CONTENT_STATIS_DAY"  IS '콘텐츠의 추천 통계 (일별)';
 
--------------------------------------------------------
--  DDL for Index ASA_CONTENT_STATIS_DAY_INDEX1
--------------------------------------------------------

  CREATE INDEX "ASA_CONTENT_STATIS_DAY_INDEX1" ON "ASA_CONTENT_STATIS_DAY" ("SITE_PREFIX", "CS_MODIUL_CODE", "CS_MODIUL_SUB_CODE", "CS_CATE_CODE", "CS_CONTENT_ID", "CS_YEAR", "CS_MONTH", "CS_DAY");

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_CONTENT_STATIS_7
--------------------------------------------------------

  CREATE TABLE "ASA_CONTENT_STATIS_7" 
   (	"SITE_PREFIX" VARCHAR2(50), 
	"CS_MODIUL_CODE" VARCHAR2(50), 
	"CS_MODIUL_SUB_CODE" VARCHAR2(50), 
	"CS_CATE_CODE" VARCHAR2(50), 
	"CS_CONTENT_ID" VARCHAR2(50), 
	"CS_YEAR" NUMBER, 
	"CS_MONTH" NUMBER, 
	"CS_DAY" NUMBER, 
	"CS_COUNT" NUMBER, 
	"CS_START_DAY" VARCHAR2(20), 
	"CS_END_DAY" VARCHAR2(20)
   ) ;
 

   COMMENT ON COLUMN "ASA_CONTENT_STATIS_7"."SITE_PREFIX" IS '이트 프리픽스';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_7"."CS_MODIUL_CODE" IS '모듈 코드';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_7"."CS_MODIUL_SUB_CODE" IS '모듈 서브 코드';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_7"."CS_CATE_CODE" IS '카테고리 코드';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_7"."CS_CONTENT_ID" IS '콘텐츠 아이디';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_7"."CS_YEAR" IS '년';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_7"."CS_MONTH" IS '월';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_7"."CS_DAY" IS '일';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_7"."CS_COUNT" IS '추천 수';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_7"."CS_START_DAY" IS '7일 시작일';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_7"."CS_END_DAY" IS '7일 종료일';
 
   COMMENT ON TABLE "ASA_CONTENT_STATIS_7"  IS '콘텐츠 추천 통계 최근 7일';
   
--------------------------------------------------------
--  DDL for Index ASA_CONTENT_STATIS_7_INDEX1
--------------------------------------------------------

  CREATE INDEX "ASA_CONTENT_STATIS_7_INDEX1" ON "ASA_CONTENT_STATIS_7" ("SITE_PREFIX", "CS_MODIUL_CODE", "CS_MODIUL_SUB_CODE", "CS_CATE_CODE", "CS_CONTENT_ID", "CS_YEAR", "CS_MONTH", "CS_DAY");

  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for Table ASA_CONTENT_STATIS_30
--------------------------------------------------------

  CREATE TABLE "ASA_CONTENT_STATIS_30" 
   (	"SITE_PREFIX" VARCHAR2(50), 
	"CS_MODIUL_CODE" VARCHAR2(50), 
	"CS_MODIUL_SUB_CODE" VARCHAR2(50), 
	"CS_CATE_CODE" VARCHAR2(50), 
	"CS_CONTENT_ID" VARCHAR2(50), 
	"CS_YEAR" NUMBER, 
	"CS_MONTH" NUMBER, 
	"CS_DAY" NUMBER, 
	"CS_COUNT" NUMBER, 
	"CS_START_DAY" VARCHAR2(20), 
	"CS_END_DAY" VARCHAR2(20)
   ) ;
 

   COMMENT ON COLUMN "ASA_CONTENT_STATIS_30"."SITE_PREFIX" IS '사이트 프리픽스';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_30"."CS_MODIUL_CODE" IS '모듈 코드';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_30"."CS_MODIUL_SUB_CODE" IS '모듈 서브 코드';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_30"."CS_CATE_CODE" IS '카테고리 코드';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_30"."CS_CONTENT_ID" IS '콘텐츠 아이디';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_30"."CS_YEAR" IS '년';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_30"."CS_MONTH" IS '월';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_30"."CS_DAY" IS '일';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_30"."CS_COUNT" IS '추천 수';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_30"."CS_START_DAY" IS '통계 시작일자';
 
   COMMENT ON COLUMN "ASA_CONTENT_STATIS_30"."CS_END_DAY" IS '통계 종료일자';
 
   COMMENT ON TABLE "ASA_CONTENT_STATIS_30"  IS '콘텐츠 추천 통계 최근 30일';
--------------------------------------------------------
--  DDL for Index ASA_CONTENT_STATIS_30_INDEX1
--------------------------------------------------------

  CREATE INDEX "ASA_CONTENT_STATIS_30_INDEX1" ON "ASA_CONTENT_STATIS_30" ("SITE_PREFIX", "CS_MODIUL_CODE", "CS_MODIUL_SUB_CODE", "CS_CATE_CODE", "CS_CONTENT_ID", "CS_YEAR", "CS_MONTH", "CS_DAY");

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  

--------------------------------------------------------
--  DDL for Table ASA_POLL
--------------------------------------------------------

  CREATE TABLE "ASA_POLL" 
   (	"PO_ID" NUMBER, 
	"PO_QUESTION" VARCHAR2(400), 
	"PO_DESCRIPTION" VARCHAR2(4000), 
	"PO_START_DATE" VARCHAR2(10), 
	"PO_END_DATE" VARCHAR2(10), 
	"PO_TYPE" VARCHAR2(100), 
	"PO_REGDATE" DATE, 
	"PO_USE" NUMBER, 
	"PO_YES_CNT" NUMBER DEFAULT 0, 
	"PO_NO_CNT" NUMBER DEFAULT 0, 
	"PO_HIT" NUMBER DEFAULT 0, 
	"PO_THUMB" NUMBER, 
	"PO_THUMB_TEXT" VARCHAR2(100), 
	"SITE_PREFIX" VARCHAR2(20)
   ) ;
 

   COMMENT ON COLUMN "ASA_POLL"."PO_ID" IS '투표 아이디';
 
   COMMENT ON COLUMN "ASA_POLL"."PO_QUESTION" IS '질문';
 
   COMMENT ON COLUMN "ASA_POLL"."PO_DESCRIPTION" IS '부가설명';
 
   COMMENT ON COLUMN "ASA_POLL"."PO_START_DATE" IS '시작일';
 
   COMMENT ON COLUMN "ASA_POLL"."PO_END_DATE" IS '종료일';
 
   COMMENT ON COLUMN "ASA_POLL"."PO_TYPE" IS '구분';
 
   COMMENT ON COLUMN "ASA_POLL"."PO_REGDATE" IS '등록일시';
 
   COMMENT ON COLUMN "ASA_POLL"."PO_USE" IS '게시여부';
 
   COMMENT ON COLUMN "ASA_POLL"."PO_YES_CNT" IS '찬성수';
 
   COMMENT ON COLUMN "ASA_POLL"."PO_NO_CNT" IS '반대수';
 
   COMMENT ON COLUMN "ASA_POLL"."PO_HIT" IS '조회수';
 
   COMMENT ON COLUMN "ASA_POLL"."PO_THUMB" IS '이미지파일 번호';
 
   COMMENT ON COLUMN "ASA_POLL"."PO_THUMB_TEXT" IS '대표이미지 대체텍스트';
 
   COMMENT ON COLUMN "ASA_POLL"."SITE_PREFIX" IS '사이트 프리픽스';
 
   COMMENT ON TABLE "ASA_POLL"  IS '투표';
--------------------------------------------------------
--  DDL for Index ASA_POLL_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ASA_POLL_PK" ON "ASA_POLL" ("PO_ID");
--------------------------------------------------------
--  Constraints for Table ASA_POLL
--------------------------------------------------------

  ALTER TABLE "ASA_POLL" ADD CONSTRAINT "ASA_POLL_PK" PRIMARY KEY ("PO_ID") ENABLE;
 
  ALTER TABLE "ASA_POLL" MODIFY ("PO_ID" NOT NULL ENABLE);

----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
---------------------------------------------------------------------------	  
---------------------------------------------------------------------------	  
---------------------------------------------------------------------------	  
---------------------------------------------------------------------------	  
	  
	  
	  
	  
--------------------------------------------------------
--  DDL for Sequence ASA_ORGANIZATION_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ASA_ORGANIZATION_SEQ"  MINVALUE 10000 MAXVALUE 99999 INCREMENT BY 1 START WITH 10007 NOCACHE  NOORDER  NOCYCLE  ;
   
--------------------------------------------------------
--  DDL for Sequence ASA_BOARD_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ASA_BOARD_SEQ"  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ASA_FILEINFO_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ASA_FILEINFO_SEQ"  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ASA_COMMON_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ASA_COMMON_SEQ"  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ASA_RENTAL_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ASA_RENTAL_SEQ"  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ASA_VOLUNTEER_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ASA_VOLUNTEER_SEQ"  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ASA_VIEWING_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ASA_VIEWING_SEQ"  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;

--------------------------------------------------------
--  DDL for Sequence ASA_EDUCATION_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ASA_EDUCATION_SEQ"  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ASA_ARCHIVE_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ASA_ARCHIVE_SEQ"  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ASA_STATISTICS_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ASA_STATISTICS_SEQ"  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ASA_CONTENT_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ASA_CONTENT_SEQ"  MINVALUE 1 INCREMENT BY 1 START WITH 2 NOCACHE  NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ASA_COMMENT_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ASA_COMMENT_SEQ"  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
      
--------------------------------------------------------
--  DDL for Sequence ASA_POLL_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ASA_POLL_SEQ"  MINVALUE 1 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;

   
   
   
   
   
   
----------------------------------------------------------------------------------------------------------------   
----------------------------------------------------------------------------------------------------------------  
--------------------------------------------------------
--  DDL for View V_ASA_DEPARTMENT
--------------------------------------------------------
	CREATE VIEW V_ASA_DEPARTMENT
	AS select 
	  LEVEL as dep_level
	  , dep_id
	  , dep_name
	  , dep_order
	  , dep_tel
	  , dep_description
	  , parent_id 
	  , dep_type
	from(
	  select 
	    dep_id
	    , dep_name
	    , dep_order
	    , dep_tel
	    , dep_description
	    , org_id as parent_id 
	    , 'DEP' as dep_type
	  from ASA_ORG_DEPARTMENT 
	  where org_id not in (select org_id from ASA_ORG_ORGANIZATION where org_use = 0) 
	  UNION
	  select 
	    org_id as dep_id
	    , org_name as dep_name
	    , org_order as dep_order
	    , '' as dep_tel
	    , org_description as dep_description
	    , '' as parent_id 
	    , 'ORG' as dep_type
	  from ASA_ORG_ORGANIZATION
	  where org_use = 1
	  UNION
	  select 
	    COMP_ID as dep_id
	    , COMP_NM as dep_name
	    , 0 as dep_order
	    , COMP_TEL as dep_tel
	    , MENT as dep_description
	    , UNIV_ID as parent_id 
	    , 'TEAM' as dep_type
	  from STARTUP_COMPANY_INFO
	  where DEL_YN = 'N'
	) department
	START WITH department.parent_id is null
	CONNECT BY PRIOR department.dep_id = department.parent_id
	ORDER SIBLINGS BY department.dep_order;
