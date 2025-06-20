<?php
   $conn=mysqli_connect("http://185.27.134.10/","unaux_31636966","Sonu@2000");
   mysqli_select_db($conn,"unaux_31636966_door_to_mart");

    $product_id=$_POST['product_id'];

    $qry="SELECT * FROM `product_table` WHERE product_id=$product_id;";
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