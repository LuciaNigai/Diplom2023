<?php
if(!empty($_POST['review_id']) ){
    $review_id=$_POST['review_id'];
    $review_id_int=(int)$review_id;
    $con = mysqli_connect("localhost", "root","","login_regiser");
        if($con){
            $sql = "delete from user_reviews where user_review_id in(select user_reviews.user_review_id from user_reviews
             left JOIN users on user_reviews.user_review_id=users.id where users.apiKey>0) 
             and user_reviews.review_id='".$review_id_int."'";
                if(mysqli_query($con,$sql) && $con->affected_rows>0){
                    echo "success";
                } else echo "Deleting Failed";
        }else echo "Database connection failed";
} else echo "All fields are required";