<?php
    session_start();
    $conn=mysqli_connect("http://185.27.134.10/","unaux_31636966","Sonu@2000");
    mysqli_select_db($conn,"unaux_31636966_door_to_mart");
    $name=$_POST['prod_name'];
    $price=$_POST['prod_price'];

    $quantity1=$_POST['quantity'];
    $quantity2=$_POST['unit'];
    $quantity=$quantity1." ".$quantity2;
    $stock=$_POST['prod_stock'];
    $desc=$_POST['prod_desc'];
    $cat=$_POST['category'];


    if($_FILES['prod_image'])
    {
        $sn= $_FILES['prod_image']['tmp_name'];  // find path source
        $an= $cat."_".$name;          //find original name
        $dn = "image/".$an;                     // destination
        move_uploaded_file($sn,$dn);

        $qry="INSERT INTO `product_table` (`product_id`, `category_id`, `product_name`, `product_image`, `product_description`, `product_price`, `product_quantity`, `product_stock`) 
                    VALUES (NULL, '$cat', '$name', '$an', '$desc', '$price', '$quantity', '$stock')";
        
        
        $res=mysqli_query($conn,$qry);

        if($res==true)
        $_SESSION['msg1']="inserted succesfully";
        else
        $_SESSION['msg2']="Sorry something went wrong";

        header('location:add_product.php');
    }
?>