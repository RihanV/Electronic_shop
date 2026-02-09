package backend.service;

import backend.dao.ProductDAO;
import backend.entity.Product;
import java.util.*;

/** Service layer for product operations and validation. */
public class ProductService {
  private final ProductDAO dao = new ProductDAO();

  /** Validates and creates a product. */
  public int add(Product p) throws Exception { validate(p); return dao.create(p); }
  /** Validates and updates a product. */
  public boolean update(Product p) throws Exception { if (p.getId()<=0) throw new IllegalArgumentException("Invalid product id"); validate(p); return dao.update(p); }
  /** Soft-deactivates a product. */
  public boolean deactivate(int id) throws Exception { return dao.deactivate(id); }
  /** Lists active products. */
  public List<Product> listActive() throws Exception { return dao.listActive(); }
  /** Searches active products by keyword. */
  public List<Product> searchActive(String q) throws Exception { return dao.searchActive(q==null?"":q.trim()); }

  /** Shared validation for product fields. */
  private void validate(Product p) {
    if (p==null) throw new IllegalArgumentException("Product is null");
    if (p.getName()==null || p.getName().trim().isEmpty()) throw new IllegalArgumentException("Product name required");
    if (p.getCategory()==null || p.getCategory().trim().isEmpty()) throw new IllegalArgumentException("Category required");
    if (p.getSupplierId()<=0) throw new IllegalArgumentException("Select supplier");
    if (p.getPrice()<0) throw new IllegalArgumentException("Price cannot be negative");
    if (p.getQuantity()<0) throw new IllegalArgumentException("Quantity cannot be negative");
  }
}
