package backend.entity;

/**
 * Simple Product entity.
 */
public class Product {
  private int id;
  private String name;
  private String category;
  private int supplierId;
  private double costPrice;
  private double price;
  private int quantity;
  private int warrantyMonths;
  private boolean active;

  public Product() {}

  public Product(int id, String name, String category, int supplierId, double price, int quantity, boolean active) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.supplierId = supplierId;
    this.costPrice = 0.0;
    this.price = price;
    this.quantity = quantity;
    this.warrantyMonths = 0;
    this.active = active;
  }

  public Product(int id, String name, String category, int supplierId, double costPrice, double price, int quantity, int warrantyMonths, boolean active) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.supplierId = supplierId;
    this.costPrice = costPrice;
    this.price = price;
    this.quantity = quantity;
    this.warrantyMonths = warrantyMonths;
    this.active = active;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(int supplierId) {
    this.supplierId = supplierId;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public double getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(double costPrice) {
    this.costPrice = costPrice;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getWarrantyMonths() {
    return warrantyMonths;
  }

  public void setWarrantyMonths(int warrantyMonths) {
    this.warrantyMonths = warrantyMonths;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return name;
  }
}
