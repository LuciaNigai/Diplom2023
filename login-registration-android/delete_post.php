<?php
if(!empty($_POST['post_id']) ){
    $post_id=$_POST['post_id'];
    $post_id_int=(int)$post_id;
    $con = mysqli_connect("localhost", "root","","login_regiser");
        if($con){
            $sql = "delete from post_info where id in(select post_info.id from post_info 
            LEFT JOIN users on post_info.id=users.id where users.apiKey<>'') and post_info.post_id='".$post_id."'";
                if(mysqli_query($con,$sql) && $con->affected_rows>0){
                    echo "success";
                } else echo "Registration Failed";
        }else echo "Database connection failed";
} else echo "All fields are required";