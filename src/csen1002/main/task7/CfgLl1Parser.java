package csen1002.main.task7;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Write your info here
 *
 * @name Mohamed Shetewi
 * @id 46-13908
 * @labNumber 21
 */

public class CfgLl1Parser {


    public static void main(String[] args) {
        String in = "S;T#a;c;i#S/iST,e;T/cS,a#S/i,e;T/c,a#S/$ca;T/$ca";
        CfgLl1Parser p = new CfgLl1Parser(in);
        System.out.println(p.parseTable);
        System.out.println(p.parse("iia"));
    }

    CFG cfg;
    ParseTable parseTable;
    HashMap<String, ArrayList<String>> firstMap, followMap;

    /**
     * Constructs a Context Free Grammar
     *
     * @param input A formatted string representation of the CFG, the First sets of
     *              each right-hand side, and the Follow sets of each variable. The
     *              string representation follows the one in the task description
     */
    public CfgLl1Parser(String input) {
        String[] cfgTokens = input.split("#");
        ArrayList<String> variables = new ArrayList<>(
                Arrays.stream(cfgTokens[0].split(";")).toList());
        ArrayList<String> alphabet = new ArrayList<>(
                Arrays.stream(cfgTokens[1].split(";")).toList()
        );

        String[] rulesSplit = cfgTokens[2].split(";");
        ArrayList<Rule> rulesList = new ArrayList<>();
        Arrays.stream(rulesSplit).forEach(item -> {
            String[] itemTokens = item.split("/");
            String var = itemTokens[0];
            String[] patterns = itemTokens[1].split(",");
            for (String pattern : patterns)
                rulesList.add(new Rule(var, pattern));
        });

        cfg = new CFG(alphabet, variables, rulesList);
        firstMap = new HashMap<>();
        followMap = new HashMap<>();
        for (int i = 0; i < cfg.variables.size(); i++)
            firstMap.put(cfg.variables.get(i), new ArrayList<>());
        for (int i = 0; i < cfg.variables.size(); i++)
            followMap.put(cfg.variables.get(i), new ArrayList<>());

        String[] firstTokens = cfgTokens[3].split(";");
        for (int i = 0; i < firstTokens.length; i++) {
            String[] tokenSplit = firstTokens[i].split("/");
            String varName = tokenSplit[0];
            String[] firsts = tokenSplit[1].split(",");
            for (int j = 0; j < firsts.length; j++)
                firstMap.get(varName).add(firsts[j]);
        }

        String[] followTokens = cfgTokens[4].split(";");
        for (int i = 0; i < followTokens.length; i++) {
            String[] tokenSplit = followTokens[i].split("/");
            String varName = tokenSplit[0];
            String[] follows = tokenSplit[1].split("");
            for (int j = 0; j < follows.length; j++)
                followMap.get(varName).add(follows[j]);
        }
        buildParseTable();
    }

    public void buildParseTable() {
        parseTable = new ParseTable(cfg.variables, cfg.alphabet);

        for (int i = 0; i < cfg.variables.size(); i++) {
            String curVar = cfg.variables.get(i);
            ArrayList<Rule> curRules = getRulesOf(curVar);
            ArrayList<String> firstList = firstMap.get(curVar);
            ArrayList<String> followList = followMap.get(curVar);
            for (int j = 0; j < firstList.size(); j++) {
                String firsts = firstList.get(j);
                Rule curRule = curRules.get(j);
                for (int k = 0; k < firsts.length(); k++) {
                    String curFirst = "" + firsts.charAt(k);
                    if (curFirst.equals("e")) {
                        for (int a = 0; a < followList.size(); a++) {
                            parseTable.addRule(curVar, followList.get(a), curRule);
                        }
                    } else {
                        parseTable.addRule(curVar, curFirst, curRule);
                    }
                }
            }
        }
    }

    public ArrayList<Rule> getRulesOf(String rule) {
        ArrayList<Rule> rules = new ArrayList<>();

        for (int i = 0; i < cfg.productionRules.size(); i++)
            if (cfg.productionRules.get(i).ruleName.equals(rule))
                rules.add(cfg.productionRules.get(i));
        return rules;
    }

    /**
     * @param input The string to be parsed by the LL(1) CFG.
     * @return A string encoding a left-most derivation.
     */
    public String parse(String input) {
        Stack<String> pdaStack = new Stack<>();
        ArrayList<Rule> rules = new ArrayList<>();
        input += "$";
        pdaStack.push("$");
        pdaStack.add(cfg.startVariable);

        int inputPtr = 0;
        while (inputPtr < input.length()) {
            String stackTop = pdaStack.peek();
            String curChar = "" + input.charAt(inputPtr);
            if (cfg.variables.contains(stackTop)) {
                Rule rule = parseTable.getRule(stackTop, curChar);
                if (rule == null) {
                    rules.add(null);
                    break;
                }
                pdaStack.pop();
                if (!rule.pattern.equals("e"))
                    for (int i = rule.pattern.length() - 1; i >= 0; i--) {
                        pdaStack.push("" + rule.pattern.charAt(i));
                    }
                rules.add(rule);
            } else {
                if (!curChar.equals(stackTop)) {
                    rules.add(null);
                    break;
                } else {
                    pdaStack.pop();
                    inputPtr++;
                }
            }
        }
        return getDerivations(rules);
    }

    private String getDerivations(ArrayList<Rule> rulesSequence) {
        ArrayList<String> derivations = new ArrayList<>();
        System.out.println(rulesSequence);
        String curDerivation = cfg.startVariable;

        for (int i = 0; i < rulesSequence.size(); i++) {
            Rule curRule = rulesSequence.get(i);
            derivations.add(curDerivation);
            if (curRule == null) {
                curDerivation = "ERROR";
                break;
            }
            curDerivation = curDerivation.replaceFirst(curRule.ruleName, curRule.pattern.equals("e") ? "" : curRule.pattern);
        }
        derivations.add(curDerivation);
        return String.join(";", derivations);
    }

}


class ParseTable {
    Rule[][] table;
    private HashMap<String, Integer> variablesMap, terminalsMap;
    private ArrayList<String> variables, terminals;

    public ParseTable(ArrayList<String> variables, ArrayList<String> terminals) {
        this.variables = variables;
        this.terminals = terminals;
        terminals.add("$");
        table = new Rule[variables.size()][terminals.size() + 1];
        variablesMap = new HashMap<>();
        terminalsMap = new HashMap<>();
        for (int i = 0; i < variables.size(); i++)
            variablesMap.put(variables.get(i), i);
        for (int i = 0; i < terminals.size(); i++)
            terminalsMap.put(terminals.get(i), i);
    }

    public void addRule(String var, String terminal, Rule r) {
        int varIdx = variablesMap.get(var);
        int terminalIdx = terminalsMap.get(terminal);
        table[varIdx][terminalIdx] = r;
    }

    public Rule getRule(String var, String terminal) {
        int varIdx = variablesMap.get(var);
        int terminalIdx = terminalsMap.get(terminal);
        return table[varIdx][terminalIdx];
    }

    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < variables.size(); i++)
            for (int j = 0; j < terminals.size(); j++) {
                ans.append(variables.get(i)).append(",").append(terminals.get(j));
                ans.append(" ").append(table[i][j]);
                ans.append("\n");
            }
        return ans.toString();
    }

}

class CFG {
    String startVariable;
    ArrayList<String> alphabet, variables;
    ArrayList<Rule> productionRules;

    public CFG(ArrayList<String> alphabet, ArrayList<String> variables, ArrayList<Rule> productionRules) {
        this.alphabet = alphabet;
        this.variables = variables;
        this.productionRules = productionRules;
        this.startVariable = "S";
    }

    public String alphabetRepresentation() {
        return alphabet.stream().collect(Collectors.joining(";"));
    }

    public String variableRepresentation() {
        return variables.stream()
                .collect(Collectors.joining(";"));
    }

    public String rulesRepresentation() {
        return productionRules.stream().
                map(Rule::toString).
                collect(Collectors.joining(";"));
    }

    public String toString() {
        return variableRepresentation() + "#" + alphabetRepresentation() + "#" + rulesRepresentation();
    }

    public boolean isVarHasEpsilon(String var) {
        for (int i = 0; i < this.productionRules.size(); i++) {
            Rule rule = productionRules.get(i);
            if (rule.ruleName.equals(var) && rule.pattern.equals("e"))
                return true;
        }
        return false;
    }
}

class Rule {
    String ruleName;
    String pattern;

    public Rule(String ruleName, String pattern) {
        this.ruleName = ruleName;
        this.pattern = pattern;
    }

    public String toString() {
        return ruleName + "-->" + pattern;
    }
}