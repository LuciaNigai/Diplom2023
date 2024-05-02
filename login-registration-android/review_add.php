<?php
if(!empty($_POST['review']) && !empty($_POST['email'])){
$review=$_POST['review'];
$email = $_POST['email'];
$con = mysqli_connect("localhost", "root","","login_regiser");
  if($con){
            $sql = "insert into user_reviews (user_id, user_review_id, review) values
                                                    ((select users.id from users where users.email='".$email."'),
                                                    (select id from users where apiKey>0 and email<>'".$email."'), '".$review."')";
                if(mysqli_query($con,$sql) && $con->affected_rows>0){
                    echo "success";
                }
                else echo "Registration Failed";
        }else echo "Database connection failed";
} else echo "All fields are required";