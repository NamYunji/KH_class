package hw_0205._07;
/*
 * Ltab
기본생성자, 파라미터 생성자

abstract method 구현 (operate, charge)
operate
사용을통해 배터리 감소 구현 ,
1분 사용 시 밧데리 10 감소
잔여배터리 리턴

charge
충전을 통한 배터리 증가 구현
1분 충전 시 밧데리 10 증가
잔여배터리 리턴

getter / setter
 */
public class Ltab extends Mobile{

	private int batterySize;
	private String mobileName;
	private String osType;
	
	public Ltab() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ltab(String mobileName, int batterySize, String osType) {
		super(mobileName, batterySize, osType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int operate(int time) {
		return this.getBatterySize() - (time * 10);
	}

	@Override
	public int charge(int time) {
		return this.getBatterySize() + (time * 10);
	}

	public int getBatterySize() {
		return batterySize;
	}

	public void setBatterySize(int batterySize) {
		this.batterySize = batterySize;
	}

	@Override
	public String toString(String mobileName, int getBatterySize, String osType) {
		return String.format(mobileName + " " + this.getBatterySize() + " " + osType);
	}








	
	
}
