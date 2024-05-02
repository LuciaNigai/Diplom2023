<?php
if(!empty($_POST['review_id'])){
$review_id=$_POST['review_id'];
$review_id_int=(int)$review_id;
}
$con=mysqli_connect("localhost", "root", "", "login_regiser");
if (!$con){
echo "Connection_error". mysqli_connect_error();}
$stmt = $con->prepare("select users.name, users.email from users left join user_reviews on users.id=user_reviews.user_review_id where user_reviews.review_id='".$review_id_int."'");
$stmt->execute();
$stmt->bind_result($name, $email);
$itemsJsonArray = array();

while($stmt->fetch()){
    $temp=array();
    $temp['name']=$name;
    $temp['email']=$email;
    array_push($itemsJsonArray,$temp);
}

echo json_encode($itemsJsonArray);