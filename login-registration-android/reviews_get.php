<?php
if(!empty($_POST['email'])){
$email=$_POST['email'];
}
$con=mysqli_connect("localhost", "root", "", "login_regiser");
if (!$con){
echo "Connection_error". mysqli_connect_error();}
$stmt = $con->prepare("select users.name, users.email, user_reviews.review, user_reviews.datetime,
user_reviews.review_id from user_reviews left join users on user_reviews.user_review_id=users.id
where user_reviews.user_id in(select DISTINCT user_reviews.user_id from user_reviews
left join users on user_reviews.user_id=users.id where users.email = '".$email."')");
$stmt->execute();
$stmt->bind_result($name, $email, $review, $dateTime, $reviewId);
$itemsJsonArray = array();

while($stmt->fetch()){
    $temp=array();
    $temp['name']=$name;
    $temp['email']=$email;
    $temp['review']=$review;
    $temp['dateTime']=$dateTime;
    $temp['reviewId']=$reviewId;
    array_push($itemsJsonArray,$temp);
}

echo json_encode($itemsJsonArray);