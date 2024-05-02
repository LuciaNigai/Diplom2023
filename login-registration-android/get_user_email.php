<?php
    $con=mysqli_connect("localhost", "root", "", "login_regiser");
    if (!$con){
    echo "Connection_error". mysqli_connect_error();}
    $stmt = $con->prepare("select email from users where apiKey<>''");
    $stmt->execute();
    $stmt->bind_result($email);
    $itemsJsonArray = array();

    while($stmt->fetch()){
        $temp=array();

        $temp['email']=$email;
        array_push($itemsJsonArray,$temp);
    }

    echo json_encode($itemsJsonArray);