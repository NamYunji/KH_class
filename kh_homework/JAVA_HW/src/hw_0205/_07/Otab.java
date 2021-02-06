package hw_0205._07;
/*
 * Otab
기본생성자, 파라미터 생성자
abstract method 구현 (operate, charge)
operate
사용을 통해 배터리 감소 구현
1분 사용 시 밧데리 12 감소
잔여배터리 리턴
charge
충전을 통한 배터리 증가 구현
1분 충전 시 밧데리 8 증가
잔여배터리 리턴
getter / setter
 */
public class Otab extends Mobile{
	
	private int batterySize;

	public Otab() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Otab(String mobileName, int batterySize, String osType) {
		super(mobileName, batterySize, osType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int operate(int time) {
		return this.getBatterySize() - (time * 12);
	}

	@Override
	public int charge(int time) {
		return this.getBatterySize() + (time * 8);
	}

	public int getBatterySize() {
		return batterySize;
	}

	public void setBatterySize(int batterySize) {
		this.batterySize = batterySize;
	}
	
}
