 <?php
$xml=simplexml_load_file('./Sample_Xml_File.xml');
$ar = array();
foreach($xml->children() as $child) {
	if (!in_array(trim($child['text']), $ar))
	{
 $ar[trim($child['text'])] = 'brand';
}
 if (!in_array(trim($child->Gender['text']), $ar))
 {
 	$ar[trim($child->Gender['text'])] = 'gender';
}
 if( !in_array(trim($child->Gender->Category['text']), $ar)) 
 {
 	$ar[trim($child->Gender->Category['text'])] = 'cat';
}
 if( !in_array(trim($child->Gender->Category->Style['text']), $ar))
 {
 	$ar[trim($child->Gender->Category->Style['text'])] = 'subcat';
}
 if( !in_array(trim($child->Gender->Category->Style->ModelType['text']), $ar))
 {
 	$ar[trim($child->Gender->Category->Style->ModelType['text'])] = 'type';
}
}
echo json_encode($ar);


?>