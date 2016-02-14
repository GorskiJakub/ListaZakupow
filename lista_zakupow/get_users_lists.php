<?php
 
/*
 * Following code will get all users lists
 * A list is identified by user email and uid
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET["email"])) {
    $email = $_GET['email'];
 
    // get a user from user table
    $result = mysql_query("SELECT * FROM shopping_list 
JOIN list_of_lists ON shopping_list.id = list_of_lists.id_list
JOIN users ON list_of_lists.id_user = users.uid
WHERE users.email = $email");
 
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $shopping_list = array();
            $shopping_list["id"] = $result["id"];
            $shopping_list["name"] = $result["name"];
           
            // success
            $response["success"] = 1;
 
            // list node
            $response["shopping_list"] = array();
 
            array_push($response["shopping_list"], $shopping_list);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no list found
            $response["success"] = 0;
            $response["message"] = "No list found";
 
            // echo no list JSON
            echo json_encode($response);
        }
    } else {
        // no list found
        $response["success"] = 0;
        $response["message"] = "No list found";
 
        // echo no lists JSON
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