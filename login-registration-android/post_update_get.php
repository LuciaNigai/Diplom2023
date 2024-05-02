<?php
$con=mysqli_connect("localhost", "root", "", "login_regiser");
if(!empty($_POST['post_id'])){
$post_id=$_POST['post_id'];
$post_id_int=(int)$post_id;
}
if (!$con){
echo "Connection_error". mysqli_connect_error();}
$stmt = $con->prepare("select post_category, post_state, post_description, post_photo, post_id from post_info where post_id='".$post_id_int."'");
$stmt->execute();
$stmt->bind_result( $category, $state, $postDescription, $postImage, $post_id);
$itemsJsonArray = array();

while($stmt->fetch()){
    $temp=array();
    $temp['post_category']=$category;
    $temp['post_state']=$state;
    $temp['post_description']=$postDescription;
    $temp['post_photo']=$postImage;
    $temp['post_id']=strval($post_id);
    array_push($itemsJsonArray,$temp);
}

echo json_encode($itemsJsonArray);
