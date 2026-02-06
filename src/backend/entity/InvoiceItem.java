package backend.entity;

/**
 * Simple Invoice item entity.
 */
public class InvoiceItem {
  private int productId;
  private String name;
  private int quantity;
  private double unitPrice;
  private double total;

  public InvoiceItem() {}

  public InvoiceItem(int productId, String name, int quantity, double unitPrice, double total) {
    this.productId = productId;
    this.name = name;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.total = total;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(double unitPrice) {
    this.unitPrice = unitPrice;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }
}
