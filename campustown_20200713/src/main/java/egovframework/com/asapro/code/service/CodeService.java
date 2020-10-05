/**
 * 
 */
package egovframework.com.asapro.code.service;

import java.util.List;

/**
 * 코드관리 서비스
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
public interface CodeService {
	
	/**
	 * 코드 분류 목록을 조회한다.
	 * @param codeCategorySearch
	 * @return 코드 분류 목록
	 */
	public List<CodeCategory> getCodeCategoryList(CodeCategorySearch codeCategorySearch);
	
	/**
	 * 코드 분류 목록 토탈을 조회한다.
	 * @param codeCategorySearch
	 * @return 코드 분류목록 토탈
	 */
	public int getCodeCategoryListTotal(CodeCategorySearch codeCategorySearch);
	
	/**
	 * 코드 분류를 조회한다.
	 * @param catId
	 * @return 코드 분류
	 */
	public CodeCategory getCodeCategory(String catId);
	
	/**
	 * 코드 분류를 추가한다.
	 * @param codeCategory
	 * @return PK
	 */
	public String insertCodeCategory(CodeCategory codeCategory);
	
	/**
	 * 코드 분류를 수정한다.
	 * @param codeCategory
	 * @return 수정 결과
	 */
	public int updateCodeCategory(CodeCategory codeCategory);
	
	/**
	 * 분류코드를 삭제한다.
	 * @param catIds
	 * @return 삭제 결과
	 */
	public int deleteCodeCategories(String[] catIds);
	
	/*
	 * 코드 분류를 삭제한다.
	 * @param catId
	 * @return 삭제 결과
	 */
	//public int deleteCodeCategory(String catId);
	
	/**
	 * 상세 코드 목록을 조회한다.
	 * <pre>
	 * - 주로 관리목적으로 사용하는 메서드
	 * - 사용쪽에서 호출해도 무관
	 * - 단, 코드분류정보를 join으로 함꼐 쿼리하므로 약간 더 무거움
	 * </pre>
	 * @param codeSearch
	 * @return 상세 코드
	 */
	public List<Code> getCodeList(CodeSearch codeSearch);
	
	/**
	 * 상세코드 목록 토탈을 조회한다.
	 * @param codeSearch
	 * @return 상세코드 목록 토탈
	 */
	public int getCodeListTotal(CodeSearch codeSearch);
	
	/**
	 * 상세 코드 목록을 조회한다. 
	 * <pre>
	 * - 코드사용시 호출하는 메서드
	 * - 기본 정렬은 정렬순서(codeOrder) ASC
	 * - 다른 정렬을 사용해서 조회하고 싶으면 같은 이름의 오버로딩 메소드 사용
	 * </pre>
	 * @param catId
	 * @return 상세 코드 목록
	 */
	public List<Code> getCodeList(String catId);
	
	/**
	 * 상세 코드 목록을 조회한다. 
	 * <pre>
	 * - 코드사용시 호출하는 메서드 
	 * - 기본 정렬은 정렬순서(codeOrder) ASC
	 * - 정렬 옵션이 추가된 오버로딩 메소드
	 * - 코드아이디로 정렬할때 가장 뒤로 빼고 싶은 코드는 코드아이디가 _로 시작하도록 생성
	 * </pre>
	 * @param catId
	 * @param sortOrder
	 * @param sortDirection
	 * @return 상세 코드 목록
	 */
	public List<Code> getCodeList(String catId, String sortOrder, String sortDirection);
	
	/**
	 * 상세 코드를 추가한다.
	 * @param code
	 * @return PK
	 */
	public String insertCode(Code code);
	
	/**
	 * 상세코드를 조회한다.
	 * @param code
	 * @return 상세 코드
	 */
	public Code getCode(Code code);
	
	/**
	 * 상세코드를 수정한다.
	 * @param code
	 * @return 수정 결과
	 */
	public int updateCode(Code code);
	
	/*
	 * 상세코드를 삭제한다.
	 * @param code
	 * @return 삭제 결과
	 */
	//public int deleteCode(Code code);
	
	/**
	 * 상세코드를 삭제한다.
	 * @param codeList
	 * @return 삭제 결과
	 */
	public int deleteCodes(List<Code> codeList);
	
	/**
	 * <pre>
	 * 상세코드를 삭제한다. 
	 * - 배열의 원소문자열은 '@'를 구분자로 코드아이디@카테고리 아이디의 형태로 구성되어 있음.
	 * </pre>
	 * @param codeIds
	 * @return 삭제 결과
	 */
	public int deleteCodes(String[] codeIds);

	/**
	 * <pre>
	 * 상세코드 정렬 순서를 변경한다.
	 * - 배열의 원소문자열은 '@'를 구분자로 코드아이디@카테고리 아이디의 형태로 구성되어 있음.
	 * </pre>
	 * @param codes
	 * @return 수정결과
	 */
	public int updateCodeOrder(List<Code> codes);

}
