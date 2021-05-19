# CPU_Mixed_Scheduler_Simulation
Not perfect simulation, 2/3 points.
# Hungarian specification
Készítsen egy programot Java vagy Python nyelven, amely egy összetett ütemező működését szimulálja!

A globálisan preemptív, statikus prioritásos ütemező az alábbi ütemezési algoritmusokat futtatja az egyes szinteken az előadáson ismertetett módon:

1. magas prioritású szint (prioritás = 1) SJF ütemező
2. alacsony prioritású szint (prioritás = 0) RR ütemező, időszelet: 2
Bemenet (standard input, stdin)
Soronként egy (max. 10) taszk adatai. Egy sor felépítése (vesszővel elválasztva):

a taszk betűjele (A, B, C...)
a taszk prioritása (0 vagy 1)
a taszk indítási ideje (egész szám >= 0), a következő időszeletben már futhat (0: az ütemező indításakor már létezik), egyszerre érkező taszkok esetén az ABC-sorrend dönt
a taszk CPU-löketideje (egész szám >= 1)
Példa:

A,0,0,6<br>
B,0,1,5<br>
C,1,5,2<br>
D,1,10,1<br><br>
A bemenet végét EOF jelzi (előtte soremelés biztosan van, és üres sor is előfordulhat).

Kimenet (standard output, stdout)
A kimenet első sorában a taszkok futási sorrendje betűjeleikkel (csak betűk, szóközök nélkül).
A második sorban a teljes várakozási idő taszkonként, érkezésük (nem feltétlenül abc-) sorrendjében, az alábbi formában (vesszővel elválasztva, szóközök nélkül):

1. taszk betűjel:várakozási idő,2. betűjel:várakozási idő, ...

Példa (a fenti bemenetre adott válasz):

ABACBADBA<br>
A:8,B:6,C:0,D:0<br>

Tesztadatok
Az első beküldés előtt érdemes az alábbi tesztekkel megpróbálkozni.

A,0,2,7<br>
B,0,2,3<br><br>
ABABA<br>
A:3,B:4<br><br>
Q,0,5,8<br>
P,1,7,2<br><br>
QPQ<br>
Q:2,P:0<br><br>
A,0,0,3<br>
B,1,0,2<br>
C,0,3,3<br>
D,1,4,1<br><br>
BADCAC<br>
A:5,B:0,C:3,D:0<br><br>
