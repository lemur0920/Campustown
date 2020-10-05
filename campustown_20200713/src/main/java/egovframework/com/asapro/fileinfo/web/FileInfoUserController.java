/**
 * 
 */
package egovframework.com.asapro.fileinfo.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.board.service.BoardService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.Member;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.site.service.Site;
//import egovframework.com.asapro.site.service.SiteService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.cmm.service.EgovProperties;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 파일정보 컨트롤러
 * @author yckim
 * @since 2018. 7. 3.
 *
 */
@Controller
public class FileInfoUserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileInfoUserController.class);
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private BoardService boardService;
	
//	@Autowired
//	private SiteService siteService;
/*	
	*//**
	 * 파일을 다운로드 한다. 
	 * <pre>
	 * method 로 구분해서 처리함
	 * ${APP_PATH}/file/download/{fileId}
	 * ${APP_PATH}/file/image/{fileId}
	 * ${APP_PATH}/file/thumbnail/{fileId}
	 * = ${APP_PATH}/file/thumb/{fileId}
	 * ${APP_PATH}/file/fly/{fileId}?w={넓이}&amp;h={높이} - 원본으로 다이나믹 이미지 생성 => 메모리 많이 소모
	 * ${APP_PATH}/file/thumbfly/{fileId}?w={넓이}&amp;h={높이} - 썸네일로 다이나믹 이미지 생성 => 메모리 적게 소모
	 * </pre>
	 * @param fileInfo
	 * @throws IOException
	 *//*
	@SuppressWarnings("rawtypes")
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/file/{method}/{fileId}", method=RequestMethod.GET)
	public ResponseEntity download(HttpServletRequest request, HttpServletResponse response, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin, @CurrentUser UserMember currentUser
			, @PathVariable(value="method") String method, @PathVariable(value="fileId") Integer fileId, @ModelAttribute FileInfo fileInfo) throws IOException{
		
		Member currentMember = new Member();
		
		//회원정보 공통 객체에 셋팅
		if( currentAdmin != null ){
			currentMember.setMemberFromAdmin(currentAdmin);
		}else if(currentUser != null){
			currentMember.setMemberFromUser(currentUser);
		}else{
			currentMember.setMemberDefault(request);
		}
		
		// ===== 시작 : 웹취약점 보완 =====
		if( !ArrayUtils.contains(new String[]{"download", "image", "thumbnail", "thumb", "fly", "thumbfly", "stream"}, method) ){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		// ===== 끝 : 웹취약점 보완 =====
				
		// 디렉토리형 서브사이트에서 요청하는 경우 못찾아서.. 
		if( currentSite == null ){
//			currentSite = siteService.detectCurrentSite(request);
		}
		
		FileInfo fileIdSearch = new FileInfo();
		fileIdSearch.setSitePrefix(currentSite.getSitePrefix());
		fileIdSearch.setFileId(fileId);
		
		FileInfo fileInfo = fileInfoService.getFileInfo(fileIdSearch);
		fileInfo.setSitePrefix(currentSite.getSitePrefix());
		
		//게시판 첨부파일이면 다운로드 권한 체크한다.
		if( "download".equalsIgnoreCase(method) ){
			if( "board".equals(fileInfo.getFileModule()) && "article".equals(fileInfo.getFileModuleSub()) ){
				BoardConfig bcIdSearch = new BoardConfig();
				bcIdSearch.setBcId(fileInfo.getFileModuleId());
				bcIdSearch.setSitePrefix(currentSite.getSitePrefix());
				BoardConfig boardConfig = boardService.getBoardConfig(bcIdSearch);
				if( "ROLE_GUEST".equals(currentMember.getMemberRole().getRoleCode()) ){
					if( !boardConfig.isBcAllowGuestDownload() ){
						//throw new AsaproNoCapabilityException("다운로드 권한이 없습니다.");
						return new ResponseEntity(HttpStatus.UNAUTHORIZED);
					}
				} else {
					if( boardService.isBoardManager(boardConfig, currentMember) ){
						if( !boardConfig.isBcAllowMemberDownload() ){
							//throw new AsaproNoCapabilityException("다운로드 권한이 없습니다.");
							return new ResponseEntity(HttpStatus.UNAUTHORIZED);
						}
					}
				}
			}
		}
		
		//EgovProperties.getProperty("Globals.fileStorePath")
		//Globals.fileStorePath 의 값이 있을경우 지정한 경로에 저장되게 하고 값이없을경우 디폴트로 설정된 컨텍스트내 폴더에 저장된다.
		String cwd = EgovProperties.getProperty("Globals.fileStorePath");
		if(StringUtils.isBlank(cwd)){
			cwd = StringUtils.removeEnd(AsaproUtils.getWebRoot(request).replaceAll("\\\\", "/"), "/");
		}
		
		 ============================================================ 
		// - 이미지 썸네일을 on the fly 로 생성해서 response 에 출력한다.
		// - 퍼블용으로 별도의 사이즈가 필요할때 사용, 중앙을 기준으로 주어진 사이즈에 맞춰서 crop 한다
		// - !!! 썸네일을 생성해놓고 서버에서 호스팅하는게 아니라 요청할때마다 만들어서 주기때문에 
		// - 요청시마다 서버자원을 사용하게 되므로 과다하게 쓰지말자...(어느정도가 과다인지는 나도 모르겠다...) 
		 ------------------------------------------------------------ 
		if( "fly".equalsIgnoreCase(method) || "thumbfly".equalsIgnoreCase(method) ){
			
			// ===== 시작 : 웹취약점 보완 =====
			if( StringUtils.isNotBlank(request.getParameter("w")) && !StringUtils.isNumeric(request.getParameter("w")) ){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			if( StringUtils.isNotBlank(request.getParameter("h")) && !StringUtils.isNumeric(request.getParameter("h")) ){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			// ===== 끝 : 웹취약점 보완 =====
			
			//jsp에서만
			//out.clear(); //out--> jsp자체 객체
			//out = pageContext.pushBody(); //out--> jsp자체 객체
			
			BufferedImage originalImage = null;
			if( "fly".equalsIgnoreCase(method) ){
				originalImage = ImageIO.read( new File(cwd + fileInfo.getFilePath()) );
			}
			//원본 이미지가 용량이 너무 큰 경우 메모리 넘쳐서 다운됨 - 그걸 방지하기 위해 thumbfly 로 들어오면 썸네일 이미지로 작업하도록 개선
			else if( "thumbfly".equalsIgnoreCase(method) ){
				originalImage = ImageIO.read( new File(cwd + fileInfo.getFileThumbnailPath()) );
			}
			OutputStream os = response.getOutputStream();
				
			response.setHeader("Content-Type", fileInfo.getFileMimeType());

			//default w/h
			int w = 200;
			int h = 160;
			if( StringUtils.isBlank(request.getParameter("w")) || StringUtils.isBlank(request.getParameter("h")) ){
				LOGGER.warn("[asapro] FileInfoUserController - w(idth) and h(eight) parameters are required for generating thumbnail on the fly.");
			} else {
				w = Integer.parseInt(request.getParameter("w"));
				h = Integer.parseInt(request.getParameter("h"));
			}
			
			Thumbnails.of(originalImage).crop(Positions.CENTER).size(w, h).outputFormat(fileInfo.getFileExt()).toOutputStream(os);
			
			os.flush();
			os.close();
			
			return new ResponseEntity(HttpStatus.OK);
		}
		 ============================================================ 
		
		 ============================================================ 
		//- 동영상, 오디오 재생일 경우는 포워딩 시켜서 WAS 대신 웹서버가 처리하도록 한다. 
		//- 동영상 주소에 ${APP_PATH}/file/download/xxx 로 넣으면 버퍼(디폴트 4mb?)보다 큰 파일은 Software caused connection abort: socket write error 에러가 나는데 정황한 원인은 모르겠음...
		 ------------------------------------------------------------ 
		if( "stream".equalsIgnoreCase(method) ){
			response.sendRedirect(fileInfo.getFilePath());
			
			try {
				//redirect 대신 forward로 변경
				request.getRequestDispatcher(fileInfo.getFilePath()).forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
			
			return new ResponseEntity(HttpStatus.PARTIAL_CONTENT);
		}
		 ============================================================ 
		//method가 image 이면 원본이미지 파일 thumbnail 이면 썸네일 이미지 파일
		String filePath = cwd + fileInfo.getFilePath();
		if( "thumbnail".equalsIgnoreCase(method) || "thumb".equalsIgnoreCase(method) ){
			filePath = cwd + fileInfo.getFileThumbnailPath();
			 ============================================================ 
			// 모바일이면 큰 이미지 대신 썸네일을 반환하도록 하자
			 ------------------------------------------------------------ 
			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
			if( userAgent.getOperatingSystem().getDeviceType().equals(DeviceType.MOBILE) ){
				filePath = cwd + fileInfo.getFileThumbnailPath();
			}
			 ============================================================ 
		}
		
		LOGGER.info("[ASAPRO] FileInfoUserController File path : {}", filePath);
		
		//name to download
		String fileNameToSave = "";
		//original file name
		String fileOriginalName = fileInfo.getFileOriginalName();
		
		//저장할 파일명 변환
		String serverInfo = request.getSession().getServletContext().getServerInfo();
		if(serverInfo.contains("JEUS") || serverInfo.contains("Jeus") ){
			fileNameToSave = AsaproUtils.getKoreanFileNameToSaveJeus(request, fileOriginalName);
		} else {
			fileNameToSave = AsaproUtils.getKoreanFileNameToSave(request, fileOriginalName);
		}
		
		if( "download".equalsIgnoreCase(method) ){
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileNameToSave + "\";");
		} else {
			response.setContentType(fileInfo.getFileMimeType());
		}
		
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		IOUtils.copy(fis, response.getOutputStream());
		if(fis != null){
			fis.close();
		}
		
		response.flushBuffer();
		
		//파일다운로드 카운트 증가
		if( "download".equalsIgnoreCase(method) ){
			fileInfoService.updateFileInfoDownloadCount(fileInfo);
		}
		
		//return new ResponseEntity(HttpStatus.OK);
		return null;
	}
*/	
	
	/**
	 * 파일을 다운로드 한다. 
	 * <pre>
	 * method 로 구분해서 처리함
	 * ${APP_PATH}/file/download/uu/{fileUUID}
	 * ${APP_PATH}/file/image/uu/{fileUUID}
	 * ${APP_PATH}/file/thumbnail/uu/{fileUUID}
	 * = ${APP_PATH}/file/thumb/uu/{fileUUID}
	 * ${APP_PATH}/file/fly/uu/{fileUUID}?w={넓이}&amp;h={높이} - 원본으로 다이나믹 이미지 생성 => 메모리 많이 소모
	 * ${APP_PATH}/file/thumbfly/uu/{fileUUID}?w={넓이}&amp;h={높이} - 썸네일로 다이나믹 이미지 생성 => 메모리 적게 소모
	 * </pre>
	 * @param fileInfo
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/file/{method}/uu/{fileUUID}", method=RequestMethod.GET)
	public ResponseEntity download(HttpServletRequest request, HttpServletResponse response, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin, @CurrentUser UserMember currentUser
			, @PathVariable(value="method") String method, @PathVariable(value="fileUUID") String fileUUID/*, @ModelAttribute FileInfo fileInfo*/) throws IOException{
		
		Member currentMember = new Member();
		
		//회원정보 공통 객체에 셋팅
		if( currentAdmin != null ){
			currentMember.setMemberFromAdmin(currentAdmin);
		}else if(currentUser != null){
			currentMember.setMemberFromUser(currentUser);
		}else{
			currentMember.setMemberDefault(request);
		}
		
		// ===== 시작 : 웹취약점 보완 =====
		if( !ArrayUtils.contains(new String[]{"download", "image", "thumbnail", "thumb", "fly", "thumbfly", "stream", "streamweb"}, method) ){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		// ===== 끝 : 웹취약점 보완 =====
		
		// 디렉토리형 서브사이트에서 요청하는 경우 못찾아서.. 
		if( currentSite == null ){
//			currentSite = siteService.detectCurrentSite(request);
		}
		
		FileInfo fileIdSearch = new FileInfo();
		fileIdSearch.setSitePrefix(currentSite.getSitePrefix());
		fileIdSearch.setFileUUID(fileUUID);

		FileInfo fileInfo = fileInfoService.getFileInfo(fileIdSearch);
		fileInfo.setSitePrefix(currentSite.getSitePrefix());
		
		//게시판 첨부파일이면 다운로드 권한 체크한다.
		if( "download".equalsIgnoreCase(method) ){
			if( "board".equals(fileInfo.getFileModule()) && "article".equals(fileInfo.getFileModuleSub()) ){
				BoardConfig bcIdSearch = new BoardConfig();
				bcIdSearch.setBcId(fileInfo.getFileModuleId());
				bcIdSearch.setSitePrefix(currentSite.getSitePrefix());
				BoardConfig boardConfig = boardService.getBoardConfig(bcIdSearch);
				if( "ROLE_GUEST".equals(currentMember.getMemberRole().getRoleCode()) ){
					if( !boardConfig.isBcAllowGuestDownload() ){
						//throw new AsaproNoCapabilityException("다운로드 권한이 없습니다.");
						return new ResponseEntity(HttpStatus.UNAUTHORIZED);
					}
				} else {
					if( boardService.isBoardManager(boardConfig, currentMember) ){
						if( !boardConfig.isBcAllowMemberDownload() ){
							//throw new AsaproNoCapabilityException("다운로드 권한이 없습니다.");
							return new ResponseEntity(HttpStatus.UNAUTHORIZED);
						}
					}
				}
			}
		}
		
		//EgovProperties.getProperty("Globals.fileStorePath")
		//Globals.fileStorePath 의 값이 있을경우 지정한 경로에 저장되게 하고 값이없을경우 디폴트로 설정된 컨텍스트내 폴더에 저장된다.
		String cwd = EgovProperties.getProperty("Globals.fileStorePath");
		if(StringUtils.isBlank(cwd)){
			cwd = StringUtils.removeEnd(AsaproUtils.getWebRoot(request).replaceAll("\\\\", "/"), "/");
		}
		
		/* ============================================================ */
		// - 이미지 썸네일을 on the fly 로 생성해서 response 에 출력한다.
		// - 퍼블용으로 별도의 사이즈가 필요할때 사용, 중앙을 기준으로 주어진 사이즈에 맞춰서 crop 한다
		// - !!! 썸네일을 생성해놓고 서버에서 호스팅하는게 아니라 요청할때마다 만들어서 주기때문에 
		// - 요청시마다 서버자원을 사용하게 되므로 과다하게 쓰지말자...(어느정도가 과다인지는 나도 모르겠다...) 
		/* ------------------------------------------------------------ */
		if( "fly".equalsIgnoreCase(method) || "thumbfly".equalsIgnoreCase(method) ){
			
			// ===== 시작 : 웹취약점 보완 =====
			if( StringUtils.isNotBlank(request.getParameter("w")) && !StringUtils.isNumeric(request.getParameter("w")) ){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			if( StringUtils.isNotBlank(request.getParameter("h")) && !StringUtils.isNumeric(request.getParameter("h")) ){
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			// ===== 끝 : 웹취약점 보완 =====
			
			//jsp에서만
			//out.clear(); //out--> jsp자체 객체
			//out = pageContext.pushBody(); //out--> jsp자체 객체
			
			BufferedImage originalImage = null;
			if( "fly".equalsIgnoreCase(method) ){
				originalImage = ImageIO.read( new File(cwd + fileInfo.getFilePath()) );
			}
			//원본 이미지가 용량이 너무 큰 경우 메모리 넘쳐서 다운됨 - 그걸 방지하기 위해 thumbfly 로 들어오면 썸네일 이미지로 작업하도록 개선
			else if( "thumbfly".equalsIgnoreCase(method) ){
				originalImage = ImageIO.read( new File(cwd + fileInfo.getFileThumbnailPath()) );
			}
			OutputStream os = response.getOutputStream();
			
			response.setHeader("Content-Type", fileInfo.getFileMimeType());
			
			//default w/h
			int w = 200;
			int h = 160;
			if( StringUtils.isBlank(request.getParameter("w")) || StringUtils.isBlank(request.getParameter("h")) ){
				LOGGER.warn("[asapro] FileInfoUserController - w(idth) and h(eight) parameters are required for generating thumbnail on the fly.");
			} else {
				w = Integer.parseInt(request.getParameter("w"));
				h = Integer.parseInt(request.getParameter("h"));
			}
			
			Thumbnails.of(originalImage).crop(Positions.CENTER).size(w, h).outputFormat(fileInfo.getFileExt()).toOutputStream(os);
			
			os.flush();
			os.close();
			
			return new ResponseEntity(HttpStatus.OK);
		}
		/* ============================================================ */
		
		/* ============================================================ */
		//- 동영상, 오디오 재생일 경우는 포워딩 시켜서 WAS 대신 웹서버가 처리하도록 한다. 
		//- 동영상 주소에 ${APP_PATH}/file/download/xxx 로 넣으면 버퍼(디폴트 4mb?)보다 큰 파일은 Software caused connection abort: socket write error 에러가 나는데 정황한 원인은 모르겠음...
		/* ------------------------------------------------------------ */
		if( "streamweb".equalsIgnoreCase(method) ){
			response.sendRedirect(fileInfo.getFilePath());
			/*
			try {
				//redirect 대신 forward로 변경
				request.getRequestDispatcher(fileInfo.getFilePath()).forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
			 */
			return new ResponseEntity(HttpStatus.PARTIAL_CONTENT);
		}
		if( "stream".equalsIgnoreCase(method) ){
			String filePath = cwd + fileInfo.getFilePath();

	        //progressbar 에서 특정 위치를 클릭하거나 해서 임의 위치의 내용을 요청할 수 있으므로
	        //파일의 임의의 위치에서 읽어오기 위해 RandomAccessFile 클래스를 사용한다.
	        //해당 파일이 없을 경우 예외 발생
	        
	        File file = new File(filePath);
	        RandomAccessFile randomFile = new RandomAccessFile(file, "r");

	        long rangeStart = 0; //요청 범위의 시작 위치
	        long rangeEnd = 0;  //요청 범위의 끝 위치
	        boolean isPart=false; //부분 요청일 경우 true, 전체 요청의 경우 false

	        //randomFile 을 클로즈 하기 위하여 try~finally 사용
	        try{
	            //동영상 파일 크기
	            long movieSize = randomFile.length();
	            //스트림 요청 범위, request의 헤더에서 range를 읽는다.
	            String range = request.getHeader("range");
	            LOGGER.debug("range: {}", range);

	            //브라우저에 따라 range 형식이 다른데, 기본 형식은 "bytes={start}-{end}" 형식이다.
	            //range가 null이거나, reqStart가  0이고 end가 없을 경우 전체 요청이다.
	            //요청 범위를 구한다.
	            if(range!=null) {
	                //처리의 편의를 위해 요청 range에 end 값이 없을 경우 넣어줌
	                if(range.endsWith("-")){
	                    range = range+(movieSize - 1);
	                }
	                int idxm = range.trim().indexOf("-");                           //"-" 위치
	                rangeStart = Long.parseLong(range.substring(6,idxm));
	                rangeEnd = Long.parseLong(range.substring(idxm+1));
	                if(rangeStart > 0){
	                    isPart = true;
	                }
	           }else{   //range가 null인 경우 동영상 전체 크기로 초기값을 넣어줌. 0부터 시작하므로 -1
	                rangeStart = 0;
	                rangeEnd = movieSize - 1;
	           }

	           //전송 파일 크기
	           long partSize = rangeEnd - rangeStart + 1;
	           LOGGER.debug("accepted range: {}",rangeStart+"-"+rangeEnd+"/"+partSize+" isPart:"+isPart);

	           //전송시작
	           response.reset();

	           //전체 요청일 경우 200, 부분 요청일 경우 206을 반환상태 코드로 지정
	           response.setStatus(isPart ? 206 : 200);

	           //mime type 지정
	           response.setContentType(fileInfo.getFileMimeType());

	           //전송 내용을 헤드에 넣어준다. 마지막에 파일 전체 크기를 넣는다.
	           response.setHeader("Content-Range", "bytes "+rangeStart+"-"+rangeEnd+"/"+movieSize);
	           response.setHeader("Accept-Ranges", "bytes");
	           response.setHeader("Content-Length", ""+partSize);
	   
	           OutputStream out = response.getOutputStream();
	           //동영상 파일의 전송시작 위치 지정
	           randomFile.seek(rangeStart);
	   
	           //파일 전송...  java io는 1회 전송 byte수가 int로 지정됨
	           //동영상 파일의 경우 int형으로는 처리 안되는 크기의 파일이 있으므로
	           //8kb로 잘라서 파일의 크기가 크더라도 문제가 되지 않도록 구현
	           int bufferSize = 8 * 1024;
	           byte[] buf = new byte[bufferSize];
	           do{
	               int block = partSize > bufferSize ? bufferSize : (int)partSize;
	               int len = randomFile.read(buf, 0, block);
	               out.write(buf, 0, len);
	               partSize -= block;
	           }while(partSize > 0);
	           //LOGGER.debug("sent " + movieName + " " + rangeStart + "-" + rangeEnd);
	       }catch(IOException e){
	           //전송 중에 브라우저를 닫거나, 화면을 전환한 경우 종료해야 하므로 전송취소.
	           // progressBar를 클릭한 경우에는 클릭한 위치값으로 재요청이 들어오므로 전송 취소.
	    	   LOGGER.info("[ASAPRO] FileInfoUserController File path : 파일전송 취소");
	       }finally{
	           randomFile.close();
	       }
			
			return null;
		}
		
		/* ============================================================ */
		//method가 image 이면 원본이미지 파일 thumbnail 이면 썸네일 이미지 파일
		String filePath = cwd + fileInfo.getFilePath();
		if( "thumbnail".equalsIgnoreCase(method) || "thumb".equalsIgnoreCase(method) ){
			filePath = cwd + fileInfo.getFileThumbnailPath();
			/* ============================================================ */
			// 모바일이면 큰 이미지 대신 썸네일을 반환하도록 하자
			/* ------------------------------------------------------------ */
			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
			if( userAgent.getOperatingSystem().getDeviceType().equals(DeviceType.MOBILE) ){
				filePath = cwd + fileInfo.getFileThumbnailPath();
			}
			/* ============================================================ */
		}
		
		LOGGER.info("[ASAPRO] FileInfoUserController File path : {}", filePath);
		
		//name to download
		String fileNameToSave = "";
		//original file name
		String fileOriginalName = fileInfo.getFileOriginalName();
		
		//저장할 파일명 변환
		String serverInfo = request.getSession().getServletContext().getServerInfo();
		if(serverInfo.contains("JEUS") || serverInfo.contains("Jeus") ){
			fileNameToSave = AsaproUtils.getKoreanFileNameToSaveJeus(request, fileOriginalName);
		} else {
			fileNameToSave = AsaproUtils.getKoreanFileNameToSave(request, fileOriginalName);
		}

		if( "download".equalsIgnoreCase(method) ){
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileNameToSave + "\";");
		} else {
			response.setContentType(fileInfo.getFileMimeType());
		}
		
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		IOUtils.copy(fis, response.getOutputStream());
		if(fis != null){
			fis.close();
		}
		
		response.flushBuffer();
		
		//파일다운로드 카운트 증가
		if( "download".equalsIgnoreCase(method) ){
			fileInfoService.updateFileInfoDownloadCount(fileInfo);
		}
		
		//return new ResponseEntity(HttpStatus.OK);
		return null;
	} 
	
}
