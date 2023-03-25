package csen1002.main.task4;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Write your info here
 *
 * @name Mohamed Shetewi
 * @id 46-13908
 * @labNumber 21
 */

public class CfgEpsUnitElim {

    /**
     * Constructs a Context Free Grammar
     *
     * @param cfg A formatted string representation of the CFG. The string
     * representation follows the one in the task description
     */

    CFG cfg;

    public static void main(String[] args) {
        String s = "ACACA";

        HashSet<String> newPatterns = new HashSet<>();
        addAllOccurrences("A",s, 0, "", newPatterns);
        System.out.println(s);
        System.out.println(newPatterns.size());
        System.out.println(newPatterns.stream().collect(Collectors.joining(";")));
    }

    public CfgEpsUnitElim(String cfgRepresentation) {
        String[] cfgTokens = cfgRepresentation.split("#");
        ArrayList<String> variables = new ArrayList<>(
                Arrays.stream(cfgTokens[0].split(";")).toList());
        ArrayList<String> alphabet = new ArrayList<>(
                Arrays.stream(cfgTokens[1].split(";")).toList()
        );
        ArrayList<Rule> productionRules = new ArrayList<>(
                Arrays.stream(cfgTokens[2].split(";"))
                        .map(item -> {
                            String[] itemSplit = item.split("/");
                            String ruleName = itemSplit[0];
                            Rule rule = new Rule(ruleName);
                            String[] patternSplit = itemSplit[1].split(",");
                            Arrays.stream(patternSplit).forEach(rule::addPattern);
                            return rule;
                        }).toList()
        );
        cfg = new CFG(alphabet, variables, productionRules);
    }

    /**
     * @return Returns a formatted string representation of the CFG. The string
     * representation follows the one in the task description
     */
    @Override
    public String toString() {
        return cfg.toString();
    }

    /**
     * Eliminates Epsilon Rules from the grammar
     */
    public void eliminateEpsilonRules() {
        ArrayList<String> processedRules = new ArrayList<>();
        for (int i = 0; i < cfg.productionRules.size(); i++) {
            Rule rule = cfg.productionRules.get(i);
            if (rule.containsEpsilon() && !rule.ruleName.equals(cfg.startVariable)) {
                cfg.productionRules.get(i).patterns.remove("e");
                processedRules.add(rule.ruleName);
                eliminateEpsilonRulesHelper(cfg.productionRules.get(i), processedRules);
                i = -1;
            }
        }
    }

    private void eliminateEpsilonRulesHelper(Rule ruleWithEpsilon, ArrayList<String> processedRules) {
        for (int i = 0; i < cfg.productionRules.size(); i++) {
            Rule rule = cfg.productionRules.get(i);
            HashSet<String> newPatterns = new HashSet<>();
            for (int j = 0; j < rule.patterns.size(); j++) {
                String pattern = rule.patterns.stream().toList().get(j);
                if (pattern.contains(ruleWithEpsilon.ruleName)) {
                    addAllOccurrences(ruleWithEpsilon.ruleName, pattern, 0, "", newPatterns);
                }
            }
            if (processedRules.contains(rule.ruleName)) {
                newPatterns.remove("e");
            }
            rule.patterns.addAll(newPatterns);
        }
    }

    public static void addAllOccurrences(String rule, String pattern, int idx, String ans, HashSet<String> newPattern) {
        if (idx == pattern.length()) {
            newPattern.add(ans.length() > 0 ? ans : "e");
        } else {
            if (pattern.charAt(idx) == rule.charAt(0)) {
                addAllOccurrences(rule, pattern, idx + 1, ans, newPattern);
            }
            addAllOccurrences(rule, pattern, idx + 1, ans + (pattern.charAt(idx)), newPattern);
        }
    }

    /**
     * Eliminates Unit Rules from the grammar
     */
    public void eliminateUnitRules() {
        HashMap<String, HashSet<String>> processedRules = new HashMap<>();
        for (String var : cfg.variables)
            processedRules.put(var, new HashSet<>());
        for (int i = 0; i < cfg.productionRules.size(); i++) {
            Rule curRule = cfg.productionRules.get(i);
            ArrayList<String> unitRules = curRule.getUnitRules(cfg.variables);

            for (int j = 0; j < unitRules.size(); j++) {
                i = -1;
                List<String> patternsForUnitRules = cfg.getPatterns(unitRules.get(j));
                for (int k = 0; k < patternsForUnitRules.size(); k++) {
                    String pattern = patternsForUnitRules.get(k);
                    if (processedRules.get(curRule.ruleName).contains(pattern)) {
                        continue;
                    }
                    curRule.patterns.add(pattern);
                }
                curRule.patterns.remove(unitRules.get(j));
                processedRules.get(curRule.ruleName).add(unitRules.get(j));
            }
        }
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

    public List<String> getPatterns(String rule) {
        ArrayList<String> ans = new ArrayList<>();
        ArrayList<Rule> arrayList = new ArrayList<>(productionRules
                .stream()
                .filter(item -> item.ruleName.equals(rule))
                .toList());
        arrayList.forEach(item -> ans.addAll(item.patterns));
        return ans;
    }

    public String toString() {
        return variableRepresentation() + "#" + alphabetRepresentation() + "#" + rulesRepresentation();
    }

}

class Rule {
    String ruleName;
    HashSet<String> patterns;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(ruleName, rule.ruleName) && Objects.equals(patterns, rule.patterns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleName, patterns);
    }

    public Rule(String ruleName) {
        this.ruleName = ruleName;
        this.patterns = new HashSet<>();
    }

    public void addPattern(String pattern) {
        patterns.add(pattern);
    }

    public boolean containsEpsilon() {
        return patterns.contains("e");
    }

    public boolean containsVariable(String var) {
        for (String pattern : patterns)
            if (pattern.contains(var))
                return true;
        return false;
    }

    public String toString() {
        return ruleName + "/" + patterns.stream().sorted().collect(Collectors.joining(","));
    }

    public ArrayList<String> getUnitRules(ArrayList<String> variables) {
        ArrayList<String> unitRules = new ArrayList<>();
        for (String pattern : patterns)
            if (variables.contains(pattern))
                unitRules.add(pattern);
        return unitRules;
    }
}