-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 125.141.56.226    Database: ggyouth
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asa_admin_access_log`
--

DROP TABLE IF EXISTS `asa_admin_access_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_admin_access_log` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '로그 아이디',
  `ADMIN_ID` varchar(50) DEFAULT NULL COMMENT '관리자 아이디',
  `ADMIN_NAME` varchar(50) DEFAULT NULL COMMENT '관리자 이름',
  `LOG_REMOTE_IP` varchar(50) DEFAULT NULL COMMENT '접속 아이디',
  `SITE_ID` varchar(50) DEFAULT NULL COMMENT '사이트 아이디',
  `LOG_REGDATE` datetime DEFAULT NULL COMMENT '등록일자',
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='관리자 접속 로그';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_admin_inquire_log`
--

DROP TABLE IF EXISTS `asa_admin_inquire_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_admin_inquire_log` (
  `INQ_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '조회 로그 아이디',
  `INQ_WORKER_ID` varchar(50) DEFAULT NULL COMMENT '접속자 아이디',
  `INQ_WORKER_NAME` varchar(50) DEFAULT NULL COMMENT '접속자 이름',
  `INQ_TARGET_ID` varchar(50) DEFAULT NULL COMMENT '피검색자 아이디',
  `INQ_TARGET_NAME` varchar(50) DEFAULT NULL COMMENT '피검색자 이름',
  `INQ_URL` varchar(200) DEFAULT NULL COMMENT '요청 url',
  `INQ_WORK` varchar(100) DEFAULT NULL COMMENT '작업내역',
  `INQ_REMORT_IP` varchar(100) DEFAULT NULL COMMENT '접속자 아이피',
  `INQ_REGDATE` datetime DEFAULT NULL COMMENT '작업일시',
  PRIMARY KEY (`INQ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='관리자 회원 정보 조회, 수정, 삭제 내역 로그';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_admin_member`
--

DROP TABLE IF EXISTS `asa_admin_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_admin_member` (
  `ADMIN_ID` varchar(50) NOT NULL COMMENT '관리자 아이디',
  `ADMIN_PASSWORD` varchar(200) NOT NULL COMMENT '로그인비밀번호',
  `ADMIN_NAME` varchar(200) DEFAULT NULL COMMENT '관리자 성명',
  `ADMIN_SEX` varchar(10) DEFAULT NULL COMMENT '관리자 성별(코드)',
  `ADMIN_EMAIL` varchar(200) DEFAULT NULL COMMENT '관리자 이메일',
  `ADMIN_TEL1` varchar(100) DEFAULT NULL COMMENT '관리자 전화번호1',
  `ADMIN_TEL2` varchar(100) DEFAULT NULL COMMENT '관리자 전화번호2',
  `ADMIN_TEL3` varchar(100) DEFAULT NULL COMMENT '관리자 전화번호3',
  `ADMIN_MOBILE1` varchar(100) DEFAULT NULL COMMENT '관리자 휴대폰번호1',
  `ADMIN_MOBILE2` varchar(100) DEFAULT NULL COMMENT '관리자 휴대폰번호2',
  `ADMIN_MOBILE3` varchar(100) DEFAULT NULL COMMENT '관리자 휴대폰번호3',
  `ADMIN_FAX` varchar(50) DEFAULT NULL COMMENT '관리자 팩스번호',
  `ADMIN_ZIPCODE` varchar(100) DEFAULT NULL COMMENT '관리자 우편번호',
  `ADMIN_ADDRESS` varchar(200) DEFAULT NULL COMMENT '관리자 주소',
  `ADMIN_ADDRESS_DETAIL` varchar(200) DEFAULT NULL COMMENT '관리자 상세주소',
  `ADMIN_REGDATE` datetime DEFAULT NULL COMMENT '가입일시',
  `ADMIN_ACTIVE` int(11) NOT NULL DEFAULT '1' COMMENT '관리자 상태',
  `ADMIN_LOGIN_LAST_DATE` datetime DEFAULT NULL COMMENT '마지막 로그인 일시',
  `ADMIN_PW_LAST_UPDATE` datetime DEFAULT NULL COMMENT '비밀번호 마지막 수정일시',
  `ADMIN_ORGANIZATION` varchar(100) DEFAULT NULL COMMENT '소속 기관명',
  `ADMIN_DEPARTMENT` varchar(100) DEFAULT NULL COMMENT '소속 부서명',
  `ADMIN_POSITION` varchar(100) DEFAULT NULL COMMENT '직급명',
  `ADMIN_ROLE` varchar(100) DEFAULT NULL COMMENT '역할',
  `ADMIN_LOCK` int(11) DEFAULT '0' COMMENT '로그인 제한 여부',
  `ADMIN_LOGIN_FAIL_COUNT` int(11) DEFAULT '0' COMMENT '로그인 실패 횟수',
  `ADMIN_LOGIN_FAIL_DATE` datetime DEFAULT NULL COMMENT '로그인 마지막 실패 일시',
  PRIMARY KEY (`ADMIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='관리자 정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_admin_site_role_rel`
--

DROP TABLE IF EXISTS `asa_admin_site_role_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_admin_site_role_rel` (
  `ADMIN_ID` varchar(50) NOT NULL COMMENT '관리자 아이디',
  `SITE_PREFIX` varchar(20) NOT NULL COMMENT '사이트 프리픽스',
  `ROLE_CODE` varchar(100) DEFAULT NULL COMMENT '역할 코드',
  PRIMARY KEY (`ADMIN_ID`,`SITE_PREFIX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='관리자 사이트별 롤 릴레이션';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_advertising`
--

DROP TABLE IF EXISTS `asa_advertising`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_advertising` (
  `ARC_ID` int(11) NOT NULL COMMENT '아카이브아이디',
  `ADT_MANUFACTURE_YEAR` varchar(20) DEFAULT NULL COMMENT '제작년도',
  `ADT_PRODUCER` varchar(50) DEFAULT NULL COMMENT '광고회사/제작사',
  `ADT_DIRECTOR` varchar(50) DEFAULT NULL COMMENT '감독',
  `ADT_BACK_MUSIC` varchar(200) DEFAULT NULL COMMENT '배경음악',
  `ADT_PLAN_INTENTION` varchar(2000) DEFAULT NULL COMMENT '기획의도',
  `ADT_COMPOSITION` varchar(2000) DEFAULT NULL COMMENT '구성 및 표현',
  `ADT_PRODUCTION_REVIEW` varchar(2000) DEFAULT NULL COMMENT '제작후기',
  `ADT_TV_CF` varchar(4000) DEFAULT NULL COMMENT 'TV CF Copy',
  `ADT_RADIO_CF` varchar(4000) DEFAULT NULL COMMENT 'Radio CF Copy',
  `ADT_VIDEO` int(11) DEFAULT NULL COMMENT '동영상',
  `ADT_RADIO` int(11) DEFAULT NULL COMMENT '라디오',
  `ADT_MEDIA` varchar(100) DEFAULT NULL COMMENT '매체',
  `ADT_AWARD_TYPE` varchar(50) DEFAULT NULL COMMENT '수상종류',
  `ADT_WINNER` varchar(200) DEFAULT NULL COMMENT '수상자',
  `ADT_UCC_URL` varchar(200) DEFAULT NULL COMMENT 'ucc url',
  PRIMARY KEY (`ARC_ID`),
  CONSTRAINT `ASA_ADVERTISING_FK1` FOREIGN KEY (`ARC_ID`) REFERENCES `asa_archive` (`ARC_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='광고자료';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_allow_ip`
--

DROP TABLE IF EXISTS `asa_allow_ip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_allow_ip` (
  `IP_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `IP_TYPE` varchar(50) NOT NULL COMMENT '허용, 차단 구분',
  `IP_START` varchar(20) DEFAULT NULL COMMENT '1건차단, 패턴차단, 구간시작 IP',
  `IP_END` varchar(20) DEFAULT NULL COMMENT '구간 종료 아이디',
  `IP_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  `IP_RULE_NAME` varchar(500) DEFAULT NULL COMMENT '규칙명',
  `IP_USE` int(11) DEFAULT NULL COMMENT '사용유무',
  `SITE_PREFIX` varchar(50) DEFAULT NULL COMMENT '사이트프리픽스',
  PRIMARY KEY (`IP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='관리자 접근 허용, 차단 아이피';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_allow_ip_temp`
--

DROP TABLE IF EXISTS `asa_allow_ip_temp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_allow_ip_temp` (
  `ADMIN_ID` varchar(50) NOT NULL COMMENT '관리자 아이디',
  `TEMP_IP` varchar(20) DEFAULT NULL COMMENT '임시허용 아이피',
  `ADMIN_HP` varchar(20) DEFAULT NULL COMMENT '관리자 휴대폰번호',
  `CERTI_NUM` varchar(20) DEFAULT NULL COMMENT '인증번호',
  `TEMP_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  `CERTI_REQUEST_DATE` datetime DEFAULT NULL COMMENT '인증번호 요청일시',
  `CERTI_COMPLET_DATE` datetime DEFAULT NULL COMMENT '인증완료일시',
  `AUTHENTICATION` int(11) DEFAULT NULL COMMENT '인증완료여부',
  `SMS_SEND_CNT` int(11) DEFAULT '0' COMMENT 'SMS발송 횟수',
  `AUTHENTI_FAIL_CNT` int(11) DEFAULT '0' COMMENT '인증실패 횟수',
  PRIMARY KEY (`ADMIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='임시 아이피 허용 정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_allow_mac`
--

DROP TABLE IF EXISTS `asa_allow_mac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_allow_mac` (
  `MAC_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `MAC_TYPE` varchar(50) DEFAULT NULL COMMENT '허용, 차단',
  `MAC_ADDRESS` varchar(50) DEFAULT NULL COMMENT 'Mac 주소',
  `MAC_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  `MAC_RULE_NAME` varchar(500) DEFAULT NULL COMMENT '규칙명',
  `MAC_USE` int(11) DEFAULT NULL COMMENT '사용유무',
  `SITE_PREFIX` varchar(50) DEFAULT NULL COMMENT '사이트프리픽스',
  PRIMARY KEY (`MAC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='관리자 접근 허용, 차단 Mac 주소';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_api_info`
--

DROP TABLE IF EXISTS `asa_api_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_api_info` (
  `API_SEQ` int(11) NOT NULL AUTO_INCREMENT COMMENT '연번',
  `API_ID` varchar(60) DEFAULT NULL COMMENT 'API 영문명( 영문 서비스명)',
  `API_TITLE` varchar(200) DEFAULT NULL COMMENT 'API 한글명',
  `API_CONTENT` varchar(4000) DEFAULT NULL COMMENT 'API 설명',
  `API_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  `API_PROVIDER` varchar(50) DEFAULT NULL COMMENT 'API 제공 사이트',
  `API_DOMAIN` varchar(100) DEFAULT NULL COMMENT 'API 도메인',
  `API_KEY` varchar(200) DEFAULT NULL COMMENT 'API 키',
  `API_RETURN_TYPE` varchar(20) DEFAULT NULL COMMENT 'API 결과 유형(XML, JSON)',
  `API_NUM_OF_ROW` int(11) DEFAULT NULL COMMENT '한번에 가져올 결과 수',
  `API_TABLE_NAME` varchar(60) DEFAULT NULL COMMENT '배치할 테이블 명',
  `API_LAST_BATCH` datetime DEFAULT NULL COMMENT '마지막 배치일',
  `API_INPUT_TYPE` varchar(50) DEFAULT NULL COMMENT '배치실행구분(auto, manual)',
  PRIMARY KEY (`API_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='OPEN API 등록 정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_archive`
--

DROP TABLE IF EXISTS `asa_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_archive` (
  `ARC_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `ARC_CATEGORY` varchar(50) DEFAULT NULL COMMENT '카테고리 아이디',
  `ARC_TITLE` varchar(500) DEFAULT NULL COMMENT '제목',
  `ARC_PRODUCT_DIV` varchar(100) DEFAULT NULL COMMENT '제품분류',
  `ARC_CUSTOM_TYPE` varchar(50) DEFAULT NULL COMMENT '커스텀 타입분류',
  `ARC_CONTENT` longtext COMMENT '내용',
  `ARC_PLAN_TEXT` varchar(4000) DEFAULT NULL COMMENT '내용 택스트',
  `MEM_ID` varchar(50) DEFAULT NULL COMMENT '작성자 아이디',
  `ARC_REGDATE` datetime DEFAULT NULL COMMENT '등록일자',
  `ARC_LAST_UPDATE` datetime DEFAULT NULL COMMENT '마지막 수정일자',
  `ARC_HIT` int(11) DEFAULT '0' COMMENT '조회수',
  `ARC_RECOMMEND` int(11) DEFAULT '0' COMMENT '추천수',
  `ARC_NURI` varchar(50) DEFAULT NULL COMMENT '공공누리 코드',
  `ARC_USE` int(11) DEFAULT NULL COMMENT '게시여부',
  `ARC_THUMB` int(11) DEFAULT NULL COMMENT '썸네일 파일 번호',
  `ARC_META_CODE1` varchar(500) DEFAULT NULL COMMENT '메타코드1',
  `ARC_META_CODE2` varchar(500) DEFAULT NULL COMMENT '메타코드2',
  `ARC_META_CODE3` varchar(500) DEFAULT NULL COMMENT '메타코드3',
  `ARC_MEDIA` varchar(200) DEFAULT NULL COMMENT '매체',
  `ARC_YEAR` varchar(50) DEFAULT NULL COMMENT '제작년도',
  `ARC_ADVERTISER` varchar(100) DEFAULT NULL COMMENT '광고주',
  `ARC_PRODUCT` varchar(100) DEFAULT NULL COMMENT '제품명',
  `ARC_ACTOR` varchar(50) DEFAULT NULL COMMENT '배우,성우',
  `ARC_PERIOD` varchar(50) DEFAULT NULL COMMENT '기간',
  `ARC_THUMB_TEXT` varchar(200) DEFAULT NULL COMMENT '썸내일 대체텍스트',
  `ARC_SELECTED1` int(11) DEFAULT NULL COMMENT '선택여부',
  `ARC_TAG` varchar(500) DEFAULT NULL COMMENT '테그(키워드)',
  PRIMARY KEY (`ARC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='디지털 아카이브';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_archive_category`
--

DROP TABLE IF EXISTS `asa_archive_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_archive_category` (
  `CAT_ID` varchar(50) NOT NULL,
  `CAT_NAME` varchar(200) DEFAULT NULL COMMENT '카테고리 이름',
  `CAT_PAGE_SIZE` int(11) DEFAULT NULL COMMENT '페이지 크기',
  `CAT_USER_LIST_TEMPLATE` varchar(100) DEFAULT NULL COMMENT '사용자 목록 템플릿',
  `CAT_USER_VIEW_TEMPLATE` varchar(100) DEFAULT NULL COMMENT '사용자 뷰 템플릿',
  `CAT_ADMIN_LIST_TEMPLATE` varchar(100) DEFAULT NULL COMMENT '관리자 목록 템플릿',
  `CAT_ADMIN_FORM_TEMPLATE` varchar(100) DEFAULT NULL COMMENT '관리자 폼 템플릿',
  `CAT_META_CODE1` varchar(50) DEFAULT NULL COMMENT '메타코드1',
  `CAT_META_CODE2` varchar(50) DEFAULT NULL COMMENT '메타코드2',
  `CAT_META_CODE3` varchar(50) DEFAULT NULL COMMENT '메타코드3',
  `CAT_SORT_ORDER` varchar(50) DEFAULT NULL COMMENT '정렬필드',
  `CAT_SORT_DIRECTION` varchar(50) DEFAULT NULL COMMENT '정렬방향',
  `CAT_CUSTOM_TYPE` varchar(50) DEFAULT NULL COMMENT '아카이브 타입',
  `CAT_REGDATE` datetime DEFAULT NULL COMMENT '등록일',
  `CAT_SUPPORT_SELECT1` int(11) DEFAULT NULL COMMENT '추출1 기능 사용여부',
  `CAT_SUPPORT_NURI` int(11) DEFAULT NULL COMMENT '공공누리 사용여부',
  `CAT_SUPPORT_RECOMMEND` int(11) DEFAULT '0' COMMENT '추천기능 사요여부',
  `CAT_SUPPORT_FIXING` int(11) DEFAULT NULL COMMENT '상단 별도분리 출력 사용여부',
  `CAT_FIXING_NUM` int(11) DEFAULT NULL COMMENT '상단 별도분리 출력 갯수',
  `CAT_SUPPORT_IMAGE` int(11) DEFAULT NULL COMMENT '대표이미지 사용여부',
  `CAT_SUPPORT_THUMBNAIL` int(11) DEFAULT NULL COMMENT '썸네일 사용여부',
  `CAT_THUMBNAIL_CROP` int(11) DEFAULT NULL COMMENT '썸네일 생성시 이미지 자르기 여부',
  `CAT_THUMBNAIL_WIDTH` int(11) DEFAULT NULL COMMENT '썸네일 생성시 가로길이',
  `CAT_THUMBNAIL_HEIGHT` int(11) DEFAULT NULL COMMENT '썸네일 생성시 세로길이',
  `CAT_UPLOAD_FILE_NAME` int(11) DEFAULT NULL COMMENT '첨부가능한 파일갯수',
  `CAT_UPLOAD_SIZE_MAX` int(11) DEFAULT NULL COMMENT '첨부파일 업로드 사이즈 제한 / MB단위',
  PRIMARY KEY (`CAT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='아카이브 카테고리 정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_banner`
--

DROP TABLE IF EXISTS `asa_banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_banner` (
  `BN_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '아이디',
  `BN_TYPE` varchar(30) DEFAULT NULL COMMENT '유형',
  `BN_ORDER` int(11) DEFAULT NULL COMMENT '순서',
  `BN_NAME` varchar(600) DEFAULT NULL COMMENT '이름',
  `BN_DESCRIPTION` varchar(2000) DEFAULT NULL COMMENT '설명',
  `BN_LINK` varchar(1000) DEFAULT NULL COMMENT '링크주소',
  `BN_NEW_WIN` int(11) DEFAULT NULL COMMENT '링크대상 새창여부',
  `BN_START_DATE` varchar(20) DEFAULT NULL COMMENT '개시 시작일',
  `BN_END_DATE` varchar(20) DEFAULT NULL COMMENT '개시 종료일',
  `BN_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  `BN_USE` int(11) DEFAULT NULL COMMENT '사용유무',
  `BN_EXT` varchar(20) DEFAULT NULL COMMENT '파일확장자',
  `BN_WIDTH` int(11) DEFAULT NULL COMMENT '팝업창 가로',
  `BN_HEIGHT` int(11) DEFAULT NULL COMMENT '팝업창 세로',
  `BN_TOP` int(11) DEFAULT NULL COMMENT '팝업창 TOP위치',
  `BN_LEFT` int(11) DEFAULT NULL COMMENT '팝업창 LEFT위치',
  `BN_THUMB` int(11) DEFAULT NULL COMMENT '이미지 정보',
  `SITE_PREFIX` varchar(50) DEFAULT NULL COMMENT '사이트 프리픽스',
  PRIMARY KEY (`BN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='배너, 메인비주얼, 팝업존, 팝업';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_board_article`
--

DROP TABLE IF EXISTS `asa_board_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_board_article` (
  `BA_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '게시글 아이디',
  `BC_ID` varchar(100) NOT NULL COMMENT '게시판 아이디',
  `BA_TITLE` varchar(1000) DEFAULT NULL COMMENT '제목',
  `BA_CONTENT_HTML` longtext COMMENT '내용(html)',
  `BA_CONTENT_PLAIN` varchar(4000) DEFAULT NULL COMMENT '내용(text)',
  `BA_NOTICE` int(11) DEFAULT NULL COMMENT '공지글 여부',
  `BA_NOTICE_STARTDATE` varchar(20) DEFAULT NULL COMMENT '공지 시작일-사용안함',
  `BA_NOTICE_ENDDATE` varchar(20) DEFAULT NULL COMMENT '공지 종료일',
  `BA_SECRET` int(11) DEFAULT NULL COMMENT '비밀글 여부',
  `BA_SECRET_PASSWORD` varchar(200) DEFAULT NULL COMMENT '비밀글 비밀번호',
  `BA_CATEGORY1` varchar(50) DEFAULT NULL COMMENT '분류1(코드)',
  `BA_CATEGORY2` varchar(50) DEFAULT NULL COMMENT '분류2(코드)',
  `BA_CATEGORY3` varchar(50) DEFAULT NULL COMMENT '분류3(코드)',
  `BA_STATUS` varchar(50) DEFAULT NULL COMMENT '진행상태(코드)',
  `MEM_ID` varchar(50) DEFAULT NULL COMMENT '작성자 ID',
  `DEP_ID` varchar(50) DEFAULT NULL COMMENT '작성자 부서 코드',
  `BA_GUEST_NAME` varchar(200) DEFAULT NULL COMMENT '비회원 작성자 이름',
  `BA_GUEST_PASSWORD` varchar(200) DEFAULT NULL COMMENT '비회원 작성 비밀번호',
  `BA_EMAIL` varchar(200) DEFAULT NULL COMMENT '작성자 이메일',
  `BA_IP` varchar(20) DEFAULT NULL COMMENT '작성자 IP',
  `BA_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  `BA_LAST_MODIFIED` datetime DEFAULT NULL COMMENT '마지막 수정일시',
  `BA_UPDATER_ID` varchar(50) DEFAULT NULL COMMENT '수정자 아이디',
  `BA_HIT` int(11) DEFAULT '0' COMMENT '조회수',
  `BA_RECOMMEND` int(11) DEFAULT '0' COMMENT '추천수',
  `BA_COMMENT_TOTAL` int(11) DEFAULT '0' COMMENT '댓글수',
  `BA_THUMB` int(11) DEFAULT NULL COMMENT '대표이미지 ',
  `BA_NURI` varchar(50) DEFAULT NULL COMMENT '공공누리',
  `BA_USE` int(11) DEFAULT NULL COMMENT '게시유무',
  `BA_MAIN_SELEC` int(11) DEFAULT '0' COMMENT '메인페이지 추출',
  `BA_COMM_SELEC` int(11) DEFAULT '0' COMMENT '공통게시판 추출',
  `BA_START_DATE` varchar(20) DEFAULT NULL COMMENT '게시 시작일',
  `BA_START_TIME` varchar(20) DEFAULT NULL COMMENT '게시 시작시간',
  `BA_END_DATE` varchar(20) DEFAULT NULL COMMENT '게시 종료일',
  `BA_COM_SEL_CAT` varchar(50) DEFAULT NULL COMMENT '공통추출대상 분류코드',
  `BA_THUMB_TEXT` varchar(500) DEFAULT NULL COMMENT '대표이미지 대체텍스트',
  `BA_ANSWER` varchar(4000) DEFAULT NULL COMMENT '답글',
  `BA_CUSTOM_FIELD_1` varchar(500) DEFAULT NULL COMMENT '사용자 정의 필드 1',
  `BA_CUSTOM_FIELD_2` varchar(500) DEFAULT NULL COMMENT '사용자 정의 필드 2',
  `BA_CUSTOM_FIELD_3` varchar(500) DEFAULT NULL COMMENT '사용자 정의 필드 3',
  `BA_CUSTOM_FIELD_4` varchar(500) DEFAULT NULL COMMENT '사용자 정의 필드 4',
  `BA_CUSTOM_FIELD_5` varchar(500) DEFAULT NULL COMMENT '사용자 정의 필드 5',
  `BA_CUSTOM_FIELD_6` varchar(500) DEFAULT NULL COMMENT '사용자 정의 필드 6',
  `BA_CUSTOM_FIELD_7` varchar(500) DEFAULT NULL COMMENT '사용자 정의 필드 7',
  `BA_CUSTOM_FIELD_8` varchar(500) DEFAULT NULL COMMENT '사용자 정의 필드 8',
  `BA_CUSTOM_FIELD_9` varchar(500) DEFAULT NULL COMMENT '사용자 정의 필드 9',
  `BA_CUSTOM_FIELD_10` varchar(500) DEFAULT NULL COMMENT '사용자 정의 필드 10',
  PRIMARY KEY (`BA_ID`),
  KEY `ASA_BOARD_ARTICLE_FK1_idx` (`BC_ID`),
  CONSTRAINT `ASA_BOARD_ARTICLE_FK1` FOREIGN KEY (`BC_ID`) REFERENCES `asa_board_config` (`BC_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게시글';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_board_config`
--

DROP TABLE IF EXISTS `asa_board_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_board_config` (
  `BC_ID` varchar(100) NOT NULL COMMENT '게시판 아이디',
  `BC_NAME` varchar(100) DEFAULT NULL COMMENT '게시판 이름',
  `BC_USE` int(11) DEFAULT '1' COMMENT '게시판 사용상태',
  `BC_TYPE` varchar(50) DEFAULT NULL COMMENT '게시판 유형',
  `BC_CATEGORY1` varchar(50) DEFAULT NULL COMMENT '구분1 코드',
  `BC_CATEGORY1_NAME` varchar(100) DEFAULT NULL COMMENT '구분1 이름',
  `BC_CATEGORY2` varchar(50) DEFAULT NULL COMMENT '구분2 코드',
  `BC_CATEGORY2_NAME` varchar(100) DEFAULT NULL COMMENT '구분2 이름',
  `BC_CATEGORY3` varchar(50) DEFAULT NULL COMMENT '구분3 코드',
  `BC_CATEGORY3_NAME` varchar(100) DEFAULT NULL COMMENT '구분3 이름',
  `BC_STATUS_CODE` varchar(500) DEFAULT NULL COMMENT '진행상태 코드',
  `BC_LIST_FILE` varchar(500) DEFAULT NULL COMMENT '목록 템플릿',
  `BC_VIEW_FILE` varchar(500) DEFAULT NULL COMMENT '뷰 템플릿',
  `BC_FORM_FILE` varchar(500) DEFAULT NULL COMMENT '폼 템플릿',
  `BC_DEPARTMENT` varchar(4000) DEFAULT NULL COMMENT '관리 부서 코드',
  `BC_GROUP` varchar(50) DEFAULT NULL COMMENT '관리 그룹 아이디',
  `BC_ALLOW_MEMBER_LIST` int(11) DEFAULT '1' COMMENT '회원 목록접근 허용여부',
  `BC_ALLOW_MEMBER_VIEW` int(11) DEFAULT '1' COMMENT '회원 글조회 허용여부',
  `BC_ALLOW_MEMBER_FORM` int(11) DEFAULT '1' COMMENT '회원 글쓰기/수정 허용여부',
  `BC_ALLOW_MEMBER_DOWNLOAD` int(11) DEFAULT '1' COMMENT '회원 첨부파일 다운로드 허용여부',
  `BC_ALLOW_GUEST_LIST` int(11) DEFAULT '1' COMMENT '비회원 목록접근 허용여부',
  `BC_ALLOW_GUEST_VIEW` int(11) DEFAULT '1' COMMENT '비회원 글조회 허용여부',
  `BC_ALLOW_GUEST_FORM` int(11) DEFAULT '1' COMMENT '비회원 글쓰기/수정 허용여부',
  `BC_ALLOW_GUEST_DOWNLOAD` int(11) DEFAULT '1' COMMENT '비회원 첨부파일 다운로드 허용여부',
  `BC_SUPPORT_NOTICE` int(11) DEFAULT '1' COMMENT '공지글 기능 사용여부',
  `BC_NOTICE_EVERY_PAGE` int(11) DEFAULT '0' COMMENT '공지글 표시방법(모든페이지/첫페이지)',
  `BC_SUPPORT_SECRET` int(11) DEFAULT '1' COMMENT '비밀글기능 사용여부',
  `BC_SUPPORT_COMMENT` int(11) DEFAULT '1' COMMENT '댓글기능 사용여부',
  `BC_SUPPORT_ANSWER` int(11) DEFAULT '0' COMMENT '답글기능 사용여부',
  `BC_SUPPORT_RECOMMEND` int(11) DEFAULT '0' COMMENT '추천기능 사용여부',
  `BC_SUPPORT_THUMBNAIL` int(11) DEFAULT '1' COMMENT '썸네일 사용여부',
  `BC_THUMBNAIL_CROP` int(11) DEFAULT '0' COMMENT '썸네일 생성시 이미지 자르기 여부',
  `BC_THUMBNAIL_WIDTH` int(11) DEFAULT '200' COMMENT '썸네일 생성시 가로길이',
  `BC_THUMBNAIL_HEIGHT` int(11) DEFAULT '150' COMMENT '썸네일 생성시 세로길이',
  `BC_UPLOAD_FILE_NUM` int(11) DEFAULT '5' COMMENT '첨부가능한 파일갯수',
  `BC_UPLOAD_SIZE_MAX` int(11) DEFAULT '10' COMMENT '첨부파일 업로드 사이즈 제한',
  `BC_PAGE_SIZE` int(11) DEFAULT '10' COMMENT '목록 페이지 크기',
  `BC_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  `BC_ORGANIZATION` varchar(50) DEFAULT NULL COMMENT '기관코드',
  `BC_SUPPORT_NURI` int(11) DEFAULT '0' COMMENT '공공누리 사용여부',
  `BC_SUPPORT_IMAGE` int(11) DEFAULT '1' COMMENT '대표이미지 사용여부',
  `BC_SUPPORT_HIT_DAY` int(11) DEFAULT '1' COMMENT '조회수 증가방법',
  `BC_SUPPORT_MAIN_SELEC` int(11) DEFAULT '0' COMMENT '메인페이지 추출기능 사용여부',
  `BC_SUPPORT_COMM_SELEC` int(11) DEFAULT '0' COMMENT '공통게시판 추출기능 사용여부',
  `BC_SUPPORT_OPEN_DAY` int(11) DEFAULT '0' COMMENT '게시날짜 설정기능 사용여부',
  `BC_CUSTOM_FIELD_1` varchar(50) DEFAULT NULL COMMENT '사용자정의 필드명 1',
  `BC_CUSTOM_FIELD_2` varchar(50) DEFAULT NULL COMMENT '사용자정의 필드명 2',
  `BC_CUSTOM_FIELD_3` varchar(50) DEFAULT NULL COMMENT '사용자정의 필드명 3',
  `BC_CUSTOM_FIELD_4` varchar(50) DEFAULT NULL COMMENT '사용자정의 필드명 4',
  `BC_CUSTOM_FIELD_5` varchar(50) DEFAULT NULL COMMENT '사용자정의 필드명 5',
  `BC_CUSTOM_FIELD_6` varchar(50) DEFAULT NULL COMMENT '사용자정의 필드명 6',
  `BC_CUSTOM_FIELD_7` varchar(50) DEFAULT NULL COMMENT '사용자정의 필드명 7',
  `BC_CUSTOM_FIELD_8` varchar(50) DEFAULT NULL COMMENT '사용자정의 필드명 8',
  `BC_CUSTOM_FIELD_9` varchar(50) DEFAULT NULL COMMENT '사용자정의 필드명 9',
  `BC_CUSTOM_FIELD_10` varchar(50) DEFAULT NULL COMMENT '사용자정의 필드명 10',
  `BC_CUSTOM_FIELD_USE` varchar(100) DEFAULT NULL COMMENT '사용자정의 필드 사용유무 리스트',
  PRIMARY KEY (`BC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게시판 설정';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_capability`
--

DROP TABLE IF EXISTS `asa_capability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_capability` (
  `CAP_ID` varchar(100) NOT NULL COMMENT '권한 아이디',
  `CAP_NAME` varchar(100) DEFAULT NULL COMMENT '권한 이름',
  `CAP_DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '권한 설명',
  `CAP_VALUE` varchar(255) DEFAULT NULL COMMENT '권한 값',
  `CAP_PARAM1_KEY` varchar(50) DEFAULT NULL COMMENT '추가 파라메터1 키',
  `CAP_PARAM1_VALUE` varchar(255) DEFAULT NULL COMMENT '추가 파라메터1 값',
  `CAP_PARAM2_KEY` varchar(50) DEFAULT NULL COMMENT '추가 파라메터2 키',
  `CAP_PARAM2_VALUE` varchar(255) DEFAULT NULL COMMENT '추가 파라메터2 값',
  `CAP_HTTP_METHOD` varchar(10) DEFAULT NULL COMMENT 'HTTP 메소드',
  `CAP_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  `CAP_DEFAULT` int(11) NOT NULL DEFAULT '0' COMMENT '시스템기본권한 여부',
  `CAP_PRIORITY` int(11) DEFAULT NULL COMMENT '우선순위',
  `CAP_HIDDEN` int(11) DEFAULT NULL COMMENT '숨김여부',
  PRIMARY KEY (`CAP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='권한';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_code`
--

DROP TABLE IF EXISTS `asa_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_code` (
  `CODE_ID` varchar(30) NOT NULL DEFAULT '' COMMENT '코드 아이디',
  `CAT_ID` varchar(30) NOT NULL DEFAULT '' COMMENT '코드 분류 아이디',
  `CODE_NAME` varchar(100) DEFAULT NULL COMMENT '코드 이름',
  `CODE_NAME_EN` varchar(100) DEFAULT '' COMMENT '코드 영문 이름',
  `CODE_DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '코드 설명',
  `CODE_USE` int(11) DEFAULT '1' COMMENT '사용여부',
  `CODE_REGDATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `CODE_ORDER` int(11) DEFAULT '1' COMMENT '순서',
  PRIMARY KEY (`CODE_ID`,`CAT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='코드아이템';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_code_category`
--

DROP TABLE IF EXISTS `asa_code_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_code_category` (
  `CAT_ID` varchar(30) NOT NULL DEFAULT '' COMMENT '코드 분류 아이디',
  `CAT_NAME` varchar(50) DEFAULT NULL COMMENT '코드 분류 이름',
  `CAT_NAME_EN` varchar(100) DEFAULT '' COMMENT '코드 분류 영문 이름',
  `CAT_DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '코드 분류 설명',
  `CAT_REGDATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  PRIMARY KEY (`CAT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='코드분류';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_comment`
--

DROP TABLE IF EXISTS `asa_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_comment` (
  `CMT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '댓글 아이디',
  `CMT_PARENT_ID` int(11) DEFAULT NULL COMMENT '부모댓글 아이디',
  `CMT_TITLE` varchar(200) DEFAULT NULL COMMENT '원본글 제목',
  `CMT_CONTENT` varchar(1000) DEFAULT NULL COMMENT '댓글 내용',
  `CMT_MODULE` varchar(100) DEFAULT NULL COMMENT '댓글 사용 모듈',
  `CMT_MODULE_SUB` varchar(100) DEFAULT NULL COMMENT '댓글 사용 하위모듈',
  `CMT_MOD_CATEGORY` varchar(100) DEFAULT NULL COMMENT '댓글 사용 모듈 카테고리',
  `CMT_MOD_ITEM_ID` varchar(100) DEFAULT NULL COMMENT '댓글 사용 아이템 아이디',
  `CMT_PAGE_URL` varchar(2000) DEFAULT NULL COMMENT '댓글 작성된 페이지 URL',
  `CMT_LOGIN_TYPE` varchar(50) DEFAULT NULL COMMENT '로그인 타입',
  `CMT_USER_ID` varchar(100) DEFAULT NULL COMMENT '작성자 아이디',
  `CMT_SNS_USER_ID` varchar(100) DEFAULT NULL COMMENT 'SNS 계정 아이디',
  `CMT_SNS_USER_NAME` varchar(100) DEFAULT NULL COMMENT 'SNS 계정 이름(닉네임)',
  `CMT_SNS_USER_HOME` varchar(200) DEFAULT NULL COMMENT 'SNS 작성자 홈',
  `CMT_PROFILE_IMAGE` varchar(1000) DEFAULT NULL COMMENT '작성자 프로필 이미지',
  `CMT_AGREE` int(11) DEFAULT '0' COMMENT '찬성수',
  `CMT_DISAGREE` int(11) DEFAULT '0' COMMENT '반대수',
  `CMT_RECOMMEND` int(11) DEFAULT '0' COMMENT '추천수',
  `CMT_REPORT` int(11) DEFAULT '0' COMMENT '신고수',
  `CMT_REG_IP` varchar(20) DEFAULT NULL COMMENT '댓글 작성자 아이피',
  `CMT_REGDATE` datetime DEFAULT NULL COMMENT '댓글 작성일시',
  `CMT_STATUS` varchar(50) DEFAULT NULL COMMENT '댓글 상태',
  PRIMARY KEY (`CMT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='공통 댓글 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_common_content`
--

DROP TABLE IF EXISTS `asa_common_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_common_content` (
  `COM_CONT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `COM_CONT_MODULE` varchar(50) DEFAULT NULL COMMENT '적용모듈',
  `COM_CONT_MODULE_SUB` varchar(50) DEFAULT NULL COMMENT '적용 서브 모듈',
  `COM_CONT_CATE1` varchar(500) DEFAULT NULL COMMENT '카테고리1',
  `COM_CONT_CATE2` varchar(500) DEFAULT NULL COMMENT '카테고리2',
  `COM_CONT_CATE3` varchar(500) DEFAULT NULL COMMENT '카테고리3',
  `COM_CONT_TITLE` varchar(300) DEFAULT NULL COMMENT '제목',
  `COM_CONT_SUB_TITLE` varchar(2000) DEFAULT NULL COMMENT '간단설명',
  `COM_CONT_CONTENT` longtext COMMENT '콘텐츠',
  `COM_CONT_STATUS` varchar(20) DEFAULT NULL COMMENT '노출 상태',
  `COM_CONT_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  `COM_CONT_MODIFY_DATE` datetime DEFAULT NULL COMMENT '마지막 수정일시',
  `COM_CONT_LAST_WORKER` varchar(50) DEFAULT NULL COMMENT '마지막 수정자',
  `SITE_PREFIX` varchar(50) DEFAULT NULL COMMENT '사이트프리픽스',
  PRIMARY KEY (`COM_CONT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='공통 콘텐츠 정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_common_content_rel`
--

DROP TABLE IF EXISTS `asa_common_content_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_common_content_rel` (
  `COM_CONT_ID` int(11) NOT NULL COMMENT '공통콘텐츠 아이디',
  `PROGRAM_ID` varchar(50) NOT NULL COMMENT '프로그램 아이디',
  `MODULE_CODE` varchar(50) NOT NULL COMMENT '모듈 코드',
  PRIMARY KEY (`COM_CONT_ID`,`PROGRAM_ID`,`MODULE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='공통콘텐츠 릴레이션';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_config_option`
--

DROP TABLE IF EXISTS `asa_config_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_config_option` (
  `CONF_ID` varchar(30) NOT NULL COMMENT '설정 아이디',
  `OPT_KEY` varchar(100) NOT NULL COMMENT '설정 키',
  `OPT_VALUE` varchar(4000) DEFAULT NULL COMMENT '설정 값',
  `OPT_NAME` varchar(100) DEFAULT NULL COMMENT '설정 이름',
  `OPT_TYPE` varchar(20) DEFAULT NULL COMMENT '설정 유형',
  `OPT_HIDDEN` int(11) DEFAULT '0' COMMENT '숨김여부',
  `OPT_UNIT_TEXT` varchar(30) DEFAULT NULL COMMENT '단위텍스트',
  `OPT_HELP` varchar(500) DEFAULT NULL COMMENT '도움말',
  `SITE_PREFIX` varchar(50) NOT NULL COMMENT '사이트 프리픽스',
  PRIMARY KEY (`CONF_ID`,`SITE_PREFIX`,`OPT_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사이트 환경설정 정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_content`
--

DROP TABLE IF EXISTS `asa_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_content` (
  `CONTENT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `CONTENT_ROOT` int(11) DEFAULT NULL COMMENT '최초 콘텐츠 아이디',
  `CONTENT_TITLE` varchar(500) DEFAULT NULL COMMENT '콘텐츠 타이틀',
  `MENU_ID` varchar(50) DEFAULT NULL COMMENT '메뉴 아이디',
  `CONTENT` longtext COMMENT '콘텐츠 내용(HTML)',
  `CONTENT_PLAIN` varchar(4000) DEFAULT NULL COMMENT '콘텐츠 내용(TEXT)',
  `CONTENT_STATUS` varchar(20) DEFAULT NULL COMMENT '콘텐츠 상태',
  `CONTENT_VER` int(11) DEFAULT NULL COMMENT '콘텐츠 버전',
  `CONTENT_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  `CONTENT_LAST_MODIFIED` datetime DEFAULT NULL COMMENT '마지막 수정일시',
  `CONTENT_LAST_WORKER` varchar(100) DEFAULT NULL COMMENT '마지막 작성자 아이디',
  `CONTENT_MEMO` varchar(1000) DEFAULT NULL COMMENT '메모',
  `SITE_PREFIX` varchar(50) DEFAULT NULL COMMENT '사이트 프리픽스',
  PRIMARY KEY (`CONTENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메뉴 콘텐츠';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_content_statis_30`
--

DROP TABLE IF EXISTS `asa_content_statis_30`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_content_statis_30` (
  `SITE_PREFIX` varchar(50) DEFAULT NULL COMMENT '사이트 프리픽스',
  `CS_MODIUL_CODE` varchar(50) DEFAULT NULL COMMENT '모듈 코드',
  `CS_MODIUL_SUB_CODE` varchar(50) DEFAULT NULL COMMENT '모듈 서브 코드',
  `CS_CATE_CODE` varchar(50) DEFAULT NULL COMMENT '카테고리 코드',
  `CS_CONTENT_ID` varchar(50) DEFAULT NULL COMMENT '콘텐츠 아이디',
  `CS_YEAR` int(11) DEFAULT NULL COMMENT '년',
  `CS_MONTH` int(11) DEFAULT NULL COMMENT '월',
  `CS_DAY` int(11) DEFAULT NULL COMMENT '일',
  `CS_COUNT` int(11) DEFAULT NULL COMMENT '추천 수',
  `CS_START_DAY` varchar(20) DEFAULT NULL COMMENT '통계 시작일자',
  `CS_END_DAY` varchar(20) DEFAULT NULL COMMENT '통계 종료일자',
  KEY `ASA_CONTENT_STATIS_30_INDEX1` (`SITE_PREFIX`,`CS_MODIUL_CODE`,`CS_MODIUL_SUB_CODE`,`CS_CATE_CODE`,`CS_CONTENT_ID`,`CS_YEAR`,`CS_MONTH`,`CS_DAY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='콘텐츠 추천 통계 최근 30일';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_content_statis_7`
--

DROP TABLE IF EXISTS `asa_content_statis_7`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_content_statis_7` (
  `SITE_PREFIX` varchar(50) DEFAULT NULL COMMENT '사이트 프리픽스',
  `CS_MODIUL_CODE` varchar(50) DEFAULT NULL COMMENT '모듈 코드',
  `CS_MODIUL_SUB_CODE` varchar(50) DEFAULT NULL COMMENT '모듈 서브 코드',
  `CS_CATE_CODE` varchar(50) DEFAULT NULL COMMENT '카테고리 코드',
  `CS_CONTENT_ID` varchar(50) DEFAULT NULL COMMENT '콘텐츠 아이디',
  `CS_YEAR` int(11) DEFAULT NULL COMMENT '년',
  `CS_MONTH` int(11) DEFAULT NULL COMMENT '월',
  `CS_DAY` int(11) DEFAULT NULL COMMENT '일',
  `CS_COUNT` int(11) DEFAULT NULL COMMENT '추천 수',
  `CS_START_DAY` varchar(20) DEFAULT NULL COMMENT '7일 시작일',
  `CS_END_DAY` varchar(20) DEFAULT NULL COMMENT '7일 종료일',
  KEY `ASA_CONTENT_STATIS_7_INDEX1` (`SITE_PREFIX`,`CS_MODIUL_CODE`,`CS_MODIUL_SUB_CODE`,`CS_CATE_CODE`,`CS_CONTENT_ID`,`CS_YEAR`,`CS_MONTH`,`CS_DAY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='콘텐츠 추천 통계 최근 7일';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_content_statis_day`
--

DROP TABLE IF EXISTS `asa_content_statis_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_content_statis_day` (
  `SITE_PREFIX` varchar(50) DEFAULT NULL COMMENT '사이트 프리픽스',
  `CS_MODIUL_CODE` varchar(50) DEFAULT NULL COMMENT '모듈 코드',
  `CS_MODIUL_SUB_CODE` varchar(50) DEFAULT NULL COMMENT '모듈 서브 코드',
  `CS_CATE_CODE` varchar(50) DEFAULT NULL COMMENT '카테고리 코드',
  `CS_CONTENT_ID` varchar(50) DEFAULT NULL COMMENT '콘텐츠 아이디',
  `CS_YEAR` int(11) DEFAULT NULL COMMENT '년',
  `CS_MONTH` int(11) DEFAULT NULL COMMENT '월',
  `CS_DAY` int(11) DEFAULT NULL COMMENT '일',
  `CS_COUNT` int(11) DEFAULT NULL COMMENT '추천 수',
  `CS_WEEK_START` varchar(20) DEFAULT NULL COMMENT '주 시작일자',
  `CS_WEEK_END` varchar(20) DEFAULT NULL COMMENT '주 종료일자',
  `WEEK_OF_YEAR_ISO` varchar(20) DEFAULT NULL COMMENT '년도별 주차',
  `WEEK_OF_MONTH` varchar(20) DEFAULT NULL COMMENT '월별 주차',
  KEY `ASA_CONTENT_STATIS_DAY_INDEX1` (`SITE_PREFIX`,`CS_MODIUL_CODE`,`CS_MODIUL_SUB_CODE`,`CS_CATE_CODE`,`CS_CONTENT_ID`,`CS_YEAR`,`CS_MONTH`,`CS_DAY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='콘텐츠의 추천 통계 (일별)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_education_reservation`
--

DROP TABLE IF EXISTS `asa_education_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_education_reservation` (
  `RESERV_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `RESERV_NAME` varchar(50) DEFAULT NULL COMMENT '신청자명',
  `RESERV_GROUP` varchar(200) DEFAULT NULL COMMENT '신청자 소속 그룹',
  `RESERV_ADDRESS` varchar(400) DEFAULT NULL COMMENT '신청자 주소',
  `RESERV_EMAIL` varchar(200) DEFAULT NULL COMMENT '신청자 이메일',
  `RESERV_HP` varchar(50) DEFAULT NULL COMMENT '신청자 연락처',
  `RESERV_PEOPLE` int(11) DEFAULT NULL COMMENT '교육 인원',
  `RESERV_DATE` varchar(20) DEFAULT NULL COMMENT '교육 날짜',
  `RESERV_STIME` varchar(20) DEFAULT NULL COMMENT '교육 시작시간',
  `RESERV_ETIME` varchar(20) DEFAULT NULL COMMENT '교육 종료시간',
  `RESERV_REGDATE` datetime DEFAULT NULL COMMENT '신청일자',
  `RESERV_MEMO` varchar(4000) DEFAULT NULL COMMENT '메모',
  `RESERV_STATUS` varchar(50) DEFAULT NULL COMMENT '진행상태',
  `RESERV_AGREE` int(11) DEFAULT NULL COMMENT '개인정보이용동의',
  PRIMARY KEY (`RESERV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='교육프로그램 신청정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_email_target`
--

DROP TABLE IF EXISTS `asa_email_target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_email_target` (
  `ET_ID` varchar(50) NOT NULL COMMENT '메일대상 아이디',
  `ET_CATE` varchar(50) DEFAULT NULL COMMENT '구분코드',
  `ET_TITLE` varchar(200) DEFAULT NULL COMMENT '제목',
  `ET_TARGET` varchar(2000) DEFAULT NULL COMMENT '수신자',
  `ET_CONTENTS` varchar(4000) DEFAULT NULL COMMENT '내용',
  `ET_FORM` varchar(100) DEFAULT NULL COMMENT '발송폼',
  `ET_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (`ET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='이메일대상 정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_fileinfo`
--

DROP TABLE IF EXISTS `asa_fileinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_fileinfo` (
  `FILE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '파일아이디',
  `MEMBER_ID` varchar(50) DEFAULT NULL COMMENT '등록회원 아이디',
  `FILE_MODULE` varchar(50) DEFAULT NULL COMMENT '첨부된 모듈명',
  `FILE_MODULE_ID` varchar(50) DEFAULT NULL COMMENT '첨부된 모듈 아이디',
  `FILE_MODULE_SUB` varchar(50) DEFAULT NULL COMMENT '첨부된 하위 모듈',
  `FILE_MODULE_SUB_ID` varchar(50) DEFAULT NULL COMMENT '첨부된 하위 모듈 아이디',
  `FILE_ORIGINAL_NAME` varchar(500) DEFAULT NULL COMMENT '원본파일명',
  `FILE_PATH` varchar(1000) DEFAULT NULL COMMENT '저장위치',
  `FILE_THUMBNAIL_PATH` varchar(200) DEFAULT NULL COMMENT '썸네일파일 저장위치',
  `FILE_EXT` varchar(20) DEFAULT NULL COMMENT '파일확장자',
  `FILE_MIME_TYPE` varchar(200) DEFAULT NULL COMMENT 'MIME 유형',
  `FILE_MEDIA_TYPE` varchar(20) DEFAULT NULL COMMENT '파일 종류',
  `FILE_SIZE` int(11) DEFAULT NULL COMMENT '파일크기',
  `FILE_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  `FILE_ALT_TEXT` varchar(500) DEFAULT NULL COMMENT '대체텍스트',
  `FILE_DOWNLOAD_COUNT` int(11) DEFAULT '0' COMMENT '다운로드 횟수',
  `FILE_UUID` varchar(100) DEFAULT NULL COMMENT '파일 렌덤아이디',
  `FILE_ORI_IMAGE_WIDTH` int(11) DEFAULT NULL COMMENT '원 이미지 가로크기',
  `FILE_ORI_IMAGE_HEIGHT` int(11) DEFAULT NULL COMMENT '원 이미지 세로크기',
  `FILE_ATTACHMENT_TYPE` varchar(50) DEFAULT NULL COMMENT '첨부유형',
  `FILE_SERVLET_URL` varchar(100) DEFAULT NULL COMMENT '서블릿 호출 url',
  `FILE_SERVLET_THUMBNAIL_URL` varchar(100) DEFAULT NULL COMMENT '썸네일 서블릿 호출 url',
  PRIMARY KEY (`FILE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='파일정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_menu`
--

DROP TABLE IF EXISTS `asa_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_menu` (
  `MENU_ID` varchar(100) NOT NULL COMMENT '메뉴 아이디',
  `MENU_NAME` varchar(200) DEFAULT NULL COMMENT '메뉴 이름',
  `MENU_TYPE` varchar(20) DEFAULT NULL COMMENT '메뉴 유형',
  `MENU_GNB_TYPE` varchar(20) DEFAULT NULL COMMENT '메인메뉴 이미지/텍스트 여부',
  `MENU_GNB_EXT_ON` varchar(20) DEFAULT NULL COMMENT '메인메뉴 활성화 이미지 확장자',
  `MENU_GNB_EXT_OFF` varchar(20) DEFAULT NULL COMMENT '메인메뉴 비활성화 이미지 확장자',
  `MENU_SNB_TYPE` varchar(20) DEFAULT NULL COMMENT '서브메뉴 이미지/텍스트 여부',
  `MENU_SNB_EXT_ON` varchar(20) DEFAULT NULL COMMENT '서브메뉴 활성화 이미지 확장자',
  `MENU_SNB_EXT_OFF` varchar(20) DEFAULT NULL COMMENT '서브메뉴 비활성화 이미지 확장자',
  `MENU_TITLE_TYPE` varchar(20) DEFAULT NULL COMMENT '페이지 제목 이미지/텍스트 여부',
  `MENU_TITLE_EXT` varchar(20) DEFAULT NULL COMMENT '페이지 제목 이미지 확장자',
  `MENU_TITLE_SUB_TEXT` varchar(2000) DEFAULT NULL COMMENT '페이지 제목 부가 텍스트',
  `MENU_LINK` varchar(1000) DEFAULT NULL COMMENT '메뉴 링크',
  `MENU_NEW_WIN` int(11) DEFAULT NULL COMMENT '메뉴 링크 새창열림 여부',
  `MENU_PARENT` varchar(100) DEFAULT NULL COMMENT '부모메뉴 아이디',
  `MENU_DEPTH` int(11) DEFAULT NULL COMMENT '메뉴 깊이',
  `MENU_ORDER` int(11) DEFAULT NULL COMMENT '메뉴 순서',
  `MENU_HEADER` varchar(1000) DEFAULT NULL COMMENT '헤더파일이름',
  `MENU_TEMPLATE` varchar(1000) DEFAULT NULL COMMENT '템플릿 파일이름',
  `MENU_FOOTER` varchar(1000) DEFAULT NULL COMMENT '푸터파일 이름',
  `MENU_STATUS` varchar(20) DEFAULT NULL COMMENT '메뉴 상태',
  `MENU_USE_MANAGER_INFO` int(11) DEFAULT '0' COMMENT '메뉴 관리자정보 노출 여부',
  `MENU_MANAGER` varchar(100) DEFAULT NULL COMMENT '메뉴담당자 이름',
  `MENU_DEPARTMENT` varchar(100) DEFAULT NULL COMMENT '메뉴 담당부서명',
  `MENU_PHONE` varchar(100) DEFAULT NULL COMMENT '메뉴 담당부서 전화번호',
  `MENU_EMAIL` varchar(100) DEFAULT NULL COMMENT '메뉴 담당부서 이메일주소',
  `MENU_ETC` varchar(1000) DEFAULT NULL COMMENT '기타 정보',
  `MENU_REGDATE` datetime DEFAULT NULL COMMENT '메뉴 등록일시',
  `MENU_USE_SATISFACTION` int(11) DEFAULT '0' COMMENT '만족도조사 사용여부',
  `MENU_LAST_MODIFIED` datetime DEFAULT NULL COMMENT '마지막 수정일시',
  `MENU_LOCATION` varchar(500) DEFAULT NULL COMMENT '메뉴위치',
  `SITE_PREFIX` varchar(50) NOT NULL COMMENT '사이트 프리픽스',
  `TAG_CODE` varchar(1000) DEFAULT NULL COMMENT '해시태그 코드',
  PRIMARY KEY (`MENU_ID`,`SITE_PREFIX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메뉴 정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_menu_cont_rel`
--

DROP TABLE IF EXISTS `asa_menu_cont_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_menu_cont_rel` (
  `MENU_ID` varchar(100) NOT NULL COMMENT '메뉴아이디',
  `SITE_PREFIX` varchar(50) NOT NULL COMMENT '사이트 프리픽스',
  `CONTENT_ROOT` int(11) NOT NULL COMMENT '최초 콘텐츠 아이디',
  PRIMARY KEY (`MENU_ID`,`SITE_PREFIX`,`CONTENT_ROOT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메뉴-콘텐츠 릴레이션';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_org_department`
--

DROP TABLE IF EXISTS `asa_org_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_org_department` (
  `DEP_ID` varchar(50) NOT NULL COMMENT '부서코드',
  `ORG_ID` varchar(50) DEFAULT NULL COMMENT '기관코드',
  `DEP_NAME` varchar(100) DEFAULT NULL COMMENT '부서명',
  `DEP_NAME_EN` varchar(100) DEFAULT NULL COMMENT '부서명(영문)',
  `DEP_TEL` varchar(100) DEFAULT NULL COMMENT '부서 전화번호',
  `DEP_DESCRIPTION` varchar(2000) DEFAULT NULL COMMENT '부서 소개',
  `DEP_USE` int(11) DEFAULT '1' COMMENT '사용유무',
  `DEP_REGDATE` datetime DEFAULT NULL COMMENT '등록일',
  `DEP_ORDER` int(11) DEFAULT NULL COMMENT '순서',
  PRIMARY KEY (`DEP_ID`),
  KEY `ASA_ORG_DEPARTMENT_FK1_idx` (`ORG_ID`),
  CONSTRAINT `ASA_ORG_DEPARTMENT_FK1` FOREIGN KEY (`ORG_ID`) REFERENCES `asa_org_organization` (`ORG_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='부서';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_org_organization`
--

DROP TABLE IF EXISTS `asa_org_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_org_organization` (
  `ORG_ID` varchar(50) NOT NULL COMMENT '기관코드',
  `ORG_NAME` varchar(100) DEFAULT NULL COMMENT '기관명',
  `ORG_NAME_EN` varchar(100) DEFAULT NULL COMMENT '기관명(영문)',
  `ORG_DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '기관 소개',
  `ORG_USE` int(11) DEFAULT '1' COMMENT '사용유무',
  `ORG_REGDATE` datetime DEFAULT NULL COMMENT '등록일',
  `ORG_ORDER` int(11) DEFAULT '0' COMMENT '순서',
  PRIMARY KEY (`ORG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='기관/조직';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_policy`
--

DROP TABLE IF EXISTS `asa_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_policy` (
  `ARC_ID` int(11) NOT NULL COMMENT '아카이브아이디',
  `POLI_SHORT_DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '간단설명',
  PRIMARY KEY (`ARC_ID`),
  CONSTRAINT `ASA_POLICY_FK1` FOREIGN KEY (`ARC_ID`) REFERENCES `asa_archive` (`ARC_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='정책자료';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_poll`
--

DROP TABLE IF EXISTS `asa_poll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_poll` (
  `PO_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '투표 아이디',
  `PO_QUESTION` varchar(300) DEFAULT NULL COMMENT '질문',
  `PO_DESCRIPTION` varchar(4000) DEFAULT NULL COMMENT '부가설명',
  `PO_START_DATE` varchar(10) DEFAULT NULL COMMENT '시작일',
  `PO_END_DATE` varchar(10) DEFAULT NULL COMMENT '종료일',
  `PO_TYPE` varchar(100) DEFAULT NULL COMMENT '구분',
  `PO_REGDATE` timestamp NULL DEFAULT NULL COMMENT '등록일시',
  `PO_USE` int(11) DEFAULT NULL COMMENT '게시여부',
  `PO_YES_CNT` int(11) DEFAULT '0' COMMENT '찬성수',
  `PO_NO_CNT` int(11) DEFAULT '0' COMMENT '반대수',
  `PO_HIT` int(11) DEFAULT '0' COMMENT '조회수',
  `PO_THUMB` int(11) DEFAULT NULL COMMENT '이미지파일 번호',
  `PO_THUMB_TEXT` varchar(100) DEFAULT NULL COMMENT '대표이미지 대체텍스트',
  `SITE_PREFIX` varchar(20) DEFAULT NULL COMMENT '사이트 프리픽스',
  PRIMARY KEY (`PO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='투표';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `asa_rental`
--

DROP TABLE IF EXISTS `asa_rental`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_rental` (
  `RENT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '대관/대여 아이디',
  `RENT_CATE1` varchar(50) DEFAULT NULL COMMENT '구분 카테고리 1 (코드)',
  `RENT_CATE2` varchar(200) DEFAULT NULL COMMENT '구분 카테고리 2 (코드)',
  `RENT_MANAGER` varchar(50) DEFAULT NULL COMMENT '담당자',
  `RENT_MANATER_TEL` varchar(20) DEFAULT NULL COMMENT '담당자 연락처',
  `RENT_TITLE` varchar(200) DEFAULT NULL COMMENT '대관/대여 제목',
  `RENT_DIV` varchar(20) DEFAULT NULL COMMENT '대관/대여 구분 (시설/물품 - facility / item)',
  `RENT_CHARGE` int(11) DEFAULT NULL COMMENT '대관/대여 요금',
  `RENT_RESERV_TYPE` varchar(500) DEFAULT NULL COMMENT '예약유형 (온라인 접수, 현장 접수... 등) (코드)',
  `RENT_PAYMENT_TYPE` varchar(500) DEFAULT NULL COMMENT '결제방법 (온라인 결제, 현장결제... 등) (코드)',
  `RENT_DESCRIPTION` longtext COMMENT '공간소개',
  `RENT_ETC` varchar(1000) DEFAULT NULL COMMENT '기타 정보',
  `RENT_USE` int(11) DEFAULT NULL COMMENT '게시여부',
  `RENT_THUMB` int(11) DEFAULT NULL COMMENT '이미지 파일정보 아이디',
  `RENT_POSSIBLE_DAY_TYPE` varchar(50) DEFAULT NULL COMMENT '대관/대여 가능일 구분 (상시/기간  - always/period)',
  `RENT_PERIOD_SDATE` varchar(20) DEFAULT NULL COMMENT '대관/대여 기간 시작일',
  `RENT_PERIOD_EDATE` varchar(20) DEFAULT NULL COMMENT '대관/대여 기간 종료일',
  `RENT_RESERV_SDATE_AFTER` int(11) DEFAULT NULL COMMENT '예약가능 시작일(오늘로 부터 x일 이후 시작)',
  `RENT_RESERV_EDATE_AFTER` int(11) DEFAULT NULL COMMENT '예약가능 종료일(오늘로 부터 x일 이전 종료)',
  `RENT_POSSIBLE_DAYOFWEEK` varchar(50) DEFAULT NULL COMMENT '예약가능 요일',
  `RENT_RESERV_TIME` varchar(500) DEFAULT NULL COMMENT '예약가능 시간대(평일)',
  `RENT_IMPOSSIBLE_DATE` varchar(1000) DEFAULT NULL COMMENT '예약불가능 날짜',
  `RENT_REGDATE` datetime DEFAULT NULL COMMENT '등록일',
  `RENT_STEP` varchar(20) DEFAULT NULL COMMENT '등록처리단계',
  `RENT_PARKING` int(11) DEFAULT '0' COMMENT '주차지원대수',
  `RENT_ORDER` int(11) DEFAULT NULL COMMENT '정렬순서',
  `RENT_VR` varchar(50) DEFAULT NULL COMMENT 'VR폴더명',
  `RENT_APPROVE` int(11) DEFAULT '0' COMMENT '승인기능사용여부',
  `RENT_MEMBERSHIP` int(11) DEFAULT '0' COMMENT '회원제할인 사용여부',
  `RENT_MEMBER_DISCOUNT` int(11) DEFAULT '0' COMMENT '회원제 할인률',
  `RENT_RES_MIN_TIME` int(11) DEFAULT '1' COMMENT '최소예약시간',
  `RENT_RES_MIN_NUMBER` int(11) DEFAULT '1' COMMENT '최소예약인원',
  `RENT_RES_MAX_NUMBER` int(11) DEFAULT '1' COMMENT '최대수용인원',
  `RENT_RESERV_TIME_SAT` varchar(500) DEFAULT NULL COMMENT '예약가능 시간대(토요일)',
  `RENT_RESERV_TIME_SUN` varchar(500) DEFAULT NULL COMMENT '예약가능 시간대(일요일)',
  `RENT_REFUND_DATE_BEFORE` int(11) DEFAULT '1' COMMENT '환불 가능일',
  `RENT_REFUND_RATE` int(11) DEFAULT '100' COMMENT '환불률',
  `RENT_PAYMENT_TIME_LIMIT` int(11) DEFAULT '0' COMMENT '온라인결제 제한시간',
  `RENT_RENTING_METHOD` varchar(50) DEFAULT NULL COMMENT '대관료 책정방식(시간/패키지-time/package)',
  `RENT_SHORT_DESCRIPTION` varchar(4000) DEFAULT NULL COMMENT '한줄설명',
  `RENT_FACILITY_INFO` varchar(4000) DEFAULT NULL COMMENT '시설안내',
  `RENT_PRECAUTIONS` varchar(4000) DEFAULT NULL COMMENT '주의사항',
  `RENT_REFUND_POKICY` varchar(400) DEFAULT NULL COMMENT '환불규정',
  `RENT_AVAILABLE_TIME` varchar(300) DEFAULT NULL COMMENT '예약가능시간',
  PRIMARY KEY (`RENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='대관/대여 프로그램 정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_rental_close_time`
--

DROP TABLE IF EXISTS `asa_rental_close_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_rental_close_time` (
  `RESERV_DATE` varchar(20) NOT NULL COMMENT '예약일자',
  `RESERV_CLOSE_TIME` varchar(50) NOT NULL COMMENT '예약시간',
  `RENT_ID` int(11) NOT NULL COMMENT '대관 아이디',
  PRIMARY KEY (`RESERV_DATE`,`RESERV_CLOSE_TIME`,`RENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='대관 시간 막음처리 데이터';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_rental_reservation`
--

DROP TABLE IF EXISTS `asa_rental_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_rental_reservation` (
  `RESERV_ID` varchar(50) NOT NULL COMMENT '신청정보 아이디',
  `RESERV_RENT_ID` int(11) DEFAULT NULL COMMENT '대관대여 아이디',
  `RESERV_NAME` varchar(50) DEFAULT NULL COMMENT '신청자 명',
  `RESERV_POSITION` varchar(50) DEFAULT NULL COMMENT '신청자 직위/직책',
  `RESERV_TEL` varchar(50) DEFAULT NULL COMMENT '신청자 전화번호',
  `RESERV_HP` varchar(50) DEFAULT NULL COMMENT '신청자 휴대폰번호',
  `RESERV_EMAIL` varchar(200) DEFAULT NULL COMMENT '신청자 이메일주소',
  `RESERV_ORGANIZATION` varchar(200) DEFAULT NULL COMMENT '신청기관명',
  `RESERV_ORG_DIV_CODE` varchar(50) DEFAULT NULL COMMENT '신청기관 구분 코드',
  `RESERV_ZIPCODE` varchar(50) DEFAULT NULL COMMENT '신청자 우편번호',
  `RESERV_ADDRESS` varchar(400) DEFAULT NULL COMMENT '신청자 주소',
  `RESERV_ADDRESS_DETAIL` varchar(400) DEFAULT NULL COMMENT '신청자 상세주소',
  `RESERV_TOTAL` int(11) DEFAULT '0' COMMENT '사용 인원',
  `RESERV_DATE` varchar(20) DEFAULT NULL COMMENT '예약일',
  `RESERV_TIME` varchar(1000) DEFAULT NULL COMMENT '예약시간',
  `RESERV_TIME_ADD` int(11) DEFAULT '0' COMMENT '추가시간',
  `RESERV_PAID_TYPE` varchar(50) DEFAULT NULL COMMENT '결제방법 코드',
  `RESERV_PRICE` int(11) DEFAULT '0' COMMENT '대관/대여료',
  `RESERV_DISCOUNT_RATE` int(11) DEFAULT '0' COMMENT '할인률',
  `RESERV_PAYMENT_AMOUNT` int(11) DEFAULT '0' COMMENT '결제예정금액',
  `RESERV_TITLE` varchar(500) DEFAULT NULL COMMENT '행사명',
  `RESERV_CONTENT` varchar(4000) DEFAULT NULL COMMENT '행사/전시 내용',
  `RESERV_PARKING` int(11) DEFAULT '0' COMMENT '주차인원',
  `RESERV_FILE` int(11) DEFAULT NULL COMMENT '서업자등록증 첨부파일 번호',
  `RESERV_STATUS` varchar(50) DEFAULT NULL COMMENT '신청상태',
  `RESERV_MEMO` varchar(4000) DEFAULT NULL COMMENT '비고',
  `RESERV_REGDATE` datetime DEFAULT NULL COMMENT '등록일',
  `RESERV_MEMBERSHIP` int(11) DEFAULT '0' COMMENT '멤버쉽가입여부',
  `RESERV_REQUESTS` varchar(1000) DEFAULT NULL COMMENT '요청사항',
  `RESERV_USER_ID` varchar(50) DEFAULT NULL COMMENT '신청자 아이디',
  `RESERV_USAGE_TIME` int(11) DEFAULT NULL COMMENT '사용시간',
  `RESERV_CANCEL_DATE` varchar(20) DEFAULT NULL COMMENT '예약취소날짜',
  `RESERV_APPROV_DATE` varchar(20) DEFAULT NULL COMMENT '승인날짜',
  `RESERV_PAYMENT_DATE` varchar(20) DEFAULT NULL COMMENT '결제날짜',
  `RESERV_PAY_CANCEL_DATE` varchar(20) DEFAULT NULL COMMENT '결제취소날짜',
  `RESERV_TID` varchar(50) DEFAULT NULL COMMENT '온라인 결제 코드',
  PRIMARY KEY (`RESERV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='대관/대여 예약정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_research`
--

DROP TABLE IF EXISTS `asa_research`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_research` (
  `ARC_ID` int(11) NOT NULL COMMENT '아카이브아이디',
  `RES_RESEARCHER` varchar(200) DEFAULT NULL COMMENT '연구자,단체명',
  `RES_YEAR` varchar(50) DEFAULT NULL COMMENT '연도',
  `RES_SUPPORT` varchar(200) DEFAULT NULL COMMENT '지원처',
  `RES_SUPP_PROJECT` varchar(200) DEFAULT NULL COMMENT '지원사업명',
  PRIMARY KEY (`ARC_ID`),
  CONSTRAINT `ASA_RESEARCH_FK1` FOREIGN KEY (`ARC_ID`) REFERENCES `asa_archive` (`ARC_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='연구자료';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_role`
--

DROP TABLE IF EXISTS `asa_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_role` (
  `ROLE_CODE` varchar(100) NOT NULL COMMENT '역할 코드',
  `ROLE_NAME` varchar(100) DEFAULT NULL COMMENT '역할 이름',
  `ROLE_DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '역할 설명',
  `ROLE_REGDATE` datetime DEFAULT NULL COMMENT '등록일시',
  `ROLE_DEFAULT` int(11) DEFAULT '0' COMMENT '기본역할 여부',
  `ROLE_ADMIN` int(11) DEFAULT '0' COMMENT '최고관리자 역할 여부',
  `ROLE_JOIN` int(11) DEFAULT '0' COMMENT '가입시 역할 여부',
  PRIMARY KEY (`ROLE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='역할';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_role_cap`
--

DROP TABLE IF EXISTS `asa_role_cap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_role_cap` (
  `ROLE_CODE` varchar(100) NOT NULL COMMENT '역할 코드',
  `CAP_ID` varchar(100) NOT NULL COMMENT '권한 아이디',
  PRIMARY KEY (`ROLE_CODE`,`CAP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='역할 - 권한 릴레이션';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_satis_opinion`
--

DROP TABLE IF EXISTS `asa_satis_opinion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_satis_opinion` (
  `MENU_ID` varchar(100) NOT NULL COMMENT '메뉴 아이디',
  `SITE_PREFIX` varchar(50) NOT NULL COMMENT '사이트 프리픽스',
  `SATIS_OPINION` varchar(4000) DEFAULT NULL COMMENT '평가의견',
  `SATIS_OPI_DATE` datetime DEFAULT NULL COMMENT '평가 일시',
  KEY `ASA_SATIS_OPINION_FK1` (`MENU_ID`,`SITE_PREFIX`),
  CONSTRAINT `ASA_SATIS_OPINION_FK1` FOREIGN KEY (`MENU_ID`, `SITE_PREFIX`) REFERENCES `asa_satisfaction` (`MENU_ID`, `SITE_PREFIX`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='만족도 평가의견';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_satisfaction`
--

DROP TABLE IF EXISTS `asa_satisfaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_satisfaction` (
  `MENU_ID` varchar(100) NOT NULL COMMENT '메뉴 아이디',
  `SITE_PREFIX` varchar(50) NOT NULL COMMENT '사이트 프리픽스',
  `SATIS_SCORE_5` varchar(50) DEFAULT NULL COMMENT '매우만족 횟수',
  `SATIS_SCORE_4` varchar(50) DEFAULT NULL COMMENT '만족 횟수',
  `SATIS_SCORE_3` varchar(50) DEFAULT NULL COMMENT '보통 횟수',
  `SATIS_SCORE_2` varchar(50) DEFAULT NULL COMMENT '불만 횟수',
  `SATIS_SCORE_1` varchar(50) DEFAULT NULL COMMENT '매우불만 횟수',
  `SATIS_LAST_PARTI_DATE` datetime DEFAULT NULL COMMENT '마지막 참여일시',
  `SATIS_RESET_DATE` datetime DEFAULT NULL COMMENT '조사내용 리셋 일시',
  PRIMARY KEY (`MENU_ID`,`SITE_PREFIX`),
  CONSTRAINT `ASA_SATISFACTION_FK1` FOREIGN KEY (`MENU_ID`, `SITE_PREFIX`) REFERENCES `asa_menu` (`MENU_ID`, `SITE_PREFIX`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메뉴 만족도';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_site`
--

DROP TABLE IF EXISTS `asa_site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_site` (
  `SITE_ID` varchar(20) NOT NULL COMMENT '사이트 아이디',
  `SITE_NAME` varchar(200) DEFAULT NULL COMMENT '사이트 이름',
  `SITE_DESCRIPTION` varchar(1000) DEFAULT NULL COMMENT '사이트 설명',
  `SITE_DOMAIN` varchar(500) DEFAULT NULL COMMENT '사이트 도메인',
  `SITE_TYPE` varchar(50) DEFAULT 'domain' COMMENT '사이트 유형(도메인/디렉토리)',
  `SITE_THEME` varchar(100) DEFAULT NULL COMMENT '사이트 테마',
  `SITE_MAIN` int(11) DEFAULT NULL COMMENT '메인사이트 여부',
  `SITE_LOCALE` varchar(20) DEFAULT 'ko_KR' COMMENT '사이트 운영 위치',
  `SITE_LOGO` int(11) DEFAULT NULL COMMENT '사이트 로고',
  `SITE_REGDATE` datetime DEFAULT NULL COMMENT '사이트 등록일시',
  PRIMARY KEY (`SITE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사이트 정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_space`
--

DROP TABLE IF EXISTS `asa_space`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_space` (
  `ARC_ID` int(11) NOT NULL COMMENT '아카이브 아이디',
  `SPA_LOCATION` varchar(500) DEFAULT NULL COMMENT '위치',
  `SPA_TEL` varchar(50) DEFAULT NULL COMMENT '연락처',
  `SPA_USE_HOURS` varchar(200) DEFAULT NULL COMMENT '이용시간',
  `SPA_BUSINESS_HOURS` varchar(200) DEFAULT NULL COMMENT '업무시간',
  `SPA_AGENCY` varchar(100) DEFAULT NULL COMMENT '운영기관',
  `SPA_SITE_URL` varchar(300) DEFAULT NULL COMMENT '홈페이지 url',
  PRIMARY KEY (`ARC_ID`),
  CONSTRAINT `ASA_SPACE_FK1` FOREIGN KEY (`ARC_ID`) REFERENCES `asa_archive` (`ARC_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='공간정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_statis_browser`
--

DROP TABLE IF EXISTS `asa_statis_browser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_statis_browser` (
  `STATIS_YEAR` int(11) NOT NULL COMMENT '년',
  `STATIS_MONTH` int(11) NOT NULL COMMENT '월',
  `STATIS_DAY` int(11) NOT NULL COMMENT '일',
  `STATIS_BROWSER` varchar(50) NOT NULL COMMENT '브라우저',
  `SITE_PREFIX` varchar(20) NOT NULL COMMENT '사이트프리픽스',
  `STATIS_BROWSER_CNT` int(11) DEFAULT '0' COMMENT '접속수',
  PRIMARY KEY (`STATIS_YEAR`,`STATIS_MONTH`,`STATIS_DAY`,`STATIS_BROWSER`,`SITE_PREFIX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='브라우저별 접속통계';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_statis_country`
--

DROP TABLE IF EXISTS `asa_statis_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_statis_country` (
  `STATIS_YEAR` int(11) NOT NULL COMMENT '년',
  `STATIS_MONTH` int(11) NOT NULL COMMENT '월',
  `STATIS_DAY` int(11) NOT NULL COMMENT '일',
  `STATIS_COUNTRY_KOR` varchar(100) NOT NULL COMMENT '접속국 한글명',
  `STATIS_COUNTRY_ENG` varchar(100) NOT NULL COMMENT '접속국 영문명',
  `STATIS_COUNTRY_CNT` int(11) DEFAULT '0' COMMENT '접속국별 접속수',
  `SITE_PREFIX` varchar(50) NOT NULL COMMENT '사아트프리픽스',
  PRIMARY KEY (`STATIS_YEAR`,`STATIS_MONTH`,`STATIS_DAY`,`SITE_PREFIX`,`STATIS_COUNTRY_KOR`,`STATIS_COUNTRY_ENG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='국가별 접속통계';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_statis_iso_3166_1`
--

DROP TABLE IF EXISTS `asa_statis_iso_3166_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_statis_iso_3166_1` (
  `NUM_CODE` varchar(20) DEFAULT NULL COMMENT '넘버코드',
  `ALPHA_3` varchar(20) DEFAULT NULL COMMENT '3자리 코드',
  `ALPHA_2` varchar(20) DEFAULT NULL COMMENT '2자리 코드',
  `ENG_NAME` varchar(100) DEFAULT NULL COMMENT '영문 국가명',
  `KOR_NAME` varchar(100) DEFAULT NULL COMMENT '한글 국가명'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ISO 3166-1 코드 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_statis_menu`
--

DROP TABLE IF EXISTS `asa_statis_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_statis_menu` (
  `STATIS_YEAR` int(11) NOT NULL COMMENT '년',
  `STATIS_MONTH` int(11) NOT NULL COMMENT '월',
  `STATIS_DAY` int(11) NOT NULL COMMENT '일',
  `STATIS_MENU_ID` varchar(100) NOT NULL COMMENT '메뉴아이디',
  `STATIS_MENU_CNT` int(11) DEFAULT '0' COMMENT '접속수',
  `SITE_PREFIX` varchar(50) NOT NULL COMMENT '사이트 프리픽스',
  PRIMARY KEY (`STATIS_YEAR`,`STATIS_MONTH`,`STATIS_DAY`,`STATIS_MENU_ID`,`SITE_PREFIX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메뉴별 접속통계';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_statis_os`
--

DROP TABLE IF EXISTS `asa_statis_os`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_statis_os` (
  `STATIS_YEAR` int(11) NOT NULL COMMENT '년',
  `STATIS_MONTH` int(11) NOT NULL COMMENT '월',
  `STATIS_DAY` int(11) NOT NULL COMMENT '일',
  `STATIS_OS` varchar(50) NOT NULL COMMENT 'OS',
  `SITE_PREFIX` varchar(20) NOT NULL COMMENT '사이트프리픽스',
  `STATIS_OS_CNT` int(11) DEFAULT '0' COMMENT '접속수',
  PRIMARY KEY (`STATIS_YEAR`,`STATIS_MONTH`,`STATIS_DAY`,`STATIS_OS`,`SITE_PREFIX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='운영체제별 접속통계';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_statis_session`
--

DROP TABLE IF EXISTS `asa_statis_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_statis_session` (
  `STATIS_YEAR` int(11) NOT NULL COMMENT '년',
  `STATIS_MONTH` int(11) NOT NULL COMMENT '월',
  `STATIS_DAY` int(11) NOT NULL COMMENT '일',
  `STATIS_HOUR` int(11) NOT NULL COMMENT '시',
  `SITE_PREFIX` varchar(20) NOT NULL COMMENT '사이트프리픽스',
  `STATIS_SESSION_CNT` int(11) DEFAULT '0' COMMENT '접속수',
  `WEEK_START` varchar(20) DEFAULT NULL COMMENT '해당주의 첫째일자(일요일)',
  `WEEK_END` varchar(20) DEFAULT NULL COMMENT '해당주의 마지막일자',
  `WEEK_OF_YEAR_ISO` varchar(20) DEFAULT NULL COMMENT '년도별 주차',
  `WEEK_OF_MONTH` varchar(20) DEFAULT NULL COMMENT '월별 주차',
  PRIMARY KEY (`STATIS_YEAR`,`STATIS_MONTH`,`STATIS_DAY`,`STATIS_HOUR`,`SITE_PREFIX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='세션 통계 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_statistics_temp`
--

DROP TABLE IF EXISTS `asa_statistics_temp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_statistics_temp` (
  `STATIS_YEAR` int(11) DEFAULT NULL COMMENT '년',
  `STATIS_MONTH` int(11) DEFAULT NULL COMMENT '월',
  `STATIS_DAY` int(11) DEFAULT NULL COMMENT '일',
  `STATIS_HOUR` int(11) DEFAULT NULL COMMENT '시간',
  `STATIS_OS` varchar(100) DEFAULT NULL COMMENT 'OS',
  `STATIS_BROWSER` varchar(100) DEFAULT NULL COMMENT '브라우저',
  `STATIS_IP` varchar(20) DEFAULT NULL COMMENT '리모트 IP',
  `SITE_PREFIX` varchar(20) DEFAULT NULL COMMENT '사이트 프리픽스',
  `STATIS_COUNTRY` varchar(100) DEFAULT NULL COMMENT '접속지 국가명',
  `STATIS_ISO_CODE` varchar(20) DEFAULT NULL COMMENT '접속지 ISO코드',
  `STATIS_MENU_ID` varchar(100) DEFAULT NULL COMMENT '메뉴아이디',
  `STATIS_IS_MENU` int(11) DEFAULT '0' COMMENT '통계타입이 메뉴인지 여부'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사이트 접속통계 기본데이터 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_user_inquire_log`
--

DROP TABLE IF EXISTS `asa_user_inquire_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_user_inquire_log` (
  `INQ_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '조회 로그 아이디',
  `INQ_WORKER_ID` varchar(50) DEFAULT NULL COMMENT '접속자 아이디',
  `INQ_WORKER_NAME` varchar(50) DEFAULT NULL COMMENT '접속자 이름',
  `INQ_TARGET_ID` varchar(50) DEFAULT NULL COMMENT '피검색자 아이디',
  `INQ_TARGET_NAME` varchar(50) DEFAULT NULL COMMENT '피검색자 이름',
  `INQ_URL` varchar(200) DEFAULT NULL COMMENT '요청 url',
  `INQ_WORK` varchar(100) DEFAULT NULL COMMENT '작업내역',
  `INQ_REMORT_IP` varchar(100) DEFAULT NULL COMMENT '접속자 아이피',
  `INQ_REGDATE` datetime DEFAULT NULL COMMENT '작업일시',
  PRIMARY KEY (`INQ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='회원 정보 조회, 수정, 삭제 내역 로그';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_viewing_reservation`
--

DROP TABLE IF EXISTS `asa_viewing_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_viewing_reservation` (
  `RESERV_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `RESERV_NAME` varchar(50) DEFAULT NULL COMMENT '신청자명',
  `RESERV_GROUP` varchar(200) DEFAULT NULL COMMENT '신청자 소속 그룹',
  `RESERV_ADDRESS` varchar(400) DEFAULT NULL COMMENT '신청자 주소',
  `RESERV_EMAIL` varchar(200) DEFAULT NULL COMMENT '신청자 이메일',
  `RESERV_HP` varchar(50) DEFAULT NULL COMMENT '신청자 연락처',
  `RESERV_PEOPLE` int(11) DEFAULT NULL COMMENT '관람인원',
  `RESERV_DATE` varchar(20) DEFAULT NULL COMMENT '관람 날짜',
  `RESERV_STIME` varchar(20) DEFAULT NULL COMMENT '관람 시작시간',
  `RESERV_ETIME` varchar(20) DEFAULT NULL COMMENT '관람 종료시간',
  `RESERV_REGDATE` datetime DEFAULT NULL COMMENT '신청일자',
  `RESERV_MEMO` varchar(4000) DEFAULT NULL COMMENT '메모',
  `RESERV_STATUS` varchar(50) DEFAULT NULL COMMENT '진행상태',
  `RESERV_AGREE` int(11) DEFAULT NULL COMMENT '개인정보이용동의',
  PRIMARY KEY (`RESERV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='관람 신청정보  ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asa_volunteer_reservation`
--

DROP TABLE IF EXISTS `asa_volunteer_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asa_volunteer_reservation` (
  `RESERV_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `RESERV_NAME` varchar(50) DEFAULT NULL COMMENT '이름',
  `RESERV_GROUP` varchar(200) DEFAULT NULL COMMENT '소속단체',
  `RESERV_ADDRESS` varchar(400) DEFAULT NULL COMMENT '주소',
  `RESERV_EMAIL` varchar(200) DEFAULT NULL COMMENT '이메일',
  `RESERV_HP` varchar(50) DEFAULT NULL COMMENT '연락처',
  `RESERV_PEOPLE` int(11) DEFAULT NULL COMMENT '인원',
  `RESERV_PURPOSE` varchar(4000) DEFAULT NULL COMMENT '목적',
  `RESERV_DATE` varchar(20) DEFAULT NULL COMMENT '봉사일',
  `RESERV_STIME` varchar(20) DEFAULT NULL COMMENT '봉사시작시간',
  `RESERV_ETIME` varchar(20) DEFAULT NULL COMMENT '봉사종료시간',
  `RESERV_SUGGEST` varchar(4000) DEFAULT NULL COMMENT '건의사항',
  `RESERV_REGDATE` datetime DEFAULT NULL COMMENT '등록일',
  `RESERV_STATUS` varchar(50) DEFAULT NULL COMMENT '진행상태',
  `RESERV_AGREE` int(11) DEFAULT NULL COMMENT '개인정보이용동의',
  PRIMARY KEY (`RESERV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='자원봉사 신청정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `view_archive`
--

DROP TABLE IF EXISTS `view_archive`;
/*!50001 DROP VIEW IF EXISTS `view_archive`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_archive` AS SELECT 
 1 AS `search_key`,
 1 AS `field_subject`,
 1 AS `field_desc`,
 1 AS `field_regdate`,
 1 AS `field_regdate_view`,
 1 AS `view_link`,
 1 AS `cat_id`,
 1 AS `cat_name`,
 1 AS `menu_link_temp`,
 1 AS `menu_location`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_board`
--

DROP TABLE IF EXISTS `view_board`;
/*!50001 DROP VIEW IF EXISTS `view_board`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_board` AS SELECT 
 1 AS `SEARCH_KEY`,
 1 AS `FIELD_SUBJECT`,
 1 AS `FIELD_DESC`,
 1 AS `FIELD_AUTHOR`,
 1 AS `FIELD_REGDATE`,
 1 AS `FIELD_REGDATE_VIEW`,
 1 AS `VIEW_LINK`,
 1 AS `BC_ID`,
 1 AS `BC_NAME`,
 1 AS `MENU_LINK_TEMP`,
 1 AS `MENU_LOCATION`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_ggyouth_policy`
--

DROP TABLE IF EXISTS `view_ggyouth_policy`;
/*!50001 DROP VIEW IF EXISTS `view_ggyouth_policy`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_ggyouth_policy` AS SELECT 
 1 AS `search_key`,
 1 AS `field_subject`,
 1 AS `field_desc`,
 1 AS `field_regdate`,
 1 AS `field_regdate_view`,
 1 AS `view_link`,
 1 AS `cat_id`,
 1 AS `cat_name`,
 1 AS `menu_link_temp`,
 1 AS `menu_location`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_menu`
--

DROP TABLE IF EXISTS `view_menu`;
/*!50001 DROP VIEW IF EXISTS `view_menu`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_menu` AS SELECT 
 1 AS `menu_id`,
 1 AS `site_prefix`,
 1 AS `menu_location`,
 1 AS `menu_link`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_website`
--

DROP TABLE IF EXISTS `view_website`;
/*!50001 DROP VIEW IF EXISTS `view_website`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_website` AS SELECT 
 1 AS `menu_id`,
 1 AS `site_prefix`,
 1 AS `field_subject`,
 1 AS `field_desc`,
 1 AS `menu_link`,
 1 AS `menu_location`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `view_archive`
--

/*!50001 DROP VIEW IF EXISTS `view_archive`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`ggyouth`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `view_archive` AS select `archive`.`search_key` AS `search_key`,`archive`.`field_subject` AS `field_subject`,`archive`.`field_desc` AS `field_desc`,`archive`.`field_regdate` AS `field_regdate`,`archive`.`field_regdate_view` AS `field_regdate_view`,`archive`.`view_link` AS `view_link`,`archive`.`cat_id` AS `cat_id`,`archive`.`cat_name` AS `cat_name`,`archive`.`menu_link_temp` AS `menu_link_temp`,`menu`.`MENU_LOCATION` AS `menu_location` from ((select `ar`.`ARC_ID` AS `search_key`,`ar`.`ARC_TITLE` AS `field_subject`,`ar`.`ARC_CONTENT` AS `field_desc`,date_format(`ar`.`ARC_REGDATE`,'%Y%m%d') AS `field_regdate`,date_format(`ar`.`ARC_REGDATE`,'%Y-%m-%d') AS `field_regdate_view`,concat('/site/main/archive/',`ar`.`ARC_CUSTOM_TYPE`,'/',`ar`.`ARC_CATEGORY`,'/',`ar`.`ARC_ID`) AS `view_link`,`ac`.`CAT_ID` AS `cat_id`,`ac`.`CAT_NAME` AS `cat_name`,concat('/site/main/archive/',`ar`.`ARC_CUSTOM_TYPE`,'/',`ar`.`ARC_CATEGORY`) AS `menu_link_temp` from (`asa_archive` `ar` left join `asa_archive_category` `ac` on((`ar`.`ARC_CATEGORY` = `ac`.`CAT_ID`))) where ((`ar`.`ARC_USE` = 1) and (`ar`.`ARC_CATEGORY` <> 'youth_policy'))) `archive` left join `asa_menu` `menu` on((`menu`.`MENU_LINK` = `archive`.`menu_link_temp`))) where (`menu`.`MENU_TYPE` = 'program') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_board`
--

/*!50001 DROP VIEW IF EXISTS `view_board`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`ggyouth`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `view_board` AS select `board`.`search_key` AS `SEARCH_KEY`,`board`.`field_subject` AS `FIELD_SUBJECT`,`board`.`field_desc` AS `FIELD_DESC`,`board`.`field_author` AS `FIELD_AUTHOR`,`board`.`field_regdate` AS `FIELD_REGDATE`,`board`.`field_regdate_view` AS `FIELD_REGDATE_VIEW`,`board`.`view_link` AS `VIEW_LINK`,`board`.`bc_id` AS `BC_ID`,`board`.`bc_name` AS `BC_NAME`,`board`.`menu_link_temp` AS `MENU_LINK_TEMP`,`menu`.`MENU_LOCATION` AS `MENU_LOCATION` from ((select `ba`.`BA_ID` AS `search_key`,`ba`.`BA_TITLE` AS `field_subject`,`ba`.`BA_CONTENT_HTML` AS `field_desc`,`ba`.`BA_GUEST_NAME` AS `field_author`,date_format(`ba`.`BA_REGDATE`,'%Y%m%d') AS `field_regdate`,date_format(`ba`.`BA_REGDATE`,'%Y-%m-%d') AS `field_regdate_view`,concat('/site/main/board/',`ba`.`BC_ID`,'/',`ba`.`BA_ID`,'?baCategory1=',`ba`.`BA_CATEGORY1`) AS `view_link`,`bc`.`BC_ID` AS `bc_id`,`bc`.`BC_NAME` AS `bc_name`,(case when ((`bc`.`BC_ID` = 'notice') or (`bc`.`BC_ID` = 'faq1')) then concat('/site/main/board/',`bc`.`BC_ID`,'/list?baCategory1=',`ba`.`BA_CATEGORY1`) else concat('/site/main/board/',`bc`.`BC_ID`,'/list') end) AS `menu_link_temp` from (`asa_board_article` `ba` left join `asa_board_config` `bc` on((`ba`.`BC_ID` = `bc`.`BC_ID`))) where ((`ba`.`BA_USE` = 1) and (`bc`.`BC_USE` = 1) and (not((`bc`.`BC_ID` like 'qna%'))))) `board` left join `asa_menu` `menu` on((`menu`.`MENU_LINK` = `board`.`menu_link_temp`))) where (`menu`.`MENU_TYPE` = 'program') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_ggyouth_policy`
--

/*!50001 DROP VIEW IF EXISTS `view_ggyouth_policy`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`ggyouth`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `view_ggyouth_policy` AS select `archive`.`search_key` AS `search_key`,`archive`.`field_subject` AS `field_subject`,`archive`.`field_desc` AS `field_desc`,`archive`.`field_regdate` AS `field_regdate`,`archive`.`field_regdate_view` AS `field_regdate_view`,`archive`.`view_link` AS `view_link`,`archive`.`cat_id` AS `cat_id`,`archive`.`cat_name` AS `cat_name`,`archive`.`menu_link_temp` AS `menu_link_temp`,`menu`.`MENU_LOCATION` AS `menu_location` from ((select `ar`.`ARC_ID` AS `search_key`,`ar`.`ARC_TITLE` AS `field_subject`,`ar`.`ARC_CONTENT` AS `field_desc`,date_format(`ar`.`ARC_REGDATE`,'%Y%m%d') AS `field_regdate`,date_format(`ar`.`ARC_REGDATE`,'%Y-%m-%d') AS `field_regdate_view`,concat('/site/main/archive/',`ar`.`ARC_CUSTOM_TYPE`,'/',`ar`.`ARC_CATEGORY`,'/',`ar`.`ARC_ID`) AS `view_link`,`ac`.`CAT_ID` AS `cat_id`,`ac`.`CAT_NAME` AS `cat_name`,concat('/site/main/archive/',`ar`.`ARC_CUSTOM_TYPE`,'/',`ar`.`ARC_CATEGORY`,'/',`ar`.`ARC_ID`) AS `menu_link_temp` from (`asa_archive` `ar` left join `asa_archive_category` `ac` on((`ar`.`ARC_CATEGORY` = `ac`.`CAT_ID`))) where ((`ar`.`ARC_USE` = 1) and (`ar`.`ARC_CATEGORY` = 'youth_policy'))) `archive` left join `asa_menu` `menu` on((`menu`.`MENU_LINK` = `archive`.`menu_link_temp`))) where (`menu`.`MENU_TYPE` = 'program') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_menu`
--

/*!50001 DROP VIEW IF EXISTS `view_menu`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`ggyouth`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `view_menu` AS select `asa_menu`.`MENU_ID` AS `menu_id`,`asa_menu`.`SITE_PREFIX` AS `site_prefix`,`asa_menu`.`MENU_LOCATION` AS `menu_location`,`asa_menu`.`MENU_LINK` AS `menu_link` from `asa_menu` where ((`asa_menu`.`MENU_TYPE` = 'content') and (`asa_menu`.`MENU_STATUS` = 'public') and (not((`asa_menu`.`MENU_LINK` like '%/mypage/%')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_website`
--

/*!50001 DROP VIEW IF EXISTS `view_website`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`ggyouth`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `view_website` AS select `menu`.`MENU_ID` AS `menu_id`,`menu`.`SITE_PREFIX` AS `site_prefix`,`menu`.`MENU_NAME` AS `field_subject`,`cont`.`CONTENT` AS `field_desc`,`menu`.`MENU_LINK` AS `menu_link`,`menu`.`MENU_LOCATION` AS `menu_location` from ((`asa_menu` `menu` left join `asa_menu_cont_rel` `amcr` on(((`menu`.`MENU_ID` = `amcr`.`MENU_ID`) and (`menu`.`SITE_PREFIX` = `amcr`.`SITE_PREFIX`)))) left join `asa_content` `cont` on(((`amcr`.`CONTENT_ROOT` = `cont`.`CONTENT_ROOT`) and (`amcr`.`SITE_PREFIX` = `cont`.`SITE_PREFIX`)))) where ((`menu`.`MENU_TYPE` = 'content') and (`menu`.`MENU_STATUS` = 'public') and (`cont`.`CONTENT_STATUS` = 'publish')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-15 16:02:53
