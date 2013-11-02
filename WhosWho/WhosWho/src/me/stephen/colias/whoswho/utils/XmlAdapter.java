package me.stephen.colias.whoswho.utils;

import me.stephen.colias.whoswho.R;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class XmlAdapter extends BaseAdapter implements ListAdapter {

	private final Activity activity;
	private final Elements faces;
	final ImageLoader imageLoader;
	final DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.loading)
			.cacheInMemory(true)
			.showImageForEmptyUri(R.drawable.loading)
			.displayer(new RoundedBitmapDisplayer(100))
			.resetViewBeforeLoading(false).build();

	public XmlAdapter(Activity activity, Elements faces) {
		assert activity != null;
		assert faces != null;

		this.faces = faces;
		this.activity = activity;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
	}

	@Override
	public int getCount() {
		if (null == faces)
			return 0;
		else
			return faces.size();
	}

	@Override
	public Element getItem(int position) {
		if (null == faces)
			return null;
		else
			return faces.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.row,
					null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvDepartment = (TextView) convertView
					.findViewById(R.id.tvDepartment);
			holder.tvInfo = (TextView) convertView.findViewById(R.id.tvInfo);
			holder.ivFace = (ImageView) convertView.findViewById(R.id.ivFace);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Element face = getItem(position);
		if (face != null) {
			String imageUrl = Constants.IMAGE_LOCATION
					+ face.getElementsByTag(Constants.IMAGE_TAG_NAME).get(0)
							.attr(Constants.FILE_NAME_ATTRIBUTE_NAME);

			imageLoader.displayImage(imageUrl, holder.ivFace, options);

			holder.tvName.setText(face
					.getElementsByTag(Constants.NAME_DEP_TAG_NAME).get(0)
					.text());
			holder.tvDepartment.setText(face
					.getElementsByTag(Constants.NAME_DEP_TAG_NAME).get(1)
					.text());
			holder.tvInfo.setText(face
					.getElementsByTag(Constants.INFO_TAG_NAME).get(0).text());
		}

		return convertView;
	}

	static class ViewHolder {
		TextView tvName;
		TextView tvDepartment;
		TextView tvInfo;
		ImageView ivFace;
	}
}