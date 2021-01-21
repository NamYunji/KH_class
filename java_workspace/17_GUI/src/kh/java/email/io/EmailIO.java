package kh.java.email.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import kh.java.email.model.vo.Email;

/**
 * 직접 파일에 접근하는 성격의 코드를 작성
 *
 */
public class EmailIO {

	//필드로 만들기
	File f = new File("emailList.txt");
	/**
	 * emailList.txt에 Email객체 추가하기
	 * 
	 * @param email
	 */
	public void insertEmail(Email email) {
		//기존정보 읽어오기
		//기존 작성한 내용을 우선 읽어옴
		List<Email> list = loadEmaillist();
		//그 리스트에 방금 입력받은 이메일 추가
		list.add(email);
		System.out.println("list@io.insertEmail = " + list);
		
		try(
			ObjectOutputStream oos = 
				new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
		){
			//기존정보 읽어오기, 새로 싹 다시써줌
			for (Email e : list) {
				oos.writeObject(e);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//파일에 접근해서 읽어오기
	public List<Email> loadEmaillist() {
		List<Email> list = new ArrayList<>();
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			
		 while(true) {
			 Email email = (Email)ois.readObject();
			 list.add(email);
		 }
		}catch (FileNotFoundException e) {
			//최초등록시 파일 생성
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
		catch (EOFException e) {
			//처리코드 없음
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
}

