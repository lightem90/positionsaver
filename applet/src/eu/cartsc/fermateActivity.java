package eu.cartsc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import eu.cartsc.fermate.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.DONUT)
public class fermateActivity extends Activity {

	private String indirizzo = "stringa indirizzo vuota";
	private Cursor cursor;
	public String dataLastSync;
	public DBAdapter dbFermate;

	public class MyLocationListener implements LocationListener {

		@SuppressWarnings("static-access")
		@Override
		public void onLocationChanged(Location loc)

		{
			Toast.makeText(getApplicationContext(), "Fermata corrente",
					Toast.LENGTH_SHORT).show();
			double lat = loc.getLatitude();
			double lon = loc.getLongitude();

			String Text = "La posizione corrente è : Latitudine = "
					+ loc.convert(lat, 2) + " Longitudine = "
					+ loc.convert(lon, 2);

			Toast.makeText(getApplicationContext(), Text, Toast.LENGTH_SHORT)
					.show();
			TextView edit = (TextView) findViewById(R.id.textView1);
			edit.setText("" + loc.convert(lat, 2));

			edit = (TextView) findViewById(R.id.textView2);
			edit.setText("" + loc.convert(lon, 2));
			try {
				Geocoder gc = new Geocoder(getApplicationContext());
				List<Address> LSIndirizzo = gc.getFromLocation(lat, lon, 1);
				if (LSIndirizzo.size() > 0) {
					Address Indirizzo = LSIndirizzo.get(0);
					indirizzo = Indirizzo.getThoroughfare().toString();
				}
			}

			catch (IOException e) {
				Toast.makeText(getApplicationContext(),
						"Errore!Connessione non attiva", Toast.LENGTH_SHORT)
						.show();
			} finally {
				TextView edit4 = (TextView) findViewById(R.id.textView4);
				edit4.setText(indirizzo);
			}

		}

		public void onProviderDisabled(String provider) {
			Toast.makeText(getApplicationContext(), "Gps Disattivato",
					Toast.LENGTH_SHORT).show();
			
		}

		public void onProviderEnabled(String provider) {
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dbFermate = new DBAdapter(this);

	}

	public void onStart() {
		super.onStart();

	}

	@Override
	public void onResume() {
		super.onResume();

		final LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		LocationListener mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000,
				20, mlocListener);

		Button SYNC = (Button) findViewById(R.id.sync);
		SYNC.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				String sImei;
				String sNomeFermata;
				String sIndirizzo;
				String sDescrizione;
				String sData;

				dbFermate.open();
				cursor = dbFermate.selPrimaData();

				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {

					sImei = cursor.getString(0);
					sNomeFermata = cursor.getString(1);
					sIndirizzo = cursor.getString(2);
					sDescrizione = cursor.getString(3);
					sData = cursor.getString(4);

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://app.cartsc.eu/fermate/sync");

					try {

						List<NameValuePair> ls = new ArrayList<NameValuePair>();
						ls.add(new BasicNameValuePair("idimei", sImei));
						ls.add(new BasicNameValuePair("nomeFermata",
								sNomeFermata));
						ls.add(new BasicNameValuePair("indirizzo", sIndirizzo));
						ls.add(new BasicNameValuePair("descrizione",
								sDescrizione));
						ls.add(new BasicNameValuePair("data", sData));

						httppost.setEntity(new UrlEncodedFormEntity(ls));

						HttpResponse response = httpclient.execute(httppost);

						int risposta = response.getStatusLine().getStatusCode();
						if (risposta == 200) {

							Toast.makeText(getApplicationContext(),
									"Sincronizzazione effettuata con successo",
									Toast.LENGTH_SHORT).show();
						} else {

							Toast.makeText(getApplicationContext(),
									"Codice " + risposta, Toast.LENGTH_SHORT)
									.show();
						}

					} catch (ClientProtocolException e) {

						e.printStackTrace();

					} catch (IOException e) {

						e.printStackTrace();
					}

					cursor.moveToNext();
				}
				cursor.close();
				dbFermate.close();

			}

		});

		Button SALVA = (Button) findViewById(R.id.salva);
		SALVA.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				EditText acqFermata = (EditText) findViewById(R.id.editText1);
				String nomeFermata = acqFermata.getText().toString();

				EditText acqDesc = (EditText) findViewById(R.id.editText2);
				String desc = acqDesc.getText().toString();

				android.text.format.DateFormat df = new android.text.format.DateFormat();
				@SuppressWarnings("static-access")
				String data = df.format("yyyy-MM-dd hh:mm:ss",
						new java.util.Date()).toString();

				final TelephonyManager tm = (TelephonyManager) getBaseContext()
						.getSystemService(Context.TELEPHONY_SERVICE);
				String idimei = tm.getDeviceId();

				dbFermate.open();
				dbFermate.creaFermata(idimei, nomeFermata, indirizzo, desc,
						data);

				Toast.makeText(getApplicationContext(),
						"Inserita: " + nomeFermata + " alle ore " + data,
						Toast.LENGTH_SHORT).show();
				dbFermate.close();

			}

		});

		Button ATTIVA = (Button) findViewById(R.id.attiva);
		ATTIVA.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				final boolean flag = mlocManager
						.isProviderEnabled(LocationManager.GPS_PROVIDER);
				if (flag) {
					Toast.makeText(getApplicationContext(), "GPS ATTIVO",
							Toast.LENGTH_SHORT).show();
				}

				else {
					Toast.makeText(getApplicationContext(), "ATTIVA GPS",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(
							Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivityForResult(intent, 0);
				}
			}
		});

		Button reset = (Button) findViewById(R.id.reset);
		reset.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						fermateActivity.this);

				alertDialogBuilder.setTitle("Conferma reset");

				alertDialogBuilder
						.setMessage(
								"Stai cancellando TUTTE le fermate. Continua?")
						.setCancelable(false)
						.setPositiveButton("Si",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

										int c;
										dbFermate.open();
										c = dbFermate.cancella();
										dbFermate.close();
										Toast.makeText(
												getApplicationContext(),
												"Sono state cancellate con successo "
														+ c + " fermate!",
												Toast.LENGTH_SHORT).show();

									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();

			}

		});
	}

	public void onPause() {
		super.onPause();

	}

	public void onStop() {
		super.onStop();
	}

}