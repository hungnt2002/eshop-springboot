package hungnt2004110032.service.cart;

import hungnt2004110032.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem {
	Product product;
	int qty;
	
	public double getAmount() { // thanh tien
		return qty * product.getPromotePrice(); // so luong nhan vs sp giam gia
	}
	
	/**
	 * tăng số lượng items
	 */
	public void increase() {
		this.qty++;
	}
}
