<?php
 
/*
 * Following code will list all the data in the db
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/connexion.php';
 
// connecting to db
$db = new CONNEXION_DB();
 /*SELECT TITRE_ACT, LIBELLE, a.NUM_ACT,NUM_GR FROM actualites a
LEFT join concerne c
on a.NUM_ACT = c.NUM_ACT
where LIBELLE IS NOT NULL*/
// get all infromation from etudiants table
//$result = mysqli_query($db->connection(),"SELECT nom,username,groupe FROM etudiants where nom='$valeur_nom' AND username='$valeur_username'") or die(mysql_error());
$result = mysqli_query($db->connection(),"SELECT TITRE_ACT, LIBELLE FROM actualites where LIBELLE IS NOT NULL") or die(mysql_error());
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    $response["valeurs"] = array();
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $ligne = array();
        $ligne["TITRE_ACT"] = utf8_encode($row["TITRE_ACT"]);
        $ligne["LIBELLE"] = utf8_encode($row["LIBELLE"]);
 
        // push single row into final response array
        array_push($response["valeurs"], $ligne);
    }
    // success
    $response["success"] = 1;
	
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No data found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>
