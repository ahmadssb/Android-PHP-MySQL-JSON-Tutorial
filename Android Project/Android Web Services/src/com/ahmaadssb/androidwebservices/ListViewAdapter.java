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
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	// äŞæã ÈÊÚÑíİ ÇáãÊÛíÑÇÊ ÇáãØáæÈÉ
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	HashMap<String, String> resultp = new HashMap<String, String>();

	// ÊÚÑíİ ÇáßæäÓÊÑßÊÑ 
	// ÍíË íÍÊæí Úáì ÇáãÊÛíÑÇÊ 
	//context = ÇÓã ÇáßáÇÓ 
	// arraylist = ãÕİæİÉ users ÇáÊí ÓäÍÕá ÚáíåÇ ãä JSONArray
	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		// åäÇ ÓäÊÚÇãá ãÚ ÇáãÕİæİÉ ÇáÊí ÍÕáäÇ ÚáíåÇ ãä ÇáßæäÓÊÑßÊÑ áßí äÊÍßã ÈãÍÊæíÇÊåÇ æäÖÚåÇ İí single_user_layout
		data = arraylist;
	}

	@Override
	public int getCount() {
		// äÍÕá Úáì ÍÌã data 
		// æÇáÊí åí ÚÏÏ ãÕİæİÉ JSON
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/***
	 * İí åĞå ÇáÏÇáÉ ÓäŞæã ÈÇáÑÈØ ãÚ  single_user_layout
	 * æ äŞæã ÈÊÚÑíİ ßá ßÇÆä TextView 
	 * æ ãä Ëã äŞæã ÈÃÎĞ ÇáŞíã ÇáãæÌæÏÉ İí data
	 * æÑÈØåÇ ãÚ ßá TextView
	 */
	public View getView(final int position, View convertView, ViewGroup parent) {

		// ÊÚÑíİ ãÊÛíÑÇÊ TextView ÇáãØáæÈÉ
		TextView tvUserID, tvUsername, tvDisplayName;

		// äŞæã ÈÊÚÑíİ inflater 
		// LayoutInflater : ÊæÖíÍ ãÈÓØ
		// íÓãÍ áß ÈÊÍæíá Çí ßÇÆä (äÕ-ÒÑ-ÕæÑÉ...ÇáÎ) Çáì View Objects 
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// äŞæã ÈÊÚÑíİ itemView Object 
		// inflater.inflate (int resource, ViewGroup root, boolean attachToRoot)
		// resource = single_user_layout ÈãÚäì ÇáÊÕãíã ÇáĞí ÊæÏ ÇáÊÍßã ÈãÍÊæíÇÊå
		// ViewGroup root = parent äİÓ ÇáÈÇÑÇãíÊÑ ÇáãæÌæÏ İí getViwe() æ ÇáåÏİ ãäå ÇáÍÕæá Úáì Ãæá ÚäÕÑ İí ãáİ ÇáÊÕãíã
		View itemView = inflater.inflate(R.layout.single_user_layout, parent, false);

		// äÍÕá Úáì ãæÖÚ ÇáÏÇÊÇ
		// ÈãÚäì ÂÎÑ Çä ßÇä åäÇß 4 ãÓÊÎÏãíä ÍÕáäÇ Úáì ÈíÇäÇÊåã ãä JSON Array
		// ßá ãÓÊÎÏã áå ãÕİæİÉ ÊÍÊæí Úáì id - username - displayname 
		// åäÇ äÍä äÍÏÏ ãæÖÚ ÇáãÕİæİÉ ááãÓÊÎÏã
		// İÅĞÇ ßÇäÊ 2 ÓäÍÕá Úáì ÈíÇäÇÊ ÇáãÓÊÎÏã ÑŞã 2
		resultp = data.get(position);

		// äŞæã ÈÊÚíä ßá ãÊÛíÑ TextView ÈãÇ åæ ãæÌæÏ İí ãáİ single_user_layout
		tvUserID = (TextView) itemView.findViewById(R.id.txtID);
		tvUsername = (TextView) itemView.findViewById(R.id.txtUsername);
		tvDisplayName = (TextView) itemView.findViewById(R.id.txtDisplayName);

		// äŞæã ÈæÖÚ ÈíÇäÇÊ TextView İí ÃãÇßäåÇ
		tvUserID.setText(resultp.get(UsersActivity.TAG_USER_ID));
		tvUsername.setText(resultp.get(UsersActivity.TAG_USER_USERNAME));
		tvDisplayName.setText(resultp.get(UsersActivity.TAG_USER_DISPLAYNAME));

		// ÇÎíÑÇ äŞæã ÈÅÑÌÇÚ itemView 
		return itemView;
	}

}
