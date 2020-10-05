/**
 * 
 */
package egovframework.com.asapro.code.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 코드관리 SQL매퍼
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
@Mapper
public interface CodeMapper {
	
	/**
	 * 코드 분류를 추가한다.
	 * @param codeCategory
	 * @return 추가 결과
	 */
	//@CacheEvict(value="codeCache", allEntries=true)
	public int insertCodeCategory(CodeCategory codeCategory);
	
	/**
	 * 코드 분류 목록을 조회한다.
	 * @param codeCategorySearch
	 * @return 코드 분류 목록
	 */
	//@Cacheable(value="codeCache")
	public List<CodeCategory> selectCodeCategoryList(CodeCategorySearch codeCategorySearch);
	
	/**
	 * 코드 분류 목록 개수를 조회한다.
	 * @param codeCategorySearch
	 * @return 코드 분류 목록 개수
	 */
	//@Cacheable(value="codeCache")
	public int selectCodeCategoryListTotal(CodeCategorySearch codeCategorySearch);
	
	/**
	 * 코드 카테고리를 조회한다.
	 * @param catId
	 * @return 코드 카테고리
	 */
	//@Cacheable(value="codeCache")
	public CodeCategory selectCodeCategory(String catId);
	
	/**
	 * 코드 카테고리를 수정한다.
	 * @param codeCategory
	 * @return 수정 결과
	 */
	//@CacheEvict(value="codeCache", allEntries=true)
	public int updateCodeCategory(CodeCategory codeCategory);
	
	/**
	 * 코드 카테고리를 삭제한다.
	 * @param catId
	 * @return
	 */
	//@CacheEvict(value="codeCache", allEntries=true)
	public int deleteCodeCategory(String catId);
	
	/**
	 * 상세코드를 추가한다.
	 * @param coed
	 * @return 추가 결과
	 */
	//@CacheEvict(value="codeCache", allEntries=true)
	public int insertCode(Code code);
	
	/**
	 * 상세코드 목록을 조회한다.
	 * @param codeSearch
	 * @return 상세코드 목록
	 */
	//@Cacheable(value="codeCache")
	public List<Code> selectCodeList(CodeSearch codeSearch);
	
	/**
	 * 상세코드 목록 개수를 조회한다.
	 * @param codeSearch
	 * @return 상세코드 목록 개수
	 */
	//@Cacheable(value="codeCache")
	public int selectCodeListTotal(CodeSearch codeSearch);
	
	/**
	 * 상세코드 목록을 조회한다.
	 * @param codeSearch
	 * @return 상세코드 목록
	 */
	//@Cacheable(value="codeCache")
	public List<Code> selectCodeListForUse(CodeSearch codeSearch);
	
	/**
	 * 상세코드를 조회한다.
	 * @param code
	 * @return 상세 코드
	 */
	//@Cacheable(value="codeCache")
	public Code selectCode(Code code);
	
	/**
	 * 상세코드를 수정한다.
	 * @param code
	 * @return 수정 결과
	 */
	//@CacheEvict(value="codeCache", allEntries=true)
	public int updateCode(Code code);
	
	/**
	 * 상세코드를 삭제한다.
	 * @param code
	 * @return 삭제 결과
	 */
	//@CacheEvict(value="codeCache", allEntries=true)
	public int deleteCode(Code code);

	/**
	 * 상세코드 정렬순서를 변경한다.
	 * @param code
	 * @return 수정결과
	 */
	//@CacheEvict(value="codeCache", allEntries=true)
	public int updateCodeOrder(Code code);

}
