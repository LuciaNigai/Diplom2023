<?php
if(!empty($_POST['post_id'])){
$post_id=$_POST['post_id'];
$post_id_int=(int)$post_id;
}
$con=mysqli_connect("localhost", "root", "", "login_regiser");
if (!$con){
echo "Connection_error". mysqli_connect_error();}
$stmt = $con->prepare("select users.name, users.email, post_comments.comment, post_comments.datetime, post_comments.comment_id
                       from users left join post_comments on users.id=post_comments.user_id
                       left join post_info on post_comments.post_id=post_info.post_id
                       where post_info.post_id='".$post_id_int."' order by post_comments.datetime");
$stmt->execute();
$stmt->bind_result($name, $email, $comment, $dateTime, $comment_id);
$itemsJsonArray = array();

while($stmt->fetch()){
    $temp=array();
    $temp['name']=$name;
    $temp['email']=$email;
    $temp['comment']=$comment;
    $temp['dateTime']=$dateTime;
    $temp['comment_id']=$comment_id;
    array_push($itemsJsonArray,$temp);
}

echo json_encode($itemsJsonArray);