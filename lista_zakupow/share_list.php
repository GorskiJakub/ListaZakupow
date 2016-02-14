<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['email']) && isset($_POST['id_list'])) {
 
    $email = $_POST['email'];
    $id_list = $POST['id_list'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    $get_user_id = mysql_query("SELECT uid FROM users WHERE users.emil = '$email'")
    if(!empty($get_user_id)){
      if(mysql_num_rows($get_user_id) > 0) {
        $get_user_id = mysql_fetch_array($get_user_id);
        
        $uid = array();
        $uid["uid"] = $get_user_id["uid"];
      }
    }
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO list_of_list(id_list, id_user) VALUES('$id_list','$uid')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>