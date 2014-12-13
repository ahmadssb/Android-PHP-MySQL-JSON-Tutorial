/***
 * @author Ahmed Saleh
 * Created by ahmadssb on 2014-12-13
 * Website: http://www.ahmadssb.com
 * Email: ahmad@ahmadssb.com
 * Facebook: https://www.facebook.com/ahmadssbblog
 * Twitter: https://twitter.com/ahmadssb
 * YouTube: http://www.youtube.com/user/ahmadssbblog
 */

package com.ahmaadssb.androidwebservices;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	private EditText user, pass;
	private Button login, register;

	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();

	// „‰ Œ·«· ‘»ﬂ… «·Ê«Ì ›«Ì
	private static String LOGIN_URL = "http://192.168.1.103/GoogleDrive/android-webservice1/login.php";

	// „‰ Œ·«· Android emulator
	// private static String LOGIN_URL = "http://10.0.2.2/GoogleDrive/android-webservice1/login.php";

	// „‰ Œ·«· —«»ÿ «·„Êﬁ⁄ ⁄·Ï «·«‰ —‰ 
	// private static String LOGIN_URL = "http://www.YOUR-DOMAIN.com/GoogleDrive/android-webservice1/login.php";

	/***
	 * ﬂ„« ›⁄·‰« ›Ì „·›«  PHP ﬁ„‰« »Ê÷⁄ „›« ÌÕ („ €Ì—« ) · JSON Ì›÷· √‰ ‰ﬁÊ„
	 * » ⁄—Ì›Â« ﬂ‹ static final ·√‰Â« ﬁÌ„ À«» … ”‰Õ «ÃÂ« ·«Õﬁ«
	 */
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_USERNAME = "username";
	private static final String TAG_PASSWORD = "password";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		user = (EditText) findViewById(R.id.edtUsername);
		pass = (EditText) findViewById(R.id.edtPassword);

		login = (Button) findViewById(R.id.btnLogin);

		register = (Button) findViewById(R.id.btnRegisterUser);

		login.setOnClickListener(this);

		register.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLogin:
			// ⁄‰œ «·÷€ÿ ⁄·Ï Login
			// ‰ﬁÊ„ » ›⁄Ì· ﬂ·«” AttemptLogin()
			new AttemptLogin().execute();
			break;
		case R.id.btnRegisterUser:
			Intent i = new Intent(MainActivity.this, RegisterActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}
	}

	/***
	 * 
	 * @author Ahmadssb ›Ì Â–« «·ﬂ·«” ”‰ﬁÊ„ »«·≈ ’«· »—«»ÿ  ”ÃÌ· «·œŒÊ· Ê‰ﬁÊ„
	 *         »≈÷«›… «·»«—«„Ì —“ «·„ÿ·Ê»… username , Password Ê„‰ À„ «·Õ’Ê· ⁄·Ï
	 *         JSON Response Ê√ŒÌ—« Õ”» ﬁÌ„… success ‰ﬁ—— „« ”‰ﬁÊ„ »⁄„·Â
	 * 
	 */

	class AttemptLogin extends AsyncTask<String, String, String> {

		boolean failure = false;

		// ‰ﬁÊ„ »⁄—÷ ProgressDialog
		// Õ Ï  ‰ ÂÌ Ã„Ì⁄ «·√Ê«„— «·„ÊÃÊœ… ›Ì doInBackground()
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("„Õ«Ê·…  ”ÃÌ· «·œŒÊ· ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			int success;
			// ‰ﬁÊ„ » ”ÃÌ· «·»Ì«‰«  «·„ÊÃÊœ… ›Ì ÕﬁÊ· username Ê password ﬂ‰’
			String username = user.getText().toString();
			String password = pass.getText().toString();
			try {
				// ‰ﬁÊ„ »»‰«¡ «·»«—«„Ì —“ «·„ÿ·Ê»… ·⁄„·Ì…  ”ÃÌ· «·œŒÊ·
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair(TAG_USERNAME, username));
				params.add(new BasicNameValuePair(TAG_PASSWORD, password));

				// ›ﬁÿ ·  »⁄ „« ÌÕ’· ›Ì LogCat
				Log.d("request!", "starting");

				// ‰ﬁÊ„ »«—”«· ÿ·» POST «·Ï —«»ÿ  ”ÃÌ· «·œŒÊ· „⁄ ≈÷«›… «·»«—«„Ì Ì—“
				// ··Õ’Ê· ⁄·Ï JSON Objects 
				JSONObject json = JSONParser.makeHttpRequest(LOGIN_URL, "POST",	params);

				// ›ﬁÿ ·  »⁄ „« ÌÕ’· ›Ì LogCat
				Log.d("Login attempt", json.toString());

				// ‰÷⁄ ﬁÌ„… success Ê«· Ì Õ’·‰« ⁄·ÌÂ« „‰ JSON
				// »œ«Œ· «·„ €Ì— success
				success = json.getInt(TAG_SUCCESS);

				/*
				 * ›Ì Õ«· ﬂ«‰  ﬁÌ„… success = 1 Ì⁄‰Ì –·ﬂ «‰Â  „ «· ”ÃÌ· »‰Ã«Õ
				 * √ﬂ » «·√„— «·–Ì  —Ìœ  ‰›Ì–Â ”Ê«¡ ‰ﬁ· «·„” Œœ„ «·Ï √ﬂ Ì›Ì Ì
				 * √Œ— √Ê «Ì ‘Ì  —ÌœÂ
				 * 
				 * ******************************** √„« ›Ì Õ«·… ﬁÌ„… success = 0
				 * Ì⁄‰Ì «‰Â  ÊÃœ „‘ﬂ·… ”Ê«¡ „‰ «·„” Œœ„ √Ê „‰ «·”Ì—›— «·„” ÷Ì›
				 * ÕÌ‰Â« √ﬂ » «·√„— «·–Ì  —ÌœÂ
				 */
				if (success == 1) {
					Log.d("Login Successful!", json.toString());

					Intent i = new Intent(MainActivity.this,
							UsersActivity.class);

					startActivity(i);
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(String file_url) {
			// ‰ﬁÊ„ »≈Œ›«¡ «·œ«Ì·ÊÃ »⁄œ «·«‰ Â«¡
			pDialog.dismiss();

			// ‰ﬁÊ„ »⁄—÷ «·—”«·… «·‰« Ã… „‰ œ«·… doInBackground()
			if (file_url != null) {
				Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG)
						.show();

			}

		}

	}

}