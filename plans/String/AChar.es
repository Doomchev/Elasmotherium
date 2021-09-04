class AChar extends U8 {
	ThisType lowerCase -> "A" <= this <= "Z" ? this - "A" + "a" : this;
	ThisType upperCase -> "a" <= this <= "z" ? this - "a" + "A" : this;
	Question isDigit -> "0" <= this <= "9";
	Question isLetter -> "a" <= this <= "z" || "A" <= this <= "Z";
}