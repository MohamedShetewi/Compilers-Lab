package csen1002.main.task5;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Write your info here
 *
 * @name Mohamed Shetewi
 * @id 46-13908
 * @labNumber 21
 */

public class CfgLeftRecElim {

    /**
     * Constructs a Context Free Grammar
     *
     * @param cfg A formatted string representation of the CFG. The string
     * representation follows the one in the task description
     */
    CFG cfg;

    public CfgLeftRecElim(String cfgRepresentation) {
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
     * Eliminates Left Recursion from the grammar
     */
    public void eliminateLeftRecursion() {
        ArrayList<String> variables = cfg.variables;
        ArrayList<Rule> newRules = new ArrayList<>();
        for (int i = 0; i < variables.size(); i++) {
            Rule rule1 = cfg.getProductionRule(variables.get(i));
            for (int j = 0; j < i; j++) {
                Rule rule2 = cfg.getProductionRule(variables.get(j));
                rule1.eliminateLeftRecursion(rule2);
            }
            Rule ruleDash = rule1.removeImmediateLeftRecursion();
            if (ruleDash != null) {
                newRules.add(ruleDash);
            }
        }
        for (int i = 0; i < newRules.size(); i++) {
            Rule newRule = newRules.get(i);
            cfg.variables.add(newRule.ruleName);
            cfg.productionRules.add(newRule);
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

    public Rule getProductionRule(String var) {
        for (int i = 0; i < this.productionRules.size(); i++) {
            Rule r = this.productionRules.get(i);
            if (r.ruleName.equals(var))
                return r;
        }
        return null;
    }

    public String toString() {
        return variableRepresentation() + "#" + alphabetRepresentation() + "#" + rulesRepresentation();
    }

}

class Rule {
    String ruleName;
    ArrayList<String> patterns;

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
        this.patterns = new ArrayList<>();
    }

    public void addPattern(String pattern) {
        patterns.add(pattern);
    }

    public Rule removeImmediateLeftRecursion() {
        String ruleName = this.ruleName + "'";
        Rule newRule = new Rule(ruleName);
        boolean hasImmediate = false;
        for (int i = 0; i < this.patterns.size(); i++) {
            String curString = patterns.get(i);
            if (curString.startsWith(this.ruleName)) {
                hasImmediate = true;
                curString = curString.replaceFirst(this.ruleName, "");
                newRule.patterns.add(curString + ruleName);
                this.patterns.remove(i);
                i -= 1;
            }
        }
        if (hasImmediate) {
            for (int i = 0; i < this.patterns.size(); i++) {
                patterns.set(i, patterns.get(i) + ruleName);
            }
            newRule.patterns.add("e");
        }
        return hasImmediate ? newRule : null;
    }

    public void eliminateLeftRecursion(Rule r) {
        boolean hasPatternWithVariable = hasPatternWithVariable(r.ruleName);
        if (!hasPatternWithVariable)
            return;
        for (int i = 0; i < this.patterns.size(); i++) {
            if (patterns.get(i).startsWith(r.ruleName))
                replaceProductionRulesWithStartingVar(i, r);
        }
    }

    private boolean hasPatternWithVariable(String variable) {
        for (int i = 0; i < this.patterns.size(); i++) {
            String pattern = patterns.get(i);
            if (pattern.startsWith(variable))
                return true;
        }
        return false;
    }

    private void replaceProductionRulesWithStartingVar(int idx, Rule replacingRule) {
        String ruleToBeReplaced = this.patterns.get(idx);
        ArrayList<String> newPatterns = new ArrayList<>();
        for (int i = 0; i < replacingRule.patterns.size(); i++) {
            String rule = replacingRule.patterns.get(i);
            String newRule = replaceStartOfRule(ruleToBeReplaced, replacingRule.ruleName, rule);
            if (newRule == null)
                continue;
            newPatterns.add(newRule);
        }
        this.patterns.remove(idx);
        for (int i = 0; i < newPatterns.size(); i++) {
            this.patterns.add(idx + i, newPatterns.get(i));
        }
    }

    private String replaceStartOfRule(String ruleToBeReplaced, String newRuleName, String replacement) {
        if (!ruleToBeReplaced.startsWith(newRuleName))
            return null;
        return ruleToBeReplaced.replaceFirst(newRuleName, replacement);
    }


    public String toString() {
        return ruleName + "/" + String.join(",", patterns);
    }
}

