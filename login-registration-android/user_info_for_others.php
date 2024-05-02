<?php
$con=mysqli_connect("localhost", "root", "", "login_regiser");

if(!empty($_POST['email']) ){
    $email=$_POST['email'];
    }
if (!$con){
echo "Connection_error". mysqli_connect_error();}
$stmt = $con->prepare("select name, email, user_photo from users where email='".$email."'");
$stmt->execute();
$stmt->bind_result($name, $email, $image);
$itemsJsonArray = array();

while($stmt->fetch()){
    $temp=array();
    $temp['name']=$name;
    $temp['email']=$email;
    $temp['user_photo']=$image;
    array_push($itemsJsonArray,$temp);
}

echo json_encode($itemsJsonArray);