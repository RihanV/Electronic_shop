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
  private int warrantyMonths;
  private int warrantyId;

  public InvoiceItem() {}

  public InvoiceItem(int productId, String name, int quantity, double unitPrice, double total) {
    this.productId = productId;
    this.name = name;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.total = total;
    this.warrantyMonths = 0;
    this.warrantyId = 0;
  }

  public InvoiceItem(int productId, String name, int quantity, double unitPrice, double total, int warrantyMonths) {
    this.productId = productId;
    this.name = name;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.total = total;
    this.warrantyMonths = warrantyMonths;
    this.warrantyId = 0;
  }

  public InvoiceItem(int productId, String name, int quantity, double unitPrice, double total, int warrantyMonths, int warrantyId) {
    this.productId = productId;
    this.name = name;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.total = total;
    this.warrantyMonths = warrantyMonths;
    this.warrantyId = warrantyId;
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

  public int getWarrantyMonths() {
    return warrantyMonths;
  }

  public void setWarrantyMonths(int warrantyMonths) {
    this.warrantyMonths = warrantyMonths;
  }

  public int getWarrantyId() {
    return warrantyId;
  }

  public void setWarrantyId(int warrantyId) {
    this.warrantyId = warrantyId;
  }
}
