#include <iostream>
#include <set>
using namespace std;

int main()
{
    int n;
    cin >> n;
    set<char> piece;

    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= n; j++)
        {
            if (i + j <= n)
            {
                int third = (n - (i + j));
                if (third != 0)
                {
                    int number = third * 100 + j * 10 + i;
                    cout << number << endl;
                    // piece.insert();
                }
            }
        }
        // print piece
        for (auto it = piece.begin(); it != piece.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }

    return 0;
}