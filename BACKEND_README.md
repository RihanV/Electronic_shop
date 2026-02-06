# Backend (separate files)

This project includes **two styles** of backend separation:

1) **Classic clean architecture** (recommended): `backend/entity`, `backend/dao`, `backend/service`
2) **Per-page backend files** (your request): `backend/pages/*Backend.java`

If you want “different files for different pages”, use the classes in `backend/pages`.

## 1) Create DB + tables
Run `sql/schema.sql` in MySQL.

## 2) Configure DB connection
Edit `db.properties` (project root): db.url, db.user, db.password.
Or run with: `-Dapp.db.config=/full/path/db.properties`

## 3) Use backend from your JFrame (examples)

### Per-page backend example (recommended for your marking)

**Login.java**
```java
import backend.pages.LoginBackend;

private void doLogin() {
  try {
    boolean ok = new LoginBackend().authenticate(txtUser.getText(), txtPass.getText());
    if (ok) { new Dashboard().setVisible(true); dispose(); }
    else JOptionPane.showMessageDialog(this, "Invalid login");
  } catch (Exception ex) {
    JOptionPane.showMessageDialog(this, ex.getMessage());
  }
}
```

**AddProduct.java**
```java
import backend.pages.AddProductBackend;
import backend.entity.Product;

private void saveProduct() {
  try {
    Product p = new Product(0,
        txtName.getText(),
        cmbCategory.getSelectedItem().toString(),
        selectedSupplierId,
        Double.parseDouble(txtPrice.getText()),
        Integer.parseInt(txtQty.getText()),
        true);

    int newId = new AddProductBackend().addProduct(p);
    JOptionPane.showMessageDialog(this, "Saved! ID=" + newId);
  } catch (Exception ex) {
    JOptionPane.showMessageDialog(this, ex.getMessage());
  }
}
```

---

### Service/DAO style example (optional)

### Login (button click)
```java
import backend.service.AuthService;
import backend.entity.User;

private void doLogin() {
  try {
    User u = new AuthService().login(txtUser.getText(), txtPass.getText());
    if (u != null) { new stock().setVisible(true); dispose(); }
    else JOptionPane.showMessageDialog(this,"Invalid login");
  } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
}
```

### Add Product
```java
import backend.service.ProductService;
import backend.entity.Product;

private void saveProduct() {
  try {
    Product p = new Product(0, txtName.getText(), cmbCategory.getSelectedItem().toString(), selectedSupplierId, Double.parseDouble(txtPrice.getText()), Integer.parseInt(txtQty.getText()), true);
    new ProductService().add(p);
    JOptionPane.showMessageDialog(this,"Saved!");
  } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
}
```
