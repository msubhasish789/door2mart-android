<?php
   $conn=mysqli_connect("http://185.27.134.10/","unaux_31636966","Sonu@2000");
   mysqli_select_db($conn,"unaux_31636966_door_to_mart");
    
        $user_mobile = $_POST['user_mobile'];
        
        $qry="SELECT `user_id` FROM `user_table` WHERE `user_mobile`=$user_mobile;";
        $raw=mysqli_query($conn,$qry);
    

        if(mysqli_num_rows($raw)!=0)
        {
                
            $data=mysqli_fetch_array($raw);          
                     
            print(json_encode($data));
        }
        else{
            $qry2="INSERT INTO `user_table` (`user_id`, `user_mobile`, `user_name`, `user_address`, `user_pincode`, `satus`) 
                                                VALUES (NULL, $user_mobile, NULL, NULL, NULL, '1')";
            $raw2=mysqli_query($conn,$qry2);

            if($raw==true)
            {
            	$qry3="SELECT `user_id` FROM `user_table` WHERE `user_mobile`=$user_mobile;";
                $raw3=mysqli_query($conn,$qry3);
            
                if(mysqli_num_rows($raw3)!=0)
                {
                         $data2=mysqli_fetch_array($raw3);
                    print(json_encode($data2));
                }
            }

            
        }
?>