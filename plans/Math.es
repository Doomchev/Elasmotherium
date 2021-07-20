Num limit<Num extends Number>(Num value, Num from, Num to) -> value < from ? from : (value > to ? to : value);
Num min<Num extends Number>(Num value0, Num value1) -> value0 < value1 ? value0 : value1;
Num max<Num extends Number>(Num value0, Num value1) -> value0 > value1 ? value0 : value1;