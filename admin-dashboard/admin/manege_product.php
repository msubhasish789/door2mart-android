<?php 
    $conn=mysqli_connect("http://185.27.134.10/","unaux_31636966","Sonu@2000");
    mysqli_select_db($conn,"unaux_31636966_door_to_mart");
    include ("header.php"); 
    
?>

<main>
<div class= "section1">
<div class="container text-center">
     <h1 class="heading-1">
         Manege Your Products
    </h1>
</div>
<div>
    <?php
        $qry="SELECT * FROM `product_table`";
        $data=mysqli_query($conn,$qry);
        while($arr=  mysqli_fetch_array($data))
        {
    ?>
    <br><br>

    <div class="container">
        <div class="row">
            <div class="col">
                <img src="image/<?php echo $arr['product_image'];?>" height="150px" width="150px"/> 
            </div>
            <div class="col-7">
                <div class="row"> 
                    <div class="col-6"><?php echo $arr["product_name"]; ?></div>
                    <div class="col">Product id : <?php echo $arr["product_id"]; ?></div>
                </div>
                <div>Qnty : <?php echo $arr["product_quantity"]; ?></div>
                <div>Product Description : <br><?php echo $arr["product_description"]; ?></div>
            </div>
            <div class="col">
                Price <br/>
                <?php echo $arr["product_price"]; ?>
            </div>
        </div>
    </div>
    
    <?php } ?>
</div>
</div>
</main>

<?php include ("footer.php"); ?>