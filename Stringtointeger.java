Public class Stringtointeger {
    public int myAtoi(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int index = 0;
        int n = s.length();

        // 1. Skip leading whitespaces
        while (index < n && s.charAt(index) == ' ') {
            index++;
        }

        // Check if the string is empty after skipping spaces
        if (index == n) {
            return 0;
        }

        // 2. Check for sign
        int sign = 1;
        if (s.charAt(index) == '+' || s.charAt(index) == '-') {
            sign = (s.charAt(index) == '-') ? -1 : 1;
            index++;
        }

        // 3. Convert digits and handle overflow
        int result = 0;
        while (index < n) {
            char curr = s.charAt(index);
            
            // Stop if the character is not a digit
            if (curr < '0' || curr > '9') {
                break;
            }

            int digit = curr - '0';

            // 4. Rounding / Overflow Check
            // Integer.MAX_VALUE is 2147483647, Integer.MIN_VALUE is -2147483648
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            result = result * 10 + digit;
            index++;
        }

        return result * sign;
    }
}