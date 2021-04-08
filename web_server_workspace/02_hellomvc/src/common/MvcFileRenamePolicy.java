package common;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MvcFileRenamePolicy implements FileRenamePolicy {

	/**
	 * 벚꽃.jpg를 업로드 -> 20210406090909_123.jpg로 파일명 변경
	 */
	@Override
	public File rename(File f) {
		// 새로 생성된 이름의 file객체를 리턴해야 함
		File newFile = null;
		// do가 먼저 실행됨
		do {
			// 새 파일명 생성
			// 년월일_시분초밀리초_
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
			// 무조건 세자리수 (ex. 1 -> 001 , 10 -> 010)
			DecimalFormat df = new DecimalFormat("000");
			// 1. 확장자 구하기
			// getName() 파일명 리턴
			String oldName = f.getName();
			// ext - 확장자 - ""로 초기화
			String ext = "";
			// 마지막 .의 위치를 찾기
			int dot = oldName.lastIndexOf(".");
			// dot > -1 : 확장자가 존재함
			// substring(int beginIndex) - 문자열에서 beginIndex위치에서 끝까지의 문자열 리턴
			// .부터 끝까지 리턴 -> ex. .jpg
			if(dot > -1) ext = oldName.substring(dot); 
			
			// 2. newName 구하기
			// 현재 시각을 dateForm에 맞게 설정
			// 난수를 구해서 df의 form으로 설정 (0 ~ 999)
			// 확장자
			String newName = sdf.format(new Date())
							+ df.format(Math.random() * 999)
							+ ext;
			// 3. 파일 객체로 변환
			// 정확한 경로의 파일명을 만들어야 함
			// f.getParent() 원래 파일의 부모경로 리턴 (upload/board)
			// 해당 경로에 newName이라는 이름을 가진 파일 객체를 만들어라
			// 아직 실제 파일이 만들어진 건 아님
			// why? File은 실존할수도 아닐수도 있는 파일을 바라보는 객체
			newFile = new File(f.getParent(), newName);
		// createNewFile(newFile)이 true면 새 파일이 실제로 만들어짐
		} while(!createNewFile(newFile));
		// createNewFile이 실패한다면 이 반복문을 계속해라
		// 새로운 파일생성이 실패하면 do while문이 계속되는 구조
		return newFile;
	}

	/**
	 * f가 실제 존재하지 않으면, 파일 생성 후 true리턴
	 * f가 이미 존재하면, 파일을 생성하지 않고 IOException을 던짐 -> catch절 - return false
	 */
	private boolean createNewFile(File f) {
		try {
			return f.createNewFile();
		} catch (IOException ignored) {
			return false;
		}
	}
}
