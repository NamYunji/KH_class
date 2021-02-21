package prod.view;



import java.util.List;
import java.util.Scanner;


import prod.controller.ProdController;
import prod.model.vo.IO;
import prod.model.vo.Stock;


public class ProdMenu {

	ProdController prodController = new ProdController();
	Scanner sc = new Scanner(System.in);

	public void mainMenu() {
		
		String menu = "\n" +
					  "***** 상품재고관리프로그램 *****\n" + 
					  "1. 전체상품조회\n" + 
					  "2. 상품아이디검색\n" + 
					  "3. 상품명검색\n" + 
					  "4. 상품추가\n" + 
					  "5. 상품정보변경\n" + 
					  "6. 상품삭제\n" + 
					  "7. 상품입/출고 메뉴\n" + 
					  "9. 프로그램종료\n";
		
		
		while(true) {
			System.out.println(menu);
			System.out.print("선택 : ");
			String choice = sc.next();
			
			List<Stock> list = null;
			Stock stock = null;
			String productId = null;
			String productName = null;
			int result = 0;
			String msg = null;
			
			switch (choice) {
			
//		1. 전체상품조회
			case "1":
				list = prodController.sltAll();
				displayStockList(list);
				break;				
				
//		2. 상품아이디검색
			case "2":
			productId = inputProdId();
			stock = prodController.sltOne(productId);
			displayStock(stock);				
				break;
				
//		3. 상품명검색
			case "3":
				productName = inputProdName();
				list = prodController.sltByName(productName);
				displayStockList(list);				
				
				break;
//		4. 상품추가
			case "4":
			//1.신규회원정보 입력 -> Member객체
			stock = inputProd();
			System.out.println(">>> 상품정보 확인 : " + stock);
			//2.controller에 회원가입 요청(메소드호출) -> int리턴(처리된 행의 개수)
			result = prodController.insertProd(stock);
			//3.int에 따른 분기처리
			msg = result > 0 ? "상품 추가 성공" : "상품 추가 실패";
			displayMsg(msg);				
				break;
				
//		5. 상품정보변경
			case "5":
			updateProdInfoMenu();
			break;
			
//		6. 상품삭제
			case "6":
			productId = inputProdId();
			result = prodController.deleteProd(productId);
			msg = result > 0 ? "상품 삭제 성공!" : "상품 삭제 실패!";
			displayMsg(msg);				
			break;
			
//		7. 상품입/출고 메뉴
			case "7":
				sltIOMenu();
				break;
				
//		9. 프로그램종료
			case "9":
				System.out.println("정말 종료하시겠습니까? (y/n)");
				choice = sc.next();
				if (choice.equalsIgnoreCase("y")) {
					return;
				}
				break;

			default: System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
				break;
			}
		}
	}
	private void sltIOMenu() {
		
		String menu = "***** 상품입출고메뉴*****\n" + 
				"1. 전체입출고내역조회\n" + 
				"2. 상품입고\n" + 
				"3. 상품출고\n" + 
				"9. 메인메뉴로 돌아가기\n" + 
				"입력 : ";
		System.out.println(menu);
		
		String choice = sc.next();
		List<IO> ioList = null;
		String productId = null;
		int amount = 0;
		IO io = null;
		
		while (true) {
			switch (choice) {
			case "1":
				ioList = prodController.sltIOInfo();
				displayIOList(ioList);				
				break;
			case "2":
				break;
			case "3":
				break;
			case "9":
				return;

			default:
				System.out.println("잘못 입력하셨습니다.");
				continue;
			}	
		}
	}

	private void displayIOList(List<IO> ioList) {
		if(ioList != null && !ioList.isEmpty()) {
			System.out.println("==========================================================================================");
			for(int i = 0; i < ioList.size(); i++)
				System.out.println((i + 1) + " : " + ioList.get(i));
			System.out.println("==========================================================================================");
		}
		//조회된 데이터가 없을 경우
		else {
			System.out.println(">>> 조회된 정보가 없습니다.");
		}
	}

	private void updateProdInfoMenu() {
		String menu = "***** 상품정보변경메뉴 *****\n" + 
				"1.상품명변경\n" + 
				"2.가격변경\n" + 
				"3.설명변경\n" + 
				"9.메인메뉴로 돌아가기\n" +
				"입력 : ";
		
		String productId = inputProdId();
		
		String choice = null;
		while(true){
			//회원정보 출력
			Stock s = prodController.sltOne(productId);
			//조회된 회원정보가 없는 경우, 리턴
			if(s == null) {
				System.out.println("해당 상품이 존재하지 않습니다.");
				return;
			}
			displayProd(s);
			
			System.out.print(menu);
			choice = sc.next();
			
			switch (choice) {
			case "1":
				System.out.print("변경할 상품명 : ");
				s.setProductName(sc.next());
				break;
			case "2":
				System.out.print("변경할 가격 : ");
				s.setPrice(sc.nextInt());
				break;
			case "3":
				System.out.print("변경할 설명 : ");
				s.setDescription(sc.next());
				break;
			case "9":
				return;
			default:
				System.out.println("잘못 입력하셨습니다.");
				continue;
			}
			
			int result = prodController.updateProdInfo(s);
			displayMsg(result > 0 ? "정보 수정 성공!" : "정보 수정 실패!");
		}
	}

	private void displayProd(Stock s) {
		if(s == null)
			System.out.println(">>>> 조회된 상품이 없습니다.");
		else {
			System.out.println("****************************************************************");
			System.out.println(s);
			System.out.println("****************************************************************");
		}
	}

	private void displayMsg(String msg) {
		System.out.println(">>>처리결과 : " + msg);
	}

	private Stock inputProd() {
		System.out.println("새로운 상품정보를 입력하세요.");
		Stock stock = new Stock();
		
		System.out.print("상품 아이디 : ");
		stock.setProductId(sc.next());
		System.out.print("상품명 : ");
		stock.setProductName(sc.next());		
		System.out.print("가격 : ");		
		stock.setPrice(sc.nextInt());
		System.out.print("종목 : ");
		stock.setDescription(sc.next());
		System.out.print("설명 : ");
		stock.setStock(sc.nextInt());
		
		return stock;
	}

	private String inputProdName() {
	System.out.print("조회할 상품명 입력 : ");
	return sc.next();
	}


	private void displayStock(Stock stock) {
		if(stock == null)
			System.out.println(">>>> 조회된 상품이 없습니다.");
		else {
			System.out.println("****************************************************************");
			System.out.println(stock);
			System.out.println("****************************************************************");
		}
	}

	private String inputProdId() {
		System.out.println("아이디 입력 : ");
		return sc.next();
	}

	private void displayStockList(List<Stock> list) {
		if(list != null && !list.isEmpty()) {
			System.out.println("==========================================================================================");
			for(int i = 0; i < list.size(); i++)
				System.out.println((i + 1) + " : " + list.get(i));
			System.out.println("==========================================================================================");
		}
		else {
			System.out.println(">>> 조회된 상품 정보가 없습니다.");
		}
	}

}
