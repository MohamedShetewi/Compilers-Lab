package csen1002.main.task1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

public class NFATest {
    @Test
    public void testKleeneClosure() {
        ArrayList<Transition> transitions1 = new ArrayList<>();
        transitions1.add(new Transition(4, 5, 'b'));
        ArrayList<Integer> states = new ArrayList<>();
        states.add(4);
        states.add(5);
        NFA nfa = new NFA(states, transitions1, 4, 5);
        nfa = nfa.kleeneStar();
        assertEquals(nfa.getStart(), 6);
        assertEquals(nfa.getGoal(), 7);
        assertEquals("4;5;6;7", nfa.statesToString());
        assertEquals("4,5,b;5,4,e;5,7,e;6,4,e;6,7,e", nfa.transitionsToString());
    }


    @Test
    public void testUnion() {
        ArrayList<Transition> transitions1 = new ArrayList<>();
        transitions1.add(new Transition(4, 5, 'a'));
        ArrayList<Integer> states1 = new ArrayList<>();
        states1.add(4);
        states1.add(5);

        ArrayList<Transition> transitions2 = new ArrayList<>();
        transitions1.add(new Transition(1, 2, 'b'));
        ArrayList<Integer> states2 = new ArrayList<>();
        states2.add(1);
        states2.add(2);

        NFA nfa1 = new NFA(states1, transitions1, 4, 5);
        NFA nfa2 = new NFA(states2, transitions2, 1, 2);

        NFA newNFA = nfa1.union(nfa2);

        assertEquals(6, newNFA.getStart());
        assertEquals(7, newNFA.getGoal());
        assertEquals("1;2;4;5;6;7",newNFA.statesToString());
        assertEquals("1,2,b;2,7,e;4,5,a;5,7,e;6,1,e;6,4,e", newNFA.transitionsToString());
    }

    @Test
    public void testConcatenate() {
        ArrayList<Transition> transitions1 = new ArrayList<>();
        transitions1.add(new Transition(4, 5, 'a'));
        ArrayList<Integer> states1 = new ArrayList<>();
        states1.add(4);
        states1.add(5);

        ArrayList<Transition> transitions2 = new ArrayList<>();
        transitions2.add(new Transition(1, 2, 'b'));
        ArrayList<Integer> states2 = new ArrayList<>();
        states2.add(1);
        states2.add(2);

        NFA nfa1 = new NFA(states1, transitions1, 4, 5);
        NFA nfa2 = new NFA(states2, transitions2, 1, 2);

        NFA newNFA = nfa1.concatenate(nfa2);

        assertEquals(4, newNFA.getStart());
        assertEquals(2, newNFA.getGoal());
        assertEquals("2;4;5",newNFA.statesToString());
        assertEquals("4,5,a;5,2,b", newNFA.transitionsToString());
    }




}
