package cn.edu.bistu.tools;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

public class MyPopWindow {
	private PopupWindow popupWindow;
	private Context context;

	public MyPopWindow(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public void showPopWindow(int viewId, int listViewId, BaseAdapter adapter,
			OnItemClickListener clickListener, int width, View locationView,
			int offX) {
		if (popupWindow == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			final View view = layoutInflater.inflate(viewId, null);
			ListView listView = (ListView) view.findViewById(listViewId);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(clickListener);
			popupWindow = new PopupWindow(view, width + 10,
					LayoutParams.WRAP_CONTENT);
		}
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(locationView, offX, 0);
	}

	public PopupWindow getPopupWindow() {
		return popupWindow;
	}

	public void dismiss() {
		popupWindow.dismiss();
	}
}
