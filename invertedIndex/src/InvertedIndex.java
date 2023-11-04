import java.util.*;

public class InvertedIndex {
    private HashMap<String, ArrayList<Integer>> index;

    public InvertedIndex() {
        index = new HashMap<>();
    }

    public void addDocument(int docId, String document) {
        String[] words = tokenize(document);
        for (int i = 0; i < words.length; i++) {
            if (!isStopWord(words[i])) {
                if (!index.containsKey(words[i])) {
                    index.put(words[i], new ArrayList<>());
                }
                index.get(words[i]).add(docId);
            }
        }
    }



    private String[] tokenize(String document) {
        return document.toLowerCase().split("[^a-zA-Z0-9]+");
    }

    private boolean isStopWord(String word) {
        HashSet<String> stopWords = new HashSet<>(Arrays.asList("the", "a", "of"));
        return stopWords.contains(word);
    }


    // Merge search for multiple terms using the operator "and"
    public List<Integer> mergeSearch(Set<String> query) {
        List<Integer> results = new ArrayList<>();
        boolean firstTerm = true;
        for (String term : query) {
            if (index.containsKey(term)) {
                if (firstTerm) {
                    results.addAll(index.get(term));
                    firstTerm = false;
                } else {
                    results.retainAll(index.get(term));
                }
            } else {
                return results; // Return empty result if any term is not found
            }
        }
        return results;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InvertedIndex index = new InvertedIndex();

        index.addDocument(1, "computer systems");
        index.addDocument(2, "information systems");
        index.addDocument(3, "information retrieval and computer systems");

        Set<String> query = new HashSet<>();

        System.out.println("Enter search terms separated by spaces");
        String[] terms = scanner.nextLine().toLowerCase().split(" and ");
        query.addAll(Arrays.asList(terms));

        List<Integer> results = index.mergeSearch(query);
        System.out.println("Documents : " + results);
    }
}