Public class Palindromenumber {
    public boolean isPalindrome(int x) {
    
      if(x < 0){
        return false;
      }  

      int original = x;
      int reversed = 0;

      while ( x!=0 ){

        int digit = x % 10;
        x/= 10;
        reversed = ( reversed * 10 ) + digit ;
        

      }
      return original == reversed ;
    }
}