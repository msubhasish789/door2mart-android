<?php
   $conn=mysqli_connect("http://185.27.134.10/","unaux_31636966","Sonu@2000");
   mysqli_select_db($conn,"unaux_31636966_door_to_mart");

    $cat_id=$_POST['cat_id'];

    $qry="SELECT product_id,category_id,product_name,product_image,product_price,product_quantity FROM `product_table` WHERE category_id=$cat_id;";
    $raw=mysqli_query($conn,$qry);

    if($raw!=null)
    {
        while($res=mysqli_fetch_array($raw))
        {
            $data[]=$res;
        }

        print(json_encode($data));
    }
    else
     echo json_encode($raw);
    
?>