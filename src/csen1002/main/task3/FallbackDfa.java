package csen1002.main.task3;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Write your info here
 *
 * @name Jane Smith
 * @id 46-0234
 * @labNumber 07
 */

public class FallbackDfa {

    public static void main(String[] args) {
        FallbackDfa fallbackDfa = new FallbackDfa("0;1;2;3;4;5;6;7;8;9;10;11;12#h;k;r;x#0,h,12;0,k,0;0,r,4;0,x,5;1,h,12;1,k,6;1,r,2;1,x,8;2,h,5;2,k,5;2,r,1;2,x,8;3,h,10;3,k,0;3,r,7;3,x,9;4,h,1;4,k,7;4,r,5;4,x,4;5,h,2;5,k,8;5,r,2;5,x,12;6,h,4;6,k,8;6,r,3;6,x,4;7,h,8;7,k,12;7,r,5;7,x,10;8,h,12;8,k,12;8,r,1;8,x,6;9,h,9;9,k,0;9,r,2;9,x,10;10,h,7;10,k,12;10,r,10;10,x,10;11,h,0;11,k,11;11,r,10;11,x,1;12,h,10;12,k,8;12,r,11;12,x,4#5#0;2;6;10;11");
        System.out.println(fallbackDfa.run("xxrkxrrkkrkhrxkk"));

    }

    /**
     * Constructs a Fallback DFA
     *
     * @param fdfa A formatted string representation of the Fallback DFA. The string
     * representation follows the one in the task description
     */
    DFA dfa;

    public FallbackDfa(String fdfa) {
        String[] fDfaTokens = fdfa.split("#");

        HashSet<DFAState> states = new HashSet<>(
                Arrays.stream(fDfaTokens[0]
                                .split(";"))
                        .map(item -> new DFAState(Integer.parseInt(item))).toList());

        ArrayList<Character> alphabet = new ArrayList<>(Arrays.stream(fDfaTokens[1].split(";"))
                .map(item -> item.charAt(0))
                .toList());

        ArrayList<DFATransition> transitions = new ArrayList<>(
                Arrays.stream(fDfaTokens[2].split(";"))
                        .map(item -> item.split(","))
                        .filter(item -> item.length == 3)
                        .map(item -> {
                            DFAState state1 = new DFAState(Integer.parseInt(item[0]));
                            DFAState state2 = new DFAState(Integer.parseInt(item[2]));
                            char transition = item[1].charAt(0);
                            return new DFATransition(state1, state2, transition);
                        })
                        .toList());

        DFAState startState = new DFAState(Integer.parseInt(fDfaTokens[3]));
        HashSet<DFAState> goalStates = new HashSet<>(
                Arrays.stream(fDfaTokens[4].split(";"))
                        .map(item -> Integer.parseInt(item))
                        .map(item -> new DFAState(item))
                        .toList()
        );
        this.dfa = new DFA(
                states,
                goalStates,
                transitions,
                startState,
                alphabet
        );
    }

    /**
     * @param input The string to simulate by the FDFA.
     * @return Returns a formatted string representation of the list of tokens. The
     * string representation follows the one in the task description
     */
    public String run(String input) {
        Stack<DFAState> states = new Stack<>();
        ArrayList<String> lexemes = new ArrayList<>();
        int R = 0, L = 0;
        while (R < input.length()) {
            states.add(dfa.startState);
            DFAState curState = states.peek();
            while (L < input.length()) {
                DFAState nextState = dfa.getNextState(curState, input.charAt(L));
                states.add(nextState);
                curState = nextState;
                L++;
            }
            boolean isLexemeFound = false;
            while (L >= R) {
                DFAState dfaState = states.pop();
                if (dfa.isGoalState(dfaState)) {
                    isLexemeFound = true;
                    lexemes.add(input.substring(R, L) + "," + dfaState.state.stream().toList().get(0));
                    R = L;
                    states = new Stack<>();
                    break;
                }
                L--;
            }
            if (!isLexemeFound) {
                lexemes.add(input.substring(R)+","+curState.state.stream().toList().get(0));
                break;
            }
        }

        return String.join(";", lexemes);
    }
}


class DFA {
    HashSet<DFAState> states, goalStates;
    ArrayList<DFATransition> dfaTransitions;
    DFAState startState;
    ArrayList<Character> alphabet;

    public DFA(HashSet<DFAState> states, ArrayList<DFATransition> dfaTransitions, ArrayList<Character> alphabet) {
        this.states = states;
        this.dfaTransitions = dfaTransitions;
        this.alphabet = alphabet;
        this.goalStates = new HashSet<>();
    }

    public DFA(HashSet<DFAState> states, HashSet<DFAState> goalStates, ArrayList<DFATransition> dfaTransitions, DFAState startState, ArrayList<Character> alphabet) {
        this.states = states;
        this.goalStates = goalStates;
        this.dfaTransitions = dfaTransitions;
        this.startState = startState;
        this.alphabet = alphabet;
    }

    public void addTransition(DFAState from, DFAState to, char transition) {
        dfaTransitions.add(new DFATransition(from, to, transition));
    }

    public String statesToString() {
        return states.stream()
                .sorted()
                .map(DFAState::toString)
                .collect(Collectors.joining(";"));
    }


    public DFAState getNextState(DFAState curState, char val) {
        for (DFATransition transition : dfaTransitions)
            if (transition.from.equals(curState) && transition.transition == val)
                return transition.to;
        return null;
    }

    public boolean isGoalState(DFAState state) {
        return goalStates.contains(state);
    }

    public String alphabetString() {
        return alphabet.stream()
                .sorted()
                .map(item -> "" + item)
                .collect(Collectors.joining(";"));
    }

    public String transitionsToString() {
        return dfaTransitions.stream()
                .sorted()
                .map(DFATransition::toString)
                .collect(Collectors.joining(";"));
    }

    public String goalStatesString() {
        return goalStates.stream()
                .sorted()
                .map(DFAState::toString)
                .collect(Collectors.joining(";"));
    }
}

class DFAState implements Comparable<DFAState> {
    HashSet<Integer> state;

    public DFAState(HashSet<Integer> state) {
        this.state = state;
    }

    public DFAState(Integer a) {
        this.state = new HashSet<>();
        state.add(a);
    }

    @Override
    public int compareTo(DFAState dfaState) {
        List<Integer> thisStateList = new ArrayList<>(this.state.stream().toList());
        List<Integer> dfaStateList = new ArrayList<>(dfaState.state.stream().toList());
        Collections.sort(thisStateList);
        Collections.sort(dfaStateList);

        int i = 0, j = 0;

        while (i < thisStateList.size() && j < dfaStateList.size()) {
            Integer a = thisStateList.get(i);
            Integer b = dfaStateList.get(j);
            if (Objects.equals(a, b)) {
                i++;
                j++;
            } else {
                return a.compareTo(b);
            }
        }
        if (thisStateList.size() < dfaStateList.size())
            return -1;
        else if (thisStateList.size() > dfaStateList.size())
            return 1;
        return 0;
    }

    public String toString() {
        return state.stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining("/"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DFAState dfaState = (DFAState) o;
        return Objects.equals(state, dfaState.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}

class DFATransition implements Comparable<DFATransition> {
    DFAState from, to;
    char transition;

    public DFATransition(DFAState from, DFAState to, char transition) {
        this.from = from;
        this.to = to;
        this.transition = transition;
    }

    public String toString() {
        return from.toString() + "," + transition + "," + to.toString();
    }

    @Override
    public int compareTo(DFATransition o) {
        if (from == o.from)
            return transition == o.transition ? to.compareTo(o.to) : transition - o.transition;
        return from.compareTo(o.from);
    }
}

