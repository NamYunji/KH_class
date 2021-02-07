package hw_0205._07;

/*
abstract Class : Mobile

필드 mobileName, batterySize, osType

기본생성자
파라미터 생성자

abstract 메소드 operate 
사용을 통해 배터리 감소 분단위로 입력

abstract 메소드 charge
충전을 통한 배터리 증가 분단위로 입력
 */
public abstract class Mobile {
	private String mobileName;
	private int batterySize;
	private String osType;
	
	public Mobile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Mobile(String mobileName, int batterySize, String osType) {
		super();
		this.mobileName = mobileName;
		this.batterySize = batterySize;
		this.osType = osType;
	}

	public abstract int operate(int time);
	
	public abstract int charge(int time);

	public String toString(String mobileName, int batterySize, String osType) {
		// TODO Auto-generated method stub
		return null;
	}
}
