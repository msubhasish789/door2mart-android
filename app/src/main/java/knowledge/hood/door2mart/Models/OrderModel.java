package knowledge.hood.door2mart.Models;

public class OrderModel {
    String order_id, payment_status, order_status, product_name, product_image, product_price, product_quantity;
    String user_id,bill_total,delivery_addrs,product_ids, order_qnty, order_time;

    public OrderModel(String order_id, String payment_status, String order_status, String product_name, String product_image, String product_price, String product_quantity, String user_id, String bill_total, String delivery_addrs, String product_ids, String order_qnty, String order_time) {
        this.order_id = order_id;
        this.payment_status = payment_status;
        this.order_status = order_status;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_price = product_price;
        this.product_quantity = product_quantity;
        this.user_id = user_id;
        this.bill_total = bill_total;
        this.delivery_addrs = delivery_addrs;
        this.product_ids = product_ids;
        this.order_qnty = order_qnty;
        this.order_time = order_time;
    }

    public OrderModel() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBill_total() {
        return bill_total;
    }

    public void setBill_total(String bill_total) {
        this.bill_total = bill_total;
    }

    public String getDelivery_addrs() {
        return delivery_addrs;
    }

    public void setDelivery_addrs(String delivery_addrs) {
        this.delivery_addrs = delivery_addrs;
    }

    public String getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(String product_ids) {
        this.product_ids = product_ids;
    }

    public String getOrder_qnty() {
        return order_qnty;
    }

    public void setOrder_qnty(String order_qnty) {
        this.order_qnty = order_qnty;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }
}
