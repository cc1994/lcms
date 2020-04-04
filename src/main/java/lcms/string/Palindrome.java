package lcms.string;

public class Palindrome {
	public static void main(String[] args) {
		System.out.println(countSubstrings("aaa"));
	}
	
	// 最长回文子串
	// 以每个字符为中心找最大的回文（偶数长度则中间偏左） 
	// O(N2)
	public static String longestPalindrome(String s) {
		int resultCount = 0;
		String result = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			
			// 奇数长度回文
			int max = 1;
			String maxResult = String.valueOf(c);
			for (int delta = 1; i - delta >= 0 && i + delta < s.length(); delta++) {
				char leftChar = s.charAt(i-delta);
				char rightChar = s.charAt(i+delta);
				if (leftChar == rightChar) {
					max = max + 2;
					maxResult = leftChar + maxResult + rightChar;
				}else {
					break;
				}
			}
			
			if (max > resultCount) {
				resultCount = max;
				result = maxResult;
			}
			
			// 偶数长度回文
			if (i < s.length() - 1) {
				char nextC = s.charAt(i + 1);
				if (c == nextC) {
					max = 2;
					maxResult = String.valueOf(c) + nextC;
					for (int delta = 1; i - delta >= 0 && i + delta + 1 < s.length(); delta++) {
						char leftChar = s.charAt(i-delta);
						char rightChar = s.charAt(i+delta + 1);
						if (leftChar == rightChar) {
							max = max + 2;
							maxResult = leftChar + maxResult + rightChar;
						}else {
							break;
						}
					}
				}
				if (max > resultCount) {
					resultCount = max;
					result = maxResult;
				}
			}
		}
		return result;
    }
	
	public static int countSubstrings(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			for (int j = i + 1; j < s.length() + 1; j++) {
				String substring = s.substring(i, j);
				if (isPalindrome(substring)) {
					count++;
				}
			}
		}
		return count;
    }
	
	private static boolean isPalindrome(String s) {
		for (int i = 0; i < s.length()/2; i++) {
			if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
				return false;
			}
		}
		return true;
	}
	
	// Manacher算法:通过插入特殊字符，将奇数偶数汇总为同一种情况
	// https://blog.csdn.net/qq_42366884/article/details/89816472
	// p[] 为回文半径数组
	public static int longestPalindromeByManacher(String s) {
		// 1.构造新的字符串
        // 为了避免奇数回文和偶数回文的不同处理问题，在原字符串中插入'#'，将所有回文变成奇数回文
		String manacherStr = manacherInit(s);		
		
		// p[i]表示以i为中心的回文的最大半径，i至少为1，即该字符本身
		int[] p = new int[manacherStr.length()];
		
		// 回文最大长度
		int max = 0;
		
        // right表示已知的回文中，最右的边界的坐标
        int r = 0;
        
        // id表示已知的回文中，拥有最右边界的回文的中点坐标
        int id = 0;
        
        // 2.计算所有的p
        // 这个算法是O(n)的，因为right只会随着里层while的迭代而增长，不会减少。
		for (int i = 0; i < manacherStr.length(); i++) {
			// i是否超出 r
			p[i] = i < r ? Math.min(p[2 * id - 1], r - i) : 1;

			while (i + p[i] < manacherStr.length() 
					&& i - p[i] >= 0
					&& manacherStr.charAt(i + p[i]) == manacherStr.charAt(i - p[i])) {
				p[i] = p[i] + 1;
			}
			// 更新右边界和中点
			if (r<p[i] + i - 1) {
				r = p[i] + i -1;
				id = i;
			}
			//更新max
			if (max < p[i]) {
				max = p[i] - 1;
			}
		}
           
        return max;

	}
	
	private static String manacherInit(String s) {
		StringBuffer sb = new StringBuffer();
		sb.append('#');
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			sb.append(c);
			sb.append('#');
		}
		return sb.toString();
	}
}
