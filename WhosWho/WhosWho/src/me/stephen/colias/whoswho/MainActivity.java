package me.stephen.colias.whoswho;

import me.stephen.colias.whoswho.R;
import me.stephen.colias.whoswho.utils.NetworkDao;
import me.stephen.colias.whoswho.utils.XmlAdapter;

import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ProgressDialog mProgressDialog = null;
	ListView facesListView;
	XmlAdapter xmlAdapter;
	Elements faces;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		facesListView = (ListView) findViewById(R.id.facesListView);
	}

	@Override
	public void onResume() {
		super.onResume();
		reloadPoints();
	}

	@Override
	public void onPause() {
		super.onPause();
		hideProgress();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	private void reloadPoints() {
		showProgress(getString(R.string.searching), false);
		new MyAsyncTaskGetPlaces().execute();
	}

	private class MyAsyncTaskGetPlaces extends
			AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			try {
				faces = NetworkDao.getFaces();
			} catch (Exception e) {
				return null;
			}
			return "";
		}

		protected void onProgressUpdate(Integer... progress) {
			mProgressDialog.setProgress(progress[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			displayResults();
			hideProgress();
		}

		private void displayResults() {
			xmlAdapter = new XmlAdapter(MainActivity.this, faces);
			facesListView.setAdapter(xmlAdapter);
		}

	}

	private void showProgress(String text, boolean progress_count) {
		if (mProgressDialog != null) {
			mProgressDialog.show();
		} else {
			if (!isFinishing()) {
				if (progress_count) {
					mProgressDialog = new ProgressDialog(this);
					mProgressDialog.setMessage(text);
					mProgressDialog
							.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					mProgressDialog.setCancelable(false);
					mProgressDialog.show();
				} else {
					mProgressDialog = new ProgressDialog(this);
					mProgressDialog.setMessage(text);
					mProgressDialog.setCancelable(false);
					mProgressDialog.show();
				}
			}
		}
	}

	private void hideProgress() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		reloadPoints();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
