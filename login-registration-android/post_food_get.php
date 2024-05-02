<?php
$con=mysqli_connect("localhost", "root", "", "login_regiser");

if (!$con){
echo "Connection_error". mysqli_connect_error();}
$stmt = $con->prepare("select users.name, users.email, users.user_photo,  post_info.post_category, post_info.post_state, post_info.post_description, post_info.post_photo, post_info.post_date, post_info.post_id from users right join post_info using(id) where post_info.post_category='Food' order by post_info.post_date");
$stmt->execute();
$stmt->bind_result($name, $email, $image, $category, $state, $postDescription, $postImage, $dateTime, $post_id);
$itemsJsonArray = array();

while($stmt->fetch()){
    $temp=array();
    $temp['name']=$name;
    $temp['email']=$email;
    $temp['user_photo']=$image;
    $temp['post_category']=$category;
    $temp['post_state']=$state;
    $temp['post_description']=$postDescription;
    $temp['post_photo']=$postImage;
    $temp['post_date']=$dateTime;
    $temp['post_id']=strval($post_id);
    array_push($itemsJsonArray,$temp);
}

echo json_encode($itemsJsonArray);