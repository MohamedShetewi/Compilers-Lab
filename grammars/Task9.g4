grammar Task9;

@headers {
    import java.*;
}


@members {
    public static int equals(int i, int j) {
        return i == j ? 1: 0;
    }
}

start: s EOF;

s returns [int check]: a c[$a.n2, $a.n3, 0, 1] b {
    $check = $c.slf * $c.suf * equals($a.n, $b.n);
};

a returns [int n, int n2, int n3]: A a1=a {
    $n = $a1.n + 1;
    $n2 = $a1.n2 * 2;
    $n3 = $a1.n3 * 3;
} | {$n = 0; $n2 = 1; $n3 = 1;};

b returns [int n]: B b1=b {$n = $b1.n + 1;} | {$n = 0;};

c [int l, int u, int ilf, int iuf] returns [int slf, int suf, int m]: C c1=c[l, u, ilf, iuf] {
    $m = $c1.m + 1;
    $slf = $c1.slf + equals(l, $m);
    $suf = $c1.suf - equals($u, $c1.m);
} | {$m = 0; $slf = $ilf; $suf = $iuf;};



A: 'a';
B: 'b';
C: 'c';