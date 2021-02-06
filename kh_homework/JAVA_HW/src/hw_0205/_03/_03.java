package hw_0205._03;

/*
 * 다음
배열의 내용을 실행 결과와 같이 출력 되도록 프로그램을 작성 하시오
int[][] arr 2 = {
{20, 30, 10},
{50, 40, 60},
{80, 80, 90} }

90 80 80 60 40 50 10 30 20
 */
public class _03 {
	public static void main(String[] args) {
		int[][] arr2 = { { 20, 30, 10 }, { 50, 40, 60 }, { 80, 80, 90 } };

		for (int i = arr2.length - 1; i >= 0; i--) {
			for (int j = arr2.length - 1; j >= 0; j--) {
				System.out.print(arr2[i][j] + " ");
			}
		}
	}
}
