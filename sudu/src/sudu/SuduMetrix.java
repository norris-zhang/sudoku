package sudu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SuduMetrix implements Cloneable {
	private int[][] currentPossibilities = null;
	private int possibilityPosition = 0;
	private int[][] metrix = new int[9][9];
	public SuduMetrix() {
		
	}
	public void load() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			is = SuduMetrix.class.getResourceAsStream("input.txt");
			isr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(isr);
			int i = 0;
			while (true) {
				String line = br.readLine();
				if (line == null || line.trim().length() == 0) {
					break;
				}
				String[] split = line.split(" ");
				for (int j = 0; j < split.length && j < metrix[i].length; j++) {
					metrix[i][j] = Integer.parseInt(split[j]);
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int[][] getCurrentPossibilities() {
		return currentPossibilities;
	}
	public void setCurrentPossibilities(int[][] currentPossibilities) {
		this.currentPossibilities = currentPossibilities;
	}
	public int getPossibilityPosition() {
		return possibilityPosition;
	}
	public void setPossibilityPosition(int possibilityPosition) {
		this.possibilityPosition = possibilityPosition;
	}
	@Override
	public SuduMetrix clone() throws CloneNotSupportedException {
		SuduMetrix clone = (SuduMetrix)super.clone();
		clone.currentPossibilities = null;
		clone.possibilityPosition = 0;
		int[][] temp = clone.metrix;
		clone.metrix = new int[9][9];
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[i].length; j++) {
				clone.metrix[i][j] = temp[i][j];
			}
		}
		return clone;
	}
	public void set(int row, int col, int num) {
		metrix[row][col] = num;
	}
	public boolean isCorrect() {
		for (int i = 0; i < metrix.length; i++) {
			for (int j = 0; j < metrix[i].length; j++) {
				if (metrix[i][j] == 0) {
					return false;
				}
				if (!isPossible(i, j, metrix[i][j])) {
					return false;
				}
			}
		}
		return true;
	}
	public int[][] getMinPossibility() {
		int[] position = new int[2];
		List<Integer> possibleNumbers = null;
		for (int i = 0; i < metrix.length; i++) {
			for (int j = 0; j < metrix[i].length; j++) {
				if (metrix[i][j] != 0) {
					continue;
				}
				List<Integer> tempPossibleNumbers = new ArrayList<Integer>();
				for (int num = 1; num <= 9; num++) {
					if (isPossible(i, j, num)) {
						tempPossibleNumbers.add(num);
					}
				}
				if (possibleNumbers == null || tempPossibleNumbers.size() < possibleNumbers.size()) {
					possibleNumbers = tempPossibleNumbers;
					position[0] = i;
					position[1] = j;
				}
			}
		}
		if (possibleNumbers == null || possibleNumbers.size() == 0) {
			return null;
		}
		int[] possibleNumbersArray = new int[possibleNumbers.size()];
		for (int i = 0; i < possibleNumbers.size(); i++) {
			possibleNumbersArray[i] = possibleNumbers.get(i);
		}
		return new int[][]{position, possibleNumbersArray};
	}
	private boolean isPossible(int row, int col, int num) {
		for (int i = 0; i < metrix[row].length; i++) {
			if (i != col && metrix[row][i] == num) {
				return false;
			}
		}
		for (int i = 0; i < metrix.length; i++) {
			if (i != row && metrix[i][col] == num) {
				return false;
			}
		}
		int fromRow = row - row % 3;
		int toRow = fromRow + 2;
		int fromCol = col - col % 3;
		int toCol = fromCol + 2;
		for (int i = fromRow; i <= toRow; i++) {
			for (int j = fromCol; j <= toCol; j++) {
				if (i != row && j != col && metrix[i][j] == num) {
					return false;
				}
			}
		}
		return true;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int[] row : metrix) {
			for (int col : row) {
				sb.append(col);
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		SuduMetrix sss = new SuduMetrix();
		sss.load();
		System.out.println(sss.isPossible(1, 5, 1));
		
		int[][] minPossibility = sss.getMinPossibility();
		System.out.println(minPossibility);
		System.out.println("position: ["+minPossibility[0][0]+","+minPossibility[0][1]+"]");
		System.out.println("possibilities: [");
		for (int i = 0; i < minPossibility[1].length; i++) {
			System.out.print(minPossibility[1][i] + ", ");
		}
		System.out.println("]");
	}
}
