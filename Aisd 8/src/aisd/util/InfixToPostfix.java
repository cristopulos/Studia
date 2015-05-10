package aisd.util;

import aisd.util.InvalidCharacterExcpection;
import aisd.util.Stos;

public class InfixToPostfix {
	private static Stos<Character> stack;
	private static StringBuffer ONP;

	public static String convert(String in) throws InvalidCharacterExcpection {
		char currChar;
		stack = new Stos<Character>();
		ONP = new StringBuffer();
		for (int i = 0; i < in.length(); i++) {
			currChar = in.charAt(i);
			if (isOperator(currChar)) {
				processOperator(currChar);
			} else if (Character.isDigit(currChar)) {
				StringBuffer sb = new StringBuffer();
				sb.append(currChar);
				while ((i + 1 < in.length())
						&& ((Character.isDigit(in.charAt(i+1))))) {
					sb.append(in.charAt(++i));
				}
				sb.append('|');
				ONP.append(sb);
			} else
				throw new InvalidCharacterExcpection(Character.toString(currChar));
		}
		appendAnyOperatorsLeft();
		return ONP.toString();

	}
	
	private static void appendAnyOperatorsLeft ()
	{
		while(!stack.empty())
		{
			ONP.append(stack.pop());
		}
	}

	private static void processOperator(char op)
			throws InvalidCharacterExcpection {
		char topOp;
		if (stack.empty() || op == '(')
			stack.push(op);
		else {
			topOp = stack.peek();
			if (precedence(topOp) < precedence(op)) {
				stack.push(op);
			} else {
				while (!stack.empty() && (precedence(op) <= precedence(topOp))) {
					char temp = stack.pop();
					if (temp == '(')
						break;
					ONP.append(temp);
					if (!stack.empty())
						topOp = stack.peek();
				}
				if (op != ')')
					stack.push(op);
			}
		}
	}

	private static boolean isOperator(char ch) {
		return ch=='+' || ch=='-' || ch=='*' || ch=='/' || ch=='(' || ch == ')' || ch == '%';
	}

	private static int precedence(char op) throws InvalidCharacterExcpection {
		switch (op) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
		case '%':
			return 2;
		case '(':
		case ')':
			return -1;
		default:
			throw new InvalidCharacterExcpection(null);
		}

	}

}
