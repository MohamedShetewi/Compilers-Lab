package csen1002.main.task6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Write your info here
 *
 * @name Mohamed Shetewi
 * @id 46-13908
 * @labNumber 21
 */

public class CfgFirstFollow {

    /**
     * Constructs a Context Free Grammar
     *
     * @param cfgRepresentation A formatted string representation of the CFG. The string
     * representation follows the one in the task description
     */
    CFG cfg;

    public static void main(String[] args) {
        String cfg = "S;L;K#(;);a;n#S/(L),a;L/(L)K,aK;K/nSK,e";
        CfgFirstFollow c = new CfgFirstFollow(cfg);
        System.out.println(c.first());
        System.out.println(c.follow());
    }

    public CfgFirstFollow(String cfgRepresentation) {
        String[] cfgTokens = cfgRepresentation.split("#");
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
    }

    /**
     * Calculates the First Set of each variable in the CFG.
     *
     * @return A string representation of the First of each variable in the CFG,
     * formatted as specified in the task description.
     */
    public String first() {
        return ansToString(firstHelper());
    }

    public HashMap<String, ArrayList<String>> firstHelper() {
        HashMap<String, ArrayList<String>> firstList = new HashMap<>();
        for (int i = 0; i < cfg.alphabet.size(); i++) {
            String terminal = cfg.alphabet.get(i);
            ArrayList<String> firstArrayList = new ArrayList<>();
            firstArrayList.add(terminal);
            firstList.put(terminal, firstArrayList);
        }
        for (int i = 0; i < cfg.variables.size(); i++) {
            String var = cfg.variables.get(i);
            firstList.put(var, new ArrayList<>());
        }
        boolean change = true;

        while (change) {
            change = false;
            for (int k = 0; k < cfg.productionRules.size(); k++) {
                Rule rule = cfg.productionRules.get(k);
                if (isRuleGoesToEpsilon(rule.pattern, firstList))
                    if (!firstList.get(rule.ruleName).contains("e")) {
                        firstList.get(rule.ruleName).add("e");
                        change = true;
                    }
                if (rule.pattern.equals("e"))
                    continue;
                for (int i = -1; i < rule.pattern.length() - 1; i++) {
                    if (isRuleGoesToEpsilon(rule.pattern.substring(0, i + 1), firstList)) {
                        ArrayList<String> firstOfRule = firstList.get(rule.ruleName);
                        ArrayList<String> firstOfPattern = firstList.get("" + rule.pattern.charAt(i + 1));
                        if (!isSubset(firstOfPattern, firstOfRule, false)) {
                            for (int j = 0; j < firstOfPattern.size(); j++)
                                if (!firstOfPattern.get(j).equals("e"))
                                    firstOfRule.add(firstOfPattern.get(j));
                            change = true;
                        }
                    }
                }
            }
        }
        return firstList;
    }

    /**
     * Calculates the Follow Set of each variable in the CFG.
     *
     * @return A string representation of the Follow of each variable in the CFG,
     * formatted as specified in the task description.
     */
    public String follow() {
        HashMap<String, ArrayList<String>> followList = new HashMap<>();
        for (int i = 0; i < cfg.variables.size(); i++)
            followList.put(cfg.variables.get(i), new ArrayList<>());
        followList.get(cfg.startVariable).add("$");
        boolean change = true;
        HashMap<String, ArrayList<String>> firstList = firstHelper();
        while (change) {
            change = false;
            for (int i = 0; i < cfg.productionRules.size(); i++) {
                Rule curRule = cfg.productionRules.get(i); // A -> xBy
                String pattern = curRule.pattern;
                for (int j = 0; j < pattern.length(); j++) {
                    String curVar = "" + pattern.charAt(j);
                    if (!cfg.variables.contains(curVar))
                        continue;
                    ArrayList<String> followOfCur = followList.get(curVar);
                    ArrayList<String> firstOfNextToCur = firstOf(pattern.substring(j + 1), firstList); // Check this
                    if (!isSubset(firstOfNextToCur, followOfCur, false)) {
                        for (int k = 0; k < firstOfNextToCur.size(); k++)
                            if (!firstOfNextToCur.get(k).equals("e"))
                                followOfCur.add(firstOfNextToCur.get(k));
                        change = true;
                    }

                    if (firstOfNextToCur.contains("e") || j + 1 == pattern.length()) { // check this if failed
                        if (!isSubset(followList.get(curRule.ruleName), followList.get(curVar), false)) {
                            followList.get(curVar).addAll(followList.get(curRule.ruleName));
                            change = true;
                        }
                    }
                }
            }
        }
        return ansToString(followList);
    }

    public ArrayList<String> firstOf(String expression, HashMap<String, ArrayList<String>> firstList) {
        ArrayList<String> firstOfExpression = new ArrayList<>();

        for (int i = 0; i < expression.length(); i++) {
            String curSymbol = "" + expression.charAt(i);
            ArrayList<String> firstOfCurSym = firstList.getOrDefault(curSymbol, new ArrayList<>());
            firstOfExpression.addAll(firstOfCurSym);
            if (i + 1 < expression.length()) {
                firstOfExpression.removeIf(item -> item.equals("e"));
            }
            if (!firstOfCurSym.contains("e")) {
                break;
            }
        }

        return firstOfExpression;
    }


    public String ansToString(HashMap<String, ArrayList<String>> firstList) {
        ArrayList<String> firstStringRepresentation = new ArrayList<>();
        for (int i = 0; i < cfg.variables.size(); i++) {
            String var = cfg.variables.get(i);
            String firstString = String.join("", firstList.get(var).stream().distinct().sorted().toList());
            String ans = var +
                    "/" +
                    firstString;
            firstStringRepresentation.add(ans);
        }

        return String.join(";", firstStringRepresentation);
    }

    public boolean isSubset(ArrayList<String> A, ArrayList<String> B, boolean isEpsilon) {
        for (int i = 0; i < A.size(); i++) {
            String s = A.get(i);
            if (!isEpsilon && s.equals("e")) continue;
            if (!B.contains(s)) return false;
        }
        return true;
    }

    public boolean isRuleGoesToEpsilon(String pattern, HashMap<String, ArrayList<String>> firstList) {
        if (pattern.equals("e") || pattern.isEmpty())
            return true;
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            ArrayList<String> firstOfC = firstList.get("" + c);
            if (!firstOfC.contains("e"))
                return false;
        }
        return true;
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


}
