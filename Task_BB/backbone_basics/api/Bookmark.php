<?php
	@session_start();
    require($_SERVER["CONTEXT_DOCUMENT_ROOT"] . "backbone_basics/php-classes/_header.php");
    $con = new Connection();

	$objBookmark = new Bookmark();

	// запрос модели
	if ($_SERVER["REQUEST_METHOD"] == "GET") {
		$id = substr($_SERVER["PATH_INFO"], 1);

		$objBookmark->set_id( $id );
		echo $objBookmark->toJSON();
	}

	// добавление или обновление модели 
	// если $obj-id = 0 => добавление
	// иначе обновление
	else if($_SERVER["REQUEST_METHOD"] == "POST") {
		
		if (!empty($_SERVER["HTTP_X_HTTP_METHOD_OVERRIDE"]) and ($_SERVER["HTTP_X_HTTP_METHOD_OVERRIDE"] == "DELETE")){
			$id = $_POST["id"];
			$objBookmark->set_id( $id );
			$objBookmark->delete();
		}
		else {
			$obj = json_decode($_POST["model"]);
			$objBookmark->set( $obj );
			$objBookmark->save();
			// возвращаем обратно
			echo $objBookmark->toJSON();
		}
	}

	$con->Disconnect();
?>