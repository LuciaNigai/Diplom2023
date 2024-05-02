<?php
if(!empty($_POST['review']) && !empty($_POST['review_id']) ){
$review=$_POST['review'];
$review_id=$_POST['review_id'];
$review_id_int=(int)$review_id;
$con = mysqli_connect("localhost", "root","","login_regiser");
  if($con){
            $sql = "update user_reviews set review='".$review."' where review_id='".$review_id_int."'
            and user_reviews.user_review_id in (select user_reviews.user_review_id from user_reviews
            left join users on user_reviews.user_review_id=users.id where users.apiKey>0)";
                if(mysqli_query($con,$sql) && $con->affected_rows>0){
                    echo "success";
                }
                else echo "Registration Failed";
        }else echo "Database connection failed";
} else echo "All fields are required";