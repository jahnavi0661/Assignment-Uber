import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private ArrayList<User>   users;
  private ArrayList<Driver> drivers;

  private ArrayList<TMUberService> serviceRequests; 

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    users   = new ArrayList<User>();
    drivers = new ArrayList<Driver>();
    serviceRequests = new ArrayList<TMUberService>(); 
    
    TMUberRegistered.loadPreregisteredUsers(users);
    TMUberRegistered.loadPreregisteredDrivers(drivers);
    
    totalRevenue = 0;
  }

  // General string variable used to store an error message when something is invalid 
  // (e.g. user does not exist, invalid address etc.)  
  // The methods below will set this errMsg string and then return false
  String errMsg = null;

  public String getErrorMessage()
  {
    return errMsg;
  }
  
  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId)
  {
    // Fill in the code
    for (User user : users) {
      if (user.getAccountId().equals(accountId)) {
        return user;
      }
    }
    return null;
    
  }
  
  // Check for duplicate user
  private boolean userExists(User user)
  {
    // Fill in the code
    for (User existingUser : users) {
      if (existingUser.equals(user)) {
        return true;
      }
    }
    return false;
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
   // Fill in the code
   for (Driver existingDriver : drivers) {
    if (existingDriver.equals(driver)) {
      return true;
    }
  }
  return false;
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private boolean existingRequest(User user,String restaurant, String foodOrderId)
  {
    // Fill in the code
    for (TMUberService service : serviceRequests) {
      if (service instanceof TMUberDelivery) {
        TMUberDelivery delivery = (TMUberDelivery) service;
        if (delivery.getUser().equals(user) &&
            delivery.getRestaurant().equals(restaurant) &&
            delivery.getFoodOrderId().equals(foodOrderId)) {
            return true;
        }
    }
}

return false;
  }
  public boolean existingRequest(TMUberService req) {
    for (TMUberService service : serviceRequests) {
      if (service.equals(req)) {
        return true;
      }
    }
    return false;
  }

  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  // Return null if no available driver
  private Driver getAvailableDriver()
  {
    // Fill in the code
    for (Driver driver : drivers) {
      if (driver.getStatus() == Driver.Status.AVAILABLE) {
        return driver;
      }
    }
    return null;
  }

  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    System.out.println();
    
    for (int i = 0; i < users.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      users.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    // Fill in the code
    System.out.println();

    for (int i = 0; i < drivers.size(); i++) {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println();
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    // Fill in the code
    System.out.println();

    for (int i = 0; i < serviceRequests.size(); i++) {
      int index = i + 1;
      System.out.printf("%-2s.  ----------------------------------------------------------- ", index);
      serviceRequests.get(i).printInfo();
      System.out.println();
    }
  }

  // Add a new user to the system
  public boolean registerNewUser(String name, String address, double wallet)
  {
    // Fill in the code. Before creating a new user, check paramters for validity
    // See the assignment document for list of possible erros that might apply
    // Write the code like (for example):
    // if (address is *not* valid)
    // {
    //    set errMsg string variable to "Invalid Address "
    //    return false
    // }
    // If all parameter checks pass then create and add new user to array list users
    // Make sure you check if this user doesn't already exist!
    if (name == null || name.isEmpty()) {
      errMsg = "Invalid User Name";
      return false;
    }
    if (address == null || address.isEmpty()) { // Assuming there's a method to validate addresses
      errMsg = "Invalid User Address";
      return false;
    }
    if (wallet < 0) {
      errMsg = "Invalid Money in Wallet";
      return false;
    }
    String accountId = TMUberRegistered.generateUserAccountId(users);
    User newUser = new User(String.valueOf(accountId),name, address, wallet);
    if (userExists(newUser)) {
      errMsg = "User Already Exists in System";
      return false;
    }
    users.add(newUser);
    return true;
  }

  // Add a new driver to the system
  public boolean registerNewDriver(String name, String carModel, String carLicencePlate)
  {
    // Fill in the code - see the assignment document for error conditions
    // that might apply. See comments above in registerNewUser
    if (name == null || name.isEmpty()) {
      errMsg = "Invalid Driver Name";
      return false;
    }
    if (carModel == null || carModel.isEmpty()) {
      errMsg = "Invalid Car Model";
      return false;
    }
    if (carLicencePlate == null || carLicencePlate.isEmpty()) {
      errMsg = "Invalid Car Licence Plate";
      return false;
    }
    String accountId = TMUberRegistered.generateDriverId(drivers);
    Driver newDriver = new Driver(String.valueOf(accountId), name, carModel, carLicencePlate);
    if (driverExists(newDriver)) {
      errMsg = "Driver Already Exists in System";
      return false;
    }
    drivers.add(newDriver);
    driverId++;
    return true;
  }
  public String getUsernameFromId(String accountId)
  {
    for(User user : users){
          if(user.getAccountId().equals(accountId)){
              return user.getName();
    }
    
  }
  return null;
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public boolean requestRide(String accountId, String from, String to)
  {
    // Check for valid parameters
	// Use the account id to find the user object in the list of users
    // Get the distance for this ride
    // Note: distance must be > 1 city block!
    // Find an available driver
    // Create the TMUberRide object
    // Check if existing ride request for this user - only one ride request per user at a time!
    // Change driver status
    // Add the ride request to the list of requests
    // Increment the number of rides for this user
    
    // Check for valid parameters
    if (accountId == null || accountId.isEmpty()) {
      System.out.println("Invalid User Account");
      return false;
  }
  if (from == null || from.isEmpty() || to == null || to.isEmpty()) {
      System.out.println("Invalid Address");
      return false;
  }

  // Use the account id to find the user object in the list of users
  User user = getUser(accountId);
  if (user == null) {
      System.out.println("User Account Not Found");
      return false;
  }

  // Get the distance for this ride
  int distance = CityMap.getDistance(from, to);
  // Note: distance must be > 1 city block!
  if (distance <= 1) {
      System.out.println("Insufficient Travel Distance");
      return false;
  }
  double ridecost = getRideCost(distance);
  if(user.getWallet()<ridecost){
    errMsg = "Insufficient Funds";
    return false;
  }

  // Find an available driver
  Driver driver = getAvailableDriver();
  if (driver == null) {
      System.out.println("No Drivers Available");
      return false;
  }

  // Create the TMUberRide object
  TMUberRide ride = new TMUberRide(driver,from,to,user, distance, getRideCost(distance),1,false);

  
  // Check if existing ride request for this user - only one ride request per user at a time!
  if (existingRequest(ride)) {
      errMsg="User Already Has Ride Request";
      return false;
  }

  // Change driver status
  driver.setAvailable(false);

  // Add the ride request to the list of requests
  serviceRequests.add(ride);

  // Increment the number of rides for this user
  user.addRide();

  return true;
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public boolean requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {
    // See the comments above and use them as a guide
    // For deliveries, an existing delivery has the same user, restaurant and food order id
    // Increment the number of deliveries the user has had
    // Check for valid parameters
    if (accountId == null || accountId.isEmpty()) {
      System.out.println("Invalid User Account");
      return false;
  }
  if (from == null || from.isEmpty() || to == null || to.isEmpty()) {
      System.out.println("Invalid Address");
      return false;
  }
  if (restaurant == null || restaurant.isEmpty() || foodOrderId == null || foodOrderId.isEmpty()) {
      System.out.println("Invalid Restaurant or Food Order");
      return false;
  }

  // Use the account id to find the user object in the list of users
  User user = getUser(accountId);
  if (user == null) {
      System.out.println("User Account Not Found");
      return false;
  }

  // Get the distance for this delivery
  int distance = CityMap.getDistance(from, to);

  // Find an available driver
  Driver driver = getAvailableDriver();
  if (driver == null) {
      System.out.println("No Drivers Available");
      return false;
  }

  // Create the TMUberDelivery object
  TMUberDelivery delivery = new TMUberDelivery(driver, from, to,user, distance,getDeliveryCost(distance), restaurant, foodOrderId);

  // Check if existing delivery request for this user, restaurant, and food order id
  if (existingRequest(user, restaurant, foodOrderId)) {
      System.out.println("User Already Has Delivery Request at Restaurant with this Food Order");
      return false;
  }

  // Change driver status
  driver.setAvailable(false);

  // Add the delivery request to the list of requests
  serviceRequests.add(delivery);

  // Increment the number of deliveries for this user
  user.addDelivery();

  return true;
  }


  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public boolean cancelServiceRequest(int request)
  {
    // Check if valid request #
    // Remove request from list
    // Also decrement number of rides or number of deliveries for this user
    // since this ride/delivery wasn't completed
    // Check if valid request #
    int adjustedRequest = request - 1;
    if (adjustedRequest < 0 || adjustedRequest  >= serviceRequests.size()) {
      System.out.println("Invalid Request #");
      return false;
  }

  // Remove request from list
  TMUberService service = serviceRequests.remove(adjustedRequest);

  // Also decrement number of rides or number of deliveries for this user
  // since this ride/delivery wasn't completed
  if (service instanceof TMUberRide) {
      User user = ((TMUberRide) service).getUser();
      user.decrementRides();
  } else if (service instanceof TMUberDelivery) {
      service.getUser().decrementDeliveries();
  }

  // Change driver status
  service.getDriver().setAvailable(true);

  return true;
  }
  
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public boolean dropOff(int request)
  {
    // See above method for guidance
    // Get the cost for the service and add to total revenues
    // Pay the driver
    // Deduct driver fee from total revenues
    // Change driver status
    // Deduct cost of service from user
   // Check if valid request #
   int adjustedRequest = request - 1;
   if (adjustedRequest < 0 || adjustedRequest >= serviceRequests.size()) {
    System.out.println("Invalid Request #");
    return false;
}

// Get the service request
TMUberService service = serviceRequests.get(adjustedRequest);

// Get the cost for the service and add to total revenues
double cost = service.getCost();
totalRevenue += cost;

// Pay the driver
double driverFee = cost * PAYRATE;
service.getDriver().setWallet(driverFee);

// Deduct driver fee from total revenues
totalRevenue -= driverFee;

// Change driver status
service.getDriver().setAvailable(true);

// Deduct cost of service from user
User user = service.getUser();
if (user.getWallet() < cost) {
    System.out.println("Insufficient Funds");
    return false;
}
user.deductWallet(cost);

// Remove request from list
serviceRequests.remove(adjustedRequest);

return true;
  }


  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {
    Collections.sort(users, new NameComparator());
    listAllUsers();
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User>
  {
    @Override
    public int compare(User user1, User user2) {
        return user1.getName().compareTo(user2.getName());
    }
    
  }

  // Sort users by number amount in wallet
  // Then ist all users
  public void sortByWallet()
  {
    Collections.sort(users, new UserWalletComparator());
    listAllUsers();
  }
  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User>
  {
    @Override
    public int compare(User user1, User user2)  {
        return Double.compare(user1.getWallet(), user2.getWallet());
    }
  
  }

  // Sort trips (rides or deliveries) by distance
  // Then list all current service requests
  public void sortByDistance()
  {
    Collections.sort(serviceRequests, new ServiceDistanceComparator());
    listAllServiceRequests();
  }
  private class ServiceDistanceComparator implements Comparator<TMUberService> {
    @Override
    public int compare(TMUberService service1, TMUberService service2) {
        return Double.compare(service1.getDistance(), service2.getDistance());
    }
}
}
