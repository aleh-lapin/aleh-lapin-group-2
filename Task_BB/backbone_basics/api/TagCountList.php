<?php
	@session_start();
	require($_SERVER["CONTEXT_DOCUMENT_ROOT"] . "backbone_basics/php-classes/_header.php");
	$con = new Connection();

	// получим номер страницы
	$page = 1; //  $_GET["page"];

	$objTagCountList = new TagCountList();
	$objTagCountList->setPage( $page );
	
	echo $objTagCountList->toJSON();

	$con->Disconnect();
?>