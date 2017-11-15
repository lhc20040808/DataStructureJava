import java.util.Stack;
import java.util.regex.Pattern;

/**
 * 参考:https://yq.aliyun.com/articles/37189
 * (1)当读到数字直接送至输出队列中；
 * (2)当读到运算符t时：
 * a.将栈中所有优先级高于或等于t的运算符弹出，送到输出队列中；
 * 注：这句话不好理解，可以说成这样，从栈顶开始，依次弹出比当前处理的运算符优先级高的运算符，直到一个比它优先级低的或者遇到了一个左括号就停止。
 * (3)读到左括号时总是将它压入栈中；
 * (4)读到右括号时，将靠近栈顶的第一个左括号上面的运算符全部依次弹出，送至输出队列后，再丢弃左括号；
 * (5)中缀表达式全部读完后，若栈中仍有运算符，将其送到输出队列中。
 */
public class PolandExpression {


    public static final char BLANK = ' ';

    public static void main(String[] args) {
        final String str = "10.1+ 2+(10-5)*11+4/2-1";
        PolandExpression expression = new PolandExpression();
        System.out.println(expression.toSuffix(str));
        System.out.println(expression.calSuffix(expression.toSuffix(str)));
    }

    public PolandExpression() {

    }

    public String toSuffix(String exp) {
        Stack<Character> stack = new Stack<>();
        char[] charArr = exp.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charArr.length; i++) {
            char nowCheckChar = charArr[i];
            switch (nowCheckChar) {
                case ' ':
                    break;
                case '+':
                case '-':
                    while (!stack.empty()) {
                        char symbol = stack.peek();
                        if (symbol == '(')
                            break;
                        sb.append(BLANK);
                        sb.append(stack.pop());
                    }
                    stack.push(nowCheckChar);
                    sb.append(BLANK);
                    break;
                case '*':
                case '/':
                    while (!stack.empty()) {
                        char symbol = stack.peek();
                        if (symbol == '(' || symbol == '+' || symbol == '-')
                            break;
                        sb.append(BLANK);
                        sb.append(stack.pop());
                    }
                    stack.push(nowCheckChar);
                    sb.append(BLANK);
                    break;
                case '(':
                    stack.push(nowCheckChar);
                    break;
                case ')':
                    while (!stack.empty()) {
                        char symbol = stack.peek();
                        if (symbol == '(') {
                            stack.pop();
                            break;
                        }
                        sb.append(BLANK);
                        sb.append(stack.pop());
                    }
                    break;
                default:
                    sb.append(nowCheckChar);
            }
        }

        while (!stack.empty()) {
            sb.append(BLANK);
            sb.append(stack.pop());
        }

        return sb.toString();
    }

    /**
     * (1)建立一个栈S；
     * (2)从左到右读后缀表达式，读到数字就将它转换为数值压入栈S中，读到运算符则从栈中依次弹出两个数分别到Y和X，然后以“X 运算符 Y”的形式计算机出结果，再压加栈S中；
     * (3)如果后缀表达式未读完，就重复上面过程，最后输出栈顶的数值则为结束。
     */
    public double calSuffix(String str) {
        Pattern pattern = Pattern.compile("\\d+||(\\d+\\.\\d+)"); //使用正则表达式 匹配数字
        String[] formulas = str.split(Character.toString(BLANK));
        Stack<Double> stack = new Stack<>();
        for (int i = 0; i < formulas.length; i++) {
            String nowCheck = formulas[i];
            if (pattern.matcher(nowCheck).matches()) {
                stack.push(Double.parseDouble(nowCheck));
            } else {
                double num1 = stack.pop();
                double num2 = stack.pop();
                stack.push(cal(num2, num1, nowCheck));
            }
        }
        return stack.pop();
    }

    private double cal(double num2, double num1, String nowCheck) {
        if ("+".equals(nowCheck)) {
            return num2 + num1;
        } else if ("-".equals(nowCheck)) {
            return num2 - num1;
        } else if ("*".equals(nowCheck)) {
            return num1 * num2;
        } else if ("/".equals(nowCheck)) {
            return num2 / num1;
        } else {
            throw new IllegalArgumentException();
        }

    }
}
