<?php
    session_start();
    $conn=mysqli_connect("http://185.27.134.10/","unaux_31636966","Sonu@2000");
    mysqli_select_db($conn,"unaux_31636966_door_to_mart");
    $name=$_POST['cat_name'];

    if($_FILES['cat_image'])
    {
        $sn= $_FILES['cat_image']['tmp_name'];  // find path source
        $an= $_FILES['cat_image']['name'];          //find original name
        $dn = "image/".$an;                     // destination
        move_uploaded_file($sn,$dn);

        $qry="INSERT INTO `category_table` (`category_id`, `category_name`, `category_image`, `category_status`) VALUES (NULL, '$name', '$an', '1')";
        $res=mysqli_query($conn,$qry);

        if($res==true)
        $_SESSION['msg1']="inserted succesfully";
        else
        $_SESSION['msg2']="Sorry something went wrong";

        header('location:add_category.php');
    }
?>