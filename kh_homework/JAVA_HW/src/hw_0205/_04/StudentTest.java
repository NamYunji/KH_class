package hw_0205._04;

public class StudentTest {
	public static void main(String[] args) {
		Student[] stdt = new Student[3];
		stdt[0] = new Student("홍길동", 15, 171, 81);
		stdt[1] = new Student("홍길동", 13, 183, 72);
		stdt[2] = new Student("임걱정", 16, 175, 65);

		System.out.println("name 나이  신장  몸무게");
		for (int i = 0; i < stdt.length; i++) {
			stdt[i].printInfo();
		}

//나이, 신장, 몸무게 평균
		double age = 0;
		for (int i = 0; i < stdt.length; i++) {
			age += stdt[i].getAge();
		}

		double height = 0;
		for (int i = 0; i < stdt.length; i++) {
			height += stdt[i].getHeight();
		}

		double weight = 0;
		for (int i = 0; i < stdt.length; i++) {
			weight += stdt[i].getWeight();
		}

		System.out.printf("나이의 평균 : %.2f%n"
				+ "신장의 평균 : %.2f%n"
				+ "몸무게의 평균 : %.2f ", 
				age / stdt.length, 
				height / stdt.length,
				weight / stdt.length);
	}
}
