<?php
if(!empty($_POST['name']) ){
    $name=$_POST['name'];
    $con = mysqli_connect("localhost", "root","","login_regiser");
        if($con){
            $sql = "update users set name='".$name."' where apiKey<>''";
                if(mysqli_query($con,$sql) && $con->affected_rows>0){
                    echo "success";
                } else echo "Updating Failed";
        }else echo "Database connection failed";
} else echo "All fields are required";