package com.ronnie.view;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * ronniesun
 */
public class HighlightedTextView extends TextView {

	private static final String TAG = "Highlight";
	private String content;
	private String keyword;
	private int highlightColor;
	private int tvWidth;

	public HighlightedTextView(Context context) {
		super(context);
	}

	public HighlightedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HighlightedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (content != null) {
			tvWidth = MeasureSpec.getSize(widthMeasureSpec);
			highlight();
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void highlight() {
		Log.e(TAG, "setText");
		int totallength = getTextViewLength(content);
		if (tvWidth < totallength) {
			int len_keyword = getTextViewLength(keyword);
			int index_keyword = content.indexOf(keyword);

			// substring keyword behind front
			String front = content.substring(0, index_keyword);
			Log.e(TAG, "front = " + front);
			String behind = content.substring(index_keyword + keyword.length(), content.length());
			Log.e(TAG, "behind = " + behind);

			int len_front = getTextViewLength(front);
			Log.e(TAG, "len_front = " + len_front);
			int len_behind = getTextViewLength(behind);
			Log.e(TAG, "len_behind = " + len_behind);

			if (len_front + len_keyword < tvWidth) {
				// Add keywords ahead is less than the display area
				setEllipsize(TruncateAt.END);
			} else if (len_behind + len_keyword < tvWidth) {
				// Rear add keyword is less than the display area
				setEllipsize(TruncateAt.START);
			} else {
				setEllipsize(TruncateAt.END);
				// The rest of the half
				int len_residue = tvWidth - len_keyword;
				int len_half = len_residue / 2; // 剩余的一半
				Log.e(TAG, "len_half = " + len_half);
				// Only for computing the rear always keep ahead
				int len_bound = len_front - len_half;
				Log.e(TAG, "len_bound = " + len_bound);
				int index_front = 0;
				String tempStr = "";
				for (int i = 0; i < front.length(); i++) {
					tempStr = front.substring(0, i);
					float len = getPaint().measureText(tempStr);
					if (len >= len_bound) {
						index_front = i;
						break;
					}
				}

				// +1 error
				content = "..." + content.substring(index_front, content.length());
			}
		}
		setHighlight(content, keyword);
	}

	public void setText(String content, String keyword, int highlightColor) {
		Log.e(TAG, "setText");
		this.content = content;
		this.keyword = keyword;
		this.highlightColor = highlightColor;
	}

	private void setHighlight(String content, String keyword) {
		SpannableStringBuilder style = new SpannableStringBuilder(content);
		int index = 0;
		int next = 0;
		while ((index = content.indexOf(keyword, next)) != -1) {
			next = index + keyword.length();
			style.setSpan(new ForegroundColorSpan(highlightColor), index, next, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		setText(style);
	}

	private int getTextViewLength(String content) {
		return (int) getPaint().measureText(content);
	}

}
