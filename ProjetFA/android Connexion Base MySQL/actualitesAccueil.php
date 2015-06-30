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

$result = mysqli_query($db->connection(),"SELECT DISTINCT TITRE_ACT, LIBELLE, a.NUM_ACT,c.NUM_GR,ID_FILI FROM actualites a
LEFT join concerne c on a.NUM_ACT = c.NUM_ACT
LEFT join groupes g on g.NUM_GR = c.NUM_GR
where LIBELLE IS NOT NULL AND ID_FILI=1") or die(mysql_error());
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    $response["valeurs"] = array();
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $ligne = array();
        $ligne["TITRE_ACT"] = utf8_encode($row["TITRE_ACT"]);
        $ligne["LIBELLE"] = utf8_encode($row["LIBELLE"]);
        $ligne["NUM_ACT"] = utf8_encode($row["NUM_ACT"]);
        $ligne["NUM_GR"] = utf8_encode($row["NUM_GR"]);
        $ligne["ID_FILI"] = utf8_encode($row["ID_FILI"]);
 
        // push single row into final response array
        array_push($response["valeurs"], $ligne);
    }
	$result = mysqli_query($db->connection(),"SELECT DISTINCT TITRE_ACT, LIBELLE, a.NUM_ACT,c.NUM_GR,ID_FILI FROM actualites a
										LEFT join concerne c on a.NUM_ACT = c.NUM_ACT
										LEFT join groupes g on g.NUM_GR = c.NUM_GR
										where LIBELLE IS NOT NULL AND ID_FILI!=1") or die(mysql_error());
	
	if (mysqli_num_rows($result) > 0) {
		while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $ligne = array();
        $ligne["TITRE_ACT"] = utf8_encode($row["TITRE_ACT"]);
        $ligne["LIBELLE"] = utf8_encode($row["LIBELLE"]);
        $ligne["NUM_ACT"] = utf8_encode($row["NUM_ACT"]);
        $ligne["NUM_GR"] = utf8_encode($row["NUM_GR"]);
        $ligne["ID_FILI"] = utf8_encode($row["ID_FILI"]);
 
        // push single row into final response array
        array_push($response["valeurs"], $ligne);
    }
		
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
