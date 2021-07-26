struc Tuple<Array<Class> Types> {
	Types[index] at(Int index) assert(0 <= index < Types.size);
	at(Int index, Types[index] value) assert(0 <= index < Types.size);
} 