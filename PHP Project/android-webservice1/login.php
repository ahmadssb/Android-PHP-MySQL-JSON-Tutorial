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
 
require('config.php');

// في حال عدم وجود طلب POST 
// سنقوم بعرض فورم HTML  الموجود بآخر الكود
if(!empty($_POST)){
    
	// تأكد من أن المستخدم قام بإدخال جميع الحقول المطلوبة 
	// username , password  هي الحقول المطلوبة لتنفيذ هذا الكود
    if(empty($_POST['username'])||empty($_POST['password'])){
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
    
    // نقوم بكتابة أمر SQL للتأكد من وجود اسم المستخدم
    // ":user" فقط لحجز قيمة username  وتحويلها لنص قبل تنفيذ أمر sql
	// نقوم بعمل ذلك لحماية قاعدة البيانات من SQL injections
    
    $query = "
            SELECT * FROM `users` 
            WHERE
            user_username = :user
             ";
			 
	// نقوم بتحديث قيمة :user
    $query_params = array(
        ':user' => $_POST['username']
    );
	
	// الآن نقوم بتنقيذ أمر SQL
    try {
		// يقوم هذا السطر بإعداد أمر SQL
        $stmt = $db->prepare($query);
		
		// يقوم هذا السطر بتنفيذ أمر SQL مع المتغيرات التي قمنا بإدخالها
        $result = $stmt->execute($query_params);
    } catch (PDOException $ex) {
	
		// في حال وجود خلل في من قاعدة البيانات 
		// نقوم باعداد بيانات JSON
		// ونعرض رسالة تنبيه للمستخدم		
        $response['success'] = 0 ;
        $response['message'] = "Database Error1, Please try Again";
        die(json_encode($response));
    }
	
	/*
	* إذا لم يحصل أي خلل حتى هذا السطر 
	* ذلك يعني ان اسم المستخدم موجود في قاعدة البيانات
	*/
	
	// الآن علينا التأكد من كلمة المرور
	
	// أولا سنقوم بكتابة متغير is_login ليكون قيمته false 
	$is_login = false;
    
	// سنقوم بتشفير الباسوود المدخل بنفس طريقة التشفير المستخدمة في register.php
    $encr_user_pass = md5($_POST['password']);
    
	// سنقوم بجلب جميع الصفوف الناتجة من أمر SQL
	// في هذه الحالة سيكون  هناك صف واحد فقط لأنه لن يكون هناك اسم مستخدم مكرر
	$row = $stmt->fetch();
	
	// في حال وجود صفوف ناتجة من أمر SQL
    if ($row){
        // نقوم بمقارنة كلمة المرور المشفرة مع كلمة المرور الموجودة في قاعدة البيانات
        if($encr_user_pass === $row['user_password']){
			// في حال تطابق كلمة المرور
			// سنقوم بتغيير قيمة is_login الى true
            $is_login = true;
        }
    }
    

	/*
	* أخيرا إن كانت قيمة is_login = true
	* فهذا يعني أن اسم المستخدم و كلمة المرور صحيحة 
	* 
	*  غير ذلك فسنعرض رسالة خطأ للمستخدم
	*/
    if($is_login){
		// في حال تم تحقيق الشرط 
		// سنقوم بتغيير  success = 1
		// و رسالة تفيد بأنه تسجيل الدخول بنجاح
        $response["success"] = 1;
        $response["message"] = "Login Successful";
	
	// و أخيرا نعرض رسالة JSON  للمستخدم
        die(json_encode($response));
    }else{
		// في حال تم تحقيق الشرط 
		// سنقوم بتغيير  success = 0
		// و رسالة بأن اسم المستخدم أو كلمة المرور خاطئة
        $response["success"] = 0;
        $response["message"] = "username or password Incorrect";
		// و أخيرا نعرض رسالة JSON  للمستخدم
        die(json_encode($response));
    }
    
}else{
?>
<h1>Login</h1>
<form action="login.php" method="post">
    Username: <br/>
    <input type="text" name="username" placeholder="Username"/><br/>
    Password:<br/>
    <input type="password" name="password" placeholder="Password"/><br/>
    <input type="submit" value="Login"/>
    <a href="register.php">Register</a>
</form>
<?php
}
?>

