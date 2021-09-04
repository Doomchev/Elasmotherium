Num bound<AnyNumber Num>(Num value, Num from, Num to) -> value < from ? from : (value > to ? to : value);
Num min<AnyNumber Num>(Num value0, Num value1) -> value0 < value1 ? value0 : value1;
Num max<AnyNumber Num>(Num value0, Num value1) -> value0 > value1 ? value0 : value1;
Num abs<AnyNumber Num>(Num value) -> value < 0 ? -value : value;
Num sign<AnyNumber Num>(Num value) -> value > 0 ? 1 : (value < 0 ? -1 : 0);

Int floor<AnyInt Int, AnyFloat Float>(Float value);
Int ceil<AnyInt Int, AnyFloat Float>(Float value);
Int round<AnyInt Int, AnyFloat Float>(Float value);

enum RoundingMode {floor, mid, ceil}
Int round<AnyInt Int, AnyFloat Float>(Float value, Int digits, RoundingMode mode);