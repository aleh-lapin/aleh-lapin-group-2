<?php
	@session_start();
	require($_SERVER["CONTEXT_DOCUMENT_ROOT"] . "backbone_basics/php-classes/_header.php");
	$con = new Connection();

	// получим номер страницы
	$page = 1; //  $_GET["page"];
	$id_bookmark = $_GET["id_bookmark"];

	$objTagList = new TagList();
	$objTagList->setPage( $page );
	$objTagList->setIdBookmark( $id_bookmark );
	
	echo $objTagList->toJSON();

	$con->Disconnect();
?>