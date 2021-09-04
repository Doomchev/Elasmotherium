struc Tuple<Array<Class> Types> {
	Types[index] getAtIndex(Int index) assert(0 <= index < Types.size);
	setAtIndex(Int index, Types[index] value) assert(0 <= index < Types.size);
}