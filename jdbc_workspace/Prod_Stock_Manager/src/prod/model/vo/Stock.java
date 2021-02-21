package prod.model.vo;

public class Stock {
	
	//필드
	private String productId;
	private String productName;
	private int price;
	private String description;
	private int stock;
	
	
	//기본생성자
	public Stock() {
		super();
	}

	
	//parameter 생성자
	public Stock(String productId, String productName, int price, String description, int stock) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.stock = stock;
	}

	
	//getter & setter
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	
	//toString
	@Override
	public String toString() {
		return "StockVo [productId=" + productId + ", productName=" + productName + ", price=" + price
				+ ", description=" + description + ", stock=" + stock + "]";
	}
	
}
