<?php
	@session_start();
	require($_SERVER["CONTEXT_DOCUMENT_ROOT"] . "backbone_basics/php-classes/_header.php");
	$con = new Connection();

	// получим номер страницы
	$page = 1; //  $_GET["page"];
	if (empty($_GET["tag"])) {
		$tag = "";
	}
	else {
		$tag = $_GET["tag"];
	}
	$objBookmarkList = new BookmarkList();
	$objBookmarkList->setPage( $page );
	$objBookmarkList->setTag( $tag );
	
	echo $objBookmarkList->toJSON();

	$con->Disconnect();
?>