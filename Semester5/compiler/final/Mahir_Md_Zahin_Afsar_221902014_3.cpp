#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
#include <string>
#include <cctype>

using namespace std;

unordered_map<char, vector<string>> grammar;

unordered_set<char> computeFirst(char nonTerminal) {
    unordered_set<char> result;
    if (grammar.find(nonTerminal) != grammar.end()) {
        for (const string& rule : grammar[nonTerminal]) {
            if (rule.empty()) {
                result.insert('#');
            } else if (islower(rule[0]) || rule[0] == '#') {
                result.insert(rule[0]);
            } else {
                auto firstOfRule = computeFirst(rule[0]);
                result.insert(firstOfRule.begin(), firstOfRule.end());
            }
        }
    }
    return result;
}

int main() {
    cout << "Enter grammar productions (Enter 'x' to stop):\n";
    
    char nonTerminal;
    string production;
    
    while (true) {
        cout << "Non-terminal: ";
        cin >> nonTerminal;
        if (nonTerminal == 'x') break;
        
        cout << "Production: ";
        cin >> production;
        grammar[nonTerminal].push_back(production);
    }

    unordered_map<char, unordered_set<char>> firstSets;
    
    for (const auto& [nonTerminal, _] : grammar) {
        firstSets[nonTerminal] = computeFirst(nonTerminal);
    }

    cout << "\nFIRST sets:" << endl;
    
    for (const auto& [nonTerminal, symbols] : firstSets) {
        cout << "FIRST(" << nonTerminal << ") = { ";
        for (char symbol : symbols) {
            cout << symbol << " ";
        }
        cout << "}" << endl;
    }

    return 0;
}
