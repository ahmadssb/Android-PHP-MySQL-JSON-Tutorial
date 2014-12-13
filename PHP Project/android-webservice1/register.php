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

// في حال عدم وجود طلب POST 
// سنقوم بعرض فورم HTML  الموجود بآخر الكود
if(!empty($_POST)){

    // تأكد من أن المستخدم قام بإدخال جميع الحقول المطلوبة 
	// username , password, displayname  هي الحقول المطلوبة لتنفيذ هذا الكودdisplayname
    if(empty($_POST['username'])||empty($_POST['password'])||empty($_POST['displayname'])){
        
		// في حال عدم تعبئة أحد الحقول سنقوم بعرض رسالة تنبيه للمستخدم
		
		//  إنشاء بعض البيانات ليتم عرضها كـ JSON Object
		/*
		* الهدف من success   
		* التأكد من أن  عملية التسجيل تمت بنجاح ام لا
		* عند الحصول على القيمة صفر ، ذلك يعني لم تتحقق جميع الشروط المطلوبة لتسجيل الدخول 
		*/
		$response["success"] = 0; 
		
	    // الرسالة التي ستظهر للمستخدم أو المطور 
		// (حيث بإمكان المطور لاحقا وضع رسالة خاصة به كل ما يحتاجه فقط قيمة success)
		
        $response["message"] = "All Fields Required";
        
        //  نقوم بإيقاف السكريبت و نطبع رسالة JSON
        /* 
		* الدالة json_encode() 
		* نقوم هذه الدالة بتحويل قيمة response 
		* إلى JSON
        */
        die(json_encode($response));
    }
	
    // في حال عدم وجود أي خطأ سنقوم بتنفيذ الأسطر التالية
    
    // نتأكد من عدم وجود اسم مستخدم آخر في قاعدة البيانات باستخدام COUNT(*)
	// إذا كانت القيمة أكثر من 0 ذلك يعني وجود مستخدم آخر بنفس الإسم
    // ":user" فقط لحجز قيمة username  وتحويلها لنص قبل تنفيذ أمر sql
	// نقوم بعمل ذلك لحماية قاعدة البيانات من SQL injections
	
    $query = "SELECT COUNT(*) AS count 
				   FROM users 
				   WHERE 
				   user_username = :user";
    
    // نقوم بتحديث قيمة :user 
    $query_params = array(
        ':user' => $_POST['username']
    ); 
    
    
    
    // الآن نقوم بتنقيذ أمر SQL
    try{
        // يقوم هذا السطر بإعداد أمر SQL
        $stmt = $db->prepare($query);
		// يقوم هذا السطر بتنفيذ أمر SQL مع المتغيرات التي قمنا بإدخالها
        $result = $stmt->execute($query_params);
		
		// نقوم  الآن بحساب ناتج قيمة count 
		// PDO::FETCH_ASSOC
		// تقوم بإرجاع كل صف ناتج من الأمر كمصفوفة [column] => value
		while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
		$username_count = $row["count"];
		//print_r($row);
		}
			// الآن نتأكد ان كانت قيمة username_count
			// إذا كانت أكبر من 0  ذلك يعني وجود اسم مستخدم مسجل في قاعدة البيانات 
			if ($username_count > 0) {
			
				// نقوم بإعداد بيانات JSON 
				// ورسالة للمستخدم بإن اسم المستخدم متاح مسبقا
				$response["success"] = 0;
				$response["message"] = "That username is already taken. Please try again.";
				
				// نقوم بإيقاف السكريبت و نطبع رسالة JSON
				die(json_encode($response));
				
		}

		
    }catch(PDOException $ex){
        
		// في حال وجود خلل في من قاعدة البيانات 
		// نقوم باعداد بيانات JSON
		// ونعرض رسالة تنبيه للمستخدم
        $response["success"] = 0;
        $response["message"] = "Something went wrong. Please try again later";

		// نقوم بإيقاف السكريبت و نطبع رسالة JSON
        die(json_encode($response));
        
    }
   
	// إذا لم يحصل أي خلل حتى هذا السطر 
	// هذا يعني أنه بإمكانه إدخال بيانات المستخدم في قاعدة البيانات
	
    // الآن نقوم بإعداد أمر SQL  لإدخال بيانات المستخدم في قاعدة البيانات
	// :user, :pass نستخدم نفس الطريقة التي شرحتها بالأعلى لإدخال البيانات في قاعدة البيانات
    $query = "INSERT INTO 
					users (user_username, user_password, user_displayname) 
					VALUES 
					(:user, :pass, :displayname)";
    
    // نقوم بتشفير كلمة المرور 
    $encr_user_pass = md5($_POST['password']);
    
    
    // نقوم بتحديث قيمة المتغيرات :user, :pass ,:displayname
    $query_params = array(
        ':user' => $_POST['username'],
		// :pass قمنا بتحديثه بكلمة المرور المشفرة 
        ':pass' =>  $encr_user_pass,
		':displayname' => $_POST['displayname']
    );
    
    // نقوم بتنفيذ أمر SQL لإدخال اسم المستخدم و كلمة المرور في قاعدة البيانات 
    try {
        $stmt = $db->prepare($query);
        $result = $stmt->execute($query_params);
               
    } catch (PDOException $ex) {
       // في حال وجود خلل في من قاعدة البيانات 
		// نقوم باعداد بيانات JSON
		// ونعرض رسالة تنبيه للمستخدم
		
        $response["success"] = 0;
        $response["message"] = "The username is already in use, please try again later!";
		
		// نقوم بإيقاف السكريبت و نطبع رسالة JSON
        die(json_encode($response));
    }
	
	/*
	* إذا وصلنا إلى هذه السطر بدون أي مشاكل 
	* هذا يعني أنه تم ادخال اسم المسخدم وكلمة المرور بقاعدة البيانات بنجاح 
	*/
    
	// أخيرا سنقوم بتغيير قيمة success = 1
	// و رسالة تفيد بأنه تم ادخال البيانات بنجاح
    $response["success"] = 1;
    $response["message"] = "Username Successfully Added";
	
	// و أخيرا نعرض رسالة JSON  للمستخدم
    echo json_encode($response);
        
}else{
    ?>

<h1>Register</h1>
<form action="register.php" method="post">
    Username: <br/>
    <input type="text" name="username" placeholder="Username"/><br/>
    Password: <br/>
    <input name="password" type="password" placeholder="Password"/><br/>
    Display Name: <br/>
    <input type="text" name="displayname" placeholder="Display Name"/><br/>
    <input type="submit" value="Register User"/>
</form>
<?php
}

?>