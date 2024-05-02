<?php
if(!empty($_POST['post_id']) && !empty($_POST['comment'])){
$post_id=$_POST['post_id'];
$post_id_int=(int)$post_id;
$comment = $_POST['comment'];
$post_id_int=(int)$post_id;
$con = mysqli_connect("localhost", "root","","login_regiser");
  if($con){
            $sql = "insert into post_comments (comment, user_id, post_id) values ('".$comment."',(select id from users where apiKey<>''), '".$post_id_int."');";
                if(mysqli_query($con,$sql)){
                    echo "success";
                } else echo "Registration Failed";
        }else echo "Database connection failed";
} else echo "All fields are required";

