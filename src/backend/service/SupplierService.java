package backend.service;

import backend.dao.SupplierDAO;
import backend.entity.Supplier;
import java.util.*;

public class SupplierService {
  private final SupplierDAO dao = new SupplierDAO();

  public int add(Supplier s) throws Exception {
    validate(s);
    return dao.create(s);
  }
  public boolean deactivate(int id) throws Exception { return dao.deactivate(id); }
  public List<Supplier> listActive() throws Exception { return dao.listActive(); }
  public Supplier findByName(String name) throws Exception { return dao.findByName(name); }

  private void validate(Supplier s) {
    if (s==null) throw new IllegalArgumentException("Supplier is null");
    if (s.getName()==null || s.getName().trim().isEmpty()) throw new IllegalArgumentException("Supplier name required");
  }
}
