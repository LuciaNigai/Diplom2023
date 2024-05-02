<?php
if(!empty($_POST['email'])) {
    $email=$_POST['email'];
}
    $result=array();
    $con = mysqli_connect("localhost", "root","","login_regiser");
        $sql ="select COUNT(post_info.post_id) as count from post_info join users on post_info.id=users.id where users.email='".$email."'";
        $res = mysqli_query($con, $sql);
        $row=mysqli_fetch_assoc($res);
                $result=array($row['count']);
echo json_encode($res, JSON_PRETTY_PRINT);