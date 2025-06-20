<?php
    $conn=mysqli_connect("http://185.27.134.10/","unaux_31636966","Sonu@2000");
    mysqli_select_db($conn,"unaux_31636966_door_to_mart");
    session_start();
    include ("header.php");?>

<main>
        <div class="section1">
            <div class="container text-center">
                <h1 class="heading-1">
                    Add Your Products
                </h1>
                <div class="row">
                    <div class="col-md-8 mx-auto">
                        <form name="prod_add" action="pro-process.php" method="POST" enctype="multipart/form-data">
                            <div class="mb-3">
                            <label for="inputname" class="form-label" >Select Category Name</label>
                            <select name="category" class="form-control">
                                <?php 
                                    $qry= "select * from `category_table`";
                                    $data=mysqli_query($conn,$qry);
                                    while($arr= mysqli_fetch_array($data))
                                    {
                                ?>
                                <option selected disabled hidden>Choose a Category</option>
                                <option value="<?php echo $arr['category_id']?>"><?php echo $arr['category_name']?></option>

                                <?php } ?>
                            </select>
                            </div>
                            <div class="mb-3">
                            <label for="inputname" class="form-label" >Product Name</label>
                            <input type="text" name="prod_name" class="form-control"  placeholder="Enter Product Name">
                            </div>
                            <div class="mb-3">
                            <label for="prod_image" class="form-label">Product Image</label>
                            <input type="file" name="prod_image" class="form-control" placeholder="Upload Product image">
                            </div>
                            <div class="mb-3">
                            <label for="inputname" class="form-label" >Product Price</label>
                            <input type="text" name="prod_price" class="form-control"  placeholder="Enter Product Price">
                            </div>
                            <div class="row">                            
                                <div class="col-7">
                                    <label for="inputname" class="form-label" >Product Quantity</label>
                                    <input type="text" name="quantity" class="form-control"  placeholder="Product quantity">
                                </div>
                                <div class ="col">
                                    <label for="inputname" class="form-label" >Quantity</label>
                                    <select name="unit" class="form-control">
                                    <option selected disabled hidden>Choose Unit</option>
                                    <option value="pices">pices</option>
                                    <option value="kg">kg</option>
                                    <option value="gram">gram</option>
                                    <option value="L">L</option>
                                    <option value="ml">ml</option>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="mb-3">
                            <label for="inputname" class="form-label" >Product Stock</label>
                            <input type="text" name="prod_stock" class="form-control"  placeholder="Available in stock">
                            </div>
                            <div class="mb-3">
                            <label for="inputname" class="form-label" >Product Description</label>
                            <textarea name="prod_desc" class="form-control" row='5' placeholder="Enter Product Description"></textarea>
                            </div>
                            <br/>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </form>
                        <br/><br/><br/>
                        <?php 
                            if(isset($_SESSION['msg1']))
                                {
                        ?>
                        <div class="alert alert-success" role="alert">
                        <?php 
                            echo $_SESSION['msg1']; 
                            unset($_SESSION['msg1']);
                        ?>
                        </div>
                        <?php } ?>
                        <?php 
                            if(isset($_SESSION['msg2']))
                                {
                        ?>
                        <div class="alert alert-success" role="alert">
                        <?php 
                            echo $_SESSION['msg2']; 
                            unset($_SESSION['msg2']);
                        ?>
                        </div>
                        <?php } ?>

                    </div>
                </div>

            </div>

        </div>

</main>

<?php 
    include ("footer.php");
?>