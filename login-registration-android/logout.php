<?php
if(!empty($_POST['email'])) {
    $email=$_POST['email'];
    $con=mysqli_connect("localhost", "root", "", "login_regiser");
    if($con){
        $sql="select * from users where  email='".$email."' and apiKey<>''";
        $res=mysqli_query($con, $sql);
        if(mysqli_num_rows($res) != 0) {
            $row=mysqli_fetch_assoc($res);
            $sqlUpdate="update users set apiKey='' where email ='".$email."'";
            if(mysqli_query($con, $sqlUpdate)){
                echo "success";
            }else echo "Logout failed";
        }else echo "Unauthorised access";
    }else echo "Database connection failed";
}else echo "All fields are required";