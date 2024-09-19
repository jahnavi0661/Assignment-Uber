import java.util.Arrays;
import java.util.Scanner;

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap
{
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()
  // Other solutions are possible - you may replace this code if you wish
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    // Fill in the code
    // Make use of the helper methods above if you wish
    // There are quite a few error conditions to check for 
    // e.g. number of parts != 3
    String[] parts = getParts(address);

    // Check if the number of parts is exactly 3
    if (parts.length != 3) {
      return false;
  }
  // Check if the first part (residence number) consists of exactly 2 digits
  if (!allDigits(parts[0]) || parts[0].length() != 2) {
    return false;
}
// Check if the second part (street/avenue number) is valid
String secondPart = parts[1].toLowerCase();
if (!(secondPart.equals("1st") || secondPart.equals("2nd") || secondPart.equals("3rd") ||
      (secondPart.length() == 3 && secondPart.charAt(1) == 't' && secondPart.charAt(2) == 'h' &&
       Character.isDigit(secondPart.charAt(0)) && secondPart.charAt(0) != '0'))) {
    return false;
}
// Check if the third part (thoroughfare type) is either "Street" or "Avenue" (case insensitive)
String thirdPart = parts[2].toLowerCase();
if (!(thirdPart.equals("street") || thirdPart.equals("avenue"))) {
    return false;
}
    return true;
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address) {
    // Initialize the block array with default values indicating an error or invalid address
    int[] block = {-1, -1};

    // Use the getParts helper method to split the address into parts
    String[] parts = getParts(address);

    // Ensure the address has exactly 3 parts as per the rules
    if (parts.length != 3) {
        return block; // Return the default error values
    }

    // Extract the residence number and convert the first digit to the corresponding avenue or street number
    int firstDigit = Character.getNumericValue(parts[0].charAt(0));

    // Extract the street or avenue number from the second part of the address
    // Assuming the format is always correct as per the rules (e.g., "1st", "2nd", "3rd", "4th", etc.)
    int secondPartNumber;
    try {
        if (parts[1].length() == 3) { // For "nth" where n is 4-9
            secondPartNumber = Integer.parseInt(parts[1].substring(0, 1));
        } else { // For "1st", "2nd", "3rd"
            secondPartNumber = Integer.parseInt(parts[1].substring(0, 1));
        }
    } catch (NumberFormatException e) {
        return block; // Return the default error values if parsing fails
    }

    // Determine if the address is for a Street or an Avenue and assign the block coordinates accordingly
    if (parts[2].equalsIgnoreCase("Street")) {
        block[0] = firstDigit; // Avenue is determined by the first digit of the residence number
        block[1] = secondPartNumber; // Street is explicitly given
    } else if (parts[2].equalsIgnoreCase("Avenue")) {
        block[0] = secondPartNumber; // Avenue is explicitly given
        block[1] = firstDigit; // Street is determined by the first digit of the residence number
    }

    return block;
}
  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  // Hint: be careful not to generate negative distances
  
  // This skeleton version generates a random distance
  // If you do not want to attempt this method, you may use this default code
  public static int getDistance(String from, String to)
  {
    // Fill in the code or use this default code below. If you use
    // the default code then you are not eligible for any marks for this part
    
    // Math.random() generates random number from 0.0 to 0.999
    // Hence, Math.random()*17 will be from 0.0 to 16.999
   //  double doubleRandomNumber = Math.random() * 17;
    // cast the double to whole number
   // int randomNumber = (int)doubleRandomNumber;
   // return (randomNumber);

    if (!validAddress(from) || !validAddress(to)) {
      return -1; // Return -1 or some error code if either address is invalid
    }
  
    int[] fromBlock = getCityBlock(from);
    int[] toBlock = getCityBlock(to);
  
    // Calculate the city block distance
    int distance = Math.abs(fromBlock[0] - toBlock[0]) + Math.abs(fromBlock[1] - toBlock[1]);
    return distance;
   
  }
}
