package backend.service;

import backend.dao.SupplierDAO;
import backend.entity.Supplier;
import java.util.*;

/** Service layer for supplier operations and validation. */
public class SupplierService {
  private final SupplierDAO dao = new SupplierDAO();

  /** Validates and creates a supplier. */
  public int add(Supplier s) throws Exception {
    validate(s);
    return dao.create(s);
  }
  /** Soft-deactivates a supplier. */
  public boolean deactivate(int id) throws Exception { return dao.deactivate(id); }
  /** Lists active suppliers. */
  public List<Supplier> listActive() throws Exception { return dao.listActive(); }
  /** Finds a supplier by exact name. */
  public Supplier findByName(String name) throws Exception { return dao.findByName(name); }

  /** Shared validation for supplier fields. */
  private void validate(Supplier s) {
    if (s==null) throw new IllegalArgumentException("Supplier is null");
    if (s.getName()==null || s.getName().trim().isEmpty()) throw new IllegalArgumentException("Supplier name required");
  }
}
