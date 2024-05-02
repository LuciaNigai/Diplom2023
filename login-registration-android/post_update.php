<?php
if(!empty($_POST['post_id']) && !empty($_POST['post_category']) && !empty($_POST['post_state']) && !empty($_POST['post_description']) && !empty($_POST['image'])){
    $con = mysqli_connect("localhost", "root","","login_regiser");
    $post_id=$_POST['post_id'];
    $post_id_int=(int)$post_id;
    $post_category = $_POST['post_category'];
    $post_state = $_POST['post_state'];
    $post_description=$_POST['post_description'];
        $image1='image'.date("d_m_Y").'_'.rand(1000,100000);
        $image = 'C:/Users/ungur/AndroidStudioProjects/Volunteer/app/src/main/res/drawable/'. $image1.'.png';
        if($con){
            if(file_put_contents($image,base64_decode($_POST['image'])))
            {
            $sql = "update post_info set post_category='".$post_category."', post_state='".$post_state."', 
            post_description='".$post_description."', post_photo='".$image1."' 
            where id in(select post_info.id from post_info LEFT JOIN users on post_info.id=users.id where users.apiKey<>'')
             and post_info.post_id='".$post_id."'";
                if(mysqli_query($con,$sql) && $con->affected_rows>0){
                    echo "success";
                } else echo "Registration Failed";
            } else echo 'Failed to upload image';
        }else echo "Database connection failed";
} else echo "All fields are required";