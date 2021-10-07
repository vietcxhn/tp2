import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Word {
    private String word;
    private LinkedList<String> trigrams;
    private int nbOccurrence;
    private int levenshteinDistance;

    public Word(String word) {
        this.word = word;
        trigrams = new LinkedList<>();
        if (word.length() <= 3) trigrams.addLast(word);
        else {
            for (int i = 0; i <= word.length() - 3; i++) {
                trigrams.addLast(word.substring(i, i + 3));
            }
        }
    }

    Word copy () {
        return new Word(word);
    }

    public String toString() {
        return word;
    }

    public LinkedList<String> getTrigrams() {
        return trigrams;
    }

    public boolean isContainTrigram (String trigram) {
        return trigrams.contains(trigram);
    }

    public void setNbOccurrence(int nbOccurrence) {
        this.nbOccurrence = nbOccurrence;
    }

    public int getNbOccurrence() {
        return nbOccurrence;
    }

    public void setLevenshteinDistance(int levenshteinDistance) {
        this.levenshteinDistance = levenshteinDistance;
    }

    public int getLevenshteinDistance() {
        return levenshteinDistance;
    }

    public static int levenshtein(Word word1, Word word2) {
        int[][] d = new int[word2.word.length() + 1][word1.word.length() + 1];
        for (int i = 0; i < d[0].length; i++) {
            d[0][i] = i;
        }
        for (int j = 0; j < d.length; j++) {
            d[j][0] = j;
        }

        for (int j = 1; j < d.length; j++) {
            for (int i = 1; i < d[j].length; i++) {
                int substitutionCost;
                if (word1.word.charAt(i - 1) == word2.word.charAt(j - 1)) substitutionCost = 0;
                else substitutionCost = 1;

                d[j][i] = Math.min(d[j - 1][i] + 1, Math.min(d[j][i - 1] + 1, d[j - 1][i - 1] +substitutionCost));
            }
        }
        return d[word2.word.length()][word1.word.length()];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, trigrams);
    }
}
