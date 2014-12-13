<?php
/***
 * @author Ahmed Saleh
 * Created by ahmadssb on 2014-12-13
 * Website: http://www.ahmadssb.com
 * Email: ahmad@ahmadssb.com
 * Facebook: https://www.facebook.com/ahmadssbblog
 * Twitter: https://twitter.com/ahmadssb
 * YouTube: http://www.youtube.com/user/ahmadssbblog
 */
 
require("config.php");

// نقوم بكتابة أمر لجلب جميع الحقول الموجودة في جدول users
$query = "SELECT * FROM users";

//  نقوم بتنفيذ أمر SQL
try {
    $stmt = $db->prepare($query);
    $result = @$stmt->execute($query_params);
} catch (PDOException $ex) {
	// في حال وجود خلل في من قاعدة البيانات 
	// نقوم باعداد بيانات JSON
	// ونعرض رسالة تنبيه للمستخدم		
    $response["success"] = 0;
    $response["message"] = "Database Error";
}

// سنقوم بجلب جميع الصفوف الناتجة من أمر SQL
$rows = $stmt->fetchAll();

// في حال وجود صفوف ناتجة من أمر SQL
if ($rows){
	//  في حال وجود صفوف ناتجة من أمر SQL
	// نقوم بتعبئة بيانات JSON
	// بداية بوضع قيمة success = 1 
	// ونضع رسالة في message
    $response["success"] = 1;
    $response["message"] = "Users Available";
	
	/*******************************************************************************
	 * بما أننا سنقوم بجلب جميع بيانات المستخديم في جدول users
	 * فذلك يعني أننا سنستخدم JSON Array
	 * حيث أن كل مصفوفة تحتوي على بيانات مستخدم واحد مثل id , username , displayname
	 * لذلك سنقوم بوضع users كاسم للمصفوفة 
	 * وبداخل كل مصفوفة سنقوم بوضع بيانات كل مستخدم 
	 *****************************************************************************/
	
	// أولا سنقوم بتسمية المصفوفة users 
    $response["users"] = array();
    
	// بعد ذلك سنقوم بعملية تكرار في قاعدة البيانات لجلب بيانات المستخدم التالية
	// user_id	-  user_username	-  user_displayname	
    foreach ($rows as $row){
		// أولا سنقوم بدمج جميع هذه البيانات  في مصفوفة فرعية بمتغير باسم single_user
        $single_user = array();
		
		// ثم لكل key في single_user
		// سنقوم بوضع value خاصة بها في قاعدة البيانات
		// لتظهر لاحقا في JSON بهذا الشكل 
		
        $single_user["id"] = $row["user_id"];
        $single_user["username"] = $row["user_username"];
        $single_user["displayname"] = $row["user_displayname"];
        
        // أخيرا سنقوم بدمج مصفوفة $single_user مع مصفوفة $response["users"]
		array_push($response["users"], $single_user);
    }
    
    // و أخيرا نعرض رسالة JSON  للمستخدم
    echo json_encode($response);
}else{
		// في حال عدم وجود مستخدمين في قاعدة البيانات 
		// سنقوم بتغيير  success = 0
		// و رسالة للمستخدم بعدم وجود مستخدمين 
    $response["success"] = 0;
    $response["message"] = "No Users Available";
	
	// و أخيرا نعرض رسالة JSON  للمستخدم
    die(json_encode($response));
}
?>