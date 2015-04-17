<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Thumbnail Grid with Expanding Preview</title>
		<meta name="description" content="Thumbnail Grid with Expanding Preview" />
		<meta name="keywords" content="thumbnails, grid, preview, google image search, jquery, image grid, expanding, preview, portfolio" />
		<meta name="author" content="Codrops" />
		<link rel="shortcut icon" href="../favicon.ico"> 
		<link rel="stylesheet" type="text/css" href="css/default.css" />
		<link rel="stylesheet" type="text/css" href="css/component.css" />
		<script type="text/javascript" src="js/modernizr.custom.js"></script>
		
		
		
		
	</head>
	<body>
		<div class="container">	
			<!-- Codrops top bar -->
			<div class="codrops-top clearfix">
				<a href="demo/index.html"><strong>&laquo; Back to Search Page </strong></a>
			</div><!--/ Codrops top bar -->
			<header class="clearfix">
			<table>
			<tr><td>
			<img alt="demo/image/pittsburgh.png" src="demo/image/pittsburgh.png">
			</td>
			<td><h1>Flickr Image Search</h1>
			<p class="sub">Non-English to English Flickr Image Search</p>
			</td></tr>
			</table>
			<p class="term">Submit term:<?php echo $body; ?></p> <br/>
			<p class="term">Search term:<?php echo $text; ?></p>
			</header>
			<div class="main">				
			</div>
		</div><!-- /container -->
		
        <script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
	
		<script type="text/javascript" src="js/grid.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			Grid.init();

            $(".main").append('<ul id="og-grid" class="og-grid">');
            $.get("http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=fa39ee432208cad01d1a00b8e0a3a9e9&text=<?php echo $text; ?>&format=rest", function(data){       

            $(data).find("photo").each(function() { 
                
                var photoId = $(this).attr("id");
                var farmId = $(this).attr("farm");
                var serverId = $(this).attr("server");
                var photoOwner = $(this).attr("owner");
                var secretId = $(this).attr("secret");
                var title = $(this).attr("title");
                var smallpicture="http://farm"+farmId+".staticflickr.com/"+serverId+"/"+photoId+"_"+secretId+"_q.jpg";
                var largepicture="http://farm"+farmId+".staticflickr.com/"+serverId+"/"+photoId+"_"+secretId+"_z.jpg";
                var flickrLinks = "https://www.flickr.com/photos/"+ photoOwner + "/" + photoId ; 
                var $items = $('<li><a href="'+flickrLinks+'" data-largesrc="'+largepicture+'" data-title="'+title+'"data-discription="'+title+'"><img src="'+smallpicture+'" /></a></li>').appendTo("#og-grid");
            
               Grid.addItems( $items );
					
            });   

           

 });

			
		 });
    
		</script>
	
	</body>
</html>
