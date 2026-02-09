package backend.entity;

/** Simple POJO representing a warranty record. */
public class WarrantyRecord {
  private int id;
  private int productId;
  private String productName;
  private Integer invoiceItemId;
  private int warrantyMonths;
  private String startDate;
  private String endDate;
  private String status;

  public WarrantyRecord() {}

  public WarrantyRecord(int id, int productId, Integer invoiceItemId, int warrantyMonths, String startDate, String endDate, String status) {
    this(id, productId, null, invoiceItemId, warrantyMonths, startDate, endDate, status);
  }

  public WarrantyRecord(int id, int productId, String productName, Integer invoiceItemId, int warrantyMonths, String startDate, String endDate, String status) {
    this.id = id;
    this.productId = productId;
    this.productName = productName;
    this.invoiceItemId = invoiceItemId;
    this.warrantyMonths = warrantyMonths;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public int getProductId() { return productId; }
  public void setProductId(int productId) { this.productId = productId; }

  public String getProductName() { return productName; }
  public void setProductName(String productName) { this.productName = productName; }

  public Integer getInvoiceItemId() { return invoiceItemId; }
  public void setInvoiceItemId(Integer invoiceItemId) { this.invoiceItemId = invoiceItemId; }

  public int getWarrantyMonths() { return warrantyMonths; }
  public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }

  public String getStartDate() { return startDate; }
  public void setStartDate(String startDate) { this.startDate = startDate; }

  public String getEndDate() { return endDate; }
  public void setEndDate(String endDate) { this.endDate = endDate; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
}
