<?php
if(!empty($_POST['email']) && !empty($_POST['password'])){
    $email=$_POST['email'];
    $password =$_POST['password'];
    $result = array();
    $con = mysqli_connect("localhost", "root","","login_regiser");
            if($con){
                $sql = "select * from users where email='".$email."'";
                $res=mysqli_query($con, $sql);
                if(mysqli_num_rows($res) != 0){
                    $row = mysqli_fetch_assoc($res);
                    if($email == $row['email'] && password_verify($password, $row['password'])){
                        try{
                            $apiKey = bin2hex(random_bytes(23));
                        }
                        catch(Exception $e){
                            $apiKey = bin2hex(uniqid($email, true));
                        }
                        $sqlUpdate = "update users set apiKey ='".$apiKey."' where email = '".$email."'";
                        if(mysqli_query($con,$sqlUpdate)){
                            $result=array("status"=> "success","message"=> "Login successful","name"=> $row['name'],"email"=> $row['email'], "password"=> $row['password'], "apiKey"=> $apiKey);
                        }else $result =array("status"=> "failed", "message"=>  "Login failed, try again");
                    }else $result=array("status"=> "failed", "message"=> "Retry with correct email and password");
            }else $result=array("status"=> "failed", "message"=> "Retry with correct email and password");
            }else $result=array("status"=> "failed", "message"=> "Database connection failed");
}else $result=array("status"=> "failed", "message"=> "All fields are required");

echo json_encode($result, JSON_PRETTY_PRINT);