<?php
if(!empty($_POST['image']) ){
    $image1='image'.date("d_m_Y").'_'.rand(1000,100000);
    $image = 'C:/Users/ungur/AndroidStudioProjects/Volunteer/app/src/main/res/drawable/'. $image1.'.png';
    $con = mysqli_connect("localhost", "root","","login_regiser");
 if($con){
            if(file_put_contents($image,base64_decode($_POST['image'])))
            {
            $sql = "update users set user_photo='".$image1."' where apiKey<>''";
               if(mysqli_query($con,$sql) && $con->affected_rows>0){
                                   echo "success";
                               } else echo "Updating Failed";
                               } else echo "Failed to upload image";
                       }else echo "Database connection failed";
               } else echo "All fields are required";