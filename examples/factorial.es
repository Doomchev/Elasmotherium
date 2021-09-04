Int factorial(Int num) -> num <= 1 ? 1 : factorial(num - 1) * num;
println("Factorial of 5 is \(factorial(5)).");