package knowledge.hood.door2mart.Models;

public class CategoryResponseModel {
    String category_id, category_name, category_image, category_status;

    public CategoryResponseModel() {
    }

    public CategoryResponseModel(String category_id, String category_name, String category_image, String category_status) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_image = category_image;
        this.category_status = category_status;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getCategory_status() {
        return category_status;
    }

    public void setCategory_status(String category_status) {
        this.category_status = category_status;
    }
}
