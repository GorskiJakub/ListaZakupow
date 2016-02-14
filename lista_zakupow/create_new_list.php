<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['name']) && isset($POST['email'])) {
 
    $name = $_POST['name'];
    $email = $POST['email'];
    
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO shopping_list(name) VALUES('$name')");
    // getting id on new list
    $get_id = mysql_query("SELECT MAX(id) FROM `shopping_list`");
    
    if(!empty($get_id)){
    //check for empty result
    if(mysql_num_rows($get_id) > 0){
      $get_id = mysql_fetch_array($get_id);
      
      $list_id = array();
      $list_id["id"] = $get_id["id"];
    }
    }
    $get_user_id("SELECT uid FROM users WHERE users.email = '$email'");
    if(!empty($get_user_id)){
    //check for empty result
    if(mysql_num_rows($get_user_id) > 0){
      $get_user_id = mysql_fetch_array($get_user_id);
      
      $uid = array();
      $uid["uid"] = $uid["uid"];
    }
    }
    
    //inserting new row in list_of_lists
    $insert_connection = mysql("INSERT INTO list_of_lists(id_list, id_user) VALUES('$list_id', '$uid'");
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