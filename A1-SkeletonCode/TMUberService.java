/*
 * 
 * General class that simulates a ride or a delivery in a simple Uber app
 * 
 * This class is made abstract since we never create an object. We only create subclass objects. 
 * 
 * Implement the Comparable interface and compare two service requests based on the distance
 */
abstract public class TMUberService implements Comparable<TMUberService>
{
  private Driver driver;   
  private String from;
  private String to;
  private User user;
  private String type;  // Currently Ride or Delivery but other services could be added      
  private int distance; // Units are City Blocks
  private double cost;  // Cost of the service
  
  public TMUberService(User user, Driver driver, String from, String to, String type, int distance, double cost)
  {
    this.user = user;
    this.driver = driver;
    this.from = from;
    this.to = to;
    this.type = type;
    this.distance = distance;
    this.cost = cost;
   
    //this.distance = 0;
  }
  @Override
    public int compareTo(TMUberService other) {
        // Compare based on distance in ascending order
        return Integer.compare(this.distance, other.distance);
    }


  // Subclasses define their type (e.g. "RIDE" OR "DELIVERY") 
  abstract public String getServiceType();

  // Getters and Setters
  public Driver getDriver()
  {
    return driver;
  }
  public void setDriver(Driver driver)
  {
    this.driver = driver;
  }
  public String getFrom()
  {
    return from;
  }
  public void setFrom(String from)
  {
    this.from = from;
  }
  public String getTo()
  {
    return to;
  }
  public void setTo(String to)
  {
    this.to = to;
  }
  public User getUser()
  {
    return user;
  }
  public void setUser(User user)
  {
    this.user = user;
  }
  public int getDistance()
  {
    return distance;
  }
  public void setDistance(int distance)
  {
    this.distance = distance;
  }
  public double getCost()
  {
    return cost;
  }
  public void setCost(double cost)
  {
    this.cost = cost;
  }

  // Compare 2 service requests based on distance
  // Add the appropriate method
  
  // Check if 2 service requests are equal (this and other)
  // They are equal if its the same type and the same user
  // Make sure to check the type first
  @Override
    public boolean equals(Object other) {
        // Check if the other object is a reference to this object
        if (this == other) {
            return true;
        }
        
        // Check if the other object is an instance of TMUberService
        if (!(other instanceof TMUberService)) {
            return false;
        }
        
        // Cast the other object to a TMUberService
        TMUberService otherService = (TMUberService) other;
        
        // Check if the type and user are the same for both service requests
        // Assuming User class has properly overridden equals method
        return this.type.equals(otherService.type) && this.user.equals(otherService.user);
    }
  
  // Print Information 
  public void printInfo()
  {
    System.out.printf("\nType: %-9s From: %-15s To: %-15s", type, from, to);
    System.out.print("\nUser: ");
    user.printInfo();
    System.out.print("\nDriver: ");
    driver.printInfo();
  }
}
