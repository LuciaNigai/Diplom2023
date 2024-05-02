<?php
if(!empty($_POST['post_category']) && !empty($_POST['post_state']) && !empty($_POST['post_description']) && !empty($_POST['image'])){
    $con = mysqli_connect("localhost", "root","","login_regiser");
    $post_category = $_POST['post_category'];
    $post_state = $_POST['post_state'];
    $post_description=$_POST['post_description'];
    $image1='image'.date("d_m_Y").'_'.rand(1000,100000);
    $image = 'C:/Users/ungur/AndroidStudioProjects/Volunteer/app/src/main/res/drawable/'. $image1.'.png';

        if($con){
            if(file_put_contents($image,base64_decode($_POST['image'])))
            {
            $sql = "insert into post_info (id, post_category, post_state, post_description,post_photo) 
            values ((select id from users where apiKey<>''),'".$post_category."', '".$post_state."', '".$post_description."','".$image1."')";
                if(mysqli_query($con,$sql) && $con->affected_rows>0){
                    echo "success";
                } else echo "Registration Failed";
            } else echo 'Failed to upload image';
        }else echo "Database connection failed";
} else echo "All fields are required";