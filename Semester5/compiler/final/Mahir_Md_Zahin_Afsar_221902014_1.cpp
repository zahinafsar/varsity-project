#include <iostream>
#include <vector>
#include <string>
#include <unordered_set>
#include <cctype>

using namespace std;

unordered_set<string> keywords = {"start", "end", "var", "loop", "if", "func", "main"};
unordered_set<char> symbols = {'#', '{', '}', '[', ']', '(', ')', '!'};
unordered_set<char> operators = {'-', '+', '*', '/', '%', '=', '|', '&', '<', '>'};

struct Token
{
    string type;
    string value;
};

bool isKeyword(const string &word)
{
    return keywords.find(word) != keywords.end();
}

bool isSymbol(char ch)
{
    return symbols.find(ch) != symbols.end();
}

bool isOperator(char ch)
{
    return operators.find(ch) != operators.end();
}

bool isNumber(const string &word)
{
    for (char ch : word)
    {
        if (!isdigit(ch))
        {
            return false;
        }
    }
    return true;
}

void addToken(vector<Token> &tokens, const string &type, const string &value)
{
    tokens.push_back({type, value});
}

void processCurrentWord(vector<Token> &tokens, string &current)
{
    if (!current.empty())
    {
        if (isKeyword(current))
        {
            addToken(tokens, "Keyword", current);
        }
        else if (isNumber(current))
        {
            addToken(tokens, "Constant", current);
        }
        else
        {
            addToken(tokens, "Variable", current);
        }
        current.clear();
    }
}

vector<Token> tokenize(const string &code)
{
    vector<Token> tokens;
    string current;

    for (size_t i = 0; i < code.size(); ++i)
    {
        char ch = code[i];

        if (isspace(ch))
        {
            processCurrentWord(tokens, current);
        }
        else if (isSymbol(ch))
        {
            processCurrentWord(tokens, current);
            addToken(tokens, "Symbol", string(1, ch));
        }
        else if (isOperator(ch))
        {
            processCurrentWord(tokens, current);
            addToken(tokens, "Operator", string(1, ch));
        }
        else
        {
            current += ch;
        }
    }

    return tokens;
}

void printTokens(const vector<Token> &tokens)
{
    cout << "Keywords: ";
    for (const Token &token : tokens)
    {
        if (token.type == "Keyword")
        {
            cout << token.value << " ";
        }
    }
    cout << endl;

    cout << "Variables: ";
    for (const Token &token : tokens)
    {
        if (token.type == "Variable")
        {
            cout << token.value << " ";
        }
    }
    cout << endl;

    cout << "Operators: ";
    for (const Token &token : tokens)
    {
        if (token.type == "Operator")
        {
            cout << token.value << " ";
        }
    }
    cout << endl;

    cout << "Constants: ";
    for (const Token &token : tokens)
    {
        if (token.type == "Constant")
        {
            cout << token.value << " ";
        }
    }
    cout << endl;

    cout << "Symbols: ";
    for (const Token &token : tokens)
    {
        if (token.type == "Symbol")
        {
            cout << token.value << " ";
        }
    }
    cout << endl;
}

int main()
{
    cout << "Enter your code (type 'endall' on a new line to finish input):" << endl;

    string code, line;
    while (getline(cin, line))
    {
        if (line == "endall")
        {
            break;
        }
        code += line + "\n";
    }

    vector<Token> tokens = tokenize(code);
    printTokens(tokens);

    return 0;
}
