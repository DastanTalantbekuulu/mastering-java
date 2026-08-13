#include <vector>
#include <algorithm>
#include <iostream>

using namespace std;

int counts(int *a, int m, int n, int cnt)
{
    if (n == 0)
        return cnt;
    if (n < 0)
        return -1;
    if (m == 0)
        return -1;
    int c2 = counts(a + 1, m - 1, n - 2 * *a, cnt + 2);
    int c1 = counts(a + 1, m - 1, n - *a, cnt + 1);
    int c0 = counts(a + 1, m - 1, n, cnt);
    vector<int> b;
    if (c2 >= 0)
        b.push_back(c2);
    if (c1 >= 0)
        b.push_back(c1);
    if (c0 >= 0)
        b.push_back(c0);
    if (b.size())
        return *min_element(b.begin(), b.end());
    return -1;
}

int main(int argc, char *argv[])
{
    int n, m;

    cin >> n >> m;
    int a[15], sum = 0;
    for (int i = 0; i < m; ++i)
    {
        cin >> a[i];
        sum += 2 * a[i];
    }
    if (sum < n)
        cout << -1;
    else
    {
        sort(a, a + m, greater<int>());
        int c = counts(a, m, n, 0);
        cout << (c < 0 ? 0 : c);
    }
}