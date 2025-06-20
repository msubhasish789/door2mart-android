<?php
    $conn=mysqli_connect("http://185.27.134.10/","unaux_31636966","Sonu@2000");
    mysqli_select_db($conn,"unaux_31636966_door_to_mart");
    $qry="SELECT * FROM `category_table`";
    $raw=mysqli_query($conn,$qry);

    while($res=mysqli_fetch_array($raw))
    {
        $data[]=$res;
    }

    print(json_encode($data));
?>