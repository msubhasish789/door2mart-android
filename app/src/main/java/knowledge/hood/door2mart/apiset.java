package knowledge.hood.door2mart;

import java.util.ArrayList;
import java.util.List;

import knowledge.hood.door2mart.Models.CategoryResponseModel;
import knowledge.hood.door2mart.Models.OrderModel;
import knowledge.hood.door2mart.Models.PlaceOrderModel;
import knowledge.hood.door2mart.Models.ProductResponseModel;
import knowledge.hood.door2mart.Models.UserModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface apiset {

    @GET("category_fetch.php")
    Call<List<CategoryResponseModel>> getdata();

    @FormUrlEncoded
    @POST("allProduct_fetch.php")
    Call<List<ProductResponseModel>> getallProduct(@Field("product_name") String product_name);

    @FormUrlEncoded
    @POST("cat_product_fetch.php")
    Call<List<ProductResponseModel>> cat_pro_fetch(@Field("cat_id") String cat_id);

    @FormUrlEncoded
    @POST("product_fetch.php")
    Call<List<ProductResponseModel>> product_fetch(@Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("check_user_existance.php")
    Call<UserModel> userValidate(@Field("user_mobile") String user_mobile);

    @FormUrlEncoded
    @POST("add_user.php")
    Call<List<UserModel>> addUser(@Field("user_mobile") String user_mobile);
//      Call<List<UserModel>> addUser(@Field("user_id") String user_id,@Field("user_mobile") String user_mobile,@Field("user_name")  String user_name ,@Field("user_address")  String user_address,@Field("user_pincode") String user_pincode,@Field("status") String status);

    @FormUrlEncoded
    @POST("update_user.php")
    Call<List<UserModel>> updateUser(@Field("user_id") String user_id,@Field("user_name")  String user_name ,@Field("user_address")  String user_address,@Field("user_pincode") String user_pincode);

    @FormUrlEncoded
    @POST("getuser_details.php")
    Call<List<UserModel>> getUser_details(@Field("user_id") String user_id);
//    @FormUrlEncoded
//    @POST("get_order.php")
//    Call<List<OrderModel>> order_request(@Field(""))

//    @FormUrlEncoded
//    @POST("place_order.php")
//    Call<List<PlaceOrderModel>> place_order(@Field("user_id") String user_id, @Field("delivary_address") String delivary_address, @Field("bill_total") String bill_total, @Field("product_ids") String product_ids, @Field("product_qnt") String product_qnt);

    @FormUrlEncoded
    @POST("place_order.php")
    Call<List<OrderModel>> place_order(@Field("user_id") String user_id, @Field("delivary_address") String delivary_address, @Field("bill_total") String bill_total, @Field("product_ids") String product_ids, @Field("product_qnt") String product_qnt);

    @FormUrlEncoded
    @POST("getorder.php")
    Call<List<OrderModel>> getOrder(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("getorder_details.php")
    Call<List<OrderModel>> getOrder_details(@Field("order_id") String order_id);


    //@Field("user_id") String user_id, @Field("delivary_address") String delivary_address, @Field("bill_total") String bill_total, @Field("product_ids") String product_ids, @Field("product_qnt") String product_qnt

}

 