<?php
if(!empty($_POST['comment_id']) && !empty($_POST['comment']) ){
    $comment_id=$_POST['comment_id'];
    $comment=$_POST['comment'];
    $comment_id_int=(int)$comment_id;
    $con = mysqli_connect("localhost", "root","","login_regiser");
        if($con){
            $sql = "update post_comments set comment='".$comment."' where user_id in(select post_comments.user_id from post_info 
            LEFT JOIN users on post_comments.user_id=users.id where users.apiKey<>'' and post_comments.comment_id='".$comment_id_int."')";
                if(mysqli_query($con,$sql) && $con->affected_rows>0){
                    echo "success";
                } else echo "Updating Failed";
        }else echo "Database connection failed";
} else echo "All fields are required";