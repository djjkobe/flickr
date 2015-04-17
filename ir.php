<?php

include("./lib/ClassParser.php");
$parser = new Parser();

$body = $_REQUEST['query'];

if(strlen($body) < 1) $body = "test";

echo "Submit term: ".$body."<br>";

$G = $parser->postWeb("http://translate.google.com/", "hl=eb&ie=UTF-8&sl=auto&tl=en&text=".urlencode($body));
$content = strip_tags("<".$parser->find2find($G, "<span id=result_box ", "</span>"));

echo "Search term: ".$content."<br>";

$text = urlencode($content);
include("./photo.php");

/*

//old version of flickr connection. 

$flickr = "http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=6d6332c6f3951304df780b71aecccbbe&text=".urlencode($content)."&format=rest";
$xml = $parser->fetchWebNoProxy($flickr);

if($_REQUEST[xml]=="yes"){

   echo $xml;

}else{

   echo "api url: ".$flickr."<br>";

   $album = explode("<photo id=", $xml);
   array_shift($album);
   array_pop($album);
   foreach($album as $id => $c){

      $pid = $parser->find2find($c, "\"", "\" ");
      $user = $parser->find2find($c, "owner=\"", "\" ");
   
      echo "<iframe src=\"https://www.flickr.com/photos/$user/$pid/player/5ae28edaab\" height=\"357\" width=\"500\"  frameborder=\"0\" allowfullscreen webkitallowfullscreen mozallowfullscreen oallowfullscreen msallowfullscreen></iframe>";
   }


}
*/
