<?php
$con=mysqli_connect("localhost", "root", "", "login_regiser");
if (!$con){
echo "Connection_error". mysqli_connect_error();}
$stmt = $con->prepare("select image from images_try");
$stmt->execute();
$stmt->bind_result($image);
$itemsJsonArray = array();

while($stmt->fetch()){
    $temp=array();
    $temp['image']=$image;
    array_push($itemsJsonArray,$temp);
}
echo json_encode($itemsJsonArray);