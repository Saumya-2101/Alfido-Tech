import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class wordCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the text:");
        String text = scanner.nextLine();

        String[] words = text.split("\\s+");
        int wordCount = words.length;
        int totalWordLength = 0;

        Map<String, Integer> wordFrequency = new HashMap<>();

        for (String word : words) {
            word = word.toLowerCase().replaceAll("[^a-zA-Z]", ""); // Normalize words
            totalWordLength += word.length();
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }

        double averageWordLength = (double) totalWordLength / wordCount;

        System.out.println("Total number of words: " + wordCount);
        System.out.println("Average word length: " + averageWordLength);
        System.out.println("Word frequencies:");

        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        scanner.close();
    }
}
