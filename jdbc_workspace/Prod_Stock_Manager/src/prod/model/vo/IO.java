package prod.model.vo;

import java.sql.Date;

public class IO {
	
	//필드
	private int ioNo;
	private String productId;
	private Date ioDate;
	private int amount;
	private String status;
	
	//기본생성자
	public IO() {
		super();
	}
	
	//parameter 생성자
	public IO(int ioNo, String productId, Date ioDate, int amount, String status) {
		super();
		this.ioNo = ioNo;
		this.productId = productId;
		this.ioDate = ioDate;
		this.amount = amount;
		this.status = status;
	}

	//getter & setter
	public int getIoNo() {
		return ioNo;
	}

	public void setIoNo(int ioNo) {
		this.ioNo = ioNo;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Date getIoDate() {
		return ioDate;
	}

	public void setIoDate(Date ioDate) {
		this.ioDate = ioDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	//toString
	@Override
	public String toString() {
		return "IOVo [ioNo=" + ioNo + ", productId=" + productId + ", ioDate=" + ioDate + ", amount=" + amount
				+ ", status=" + status + "]";
	}
	
}
