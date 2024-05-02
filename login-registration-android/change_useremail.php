<?php
if(!empty($_POST['email']) ){
    $email=$_POST['email'];
    $con = mysqli_connect("localhost", "root","","login_regiser");
        if($con){
            $sql = "update users set email='".$email."' where apiKey<>''";
                if(mysqli_query($con,$sql) && $con->affected_rows>0){
                    echo "success";
                } else echo "Updating Failed";
        }else echo "Database connection failed";
} else echo "All fields are required";