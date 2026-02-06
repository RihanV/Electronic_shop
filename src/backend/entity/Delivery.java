package backend.entity;

public class Delivery {
  private int id;
  private String customerName;
  private String customerPhone;
  private String address;
  private Integer invoiceId;
  private String status;

  public Delivery() {}

  public Delivery(int id, String customerName, String customerPhone, String address, Integer invoiceId, String status) {
    this.id = id;
    this.customerName = customerName;
    this.customerPhone = customerPhone;
    this.address = address;
    this.invoiceId = invoiceId;
    this.status = status;
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public String getCustomerName() { return customerName; }
  public void setCustomerName(String customerName) { this.customerName = customerName; }

  public String getCustomerPhone() { return customerPhone; }
  public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

  public String getAddress() { return address; }
  public void setAddress(String address) { this.address = address; }

  public Integer getInvoiceId() { return invoiceId; }
  public void setInvoiceId(Integer invoiceId) { this.invoiceId = invoiceId; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
}
