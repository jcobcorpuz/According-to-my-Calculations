import java.util.Stack;

public class Calculator {
    public double evaluate(String expression){
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    public String infixToPostfix(String expression){
        String output = "";
        Stack<String> stack = new Stack<>();

        String[] tokens = expression.split(" ");

        for(int i = 0; i < tokens.length; i++){
            String token = tokens[i];

            if(Character.isDigit(token.charAt(0))){
                output += token + " ";
            }
            else if(token.equals("(")){
                stack.push(token);
            }
            else if(token.equals(")")){
                while (!stack.isEmpty() && !stack.peek().equals("(")){
                    output += stack.pop() + " ";
                }
                stack.pop();
            }
            else{
                while (!stack.isEmpty() && isOperator(stack.peek()) && getPrecedence(token) <= getPrecedence(stack.peek())){
                    output += stack.pop() + " ";
                }
                stack.push(token);
            }
        }

        while(!stack.isEmpty()){
            output += stack.pop() + " ";
        }
        return output.trim();
    }

    public double evaluatePostfix(String postfix){
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split(" ");

        for(int i = 0; i < tokens.length; i++){
            String token = tokens[i];

            if(Character.isDigit(token.charAt(0))){
                stack.push(Double.parseDouble(token));
            }
            else{
                double b = stack.pop();
                double a = stack.pop();

                if(token.equals("+")){
                    stack.push(a + b);
                }
                else if(token.equals("-")){
                    stack.push(a - b);
                }
                else if(token.equals("*")){
                    stack.push(a * b);
                }
                else if(token.equals("/")){
                    stack.push(a/b);
                }
            }
        }
        return stack.pop();
    }

    public int getPrecedence(String operator){
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        }
        else if(operator.equals("*") || operator.equals("/")){
            return 2;
        }
        return 0;
    }

    public boolean isOperator(String token){
        return token.equals("+") || token.equals("-") || token.equals("*") ||  token.equals("/");
    }
    public static void main(String[] args){
        Calculator calculator = new Calculator();

        System.out.println(calculator.evaluate("2 + 5"));
        System.out.println(calculator.evaluate("3 + 6 * 5"));
        System.out.println(calculator.evaluate("4 * ( 2 + 3 )"));
        System.out.println(calculator.evaluate("( 7 + 9 ) / 8"));
    }
}
