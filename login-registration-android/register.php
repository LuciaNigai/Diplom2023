<?php
if(!empty($_POST['name']) && !empty($_POST['email']) && !empty($_POST['password']) && !empty($_POST['image'])){
    $con = mysqli_connect("localhost", "root","","login_regiser");
    $name = $_POST['name'];
    $email = $_POST['email'];
    $image1='image'.date("d_m_Y").'_'.rand(1000,100000);
    $image = 'C:/Users/ungur/AndroidStudioProjects/Volunteer/app/src/main/res/drawable/'. $image1.'.png';
    $password = password_hash($_POST['password'], PASSWORD_DEFAULT);
        if($con){
            if(file_put_contents($image,base64_decode($_POST['image'])))
            {
            $sql = "insert into users (name, email, password, user_photo) values ('".$name."', '".$email."', '".$password."','".$image1."')";
                if(mysqli_query($con,$sql)){
                    echo "Success";
                } else echo "Registration Failed";
            } else echo 'Failed to upload image';
        }else echo "Database connection failed";
} else echo "All fields are required";
