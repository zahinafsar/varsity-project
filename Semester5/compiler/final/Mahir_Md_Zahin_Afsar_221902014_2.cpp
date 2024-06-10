#include <iostream>
#include <string>

using namespace std;

bool pattern1(const string &str)
{
    int i = 0;
    if (str[i] != 'a')
        return false;
    while (str[i] == 'a')
        i++;
    while (str[i] == 'c')
        i++;
    if (str[i] == 'b' && i + 1 == str.size())
        return true;
    return false;
}

bool pattern2(const string &str)
{
    int i = 0;
    while (i < str.size())
    {
        while (str[i] == 'b')
            i++;
        if (str[i] != 'c')
            return false;
        i++;
        while (str[i] == 'a')
            i++;
    }
    return true;
}

int main()
{
    string input;
    cout << "Enter the string: ";
    cin >> input;

    if (pattern1(input) || pattern2(input))
    {
        cout << "Accepted\n";
    }
    else
    {
        cout << "Rejected\n";
    }

    return 0;
}
