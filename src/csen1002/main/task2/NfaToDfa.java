package csen1002.main.task2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Write your info here
 *
 * @name Mohamed Shetewi
 * @id 46-13908
 * @labNumber 21
 */

public class NfaToDfa {

    /**
     * Constructs a DFA corresponding to an NFA
     *
     * @param input A formatted string representation of the NFA for which an
     * equivalent DFA is to be constructed. The string representation
     * follows the one in the task description
     */

    private HashMap<Integer, HashSet<Integer>> epsilonClosure;
    private NFAParser nfaParser;
    private DFA dfa;

    public static void main(String[] args) {
        NfaToDfa nfaToDfa = new NfaToDfa("0;1;2;3;4;5;6;7;8;9;10#a;b#0,e,1;1,b,2;2,e,3;3,e,4;3,e,9;" +
                "4,e,5;4,e,7;5,a,6;6,e,4;6,e,9;7,b,8;8,e,4;8,e,9;9,a,10#0#10");
        System.out.println(nfaToDfa.toString());
    }

    public NfaToDfa(String input) {
        nfaParser = new NFAParser(input);
        epsilonClosure = new HashMap<>();
        nfaParser.getAdjList().forEach((key, val) -> {
            HashSet<Integer> arrayList = new HashSet<>();
            arrayList.add(key);
            epsilonClosure.put(key, arrayList);
        });
        setEpsilonClosure();
        toDFA();
    }

    /**
     * @return Returns a formatted string representation of the DFA. The string
     * representation follows the one in the task description
     */
    @Override
    public String toString() {
        StringBuilder dfaStates = new StringBuilder(dfa.statesToString());
        StringBuilder alphabet = new StringBuilder(dfa.alphabetString());
        StringBuilder transition = new StringBuilder(dfa.transitionsToString());
        StringBuilder startState = new StringBuilder(dfa.startState.toString());
        StringBuilder goalStates = new StringBuilder(dfa.goalStatesString());
        return dfaStates
                .append("#")
                .append(alphabet)
                .append("#")
                .append(transition)
                .append("#")
                .append(startState)
                .append("#")
                .append(goalStates).toString();
    }

    private void setEpsilonClosure() {
        boolean isEpsilonClosureChanged;
        do {
            isEpsilonClosureChanged = false;
            for (Integer state : epsilonClosure.keySet()) {
                HashSet<Integer> closuresOfState = epsilonClosure.get(state);
                int closureSize = closuresOfState.size();
                List<Integer> tempList = new ArrayList<>();
                for (Integer closure : closuresOfState) {
                    List<Integer> epsilonTransitionsOfClosures = nfaParser.getAdjList()
                            .get(closure)
                            .stream().filter(item -> item.transition == 'e')
                            .map(item -> item.to)
                            .toList();
                    tempList.addAll(epsilonTransitionsOfClosures);
                }
                closuresOfState.addAll(tempList);
                if (closureSize != closuresOfState.size())
                    isEpsilonClosureChanged = true;
            }
        } while (isEpsilonClosureChanged);
    }

    private void toDFA() {
        Integer startState = nfaParser.getStart();
        DFAState dfaStartState = new DFAState(epsilonClosure.get(startState));
        dfa = new DFA(new HashSet<>(), new ArrayList<>(), nfaParser.getAlphabet());
        dfa.states.add(dfaStartState);
        dfa.startState = dfaStartState;
        helper(dfa);
    }

    private void helper(DFA dfa) {
        Stack<DFAState> dfaStatesStack = new Stack<>();
        dfaStatesStack.add(dfa.startState);
        HashSet<DFAState> processedStates = new HashSet<>();

        while (!dfaStatesStack.isEmpty()) {
            DFAState curState = dfaStatesStack.pop();
            if (processedStates.contains(curState))
                continue;
            processedStates.add(curState);
            for (Character element : dfa.alphabet) {
                DFAState newDFAState = new DFAState(new HashSet<>());
                for (Integer state : curState.state) {
                    List<Integer> nextStates = nfaParser.getAdjList()
                            .get(state)
                            .stream()
                            .filter(transition -> transition.transition == element)
                            .map(item -> item.to)
                            .toList();
                    newDFAState.state.addAll(nextStates);
                }
                if (newDFAState.state.isEmpty()) {
                    dfa.addDeadTransition(curState, element);
                } else {
                    ArrayList<Integer> closures = new ArrayList<>();
                    newDFAState.state.forEach(item -> closures.addAll(epsilonClosure.get(item)));
                    newDFAState.state.addAll(closures);
                    dfa.addTransition(curState, newDFAState, element);
                    dfa.states.add(newDFAState);
                    dfaStatesStack.add(newDFAState);
                }
            }
        }

        for (DFAState dfaState : dfa.states) {
            for (Integer state : dfaState.state)
                if (nfaParser.getGoals().contains(state))
                    dfa.goalStates.add(dfaState);
        }
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

    public void addDeadState() {
        DFAState dead = new DFAState(new HashSet<>());
        dead.addState(-1);

        if (states.contains(dead))
            return;
        states.add(dead);
        alphabet.forEach(item -> dfaTransitions.add(new DFATransition(dead, dead, item)));
    }

    public void addTransition(DFAState from, DFAState to, char transition) {
        dfaTransitions.add(new DFATransition(from, to, transition));
    }

    public void addDeadTransition(DFAState from, char transition) {
        addDeadState();
        DFAState deadState = new DFAState(new HashSet<>());
        deadState.state.add(-1);
        dfaTransitions.add(new DFATransition(from, deadState, transition));
    }

    public String statesToString() {
        return states.stream()
                .sorted()
                .map(DFAState::toString)
                .collect(Collectors.joining(";"));
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

    public void addState(Integer a) {
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

class NFAParser {
    private Integer start;
    private ArrayList<Character> alphabet;
    private ArrayList<Integer> goals;
    private HashMap<Integer, HashSet<Transition>> adjList;


    public NFAParser(String nfa) {
        alphabet = new ArrayList<>();
        goals = new ArrayList<>();
        adjList = new HashMap<>();
        String[] nfaTokens = nfa.split("#");
        initAdjList(nfaTokens[0]);
        initAlphabet(nfaTokens[1]);
        initGraph(nfaTokens[2]);
        start = Integer.parseInt(nfaTokens[3]);
        initGoals(nfaTokens[4]);
    }

    private void initGoals(String goalStates) {
        Arrays.stream(goalStates.split(";"))
                .map(Integer::parseInt)
                .forEach(item -> this.goals.add(item));
    }

    private void initAdjList(String states) {
        Arrays.stream(states.split(";"))
                .map(Integer::parseInt)
                .forEach(item -> adjList.put(item, new HashSet<>()));
    }

    private void initAlphabet(String alphabet) {
        Arrays.stream(alphabet.split(";"))
                .map(item -> item.charAt(0))
                .forEach(item -> this.alphabet.add(item));
    }

    private void initGraph(String transitions) {
        Arrays.stream(transitions.split(";"))
                .map(item -> item.split(","))
                .filter(item -> item.length == 3)
                .map(item -> new Transition(Integer.parseInt(item[0]), Integer.parseInt(item[2]), item[1].charAt(0)))
                .forEach(item -> {
                    HashSet<Transition> transitionArrayList = adjList.get(item.from);
                    transitionArrayList.add(item);
                    adjList.put(item.from, transitionArrayList);
                });
    }

    public Integer getStart() {
        return start;
    }

    public ArrayList<Character> getAlphabet() {
        return alphabet;
    }

    public ArrayList<Integer> getGoals() {
        return goals;
    }

    public HashMap<Integer, HashSet<Transition>> getAdjList() {
        return adjList;
    }
}

class Transition {
    int from, to;
    char transition;

    public Transition(int from, int to, char transition) {
        this.from = from;
        this.to = to;
        this.transition = transition;
    }

    @Override
    public String toString() {
        return from + "," + transition + "," + to;
    }
}