import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Demo {
    static String testWord = "ABABBAABAA";


    public static void main(String[] args) {


        for (String i : getSuffixes(testWord)) {
            System.out.println(i);
        }


    }

    private static String getLRS(String string){
        HashMap<Integer, String> mapList = new HashMap<>();
        String[] suffixes = getSuffixes(string);
        int[] lcpArray = getLCPArray(suffixes);

        for (int i = 0; i < lcpArray.length; i++) {
            mapList.put(lcpArray[i], suffixes[i]);
        }

        System.out.println(mapList);

        //get max entry
        Map.Entry<Integer, String> maxEntry = null;
        for (Map.Entry<Integer, String> entry : mapList.entrySet()) {
            if (maxEntry == null || entry.getKey().compareTo(maxEntry.getKey()) > 0) {
                maxEntry = entry;
            }
        }

        return maxEntry.getValue().substring(0, maxEntry.getKey());
    }

    private static void getLargestIndex(Set<Integer> set) {
        int largest = set.iterator().next();
        System.out.println(largest);
    }


    private static int getUniqueSubstrings(String string) {
        int numberOfAllSubstrings = getNumberOfSubstrings(string);
        int lcpSum = getLCPsSum(getLCPArray(getSuffixes(string)));

        return numberOfAllSubstrings - lcpSum;
    }

    private static int getNumberOfSubstrings(String string) {
        int stringLength = string.length();
        return stringLength * (stringLength + 1) / 2;
    }

    //key(lcp) -- value()
    //

//    private static String


    private static int getLCPsSum(int[] lcpArray) {
        int lcpSum = 0;
        for (int i : lcpArray) {
            lcpSum += i;
        }

        return lcpSum;
    }

    private static int[] getLCPArray(String[] suffixes) {
        int[] lcpArray = new int[suffixes.length];
        for (int i = 1; i < suffixes.length; i++) {
            //index zero will be 0 by default
            lcpArray[i] = getLCP(suffixes[i - 1], suffixes[i]);
        }
        return lcpArray;
    }

    //AB
    //ABBABAB
    //May be constructed in O(nlog(n)) or O(n)
    private static int getLCP(String firstSuffix, String secondSuffix) {
        int counter = 0;
        char[] firstAsArray = firstSuffix.toCharArray();
        char[] secondAsArray = secondSuffix.toCharArray();
        for (int i = 0; i < firstAsArray.length; i++) {
            for (int j = 0; j < secondAsArray.length; j++) {
                if (i == j && firstAsArray[i] == secondAsArray[j])
                    counter++;
            }
        }
        return counter;
    }


    private static String[] getSuffixes(String string) {
        String[] suffixes = new String[string.length()];
        char[] stringAsArray = string.toCharArray();

        for (int i = stringAsArray.length - 1, j = 0; i >= 0; i--, j++) {
            suffixes[j] = string.substring(i);
        }

        Arrays.sort(suffixes);

        return suffixes;

    }

    static int gcd(int firstNumber, int secondNumber) {
        int max = Math.max(firstNumber, secondNumber);
        int min = Math.min(firstNumber, secondNumber);
        for (int i = min; i > 1; i--) {
            if (max % i == 0 && min % i == 0) return i;
        }
        return 1;
    }

}

