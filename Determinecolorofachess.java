Public class Determinecolorofachessboard {
    public boolean squareIsWhite(String coordinates) {
        // Column: convert letter 'a'..'h' → 1..8
        int col = coordinates.charAt(0) - 'a' + 1;
        // Row: convert char '1'..'8' → 1..8
        int row = coordinates.charAt(1) - '0';
        
        // Odd sum → white, Even sum → black
        return (col + row) % 2 == 1;
    }
}
