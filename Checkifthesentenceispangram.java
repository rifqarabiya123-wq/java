Public class Checkifthesentenceispangram {
    public boolean checkIfPangram(String sentence) {
        
        boolean[] letters = new boolean[26];
        
        // Mark each letter that appears
        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            letters[ch - 'a'] = true;
        }
        
        // Check if every letter is present
        for (int i = 0; i < 26; i++) {
            if (letters[i] == false) {
                return false;
            }
        }
        
        return true;
    }
}