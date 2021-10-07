import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dictionary  {
    Map<Character, ArrayList<Word>> map = new HashMap<>();

    public Dictionary(String filename) {
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (!map.containsKey(line.charAt(0))) {
                    map.put(line.charAt(0), new ArrayList<>());
                }
                map.get(line.charAt(0)).add(new Word(line));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean test(Word word) {
        return map.get(word.toString().charAt(0)).contains(word);
    }

    public Word correctOrthography(String string) {
        Word word = new Word(string);
        if (test(word)) return word;

        List<Word> L = new ArrayList<>();
        for(ArrayList<Word> list : map.values()) {
            for (Word word1 : list) {
                int nbOccurrence = 0;
                for(String trigram : word1.getTrigrams()) {
                    if (word.isContainTrigram(trigram)) nbOccurrence++;
                }
                Word copy = word1.copy();
                copy.setNbOccurrence(nbOccurrence);
                if (nbOccurrence>0) L.add(copy);
            }
        }

        L.sort((o1, o2) -> o2.getNbOccurrence() - o1.getNbOccurrence());
        L = L.subList(0,20);
        L.forEach(word12 -> word12.setLevenshteinDistance(Word.levenshtein(word, word12)));

        L.sort(Comparator.comparingInt(Word::getLevenshteinDistance));

        return L.get(0);
    }

}
