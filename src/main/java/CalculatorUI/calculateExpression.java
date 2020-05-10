package CalculatorUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet
public class calculateExpression extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String expr = req.getParameter("expr");
		int result = 0;
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Result Page</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h2>");
		try {
			result = evaluate(expr);
			out.println(expr+" = "+result);
		}
		catch(Exception e) {
			out.println("Invalid Expression : {{ " + expr + " }}");
		}
		out.println("</h2>");
		out.println("</body>");
		out.println("</html>");
	}

    public static int evaluate(String expression) 
    { 
        char[] tokens = expression.toCharArray(); 
        Stack<Integer> values = new Stack<Integer>(); 
        Stack<Character> ops = new Stack<Character>(); 
  
        for (int i = 0; i < tokens.length; i++) 
        { 
            if (tokens[i] == ' ') 
                continue; 
            if (tokens[i] >= '0' && tokens[i] <= '9') 
            { 
                StringBuffer sbuf = new StringBuffer(); 
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') 
                    sbuf.append(tokens[i++]); 
                values.push(Integer.parseInt(sbuf.toString())); 
            } 
            else if (tokens[i] == '(') 
                ops.push(tokens[i]); 
            else if (tokens[i] == ')') 
            { 
                while (ops.peek() != '(') 
                  values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
                ops.pop(); 
            } 
            else if (tokens[i] == '+' || tokens[i] == '-' || 
                     tokens[i] == 'x' || tokens[i] == '/' || tokens[i]=='^') 
            { 
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) 
                  values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
 
                ops.push(tokens[i]); 
            } 
        } 
        while (!ops.empty()) 
            values.push(applyOp(ops.pop(), values.pop(), values.pop())); 
        return values.pop(); 
    } 

    public static boolean hasPrecedence(char op1, char op2) 
    { 
        if (op2 == '(' || op2 == ')') 
            return false; 
        if (op1 == '^')
        	return false;
        if ((op1 == 'x' || op1 == '/') && (op2 == '+' || op2 == '-')) 
            return false; 
        else
            return true; 
    } 
    public static int applyOp(char op, int b, int a) 
    { 
        switch (op) 
        { 
        case '+': 
            return a + b; 
        case '-': 
            return a - b; 
        case 'x': 
            return a * b; 
        case '/': 
            if (b == 0) 
                return Integer.MAX_VALUE; 
            return a / b; 
        case '^':
        	return (int)Math.pow(a, b);
        } 
        return 0; 
    }

}