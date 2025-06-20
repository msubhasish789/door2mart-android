package knowledge.hood.door2mart.Models;

public class CartResponseModel {
    String cart_pId, cart_pname, cart_pquantity, cart_pprice, cart_quantity;

    public CartResponseModel(String cart_pId, String cart_pname, String cart_pquantity, String cart_pprice, String cart_quantity) {
        this.cart_pId = cart_pId;
        this.cart_pname = cart_pname;
        this.cart_pquantity = cart_pquantity;
        this.cart_pprice = cart_pprice;
        this.cart_quantity = cart_quantity;
    }

    public String getCart_pId() {
        return cart_pId;
    }

    public void setCart_pId(String cart_pId) {
        this.cart_pId = cart_pId;
    }

    public String getCart_pname() {
        return cart_pname;
    }

    public void setCart_pname(String cart_pname) {
        this.cart_pname = cart_pname;
    }

    public String getCart_pquantity() {
        return cart_pquantity;
    }

    public void setCart_pquantity(String cart_pquantity) {
        this.cart_pquantity = cart_pquantity;
    }

    public String getCart_pprice() {
        return cart_pprice;
    }

    public void setCart_pprice(String cart_pprice) {
        this.cart_pprice = cart_pprice;
    }

    public String getCart_quantity() {
        return cart_quantity;
    }

    public void setCart_quantity(String cart_quantity) {
        this.cart_quantity = cart_quantity;
    }
}
