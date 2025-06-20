package knowledge.hood.door2mart.Models;

public class PlaceOrderModel {
    String placeOrderResponse;

    public PlaceOrderModel(String placeOrderResponse) {
        this.placeOrderResponse = placeOrderResponse;
    }

    public String getPlaceOrderResponse() {
        return placeOrderResponse;
    }

    public void setPlaceOrderResponse(String placeOrderResponse) {
        this.placeOrderResponse = placeOrderResponse;
    }
}
