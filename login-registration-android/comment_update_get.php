<?php
if(!empty($_POST['comment_id'])){
$comment_id=$_POST['comment_id'];
$comment_id_int=(int)$comment_id;
}
$con=mysqli_connect("localhost", "root", "", "login_regiser");
if (!$con){
    echo "Connection_error". mysqli_connect_error();
}
$sql = "select comment, comment_id from post_comments where user_id in(select post_comments.user_id from post_info 
LEFT JOIN users on post_comments.user_id=users.id where users.apiKey<>'' and post_comments.comment_id='".$comment_id_int."')";
$result = mysqli_query($con,$sql);
$rowcount = mysqli_num_rows($result);
if(mysqli_query($con,$sql) && $rowcount>0){
    $stmt = $con->prepare($sql);
    $stmt->execute();
    $stmt->bind_result($comment, $comment_id);
    $itemsJsonArray = array();

    while($stmt->fetch()){
        $temp=array();
        $temp['comment']=$comment;
        $temp['comment_id']=$comment_id;
        array_push($itemsJsonArray,$temp);
    }

    echo json_encode($itemsJsonArray);
}


