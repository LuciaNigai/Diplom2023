<?php
if(!empty($_POST['review_id'])){
$review_id=$_POST['review_id'];
$review_id_int=(int)$review_id;
}
$con=mysqli_connect("localhost", "root", "", "login_regiser");
if (!$con){
echo "Connection_error". mysqli_connect_error();}
$stmt = $con->prepare("select review from user_reviews where review_id='".$review_id_int."'
                       and user_reviews.user_review_id in (select user_reviews.user_review_id from user_reviews
                       left join users on user_reviews.user_review_id=users.id where users.apiKey<>'')");
$stmt->execute();
$stmt->bind_result($review);
$itemsJsonArray = array();

while($stmt->fetch()){
    $temp=array();
    $temp['review']=$review;
    array_push($itemsJsonArray,$temp);
}

echo json_encode($itemsJsonArray);