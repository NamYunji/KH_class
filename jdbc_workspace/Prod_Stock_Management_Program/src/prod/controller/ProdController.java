package prod.controller;

import java.util.List;

import prod.model.service.ProdService;
import prod.model.vo.IO;
import prod.model.vo.Stock;

public class ProdController {
	
	private ProdService prodService = new ProdService();
	public List<Stock> sltAll() {
		return prodService.sltAll();
	}
	public Stock sltOne(String productId) {
		return prodService.sltOneId(productId); 
	}
	public List<Stock> sltByName(String productName) {
		return prodService.sltByName(productName);
	}
	public int insertProd(Stock stock) {
		return prodService.insertProd(stock);
	}
	public int updateProdInfo(Stock s) {
		return prodService.updateProdInfo(s);
	}
	public int deleteProd(String productId) {
		return prodService.deleteProd(productId);
	}
	public List<IO> sltIOInfo() {
		return prodService.sltIOInfo();
	}

	
}
