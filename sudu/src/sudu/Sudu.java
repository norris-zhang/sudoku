package sudu;

import java.util.Stack;

public class Sudu {
	private static final Stack<SuduMetrix> stack = new Stack<SuduMetrix>();
	public static void main(String[] args) throws Exception {
		SuduMetrix metrix = new SuduMetrix();
		metrix.load();
		
		
		
		
		int[][] minPossibility = metrix.getMinPossibility();
		if (minPossibility == null) {
			System.out.println("error question.");
		}
		metrix.setCurrentPossibilities(minPossibility);
		metrix.setPossibilityPosition(0);
		metrix.set(minPossibility[0][0], minPossibility[0][1], minPossibility[1][0]);
		stack.push(metrix);
		SuduMetrix tryThis = tryThis();
		System.out.println(tryThis);
		
		
		/*SuduMetrix tryThis = tryThis(metrix);
		System.out.println(tryThis);*/
		
	}
	
	private static SuduMetrix tryThis() throws Exception {
		while (true) {
			stackToTop();
			SuduMetrix peek = stack.peek();
			if (peek.isCorrect()) {
				return peek;
			}
			int nextPos = peek.getPossibilityPosition() + 1;
			int[][] currentPossibilities = peek.getCurrentPossibilities();
			while (nextPos >= currentPossibilities[1].length) {
				if (stack.isEmpty()) {
					return null;
				}
				stack.pop();
				peek = stack.peek();
				nextPos = peek.getPossibilityPosition() + 1;
				currentPossibilities = peek.getCurrentPossibilities();
			}
			//System.out.println("back to " + stack.size());
			peek.set(currentPossibilities[0][0], currentPossibilities[0][1], currentPossibilities[1][nextPos]);
			peek.setPossibilityPosition(nextPos);
		}
	}
	
	private static void stackToTop() throws Exception {
		while (true) {
			SuduMetrix peek = stack.peek();
			int[][] minPossibility = peek.getMinPossibility();
			if (minPossibility == null) {
				//System.out.println("stack to top: " + stack.size());
				break;
			}
			SuduMetrix clone = peek.clone();
			clone.setCurrentPossibilities(minPossibility);
			clone.setPossibilityPosition(0);
			clone.set(minPossibility[0][0], minPossibility[0][1], minPossibility[1][0]);
			stack.push(clone);
		}
		
		
	}

	/*private static SuduMetrix tryThis(SuduMetrix aMetrix) throws Exception {
		int[][] minPossibility = aMetrix.getMinPossibility();
		if (minPossibility == null) {
			return null;
		}
		int[] position = minPossibility[0];
		int[] possibilities = minPossibility[1];
		for (int i = 0; i < possibilities.length; i++) {
			SuduMetrix clone = aMetrix.clone();
			clone.set(position[0], position[1], i);
			SuduMetrix tryThis = tryThis(clone);
			if (tryThis != null && tryThis.isCorrect()) {
				return tryThis;
			}
		}
		return null;
	}*/
}
