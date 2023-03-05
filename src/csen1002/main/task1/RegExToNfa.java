package csen1002.main.task1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Write your info here
 *
 * @name Mohamed Shetewi
 * @id 46-13908
 * @labNumber 01
 */

public class RegExToNfa {
	private String nfa;
	/**
	 * Constructs an NFA corresponding to a regular expression based on Thompson's
	 * construction
	 *
	 * @param input The alphabet and the regular expression in postfix notation for
	 *              which the NFA is to be constructed
	 */
	public RegExToNfa(String input) {
		String[] inputSplit = input.split("#");
		ThompsonController controller = new ThompsonController(inputSplit[0], inputSplit[1]);
		this.nfa = controller.constructNFA();
	}

	/**
	 * @return Returns a formatted string representation of the NFA. The string
	 *         representation follows the one in the task description
	 */
	@Override
	public String toString() {
		return this.nfa;
	}

}

class ThompsonController {

	private String regex;
	private String alphabet;

	public ThompsonController(String alphabet, String regex) {
		this.alphabet = alphabet;
		this.regex = regex;
	}

	public String constructNFA() {
		Stack<NFA> nfaStack = new Stack<>();
		Integer lastState = -1;


		for (int i = 0; i < regex.length(); i++) {
			char curChar = regex.charAt(i);
			switch (curChar) {
				case '*' :
					NFA nfaStar = nfaStack.pop().kleeneStar();
					nfaStack.push(nfaStar);
					lastState = nfaStar.getLastState();
					break;
				case '|' :
					NFA nfa2 = nfaStack.pop();
					NFA nfa1 = nfaStack.pop();
					NFA unitedNFA = nfa1.union(nfa2);
					nfaStack.push(unitedNFA);
					lastState = unitedNFA.getLastState();
					break;
				case '.' :
					NFA nfaB = nfaStack.pop();
					NFA nfaA = nfaStack.pop();
					NFA concatenatedNFA = nfaA.concatenate(nfaB);
					nfaStack.push(concatenatedNFA);
					lastState = concatenatedNFA.getLastState();
					break;
				default :
					Integer state1 = ++lastState;
					Integer state2 = ++lastState;
					ArrayList<Integer> states = new ArrayList<>();
					states.add(state1);
					states.add(state2);
					ArrayList<Transition> transitions = new ArrayList<>();
					transitions.add(new Transition(state1, state2, curChar));
					nfaStack.push(new NFA(states, transitions, state1, state2));
					break;
			}
		}
		NFA finalNFA = nfaStack.pop();
		StringBuilder statesString = new StringBuilder(finalNFA.statesToString());
		String transitionsString = finalNFA.transitionsToString();
		String startState = String.valueOf(finalNFA.getStart());

		return statesString
				.append("#")
				.append(alphabet)
				.append("#")
				.append(transitionsString)
				.append("#")
				.append(startState)
				.append("#")
				.append(finalNFA.getGoal()).toString();
	}

	class NFA {
		private final ArrayList<Integer> states;
		private final ArrayList<Transition> transitionsList;
		private Integer start, goal;

		public NFA(ArrayList<Integer> states, ArrayList<Transition> transitionsList, Integer start, Integer goal) {
			this.states = states;
			this.transitionsList = transitionsList;
			this.start = start;
			this.goal = goal;
		}

		public NFA(ArrayList<Integer> states, ArrayList<Transition> transitionsList) {
			this.states = states;
			this.transitionsList = transitionsList;
		}

		public NFA union(NFA a) {
			int lastState = Math.max(this.getLastState(), a.getLastState());
			int newStart = lastState + 1;
			int newGoal = lastState + 2;

			Transition newStartToOldStart1 = new Transition(newStart, this.start, 'e');
			Transition newStartToOldStart2 = new Transition(newStart, a.start, 'e');
			Transition oldGoalToNewGoal1 = new Transition(this.goal, newGoal, 'e');
			Transition oldGoalToNewGoal2 = new Transition(a.goal, newGoal, 'e');

			NFA unionNFA = this.clone();
			unionNFA.getStates().addAll(a.getStates());
			unionNFA.getStates().add(newStart);
			unionNFA.getStates().add(newGoal);

			unionNFA.setStart(newStart);
			unionNFA.setGoal(newGoal);

			for (Transition t : a.getTransitionsList())
				unionNFA.getTransitionsList().add(t);
			unionNFA.getTransitionsList().add(newStartToOldStart1);
			unionNFA.getTransitionsList().add(newStartToOldStart2);
			unionNFA.getTransitionsList().add(oldGoalToNewGoal1);
			unionNFA.getTransitionsList().add(oldGoalToNewGoal2);


			return unionNFA;
		}

		public NFA kleeneStar() {
			int newStart = this.getLastState() + 1;
			int newGoal = this.getLastState() + 2;
			Transition oldGoalToOldStart = new Transition(this.goal, this.start, 'e');
			Transition newStartToOldStart = new Transition(newStart, this.start, 'e');
			Transition newStartToNewGoal = new Transition(newStart, newGoal, 'e');
			Transition oldGoalToNewGaol = new Transition(this.goal, newGoal, 'e');
			NFA nfaClone = this.clone();
			nfaClone.getStates().add(newStart);
			nfaClone.getStates().add(newGoal);
			nfaClone.getTransitionsList().add(oldGoalToOldStart);
			nfaClone.getTransitionsList().add(newStartToOldStart);
			nfaClone.getTransitionsList().add(newStartToNewGoal);
			nfaClone.getTransitionsList().add(oldGoalToNewGaol);
			nfaClone.setStart(newStart);
			nfaClone.setGoal(newGoal);
			Collections.sort(nfaClone.transitionsList);
			return nfaClone;
		}

		public NFA concatenate(NFA b) {
			NFA newNFA = b.clone();
			newNFA.setStart(b.start);
			newNFA.setGoal(b.goal);

			// Adding transitions
			ArrayList<Transition> transitionsFromStartStateB = new ArrayList<>();
			newNFA.getTransitionsList().forEach(transition -> {
				if (newNFA.isStartState(transition.from))
					transitionsFromStartStateB.add(transition);
			});
			transitionsFromStartStateB.forEach(transition -> newNFA.getTransitionsList().
					add(new Transition(this.goal,
							transition.to,
							transition.transitionSymbol)));
			newNFA.getTransitionsList().removeIf(transition -> newNFA.isStartState(transition.from));

			// Removing the start state for NFA b
			newNFA.getStates().remove(newNFA.start);

			//Removing transition from the old start state
			newNFA.getTransitionsList().removeIf(obj -> newNFA.isStartState(obj.from));
			newNFA.getStates().addAll(this.getStates());
			this.getTransitionsList().forEach(transition -> newNFA.getTransitionsList().add(transition.clone()));
			newNFA.setStart(this.start);
			newNFA.setGoal(b.goal);
			return newNFA;
		}

		public boolean isStartState(Integer s) {
			return Objects.equals(s, this.start);
		}

		public Integer getLastState() {
			return Collections.max(this.states);
		}

		public NFA clone() {
			ArrayList<Transition> newTransitions = new ArrayList<>();
			for (Transition t : this.transitionsList)
				newTransitions.add(t.clone());
			ArrayList<Integer> newStates = new ArrayList<>(this.states);

			return new NFA(newStates, newTransitions);
		}

		public ArrayList<Integer> getStates() {
			return states;
		}

		public ArrayList<Transition> getTransitionsList() {
			return transitionsList;
		}


		public void setStart(Integer start) {
			this.start = start;
		}

		public void setGoal(Integer goal) {
			this.goal = goal;
		}

		public Integer getStart() {
			return start;
		}

		public Integer getGoal() {
			return goal;
		}

		public String statesToString() {
			Collections.sort(this.getStates());
			return this.getStates().stream().map(String::valueOf).collect(Collectors.joining(";"));
		}

		public String transitionsToString() {
			Collections.sort(this.transitionsList);
			return this.transitionsList.stream().map(String::valueOf).collect(Collectors.joining(";"));
		}

	}

	class Transition implements Comparable<Transition> {
		int from, to;
		char transitionSymbol;

		public Transition(int from, int to, char transitionSymbol) {
			this.from = from;
			this.to = to;
			this.transitionSymbol = transitionSymbol;
		}

		@Override
		public int compareTo(Transition o) {
			if (from == o.from)
				return transitionSymbol == o.transitionSymbol ? to - o.to : transitionSymbol - o.transitionSymbol;
			return from - o.from;
		}

		public Transition clone() {
			return new Transition(from, to, transitionSymbol);
		}

		public String toString() {
			return from + "," + transitionSymbol + "," + to;
		}

	}
}

