Int factorial(Int num) if(num <= 1) return 1; else return factorial(num - 1) * num;
println("Factorial of 5 is \(factorial(5)).");