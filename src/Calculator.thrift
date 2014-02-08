namespace java lk.swithana.calculator
 
service lk.swithana.calculator.server.CalculatorService
{
    i32 add  (1:i32 a, 2:i32 b),
    i32 sub  (1:i32 a, 2:i32 b),
    i32 div  (1:i32 a, 2:i32 b),
    i32 mult (1:i32 a, 2:i32 b)
}
