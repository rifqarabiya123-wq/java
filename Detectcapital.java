Public class Detectcapital {
    public boolean detectCapitalUse(String word) {
        int capCount = 0;

        for (int i = 0; i < word.length(); i++) {
            if (Character.isUpperCase(word.charAt(i))) {
                capCount++;
            }
        }

        if (capCount == word.length()) return true;

        if (capCount == 0) return true;

        if (capCount == 1 && Character.isUpperCase(word.charAt(0))) return true;

        return false;
    }
}