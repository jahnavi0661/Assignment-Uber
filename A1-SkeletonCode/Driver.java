/*
 * 
 * This class simulates a car driver in a simple uber app 
 * 
 * Everything has been done for you except the equals() method
 */
public class Driver
{
  private String id;
  private String name;
  private String carModel;
  private String licensePlate;
  private double wallet;
  private String type;
  
  public static enum Status {AVAILABLE, DRIVING};
  private Status status;
    
  
  public Driver(String id, String name, String carModel, String licensePlate)
  {
    this.id = id;
    this.name = name;
    this.carModel = carModel;
    this.licensePlate = licensePlate;
    this.status = Status.AVAILABLE;
    this.wallet = 0;
    this.type = "";
  }
  // Print Information about a driver
  public void printInfo()
  {
    System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f", 
                      id, name, carModel, licensePlate, wallet);
  }
  // Getters and Setters
  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getCarModel()
  {
    return carModel;
  }
  public void setCarModel(String carModel)
  {
    this.carModel = carModel;
  }
  public String getLicensePlate()
  {
    return licensePlate;
  }
  public void setLicensePlate(String licensePlate)
  {
    this.licensePlate = licensePlate;
  }
  public Status getStatus()
  {
    return status;
  }
  public void setStatus(Status status)
  {
    this.status = status;
  }
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(double wallet)
  {
    this.wallet = wallet;
  }
  /*
   * Two drivers are equal if they have the same name and license plates.
   * This method is overriding the inherited method in superclass Object
   * 
   * Fill in the code 
   */
  @Override
  public boolean equals(Object other)
  {
    // Check if the other object is a reference to this object
    if (this == other) {
      return true;
  }
  
  // Check if the other object is an instance of Driver
  if (!(other instanceof Driver)) {
      return false;
  }
  
  // Cast the other object to a Driver
  Driver otherDriver = (Driver) other;
  
  // Check if the name, license plate, and car model are equal
  return this.name.equals(otherDriver.name) &&
         this.licensePlate.equals(otherDriver.licensePlate) &&
         this.carModel.equals(otherDriver.carModel);
  }
  
  // A driver earns a fee for every ride or delivery
  public void pay(double fee)
  {
    wallet += fee;
  }
  public boolean isAvailabel()
  {
    return status == Status.AVAILABLE;
  }
  public void setAvailable(boolean available)
  {
    if(available)
    {
      this.status=Status.AVAILABLE;
    }
    else{
      this.status = Status.DRIVING;
    }
  }
}
