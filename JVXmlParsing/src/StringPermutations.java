public class StringPermutations {

	static String[] data = { "I1", "I2", "I3", "I4" };

	public StringPermutations() {
		// TODO Auto-generated constructor stub

	}

	void permute(int start, int last) {

		if (start == last) {
			for (String x : data)
				System.out.print(x + " ");
			System.out.println();
		} else {

			for (int i = start; i <= last; i++) {

				swap(start, i);
				permute(start + 1, last);
				// System.out.println(tempArray);
				swap(start,i);

			}
		}
	}

	void swap(int x, int y) {
		String tt = data[x];
		data[x] = data[y];
		data[y] = tt;
	}

	public static void main(String[] args) {

		StringPermutations mp = new StringPermutations();
		mp.permute(0, data.length - 1);

	}
}
