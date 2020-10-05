/**
 * 
 */
package egovframework.com.asapro.code.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeCategorySearch;
import egovframework.com.asapro.code.service.CodeMapper;
import egovframework.com.asapro.code.service.CodeSearch;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 코드관리 서비스 구현
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
@Service
public class CodeServiceImpl extends EgovAbstractServiceImpl implements CodeService{
	
	@Autowired
	private CodeMapper codeMapper;
	
	//분류목록조회
	@Override
	public List<CodeCategory> getCodeCategoryList(CodeCategorySearch codeCategorySearch) {
		return codeMapper.selectCodeCategoryList(codeCategorySearch);
	}

	//분류목록 개수
	@Override
	public int getCodeCategoryListTotal(CodeCategorySearch codeCategorySearch) {
		return codeMapper.selectCodeCategoryListTotal(codeCategorySearch);
	}

	//분류조회
	@Override
	public CodeCategory getCodeCategory(String catId) {
		return codeMapper.selectCodeCategory(catId);
	}
	
	//분류 추가
	@Override
	public String insertCodeCategory(CodeCategory codeCategory) {
		codeCategory.setCatRegDate(new Date());
		codeMapper.insertCodeCategory(codeCategory);
		return codeCategory.getCatId();
	}

	//분류수정
	@Override
	public int updateCodeCategory(CodeCategory codeCategory) {
		return codeMapper.updateCodeCategory(codeCategory);
	}

	//분류삭제
	@Override
	public int deleteCodeCategories(String[] catIds) {
		int result = 0;
		CodeCategory codeCategory = null;
		for(String catId : catIds){
			codeCategory = codeMapper.selectCodeCategory(catId);
			//카테고리 삭제되면 상세코드도 같이 삭제한다.
			this.deleteCodes(codeCategory.getCodeList());
			result += codeMapper.deleteCodeCategory(catId); 
		}
		return result;
	}

	//코드목록조회 - 주로 관리용, 사용쪽에서 호출 해도 무관(코드 카테고리까지 함께 jopin으로 쿼리하기 때문에 약간 무거움)
	@Override
	public List<Code> getCodeList(CodeSearch codeSearch) {
		return codeMapper.selectCodeList(codeSearch);
	}
	
	//코드목록조회 - 카테고리 아이디만 받아서 처리
	@Override
	public List<Code> getCodeList(String catId) {
		CodeSearch codeSearch = new CodeSearch(); 
		codeSearch.setCatId(catId);
		return codeMapper.selectCodeListForUse(codeSearch);
	}
	
	//코드목록 조회 - 카테고리 아이디랑 정렬조건 받아서 처리
	@Override
	public List<Code> getCodeList(String catId, String sortOrder, String sortDirection) {
		CodeSearch codeSearch = new CodeSearch(); 
		codeSearch.setCatId(catId);
		codeSearch.setSortOrder(sortOrder);
		codeSearch.setSortDirection(sortDirection);
		return codeMapper.selectCodeListForUse(codeSearch);
	}
	

	//코드목록개수
	@Override
	public int getCodeListTotal(CodeSearch codeSearch) {
		return codeMapper.selectCodeListTotal(codeSearch);
	}

	//코드추가
	@Override
	public String insertCode(Code code) {
		code.setCodeRegDate(new Date());
		codeMapper.insertCode(code);
		return code.getCodeId();
	}

	//코드조회 - codeId & catId 의 composite key 라서 String key 대신 Code 인스턴스를 사용함.
	@Override
	public Code getCode(Code code) {
		return codeMapper.selectCode(code);
	}

	//코드 수정
	@Override
	public int updateCode(Code code) {
		return codeMapper.updateCode(code);
	}

	//코드 삭제
	//@Override
	//public int deleteCode(Code code) {
	//	return codeMapper.deleteCode(code);
	//}
	
	//여러 코드 삭제 codeId & catId 의 composite key 라서 String key 대신 Code 인스턴스를 사용함.
	@Override
	public int deleteCodes(List<Code> codeList){
		int result = 0;
		if(codeList != null){
			int size = codeList.size();
			Code code = null;
			for(int i = 0; i<size; i++){
				code = codeList.get(i);
				if( StringUtils.isNotBlank(code.getCodeId()) && StringUtils.isNotBlank(code.getCodeCategory().getCatId()) ){
					//join 해서 가져오다보니 codeId가 null인 경우가...있음..-_-..젠장.
					result += codeMapper.deleteCode(code);
				}
			}
		}
		return result;
	}

	//바로위의 오버로딩된 메서드를 사용하는 코드삭제 메서드
	@Override
	public int deleteCodes(String[] codeIds) {
		List<Code> codes = new ArrayList<Code>();
		String[] temp = null;
		Code code = null;
		for(String item : codeIds){
			temp = item.split("@");
			if(temp.length != 2){
				continue;
			}
			code = new Code();
			code.setCodeCategory(new CodeCategory());
			code.getCodeCategory().setCatId(temp[0]);
			code.setCodeId(temp[1]);
			codes.add(code);
		}
		return this.deleteCodes(codes);
	}

	//상세코드 정렬순서를 변경한다.
	@Override
	public int updateCodeOrder(List<Code> codes) {
		int update = 0;
		for (Code code : codes) {
			update += codeMapper.updateCodeOrder(code);
		}
		return update;
	}

}