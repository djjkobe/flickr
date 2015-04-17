<?php 
                                                                          
class Parser{

   function find2find($str, $start, $end){
      $tmp = explode($start, $str);
      if(count($tmp)<2) return "";
      $tmp = explode($end, $tmp[1]);
      return $tmp[0];
   }
   
   function postWeb($url, $post){
      $resource = curl_init();
      //$proxy = "192.168.0.125:3128";
      //curl_setopt($resource, CURLOPT_PROXY, "$proxy");
      curl_setopt($resource, CURLOPT_TIMEOUT,10); 
      curl_setopt($resource, CURLOPT_URL, $url);
      curl_setopt($resource, CURLOPT_POST, 1);
      curl_setopt($resource, CURLOPT_POSTFIELDS, "$post");
      curl_setopt($resource, CURLOPT_FOLLOWLOCATION, 1);
      curl_setopt($resource, CURLOPT_RETURNTRANSFER, 1);
      $content = curl_exec($resource);      
      curl_close($resource);
      return $content;
   }
}
?>
